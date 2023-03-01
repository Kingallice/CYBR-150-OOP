package atm;

public class Customer {
    protected String strName, strPin;
    
    public Customer()
    {
        strName = "";
        strPin = "";
    }
    public Customer(String Name, String Pin)
    {
        strName = Name;
        strPin = Pin;
    }
    public String getName()
    {
        return strName;
    }
}
