// File Name GreetingClient.java
import apcslib.*;
import chn.util.ConsoleIO;
import java.net.*;
import java.io.*;

public class GreetingClient1 {
    private static PlayerBoard bor;
    public static void main(String [] args) {
        String serverName = "76.88.3.218";
        int port = 25565;
        try {
            bor = new PlayerBoard(10,10);
            OpponentBoard opp = new OpponentBoard();
            //Start connection to server
            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to " + client.getRemoteSocketAddress());
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            DataInputStream  in = new DataInputStream (inFromServer);


            startGame();
            out.writeBoolean(true); //Ready

            //Waiting on opponent
            boolean opponentReady = false;
            while(!opponentReady)
            {
                System.out.println("Waiting for opponent");
                opponentReady = in.readBoolean();
                System.out.println("Found opponent!");
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
            ConsoleIO con = new ConsoleIO();
            boolean hit;
            while(bor.isEmpty() && opp.isEmpty()) {
                System.out.print("Please type a X cord to shoot: ");
                xCord = con.readInt();
                while (right) {
                    if (xCord < 1 || xCord > 10) {
                        System.out.print("Please type a X cord between 1 and 10: ");
                        xCord = con.readInt();
                    } else {
                        right = false;
                    }
                }
                right = true;
                System.out.print("Please type a Y cord to shoot: ");
                yCord = con.readInt();
                while (right) {
                    if (yCord < 1 || yCord > 10) {
                        System.out.print("Please type a Y cord between 1 and 10: ");
                        yCord = con.readInt();
                    } else {
                        right = false;
                    }
                }
                opponentReady = false;
                while(!opponentReady)
                {
                    System.out.println("Waiting for opponent's attacks...");
                    opponentReady = in.readBoolean();

                    //Send attacks
                    out.writeInt(xCord);
                    out.writeInt(yCord);
                    hit = in.readBoolean();

                    if(hit)
                        System.out.println("It's a hit!");
                    else
                        System.out.println("It's a miss.");

                    //Receive where the attacks hit on opponent board
                    try{
                        ObjectInputStream objectIn = new ObjectInputStream(inFromServer);
                        opp = (OpponentBoard)objectIn.readObject();
                        //Get new player board with opponents attacks
                        bor = (PlayerBoard)objectIn.readObject();
                    }catch(Exception z){
                        z.printStackTrace();
                    }
                }
            }

            //Close connection
            client.close();

        }catch(IOException e) {
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