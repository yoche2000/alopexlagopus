# This is a code for PTT users to check on the petition results.

def get_petition_id_list(path):
    lst = []
    file1 = open(path, 'r', encoding="utf8")
    Lines = file1.readlines()
    for line in Lines:
        name = line.split(".", 1)[1]
        name = name.split(" ", 1)[0]
        lst.append(name)
    return(lst)

def get_user(ptt_api, name):
    try:
        user = ptt_api.get_user(name)
        post = user.legal_post
        login = user.login_time
        info = {"ID": name, "post": post, "login": login}
        return info
    except Exception as e:
        print(e)

def petition_filter(ptt_api, path, lim_post, lim_login):
    list = get_petition_id_list(path)
    q = []
    for name in list:
        info = get_user(ptt_bot, name)
        q.append(info)
    nq = []
    for entry in q:
        usr = entry
        if int(entry['post']) >= lim_post and int(entry['login']) >= lim_login:
            nq.append(usr)
    for entry in nq:
        print (entry)
    print("Applying filter post >=", lim_post, "and login >=", lim_login, ":")
    print("Out of the", len(list), "petitioners, there are", len(nq), "valid ones.")


if __name__ == '__main__':
    import sys
    from PyPtt import PTT

    ptt_bot = PTT.API(
        screen_long_timeout=15
    )



    id = ""			#Insert PTT ID
    pwn = ""			#Insert PTT password

    try:
        ptt_bot.login(id, pwd)
    except PTT.exceptions.LoginError:
        ptt_bot.log('登入失敗')
        sys.exit()
    except PTT.exceptions.WrongIDorPassword:
        ptt_bot.log('帳號密碼錯誤')
        sys.exit()
    except PTT.exceptions.LoginTooOften:
        ptt_bot.log('請稍等一下再登入')
        sys.exit()
    ptt_bot.log('登入成功')

    if ptt_bot.unregistered_user:
        print('未註冊使用者')
        if ptt_bot.process_picks != 0:
            print(f'註冊單處理順位 {ptt_bot.process_picks}')
    if ptt_bot.registered_user:
        print('已註冊使用者')
    # call ptt_bot other api
    print("Login Success")

    path = "petition_list.txt"
    petition_filter(ptt_bot, path, 200, 200)

    ptt_bot.logout()

# See PyCharm help at https://www.jetbrains.com/help/pycharm/
