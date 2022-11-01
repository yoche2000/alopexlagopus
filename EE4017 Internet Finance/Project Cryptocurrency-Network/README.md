# Cryptocurrency-Network

This is the project for EE4017 Internet Finance 

<img src="pic/5000.png" width="50%" height="50%">

Python version: 3.9.12

Running device: Macbook Pro 2021 

## Getting Started

### Installation

1. Clone the repo
```bash
git clone https://github.com/andy145155/Cryptocurrency-Network.git
```

2. Setup a virtual environment 
```bash
pip3 install virtualenv
virtualenv crypto
source crypto/bin/activate
```

3.Install required pip packages
```bash
pip3 install pycryptodome
pip3 install flask
pip3 install requests
```

## Run

Open 3 Terminal tabs and run the following code in each terminal.

The 1st terminal window
```bash
source crypto/bin/activate
python3 main.py
```

The 2nd terminal window
```bash
source crypto/bin/activate
python3 main5001.py
```

The 3rd terminal window
```bash
source crypto/bin/activate
python3 main5002.py
```

## Usage

You will see nothing is connected to the node initially by entering the url path: 

http://127.0.0.1:5000/get_nodes

http://127.0.0.1:5001/get_nodes

http://127.0.0.1:5002/get_nodes