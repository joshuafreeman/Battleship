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
            OpponentBoard player1Opp;
            OpponentBoard player2Opp;
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
            System.out.println("Waiting for both players to be ready....");
            while(!gameReadyP1 || !gameReadyP2 )
            {
               gameReadyP1 = inP1.readBoolean();
               gameReadyP2 = inP2.readBoolean();
            }

            outP1.writeBoolean(true); //Player 2 is ready
            outP2.writeBoolean(true); //Player 1 is ready

            try{ //OBJECT STREAMS NEED TO BE IN TRY CATCH OR IT WONT WORK!!!!!!!!!!!???????????????!!!!!!!!!!!!!!!!!
               ObjectInputStream objectInP1 = new ObjectInputStream(server1.getInputStream());
               ObjectInputStream objectInP2 = new ObjectInputStream(server2.getInputStream());
               player1Board = (PlayerBoard)objectInP1.readObject();
               player2Board = (PlayerBoard)objectInP2.readObject();
            }catch(Exception e)
            {
               e.printStackTrace();
            }


            while(!player1Board.isEmpty() && !player2Board.isEmpty())
            {
               outP1.writeBoolean(true); //P1 can attack
               xCordP1 = inP1.readInt();
               yCordP1 = inP1.readInt();
               outP2.writeBoolean(true); //P2 can attack
               xCordP2 = inP2.readInt();
               yCordP2 = inP2.readInt();

               outP1.writeBoolean(player2Board.receiveAttack(xCordP1, yCordP1));
               outP2.writeBoolean(player1Board.receiveAttack(xCordP2, yCordP2));

               player1Opp = new OpponentBoard(player2Board);
               player2Opp = new OpponentBoard(player1Board);

               try{
                  ObjectOutputStream objectOutP1 = new ObjectOutputStream(server1.getOutputStream());
                  ObjectOutputStream objectOutP2 = new ObjectOutputStream(server2.getOutputStream());
                  objectOutP1.writeObject(player2Opp);
                  objectOutP2.writeObject(player1Opp);
                  objectOutP1.writeObject(player1Board);
                  objectOutP2.writeObject(player2Board);
               }catch(Exception z)
               {
                  z.printStackTrace();
               }
            }

            server1.close();

         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = 25565;
      try {
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }

   }
}