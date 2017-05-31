
/**
 * Created by Josh on 5/3/2017.
 */
public class HeadSpot extends Ship 
{
	/**sets horizontal or vertical rotation*/
    private int myRotation;
    /**the size/length of the ship*/
    private int mySize;
    /**what type of ship it is*/
    private String myHull;
    /**the x coordinate of the head of the ship*/
    private int xCord;
    /**the y coordinate of the head of the ship*/
    private int yCord;

    /**
     * Constructs based off the ship type specified
     * @param type
     */
    public HeadSpot(String type)
    {
        super("head");
        constructShip(type);
    }

    /**
     * constructs a ship with specified rotation and coordinate
     * @param type
     * @param rot
     * @param x
     * @param y
     */
    public HeadSpot(String type, int rot, int x, int y)
    {
        super("head");
        myRotation = rot;
        xCord = x;
        yCord = y;
        constructShip(type);
    }
    
    /**
     * Sets ship information to the specified ship type
     * @param type
     */
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
                mySize = 3;
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
    
    /**
     * Returns the rotation of the ship
     * @return myRotation
     */
    public int getRotation()
    {
        return myRotation;
    }
    
    /**
     * Returns the size/length of the ship
     * @return mySize
     */
    public int getSize()
    {
        return mySize;
    }
    
    /**
     * Returns the type of ship
     * @return myHull
     */
    public String getHull()
    {
        return myHull;
    }
    
    /**
     * Returns the x-coordinate of the head of the ship
     * @return xCoord
     */
    public int getXCord()
    {
        return xCord;
    }
    
    /**
     * Returns the y-coordinate of the head of the ship
     * @return yCord
     */
    public int getYCord()
    {
        return yCord;
    }

    /**
     * Returns "H" to represent HeadSpot
     */
    public String toString()
    {
        return "H";
    }
}
