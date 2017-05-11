/**
 * Created by Josh on 5/2/2017.
 */
public abstract class Board
{
    protected int myHeight;
    protected int myWidth; 
    protected Spot[][] myBoard;
    
    public Board()
    {
        myHeight = 10;
        myWidth = 10;
        myBoard = new Spot[myHeight][myWidth];
    }
    
    public Board(int width, int height)
    {
        myHeight = height;
        myWidth = width;
        myBoard = new Spot[myHeight][myWidth];
    }
    
    /**
     * Empties the board and sets every cell to be water
     */
    public void emptyBoard()
    {
        for(int row = 0; row < myHeight; row++)
            for(int col = 0; col < myWidth; col++)
                myBoard[row][col] = new Water();
    }        
    
    public boolean isEmpty()
    {
        boolean empt = true;
        for(int x = 0; x < myHeight; x++)
        {
            for(int y = 0; y < myWidth; y++)
            {
                if(myBoard[x][y].getType().equals("ship") || myBoard[x][y].getType().equals("hull") || myBoard[x][y].getType().equals("head"))
                {
                    empt = false;
                }
            }
        }
        return empt;
    }
    
    public String displaySpot(int x, int y)
    {
        return myBoard[y][x].toString();
    }
    
    public int getHeight()
    {
        return myHeight;
    }
    
    public int getWidth()
    {
        return myWidth;
    }
    
    public String toString()
    {
        System.out.print("row: " + myBoard.length + " col: " + myBoard[0].length);
        return ("Rows: " + myHeight + " Columns: " + myWidth);
    }
    
    protected Spot[][] getArray()
    {
        return myBoard;
    }
}
