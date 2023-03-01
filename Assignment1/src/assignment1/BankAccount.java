package assignment1;

public abstract class BankAccount {
    private double dblBalance;
    private double dblInterestRate;
    private double dblServiceCharge;
    private int intDeposits;
    private int intWithdrawals;
    private int intServiceCharges;
    String strPin = "1234";
    
    public BankAccount(double Balance, double Interest, double ServiceCharge)
    {
        dblBalance = Balance;
        dblInterestRate = Interest;
        dblServiceCharge = ServiceCharge;
        intDeposits = 0;
        intWithdrawals = 0;
    }
    public BankAccount()
    {
        dblBalance = 0;
        dblInterestRate = 0;
        dblServiceCharge = 0;
        intDeposits = 0;
        intWithdrawals = 0;
    }
    
    //Calculates Balance
    public void Deposit(double Deposit)
    {
        if(Deposit > 0)
        {
            dblBalance = dblBalance + Deposit;
            intDeposits++;
            System.out.println("Deposited: $" + Deposit);
        }
        else
        {
            System.out.println("Please enter a value larger than zero.");
        }
    }
    public void Withdraw(double Withdraw)
    {
        if(Withdraw > 0 && dblBalance - Withdraw >= 0)
        {
            dblBalance = dblBalance - Withdraw;
            intWithdrawals++;
            System.out.println("Withdrew: $" + Withdraw);
        }
        else if(Withdraw < 0)
        {
            System.out.println("Please enter a value larger than zero.");
        }
        else
        {
            System.out.println("Enter a value less than or equal to the balance.");
        }
    }
    public void CalcInterest()
    {
        double MonthlyInterestRate = dblInterestRate/12;
        double Interest = MonthlyInterestRate * dblBalance;
        dblBalance = dblBalance + Interest;
    }
    public void ResetActions()
    {
        if(intWithdrawals >= 4)
        {
        intWithdrawals = intWithdrawals - 4;
        dblServiceCharge = dblServiceCharge - intWithdrawals;
        }   
        intDeposits = 0;
        intWithdrawals = 0;
    }
    public void MonthlyProcess()
    {      
        dblBalance = dblBalance - dblServiceCharge;
        
        CalcInterest();
        ResetActions();
    }
    
    //Sets Values
    public void setMonthlyServiceCharge(double Charge)
    {
        dblServiceCharge = Charge;
    }
    
    //Gets Values
    public double getBalance()
    {
        return dblBalance;
    }
    public double getInterestRate()
    {
        return dblInterestRate;
    }
    public double getMonthlyServiceCharge()
    {
        return dblServiceCharge;
    }
    public int getNumDeposits()
    {
        return intDeposits;
    }
    public int getNumWithdrawals()
    {
        return intWithdrawals;
    }
    public String getPinCode()
    {
        return strPin;
    }
}
