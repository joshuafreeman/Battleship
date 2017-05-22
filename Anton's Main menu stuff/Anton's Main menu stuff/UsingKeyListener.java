import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class UsingKeyListener {
    public static void main(String[] args) {
        JFrame aWindow = new JFrame("This is the Window Title");
        
        aWindow.setBounds(50, 100, 300, 300);
        aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTextField typingArea = new JTextField(20);
        typingArea.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                displayInfo(e, "KEY TYPED: ");
            }
            
            /** Handle the key pressed event from the text field. */
            public void keyPressed(KeyEvent e) {
                displayInfo(e, "KEY PRESSED: ");
            }
            
            /** Handle the key released event from the text field. */
            public void keyReleased(KeyEvent e) {
                //displayInfo(e, "KEY RELEASED: ");
            }
            
            protected void displayInfo(KeyEvent e, String s) {
                String keyString, modString, tmpString, actionString, locationString;
                
                // You should only rely on the key char if the event
                // is a key typed event.
                int i =1;
                if (KeyEvent.KEY_TYPED == 'r') {
                    System.out.println("hi");
                char c = e.getKeyChar();
                keyString = "key character = '" + c + "'";
                } else {
                int keyCode = e.getKeyCode();
                keyString = "key code = " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + "))";
                }
            }
        });
    
        aWindow.add(typingArea);
        aWindow.setVisible(true);
    }
}