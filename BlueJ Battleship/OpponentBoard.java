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
    
    public boolean recieveAttack(int x, int y)
    {
        x--;y--;
        boolean hit = false;
        
        if(myBoard[y][x].getType().equals("Ship") || myBoard[y][x].getType().equals("HeadSpot"))
        {
            hit = true;
            myBoard[y][x] = new Hit();
        }
        else
            myBoard[y][x] = new Miss();
        return hit;
    }
    
}
