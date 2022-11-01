## PyPTT code for PTT currency transactioon automation
## written by yoche2000@ptt.cc

def giv_p(ptt_api, name, val):
    try:
        ptt_api.give_money(name, val)
        print("Successful Transaction  ", str(name), str(val)+"P.")
    except Exception as e:
        print(e)


def get_user(ptt_api, name):
    try:
        user = ptt_api.get_user(name)
        post = user.legal_post
        login = user.login_time
        info = {"ID": name, "post": post, "login": login}
        return info
    except Exception as e:
        print(e)



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


    path = "transaction_list.txt"
    file = open(path, 'r', encoding="utf8")
    Lines = file.readlines()
    for line in Lines:
        id = line.split(":")[0]
        amount = int(line.split(":")[1])
        try:
            giv_p(ptt_bot, id, amount)
            print("Transaction to " + id + ": " + str(amount))
        except:
            print("Fail transaction to " + id + ": " + str(amount))
            pass

    ptt_bot.logout()
