#!/bin/bash
# Testing mac

sudo make clean 
sudo make 
echo ">>Unclassified"
./mac read unclassified.data 
echo ">>confidential"
./mac read confidential.data 
echo ">>secret"
./mac read secret.data 
echo ">>top_secret"
./mac read top_secret.data 


 
