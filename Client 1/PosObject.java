
/**
 * PosObject holds a friendly ship's info.
 * 
 * @author Daniel Trupp
 * @version 5/30/2017
 */
public class PosObject
{
    private int x, y, r;
    private String name;
    /**
     * Constructor for objects of class PosObject
     */
    public PosObject(int ex, int why, int arr, String str)
    {
        x = ex;
        y = why;
        r = arr;
        name = str;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getR()
    {
        return r;
    }
    
    public String getName()
    {
        return name;
    }
}