/**
 * Created by Johnny Liu
 * 5/3/2017.
 * This class represents a miss marker on the board
 */
public class Miss extends Spot 
{
	/**
	 * Default constructor
	 */
    public Miss()
    {
        super("miss");
    }

    /**
     * Returns "O" to represent a miss
     */
    public String toString()
    {
        return "O";
    }
}
