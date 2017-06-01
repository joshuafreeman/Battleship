import java.io.Serializable;

/**
 * Created by Johnny Liu
 * 5/2/2017.
 * This class is the parent class on which friendly and enemy boards are based off of
 */
public abstract class Board implements Serializable
{
	/**height of the board*/
    protected int myHeight;
    /**width of the board*/
    protected int myWidth;
    /**keeps track of which spot is which element*/
    protected Spot[][] myBoard;
    
    /**
     * Default constructor creates a 10x10 board 
     */
    public Board()
    {
        myHeight = 10;
        myWidth = 10;
        myBoard = new Spot[myHeight][myWidth];
    }
    
    /**
     * Constructor that creates a board up to 26x26 because there 
     * is only 26 letters in the alphabet 
     * @param width
     * @param height
     */
    public Board(int width, int height)
    {
    	//if board is too big
        if(width > 26 || height > 26)
            throw new ArithmeticException("YODAWGYOUMADEDABOARDTOOBIG");
        myHeight = height;
        myWidth = width;
        myBoard = new Spot[myHeight][myWidth];
    }
    
    /**
     * Empties the board and sets every cell to be water
     */
    public void emptyBoard()
    {
    	//traverses every spot in the board
        for(int row = 0; row < myHeight; row++)
            for(int col = 0; col < myWidth; col++)
                myBoard[row][col] = new Water();
    }        
    
    /**
     * Traverses each spot and returns if there are any spots that are not water
     * @return empt
     */
    public boolean isEmpty()
    {
        boolean empt = true;
        //traverses every spot
        for(int x = 0; x < myHeight; x++)
        {
            for(int y = 0; y < myWidth; y++)
            {
            	//if there is a ship
                if(myBoard[x][y].getType().equals("ship") || myBoard[x][y].getType().equals("hull") || myBoard[x][y].getType().equals("head"))
                {
                    empt = false;
                }
            }
        }
        return empt;
    }

    /**
     * Returns the information at a specified spot on myBoard
     * @param x
     * @param y
     */
    public abstract String displaySpot(int x, int y);
    
    /**
     * Returns a specified spot on myBoard
     * @param x
     * @param y
     * @return myBoard[y][x]
     */
    public Spot getSpot(int x, int y)
    {
    	//decrements both in order to correspond to the board 
        x--;y--;
        return myBoard[y][x];
    }
    
    /**
     * Returns the height of the board
     * @return myHeight
     */
    public int getHeight()
    {
        return myHeight;
    }
    
    /**
     * Returns the width of the board
     * @return myWidth
     */
    public int getWidth()
    {
        return myWidth;
    }
    
    /**
     * Prints and returns the number of rows and columns in the board 
     */
    public String toString()
    {
        System.out.print("row: " + myBoard.length + " col: " + myBoard[0].length);
        return ("Rows: " + myHeight + " Columns: " + myWidth);
    }
    
    /**
     * Returns the entire board
     * @return myBoard
     */
    protected Spot[][] getArray()
    {
        return myBoard;
    }
}
