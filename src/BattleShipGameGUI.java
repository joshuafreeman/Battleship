import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
import java.awt.event.MouseAdapter;

/**
 * This class provides a GUI for the Battleship Game developed as a final APCS project.
 * 
 * @author Daniel Trupp
 * @version .5
 */

public class BattleShipGameGUI extends JFrame implements ActionListener, KeyListener
{
    /** Height of the game frame. */
    private static final int DEFAULT_HEIGHT = 810;
    /** Width of the game frame. */
    private static final int DEFAULT_WIDTH = 1300;
    
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
    private JLabel[] boardLable;
    private JLabel[][] grids;
    private JLabel background;
    
    /** Letters and numbers*/
    private JLabel letters[];
    private JLabel numbers[];
    
    /** Button array of accessable Squares. */
    private JButton[][] validSpaces;
    
    /** Selectable Ships */
    private JButton[] ships;
    
    private Color col;
    private JTextArea bA;
    private JScrollPane battleLog;

    private int attX = -1, attY = -1;
    private int placeX = -1, placeY = -1, placeR = 0;
    private boolean selectable = false;
    private String shipName = "";
    private int firstClick = 0;
    private ImageIcon mouseIcon;
    
    private int num = 0;
    private JButton[] ship = new JButton[5];

    
    public BattleShipGameGUI(PlayerBoard p1, OpponentBoard p2, JPanel pan)
    {
        panel = pan;
        friend = p1;
        enemy = p2;
        boardHeight = p1.getHeight();
        boardWidth = p1.getWidth();
        
        validSpaces = new JButton[boardHeight][boardWidth * 2];
        grids = new JLabel[boardHeight + 2][boardWidth * 4];
        
        for (int x = 0; x < boardHeight; x++)
            for (int y = 0; y < boardWidth * 2; y++)
            {
                validSpaces[x][y] = new JButton();
            }
                
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
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));
        panel.addKeyListener(this);
        panel.setFocusable(true);
        
        boardLable = new JLabel[2];

        for(int k = 0; k < 2; k++)
            for (int x = 0; x < boardHeight; x++)
            {
                for (int y = 0; y < boardWidth; y++)
                {
                    JButton butt = validSpaces[x][(boardWidth * k) + y];
                    String text;
                    if (k > 0 )
                    {
                        text = "F";
                        butt.setActionCommand("Place Ship");
                        butt.addMouseListener(new MyMouseListener());
                    }
                    else
                    {
                        text = "E";
                        butt.setActionCommand("Send Attack");
                    }
                        
                    text += (char)(65+y) + "" + (int)(x+1);
                    butt.setText(text);
                    butt.setContentAreaFilled(false);
                    butt.setBorder(null);
                    butt.setForeground(new Color(0,255,0,0));
                    panel.add(butt);
                    butt.setBounds(28 + 48 * x + (k * 720), 28 + 50 * y, 55, 55);
                    butt.addActionListener(this);
                }
            }

        for (int x = 0; x < 5; x++)
        {
            ship[x] = new JButton();
            panel.add(ship[x]);
        }
        
        for(int k = 0; k < 2; k++)    
            for (int x = 0; x < boardHeight + 1; x++)
                for (int y = 0; y <= boardWidth * 2; y++)
                {
                    grids[x][y] = new JLabel();
                    grids[x][y].setBounds(28 + 48 * x + (k * 720), 30, 2, 500);
                    grids[x][y].setBackground(new Color(225,140,0));
                    grids[x][y].setOpaque(true);
                    panel.add(grids[x][y]);
                    grids[x][y].setVisible(true);
                    
                    grids[x][y + boardWidth] = new JLabel();
                    grids[x][y + boardWidth].setBounds(28 + (k * 720), 28 + 50 * x, 482, 2);
                    grids[x][y + boardWidth].setBackground(new Color(225,140,0));
                    grids[x][y + boardWidth].setOpaque(true);
                    panel.add(grids[x][y + boardWidth]);
                    grids[x][y + boardWidth].setVisible(true);
                }
                
        for (int k = 0; k < 2; k++)
        {   
            letters = new JLabel[boardHeight];
            for (int x = 0; x < boardHeight; x++)
            {
                letters[x] = new JLabel();
                letters[x].setBounds(10 + (k * 1240), 50 * x + 30, 50, 50);
                letters[x].setForeground(Color.WHITE);
                letters[x].setText((char)(x + 65) + "");
                panel.add(letters[x]);
                letters[x].setVisible(true);
            }

            //Made top row pixel numbers
            numbers = new JLabel[boardWidth];
            String text = "";
            for (int x = 0; x < boardWidth; x++)
            {
                text = ((Integer)(x + 1)).toString();
                numbers[x] = new JLabel();
                numbers[x].setBounds((48 * x ) + 47 + (k * 720), -9, 50, 50);
                numbers[x].setForeground(Color.WHITE);
                numbers[x].setText(text);
                panel.add(numbers[x]);
                numbers[x].setVisible(true);
            }
            if (k == 0)
                {
                    text = "Enemy Board";
                    col = Color.RED;
                }
                else
                {
                    text = "Your Board";
                    col = Color.GREEN;
                }
                    
                boardLable[k] = new JLabel();
                boardLable[k].setBounds(180 + (k * 740), 525, 200, 50);
                boardLable[k].setForeground(col);
                boardLable[k].setFont(new Font("SansSerif", Font.BOLD, 25));
                boardLable[k].setText(text);
                panel.add(boardLable[k]);
                boardLable[k].setVisible(true);
                   
            if (k == 0)
            {
                text = "Enemy Board";
                col = Color.RED;
            }
            else
            {
                text = "Your Board";
                col = Color.GREEN;
            }
                
            boardLable[k] = new JLabel();
            boardLable[k].setBounds(180 + (k * 740), 525, 200, 50);
            boardLable[k].setForeground(col);
            boardLable[k].setFont(new Font("SansSerif", Font.BOLD, 25));
            boardLable[k].setText(text);
            
            panel.add(boardLable[k]);
            boardLable[k].setVisible(true);
            }
        
        ships = new JButton[5];
        JButton butt;
        ImageIcon icon = new ImageIcon("/Images/BattleShip.png");
        for (int x = 0; x < 5; x++)
        {
            switch (x)
            {
                case 0:
                    icon = new ImageIcon(getClass().getResource("/Images/AircraftCarrier.png"));
                    icon.setDescription("AircraftCarrier");
                break;
                case 1:
                    icon = new ImageIcon(getClass().getResource("/Images/BattleShip.png"));
                    icon.setDescription("BattleShip");
                break;
                case 2:
                    icon = new ImageIcon(getClass().getResource("/Images/Destroyer.png"));
                    icon.setDescription("Destroyer");
                break;
                case 3:
                    icon = new ImageIcon(getClass().getResource("/Images/Submarine.png"));
                    icon.setDescription("Submarine");
                break;
                case 4:
                    icon = new ImageIcon(getClass().getResource("/Images/PatrolBoat.png"));
                    icon.setDescription("PatrolBoat");
                break;
            }  
            ships[x] = butt = new JButton();
            butt.setIcon(icon);
            butt.setVerticalAlignment(SwingConstants.CENTER);
            butt.setContentAreaFilled(false);
            panel.add(butt);
            butt.setBounds(50 + 240 * x, 635, 200, 100);
            butt.setActionCommand("Select Ship");
            butt.addActionListener(this);
            butt.addMouseListener(new MyMouseListener());
        }                
            
        JLabel shipBackground = new JLabel();
        shipBackground.setBounds(30, 600, 1205, 150);
        shipBackground.setBackground(new Color(180,160,140));
        shipBackground.setFont(new Font("SansSerif", Font.BOLD, 25));
        shipBackground.setText("Ships");
        shipBackground.setHorizontalAlignment(SwingConstants.CENTER);
        shipBackground.setVerticalAlignment(SwingConstants.TOP);
        shipBackground.setOpaque(true);
        panel.add(shipBackground);
        shipBackground.setVisible(true);
          
        display = new JLabel[2];
        for (int k = 0; k < 2; k++) 
        {
            display[k] = new JLabel();
            panel.add(display[k]);
            display[k].setBounds((k * 720) + 30, 30, BACK_WIDTH - 5, BACK_HEIGHT);
            display[k].addMouseListener(new MyMouseListener());
        }    
        
        bA = new JTextArea();
        bA.setLineWrap(true);
        bA.setWrapStyleWord(true);
        bA.setEditable( false );
        battleLog = new JScrollPane(bA);

        battleLog.setFont(new Font("Arial", 0, 12));
        //battleLog.setBounds(515,60,160,450);

        battleLog.setBounds(515,60,227,450);


        panel.add(battleLog);
        
        JLabel logBackground = new JLabel();
        logBackground.setBounds(510, 20, 238, 520);
        logBackground.setBackground(new Color(120,120,130));
        logBackground.setFont(new Font("SansSerif", Font.BOLD, 25));
        logBackground.setText("Battle Log");
        logBackground.setHorizontalAlignment(SwingConstants.CENTER);
        logBackground.setVerticalAlignment(SwingConstants.TOP);
        logBackground.setOpaque(true);
        panel.add(logBackground);
        logBackground.setVisible(true);
        
        background = new JLabel();
        panel.add(background);
        background.setBounds(0,0, 1300,810);
        
        URL bground = getClass().getResource("/Images/punk.jpg");
        ImageIcon iconback = new ImageIcon(bground);
        background.setIcon(iconback);
        background.setVisible(true);
        
        for (int k = 0; k < 2; k++) 
        {
            URL imageURL = getClass().getResource("/Images/ocean.jpeg");
            if (imageURL != null) 
            {
                icon = new ImageIcon(imageURL);
                display[k].setIcon(icon);
                display[k].setVisible(true);
            } else {
                throw new RuntimeException("Image not found");
            }
        }
        
        pack();
        getContentPane().add(panel);
        panel.setVisible(true); 
    }
    
    /**
     * Draw the display (cards and messages).
     */
    public void repaint() 
    {
        pack();
        panel.repaint();
    }
    
    public void printLog(String str)
    {
        bA.append(str + "\n");
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
        if (e.getActionCommand().equals("Place Ship"))
        {
            placeX = Integer.parseInt(((JButton)e.getSource()).getText().substring(2));
            placeY = (int)(((JButton)e.getSource()).getText().charAt(1)) - 64;
            //placeY = 5;
        }

        if (e.getActionCommand().equals("Send Attack"))
        {
            attX = Integer.parseInt(((JButton)e.getSource()).getText().substring(2));
            attY = (int)(((JButton)e.getSource()).getText().charAt(1)) - 64;
        }
        
        if (e.getActionCommand().equals("Select Ship"))
        {
            if (selectable == true)
            {
                shipName = ((ImageIcon)((JButton)e.getSource()).getIcon()).getDescription();
            }
        }
    }
    
    public Point getAttack()
    {
        attX = -1;
        attY = -1;
        int test = 0;
        String tester;
        while (attX < 0 || attY < 0)
            tester = ((Integer)test).toString();
        Point pointy = new Point(attX, attY);
        return pointy;
    }
    
    public PosObject placeShip()
    {
        selectable = true;
        placeX = -1;
        placeY = -1;
        placeR = 0;
        shipName = "";
        int test = 0;
        String tester;
        while (placeX < 0 || placeY < 0 || shipName.equals(""))
            tester = ((Integer)test).toString();
        //System.out.println("Y AFTER LOOP: " + placeY); //The only way this works is if I print placeY what????
        fixInt(((Integer)placeY).toString());
        PosObject ship = new PosObject(placeX, placeY, placeR, shipName);
        selectable = false;
        return ship;
    }
    private static void fixInt(String string)
    {
        string += "";
    }
    /**
     * To be called if the ship is in a placeable coordinate
     * str should be the name of the ship without spaces (ex "BattleShip")
     */
    public void showShip(int x, int y, int r, String str)
    {
        ImageIcon icon;
        if (r == 0)
                    icon = new ImageIcon(getClass().getResource("/Images/" + str + ".png"));
                else
                    icon = new ImageIcon(getClass().getResource("/Images/" + str + "vert.png"));
        
        ship[num].setIcon(icon);
        ship[num].setBounds(42 * (x) + 720, 38 + 50 * (y - 1), 330 - (220 * r), 110 + (220 * r));
        ship[num].setHorizontalAlignment(SwingConstants.LEFT);
        ship[num].setVerticalAlignment(SwingConstants.TOP);
        ship[num].setContentAreaFilled(false);
        ship[num].setBorder(null);
        num++;
        
        pack();
        panel.repaint();
    }
    
    /**
     * P represents the x co-ordinate
     * Q represents the y co-ordinate
     * str should be "F" or "E" for friendly or enemy
     */
    public void showHit(int p, int q, String str)
    {
        String text = str + (char)(q + 64) + p;
        for(int k = 0; k < 2; k++)
            for (int x = 0; x < boardHeight; x++)
            {
                for (int y = 0; y < boardWidth; y++)
                    if (validSpaces[x][(boardWidth * k) + y].getText().equals(text))
                    {
                        text = "Hit";
                        validSpaces[x][(boardWidth * k) + y].setText(text);
                        validSpaces[x][(boardWidth * k) + y].setForeground(Color.WHITE);
                        validSpaces[x][(boardWidth * k) + y].setBackground(Color.RED);
                        validSpaces[x][(boardWidth * k) + y].setBorder(ships[1].getBorder());
                        validSpaces[x][(boardWidth * k) + y].setContentAreaFilled(true);
                        validSpaces[x][(boardWidth * k) + y].setActionCommand("void");
                        validSpaces[x][(boardWidth * k) + y].setOpaque(true);
                        validSpaces[x][(boardWidth * k) + y].setBorderPainted(false);
                        validSpaces[x][(boardWidth * k) + y].setFont(new Font("ArialSmall", 0, 1));
                        panel.add(validSpaces[x][(boardWidth * k) + y]);
                        validSpaces[x][(boardWidth * k) + y].setBounds(28 + 48 * x + (k * 720), 28 + 50 * y, 50, 50);
                    }
            }
        pack();
        panel.repaint();
    }
    
    /**
     * P represents the x co-ordinate
     * Q represents the y co-ordinate
     * str should be "F" or "E" for friendly or enemy
     */
    public void showMiss(int p, int q, String str)
    {
        String text = str + (char)(q + 64) + p;
        for(int k = 0; k < 2; k++)
            for (int x = 0; x < boardHeight; x++)
            {
                for (int y = 0; y < boardWidth; y++)
                    if (validSpaces[x][(boardWidth * k) + y].getText().equals(text))
                    {
                        text = "Miss";
                        validSpaces[x][(boardWidth * k) + y].setText(text);
                        validSpaces[x][(boardWidth * k) + y].setForeground(Color.RED);
                        validSpaces[x][(boardWidth * k) + y].setBackground(Color.WHITE);
                        validSpaces[x][(boardWidth * k) + y].setBorder(ships[1].getBorder());
                        validSpaces[x][(boardWidth * k) + y].setContentAreaFilled(true);
                        validSpaces[x][(boardWidth * k) + y].setActionCommand("void");
                        validSpaces[x][(boardWidth * k) + y].setOpaque(true);
                        validSpaces[x][(boardWidth * k) + y].setBorderPainted(false);
                        validSpaces[x][(boardWidth * k) + y].setFont(new Font("ArialSmall", 0, 1));
                        panel.add(validSpaces[x][(boardWidth * k) + y]);
                        validSpaces[x][(boardWidth * k) + y].setBounds(28 + 48 * x + (k * 720), 28 + 50 * y, 50, 50);
                    }
            }
        pack();
        panel.repaint();
    }
    
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_R)
        {
            if (placeR == 0)
                placeR = 1;
            else
                placeR = 0;
        }
    }
    
    public void keyReleased(KeyEvent e)
    {
    }
    
    public void keyTyped(KeyEvent e)
    {
    }
    
    /**
     * Receives and handles mouse clicks.  Other mouse events are ignored.
     */
    private class MyMouseListener extends MouseAdapter
    {

        /**
         * Handle a mouse click on a card by toggling its "selected" property.
         * Each card is represented as a label.
         * @param e the mouse event.
         */
        public void mouseClicked(MouseEvent e) 
        {
            if (selectable == true && firstClick == 0 && ((JButton)e.getSource()).getIcon() != null)
            {
                firstClick = 1;
                if (placeR == 0)
                    mouseIcon = ((ImageIcon)((JButton)e.getSource()).getIcon()); 
                else
                    mouseIcon = new ImageIcon(getClass().getResource(((ImageIcon)((JButton)e.getSource()).getIcon()).getDescription() + "vert.png"));
            }
            if (firstClick == 1 && ((JButton)e.getSource()).getActionCommand().equals("Place Ship"))
            {
                firstClick = 0;
                mouseIcon = null;
            }
        }
        
        public void mouseMoved(MouseEvent e)
        {
            if (firstClick == 1 && ((JButton)e.getSource()).getActionCommand().equals("Place Ship"))
            {
                if (placeR == 0)
                    mouseIcon = ((ImageIcon)((JButton)e.getSource()).getIcon()); 
                else
                    mouseIcon = new ImageIcon(getClass().getResource(((ImageIcon)((JButton)e.getSource()).getIcon()).getDescription() + "vert.png"));
                JLabel ship = new JLabel(mouseIcon);
                panel.add(ship);
                ship.setBounds(((JButton)e.getSource()).getX(), ((JButton)e.getSource()).getY(), 200, 100);
                pack();
                panel.repaint();
            }
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
