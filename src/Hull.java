/**
 * Created by Josh on 5/8/2017.
 */
public class Hull extends Ship
{
	/**head of the ship*/
    private HeadSpot myHead;
    
    /**
     * Default constructor
     */
    public Hull()
    {
        super("hull");
    }
    
    /**
     * Constructor setting myHead to the head 
     * @param head
     */
    public Hull(HeadSpot head)
    {
        super("hull");
        myHead = head;
    }
    
    /**
     * Returns a "S" to represent a Hull
     */
    public String toString()
    {
        return "S";
    }
    
    /**
     * Returns the head of the ship
     * @return myHead
     */
    public HeadSpot getHead()
    {
        return myHead;
    }
}
