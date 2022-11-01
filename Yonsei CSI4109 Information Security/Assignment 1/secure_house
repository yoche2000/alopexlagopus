#!/usr/bin/python3

import os
import sys

arg_list = sys.argv
cnt = len(sys.argv)
keylist = ["keylist"]
inhouse_list = []

key_slot = {
        "name":"",
        "key":"",
        "var":False
        }

def hi():
    print("hi")
    return

try:
    owner_usr = arg_list[1]
except IndexError:    
    print("Insert the owner's name to set the lock.")
    raise(SystemExit)

try:
    keylist = arg_list[2:]
except IndexError:
    print("Set at least one key.")
    raise(SystemExit)


def ik(name, key):
    key_slot["name"]=name
    key_slot["key"]=key
    key_slot["var"]=False
    print("KEY " + key + " INSERTED BY " + name )


def tk(name):
    if (name == key_slot["name"]) and (key_slot["key"] in keylist):
        key_slot["var"] = True
        print("SUCCESS " + name + " TURNS KEY " + key_slot["key"])
    else:
        print("FAILURE " + name + " UNABLE TO TURN KEY " + key_slot["key"])

def eh(name):
    if (name == key_slot["name"]) and key_slot["var"]:
        inhouse_list.append(name)  
        key_slot["name"]=""
        key_slot["key"]=""
        key_slot[""]=False
        print("ACCESS ALLOWED")
    else:
        print("ACCESS DENIED")

def wi():
    if len(inhouse_list) == 0:
        txt = "NOBODY HOME"
    else:
        txt = str(inhouse_list)
        txt = txt.replace("[",'').replace("]",'').replace("\'",'')
    print(txt)

def lh(name):
    if name in inhouse_list:
        inhouse_list.remove(name)
        print("OK")
    else:
        print(name + " NOT HERE")

def ck(name, key):
    if name == owner_usr:    
        print("OK")
        return key
    else:
        print("ACCESS DENIED")


def show():
    print("--")
    print("Owner: " + str(owner_usr))
    print("KEYLIST: " + str(keylist))
    print("KEYSLOT: " + str(key_slot))
    print("INHOUSE: " + str(inhouse_list))
    print("--")


##show()




while(True):
    
    cmd = str(input())   

    if(cmd == ""):
        raise(SystemExit)   
    else:

        if cmd[:10] == "INSERT KEY":
            if len(cmd.split(" ", ))<4:
                print("ERROR")
            else:
                name =  cmd.split(" ", )[2]
                key = cmd.split(" ",)[3]
                ik(name, key)            

        elif cmd[:8] == "TURN KEY":
            if len(cmd.split(" ", ))<3:
                print("ERROR")
            else:
                tk(cmd.split(" ", )[2])   

        elif cmd[:11] == "ENTER HOUSE":
            if len(cmd.split(" ", ))<3:
                print("ERROR")
            else:
                eh(cmd.split(" ", )[2])

        elif cmd[:13] == "WHO'S INSIDE?":
            wi()

        elif cmd[:11] == "LEAVE HOUSE":
            if len(cmd.split(" ", ))<3:
                print("ERROR")
            else:
                lh(cmd.split(" ", )[2])

        elif cmd[:12] == "CHANGE LOCKS":
            if len(cmd.split(" ", ))<4:
                print("ERROR")
            else:                          
                name =  cmd.split(" ", )[2]
                key = cmd.split(" ", )[3:]
                keylist = ck(name, key)
        else:
            print("ERROR")          

    ##show()                 

sys.exit()




