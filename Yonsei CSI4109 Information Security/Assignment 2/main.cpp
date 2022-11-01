#include <bits/stdc++.h>
#include <iostream>
#include <string>
#include <fstream>
#include <map>
#include <unistd.h>

using namespace std;

int main(int argc, char** argv)
{

    //static int   orig_ngroups = -1;
    //static gid_t orig_gid = -1;
    //static uid_t orig_uid = -1;
    //static gid_t orig_groups[NGROUPS_MAX];
    
    //char uname[255];
    //FILE *name;
    //name = popen("whoami", "r");
    //fgets(uname, sizeof(uname), name);	
    //pclose(name);

    //cout << "username: " << uname << "You have entered " << argc
    //     << " arguments:" << "\n";
  
    //for (int i = 0; i < argc; ++i)
    //    cout << argv[i] << "\n";

    //Read mac.policy

    string tg = string(argv[2]);
    string uname = string(getenv("USER"));
    string level = "UNCLASSIFIED";		//default
	
    //cout << tg << endl;

    ifstream file_in("mac.policy");
    string str;
    while(getline(file_in, str))
    {
        if (str.find(uname) != std::string::npos )
	{
	    //cout<< "found " << str<< endl;
	    level = str;   
	    level.erase(str.find(uname) , uname.length()+1);	   
	    break;
	}
    }

    //check authentication
    //cout<< "Level -- " << level << endl;

    int level_int = 0;
    if (level == "TOP_SECRET")
        level_int = 3;
    else if (level ==  "SECRET")
        level_int = 2;
    else if (level == "CONFIDENTIAL")
        level_int = 1;
    else if (level == "UNCLASSIFIED")
        level_int = 0;
    

    int target_level = 4;
    if (tg == "top_secret.data")
        target_level = 3;
    else if (tg == "secret.data")
        target_level = 2;
    else if (tg == "confidential.data")
        target_level = 1;
    else if (tg == "unclassified.data")
        target_level = 0;
    


    string ln;
    if (level_int >= target_level)
    {

	uid_t ruid, euid, suid; /* Real, Effective, Saved user ID */
	gid_t rgid, egid, sgid; /* Real, Effective, Saved group ID */
	int uerr, gerr, fd;
	
        if (getresuid(&ruid, &euid, &suid) == -1) {
            cerr << "Cannot obtain user identity: \n";
            return EXIT_FAILURE;
        }
        if (getresgid(&rgid, &egid, &sgid) == -1) {
            cerr <<  "Cannot obtain group identity: \n";
            return EXIT_FAILURE;
        }
        if (ruid != (uid_t)0 && ruid < (uid_t)500) {
            cerr << "Invalid user.\n";
            return EXIT_FAILURE;
        }
        if (rgid != (gid_t)0 && rgid < (gid_t)500) {
            cerr << "Invalid group.\n";
            return EXIT_FAILURE;
        }

	/* Switch to target user. setuid bit handles this, but doing it again does no harm. */
	if (seteuid((uid_t)0) == -1) {
            cerr << "Insufficient user privileges.\n";
	    return EXIT_FAILURE;
	}

        /* Switch to target group. setgid bit handles this, but doing it again does no harm.
         * If TARGET_UID == 0, we need no setgid bit, as root has the privilege. */
        if (setegid((gid_t)0) == -1) {
            fprintf(stderr, "Insufficient group privileges.\n");
            return EXIT_FAILURE;
        }


	    /* ... privileged operations ... */
 
    /* Open the restricted file.
     * If 'filename' is specified by the calling user,
     * in command-line parameters or environment variables,
     * you may have handed them a way to read e.g. /etc/gpasswd.
     * Don't do that.
     * If you have to do that, a lot of additional checks are needed,
     * some before opening the file, and others after opening the file.
     * Even then it is risky (hard link races and such).
    */
        do {
            ifstream file(tg);
	    //cout << tg << endl;
            while(getline(file, ln))
                cout <<ln << endl;
        } while (fd == -1 && errno == EINTR);
        if (fd == -1) {
            cerr << "Cannot read " << tg <<": \n" << endl;
            return EXIT_FAILURE;
        }

    /* Drop privileges. */
        gerr = 0;
        if (setresgid(rgid, rgid, rgid) == -1) {
            gerr = errno;
            if (!gerr)
                gerr = EINVAL;
        }
        uerr = 0;
        if (setresuid(ruid, ruid, ruid) == -1) {
            uerr = errno;
            if (!uerr)
                uerr = EINVAL;
        }
        if (uerr || gerr) {
            if (uerr)
                cerr << "Cannot drop user privileges: " << uerr << endl;
            if (gerr)
                cerr << "Cannot drop group privileges: " << gerr << endl;
            return EXIT_FAILURE;
        }
 
    /* ... unprivileged operations ... */
	
	//std::ifstream file(tg, std::ios::binary | std::ios::ate);

	//while(getline(file, ln))
	//	cout << ln << endl;


    }
    else
	cout << "ACCESS DENIED" << endl;




    //while (getline(file_in, key) && getline(file_in, value)){
    //    cout << "Read: key=" << key << " value=" << value << endl;
    //    mac_map[key] = value;
    //}

    //Logging

    string logstr = string(argv[1]) + " " + string(argv[2]);
    string fname = string(getenv("USER"))+".log";

    FILE *file;

    if (file = fopen(fname.c_str(), "r")) {
        fclose(file);
	ofstream logfile(fname, std::ios_base::app | std::ios_base::out);
        logfile << logstr << endl;
    } else {
        ofstream logfile(fname);
	logfile << logstr << endl;

    }
    return 0;
}
