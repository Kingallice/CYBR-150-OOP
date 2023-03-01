package latticepath;

public class LatticePath {

    static String[][] Square = new String[21][21];
    public static void main(String[] args) {
        for(int i = 0; i < Square.length; i++)
        {
            for(int j = 0; j < Square.length; j++)
            {
                Square[i][j] = ".";
            }
        }
        for(int i = 0; i < Square.length; i++)
        {
            System.out.println();
            for(int j = 0; j < Square.length; j++)
            {
                System.out.print(Square[i][j]);
            }
        }
    }
    
}
