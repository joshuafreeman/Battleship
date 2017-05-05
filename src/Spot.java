
/**
 * Created by Josh on 5/2/2017.
 */
public abstract class Spot {
    protected String myType;

    public Spot()
    {
        myType = null;
    }
    public Spot(String type)
    {
        myType = type;
    }
    public String getType()
    {
        return myType;
    }
    public abstract String toString();
}
