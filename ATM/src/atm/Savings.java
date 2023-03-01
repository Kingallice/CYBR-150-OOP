package atm;

public class Savings extends BankAccount{
    private double Interest = 0.015;
    
    public Savings()
    {
        super();
    }
    public Savings(String Pin, String AccNum, double Balance)
    {
        super(Pin,AccNum,Balance);
    }
    public void PayInterest()
    {
        dblBalance += (dblBalance * Interest);
    }
}
