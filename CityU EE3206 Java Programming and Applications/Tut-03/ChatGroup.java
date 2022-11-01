// Do not modify this class.
// No need to upload this file to Canvas.

import java.util.ArrayList;
import java.util.Collections;

public class ChatGroup 
{
    private final String groupName;
    private final ArrayList<User> members;  // Users in the list are ordered by tel
    
    public ChatGroup(String gName)
    {
        groupName = gName;
        members = new ArrayList();
    }

    public String getGroupName()
    {
        return groupName;
    }
   
    public User[] getMembers()
    {
        return members.toArray(new User[members.size()]);
    }
    
    /* -------------------------------------------
    public ArrayList<User> getMembers()
    {
        return members;  // Not a good design. Why ??
    }    
    ---------------------------------------------- */

    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Chat group : ").append(groupName).append('\n');
        for (User u : members)
             buffer.append(u).append('\n');
              
        return buffer.toString();
    }
   
    public User getUser(String tel)
    {      
        User key = new User("", tel);
        int k = Collections.binarySearch(members, key);
        if (k >= 0)
            return members.get(k);
        else
            return null;
    }

    public void addUser(User u)
    {       
        int k = Collections.binarySearch(members, u);
        if (k < 0)
            members.add(-(k+1), u); 
    }
    
    public void removeUser(User u)
    {       
        int k = Collections.binarySearch(members, u);
        if (k >= 0)
            members.remove(k);
    }
}