import Crypto
import Crypto.Random
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5
from Crypto.Hash import SHA256
import binascii
from Transaction import Transaction

class Wallet:
    def __init__(self):
        random = Crypto.Random.new().read
        self._private_key = RSA.generate(1024, random)
        self._public_key = self._private_key.publickey()

    def sign_transaction(self, transaction: Transaction):
        signer = PKCS1_v1_5.new(self._private_key)
        h = SHA256.new(str(transaction.to_dict()).encode('utf-8'))
        return binascii.hexlify(signer.sign(h)).decode('ascii')

    @property
    def identity(self):
        pubkey = binascii.hexlify(self._public_key.exportKey(format='DER'))
        return pubkey.decode('ascii')

    @property
    def sign(self):
        return binascii.hexlify(self._private_key.exportKey(format='DER')).decode('ascii')
