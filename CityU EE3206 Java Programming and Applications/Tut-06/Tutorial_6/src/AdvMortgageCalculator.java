// Student name:Lee Yo-Che
// Studnet ID  :55348947

// Submission deadline: Friday, 30 Oct 2020, 11 am.

// Make suitable modifications to the class.
// Implement the ActionListener

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class AdvMortgageCalculator extends JFrame
{
    final int DEFAULT_FRAME_WIDTH = 550;
    final int DEFAULT_FRAME_HEIGHT = 240;

    private final JButton calculate, basicCalculator;
    private final JTextField loanAmount, loanTenor;
    private final JTextField installment1, installment2, interestRate1, interestRate2; 
    private final JTextField year_rate1;
    private final JLabel labelC, labelD;
    
    // Other instance variable where appropriate.
          
    public AdvMortgageCalculator()  // Can modify the constructor, but not changing the layout.
    {
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Advanced Mortgage Calculator");
        ActionListener listener = new ButtonListener();  
        
        calculate = new JButton("Calculate");
        basicCalculator = new JButton("Basic Mortgage Calculator");
        calculate.addActionListener(listener);
        basicCalculator.addActionListener(listener);
        
        loanAmount = new JTextField(10);
        loanTenor = new JTextField(10);
        interestRate1 = new JTextField(10);
        interestRate2 = new JTextField(10);
        installment1 = new JTextField(10);
        installment1.setEditable(false);
        installment2 = new JTextField(10);
        installment2.setEditable(false);
        
        year_rate1 = new JTextField(2);
        
        JLabel label1 = new JLabel("Loan Amount (HKD)");
        JLabel label2 = new JLabel("Loan Tenor (year)");
        JLabel label3 = new JLabel("Mortgage Interest Rate (% p.a.)");
        JLabel label4 = new JLabel("Mortgage Interest Rate (% p.a.)");
        JLabel label5 = new JLabel("Monthly Repayment (HKD)");
        JLabel label6 = new JLabel("Monthly Repayment (HKD)");
        
        JLabel labelA = new JLabel("years 1 to ");
        JLabel labelB = new JLabel("remaining years");
        
        JLabel dummy1 = new JLabel(" ");
        JLabel dummy2 = new JLabel(" ");
        JLabel dummy3 = new JLabel(" ");

        
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridLayout(1,2));
        subPanel.add(labelA);
        subPanel.add(year_rate1);
        
        labelC = new JLabel("To be determined");
        labelD = new JLabel("To be determined");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 3));
        panel.add(label1);
        panel.add(loanAmount);
        panel.add(dummy1);
        
        panel.add(label2);
        panel.add(loanTenor);
        panel.add(dummy2);
        
        panel.add(label3);
        panel.add(interestRate1);
        panel.add(subPanel);
        
        panel.add(label4);
        panel.add(interestRate2);
        panel.add(labelB);
        
        panel.add(label5);
        panel.add(installment1);
        panel.add(labelC);
        
        panel.add(label6);
        panel.add(installment2);
        panel.add(labelD);
        
        panel.add(calculate);
        panel.add(basicCalculator);
        panel.add(dummy3);
       
        add(panel, "Center");
    }
    
    private class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == calculate)
            {                
                //installment1.setText("Calculate");                           
                String result = new String();                
                String result2 = new String();
                //boolean flag = true;
                try
                {
                    double principle = Double.parseDouble(loanAmount.getText());
                    double t2 = Double.parseDouble(loanTenor.getText());  //The length
                    double t1 = Double.parseDouble(year_rate1.getText()); //The time of separation  
                    double rate1 = Double.parseDouble(interestRate1.getText())/1200;  
                    double rate2 = Double.parseDouble(interestRate2.getText())/1200;  
                    
                    double top1 = principle * rate1 * ( Math.pow((1+rate1),(t2*12)));
                    double bot1 = Math.pow((1+rate1 ),(t2*12))-1;
                    
                    //double top2 = principle * rate2 * ( Math.pow((1+rate2),(t2*12)) - Math.pow((1+rate2),(t1*12)));
                    //principle -= t1*12*top1/bot1;
                    double top2 = principle * rate2 * ( Math.pow((1+rate2),((t2)*12)) - Math.pow((1+rate2),((t1)*12)));
                    double bot2 = Math.pow((1+rate2 ),(t2*12))-1;
                    
                    labelC.setText("for years 1 to " + String.valueOf(Math.round(t1)));
                    labelD.setText("for years " + String.valueOf(Math.round(t1)) + " to " + String.valueOf(Math.round(t2)));
                    
                    result = String.valueOf(top1/bot1);
                    result2 = String.valueOf(top2/bot2);
                }
                catch(NumberFormatException ex)
                {
                    result = "err"; 
                }
                
                installment1.setText(result);
                installment2.setText(result2);
            }                               
            else if (e.getSource() == basicCalculator)
            {                
                //installment1.setText("Basic");
                BasicMortgageCalculator ncalculator = new BasicMortgageCalculator();
                ncalculator.setVisible(true);
                dispose();
                //System.exit(0);
            }    
                
        } 
        // Implement this class.
        
    }
}
