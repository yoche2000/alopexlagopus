// Student name: 
// Student ID  : 

// Implement the method gradePoint() in this class.

// Submission deadline: Friday, 18 Sept 2020, 11 am
// Upload 2 files, Grade.java and Student.java to Canvas

public class Grade 
{
    private final String courseCode;
    private String grade;             // letter grade, A+, A, A-, B+, B, etc.
    private final int credit;         // number of credits
    private final char mode;          // grade mode, L = letter grade, P = pass/fail
                                      //             N = not counted in GPA calculation
    private final String sem;         // semester, e.g. 2019-09

    
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
        return courseCode + "\t" + grade + "\t" + credit + "\t" + mode + "\t" + sem + "\t" + gradePoint(grade);
    }
    
    public static double gradePoint(String letterGrade)
    {
        double pt;
        switch(letterGrade)
        {
            case "A+":
                pt = 4.3;
                break;
            case "A":
                pt = 4.0;
                break;
            case "A-":
                pt = 3.7;
                break;
            case "B+":
                pt = 3.3;
                break;
            case "B":
                pt = 3.0;
                break;
            case "B-":
                pt = 2.7;
                break;
            case "C+":    
                pt = 2.3;
                break;
            case "C":
                pt = 2.0;
                break;
            case "C-":
                pt = 1.7;
                break;
            case "D":
                pt = 1.0;
                break;
            case "F":
                pt = 0.0;
                break;
            default:
                pt = 0;
        }     
        return pt;               
    }    
}
