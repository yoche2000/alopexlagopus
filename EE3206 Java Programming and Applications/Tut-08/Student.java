// Student name: 
// Student ID  : 

// Submission deadline: Friday, 13 Nov 2020, 11 am

// Implement the method search.
// Do not change other parts of the class.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

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
    
    public static Optional<Student> search(Integer id)
    {
        // Implement this method

    }
}
