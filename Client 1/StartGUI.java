import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;

/**
 * This is the GUI for the start menu for the game, because
 * "What kind of a game doesen't have a start menu!?".
 * 
 * @author Daniel Trupp 
 * @version 1.0
 */
public class StartGUI extends JFrame implements ActionListener
{
    /** Height of the game frame. */
    private static final int DEFAULT_HEIGHT = 810;
    /** Width of the game frame. */
    private static final int DEFAULT_WIDTH = 1300;
    
    private JPanel panel;
    private JLabel logo;
    private JLabel background;
    private JButton start;
    
    /**
     * Constructor for objects of class StartGUI
     */
    public StartGUI(JPanel pan)
    {
        panel = pan;
        
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }

    public void displayGame() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    
    private void initDisplay()  
    {
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        
        /**
        logo = new JLabel();
        panel.add(logo);
        logo.setBounds(160, -20,1440,400);
        */
        start = new JButton("Start");
        start.setFont(new Font("SansSerif", Font.BOLD, 25));
        panel.add(start);
        start.setBounds(500, 480, 240, 80);
        start.setVisible(true);
        start.addActionListener(this);
                
        background = new JLabel();
        panel.add(background);
        background.setBounds(0, -140, 1800, 1440);
        
        pack();
        getContentPane().add(panel);
        panel.setVisible(true); 
    }
    
    public void repaint() 
    {
        /**
        URL imageURL = getClass().getResource("/Images/logo.jpg");
        ImageIcon icon = new ImageIcon(imageURL);
        logo.setIcon(icon);
        logo.setVisible(true);
        */
        URL backgroundURL = getClass().getResource("/Images/Title.png");
        ImageIcon bicon = new ImageIcon(backgroundURL);
        background.setIcon(bicon);
        background.setVisible(true);
        
        pack();
        panel.repaint();
    }
    
    /**
     * Deal with the user clicking on something other than a button or a card.
     */
    private void signalError() {
        Toolkit t = panel.getToolkit();
        t.beep();
    }
    
    /**
     * Respond to a button click (on either the "Replace" button
     * or the "Restart" button).
     * @param e the button click action event
     */
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource().equals(start)) 
        {
            logo.setVisible(false);
            background.setVisible(false);
            start.setVisible(false);
        }
    }
    
    /**
     * Receives and handles mouse clicks.  Other mouse events are ignored.
     */
    private class MyMouseListener implements MouseListener 
    {

        /**
         * Handle a mouse click on a card by toggling its "selected" property.
         * Each card is represented as a label.
         * @param e the mouse event.
         */
        public void mouseClicked(MouseEvent e) {
            signalError();
        }

        /**
         * Ignore a mouse exited event.
         * @param e the mouse event.
         */
        public void mouseExited(MouseEvent e) {
        }

        /**
         * Ignore a mouse released event.
         * @param e the mouse event.
         */
        public void mouseReleased(MouseEvent e) {
        }

        /**
         * Ignore a mouse entered event.
         * @param e the mouse event.
         */
        public void mouseEntered(MouseEvent e) {
        }

        /**
         * Ignore a mouse pressed event.
         * @param e the mouse event.
         */
        public void mousePressed(MouseEvent e) {
        }
    }
}
