import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;

/**
 * Write a description of class MenuGUI here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuGUI
{
    /**
     * Here is the main() method
     * 
     */
    public static void main(String[] args)
    {
        EventQueue.involeLater(new Runnable()
        {
            public void run()
            {
                MenuGUI menu = new MenuGUI();    
                menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                menu.setVisible(true);
            }    
        }  );  
    }
}
