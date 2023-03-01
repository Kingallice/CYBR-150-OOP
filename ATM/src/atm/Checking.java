package atm;

import javax.swing.JOptionPane;

public class Checking extends BankAccount{
    private double BillFee = 1.25;
    private double MaxBillPayment = 10000;
    
    public Checking()
    {
        super();
    }
    public Checking(String Pin, String AccNum, double Balance)
    {
        super(Pin,AccNum,Balance);
    }
    public void PayBill(double Bill)
    {
        if(Bill <= MaxBillPayment)
        {
            if(dblBalance - Bill > 0)
                dblBalance -= (Bill + BillFee);
            else JOptionPane.showMessageDialog(null,"Your balance is too low for this transaction." +
                    "\n" + "Balance: " + dblBalance);
        }
        else JOptionPane.showMessageDialog(null, "The maximum charge for a bill is $" + MaxBillPayment);
    }
}
