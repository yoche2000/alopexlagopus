// Student name: 
// Student ID  : 

// Submission deadline: Friday, ?? Nov 2020, 11 am

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
        // Item t is a Triple<courseCode, sid, grade>
        // Result list is a list of Triple<sid, total_gradePoint, credits>
        BiConsumer<ArrayList<Triple<Integer, Double, Integer>>, Triple<String, Integer, String>>
            accumulator1 = (summaryList, t) -> {
                double gp = Course.gradePoint(t.getThird());
                Optional<Course> r = Course.search(t.getFirst());
                int credit = r.get().getCredit();
                           
                if (summaryList.isEmpty())
                    summaryList.add(new Triple(t.getSecond(), gp * credit, credit));
                else
                {
                    Triple<Integer, Double, Integer> item = summaryList.get(summaryList.size()-1);
                    if (item.getFirst().equals(t.getSecond()))
                    {
                        item.setSecond(item.getSecond() + gp * credit);
                        item.setThird(item.getThird() + credit);
                    }
                    else
                        summaryList.add(new Triple(t.getSecond(), gp * credit, credit));
                }
            };
        
        // Map Triple<sid, total_gradePoint, credits> to Pair<Student, GPA>
        Function<Triple<Integer, Double, Integer>, Pair<Student, Double>> mapper =
            t -> {
                double gpa = t.getThird() > 0 ? t.getSecond() / t.getThird() : 0;
                Student s = Student.search(t.getFirst()).get();
                return new Pair(s, gpa);
            };
        
        // Accumulator to select the student with highest GPA in each major.
        BiConsumer<ArrayList<Pair<Student, Double>>, Pair<Student, Double>>
            accumulator2 = (r, p) -> {
                int k = Collections.binarySearch(r, p, 
                            (t1, t2) -> t1.getFirst().getMajor().compareTo(t2.getFirst().getMajor()));
                if (k >= 0)
                {
                    Pair<Student, Double> item = r.get(k);
                    if (item.getSecond() < p.getSecond())                        
                        r.set(k, p);
                }
                else
                    r.add(-(k+1), p);
            };
                
        Path filepath = Paths.get(filename);
        try (Stream<String> lines = Files.lines(filepath))
        {
            return
            lines.map(line -> {   
                        String[] token = line.split("\\s");
                        return new Triple<String, Integer, String>(token[0], Integer.parseInt(token[1]), token[2]); 
                     })
                 .collect(ArrayList::new,
                          accumulator1,
                          ArrayList::addAll)
                 .stream()
                 .map(mapper)
                 .collect(ArrayList::new,
                          accumulator2,
                          ArrayList::addAll);
        }
    }
}
