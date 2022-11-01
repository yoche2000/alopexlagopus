// Student name: 
// Student ID  : 

// Submission deadline: Friday, 9 Oct 2020, 11 am.

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

        The class FunctionUtil is given to you.
	Implement the method getReward() using the functional programming approach.
        That is, you should not use any explicit looping in the method getReward().
        You can use the sort() method, and the methods in FunctionUtil.
	
        You can use the class TempRec or the generic class Triple in your design.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class CardTransTest2 
{            
    static List<Pair<String, Double>> getReward(List<CardTransaction> list, 
                                                double monthlyExp, double minExp,
                                                double rebate)
    {
        // Input list is initially sorted by date.
        // Implement this method using the Functional Programming approach.
        // Do not use any explicit loop in the implementation of this method.     
         
        /* Design using the generic class Triple<E1, E2, E3>

        // Comparator to compare card number, and then month
        Comparator<CardTransaction> c = (t1, t2) -> {   
                                            int r = t1.getCard().compareTo(t2.getCard());
                                            if (r != 0)
                                                return r;
                                   
                                            String d1 = t1.getDate();
                                            String d2 = t2.getDate();
                                            if (d1.charAt(5) != d2.charAt(5))
                                                return d1.charAt(5) - d2.charAt(5);
                                            return d1.charAt(6) - d2.charAt(6);
                                        };  

        BiConsumer<List<Triple<CardTransaction, Double, Integer>>, CardTransaction> action =
            (summaryList, t) -> {
                if (summaryList.isEmpty())
                    summaryList.add(new Triple<>(t, t.getAmount(),1));
                else
                {
                    Triple<CardTransaction, Double, Integer> item = summaryList.get(summaryList.size()-1);
                    if (c.compare(item.getFirst(), t) == 0)
                    {
                        item.setSecond(item.getSecond() + t.getAmount());
                        item.setThird(item.getThird() + 1);
                    }
                    else
                        summaryList.add(new Triple<>(t, t.getAmount(),1));
                }
            };
        
        BiConsumer<List<Pair<String, Double>>, Triple<CardTransaction, Double, Integer>>
            action2 = (resList, item) -> {
                if (resList.isEmpty())
                {
                    resList.add(new Pair<>(item.getFirst().getCard(), item.getThird()*rebate));
                }
                else
                {
                    Pair<String, Double> p = resList.get(resList.size()-1);
                    if (p.getFirst().equals(item.getFirst().getCard()))
                        p.setSecond(p.getSecond() + item.getThird()*rebate);
                    else
                        resList.add(new Pair<>(item.getFirst().getCard(), item.getThird()*rebate));
                }
            };
        
        List<CardTransaction> qualifiedTrans = FunctionUtil.filter(list, t -> t.getAmount() >= minExp);
        qualifiedTrans.sort(comparing(CardTransaction::getCard));  // sort by card number, stable sorting algorithm
        List<Triple<CardTransaction, Double, Integer>> temp = FunctionUtil.transform(qualifiedTrans, action);
        temp = FunctionUtil.filter(temp, item -> item.getSecond() >= monthlyExp);
        List<Pair<String, Double>> result = FunctionUtil.transform(temp, action2);    
        */
        

        // Design using the working class TempRec 

        BiConsumer<List<TempRec>, CardTransaction> 
            action1 = (summaryList, t) -> {
                if (summaryList.isEmpty())
                    summaryList.add(new TempRec(t.getCard(), t.getDate().substring(0,7), t.getAmount(),1));
                else
                {
                    TempRec item = summaryList.get(summaryList.size()-1);
                    if (item.getCard().equals(t.getCard()) && 
                        item.getYearMonth().equals(t.getDate().substring(0,7)))
                    {
                        item.addTotalExp(t.getAmount());
                        item.incCount();
                    }
                    else
                        summaryList.add(new TempRec(t.getCard(), t.getDate().substring(0,7), t.getAmount(),1));
                }
            };

        BiConsumer<List<Pair<String, Double>>, TempRec>
            action2 = (resList, item) -> {
                if (resList.isEmpty())
                {
                    resList.add(new Pair<>(item.getCard(), item.getCount()*rebate));
                }
                else
                {
                    Pair<String, Double> p = resList.get(resList.size()-1);
                    if (p.getFirst().equals(item.getCard()))
                        p.setSecond(p.getSecond() + item.getCount()*rebate);
                    else
                        resList.add(new Pair<>(item.getCard(), item.getCount()*rebate));
                }
            };
        
        List<CardTransaction> qualifiedTrans = FunctionUtil.filter(list, t -> t.getAmount() >= minExp);
        qualifiedTrans.sort(comparing(CardTransaction::getCard));  // sort by card number, stable sorting algorithm   
        List<TempRec> temp = FunctionUtil.transform(qualifiedTrans, action1);
        temp = FunctionUtil.filter(temp, item -> item.getTotalExp() >= monthlyExp);
        List<Pair<String, Double>> result = FunctionUtil.transform(temp, action2); 
        return result;
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
    
    public TempRec(String c, String ym, double amount, int n)
    {
        card = c;
        yearMonth = ym;
        totalExp = amount;
        count = n;
    }
    
    public String getCard()
    {
        return card;
    }
    
    public String getYearMonth()
    {
        return yearMonth;
    }
    
    public double getTotalExp()
    {
        return totalExp;
    }
    
    public void addTotalExp(double amount)
    {
        totalExp += amount;
    }
    
    public int getCount()
    {
        return count;
    }
    
    public void incCount()
    {
        count++;
    }
    
}

