// No need to submit this file
// No need to upload this file to Canvas

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tut_02 
{
    public static void main(String[] args) throws IOException
    {
        String file_s1 = "student1.txt";  
        String file_s2 = "student2.txt"; 
        String file_s3 = "student3.txt";
        
        processStudent(file_s1);
        processStudent(file_s2);  
        processStudent(file_s3);
    }
    
    private static void processStudent(String filename) throws IOException
    {
        Student st = new Student(filename);
        System.out.println("---------------------------------------------");
        st.print();
              
    }    
}
