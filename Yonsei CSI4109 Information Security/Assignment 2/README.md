# secure_house
* A program to grant temporary privilige to users, using seteuid and setegid. 
* This is the second Assignment to the course "CSI4109 Introduction to Information Security", Yonsei University.

## Auther
LEE YO-CHE 2021840150
Mail: thomaslee7-c@my.cityu.edu.hk
Linkedin: https://www.linkedin.com/in/yo-che-thomas-lee-b1b478193/
github: https://github.com/yoche2000


## Prerequisites
C++: g++

## Environment
Linux, Ubuntu 18.04.5

## Components
- main.cpp, main source file
- mac.policy
- Makefile 
    try $make, to generate the executable mac with proper chmod setting
    try #make clean, to clean the logs and the executable mac
-test.sh, a script that checks the readability of the 4 data files.

## Description
Place the execulable along with 
   1. mac.policy, which contains the configuration of users and their acess control level.
*    2. 4 files, *top_secret.data* , secret.data , *confidential.data* ,and *unclassified.data*, with different access level.
The executable mac checks the access level of the calling user, based on mac.policy, and grant read access of the file if allowed.
mac also generates logs for different users to keep track of their record using mac.

## Testing
Running test.sh with different access level of usr "yoche2000"
https://i.imgur.com/EoGPFwr.jpg
https://i.imgur.com/rIUaZfK.jpg

## Final thoughts
* I asked for some hints on Friday, then I found that I was stucked at the setuid bit of the mac file has not been set.
* And the rest has been fine all along. This project clarified my logic on Linux access control and mac policy. 
