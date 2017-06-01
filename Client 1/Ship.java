/**
 * Created by Johnny Liu
 * 5/2/2017.
 */
public class Ship extends Spot
{
	/**
	 * Default constructor
	 */
    public Ship()
    {
        super("ship");
    }
    
    /**
     * Constructs a Ship based on the type specified
     * @param type
     */
    public Ship(String type)
    {
        super(type);
    }

    /**
     * Returns "S" to represent a Ship
     */
    public String toString()
    {
        return "S";
    }
    
    
}
