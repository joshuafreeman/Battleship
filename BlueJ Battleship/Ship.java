import java.util.ArrayList;

/**
 * Created by Josh on 5/2/2017.
 */
public class Ship extends Spot{
    private HeadSpot myHead;
    public Ship()
    {
        super("ship");
    }
    
    public Ship(HeadSpot head)
    {
        super("ship");
        myHead = head;
    }
    public HeadSpot getHead()
    {
        return myHead;
    }
    public String toString()
    {
        return "S";
    }
    
    
}
