 

/**
 * Created by Josh on 5/2/2017.
 */
public class Ship {
    protected String myType;
    protected int mySize;
    public Ship(String s)
    {
        myType = s;
        mySize = 0;
        constructShip();
    }

    private void constructShip()
    {
        switch(myType)
        {
            case("Aircraft Carrier"):
                mySize = 5;
                break;
            case("Battleship"):
                mySize = 4;
                break;
            case("Submarine"):
                mySize = 3;
                break;
            case("Cruiser"):
                mySize = 2;
                break;
            case("Scout"):
                mySize = 1;
                break;
        }
    }
    public int getSize()
    {
        return mySize;
    }
    public String getType()
    {
        return myType;
    }

}
