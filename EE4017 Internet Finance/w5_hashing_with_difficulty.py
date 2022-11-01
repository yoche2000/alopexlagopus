def sha256(message):
  encoded = message.encode('ascii')
  hashed_output = hashlib.sha256(encoded)
  return hashed_output.hexdigest()

def proof_of_work(message, diff = 2):
  nonce = 0
  computed_hash = sha256(message + str(nonce))
  while not computed_hash.startswith("0" * diff):
    nonce += 1
    computed_hash = sha256(message+str(nonce))
  return computed_hash, nonce

msg = "EE4017 Internet Finance"
pof = proof_of_work(msg, 0)
n = 0