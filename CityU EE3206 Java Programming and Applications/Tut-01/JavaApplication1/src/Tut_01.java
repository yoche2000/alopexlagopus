// Student name: Lee Yo-Che
// Student ID  : 55348947

/*
	Submission deadline: Friday, 11 Sept 2020, 11 am

	Upload your .java file to Canvas.

	Put down your name and student ID at the top of your program file.
	10% marks will be deducted if your program file does not contain your name and ID.

	Submitted program that cannot be compiled (with syntax errors) 
	will receive no more than 30% marks.

	Late submissions or submissions by other means are not accepted.
*/

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Tut_01 
{                       
    // No need to modify the main method
    public static void main(String[] args) throws IOException
    {
        String filename1 = "T01-data.txt";
        String filename2 = "patterns.txt";
        StringBuilder text = new StringBuilder();
        
        // Read test data from file to a StringBuilder
        try (Scanner sc1 = new Scanner(new File(filename1)))
        {
            while (sc1.hasNextLine())
                text.append(sc1.nextLine()).append('\n');
        
            System.out.println(text);
        }
        
        // Read test patterns from file
        try (Scanner sc2 = new Scanner(new File(filename2)))
        {
            String number = sc2.nextLine();
            int n = Integer.parseInt(number);
            for (int i = 0; i < n; i++)
            {
                String pattern = sc2.nextLine();
                char[] p = pattern.toCharArray();  // alphanumeric char in lowercase

                System.out.println("Number of words that contain chars " + Arrays.toString(p));      
                System.out.println("  count = " + countWord(text, p) + "\n");
            }
        }
    }
    

    // ---------------------------- Method to be implemented by student

    private static int countWord(CharSequence text, char[] p)
    {
        // Count the number of words in text that contain chars in p[] (case-insensitive).
        // p[] contains alphanumeric char in lowercase.
        // Do not modify the method interface.
        // Do not create other String object in your implementation.
        // No need to use other data sturctures, such as stack, queue, list.

        // p.length is the length of the char array p
        // To get the char at index i of text, use the method text.charAt(i)
        // Reference the Java API for static methods available in class Character
        int count = 0;
        int startindex = 0;     //marking the start of the substring(between spaces)
        int endindex = 0;       //marking the end of the substring
        
        text = text.toString().replace('\n', ' ');          //Replace '/n' to ' ' for convenience
        while(endindex <= text.length() && endindex >=0)
        {
            startindex = endindex;
            endindex = text.toString().indexOf(" ", startindex+1);
            
            int stf = startindex;
            if (text.charAt(startindex) == ' ')
                stf++;
            StringBuilder sample = new StringBuilder();
            
            if(endindex >=0)
                sample.append(text.toString().substring(stf, endindex));
            else
                sample.append(text.toString().substring(stf));  //exceptional condition when endindex==-1
            
            int flag = 1;
            for(int i=0; i<p.length; i++)
            {   
                if (sample.toString().toLowerCase().indexOf(String.valueOf(p).charAt(i)) == -1 )
                {
                    flag = 0;   //Flag==1 means an elements in p cannot be found
                    break;
                }
                else
                    sample.setCharAt(sample.toString().toLowerCase().indexOf(String.valueOf(p).charAt(i)), ' ');
            }
            if(flag == 1)  //Flag==1 means all the elements in p can be found
                count++;
        }
        return count;
    }
}