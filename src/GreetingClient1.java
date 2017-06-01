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
        Audio ao = new Audio();
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
            ao.playBattle();
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
                {
                    gui.printLog("You sunk the opponent's ship!");
                    ao.playSunk();
                }
                //Receive where the attacks hit on opponent board
                gui.printLog("Waiting for opponent's attacks...");
                PlayerBoard oldBoard = bor;
                try{
                    ObjectInputStream objectIn = new ObjectInputStream(inFromServer);
                    opp = (OpponentBoard)objectIn.readObject();
                    //Get new player board with opponents attacks
                    bor = (PlayerBoard)objectIn.readObject();
                }catch(Exception z){
                    z.printStackTrace();
                }

                PosObject difference = bor.singleDifference(oldBoard);
                if(difference != null)
                    if(difference.getName().equals("hit"))
                        gui.showHit(difference.getX(),difference.getY(),"F");
                    else
                        gui.showMiss(difference.getX(),difference.getY(),"F");

            }
            boolean winner = in.readBoolean();

            if(winner)
            {
                gui.printLog("Congrats! You sunk all their battleships.");
                ao.playWon();
            }
            else
            {
                gui.printLog("Sorry. They sunk all your battleships.");
                ao.playLoss();
            }    
            //Close connection
            ao.playThanks();
            client.close();

        }catch(SocketException e){
            gui.printLog("The opponent has disconnected. Please restart the game to find a new game.");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}