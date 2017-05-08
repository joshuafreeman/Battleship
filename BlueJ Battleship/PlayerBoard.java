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
                myBoard[yCord][xCord] = new HeadSpot(name, 0);
                for (int x = xCord + 1; x < xCord + type.getSize(); x++)
                    myBoard[yCord][x] = new Ship();
                return true;
            }
            else
            {
                myBoard[yCord][xCord] = new HeadSpot(name, 1);
                for (int y = yCord + 1; y < yCord + type.getSize(); y++)
                    myBoard[y][xCord] = new Ship();
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
}
