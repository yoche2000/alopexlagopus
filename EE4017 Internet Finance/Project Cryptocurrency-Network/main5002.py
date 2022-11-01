import json
from flask import Flask, jsonify, request, render_template
import requests
from Blockchain import Blockchain
from Transaction import Transaction
from Wallet import Wallet


app = Flask(__name__)


@app.route("/")
def index():
    return render_template("index.html", user=5002)


@app.route("/get_wallet")
def get_wallet():
    return jsonify(myWallet.identity),200


@app.route("/change_difficuly", methods=['POST'])
def change_difficuly():
    values = request.form
    number = values.get('number')
    Blockchain.difficulty = number
    return "successfully change difficuly to " + str(number)

@app.route('/new_transaction', methods=['POST'])
def new_transaction():
    values = request.form.to_dict()
    required = ['recipient_address', 'amount']
    if not all(k in values for k in required):
        return 'Missing Values', 400

    transaction = Transaction(
        myWallet.identity,
        values['recipient_address'],
        values['amount'],
        None
    )
    transaction.add_signature(myWallet.sign_transaction(transaction))
    transaction_result = blockchain.add_new_transaction(transaction)

    if transaction_result:
        response = {'message': 'Transaction will be added to Block'}
        return jsonify(response), 201
    else:
        response = {'message': 'Invalid Transaction!'}
        return jsonify(response), 406


@app.route("/get_transaction", methods=['GET'])
def get_transaction():
    transaction = blockchain.unconfirmed_transactions
    response = {'transactions': transaction}
    return jsonify(response), 200


@app.route("/chain", methods=['GET'])
def last_ten_blocks():
    response = {'chain': blockchain.chain[-10:],
                'length': len(blockchain.chain)}
    return jsonify(response), 200


@app.route("/fullchain", methods=['GET'])
def full_chain():
    response = {'chain': json.dumps(blockchain.chain),
                'length': len(blockchain.chain)}
    return jsonify(response), 200


@app.route("/get_nodes", methods=['GET'])
def get_nodes():
    nodes = list(blockchain.nodes)
    response = {'nodes': nodes}
    return jsonify(response), 200


@app.route("/register_node", methods=['POST'])
def register_node():
    value = request.form
    node = value.get('node')
    com_port = value.get('com_port')
    if com_port is not None:
        blockchain.register_node(
            'http://' + request.remote_addr + ":" + com_port)
        return "ok", 200
    if node is None and com_port is None:
        return "Error: Please suply a valid list of nodes", 400

    blockchain.register_node('http://' + node)
    node_list = requests.get('http://' + node + '/get_nodes')
    if node_list.status_code == 200:
        node_list = node_list.json()['nodes']
        for node in node_list:
            blockchain.register_node('http://' + node)

    for new_nodes in blockchain.nodes:
        requests.post('http://' + new_nodes + '/register_node',
                      data={'com_port': str(port)})

    replaced = blockchain.consensus()
    if replaced:
        response = {
            'message': 'Longer authoritative chain found from peers, replacing ours',
            'total_nodes': [node for node in blockchain.nodes]
        }
    else:
        response = {
            'message': 'New nodes have been added, but our chain is authoritative',
            'total_nodes': [node for node in blockchain.nodes]
        }
    return jsonify(response), 201


@app.route("/consensus", methods=['GET'])
def consensus():
    replaced = blockchain.consensus()
    if replaced:
        response = {'message': 'Our chain was replaced'}
    else:
        response = {'message': 'Our chain is authoritative'}

    return jsonify(response), 200


@app.route("/mine", methods=['GET'])
def mine():
    newblock = blockchain.mine(myWallet)
    for node in blockchain.nodes:
        requests.get('http://' + node + '/consensus')
    response = {
        'index': newblock.index,
        'transactions': newblock.transactions,
        'timestamp': newblock.timestamp,
        'nonce': newblock.nonce,
        'hash': newblock.hash,
        'previous_hash': newblock.previous_hash
    }
    return jsonify(response), 200


if __name__ == '__main__':
    myWallet = Wallet()
    blockchain = Blockchain()
    port = 5002
    app.run(host='127.0.0.1', port=port)
