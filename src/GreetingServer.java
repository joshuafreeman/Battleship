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
            PlayerBoard board = null;
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());

            //Get PlayerBoard Object from server.
             board = (PlayerBoard)in.readObject();

            //Send PlayerBoard back
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            out.writeObject(board);

            //Close server
            server.close();
            
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