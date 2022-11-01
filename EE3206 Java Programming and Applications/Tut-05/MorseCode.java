// Student name: 
// Student ID  : 

// Submission deadline: Friday, 23 Oct 2020, 11 am.

// Complete the implementation of class MorseCode and class Node.

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MorseCode
{
    // Define the required instance variables.
    
    public MorseCode(String filename) throws IOException
    {   
        // Read the code table from data file and set up the data structures.
        // You need not provide the exception hander in this method.

    }
    
    public String encode(String text)
    {
        // Return a String of '0' and '1' that represents the Morse code
        // of the input text.
        // Input text may contain uppercase letters, digits, and space char.
        
        
        // Your codes

        return "";  // dummy return statement, replace it by your own codes
    }
    
    public String decode(String mc)
    {
        // mc is a sequence of '0' and '1' that represents the Morse code of a message.
        // Return the decoded message as a String.
        
        
        // Your codes.
        // An efficient approach to do the decoding is to represent the code table by a binary tree.
        // If you use a brute-force approach in your answer, you will get lower marks.

        return "";  // dummy return statement, replace it by your own codes
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