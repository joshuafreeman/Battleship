import java.io.Serializable;

/**
 * Created by Josh on 5/2/2017.
 */
public abstract class Spot implements Serializable 
{
	/**what type of spot is it*/
    protected String myType;

    /**
     * Default constructor sets myType to null
     */
    public Spot()
    {
        myType = null;
    }
    
    /**
     * Constructor sets myType to a specified type
     * @param type
     */
    public Spot(String type)
    {
        myType = type;
    }
    
    /**
     * Returns myType 
     */
    public String getType()
    {
        return myType;
    }
    
    /**
     * Abstract toString to be implemented by subclasses
     */
    public abstract String toString();
}
