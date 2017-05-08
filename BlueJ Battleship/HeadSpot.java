/**
 * Created by Josh on 5/3/2017.
 */
public class HeadSpot extends Spot {

    private int myRotation;
    private int mySize;

    public HeadSpot(String type)
    {
        super(type);
        constructShip();
    }

    public HeadSpot(String type, int rot)
    {
        super (type);
        myRotation = rot;
        constructShip();
    }
    private void constructShip()
    {
        switch(myType)
        {
            case("Battleship"):
                mySize = 6;
                break;
            case("Carrier"):
                mySize = 5;
                break;
            case("Destroyer"):
                mySize = 4;
                break;
            case("Submarine"):
                mySize = 3;
                break;
            case("Patrol Boat"):
                mySize = 2;
                break;
        }
    }
    public int getRotation()
    {
        return myRotation;
    }
    public int getSize()
    {
        return mySize;
    }
    public String getType()
    {
        return myType;
    }
    public String toString()
    {
        return "H";
    }
}
