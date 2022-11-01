
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
    private final JFrame basicMC;
          
    public AdvMortgageCalculator(JFrame frame)
    {
        basicMC = frame;
        
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
        private NumberFormat nf = NumberFormat.getInstance();
        
        {
            nf.setMaximumFractionDigits(2);
        }
        
        @Override
        public void actionPerformed(ActionEvent event) 
        {
            double p = 0, r1 = 0, r2 = 0;
            int y = 0, m = 0;
            boolean hasError = false;
            
            if (event.getSource() == calculate)
            {
                try
                {
                    p = Double.parseDouble(loanAmount.getText());
                    if (p <= 0)
                    {
                        loanAmount.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    loanAmount.setText("Error");
                    hasError = true;
                }
                
                try
                {
                    y = Integer.parseInt(loanTenor.getText());
                    if (y < 1)
                    {
                        loanTenor.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    loanTenor.setText("Error");
                    hasError = true;
                }
                
                try
                {
                    r1 = Double.parseDouble(interestRate1.getText());
                    r1 /= 100.0;
                    if (r1 <= 0)
                    {
                        interestRate1.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    interestRate1.setText("Error");
                    hasError = true;
                }
                
                try
                {
                    r2 = Double.parseDouble(interestRate2.getText());
                    r2 /= 100.0;
                    if (r2 <= 0)
                    {
                        interestRate2.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    interestRate2.setText("Error");
                    hasError = true;
                }
                
                try
                {
                    m = Integer.parseInt(year_rate1.getText());
                    if (m < 1 || m >= y)
                    {
                        year_rate1.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    year_rate1.setText("Error");
                    hasError = true;
                }
                
                if (hasError)
                {
                    installment1.setText("");
                    installment2.setText("");
                    labelC.setText("To be determined");
                    labelD.setText("To be determined");
                }
                else
                {
                    double payment1 = MortgageUtil.getMonthlyPayment(p, r1, y);
                    double balance = MortgageUtil.getOutstandingBalance(p, r1, y, m);
                    double payment2 = MortgageUtil.getMonthlyPayment(balance, r2, y-m);
                
                    installment1.setText(nf.format(payment1));
                    labelC.setText("for years 1 to " + m);
                    installment2.setText(nf.format(payment2));
                    labelD.setText("for years " + (m+1) + " to " + y);
                }
            }
            else if (event.getSource() == basicCalculator)
            {
                setVisible(false);
                basicMC.setVisible(true);
            }
        }
        
    }
}
