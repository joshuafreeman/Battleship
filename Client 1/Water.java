/**
 * Created by Johnny Liu
 * 5/3/2017.
 * This class represents a untouched spot on the board
 */
public class Water extends Spot
{
	/**
	 * Default constructor
	 */
    public  Water()
    {
        super("water");
    }

    /**
     * Returns "." to represent water
     */
    public String toString()
    {
        return ".";
    }
}
