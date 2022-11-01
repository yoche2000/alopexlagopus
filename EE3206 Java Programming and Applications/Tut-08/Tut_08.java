// Student name: 
// Student ID  : 

// Submission deadline: Friday, 13 Nov 2020, 11 am

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class Tut_08 
{
    public static void main(String[] args) throws IOException
    {
        // Read a data file of grades of a semester
        ArrayList<Triple<String, Integer, String>> grades = new ArrayList();
        try (Scanner sc1 = new Scanner(new File("grades.txt")))
        {
            while (sc1.hasNextLine())
            {
                String line = sc1.nextLine();
                String[] token = line.split("\\s");
                grades.add(new Triple(token[0], Integer.parseInt(token[1]), token[2]));
            }
        }
        
        // Your codes to read a data file of courses into Course.courseList
        // Your codes to read a data file of students into Student.studentList
                
        // Find the students with the highest GPA in each major
        // Implement the computation using Functional Programming approach

        List<Pair<Student, Double>> result = bestStudents(grades);
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        for (Pair<Student, Double> item : result)
        {
            Student s = item.getFirst();
            System.out.println(s.getMajor() + ", " + s.getSid() + ", " + s.getName() +
                               ", GPA = " + nf.format(item.getSecond()));
        }        
    }
     
    static List<Pair<Student, Double>> bestStudents(List<Triple<String, Integer, String>> grades)
    {
        // Input list of Triple<courseCode, sid, grade>, already sorted by sid
        // All course grades in the input list are between A+ to F.
        // That is, all course grades are counted in the GPA calculation.

        // You may assume that the data in the data files are consistent.
        // That is, all student IDs, and course codes are valid.

        // Implement the computation using Functional Programming approach.
        
        
        
    }
     
}
