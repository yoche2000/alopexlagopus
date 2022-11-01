
public class MortgageUtil 
{
    public static double getMonthlyPayment(double loanAmount, double annualInterestRate,
                                           int loanTenor)
    {
        double r = annualInterestRate / 12;
        int n = loanTenor * 12;
        double payment = loanAmount * r * Math.pow((1.0 + r), n);
        payment /= (Math.pow((1.0 + r), n) - 1.0);
        return payment;
    }
    
    public static double getOutstandingBalance(double loanAmount, double annualInterestRate,
                                               int loanTenor, int yearsOfPayment)
    {
        double r = annualInterestRate / 12;
        int n = loanTenor * 12;
        int m = yearsOfPayment * 12;
        double balance = loanAmount * (Math.pow(1.0 + r, n) - Math.pow(1.0 + r, m));
        balance /= (Math.pow(1.0 + r, n) - 1.0);
        return balance;
    }
    
}
