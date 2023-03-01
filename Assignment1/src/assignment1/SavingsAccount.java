package assignment1;

public class SavingsAccount extends BankAccount{
 
    private boolean blnActive;
    
    public SavingsAccount(double Balance, double InterestRate, double ServiceCharge)
    {
        super(Balance, InterestRate, ServiceCharge);
        if(Balance < 25)
        {
            blnActive = false;
        }
        else blnActive = true;
    }
    public SavingsAccount()
    {
        super();
        blnActive = false;
    }
    
    @Override
    public void Deposit(double Deposit)
    {
        if (blnActive) 
        {
            super.Deposit(Deposit);
        } 
        else 
        {
            super.Deposit(Deposit);
            if(super.getBalance() >= 25) 
            {
            blnActive = true;
            }
        }
    }
    @Override
    public void Withdraw(double Withdrawal)
    {
        if (blnActive) 
        {
            super.Withdraw(Withdrawal);
            if (super.getBalance() < 25) 
            {
                blnActive = false;
            }
        }
        else if(!blnActive)
        {
            System.out.println("Account is inactive! Withdrawal not available. "
                + "To allow withdrawals bring the balance above $25");
        }
    }
    @Override
    public void MonthlyProcess()
    {
        if(super.getNumWithdrawals()>4)
        {
            double dblCharge = super.getMonthlyServiceCharge();
            int numCharges = super.getNumWithdrawals() - 4;
            for(int i = 0; i < numCharges;i++)
            {
                dblCharge = super.getMonthlyServiceCharge() + 1;
                super.setMonthlyServiceCharge(dblCharge);
            }
            super.setMonthlyServiceCharge(dblCharge);
        }
        super.MonthlyProcess();
        if (super.getBalance() < 25) 
        {
            blnActive = false;
        }
    }
}
