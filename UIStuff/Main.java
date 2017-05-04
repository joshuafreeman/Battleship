 

public class Main {

    public static void main(String[] args)
    {
        Board bor = new Board(10, 10);
        bor.emptyBoard();
        for(int y = 0; y < bor.getHeight(); y++)
        {
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }
        System.out.println();
        bor.setShip(1, 1, 0, new Ship("Battleship"));
        bor.setShip(4, 4, 1, new Ship("Submarine"));
        bor.setShip(6, 6, 0, new Ship("Scout"));

        for(int y = 0; y < bor.getHeight(); y++)
        {
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }
	// write your code here
    }
}
