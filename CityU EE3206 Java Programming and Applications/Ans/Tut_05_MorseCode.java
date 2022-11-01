// Student name: 
// Student ID  : 

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MorseCode
{
    private ArrayList<String> codes; // May use ArrayList<Pair<Character, String>>
    private Node tree; 
    
    public MorseCode(String filename) throws IOException
    {       
        codes = new ArrayList();
        try (Scanner sc = new Scanner(new File(filename)))
        {
            while (sc.hasNextLine())
            {
                codes.add(sc.nextLine());
            }
        }
        tree = new Node(' ');
        buildTree();
    }
    
    public String encode(String text)
    {
        // Return a String of zero and one that represents the Morse code
        // of the input text.
        
        StringBuilder sb = new StringBuilder();
        boolean firstLetter = true;
        boolean isSpace = true;
        
        for (int i = 0; i < text.length(); i++)
        {
            char a = text.charAt(i);
            if (Character.isWhitespace(a))
                isSpace = true;
            else
            {
                if (!firstLetter)
                    if (isSpace)  // next word
                        sb.append("0000000");
                    else          // next letter in a word
                        sb.append("000");
                
                isSpace = firstLetter = false;
                int index = a - 'A';
                if (Character.isDigit(a))               
                    index = (a - '0' + 9) % 10 + 26;

                String mc = codes.get(index);
                putCode(sb, mc);
            }
        }
        return sb.toString();
    }
    
    public String decode(String mc)
    {
        // Decode the given Morse code
        // Return the decoded message as a String.
        
        StringBuilder sb = new StringBuilder();
        
        String[] words = mc.split("0000000"); // may use split("0000000+"), 7 or more '0' 
                                              // is regarded as inter-word delimiter
        for (int i = 0; i < words.length; i++)
        {
            String[] letters = words[i].split("000"); // may use split("000+"), 3 to 6 '0'
                                                      // regarded as inter-letter delimiter
            for (String ch : letters)
            {
                String[] u = ch.split("0");  // may use split("0+"), 1 or 2 '0'
                sb.append(decodeSymbol(u));  // regarded as inter-symbol delimiter
            }
            if (i < words.length - 1)
                sb.append(' ');
        }
        return sb.toString();
    } 
    
    private static void putCode(StringBuilder sb, String mc)
    {
        // mc is the Morse code in the format "Symbol dot-dash"
        for (int i = 2; i < mc.length(); i++)
        {
            if (mc.charAt(i) == '.')
                sb.append('1');
            else  // '-'
                sb.append("111");
            
            if (i < mc.length() - 1)
                sb.append('0');
        }
    }
    
    private void buildTree()
    {
        for (String s : codes)
        {           
            Node p = tree;
            for (int i = 2; i < s.length(); i++)
            {
                if (s.charAt(i) == '.')
                {
                    if (p.getLeft() == null)
                        p.extendLeft(' ');
                    
                    p = p.getLeft();
                }
                else
                {
                    if (p.getRight() == null)
                        p.extendRight(' ');
                    
                    p = p.getRight();
                }
            }
            p.setSymbol(s.charAt(0));
        }
    }
    
    private char decodeSymbol(String[] u)
    {
        Node p = tree;
        for (String s : u)
        {
            if (s.equals("1"))  // dot
                p = p.getLeft();
            else                // dash
                p = p.getRight();
        }
        return p.getSymbol();
    }
}

class Node
{
    private char symbol;
    private Node left, right;
    
    public Node(char c)
    {
        symbol = c;
        left = right = null;
    }
    
    public Node getLeft()
    {
        return left;
    }
    
    public Node getRight()
    {
        return right;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(char c)
    {
        symbol = c;
    }
    
    public void extendLeft(char c)
    {
        left = new Node(c);
    }
    
    public void extendRight(char c)
    {
        right = new Node(c);
    }
}