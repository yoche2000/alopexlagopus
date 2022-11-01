import json
import hashlib

class Block:
    def __init__(self, index, transactions, timestamp, previous_hash, hash, nonce):
        self.index = index
        self.transactions = transactions
        self.timestamp = timestamp
        self.previous_hash = previous_hash
        self.hash = hash
        self.nonce = nonce

    def __str__ (self):
        return str({
            'index': self.index,
            'transactions': self.transactions,
            'timestamp': self.timestamp,
            'previous_hash': self.previous_hash,
            'hash':self.hash,
            'nonce': self.nonce
        })
       

    def to_dict(self):
        return({
            'index': self.index,
            'transactions': self.transactions,
            'timestamp': self.timestamp,
            'previous_hash': self.previous_hash,
            'nonce': self.nonce
        })

    def to_json(self):
        return json.dumps(self.__dict__)


    def compute_hash(self):
        return hashlib.sha256(str(self.to_dict()).encode()).hexdigest()