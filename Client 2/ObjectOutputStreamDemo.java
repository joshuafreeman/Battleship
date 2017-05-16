import java.io.*;

public class ObjectOutputStreamDemo {

    public static void main(String[] args) {
        String s = "Hello world!";
        int i = 897648764;

        try {
            PlayerBoard board = new PlayerBoard();
            // create a new file with an ObjectOutputStream
            FileOutputStream out = new FileOutputStream("test.txt");
            ObjectOutputStream oout = new ObjectOutputStream(out);

            // write something in the file
            oout.writeObject(board);
            oout.writeObject(i);

            // close the stream
            oout.close();

            // create an ObjectInputStream for the file we created before
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.txt"));

            // read and print what we wrote before
            System.out.println("" + (Board) ois.readObject());
            System.out.println("" + ois.readObject());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}