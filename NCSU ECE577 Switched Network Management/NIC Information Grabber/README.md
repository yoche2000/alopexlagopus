# NIC Grabber
With the given remote host information (IP, USR, PWD), provide NIC details on the remote host. Filtering by IP/MAC/TYPE are available.

## Information
- ECE577, LAB-HW1, Q3
- Yo-Che "Thomas" Lee
- Sep 21st, 2023

## Environment
- Python 3
```bash
$: sudo apt install python3.8
```


### Libraries
- [*subprocess*](https://docs.python.org/3/library/subprocess.html)
```bash 
import subprocess
```


## Components
- README.md
- getipaddr.py


# Description

1. Run getipaddr.py
``` bash
$: python3 getipaddr.py
```
2. Enter the IP address, Username, and Password of the remote host. For a newly connected SSH server, fingerprint has to be allowed to establish the connection. 
```bash
Remote Host IP (connection will be done via ssh): 152.14.83.141
User Name: ece577
Please enter 'yes' when asked to allow fingerprint, then enter the password.

ece577@152.14.83.141's password:
```
3. The details of all NIC on the remote host will be printed out. Then, filters can be applied using the below commands:

### f(ilter)
Then, enter the number of hosts to be put into the subnet (E.g., 100). The address of the newly appended subnet will be returned. There are 3 options of filter including TYPE, IP, and MAC. The TYPE filter shows all NICs of the same type; The IP/MAC filter shows all NICs whose IP/MAC address have the inserted keyword.

- TYPE filter
```bash
Command Options q{uit}, l(ist), f(ilter):f
Insert Filter Type (IP, MAC, TYPE), or 'q' to quit: TYPE
Insert TYPE: loopback
lo
====================
name        : lo
mtu         : 65536
qdisc       : noqueue
state       : UNKNOWN
group       : default
qlen        : 1000
type        : loopback
MAC         : 00:00:00:00:00:00
inet        : 127.0.0.1/8
valid_lft   : forever
scope       : host
inet6       : ::1/128
i6_scope    : host
i6_valid_lft: forever
```
- IP filter

```bash
Command Options q{uit}, l(ist), f(ilter):f
Insert Filter Type (IP, MAC, TYPE), or 'q' to quit: IP
Insert IP: 192.168.
virbr2
====================
name        : virbr2
mtu         : 1500
qdisc       : noqueue
state       : UP
group       : default
qlen        : 1000
type        : ether
MAC         : 52:54:00:6a:d9:24
inet        : 192.168.122.1/24
inet_brd    : 192.168.122.255
valid_lft   : forever
scope       : global
snmbr1
====================
name        : snmbr1
mtu         : 1500
qdisc       : noqueue
state       : UP
group       : default
qlen        : 1000
type        : ether
MAC         : fe:54:05:77:01:02
inet        : 192.168.131.1/24
valid_lft   : forever
scope       : global
inet6       : fe80::9ccc:6aff:fe95:91f0/64
i6_scope    : link
i6_valid_lft: forever
```
- MAC filter

```bash
Command Options q{uit}, l(ist), f(ilter):f
Insert Filter Type (IP, MAC, TYPE), or 'q' to quit: MAC
Insert MAC: 52:54:00
virbr2
====================
name        : virbr2
mtu         : 1500
qdisc       : noqueue
state       : UP
group       : default
qlen        : 1000
type        : ether
MAC         : 52:54:00:6a:d9:24
inet        : 192.168.122.1/24
inet_brd    : 192.168.122.255
valid_lft   : forever
scope       : global
virbr2-nic
====================
name        : virbr2-nic
mtu         : 1500
qdisc       : fq_codel
state       : DOWN
group       : default
qlen        : 1000
type        : ether
MAC         : 52:54:00:6a:d9:24
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

### l(ist)
Listing all NIC details without filtering

