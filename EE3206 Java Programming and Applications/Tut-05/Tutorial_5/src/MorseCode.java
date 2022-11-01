// Student name: LEE Yo-Che
// Student ID  : 55348947

// Submission deadline: Friday, 23 Oct 2020, 11 am.

// Complete the implementation of class MorseCode and class Node.

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap; 
import java.util.Collection;

public class MorseCode
{
    HashMap<String, String> mtable = new HashMap<String, String>();
    HashMap<String, String> inversemtable = new HashMap<String, String>();
    
    public MorseCode(String filename) throws IOException
    {   
        try
        {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine())
            {
                String line = sc.nextLine();
                String[] kv = line.split(" ", 2);                                
                String inbin = kv[1].replace(".", "10").replace("-", "1110");
                inbin = inbin.substring(0,inbin.length()-1);
                //System.out.print(kv[0]+ " = " + kv[1] + "   \t" + inbin + "\n");
                mtable.put(kv[0], inbin); 
                inversemtable.put(inbin, kv[0]);                
            }
            mtable.put(" "," "); 
            inversemtable.put(" "," ");    
            sc.close();
            //System.out.println(mtable);
        }
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }

    }
    
    
    
    public String encode(String text)
    {
        String result = new String();
        for(int i=0; i<text.length(); i++)
        {
            String enchar = mtable.get(String.valueOf(text.charAt(i)));
            //System.out.println("<<< " + text.charAt(i) + " >>>\t" + enchar);
            result += enchar;
            if(i< (text.length()-1))
                result += "000";
        }
        //System.out.println(text + "=" + result);                
        return result;        
    }
    
    public String decode(String mc)
    {                
        String[] dstr = mc.replace("0000000", "000 000").split("000");
        
        String word = "";
        for(int i = 0; i < dstr.length; i++)                     
            word += inversemtable.get(dstr[i]);
        
        return word;  // dummy return statement, replace it by your own codes
    } 
       
    // Other supporting methods where appropriate.
}

// --------------------------------------------------------------------------------

// class to model a tree node to support decoding of Morse code
class Node
{
    private char symb;
    private Node left, right;
    
    public Node(char c)
    {
        symb = c;
        left = right = null;
    }
    
    // other methods

    /*  Hint: a method to create the left child of the implicit Node with symbol c.

    public void extendLeft(char c)
    {
        left = new Node(c);
    }

    */

}