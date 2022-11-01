// Student name: 
// Student ID  : 

// Submission deadline: Friday, 20 Nov 2020, 11 am
// Implement the method bestStudents() using the Stream API.

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class Tut_09 
{
    public static void main(String[] args) throws IOException
    {       
        try (Scanner sc2 = new Scanner(new File("students.txt")))
        {
            while (sc2.hasNextLine())
            {
                String line = sc2.nextLine();
                String[] token = line.split("\\s");
                String name = token[2];
                for (int i = 3; i < token.length; i++)
                    name = name + " " + token[i];

                Student.add(new Student(name, Integer.parseInt(token[0]), token[1]));
            }
        }

        try (Scanner sc3 = new Scanner(new File("courses.txt")))
        {
            while (sc3.hasNextLine())
            {
                String line = sc3.nextLine();
                String[] token = line.split("\\s");
                String title = token[2];
                for (int i = 3; i < token.length; i++)
                    title = title + " " + token[i];
            
                Course.add(new Course(token[0], title, Integer.parseInt(token[1])));
            }
        }
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);

        System.out.println("Students with highest GPA in each major");
        List<Pair<Student, Double>> result = bestStudents("grades.txt");
        for (Pair<Student, Double> item : result)
        {
            Student s = item.getFirst();
            System.out.println(s.getMajor() + ", " + s.getSid() + ", " + s.getName() +
                               ", GPA = " + nf.format(item.getSecond()));
        }     
    }
     
    static List<Pair<Student, Double>> bestStudents(String filename) throws IOException
    {
        // Find the student with the highest GPA in each major.
        // Implement this method using Stream API.
        // Do not use any explicit loop in your design.

        // Format your program statements properly.
        // Align the 'dots' of the Stream pipeline vertically.

    }
     
}
