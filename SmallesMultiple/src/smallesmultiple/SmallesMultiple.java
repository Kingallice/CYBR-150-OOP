package smallesmultiple;

public class SmallesMultiple {

    public static void main(String[] args) 
    {
        System.out.println("Smallest Multiple: " + SmallestMultiple(20));
    }
    public static int SmallestMultiple(int High)
    {
        boolean loop = true;
        int Multiple = 0,i = High;
        while(loop)
        {
            i+=High;
            for(int j = 1; j <= High; j++)
            {
                if(i%j == 0)
                {
                    if(j >= High)
                    {
                        Multiple = i;
                        loop = false;
                    }
                }
                else 
                {
                    break;
                }
            }
        }
        return Multiple;
    }
}