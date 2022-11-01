import java.util.ArrayList;
import java.util.Collections;
import static java.util.Comparator.comparing;
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
        Student key = new Student("", id, "");
        int k = Collections.binarySearch(studentList, key,
                                         comparing(Student::getSid));
        if (k >= 0)
            return Optional.of(studentList.get(k));
        else
            return Optional.empty();
    }
}
