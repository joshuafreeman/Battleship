/**
 * Created by Johnny Liu
 * 5/4/2017.
 * This class represents your own board and is child class of Board
 */
public class PlayerBoard extends Board
{
	/**
	 * Default constructor creates a 10x10 board
	 */
    public PlayerBoard()
    {
        super(10,10);
    }
    
    /**
     * Constructor creates a x by y sized board
     * @param x
     * @param y
     */
    public PlayerBoard(int x, int y)
    {
        super(x,y);
    }
    
    /**
     * Places a ship on the board
     * @param xCord
     * @param yCord
     * @param rotation
     * @param name
     */
    public boolean setShip(int xCord, int yCord, int rotation, String name)
    {
    	//creates the head of the ship
        HeadSpot type = new HeadSpot(name);
        //Make bounds 0-9 for array
        xCord--;yCord--; 
        //tests if the ship fits
        boolean canSet = canPlace(xCord, yCord, rotation, name);

        //if the ship can be placed there
        if(canSet)
        {
        	//if horizontal
            if (rotation == 0)
            {
            	//places the entire ship horizontally
                myBoard[yCord][xCord] = new HeadSpot(name, 0, xCord, yCord);
                for (int x = xCord + 1; x < xCord + type.getSize(); x++)
                    myBoard[yCord][x] = new Hull((HeadSpot)myBoard[yCord][xCord]);
                return true;
            }
            //if vertical
            else
            {
            	//places the entire ship vertically
                myBoard[yCord][xCord] = new HeadSpot(name, 1, xCord, yCord);
                for (int y = yCord + 1; y < yCord + type.getSize(); y++)
                    myBoard[y][xCord] = new Hull((HeadSpot)myBoard[yCord][xCord]);
                return true;
            }
        }
        else
            return false;
    }

    /**
     * Rotates the ship
     * @param xCord
     * @param yCord
     * @return rotate
     */
    public boolean rotateShip(int xCord, int yCord)
    {
    	//Make bounds 0-9 for array
        xCord--;yCord--;

        //if not a ship
        if(myBoard[yCord][xCord].getType().equals("water"))
            return false;

        //saves the head of the ship in a temporary variable
        HeadSpot head = (HeadSpot)myBoard[yCord][xCord];
        int newRotation;

        //Figure out new rotation direction
        if(head.getRotation() == 1)
            newRotation = 0;
        else
            newRotation = 1;

        //Test to see if you can rotate
        boolean rotate = canRotate(xCord, yCord, newRotation, head.getType());

        //Set the ship to be water if you can rotate
        if(rotate)
        {
        	//if horizontal
            if(newRotation == 0)
            {
            	//places the new ship
                for (int x = xCord + 1; x < xCord + head.getSize(); x++)
                    myBoard[yCord][x] = new Ship();
                //removes the old ship
                for (int y = yCord + 1; y < xCord + head.getSize();y++)
                    myBoard[y][xCord] = new Water();
            }
            //if vertical
            else
            {
            	//places the new ship
                for (int y = yCord + 1; y < xCord + head.getSize(); y++)
                    myBoard[y][xCord] = new Ship();
                //removes the old ship
                for (int x = xCord + 1; x < xCord + head.getSize(); x++)
                    myBoard[yCord][x] = new Water();
            }
        }

        return rotate;
    }
    
    /**
     * Tests to see if you can rotate the ship
     * @param xCord
     * @param yCord
     * @param rotation
     * @param name
     * @return rotate
     */
    private boolean canRotate(int xCord, int yCord, int rotation, String name)
    {
    	//creates a HeadSpot that is treated as if it was on the board
        HeadSpot type = new HeadSpot(name);
        boolean rotate = true;
        
        //if horizontal
        if(rotation == 0)
        {
        	//if longer than the board
            if (xCord + type.getSize() > myBoard[0].length)
                rotate = false;
            else
            	//if another ship is in the way
                for(int x = xCord + 1; x < xCord + type.getSize(); x++)
                    if(!myBoard[yCord][x].getType().equals("water"))
                        rotate = false;
        }
        //if vertical
        else
        {
        	//if longer than the board
            if (yCord + type.getSize() > myBoard.length)
                rotate = false;
            else
            	//if another ship is in the way
                for(int y = yCord + 1; y < yCord + type.getSize(); y++)
                    if(!myBoard[y][xCord].getType().equals("water"))
                        rotate = false;
        }
        return rotate;
    }
    
    /**
     * Tests to see if you can place a ship
     * @param xCord
     * @param yCord
     * @param rotation
     * @param name
     * @return canSet
     */
    private boolean canPlace(int xCord, int yCord, int rotation, String name)
    {
    	//creates a HeadSpot that is treated as if it was on the board
        HeadSpot type = new HeadSpot(name);
        boolean canSet = true;
        
        //if horizontal
        if(rotation == 0)
        {
        	//if longer than board
            if (xCord + type.getSize() > myBoard[0].length)
                canSet = false;
            else
                //if there is another ship in the way
                for(int x = xCord; x < xCord + type.getSize(); x++)
                    if(!myBoard[yCord][x].getType().equals("water"))
                        canSet = false;
        }
        //if vertical
        else
        {
        	//if longer than board
            if (yCord + type.getSize() > myBoard.length)
                canSet = false;
            else
                //if there is another ship in the way
                for(int y = yCord; y < yCord + type.getSize(); y++)
                    if(!myBoard[y][xCord].getType().equals("water"))
                        canSet = false;
        }
        return canSet;
    }
    
    /**
     * Tests to see if the specified spot has a ship
     * @param x
     * @param y
     * @return hit
     */
    public boolean receiveAttack(int x, int y)
    {
    	//makes bound 0-9 for array
        x--;y--;
        boolean hit = false;
        
        //if not water
        if(myBoard[y][x].getType().equals("hull") || myBoard[y][x].getType().equals("head") || myBoard[y][x].getType().equals("hit"))
        {
            myBoard[y][x] = new Hit();
            hit = true;
        }
        else
        	//if water
            myBoard[y][x] = new Miss();

        return hit;
    }
    
    /**
     * Tests if the specified spot on opponent's board has a ship
     * @param x
     * @param y
     * @param board
     * @return hit
     */
    public boolean sendAttack(int x, int y, OpponentBoard board)
    {
    	//makes bounds 0-9 for array
        x--;y--;
        boolean hit;
        //if there is a ship
        hit = board.receiveAttack(x, y);
        return hit;
    }

    /**
     * Tests if a ship is sunk
     * @param place
     * @return sunken
     */
    public boolean sunk(Spot place)
    {
    	//declaration of variables
        int xCord, yCord;
        boolean sunken = true;
        int rotation;
        int size;
        Ship ship = (Ship)place;

        //stores information based the Spot
        if(place.getType().equals("head"))
        {
            xCord = ((HeadSpot)ship).getXCord();
            yCord = ((HeadSpot)ship).getYCord();
            rotation = ((HeadSpot)ship).getRotation();
            size = ((HeadSpot)ship).getSize();
        }
        else // if(place.getType().equals("ship"))
        {
            xCord = ((Hull)ship).getHead().getXCord();
            yCord = ((Hull)ship).getHead().getYCord();
            rotation = ((Hull)ship).getHead().getRotation();
            size = ((Hull)ship).getHead().getSize();
        }

        //if horizontal
        if(rotation == 0)
        {
        	//checks if the entire ship is hit
            for(int x = xCord + 1; x < xCord + size; x++)
                if(!myBoard[yCord][x].getType().equals("hit"))
                    sunken = false;
        }
        //if vertical
        else
        {
        	//checks if the entire ship is hit
            for(int y = yCord + 1; y < yCord + size; y++)
                if(!myBoard[y][xCord].getType().equals("hit"))
                    sunken = false;
        }

        return sunken;
    }

    /**
     * Finds and returns the single difference between myBoard and newPlayerBoard
     * @param newPlayerBoard
     */
    public PosObject singleDifference(PlayerBoard newPlayerBoard)
    {
    	//traverses the entire board
        for(int y = 0; y < 10; y++)
            for(int x =0; x < 10; x++)
            {
            	//the single difference
                if(!myBoard[y][x].toString().equals(newPlayerBoard.displaySpot(x, y)))
                {
                    return new PosObject(x + 1, y + 1, 0, myBoard[y][x].getType());
                }
            }
        //if no differences
        return null;
    }
    /**
     * Returns information of a specified spot
     */
    public String displaySpot(int x, int y)
    {
        return myBoard[y][x].toString();
    }
}