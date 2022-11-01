// Student name: Lee Yo-Che
// Student ID  : 55348947

// Implement the method getCGPA() in this class.

// Submission deadline: Friday, 18 Sept 2020, 11 am
// Upload 2 files, Grade.java and Student.java to Canvas

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Student 
{
    private String name;
    private String sid;
    private final ArrayList<Grade> gradeList;  // list of Grade
    
    private static NumberFormat nf = NumberFormat.getInstance();
    
    public Student(String filename) throws IOException
    {
        try (Scanner sc = new Scanner(new File(filename)))
        {
            String line = sc.nextLine();
            String[] tokens = line.split("\\s+");
            name = tokens[0];
            for (int i = 1; i < tokens.length - 1; i++)
                name = name + " " + tokens[i];
            sid = tokens[tokens.length-1];
        
            gradeList = new ArrayList();
            while (sc.hasNextLine())
            {
                line = sc.nextLine();
                tokens = line.split("\\s+");
                Grade g = new Grade(tokens[0], tokens[1], Integer.parseInt(tokens[2]),
                                tokens[3].charAt(0), tokens[4]);
                gradeList.add(g);
            }
        }
    }
    
    public void print()
    {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + sid);
        System.out.println("Grades:");
        for (Grade g : gradeList)
            System.out.println(g);
        
        nf.setMaximumFractionDigits(2);
        System.out.println("CGPA = " + nf.format(getCGPA()));
    }
    

    // ------------------------ method to be implemented by student

    public double getCGPA()
    {
        // Return the CGPA of the student.

        // A student can retake a course with F or D grade.
        // Only the last attempt of a course is counted in the CGPA calculation.

        // Courses with I grade, IP grade, P grade or X grade are not counted in CGPA calculation.
        // Courses with grade mode equals to N are not counted in CGPA calculation.
        
        return 0;
    }
}
