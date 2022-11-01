// Student name: Lee Yo-Che
// Student ID  : 55348947

// Submission deadline: Friday, 13 Nov 2020, 11 am

// Implement the method search.
// Do not change other parts of the class.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

public class Student 
{
    private static ArrayList<Student> studentList = new ArrayList();
    // studentList is sorted by sid
    
    private String name;
    private Integer sid;
    private String major;
    
    public Student(String n, int id, String m)
    {
        name = n;
        sid = id;
        major = m;
    }
    
    public String getName()
    {
        return name;
    }
    
    public Integer getSid()
    {
        return sid;
    }
    
    public String getMajor()
    {
        return major;
    }
    
    public static void add(Student s)
    {
        studentList.add(s);
    }
    
    public static Optional<Student> search(List<Student> s_list, Integer id)
    {        
        for (Student target: s_list)
            if (target.getSid().equals(id))
                     return Optional.of(target);
        return Optional.empty();
    }
}
