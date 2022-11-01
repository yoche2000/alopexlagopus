import json
import datetime
from urllib.parse import urlparse
import requests
from Transaction import Transaction
from Block import Block
from datetime import datetime


class Blockchain:
    difficulty = 2
    nodes = set()

    def __init__(self):
        self.unconfirmed_transactions = []
        self.chain = []
        self.create_genesis_block()

    def create_genesis_block(self):
        genesis_block = Block(
            0, [], datetime.now().strftime("%m/%d/%Y, %H:%M:%S"), "0" * 64, None, 0)
    
        while True:
            if genesis_block.compute_hash()[:self.difficulty] == "0" * self.difficulty:
                genesis_block.hash = genesis_block.compute_hash()
                self.chain.append(genesis_block.to_json())
                break
            else:
                genesis_block.nonce += 1

    def add_new_transaction(self, transaction: Transaction):
        if transaction.verify_transaction_signature():
            self.unconfirmed_transactions.append(transaction.to_json())
            return True
        else:
            return False

    def add_block(self, block, proof):
        previous_hash = json.loads(self.chain[-1])['hash']
        if previous_hash != block.previous_hash:
            return False

        if not self.is_valid_proof(block, proof):
            return False

        block.hash = proof
        self.chain.append(block.to_json())
        return True

    def is_valid_proof(self, block, block_hash):
        return (block_hash.startswith('0' * int(Blockchain.difficulty)) and block_hash == block.compute_hash())
        
    def proof_of_work(self, block):
        block.nounce = 0
        computed_hash = block.compute_hash()
        while not computed_hash.startswith('0' * int(Blockchain.difficulty)):
            block.nonce += 1
            computed_hash = block.compute_hash()
        return computed_hash

    def mine(self, myWallet):
        block_reward = Transaction(
            "Block_Reward",
            myWallet.identity,
            "5.0",
            None
        ).to_json()
        self.unconfirmed_transactions.insert(0, block_reward)
        if not self.unconfirmed_transactions:
            return False

        new_block = Block(index= json.loads(self.chain[-1])['index'] + 1,
                          transactions=self.unconfirmed_transactions,
                          timestamp=datetime.now().strftime("%m/%d/%Y, %H:%M:%S"),
                          previous_hash=json.loads(self.chain[-1])['hash'],
                          hash=None,
                          nonce=0)

        proof = self.proof_of_work(new_block)
        if self.add_block(new_block, proof):
            self.unconfirmed_transactions = []
            return new_block
        else:
            return False

    def register_node(self, node_url):
        parsed_url = urlparse(node_url)
        if parsed_url.netloc:
            self.nodes.add(parsed_url.netloc)
        elif parsed_url.path:
            self.nodes.add(parsed_url.path)
        else:
            raise ValueError('Invalid URL')

    def consensus(self):
        neighbours = self.nodes
        new_chain = None
        max_length = len(self.chain)

        for node in neighbours:
            response = requests.get('http://' + node + '/fullchain')
            if response.status_code == 200:
                length = response.json()['length']
                chain = response.json()['chain']
                if length > max_length and self.vaild_chain(chain):
                    max_length = length
                    new_chain = chain

        if new_chain:
            self.chain = json.loads(new_chain)
            return True

        return False

    def vaild_chain(self, chain):
        current_index = 0
        chain = json.loads(chain)
        while current_index < len(chain):
            block = json.loads(chain[current_index])
            current_block = Block(block['index'],
                                  block['transactions'],
                                  block['timestamp'],
                                  block['previous_hash'],
                                  block['hash'],
                                  block['nonce'])
            if current_index + 1 < len(chain):
                if current_block.compute_hash() != json.loads(chain[current_index + 1])['previous_hash']:
                    return False
            if isinstance(current_block.transactions, list):
                for transaction in current_block.transactions:
                    transaction = json.loads(transaction)
                    if transaction['sender'] == 'Block_Reward':
                        continue
                    current_transaction = Transaction(transaction['sender'],
                                                      transaction['recipient'],
                                                      transaction['value'],
                                                      transaction['signature'])
                    if not current_transaction.verify_transaction_signature():
                        return False
                if not self.is_valid_proof(current_block, block['hash']):
                    return False
            current_index += 1

        return True
