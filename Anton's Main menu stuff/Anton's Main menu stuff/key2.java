import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class key2 {
  public static void main(String[] args) {
    JFrame aWindow = new JFrame("This is the Window Title");

    aWindow.setBounds(50, 100, 300, 300);
    aWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JTextField typingArea = new JTextField(20);
    typingArea.addKeyListener(new KeyListener() {

      /** Handle the key typed event from the text field. */
      public void keyTyped(KeyEvent e) {
        displayInfo(e, "KEY TYPED: ");
      }

      /** Handle the key pressed event from the text field. */
      public void keyPressed(KeyEvent e) {
        displayInfo(e, "KEY PRESSED: ");
      }

      /** Handle the key released event from the text field. */
      public void keyReleased(KeyEvent e) {
        displayInfo(e, "KEY RELEASED: ");
      }

      protected void displayInfo(KeyEvent e, String s) {
        String keyString, modString, tmpString, actionString, locationString;

        // You should only rely on the key char if the event
        // is a key typed event.
        int id = e.getID();
        if (id == KeyEvent.KEY_TYPED) {
          char c = e.getKeyChar();
          keyString = "key character = '" + c + "'";
        } else {
          int keyCode = e.getKeyCode();
          keyString = "key code = " + keyCode + " (" + KeyEvent.getKeyText(keyCode) + ")";
        }

        int modifiers = e.getModifiersEx();
        modString = "modifiers = " + modifiers;
        tmpString = KeyEvent.getModifiersExText(modifiers);
        if (tmpString.length() > 0) {
          modString += " (" + tmpString + ")";
        } else {
          modString += " (no modifiers)";
        }

        actionString = "action key? ";
        if (e.isActionKey()) {
          actionString += "YES";
        } else {
          actionString += "NO";
        }

        locationString = "key location: ";
        int location = e.getKeyLocation();
        if (location == KeyEvent.KEY_LOCATION_STANDARD) {
          locationString += "standard";
        } else if (location == KeyEvent.KEY_LOCATION_LEFT) {
          locationString += "left";
        } else if (location == KeyEvent.KEY_LOCATION_RIGHT) {
          locationString += "right";
        } else if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
          locationString += "numpad";
        } else { // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
          locationString += "unknown";
        }

        System.out.println(keyString);
        System.out.println(modString);
        System.out.println(actionString);
        System.out.println(locationString);
      }

    });
    aWindow.add(typingArea);
    aWindow.setVisible(true);
  }
}