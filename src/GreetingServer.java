// File Name GreetingServer.java
import java.net.*;
import java.io.*;

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   private int x,y;
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
<<<<<<< HEAD:src/GreetingServer.java
            ObjectInputStream in = new ObjectInputStream(server.getInputStream());

            //Get PlayerBoard Object from server.
             board = (PlayerBoard)in.readObject();

            //Send PlayerBoard back
            ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
            out.writeObject(board);

            //Close server
=======
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            System.out.println("Waiting for second client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server2 = serverSocket.accept();
            
            System.out.println("Just connected to " + server2.getRemoteSocketAddress());
            DataInputStream in2 = new DataInputStream(server2.getInputStream());
            
            
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            DataOutputStream out2 = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress());
            out.writeUTF("Please type in a X cordinate to shoot");
            x = in.readInt();
            out.writeUTF("Please type in a Y cordinate to shoot");
            y = in.readInt();
            System.out.println(x + "   " + y);
            out2.writeUTF(" You are getting attacked at:");
            out2.writeInt(x);
            out2.writeInt(y);
>>>>>>> origin/master:ServerTest/GreetingServer.java
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
      int port = 25567;
      try {
         Thread t = new GreetingServer(port);
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }

   }
}