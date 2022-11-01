// Student name: LEE Yo-Che
// Student ID  : 55348947

// Submission deadline: Friday, 25 Sept 2020, 11 am

// Implement the method getMutualFriends().
// Lookup class Arrays and ArrayList, and interface List and Collections.sort() in java.util

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ChatGroupTest 
{                
    static List<User> getMutualFriends(List<ChatGroup> groupList, User u1, User u2)
    {                 
        // Implement this static method.
        // Users in the same chat group are considered to be friends.

        // Return a list of users (without duplicate) that are mutual friends 
        // of u1 and u2 derived from the list of chat groups.          
        List<User> listU1 = new ArrayList();
        List<User> listU2 = new ArrayList();
        List<User> result = new ArrayList();
        
        for(ChatGroup grp : groupList)
        {
            boolean u1ex = false;
            boolean u2ex = false;           
            User[] ulist = grp.getMembers();
            for(User usr: ulist)
            {                
                if(String.valueOf(usr.getTel()).matches(String.valueOf(u1.getTel())))                                
                    u1ex = true;                               
                if(String.valueOf(usr.getTel()).matches(String.valueOf(u2.getTel())))                
                    u2ex = true;                                               
            }                                    

            //Catagorise entries in different Arraylist dased on the two flags 
            if(u1ex && u2ex)            
                for(User usr: ulist)
                    result.add(usr);    //Case of every users in the qrp is a Mutual Friends of U1 and U2
            else if(u1ex)
                for(User usr: ulist)
                    listU1.add(usr);    //Case of every users in the qrp is a Friends of U1    
            else if(u2ex)
                for(User usr: ulist)
                    listU2.add(usr);    //Case of every users in the qrp is a Friends of U2
        }
        
        //Filter out the users that are friend of both U1 and U2 
        for(User usr: listU1)        
            for(User usr2: listU2)
                if(String.valueOf(usr.getTel()).matches(String.valueOf(usr2.getTel())))
                {                    
                    result.add(usr);
                    break;
                }         
        
        for(int i = 0; i < result.size(); i++)
        {    
            for(int j = 0; j < i; j ++)                
                if(String.valueOf(result.get(i).getTel()).matches(String.valueOf(result.get(j).getTel())))
                    result.remove(j);                                        
            if(String.valueOf(result.get(i).getTel()).matches(String.valueOf(u1.getTel())))
                    result.remove(i);    
            else if(String.valueOf(result.get(i).getTel()).matches(String.valueOf(u2.getTel())))
                    result.remove(i);    
        }        
        Collections.sort(result);                  

        
        return result;
    }
    
    // -------------------------------------- methods given to you
    
    public static void main(String[] args) throws FileNotFoundException
    {
        ArrayList<ChatGroup> groupList = new ArrayList();
        try (Scanner sc = new Scanner(new File("chatGroups-data.txt")))
        {
            int numGroup = sc.nextInt();
            for (int g = 0; g < numGroup; g++)
            {
                String groupName = sc.next();
                int k = sc.nextInt();
            
                ChatGroup group = new ChatGroup(groupName);
                for (int i = 0; i < k; i++)
                {
                    String tel = sc.next();
                    String name = sc.nextLine().trim();
                    group.addUser(new User(name, tel));
                }
                groupList.add(group);
            }
        }
        
        try (Scanner sc = new Scanner(new File("mutualFriend-tests.txt")))
        {
            int tests = sc.nextInt();
            for (int i = 0; i < tests; i++)
            {
                System.out.println("-----------------------------------------------");
                String tel1 = sc.next();
                String name1 = sc.nextLine().trim();
                String tel2 = sc.next();
                String name2 = sc.nextLine().trim();
                
                User u1 = new User(name1, tel1);     
                User u2 = new User(name2, tel2);
        
                List<User> friends = getMutualFriends(groupList, u1, u2);
                System.out.println("Mutual friends of " + u1.getName() + " and " + u2.getName());
                System.out.println("Number of mutual friends = " + friends.size()); 

                friends.forEach(System.out::println);  // Functional operation
                // Equivalent to
                // for (User u : friends)
                //     System.out.println(u);
                //
            }
        }
    }
}
