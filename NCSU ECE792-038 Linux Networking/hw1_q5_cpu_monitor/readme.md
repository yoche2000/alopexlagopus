# CPU Monitor
A CPI monitoring tool with logging and high usage alert.

## General Information
- ECE792-38 Linux Networking, HW1, Problem 5
- Yo-Che "Thomas" Lee
- Jan 24th, 2023

## Environment
- Python 3
```bash
$: sudo apt install python3.8
```


### Libraries
- psutil
- os
- datetime
- argparse

```python
import psutil
import os
from datetime import datetime
import argparse
```


## Components
- readme.md
- cpu_monitor.py
- clean.sh

# Description

1. Run cpu_monitor.py and see argument options
``` bash
$ python3 cpu_monitor.py -h

usage: cpu_monitor.py [-h] [-T INTERVAL] [-P DURATION] [-X THRESHOLD1] [-Y THRESHOLD2]

options:
  -h, --help            show this help message and exit
  -T INTERVAL, --interval INTERVAL
                        set the interval for cpu ulization sampling
  -P DURATION, --duration DURATION
                        Total duration of monitoring
  -X THRESHOLD1, --threshold1 THRESHOLD1
                        User defined HIGH CPU alert threshold
  -Y THRESHOLD2, --threshold2 THRESHOLD2
                        User defined very HIGH CPU alert threshold
```
2. The T, TP, X, Y as as specified in the homework specifications, are mutable. For example, to run the program for 30 minutes with sample per 10 seconds, and give a HIGH/VERYHIGH alert on threshold 50%/50%, use the following commands. Path to the logs are shown in the output prompt.
 
```bash
$ python3 cpu_monitor.py -T 10 -P 900 -X 50 -Y 50

CPU_Monitor Starting with a sample interval of 10 seconds for 900 seconds.
HIGH & VERY HIGH threshold are set as: 50 and 50
log-20240124-215017.955157/cpu_log.csv log-20240124-215017.955157/alert.csv
1706151022.961496 The CPU usage is:  0.5
MA1: 0.50, MA5: 0.50, MA15: 0.50
1706151027.967596 The CPU usage is:  0.2
MA1: 0.45, MA5: 0.49, MA15: 0.50
1706151032.973858 The CPU usage is:  1.0
MA1: 0.54, MA5: 0.51, MA15: 0.50
...

...
1706151203.131322 The CPU usage is:  100.0
MA1: 97.51, MA5: 49.89, MA15: 20.63
[ALERT] HIGH GPU usage
1706151208.135643 The CPU usage is:  100.0
MA1: 97.92, MA5: 51.56, MA15: 21.51
[ALERT] Very HIGH CPU usage
...

```
3. After running the CPU Monitor, the records can be found in the paths. A directory name after the timestamp at the starting time can be found parallel to the main program. The CPU log and alert log can be found under the directory.

```bash
$ ls -al

total 24
drwxrwxr-x 3 yoche2000 yoche2000 4096 Jan 24 21:56 ./
drwxr-xr-x 3 yoche2000 yoche2000 4096 Jan 24 21:28 ../
-rwxrwxrwx 1 yoche2000 yoche2000   11 Jan 24 20:59 clean.sh*
-rw-rw-r-- 1 yoche2000 yoche2000 3135 Jan 24 21:35 cpu_monitor.py
-rw-rw-r-- 1 yoche2000 yoche2000 1882 Jan 24 19:13 cpu_monitor.py.bak
drwxrwxr-x 2 yoche2000 yoche2000 4096 Jan 24 21:50 log-20240124-215017.955157/

$ ls -al log-20240124-215017.955157/

total 20
drwxrwxr-x 2 yoche2000 yoche2000 4096 Jan 24 21:50 .
drwxrwxr-x 3 yoche2000 yoche2000 4096 Jan 24 21:56 ..
-rw-rw-r-- 1 yoche2000 yoche2000 2465 Jan 24 21:55 alert.csv
-rw-rw-r-- 1 yoche2000 yoche2000 6775 Jan 24 21:57 cpu_log.csv

$ tail -5 log-20240124-215017.955157/cpu_log.csv 

1706151448.404721, 0.7833467651480646, 26.536076017152478, 25.480456701514683
1706151453.411561, 0.6861223042900538, 25.65820681658073, 25.19956273816452
1706151458.418065, 0.6051019202417115, 24.80959992269471, 24.921789818851583
1706151463.424218, 0.5709182668680929, 23.995946591938218, 24.649325487531012
1706151468.431528, 0.5090985557234108, 23.202748372206944, 24.377666315447335

```
4. running the clean.sh bash script clears all the logs and directories created by CPU Monitor.

```bash
$ ./clean.sh
```

