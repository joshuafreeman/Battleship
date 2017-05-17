import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;

/**
 * This class provides a GUI for the Battleship Game developed as a final APCS project.
 * 
 * @author Daniel Trupp
 * @version .5
 */

public class BattleShipGameGUI extends JFrame implements ActionListener 
{
    /** Height of the game frame. */
    private static final int DEFAULT_HEIGHT = 810;
    /** Width of the game frame. */
    private static final int DEFAULT_WIDTH = 1440;
    
    /** Width of background. */
    private static final int BACK_WIDTH = 485;
    /** Height of background. */
    private static final int BACK_HEIGHT = 500;
    
    /** The main panel containing the game components. */
    private JPanel panel;
    
    /** The boards. */
    private PlayerBoard friend;
    private OpponentBoard enemy;
    
    /** Board sizes*/
    private int boardWidth;
    private int boardHeight;
    
    /** The displays. */
    private JLabel[] display;
    
    /** Letters and numbers*/
    JLabel letters[];
    JLabel numbers;
    
    /** Button array of accessable Squares. */
    private JButton[][] validSpaces = new JButton[10][10];
    
    public BattleShipGameGUI(PlayerBoard p1, OpponentBoard p2)
    {
        friend = p1;
        enemy = p2;
        boardHeight = p1.getHeight();
        boardWidth = p1.getWidth();
        
        for (int x = 0; x < boardHeight; x++)
            for (int y = 0; y < boardWidth; y++)
                validSpaces[x][y] = new JButton();
                
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
    
    /**
     * Run the game.
     */
    public void displayGame() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                setVisible(true);
            }
        });
    }
    
    /**
     * Initialize the display.
     */
    private void initDisplay()  {
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        // Calculate number of rows of cards (5 cards per row)
        // and adjust JFrame height if necessary

        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        
        display = new JLabel[2];
        for (int k = 0; k < 2; k++) 
        {
            display[k] = new JLabel();
            panel.add(display[k]);
            display[k].setBounds((k * 720) + 30, 30, BACK_WIDTH, BACK_HEIGHT);
            display[k].addMouseListener(new MyMouseListener());
        }
        
        for (int k = 0; k < 2; k++)
        {
            //Do the buttons
            for (int x = 0; x < boardHeight; x++)
                for (int y = 0; y < boardWidth; y++)
                {
                    JButton butt = validSpaces[x][y];
                    butt.setText((char)(65+y) + "" + (int)(x+1));
                    butt.setContentAreaFilled(false);
                    butt.setBorder(null);
                    butt.setForeground(new Color(0,255,255));
                    panel.add(butt);
                    butt.setBounds(28 + 48 * x, 28 + 50 * y, 55, 55);
                    butt.addActionListener(this);
                } 
                
            letters = new JLabel[boardHeight];
            for (int x = 0; x < boardHeight; x++)
            {
                letters[x] = new JLabel();
                letters[x].setBounds(10 + (k * 1240), 50 * x + 30, 50, 50);
                letters[x].setForeground(Color.BLUE);
                letters[x].setText((char)(x + 65) + "");
                panel.add(letters[x]);
                letters[x].setVisible(true);
            }
            
            numbers = new JLabel();
            numbers.setBounds(50 + (k * 720), -10, 500, 50);
            numbers.setForeground(Color.BLUE);
            
            String text = ""; 
            for (int x = 0; x < boardWidth; x++)
            {
                text += ((x+1));
                for (int y = 1; y < 21 - boardWidth; y++)
                    text += " ";
            }
            
            numbers.setText(text);
            panel.add(numbers);
            numbers.setVisible(true);
        }
        
        pack();
        getContentPane().add(panel);
        panel.setVisible(true); 
    }
    
    /**
     * Draw the display (cards and messages).
     */
    public void repaint() {
        for (int k = 0; k < 2; k++) 
        {
            URL imageURL = getClass().getResource("/Images/ocean.jpeg");
            if (imageURL != null) 
            {
                ImageIcon icon = new ImageIcon(imageURL);
                display[k].setIcon(icon);
                display[k].setVisible(true);
            } else {
                throw new RuntimeException("Image not found");
            }
        }
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
            for (int k = 0; k < 5; k++) 
            {
                if (e.getSource().equals(display[k])) 
                {
                    repaint();
                    return;
                }
            }
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
