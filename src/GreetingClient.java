// File Name GreetingClient.java
import apcslib.*;
import java.net.*;
import java.io.*;
import java.io.Serializable;

public class GreetingClient {

   public static void main(String [] args) {
      String serverName = "76.88.3.218";
      PlayerBoard board = null;
      int port = 25565;
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);

         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         ObjectOutputStream out = new ObjectOutputStream(outToServer);

         //Send server PlayerBoard object
         out.writeObject(board);

         InputStream inFromServer = client.getInputStream();
         ObjectInputStream in = new ObjectInputStream(inFromServer);

         //Get PlayerBoard object back from server
         board = (PlayerBoard)in.readObject();


         //Loop to print out board to console
         System.out.println("Board:");
         System.out.print("   ");

         for(int y = 1; y <= board.getWidth(); y++)
         {
            System.out.print(y + " ");
         }
         System.out.println();
         for(int y = 0; y < board.getHeight(); y++)
         {
            System.out.print(Format.left(y+1, 3));
            for (int x = 0; x < board.getWidth(); x++)
               System.out.print(board.displaySpot(x, y) + " ");
            System.out.println();
         }

         //Close connection
         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}