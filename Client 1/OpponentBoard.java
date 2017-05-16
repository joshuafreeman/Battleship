/**
 * Created by Josh on 5/4/2017.
 */
public class OpponentBoard extends Board {
    public OpponentBoard()
    {
        super(10,10);
    }
    public OpponentBoard(PlayerBoard board)
    {
        super(board.getWidth(),board.getHeight());
        myBoard = board.getArray();
    }

    public boolean receiveAttack(int x, int y)
    {
        x--;y--;
        boolean sunken = false;
        Spot temp = myBoard[y][x];
        if(myBoard[y][x].getType().equals("hull") || myBoard[y][x].getType().equals("head"))
        {
            myBoard[y][x] = new Hit();
        }
        else
            myBoard[y][x] = new Miss();

        if(temp.getType().equals("hull") || temp.getType().equals("head"))
            sunken = sunk(temp);
        return sunken;
    }

    public boolean sunk(Spot place)
    {
        int xCord;
        int yCord;
        boolean sunken = true;
        int rotation;
        int size;
        Ship ship = (Ship)place;
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


        if(rotation == 0)
        {
            for(int x = xCord + 1; x < xCord + size; x++)
                if(!myBoard[yCord][x].getType().equals("hit"))
                    sunken = false;
        }
        else
        {
            for(int y = yCord + 1; y < yCord + size; y++)
                if(!myBoard[y][xCord].getType().equals("hit"))
                    sunken = false;
        }
        return sunken;
    }
    
}
