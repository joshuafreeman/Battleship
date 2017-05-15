// File Name GreetingClient.java
import java.net.*;
import java.io.*;

public class GClient2 {

   public static void main(String [] args) {
      String serverName = "localhost";
      int port = 25567;
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         int x = in.readInt();
         int u = in.readInt();
         System.out.println(x + "   " + u);
         
         
         
         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}