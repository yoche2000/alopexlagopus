import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Tut_01 
{                       
    public static void main(String[] args) throws IOException
    {
        String filename1 = "T01-data.txt";
        String filename2 = "patterns.txt";
        StringBuilder text = new StringBuilder();
        
        try (Scanner sc1 = new Scanner(new File(filename1)))
        {
            while (sc1.hasNextLine())
                text.append(sc1.nextLine()).append('\n');
        
            System.out.println(text);
        }
        
        try (Scanner sc2 = new Scanner(new File(filename2)))
        {
            String number = sc2.nextLine();
            int n = Integer.parseInt(number);
            for (int i = 0; i < n; i++)
            {
                String pattern = sc2.nextLine();
                char[] p = pattern.toCharArray();  // alphanumeric chars in lowercase

                System.out.println("Number of words that contain chars " + Arrays.toString(p));      
                System.out.println("  count = " + countWord(text, p) + "\n");
            }
        }
    }
    
    private static int countWord(CharSequence text, char[] p)
    {
        // Count the number of words in text that contain chars in p[] (case-insensitive).
        // p[] contains alphanumeric chars in lowercase.
        // Do not modify the method interface.
        // Do not create other String objects in your implementation.
        
        int count = 0;
        int i = 0;  // index to reference char in text
        int m = 0;  // number of matched char found in current word
        
        while (i < text.length())
        {
            char c = text.charAt(i++);
            if (Character.isWhitespace(c))
                m = 0;
            else
            {
                c = Character.toLowerCase(c);
                for (int j = m; j < p.length; j++)
                {
                    if (c == p[j])  // swap p[j] with p[m], and increment m
                    {
                        p[j] = p[m];
                        p[m++] = c;                        
                        break;
                    }
                }
             
                if (m == p.length)  // increment count, consume current word
                {
                    count++;
                    while (i < text.length() && !Character.isWhitespace(text.charAt(i)))
                        i++;
                }
            }
        }
        return count;
    }
}
