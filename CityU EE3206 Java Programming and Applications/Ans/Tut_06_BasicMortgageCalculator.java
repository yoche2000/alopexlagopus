
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
    private final JFrame advMC;
     
    public BasicMortgageCalculator()
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
        
        advMC = new AdvMortgageCalculator(this);
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
            double p = 0, r = 0;
            int y = 0;
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
                    r = Double.parseDouble(interestRate.getText());
                    r /= 100.0;
                    if (r <= 0)
                    {
                        interestRate.setText("Error");
                        hasError = true;
                    }
                }
                catch(NumberFormatException e)
                {
                    interestRate.setText("Error");
                    hasError = true;
                }

                if (hasError)
                    installment.setText("");
                else
                {
                    double payment = MortgageUtil.getMonthlyPayment(p, r, y);
                    installment.setText(nf.format(payment));    
                }
            }
            else if (event.getSource() == advCalculator)
            {
                setVisible(false);
                advMC.setVisible(true);
            }
        }
    }
}
