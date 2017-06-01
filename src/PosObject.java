
/**
 * PosObject holds a friendly ship's info.
 * 
 * @author Daniel Trupp
 * @version 5/30/2017
 */
public class PosObject
{
	/**Holds the information about coordinates and rotation*/
    private int x, y, r;
    /**Holds the name*/
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
    
    /**
     * Returns the x-coordinate
     */
    public int getX()
    {
        return x;
    }
    
    /**
     * Returns the y-coordinate
     */
    public int getY()
    {
        return y;
    }
    
    /**
     * Returns the rotation
     */
    public int getR()
    {
        return r;
    }
    
    /**
     * Returns the name
     */
    public String getName()
    {
        return name;
    }
}