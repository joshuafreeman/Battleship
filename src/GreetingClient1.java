// File Name GreetingClient.java
import apcslib.*;
import chn.util.ConsoleIO;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
public class GreetingClient1 {
    private static PlayerBoard bor;
    private static BattleShipGameGUI gui;
    public static void main(String [] args) {
        String serverName = "76.88.3.218";
        int port = 8080;
        try {
            bor = new PlayerBoard(10,10);
            bor.emptyBoard();
            OpponentBoard opp = new OpponentBoard();
            
            JPanel uniPanel = new JPanel()
            {
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                }
            };
     
            StartGUI start = new StartGUI(uniPanel);
            gui = new BattleShipGameGUI(bor, opp, uniPanel);
            gui.displayGame();


            //This start test only works if we call a method in the while loop???
            int test = 0;
            while(!start.clickedStart()) {
                String tester = ((Integer)test).toString();
            }

            Socket client = null;
            //Start connection to server

            gui.printLog("Connecting to Server 1 (" + serverName + ") on port " + port + ".");
            boolean connected = false;
            int numberOfExceptionsThrown = 0;
            while(!connected)
                try {
                client = new Socket(serverName, port);
                connected = true;
            }
            catch(ConnectException e) {
                numberOfExceptionsThrown++;
                if(numberOfExceptionsThrown == 1)
                    gui.printLog("Cannot connect to Server 1 (" + serverName + "). Retrying.");
                else
                    if(numberOfExceptionsThrown % 3 == 0)
                    gui.printLog("Cannot connect to Server 1 (" + serverName + "). Retrying.");
            }

            gui.printLog("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            DataInputStream  in = new DataInputStream (inFromServer);


            //startGame();
            PosObject positions[] = new PosObject[5];
            ArrayList ships = new ArrayList();
            boolean duplicateShips;
            for(int x = 0; x < 5; x ++) {
                duplicateShips = false;
                positions[x] = gui.placeShip();

                for(int count = 0; count < ships.size(); count++)
                {
                    if(ships.get(count).equals(positions[x].getName())) {
                        gui.printLog("You already placed that ship. Try placing a different one.");
                        x--;
                        duplicateShips = true;
                    }
                }
                if(!duplicateShips)
                    if (!bor.setShip(positions[x].getX(), positions[x].getY(), positions[x].getR(), positions[x].getName())) {
                        gui.printLog("Can't place ship there.");
                        x--;
                    } else {
                        gui.showShip(positions[x].getX(), positions[x].getY(), positions[x].getR(), positions[x].getName());
                        ships.add(positions[x].getName());
                    }
            }
            out.writeBoolean(true); //Ready

            //Waiting on opponent
            boolean opponentReady = false;
            while(!opponentReady)
            {
                gui.printLog("Waiting for opponent");
                opponentReady = in.readBoolean();
                gui.printLog("Found opponent!");
            }

            //Turn opponents player board into opponent board
            try{
                ObjectOutputStream objectOut = new ObjectOutputStream(outToServer);
                objectOut.writeObject(bor);
                ObjectInputStream objectIn = new ObjectInputStream(inFromServer);
                opp = (OpponentBoard)objectIn.readObject();

            }catch(Exception x)
            {
                x.printStackTrace();
            }

            //Attacking
            boolean gameOver = false;
            boolean right = true;
            int xCord, yCord;
            boolean sunk;
            ConsoleIO con = new ConsoleIO();
            boolean hit;
            int attacks = 0;
            while(!bor.isEmpty() && !opp.isEmpty()) {
                attacks++;
                opponentReady = false;
                if(attacks == 1)
                    gui.printLog("Waiting for opponent's attacks...");
                opponentReady = in.readBoolean();

                //Testing hitting and missing
                gui.showHit(1,1,"E");
                gui.showMiss(1,1,"E");
                gui.showHit(1,1,"F");
                gui.showMiss(1,1,"F");

                gui.printLog("Please click on the coordinate you would like to attack.");
                Point place = gui.getAttack();
                xCord = (int)place.getX();
                yCord = (int)place.getY();
                //Send attacks
                out.writeInt(xCord);
                out.writeInt(yCord);
                hit = in.readBoolean();
 

                if(hit) {
                    gui.printLog("It's a hit!");
                    gui.showHit(xCord,yCord,"E");
                }
                else {
                    gui.printLog("It's a miss.");
                    gui.showMiss(xCord,yCord,"E");
                }

                sunk = in.readBoolean();
                if(sunk)
                    gui.printLog("You sunk the opponent's ship!");
                
                //Receive where the attacks hit on opponent board
                gui.printLog("Waiting for opponent's attacks...");
                try{
                    ObjectInputStream objectIn = new ObjectInputStream(inFromServer);
                    opp = (OpponentBoard)objectIn.readObject();
                    //Get new player board with opponents attacks
                    bor = (PlayerBoard)objectIn.readObject();
                }catch(Exception z){
                    z.printStackTrace();
                }
            }
            boolean winner = in.readBoolean();

            if(winner)
                gui.printLog("Congrats! You sunk all their battleships.");
            else
                gui.printLog("Sorry. They sunk all your battleships.");
            //Close connection
            client.close();

        }catch(SocketException e){
            gui.printLog("The opponent has disconnected. Please restart the game to find a new game.");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static void startGame()
    {
        ConsoleIO con = new ConsoleIO();
        bor = new PlayerBoard(10, 10);
        int xCord,yCord,boatType,rot;
        boolean right = true;
        bor.emptyBoard();
        System.out.print(" ");

        System.out.println(
                "\n______       _   _   _           _     _" +
                        "\n| ___ \\     | | | | | |         | |   (_)" +
                        "\n| |_/ / __ _| |_| |_| | ___  ___| |__  _ _ __" +
                        "\n| ___ \\/ _` | __| __| |/ _ \\/ __| '_ \\| | '_ \\" +
                        "\n| |_/ / (_| | |_| |_| |  __/\\__ \\ | | | | |_) |" +
                        "\n\\____/ \\__,_|\\__|\\__|_|\\___||___/_| |_|_| .__/" +
                        "\n                                        | |" +
                        "\n                                        |_|"

        );
        System.out.println("Board:");
        System.out.print("   ");
        for(int y = 1; y <= bor.getWidth(); y++)
        {
            System.out.print(y + " ");
        }
        System.out.println();
        for(int y = 0; y < bor.getHeight(); y++)
        {
            System.out.print(Format.left(y+1, 3));
            for (int x = 0; x < bor.getWidth(); x++)
                System.out.print(bor.displaySpot(x, y) + " ");
            System.out.println();
        }

        for(int x = 0; x < 5; x++)
        {
            System.out.println("\nEnter in the number corresponding with the boat you want to place.\n");
            System.out.println("Patrol Boat (Size 2) | Submarine (Size 3) | Destroyer (Size 4) | Battleship (Size 5) | Carrier (Size 6)");
            System.out.println("      Enter 1                Enter 2             Enter 3              Enter 4             Enter 5");
            boatType = con.readInt();
            while(right)
            {
                if(boatType < 1 || boatType > 5)
                {
                    System.out.println("Please type between 1 and 5.");
                    boatType = con.readInt();
                }
                else
                {
                    right = false;
                }
            }
            right = true;
            System.out.print("Enter an X Coordinate: ");
            xCord = con.readInt();
            while(right)
            {
                if(xCord < 1 || xCord > 10)
                {
                    System.out.println("Please type a X cord between 1 and 10.");
                    xCord = con.readInt();
                }
                else
                {
                    right = false;
                }
            }
            right = true;
            System.out.print("Enter a Y Coordinate: ");
            yCord = con.readInt();
            while(right)
            {
                if(yCord < 1 || yCord > 10)
                {
                    System.out.println("Please type a Y cord between 1 and 10.");
                    yCord = con.readInt();
                }
                else
                {
                    right = false;
                }
            }
            right = true;
            System.out.print("Enter Horizontal (1) or Vertical (2): ");
            rot = con.readInt();
            rot--;
            while(right)
            {
                if(rot < 0 || rot > 1)
                {
                    System.out.println("Please learn to type.");
                    rot = con.readInt();
                    rot--;
                }
                else
                {
                    right = false;
                }
            }
            right = true;

            switch(boatType)
            {
                case(1):

                    if(!bor.setShip(xCord,yCord,rot,"Patrol Boat"))
                    {
                        System.out.println("Brah thers es a buot teire alrudy.");
                        x--;
                    }
                    break;
                case(2):
                    if(!bor.setShip(xCord,yCord,rot,"Submarine"))
                    {
                        System.out.println("Brah thers es a buot teire alrudy.");
                        x--;
                    }
                    break;
                case(3):
                    if(!bor.setShip(xCord,yCord,rot,"Destroyer"))
                    {
                        System.out.println("Brah thers es a buot teire alrudy.");
                        x--;
                    }
                    break;
                case(4):
                    if(!bor.setShip(xCord,yCord,rot,"Battleship"))
                    {
                        System.out.println("Brah thers es a buot teire alrudy.");
                        x--;
                    }
                    break;
                case(5):
                    if(!bor.setShip(xCord,yCord,rot,"Carrier"))
                    {
                        System.out.println("Brah thers es a buot teire alrudy.");
                        x--;
                    }
                    break;
            }
            for(int y = 0; y < bor.getHeight(); y++)
            {
                for (int x2 = 0; x2 < bor.getWidth(); x2++)
                    System.out.print(bor.displaySpot(x2, y) + " ");
                System.out.println();
            }
        }
    }
}