package atm;

public class Bank extends BankAccount {
    private double maximumTopUp = 20000;
    private double refillAmount = 5000;
    
    public Bank()
    {
        super();
    }
    public Bank(String Pin, String AccNum, double Balance)
    {
        super(Pin,AccNum,Balance);
    }
    public void RefillATM()
    {
        if((dblBalance + refillAmount) < maximumTopUp)
        dblBalance += refillAmount;
        else dblBalance = maximumTopUp;
    }
}
