import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
/**
 * Write a description of class MenuRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuRunner extends JFrame
{
    public static final int DEFAULT_WIFTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
    public MenuRunner()
    {
        setTitle("Main Menu Test");
        setSize(DEFAULT_WIFTH, DEFAULT_HEIGHT);
        ImageComponent comp = new ImageComponent();
        add(comp);
    }
    
   
}


