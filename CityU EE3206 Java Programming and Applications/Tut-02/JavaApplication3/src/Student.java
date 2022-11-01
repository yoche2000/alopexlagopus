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
        double credit = 0.0;
        double weighted = 0.0;
        int ind = 0;
        for (Grade g : gradeList)
        {            
            if(g.getMode() == 'L')
            {
                double c = g.getCredit();                       //Credit
                double w = c * Grade.gradePoint(g.getGrade());  //Weighted Grade
                
                //Check if being retaken later
                boolean ifretake = false;
                String crsname = g.getCourseCode();             
                for(Grade newg : gradeList.subList(ind+1, gradeList.size()))   
                    if(crsname.equals(newg.getCourseCode()))                    
                        ifretake = true;                                   
                //Check if mode grade is I, P, IP, X, or being retaken, exclude from GPA count                
                if(g.getGrade().equals("I") || g.getGrade().equals("P") || g.getGrade().equals("IP") || g.getGrade().equals("X") || ifretake)                
                    c = w = 0;
                                              
                credit += c;
                weighted += w;
            } 
            else if (g.getMode() == 'P' && g.getGrade().equals("F") )            
                credit += g.getCredit();                         
            ind ++;        
        }
        return weighted/credit;
    }
}
