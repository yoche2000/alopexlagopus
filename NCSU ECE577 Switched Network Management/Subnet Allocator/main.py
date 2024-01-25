import ipaddress
import math
#input one network, allocate subnet with host numbers, return allocated/remaining network(s)

def new_subnet(network, hosts_num):
    nwk_pref = network.prefixlen
    sub_pref = 32 - math.ceil(math.log2(hosts_num + 2))
    if nwk_pref > sub_pref:
        return {"status": False}
    elif nwk_pref == sub_pref:
        return {"status": True, "allocated": network, "remaining": []}
    else:
        s = list(network.subnets(new_prefix=sub_pref))
        rem = []
        for i in range(math.ceil(math.log2(len(s)))):
            n = s[2 ** i]
            rem.append(n.supernet(prefixlen_diff=i))
        return {"status": True, "allocated": s[0], "remaining": rem}


def allocate(system_space, hosts_num):
    allocated = ""
    network_list = system_space["unallocated"]
    rem = []
    flag = False  # indicate alocated or not
    for n in range(len(network_list)):
        if not flag:  # Not Yet Allocated
            result = new_subnet(network_list[n], hosts_num)
            flag = result["status"]
            if flag:  # available subnet found
                allocated = result["allocated"]
                rem += result["remaining"]
            else:  # available subnet not found
                rem.append(network_list[n])
        else:
            rem.append(network_list[n])

    if flag:
        system_space["allocated"]['hosts'].append(hosts_num)
        system_space["allocated"]['subnets'].append(allocated)
        system_space["unallocated"] = rem
        print("[SYS-APP] A new subnet with", hosts_num, "host(s) has been created on", allocated)
    else:
        print("[SYS-APP] The intended subnet of size", hosts_num, "cannot be created.")
    return flag, system_space


def printsp(sp):
    print("[SYS-PNT] The allocated subnets are:")
    for i in range(len(sp["allocated"]['subnets'])):
        print("∟ Subnet " + str(i))
        print("  ∟ Hosts: " + str(sp["allocated"]['hosts'][i]))
        print("  ∟ Address: " + str(sp["allocated"]['subnets'][i]))


def printrm(sp):
    print("[SYS-RMN] The unallocated range of addresses:")
    for u in sp["unallocated"]:
        print(" ∟ " + str(u.network_address) + "~" + str(u.broadcast_address))

def run():
  netok = False
  while not netok:
    try:
      origin = input("Enter the network range (E.g., 10.0.0.0/16): ")
      sp = {
        "allocated":{
            "hosts":[],
            "subnets":[]
        },
        "unallocated": [ipaddress.ip_network(origin)]
      }
      netok = True
    except:
      print("[ERR] Please insert the network range in the format.")

  while (True):
    cmd = input("\n[SYS] use command a(ppend)/p(rint)/q(quit)/r(emaining): ")
    if cmd == "q":
      return
    elif cmd == "a":
      try:
        inp = int(input("[SYS-APP] To allocate a new subnet, enter hosts number: "))
        if inp > 0:
          x,y = allocate(sp, inp)
        else:
          print("[SYS-APP] Please input a number larger than 0.")
      except:
        print("[ERR] Please enter an integer.")

    elif cmd == "p":
      printsp(sp)
    elif cmd == "r":
      printrm(sp)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    run()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
