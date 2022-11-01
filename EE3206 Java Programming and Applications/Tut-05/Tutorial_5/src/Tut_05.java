// No need to modify this class
// No need to upload this file to Canvas.

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Tut_05 
{
    public static void main(String[] args) throws IOException
    {
        String file = "MorseCodeTable.txt";
        
        MorseCode coder = new MorseCode(file);       
        
        String text1 = "SOS";
        String morsecode1 = coder.encode(text1);
        //System.out.println(text1);
        System.out.println("Morse code for \"" + text1 + "\"");
        System.out.println(morsecode1);
        System.out.println("Decoded message : " + coder.decode(morsecode1));
        System.out.println();
        
        String text2 = "JAVA JDK 08";
        String morsecode2 = coder.encode(text2);
        //System.out.println(text2);
        System.out.println("Morse code for \"" + text2 + "\"");
        System.out.println(morsecode2);
        System.out.println("Decoded message : " + coder.decode(morsecode2));
        System.out.println();
        
        
        String filename = "Tut-05.txt";
        try (Scanner sc = new Scanner(new File(filename)))
        {
            String line = sc.nextLine();
            System.out.println("-- Test decode method --");
            System.out.println("Decoded message : " + coder.decode(line));
        }
    }
}
