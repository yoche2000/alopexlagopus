// Student name: 
// Student ID  : 

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Student 
{
    private String name;
    private String sid;
    private final ArrayList<Grade> gradeList;
    
    private static NumberFormat nf = NumberFormat.getInstance();
    
    public Student(String filename) throws IOException
    {
        Scanner sc = new Scanner(new File(filename));
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
        sc.close();
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
    
    public double getCGPA()
    {
        double gpa = 0;
        int totalCredit = 0;
        
        for (int i = 0; i < gradeList.size(); i++)
        {
            Grade g = gradeList.get(i);
            char firstLetter = g.getGrade().charAt(0); // first letter of grade

            boolean include = g.getMode() != 'N' && firstLetter != 'I' &&
                              firstLetter != 'P' && firstLetter != 'X';
            
            if (firstLetter == 'D' || firstLetter == 'F')            
                for (int j = i+1; include && j < gradeList.size(); j++)
                    if (gradeList.get(j).getCourseCode().equals(g.getCourseCode()))
                        include = false;
            
            if (include)
            {
                gpa += g.getCredit() * Grade.gradePoint(g.getGrade());
                totalCredit += g.getCredit();                               
            }   
        } 
        if (totalCredit > 0)
            gpa /= totalCredit;
        
        return gpa;
    }
}
