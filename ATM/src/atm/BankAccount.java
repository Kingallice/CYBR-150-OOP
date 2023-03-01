package atm;

import javax.swing.JOptionPane;

public class BankAccount {
    private String strPin, strAccNum;
    protected double dblBalance;
    private double dblMaxWithdrawal = 1000;
    
    BankAccount(){
        strPin = "";
        strAccNum = "";
        dblBalance = 0;
    }
    BankAccount(String Pin, String AccNum, double Balance){
        strPin = Pin;
        strAccNum = AccNum;
        dblBalance = Balance;
    }
    public void Deposit(double Deposit)
    {
        dblBalance += Deposit;
    }
    public void Withdraw(double Withdrawal)
    {
        if(Withdrawal % 10 == 0)
        {    
            if(Withdrawal <= dblMaxWithdrawal)
            {
                if(dblBalance - Withdrawal > 0)
                    dblBalance -= Withdrawal;
                else JOptionPane.showMessageDialog(null, 
                        "Your account balance is too low for this transaction." + "\n" + 
                                "Balance: " + dblBalance);
            }
            else
            {
                JOptionPane.showMessageDialog(null,"The maximum withdrawal is $" + dblMaxWithdrawal + ".");
            }
        }
        else JOptionPane.showMessageDialog(null,"Multiples of 10 can only be withdrawn."); 
    }
    public void TransferIn(double Transfer)
    {
        dblBalance += Transfer;
    }
    public void TransferOut(double Transfer)
    {
        dblBalance -= Transfer;
    }
    public String getPin()
    {
        return strPin;
    }
    public String getAccNum()
    {
        return strAccNum;
    }
    public double getBalance()
    {
        return ((double)Math.round(dblBalance * 100d) / 100d);
    }
}
