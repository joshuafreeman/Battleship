/**
 * Created by Josh on 5/8/2017.
 */
public class Hull extends Ship{
    private HeadSpot myHead;
    public Hull()
    {
        super("hull");
    }
    public Hull(HeadSpot head)
    {
        super("hull");
        myHead = head;
    }
    public String toString()
    {
        return "S";
    }
    public HeadSpot getHead()
    {
        return myHead;
    }
}
