// Student name: Lee Yo-Che
// Student ID  : 55348947

// Submission deadline: Friday, 6 Nov 2020, 11 am.

/*
	A data file with credit transaction records is given to you.
	Assume the card company has launched a reward program.
	The reward program is as follows:
		- Transaction with amount greater than or equal to minExp is a qualified transaction.
		- Total amount of qualified transactions in a month is greater than or equal to monthlyExp.
		- For each qualified transaction, the card will receive a fixed rebate.

	For example, the minExp for qualified transaction is $500, the monthlyExp is $2500,
	and rebate for each qualified transaction is $10.

	A card has 3 qualified transactions, say $1000, $600, $1500 in a given month.
	Total amount of the qualified transactions is $3100.
	This card will earn 3 x $10 = $30 reward for that month.

	Implement the method getReward() using the functional programming approach.
	The class FunctionUtil is given to you.
	That is, you should not use any explicit looping in the method getReward().
	You can use the sort() method, and the methods in FunctionUtil.
	
	You can use the class TempRec or the generic class Triple in your design.
	The class TempRec is to be defined at the bottom of this file.

        // Input list is initially sorted by date.
        // Implement this method using the Functional Programming approach.
        // Do not use any explicit loop in the implementation of this method.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
//import java.util.Comparator;
//import static java.util.Comparator.comparing;
//import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
//import java.util.function.Predicate;
//import java.util.function.Consumer;
import java.util.stream.IntStream;
        
public class CardTransTest2 
{            
    static List<Pair<String, Double>> getReward(List<CardTransaction> list, 
                                                double monthlyExp, double minExp,
                                                double rebate)
    {                                              
        List<Integer> intlist = new ArrayList<Integer>();
        IntStream.rangeClosed(1, 12).forEach(i -> intlist.add(i));          //For Iteration through the months 
        
        BiConsumer<List<Pair<String, Double>>, Integer > loopact =
            (l, i) -> {
                //
                String str;
                if(i < 10)
                    str = "2020-0" + Integer.toString(i);
                else
                    str = "2020-" + Integer.toString(i);                  
                
                
                BiConsumer<List<TempRec>, CardTransaction> getmonthly =
                    (result, c) -> {
                        if (result.isEmpty())
                            result.add(new TempRec(c.getCard(), str , c.getAmount(), 1));
                        else
                        {
                            TempRec item = result.get(result.size()-1);
                            if (item.getCard().equals(c.getCard()))
                            {    
                                item.setTotalExp(item.getTotalExp() + c.getAmount());
                                item.setCount(item.getCount()+1);                                
                            }   
                            else
                                result.add(new TempRec(c.getCard(), str , c.getAmount(), 1));
                        }
                    };                
                
                List<CardTransaction> janlist = FunctionUtil.filter(list, p -> p.getDate().contains(str) & p.getAmount() > minExp);
                janlist.sort((r1, r2) -> r1.getCard().compareTo(r2.getCard()));
                List<TempRec> janresult = FunctionUtil.transform(janlist, getmonthly);
                janresult = FunctionUtil.filter(janresult, p -> p.getTotalExp() > monthlyExp);                                                                                                                                 
                FunctionUtil.forEach(janresult, p -> l.add(new Pair(p.getCard(), p.getCount()*rebate)));
            };
        
        List<Pair<String, Double>> resultlist = FunctionUtil.transform(intlist, loopact);
        resultlist.sort((r1, r2) -> r1.getFirst().compareTo(r2.getFirst()));
        
        
        BiConsumer<List<Pair<String, Double>>, Pair<String, Double>> resultact =
            (result, b) -> {
                if (result.isEmpty())
                    result.add(b);
                else
                {
                    Pair<String, Double> tmp = result.get(result.size()-1);
                    if (tmp.getFirst().equals(b.getFirst()))                                                
                        tmp.setSecond(tmp.getSecond()+b.getSecond());                                               
                    else
                        result.add(b);
                }
            };
        List<Pair<String, Double>> fin = FunctionUtil.transform(resultlist, resultact);   
        return fin;                                                     
    }            
   
    // -------------------------------------- methods given to you
    
    public static void main(String[] args) throws FileNotFoundException
    {
        String filename = "creditCard-data.txt";  // data records sorted by date
        ArrayList<CardTransaction> list = new ArrayList();
        try (Scanner sc = new Scanner(new File(filename)))
        {
            while (sc.hasNext())
            {
                String date = sc.next();
                String card = sc.next().trim();
                double amount = sc.nextDouble();
                CardTransaction t = new CardTransaction(card, date, amount);
                list.add(t);
            }
        }
        
        List<Pair<String, Double>> result = getReward(list, 2500.0, 500.0, 10.0);
        System.out.println("List of cards earning rewards:");
        for (Pair<String, Double> p : result)
            System.out.println(p);
        
    }
}


class TempRec
{
    String card;
    String yearMonth;  // yyyy-mm
    double totalExp;   
    int count;

    // constructor and other methods
    public TempRec(String a, String b, double c, int d)
    {
        card = a;
        yearMonth = b;
        totalExp = c;
        count = d;
    }
    
    public String getCard()
    {
        return card;
    }    
    public String getYearMonth()
    {
        return yearMonth;
    }
    public Double getTotalExp()
    {
        return totalExp;
    }
    public Integer getCount()
    {
        return count;
    }
    
    public void setCard(String c)
    {
        card = c;
    }    
    public void setYearMonth(String ym)
    {
        yearMonth = ym;
    }
    public void setTotalExp(Double te)
    {
        totalExp = te;
    }
    public void setCount(Integer cnt)
    {
        count = cnt;
    }
    @Override
    public String toString()
    {
        return yearMonth + "\t" + card + "\t" + totalExp + "\t" + count;
    }                    
}
