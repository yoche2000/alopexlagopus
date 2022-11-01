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

	You need to implement the method getReward().
	The method returns a list of cards and the total rewards received by each card.
	Only cards with non-zero reward are included in the output list.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import static java.util.Comparator.comparing;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class CardTransTest 
{            
    static List<Pair<String, Double>> getReward(List<CardTransaction> list, 
                                                double monthlyExp, double minExp,
                                                double rebate)
    {
        // Input list is initially sorted by date.
        // Use Iterator to traverse the list in your implementation.
        // Do not traverse the list using integer index.
        
        List<Pair<String, Double>> result = new ArrayList();      
        
        list.sort(comparing(CardTransaction::getCard));  // sort by card number, stable sorting algorithm
        // list.sort((t1, t2) -> t1.getCard().compareTo(t2.getCard());
        
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
        
        /*
        int count = 0;
        double qualifiedExp = 0;
        CardTransaction ref = new CardTransaction("", "", 0);

        Iterator<CardTransaction> itr = list.iterator();
        while (itr.hasNext())
        {
            CardTransaction t = itr.next();
            if (c.compare(t, ref) == 0)  // same card same month
            {   
                if (t.getAmount() >= minExp)
                {
                    count++;
                    qualifiedExp += t.getAmount();
                }
            }
            else
            {
                if (qualifiedExp >= monthlyExp)
                {
                    double reward = count * rebate;
                    if (result.isEmpty())
                        result.add(new Pair<>(ref.getCard(), reward));
                    else
                    {
                        Pair<String, Double> p = result.get(result.size()-1);
                        if (p.getFirst().equals(ref.getCard()))
                            p.setSecond(p.getSecond() + reward);
                        else
                            result.add(new Pair<>(ref.getCard(), reward));
                    }
                }
                
                if (t.getAmount() >= minExp)
                {
                    count = 1;
                    qualifiedExp = t.getAmount();
                }
                else
                {
                    count = 0;
                    qualifiedExp = 0;
                }
                ref = t;
            }
        }
        if (qualifiedExp >= monthlyExp)
        {
            double reward += count * rebate;
            if (result.isEmpty())
                result.add(new Pair<>(ref.getCard(), reward));
            else
            {
                Pair<String, Double> p = result.get(result.size()-1);
                if (p.getFirst().equals(ref.getCard()))
                    p.setSecond(p.getSecond() + reward);
                else
                    result.add(new Pair<>(ref.getCard(), reward));
            }
        }
        */
        
        Iterator<CardTransaction> itr = list.iterator();
        CardTransaction ref = itr.hasNext() ? itr.next() : null;
        CardTransaction t = ref;
        while (ref != null)
        {
            int count = 0;           // number of qualified transactions
            double qualifiedExp = 0; // sum of the amount of qualified transactions

            while (t != null && c.compare(t, ref) == 0)  
            {
                if (t.getAmount() >= minExp)
                {
                    qualifiedExp += t.getAmount();
                    count++;
                }
                t = itr.hasNext() ? itr.next() : null;
            }
            if (qualifiedExp >= monthlyExp)
            {
                double reward = count * rebate;
                if (result.isEmpty())
                    result.add(new Pair<>(ref.getCard(), reward));
                else
                {
                    Pair<String, Double> p = result.get(result.size()-1);
                    if (p.getFirst().equals(ref.getCard()))
                        p.setSecond(p.getSecond() + reward);
                    else
                        result.add(new Pair<>(ref.getCard(), reward));
                }
            }
            ref = t;
        }
        
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
