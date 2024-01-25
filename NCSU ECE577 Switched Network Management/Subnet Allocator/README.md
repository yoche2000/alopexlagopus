# Subnet Allocator
Within a given range of IP addresses, the program allocates a subnet with given condition (number of hosts). 

## Information
- ECE577, LAB-HW1, Q4
- Yo-Che "Thomas" Lee
- Sep 19th, 2023

## Environment
- Python 3
```bash
$: sudo apt install python3.8
```


### Libraries
- [*math*](https://docs.python.org/3/library/math.html)
```bash
pip install math
```
- [*ipaddress*](https://docs.python.org/3/library/math.html)

```bash
pip install ipaddress
```

## Components
- README.md
- main.py


# Description

1. Run main.py
``` bash
$: python main.py
```
2. Enter the range of address to be allocated )(10.0.0.0/22 as an example)
```bash
python .\main.py
Enter the network range (E.g., 10.0.0.0/16): 10.0.0.0/22
```
3. After the range of address has been set, use the command *a* to append a subnet, command *p* to view the allocated subnets, command *q* to end the program, command *r* to see the remaining range of address.

### a(ppend)
Then, enter the number of hosts to be put into the subnet (E.g., 100). The address of the newly appended subnet will be returned. 

```bash
[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): a
[SYS-APP] To allocate a new subnet, enter hosts number: 100
[SYS-APP] A new subnet with 100 host(s) has been created on 10.0.0.0/25
```

**Example**, add 2 other subnets with 54 and 499 hosts.
```
[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): a
[SYS-APP] To allocate a new subnet, enter hosts number: 54
[SYS-APP] A new subnet with 54 host(s) has been created on 10.0.0.128/26

[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): a
[SYS-APP] To allocate a new subnet, enter hosts number: 499
[SYS-APP] A new subnet with 499 host(s) has been created on 10.0.2.0/23
```

### p(rint)
The command *p* returns the current allocated subnets and their numbers of hosts. 
```bash
[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): p
[SYS-PNT] The allocated subnets are:
∟ Subnet 0
  ∟ Hosts: 100
  ∟ Address: 10.0.0.0/25
∟ Subnet 1
  ∟ Hosts: 54
  ∟ Address: 10.0.0.128/26
∟ Subnet 2
  ∟ Hosts: 499
  ∟ Address: 10.0.2.0/23
```

### r(emaining)
The command *r* returns the remained available range of address. 
```bash
[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): r
[SYS-RMN] The unallocated range of address:
 ∟ 10.0.0.192~10.0.0.255
 ∟ 10.0.1.0~10.0.1.255
```
