// Student name: Lee Yo-Che
// Student ID  : 55348947

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
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.*;
import java.util.*;


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
        List<Pair<Student, Double>> result = new ArrayList<Pair<Student, Double>>();
                
        
        Function<String, Triple<String, Integer, String>> mapper =
            line -> {
                String[] token = line.split("\\s");
                return new Triple(token[0],token[1], token[2]);
            };
        
        try (Stream<String> lines = Files.lines(Paths.get(filename))) 
        {
            List<Triple<String, Integer, String>> mapped = new ArrayList<Triple<String, Integer, String>>();
            mapped = lines.map(mapper)                             
                          .peek(x -> x.setThird(Double.toString(Course.gradePoint(x.getThird())*Course.search(x.getFirst()).get().getCredit())))
                          .peek(x -> x.setFirst(Integer.toString(Course.search(x.getFirst()).get().getCredit())))                                                  
                          .collect(Collectors.toList());
            
            Map<Integer, String> cntMap =
                mapped.stream()
                      .collect(Collectors.toMap(
                              Triple::getSecond,
                              Triple::getThird, 
                              (s1, s2) -> (Double.toString(Double.parseDouble(s1) + Double.parseDouble(s2))))); 
            
            Map<Integer, String> cntMap2 =
                mapped.stream()
                      .collect(Collectors.toMap(
                              Triple::getSecond,
                              Triple::getFirst,
                              (s1, s2) -> (Double.toString(Double.parseDouble(s1) + Double.parseDouble(s2))))); 
            
            //List<Pair<Student, Double>> lst = new ArrayList<Pair<Integer, Double>>(); 
            List<Pair<Student, Double>> tmp = new ArrayList<Pair<Student, Double>>();
            List<String> l1 = new ArrayList(cntMap2.keySet());                
            l1.forEach(e -> tmp.add( new Pair (
                            Student.search(Integer.parseInt(e)).get(),
                            Double.parseDouble(cntMap.get(e))/Double.parseDouble(cntMap2.get(e)))));
            
            tmp.sort((c1, c2) -> c2.getSecond().compareTo(c1.getSecond()));            
            result.add(tmp.stream().filter(e -> e.getFirst().getMajor().equals("INFE")).findFirst().get());
            result.add(tmp.stream().filter(e -> e.getFirst().getMajor().equals("CDE")).findFirst().get());
            result.add(tmp.stream().filter(e -> e.getFirst().getMajor().equals("ECE")).findFirst().get());
            result.sort((c1, c2) -> c1.getFirst().getSid().compareTo(c2.getFirst().getSid()));                               
                     
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }                
         
        // Format your program statements properly.
        // Align the 'dots' of the Stream pipeline vertically.
        
        return result;
    }
     
}
