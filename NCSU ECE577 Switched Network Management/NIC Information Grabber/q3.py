import subprocess

def new_conn():
    host = input("Remote Host IP (connection will be done via ssh): ")
    usr = input("User Name: ")

    print("SSH connection will be initiated.\nPlease enter 'yes' when asked to allow fingerprint, then enter the password.\n")

    result = subprocess.Popen("ssh "+ usr + "@" + host + " ip addr", shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
    result = str(result[0]).split(": ")
    return result

def test_conn():

    result = subprocess.Popen(f"ssh ece577@127.0.0.1 ip addr", shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE).communicate()
    result = str(result[0]).split(": ")
    return result

def nicformat(nics):
    ns = []
    n = len(nics)

    for a in range(int((n-1)/2)):

        info = {}

        info['name'] = nics[a*2+1]
        data = nics[a*2+2]
        keys = ['mtu', 'qdisc', 'state', 'group','qlen']
        for key in keys:
            if key in data:
                info[key] =  data.split(key)[1].split()[0].split('\\n')[0]

        try:
            info['type'] = data.split('link/')[1].split()[0]
        except:
            pass

        try:
            info['MAC'] = data.split('link/')[1].split()[1]
        except:
            pass

        keys = ['crd', 'inet']

        for key in keys:
            if key in data:
                info[key] =  data.split(key)[1].split()[0]

        if "ether" in data:
            try:
                info['inet_brd'] = data.split('brd')[2].split()[0]
            except:
                pass

        keys = ['valid_lft', 'scope', 'inet6']
        for key in keys:
            if key in data:
                info[key] =  data.split(key)[1].split()[0]

        try:
            info['i6_scope'] = data.split('scope')[2].split()[0]
        except:
            pass
        try:
            info['i6_valid_lft'] = data.split('valid_lft')[2].split()[0]
        except:
            pass

        ##print(info)
        ns.append(info)
    return ns

def printnic(d):

    print(d['name'])
    print('='*20)
    for key, val in d.items():
        print(f'{key:12}: {val:12}')


def filter_print(nl):
    cat = input("Insert Filter Type (IP, MAC, TYPE), or 'q' to quit: ")
    if cat == 'IP':
        content = input("Insert IP: ")
        for nic in nl:
            if 'inet' in nic.keys():
                if content in nic['inet']:
                    printnic(nic)
    elif cat == 'MAC':
        content = input("Insert MAC: ")
        for nic in nl:
            if 'MAC' in nic.keys():
                if content in nic['MAC']:
                    printnic(nic)
    elif cat == "TYPE":
        content = input("Insert TYPE: ")
        for nic in nl:
            if 'type' in nic.keys():
                if content == nic['type']:
                    printnic(nic)


    print()

nics = new_conn()
print(nics)
if len(nics) == 1:
    quit()
nl = nicformat(nics)

for nic in nl:
    printnic(nic)
    print()

while True:
    cmd = input("Command Options q{u:it}, l(ist), f(ilter):")
    if cmd == 'q':
        exit()
    elif cmd == 'f':
        filter_print(nl)
    elif cmd == 'l':
        for nic in nl:
            printnic(nic)
            print()
    else:
        print("Please enter the correct command.")