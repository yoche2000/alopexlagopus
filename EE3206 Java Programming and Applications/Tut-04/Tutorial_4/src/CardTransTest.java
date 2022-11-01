// Student name: Lee Yo-Che
// Student ID  : 55348947

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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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
                      
        List<Pair<String, Double>> result = new ArrayList();                //The resilt arraylist
        List<Pair<String, Pair<Double, Double>>> monthly= new ArrayList();  //The list sorting a month
                                                                            //Structure <Number <amount, reward>>  
        //List<Pair<String, Double>> reward = new ArrayList();
        //List<Pair<String, Double>> moneycount = new ArrayList();
                
        Iterator<CardTransaction> it = list.iterator();          
        int i = 0;
        String month = new String("01");    //Set month to initial value
        while(it.hasNext())
        {            
            CardTransaction tmp = it.next();   
            if(!tmp.getDate().substring(5,7).matches(month))    //If the month is different
            {                                                   
                //System.out.println("Month: " + month);
                //System.out.println(monthly); 
                Iterator<Pair<String, Pair<Double, Double>>> it1 = monthly.iterator();  //Set iterator of the monthly list
                while(it1.hasNext())  
                {
                    Pair<String, Pair<Double, Double>> appended = it1.next();           //The entry to be appended
                    if(appended.getSecond().getFirst() >= monthlyExp)                   //If the amount exceed monthlyexp
                    {
                        String cn = appended.getFirst();                                //tmp credit card number
                        Double mn = appended.getSecond().getSecond();                   //tmp money
                                
                        Iterator<Pair<String, Double>> it2 = result.iterator();         //Set iterator of the result list
                        while(it2.hasNext())
                        {
                            Pair<String, Double> nxt = it2.next();                    
                            if(nxt.getFirst().equals(cn))                               //If the cn already in the list
                            {
                            //System.out.println("\t\tFound Exist Entry: " + nxt);
                                it2.remove();                                           //Remove the old object in result
                                mn += nxt.getSecond();                                  //Update the reward value
                            }                    
                        }
                        
                        Pair appair = new Pair(cn, mn);
                        result.add(appair);                                             //Append a new object
                    }    
                }    
                
                monthly.clear();                                                        //The month changes, clear the list
                month = tmp.getDate().substring(5,7);                                   //Update the month var to
            }
            //System.out.println("Entry " + tmp);
            
            if(tmp.getAmount() >= minExp)                                               //Check if the entry meets minexp
            {
                //System.out.println("\tMeet min requirement");
                //boolean exist = false;
                String cnum = tmp.getCard();
                Double amt = tmp.getAmount();
                Double rwd = rebate;                                                      //set reward to 10
                Iterator<Pair<String, Pair<Double, Double>>> mit = monthly.iterator();  
                while(mit.hasNext())
                {
                    Pair<String, Pair<Double, Double>> nxt = mit.next();                    
                    if(nxt.getFirst().equals(cnum))                                     //If the cardnum already in the monthly list
                    {
                        //System.out.println("\t\tFound Exist Entry: " + nxt);
                        mit.remove();                                                   //Remove the old entry
                        amt += nxt.getSecond().getFirst();                              //Update the amout of transaction
                        rwd += nxt.getSecond().getSecond();                             //Update the reward
                    }                    
                }
                
                Pair records = new Pair(amt,rwd);
                Pair newent = new Pair(cnum,records);
                //System.out.println("\t\t\tAppend Entry: "+newent.toString()+" with Amount " + rwd + "\n");
                monthly.add(newent);                                                    //Append a new entry
            }                            
        }
        //System.out.println("Month: " + month);
        //System.out.println(monthly);
                           
        result.sort(Comparator.<Pair<String, Double>>comparingDouble(Pair::getSecond)); //Sort by wreward earned
        Collections.reverse(result);                                                  
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
