 

/**
 * Created by Josh on 5/2/2017.
 */
public class Spot {

    private String myType;

    public Spot(String type)
    {
        myType = type;
    }

    public String toString()
    {
        if(myType.equals("ship"))
            return "x";
        else
            return ".";
    }
}
