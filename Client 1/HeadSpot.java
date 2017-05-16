
/**
 * Created by Josh on 5/3/2017.
 */
public class HeadSpot extends Ship {

    private int myRotation;
    private int mySize;
    private String myHull;
    private int xCord;
    private int yCord;

    public HeadSpot(String type)
    {
        super("head");
        constructShip(type);
    }

    public HeadSpot(String type, int rot, int x, int y)
    {
        super("head");
        myRotation = rot;
        xCord = x;
        yCord = y;
        constructShip(type);
    }
    private void constructShip(String type)
    {
        switch(type)
        {
            case("Battleship"):
                mySize = 5;
                myHull = "Battleship";
                break;
            case("Carrier"):
                mySize = 6;
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
    public int getXCord()
    {
        return xCord;
    }
    public int getYCord()
    {
        return yCord;
    }

    public String toString()
    {
        return "H";
    }
}
