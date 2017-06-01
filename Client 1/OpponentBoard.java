/**
 * Created by Johnny Liu
 * 5/4/2017.
 * Uses parent class Board to represent an enemy's board
 */
public class OpponentBoard extends Board 
{
	/**
	 * Default constructor creates a 10x10 board
	 */
    public OpponentBoard()
    {
        super(10,10);
    }
    
    /**
     * Constructs an OpponentBoard based on the PlayerBoard passed
     * @param board
     */
    public OpponentBoard(PlayerBoard board)
    {
        super(board.getWidth(),board.getHeight());
        myBoard = board.getArray();
    }

    /**
     * Updates board at the x and y coordinate specified
     * @param x
     * @param y
     * @return hit
     */
    public boolean receiveAttack(int x, int y)
    {
    	//adjusts x and y to fit the board
        x--;y--;
        boolean hit = false;
        //if not water
        if(myBoard[y][x].getType().equals("hull") || myBoard[y][x].getType().equals("head") || myBoard[y][x].getType().equals("hit"))
        {
        	//updated board to a hit
            myBoard[y][x] = new Hit();
            //updates boolean
            hit = true;
        }
        else
        	//updates board to a miss
            myBoard[y][x] = new Miss();

        return hit;
    }

    /**
     * Tests if a ship at a specified coordinate is sunk or not
     * @param xCord
     * @param yCord
     * @return
     */
    public boolean sunk(int xCord, int yCord)
    {
        boolean sunken = true;
        int rotation;
        int size;
        //saving the spot as a temporary variable 
        Spot place = myBoard[yCord][xCord];
        //type casting place into a ship in order to be able to use the ship methods
        Ship ship = (Ship)place;
        if(place.getType().equals("head"))
        {
        	//gets the information of the spot
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

        //if the ship is horizontal
        if(rotation == 0)
        {
        	//traverses the entire ship
            for(int x = xCord + 1; x < xCord + size; x++)
                if(!myBoard[yCord][x].getType().equals("hit"))
                    sunken = false;
        }
        //if ship is vertical
        else
        {
        	//traverses the entire ship
            for(int y = yCord + 1; y < yCord + size; y++)
                if(!myBoard[y][xCord].getType().equals("hit"))
                    sunken = false;
        }
        return sunken;
    }
    
    /**
     * Returns basic information about the spot
     */
    public String displaySpot(int x, int y)
    {
        if(myBoard[y][x].getType().equals("ship") || myBoard[y][x].getType().equals("head") || myBoard[y][x].getType().equals("hull"))
            return new Water().toString();
        else
            return myBoard[y][x].toString();
    }
}
