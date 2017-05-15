// File Name GreetingClient.java
import java.net.*;
import java.io.*;
import chn.util.*;
public class GreetingClient {
    
   public static void main(String [] args) {
      String serverName = "localhost";
      int port = 25567;
      ConsoleIO con = new ConsoleIO();
      try {
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
         System.out.println(in.readUTF());
         int x = con.readInt();
         out.writeInt(x);
         
         System.out.println(in.readUTF());
         int y = con.readInt();
         out.writeInt(y);
         
         
         client.close();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}