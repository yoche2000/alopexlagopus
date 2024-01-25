import psutil
import os
from datetime import datetime
import argparse

p = argparse.ArgumentParser()

p.add_argument("-T", "--interval", type=int,
        help="set the interval for cpu ulization sampling")
p.add_argument("-P", "--duration", type=int,
        help="Total duration of monitoring")
p.add_argument("-X", "--threshold1", type=int,
        help="User defined HIGH CPU alert threshold")
p.add_argument("-Y", "--threshold2", type=int,
        help="User defined very HIGH CPU alert threshold")
args = p.parse_args()


# defaults
T = 5       #default time of intercal
TP = 300    #default total duration
X = 70      #default HIGH usage threshold
Y = 50      #default VERY HIGH usage threshold

if args.interval:
    T = args.interval
if args.duration and args.duration>300:
    TP = args.duration
if args.threshold1:
    X = args.threshold1
if args.threshold2:
    Y = args.threshold2

print("CPU_Monitor Starting with a sample interval of", T, "seconds for",TP, "seconds.")
print("HIGH & VERY HIGH threshold are set as:", X, "and", Y,)

ma1 = 0
ma5 = 0     #default  5 min MA value
ma15 = 0    #default 15 min MA value
n1 = T/60
n5 = T/300
n15 = T/900

path = ""

def get_timestamp():
    now = datetime.now()
    ts = datetime.timestamp(now)
    return ts

def get_cpu(t=T):
    v = psutil.cpu_percent(t)
    t = get_timestamp()
    print(t, 'The CPU usage is: ', v)
    return v

def log_update_alert(t, alert, ma):
    global path
    fn_alert = path+"/alert.csv"
    alert_line = str(t)+", "+str(alert)+", "+str(ma)
    with open(fn_alert,'a') as file:
        file.write(alert_line)
        file.write('\n')

def check_alert(v, ma1, ma5):
    if (ma5 > Y and v > ma1 and ma1 > ma5):
        alert = "[ALERT] Very HIGH CPU usage"
    elif (ma1 > X):
        alert =  "[ALERT] HIGH GPU usage"
    else:
        return
    print(alert)
    t=get_timestamp()
    log_update_alert(t, alert, ma1)

def log_update_cpu(t, ma1, ma5, ma15):
    global path
    fn_log = path+"/cpu_log.csv"
    cpu_line = str(t)+", "+str(ma1)+", "+str(ma5)+", "+str(ma15)
    with open(fn_log,'a') as file:
        file.write(cpu_line)
        file.write('\n')

def stat_update(new_v, ma1, ma5, ma15):
    if (ma1 == 0 and ma5 == 0 and ma15 == 0):
        ma1 = new_v
        ma5 = new_v
        ma15 = new_v
    else:
        ma1 = (1-n1)*ma1 + n1*new_v
        ma5 = (1-n5)*ma5 + n5*new_v
        ma15 = (1-n15)*ma15 + n15*new_v
    t=get_timestamp()
    print("MA1: %.2f, MA5: %.2f, MA15: %.2f" % (ma1, ma5, ma15))
    log_update_cpu(t, ma1, ma5, ma15)
    return ma1, ma5, ma15

def log_init():
    dt = datetime.now()
    global path 
    path = str(dt).replace("-", '').replace(":",'').replace(" ", '-')
    path = "log-"+path
    fn_log = path+"/cpu_log.csv"
    fn_alert = path+"/alert.csv"
    print (fn_log, fn_alert)
    
    os.mkdir(path)
    file = open(fn_log, 'a')
    file.close()
    file = open(fn_alert, 'a')
    file.close()
    return path




log_init()
endtime = TP/T
timer = 0
while (timer < endtime):
    v = get_cpu(t=5)
    ma1, ma5, ma15 = stat_update(v, ma1, ma5, ma15)
    check_alert(v, ma1, ma5)
    timer += 1


