// File Name Client.java
/**
 *  The main client class for the battleship game. The client class is set up parallel to the server.
 *  The class connects to the server, plays against another client in a game of battleship.
 *  A GUI is created to display the game with audio bits.
 *
 *  @author Joshua Freeman
 *  @version 1.0
 *  @date 6/1/17
 */

import chn.util.ConsoleIO;
import java.net.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
public class Client {
    private static PlayerBoard bor;
    private static BattleShipGameGUI gui;
    public static void main(String [] args) {

        String serverName = "76.88.3.218"; //Fixed server IP (My House IP)
        Audio ao = new Audio(); //Create new audio object
        int port = 8080; //Fixed port
        try {
            //Create boards and empty it
            bor = new PlayerBoard(10,10);
            bor.emptyBoard();
            OpponentBoard opp = new OpponentBoard();

            //Start GUI
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
            ao.playBattle(); //Start audio
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

            //Create input and output streams
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            DataInputStream  in = new DataInputStream (inFromServer);


            //Start placing ships
            PosObject positions[] = new PosObject[5];
            //ArrayLists of all the place ships
            ArrayList ships = new ArrayList();
            boolean duplicateShips; //Boolean if there are duplicate ships
            for(int x = 0; x < 5; x ++) {
                duplicateShips = false;

                positions[x] = gui.placeShip(); //Get location the user set for ship

                //Make sure the ship hasn't already ben placed
                for(int count = 0; count < ships.size(); count++)
                {
                    if(ships.get(count).equals(positions[x].getName())) {
                        gui.printLog("You already placed that ship. Try placing a different one.");
                        x--;
                        duplicateShips = true;
                    }
                }
                if(!duplicateShips)
                    //Make sure ship doesn't go off the edge of the board or ontop of another ship
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
            ObjectOutputStream objectOut = new ObjectOutputStream(outToServer);
            objectOut.writeObject(bor);
            ObjectInputStream objectIn = new ObjectInputStream(inFromServer);
            opp = (OpponentBoard)objectIn.readObject();


            //Attacking
            boolean gameOver = false;
            boolean right = true;
            int xCord, yCord;
            boolean sunk;
            ConsoleIO con = new ConsoleIO();
            boolean hit;
            int attacks = 0;

            //While no one has won
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
                objectIn = new ObjectInputStream(inFromServer);
                opp = (OpponentBoard)objectIn.readObject();
                //Get new player board with opponents attacks
                bor = (PlayerBoard)objectIn.readObject();

                PosObject difference = bor.singleDifference(oldBoard);
                if(difference != null)
                    if(difference.getName().equals("hit"))
                        gui.showHit(difference.getX(),difference.getY(),"F");
                    else
                        gui.showMiss(difference.getX(),difference.getY(),"F");

            }
            //If you exit the loop, server says who the winner is
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
            ao.stopBattle();
            ao.playThanks();
            client.close();

        }catch(SocketException e){
            gui.printLog("The opponent has disconnected. Please restart the game to find a new game.");
        }catch(EOFException e){
            gui.printLog("Server restarted. Please restart the game to find a new game.");
        }catch(IOException e) {
            e.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}