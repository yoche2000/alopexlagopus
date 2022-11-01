// Do not modify this class.
// This class does not implement Comparable.
// No need to upload this file.

public class CardTransaction
{
    private final String card;   // card number
    private final String date;   // yyyy-mm-dd
    private final double amount;
    
    public CardTransaction(String c, String d, double a)
    {
        card = c;
        date = d;
        amount = a;
    }

    public String getCard()
    {
        return card;
    }
    
    public String getDate()
    {
        return date;
    }
    
    public double getAmount()
    {
        return amount;
    }
    
    @Override
    public String toString()
    {
        return date + "\t" + card + "\t" + amount;
    }
}

