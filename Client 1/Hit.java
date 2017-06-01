/**
 * Created by Johnny Liu
 * 5/3/2017.
 * This class represents a hit on the battleship board
 */
public class Hit extends Spot
{
	/**
	 * Default constructor
	 */
    public Hit()
    {
        super("hit");
    }

    /**
     * Returns "X" to represent a Hit
     */
    public String toString()
    {
        return "X";
    }
}
