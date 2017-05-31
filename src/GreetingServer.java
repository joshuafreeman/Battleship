// File Name GreetingServer.java
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   public GreetingServer(int port) throws IOException {
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(10000);  //Don't timeout
   }

   public void run() {
      while(true) {
         try {
            PlayerBoard player1Board = null;
            PlayerBoard player2Board = null;
            OpponentBoard player1Opp = null;
            OpponentBoard player2Opp = null;
            int xCordP1 = -1, xCordP2 = -1, yCordP1 = -1, yCordP2 = -1;
            //Player 1
            System.out.println("Waiting for Player 1 on port " +  serverSocket.getLocalPort() + "...");
            Socket server1 = serverSocket.accept();

            System.out.println("Just connected to Player 1 " + server1.getRemoteSocketAddress());
            DataInputStream inP1 = new DataInputStream(server1.getInputStream());
            DataOutputStream outP1 = new DataOutputStream(server1.getOutputStream());


            //Player 2
            System.out.println("Waiting for Player 2 on port " + serverSocket.getLocalPort() + "...");
            Socket server2 = serverSocket.accept();

            System.out.println("Just connected to Player 2 " + server2.getRemoteSocketAddress());
            DataInputStream inP2 = new DataInputStream(server2.getInputStream());
            DataOutputStream outP2 = new DataOutputStream(server2.getOutputStream());

            boolean gameReadyP1 = false;
            boolean gameReadyP2 = false;
            boolean player1Won = false;
            boolean player2Won = false;
            System.out.println("Waiting for both players to be ready...");
            while(!gameReadyP1 || !gameReadyP2 )
            {
               gameReadyP1 = inP1.readBoolean();
               gameReadyP2 = inP2.readBoolean();
            }

            outP1.writeBoolean(true); //Player 2 is ready
            outP2.writeBoolean(true); //Player 1 is ready

            System.out.println("Sending PlayerBoards to players.");
            try{ //OBJECT STREAMS NEED TO BE IN TRY CATCH OR IT WONT WORK!!!!!!!!!!!???????????????!!!!!!!!!!!!!!!!!
               ObjectInputStream objectInP1 = new ObjectInputStream(server1.getInputStream());
               ObjectInputStream objectInP2 = new ObjectInputStream(server2.getInputStream());
               player1Board = (PlayerBoard)objectInP1.readObject();
               player2Board = (PlayerBoard)objectInP2.readObject();
               ObjectOutputStream objectOutP1 = new ObjectOutputStream(server1.getOutputStream());
               ObjectOutputStream objectOutP2 = new ObjectOutputStream(server2.getOutputStream());
               player1Opp = new OpponentBoard(player2Board);
               player2Opp = new OpponentBoard(player1Board);
               objectOutP1.writeObject(player1Opp);
               objectOutP2.writeObject(player2Opp);
            }catch(Exception e)
            {
               e.printStackTrace();
            }
            Spot temp1, temp2;
            System.out.println("Players are attacking.");
            //RECEIVE ATTACK DOESN'T TELL IF ITS A HIT, ONLY SUNKEN SHIP
            while(!player1Board.isEmpty() && !player2Board.isEmpty())
            {
               outP1.writeBoolean(true); //P1 can attack
               xCordP1 = inP1.readInt();
               yCordP1 = inP1.readInt();
               temp1 = player2Board.getSpot(xCordP1, yCordP1);
               outP1.writeBoolean(player2Board.receiveAttack(xCordP1, yCordP1));
               if(player2Board.receiveAttack(xCordP1, yCordP1))
                  System.out.println("Player 1 hit Player 2's boat");
               else
                  System.out.println("Player 1 missed Player 2's boats");
                  
               if((temp1.myType.equals("hull") || temp1.myType.equals("head") || temp1.myType.equals("ship")) && player2Board.sunk(temp1))
               {
                   outP1.writeBoolean(player2Board.sunk(temp1));
                   if(player1Board.isEmpty())
                        player1Won = true;
               }
               else
                  outP1.writeBoolean(false);

               outP2.writeBoolean(true); //P2 can attack
               xCordP2 = inP2.readInt();
               yCordP2 = inP2.readInt();
               
               temp2  = player1Board.getSpot(xCordP2, yCordP2);
               outP2.writeBoolean(player1Board.receiveAttack(xCordP2, yCordP2));
               if(player1Board.receiveAttack(xCordP2, yCordP2))
                  System.out.println("Player 2 hit Player 1's boat");
               else
                  System.out.println("Player 2 missed Player 1's boats");
               if((temp2.myType.equals("hull") || temp2.myType.equals("head") || temp2.myType.equals("ship")) && player1Board.sunk(temp2))
               {
                   outP2.writeBoolean(player1Board.sunk(temp2));
                   if(player2Board.isEmpty() && !player1Won)
                        player2Won = true;
               }    
               else
                  outP2.writeBoolean(false);
               

               player1Opp = new OpponentBoard(player2Board);
               player2Opp = new OpponentBoard(player1Board);

               try{
                  ObjectOutputStream objectOutP1 = new ObjectOutputStream(server1.getOutputStream());
                  ObjectOutputStream objectOutP2 = new ObjectOutputStream(server2.getOutputStream());

                  objectOutP1.writeObject(player1Opp);
                  objectOutP1.writeObject(player1Board);

                  objectOutP2.writeObject(player2Opp);
                  objectOutP2.writeObject(player2Board);
               }catch(Exception z)
               {
                  z.printStackTrace();
               }
            }
            //Game over
            if(player1Won)
            {
               System.out.println("Player 1 lost");
               outP1.writeBoolean(false);
               outP2.writeBoolean(true);
            }
            else
                if(player2Won)
                {
                   System.out.println("Player 2 lost");
                   outP1.writeBoolean(true);
                   outP2.writeBoolean(false);
                }

            server1.close();

         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(SocketException s) {
            System.out.println("One of the players disconnected. Restarting server.");

         }catch(EOFException e){
            System.out.println("Got end of file.");
         }
         catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = 8080;
      try {
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }

   }
}