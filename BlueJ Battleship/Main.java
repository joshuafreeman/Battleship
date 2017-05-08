public class Main {

    public static void main(String[] args)
    {
        PlayerBoard bor = new PlayerBoard(10, 10);
        bor.emptyBoard();
        for(int y = 0; y < bor.getHeight(); y++)
        {
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }
        System.out.println();
        bor.setShip(1, 1, 0, "Battleship");
        bor.setShip(4, 4, 1, "Submarine");
        System.out.println(bor.setShip(6, 6, 0, "Carrier"));
        System.out.println(bor.setShip(6,6,0, "Patrol Boat")); //Test to see if you can place a ship on another ship
        System.out.println(bor.setShip(10,10,0, "Battleship")); //Test to see if you can place a ship off array
        System.out.println(bor.rotateShip(4,4));
        System.out.println(bor.rotateShip(6,6));
        for(int y = 0; y < bor.getHeight(); y++)
        {
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }
        // write your code here

        //test
    }
}
