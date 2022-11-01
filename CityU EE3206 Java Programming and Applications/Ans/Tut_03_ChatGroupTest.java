import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ChatGroupTest 
{        
    static List<User> getFriends(List<ChatGroup> groups, User u)
    {
        ArrayList<User> temp = new ArrayList();

        for (ChatGroup g : groups)        
            if (g.getUser(u.getTel()) != null)
                temp.addAll(Arrays.asList(g.getMembers()));
        
        Collections.sort(temp);
        ArrayList<User> result = new ArrayList();

        User lastItem = new User("", "");
        for (User t : temp)
           if (t.compareTo(u) != 0 && t.compareTo(lastItem) != 0)
           {   // or you can compare the tel number directly 
               result.add(t);
               lastItem = t;
           }
           // --- Alternative design of the if-statement ---
           // if (result.isEmpty() ||
           //     t.compareTo(result.get(result.size()-1)) != 0)
           //    result.add(t);
        return result;       
    }    
    
    static List<User> getMutualFriends(List<ChatGroup> groupList, User u1, User u2)
    {
        // Implement this static method.
        // Users in the same chat group are considered to be friends.

        // Return a list of users (without duplicate) that are friends 
        // of u1 and u2 derived from the list of chat groups.
        
        List<User> list1 = getFriends(groupList, u1);  // friends of u1, sorted by tel
        List<User> list2 = getFriends(groupList, u2);  // friends of u2, sorted by tel
        List<User> result = new ArrayList();
        
        //  Linear time complexity for ArrayList and Vector.
        //  Not efficient if the List object is a LinkedList.
        //  list.get(i) performs a sequential traversal of the list from the front.
        /*  
        int i = 0, j = 0;
        while (i < list1.size() && j < list2.size())
        {
            int c = list1.get(i).compareTo(list2.get(j));
            if (c == 0)
            {
                result.add(list1.get(i));
                i++;
                j++;
            }
            else if (c < 0)
                i++;
            else
                j++;
        }
        */
        
        //  Use an Iterator to control looping with linear time complexity
        //  for ArrayList, Vector and LinkedList.
        Iterator<User> i1 = list1.iterator();
        Iterator<User> i2 = list2.iterator();
        User e1 = i1.hasNext() ? i1.next() : null;
        User e2 = i2.hasNext() ? i2.next() : null;
        
        while (e1 != null && e2 != null)
        {
            int c = e1.compareTo(e2);
            if (c == 0)
            {
                result.add(e1);
                e1 = i1.hasNext() ? i1.next() : null;
                e2 = i2.hasNext() ? i2.next() : null;
            }
            else if (c < 0)
                e1 = i1.hasNext() ? i1.next() : null;
            else
                e2 = i2.hasNext() ? i2.next() : null;
        }
        return result;
    }
    
    // -------------------------------------- methods given to you
    
    public static void main(String[] args) throws FileNotFoundException
    {
        ArrayList<ChatGroup> groupList = new ArrayList();
        try (Scanner sc = new Scanner(new File("chatGroups-data.txt")))
        {
            int numGroup = sc.nextInt();  // Compiler warning - unsafe and unchecked operation
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
