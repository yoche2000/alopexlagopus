// Student name: 
// Studnet ID  :

// Submission deadline: Friday, 30 Oct 2020, 11 am.

// Make suitable modifications to the class.
// Implement the ActionListener

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class BasicMortgageCalculator extends JFrame
{
    final int DEFAULT_FRAME_WIDTH = 420;
    final int DEFAULT_FRAME_HEIGHT = 200;

    private final JButton calculate, advCalculator;
    private final JTextField loanAmount, interestRate, loanTenor, installment;
    
    // Other instance variable where appropriate.
     
    public BasicMortgageCalculator()  // Can modify the constructor, but not changing the layout.
    {
        setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Basic Mortgage Calculator");
        ActionListener listener = new ButtonListener();  
        
        calculate = new JButton("Calculate");
        advCalculator = new JButton("Adv. Mortgage Calculator");
        calculate.addActionListener(listener);
        advCalculator.addActionListener(listener);
        
        loanAmount = new JTextField(10);
        loanTenor = new JTextField(10);
        interestRate = new JTextField(10);
        installment = new JTextField(10);
        installment.setEditable(false);
        
        JLabel label1 = new JLabel("Loan Amount (HKD)");
        JLabel label2 = new JLabel("Loan Tenor (year)");
        JLabel label3 = new JLabel("Mortgage Interest Rate (% p.a.)");
        JLabel label4 = new JLabel("Monthly Repayment (HKD)");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(label1);
        panel.add(loanAmount);
        panel.add(label2);
        panel.add(loanTenor);
        panel.add(label3);
        panel.add(interestRate);
        panel.add(label4);
        panel.add(installment);
        panel.add(calculate);
        panel.add(advCalculator);
        
        add(panel, "Center");
    }
    
    private class ButtonListener implements ActionListener 
    {
        // Implement this class.
    }
}
