public class Grade 
{
    private final String courseCode;
    private final String grade;
    private final int credit;
    private final char mode;
    private final String sem;

    
    public Grade(String c, String g, int cu, char m, String s)
    {
        courseCode = c;
        grade = g;
        credit = cu;
        mode = m;
        sem = s;
    }
    
    public String getCourseCode()
    {
        return courseCode;
    }
    
    public String getGrade()
    {
        return grade;
    }
    
    public String getSem()
    {
        return sem;
    }
    
    public char getMode()
    {
        return mode;
    }
    
    public int getCredit()
    {
        return credit;
    }
    
    @Override
    public String toString()
    {
        return courseCode + "\t" + grade + "\t" + credit + "\t" + mode + "\t" + sem;
    }
    
    public static double gradePoint(String letterGrade)
    {
        double gp = 0;
        
        if (letterGrade.charAt(0) >= 'A' && letterGrade.charAt(0) <= 'D')
        {
            gp = 'E' - letterGrade.charAt(0);
            if (letterGrade.length() > 1)
                if (letterGrade.charAt(1) == '+')
                    gp += 0.3;
                else if (letterGrade.charAt(1) == '-')
                    gp -= 0.3;            
        }
        return gp;
    }    
}
