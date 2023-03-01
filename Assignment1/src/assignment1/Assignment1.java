/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment1;

import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

public class Assignment1 {

    public static void main(String[] args) {
        JPasswordField pf = new JPasswordField();
        BankAccount account = new SavingsAccount();
        String Pin = "";
        boolean PinNeeded = true;
        while(PinNeeded)
        {
            int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Pin",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (okCxl == JOptionPane.OK_OPTION) 
            {
                Pin = new String(pf.getPassword());
                if(Pin.matches(account.getPinCode()))
                {
                    PinNeeded = false;
                }
                else
                {
                    System.out.println("Enter a valid pin.");
                }
            }
            else PinNeeded = false;
        }
        if(account.getPinCode().matches(Pin))
        {
            double dblBalance = Double.parseDouble(JOptionPane.showInputDialog("Please enter the balance."));
            double dblInterest = Double.parseDouble(JOptionPane.showInputDialog("Please enter the interest rate."));
            double dblMonthlyCharge = Double.parseDouble(JOptionPane.showInputDialog("Please enter a monthly service charge."));

            account = new SavingsAccount(dblBalance, dblInterest, dblMonthlyCharge);

            boolean blnLoop = true;
            while(blnLoop)
            {
                int intAction = Integer.parseInt(JOptionPane.showInputDialog(
                        "What action would you like to take? \n"
                    + "     1)List the balance, number of deposits and withdrawals \n"
                    + "     2)Deposit money \n"
                    + "     3)Withdraw money \n"
                    + "     4)Exit"));
                switch (intAction)
                {
                    case 1:
                        System.out.println("Balance: $" + account.getBalance() + "\n"
                                + "Number of Deposits: " + account.getNumDeposits() + "\n"
                                        + "Number of Withdrawals: " + account.getNumWithdrawals() + "\n");
                        break;
                    case 2:
                        double Deposit = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of the deposit."));
                        account.Deposit(Deposit);
                        System.out.println("Balance: $" + account.getBalance() + "\n"); 
                        break;
                    case 3:
                        double Withdraw = Double.parseDouble(JOptionPane.showInputDialog("Enter the amount of the withdrawal."));
                        account.Withdraw(Withdraw);
                        System.out.println("Balance: $" + account.getBalance() + "\n");
                        break;
                    case 4:
                        blnLoop = false;
                        break;
                }
            }
        }
    }    
}
