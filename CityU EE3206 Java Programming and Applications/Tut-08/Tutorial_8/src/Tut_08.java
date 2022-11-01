// Student name: Lee Yo-Che
// Student ID  : 55348947

// Submission deadline: Friday, 13 Nov 2020, 11 am

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
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
        //grades.forEach(p -> System.out.println(p.getFirst()+"\t"+p.getSecond()+"\t"+p.getThird()));
        
        
        // Your codes to read a data file of courses into Course.courseList
        ArrayList<Course> c_list = new ArrayList();
        try (Scanner sc2 = new Scanner(new File("courses.txt")))
        {
            while (sc2.hasNextLine())
            {
                String line = sc2.nextLine();
                String[] token = line.split("\\s");                
                List<String> list = Arrays.asList(token); 
                String c_title = list.subList(2, list.size()).toString().replace(",", "").replace("[", "").replace("]", "");                
                c_list.add(new Course(token[0], c_title, Integer.parseInt(token[1])));
            }
        }        
        //c_list.forEach(p -> System.out.println(p.getCourseCode()+p.getCredit()+p.getCourseTitle()));        

        
        // Your codes to read a data file of students into Student.studentList
        ArrayList<Student> s_list = new ArrayList();
        try (Scanner sc3 = new Scanner(new File("students.txt")))
        {
            while (sc3.hasNextLine())
            {
                String line = sc3.nextLine();
                String[] token = line.split("\\s");                
                List<String> list = Arrays.asList(token); 
                String s_name = list.subList(2, list.size()).toString().replace(",", "").replace("[", "").replace("]", "");                
                s_list.add(new Student(s_name, Integer.parseInt(token[0]), token[1]));                                
            }
        }
        
        // Find the students with the highest GPA in each major
        // Implement the computation using Functional Programming approachs
        
        List<Pair<Student, Double>> result = bestStudents(grades, s_list, c_list);
        
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        for (Pair<Student, Double> item : result)
        {
            Student sdt = item.getFirst();
            System.out.println(sdt.getMajor() + ", " + sdt.getSid() + ", " + sdt.getName() +
                               ", GPA = " + nf.format(item.getSecond()));
        }        
    }
     
    static List<Pair<Student, Double>> bestStudents(List<Triple<String, Integer, String>> grades, List<Student> s_list, List<Course> c_list)
    {
        
        List<Pair<Student, Double>> result = new ArrayList<Pair<Student, Double>>();
        
        
        List<Integer> i_list = FunctionUtil.map(grades, p -> p.getSecond());            
        Set<Integer> numbersSet = new HashSet<Integer>(i_list);
        List<Integer> ilist2 = new ArrayList<>(numbersSet);                             //ID List                                    
        
        BiConsumer<List<Pair<Integer, Double>>, Integer > bicom =
                    (resultlst, i) -> {                                                     
                            ArrayList<Double> ia = new ArrayList<Double>();
                            ArrayList<Integer> cu = new ArrayList<Integer>();                            
                            List<Triple<String, Integer, String>> lstia = FunctionUtil.filter(grades, p -> p.getSecond().equals(i));                            
                            FunctionUtil.forEach(lstia, p1 -> ia.add(Course.gradePoint(p1.getThird()) * Course.search(c_list, p1.getFirst()).get().getCredit()));
                            FunctionUtil.forEach(lstia, p1 -> cu.add(Course.search(c_list, p1.getFirst()).get().getCredit()));        

                            Double iasum = ia.stream().collect(Collectors.summingDouble(Double::doubleValue));
                            Integer cusum = cu.stream().collect(Collectors.summingInt(Integer::intValue));
                            
                            resultlst.add(new Pair(i, iasum/cusum));
                    };                        
                       
        List<Pair<Integer, Double>> fin = FunctionUtil.transform(ilist2, bicom);  
        
        
        BiConsumer<List<Pair<Student, Double>>, Pair<Integer, Double> > bicom2 =
                    (resultlst, i) -> {                         
                            Student tmp = Student.search(s_list, i.getFirst()).get();
                            resultlst.add(new Pair(tmp, i.getSecond()));
                    };
        
        List<Pair<Student, Double>> fin2 = FunctionUtil.transform(fin, bicom2);        
        fin2.sort((r1, r2) -> (r2.getSecond()).compareTo(r1.getSecond()));        
        
        result.add(FunctionUtil.filter(fin2, p -> p.getFirst().getMajor().equals("CDE")).get(0));
        result.add(FunctionUtil.filter(fin2, p -> p.getFirst().getMajor().equals("ECE")).get(0));
        result.add(FunctionUtil.filter(fin2, p -> p.getFirst().getMajor().equals("INFE")).get(0));
        
        return result;
    }
     
}
