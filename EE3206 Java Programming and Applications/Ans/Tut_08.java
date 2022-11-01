// Student name: 
// Student ID  : 

// Submission deadline: Friday, 13 Nov 2020, 11 am

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
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
        
        // Your codes to read a data file (courses.txt) of courses into Course.courseList.
        // Each line in the file contains the course code, credit units, and course title.
        // The data file is sorted by course code.

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

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        
        // Find the students with the highest GPA in each major
        // Implement the computation using Functional Programming approach   
     
        List<Pair<Student, Double>> result = bestStudents(grades);
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
        
        // Transform input list of Triple<courseCode, sid, grade> to
        // output list of Triple<sid, total_gradePoint, credits>

        BiConsumer<List<Triple<Integer, Double, Integer>>, Triple<String, Integer, String>>
            action1 = (summaryList, t) -> {
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
        
        List<Triple<Integer, Double, Integer>> temp = FunctionUtil.transform(grades, action1);
        
        // Map Triple<sid, total_gradePoint, credits> to Pair<Student, gpa>
        Function<Triple<Integer, Double, Integer>, Pair<Student, Double>> mapper =
            t -> {
                double gpa = t.getThird() > 0 ? t.getSecond() / t.getThird() : 0;
                Student s = Student.search(t.getFirst()).get();
                return new Pair(s, gpa);
            };
        
        List<Pair<Student, Double>> temp2 = FunctionUtil.map(temp, mapper);
        
        BiConsumer<List<Pair<Student, Double>>, Pair<Student, Double>>
            action2 = (r, p) -> {
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
        
        return FunctionUtil.transform(temp2, action2);
    }
}
