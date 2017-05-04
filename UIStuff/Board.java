 

/**
 * Created by Josh on 5/2/2017.
 */
public class Board
{
    protected int myHeight;
    protected int myWidth;
    protected Spot[][] myBoard;

    public Board(int width, int height)
    {
        myHeight = height;
        myWidth = width;
        myBoard = new Spot[myHeight][myWidth];
    }
    public void emptyBoard()
    {
        for(int row = 0; row < myHeight; row++)
            for(int col = 0; col < myWidth; col++)
                myBoard[row][col] = new Spot("water");
    }
    public boolean setShip(int xCord, int yCord, int rotation, Ship type)
    {
        boolean canSet = true;
        xCord--;yCord--;
        if(rotation == 0)
            if(xCord + type.getSize() >= myBoard[0].length)
                canSet = false;
        else
            if(yCord + type.getSize() >= myBoard.length)
                canSet = false;
        if(canSet)
        {
            if (rotation == 0)
            {
                for (int x = xCord; x < xCord + type.getSize(); x++)
                    myBoard[yCord][x] = new Spot("ship");
                return true;
            }
            else
            {
                for (int y = yCord; y < yCord + type.getSize(); y++)
                    myBoard[y][xCord] = new Spot("ship");
                return true;
            }
        }
        else
            return false;
    }

    public String displaySpot(int x, int y)
    {
        return myBoard[y][x].toString();
    }
    public Spot[][] getBoard()
    {
        return myBoard;
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

}
