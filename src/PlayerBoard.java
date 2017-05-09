/**
 * Created by Josh on 5/4/2017.
 */
public class PlayerBoard extends Board{

    public PlayerBoard()
    {
        super(10,10);
    }
    public PlayerBoard(int x, int y)
    {
        super(x,y);
    }
    /**
     * Places an initial ship
     * @param xCord
     * @param yCord
     * @param rotation
     * @param name
     * @return
     */
    public boolean setShip(int xCord, int yCord, int rotation, String name)
    {
        HeadSpot type = new HeadSpot(name);
        xCord--;yCord--; //Make bounds 0-9 for array
        boolean canSet = canPlace(xCord, yCord, rotation, name);

        if(canSet)
        {
            if (rotation == 0)
            {
                myBoard[yCord][xCord] = new HeadSpot(name, 0, xCord, yCord);
                //((HeadSpot)myBoard[yCord][xCord]).setHead((HeadSpot)myBoard[yCord][xCord]);
                for (int x = xCord + 1; x < xCord + type.getSize(); x++)
                    myBoard[yCord][x] = new Hull((HeadSpot)myBoard[yCord][xCord]);
                return true;
            }
            else
            {
                myBoard[yCord][xCord] = new HeadSpot(name, 1, xCord, yCord);
                //((HeadSpot)myBoard[yCord][xCord]).setHead((HeadSpot)myBoard[yCord][xCord]);
                for (int y = yCord + 1; y < yCord + type.getSize(); y++)
                    myBoard[y][xCord] = new Hull((HeadSpot)myBoard[yCord][xCord]);
                return true;
            }
        }
        else
            return false;
    }

    public boolean rotateShip(int xCord, int yCord)
    {
        xCord--;yCord--;//Make bounds 0-9 for array

        if(myBoard[yCord][xCord].getType().equals("water"))
            return false;

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
            if(newRotation == 0)
            {
                for (int x = xCord + 1; x < xCord + head.getSize(); x++)
                    myBoard[yCord][x] = new Ship();
                for (int y = yCord + 1; y < xCord + head.getSize();y++)
                    myBoard[y][xCord] = new Water();
            }
            else
            {
                for (int y = yCord + 1; y < xCord + head.getSize(); y++)
                    myBoard[y][xCord] = new Ship();
                for (int x = xCord + 1; x < xCord + head.getSize(); x++)
                    myBoard[yCord][x] = new Water();
            }
        }

        return rotate;
    }
    private boolean canRotate(int xCord, int yCord, int rotation, String name)
    {
        HeadSpot type = new HeadSpot(name);
        boolean rotate = true;
        if(rotation == 0)
        {
            if (xCord + type.getSize() > myBoard[0].length)
                rotate = false;
            else
                for(int x = xCord + 1; x < xCord + type.getSize(); x++)
                    if(!myBoard[yCord][x].getType().equals("water"))
                        rotate = false;
        }
        else
        {
            if (yCord + type.getSize() > myBoard.length)
                rotate = false;
            else
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
     * @return
     */
    private boolean canPlace(int xCord, int yCord, int rotation, String name)
    {
        HeadSpot type = new HeadSpot(name);
        boolean canSet = true;
        if(rotation == 0)
        {
            if (xCord + type.getSize() > myBoard[0].length)
                canSet = false;
            else
                for(int x = xCord; x < xCord + type.getSize(); x++)
                    if(!myBoard[yCord][x].getType().equals("water"))
                        canSet = false;
        }
        else
        {
            if (yCord + type.getSize() > myBoard.length)
                canSet = false;
            else
                for(int y = yCord; y < yCord + type.getSize(); y++)
                    if(!myBoard[y][xCord].getType().equals("water"))
                        canSet = false;
        }
        return canSet;
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
    
    public boolean sendAttack(int x, int y, OpponentBoard board)
    {
        x--;y--;
        boolean hit = false;
        
        hit = board.receiveAttack(x, y);
        return hit;
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
