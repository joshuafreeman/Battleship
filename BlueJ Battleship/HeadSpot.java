/**
 * Created by Josh on 5/3/2017.
 */
public class HeadSpot extends Spot {

    private int myRotation;
    private int mySize;
    private String myHull;
    public HeadSpot(String type)
    {
        super("HeadSpot");
        constructShip(type);
    }

    public HeadSpot(String type, int rot)
    {
        super ("HeadSpot");
        myRotation = rot;
        constructShip(type);
    }
    private void constructShip(String type)
    {
        switch(type)
        {
            case("Battleship"):
                mySize = 6;
                myHull = "Battleship";
                break;
            case("Carrier"):
                mySize = 5;
                myHull = "Carrier";
                break;
            case("Destroyer"):
                mySize = 4;
                myHull = "Destroyer";
                break;
            case("Submarine"):
                mySize = 3;
                myHull = "Submarine";
                break;
            case("Patrol Boat"):
                mySize = 2;
                myHull = "Patrol Boat";
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
    public String getHull()
    {
        return myHull;
    }
    public String toString()
    {
        return "H";
    }
}
