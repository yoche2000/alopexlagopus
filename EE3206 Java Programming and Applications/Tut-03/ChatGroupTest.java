// Student name: 
// Student ID  : 

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
    // You may define other supporting methods where appropriate.
    
    static List<User> getMutualFriends(List<ChatGroup> groupList, User u1, User u2)
    {
        // Implement this static method.
        // Users in the same chat group are considered to be friends.

        // Return a list of users (without duplicate) that are mutual friends 
        // of u1 and u2 derived from the list of chat groups.
        
        // groupList should not be modified

        List<User> result = new ArrayList();


        // Your codes

        
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
