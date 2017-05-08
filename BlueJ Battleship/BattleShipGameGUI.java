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
    /* Height of the game frame. */
    private static final int DEFAULT_HEIGHT = 810;
    /* Width of the game frame. */
    private static final int DEFAULT_WIDTH = 1440;
    
    /** The main panel containing the game components. */
	private JPanel panel;
	
	/** The boards. */
	private PlayerBoard friend;
	private OpponentBoard enemy;
	
	/** Board sizes*/
	private int boardWidth;
	private int boardHeight;
	
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
	private void initDisplay()	{
		panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
			}
		};

		// If board object's class name follows the standard format
		// of ...Board or ...board, use the prefix for the JFrame title
		String className = friend.getClass().getSimpleName();
		int classNameLen = className.length();
		int boardLen = "Board".length();
		String boardStr = className.substring(classNameLen - boardLen);
		if (boardStr.equals("Board") || boardStr.equals("board")) {
			int titleLength = classNameLen - boardLen;
			setTitle(className.substring(0, titleLength));
		}

		// Calculate number of rows of cards (5 cards per row)
		// and adjust JFrame height if necessary

		this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(DEFAULT_WIDTH - 20, DEFAULT_HEIGHT - 20));

		//Do the buttons
		for (int x = 0; x < boardHeight; x++)
		    for (int y = 0; y < boardWidth; y++)
    		{
    		    JButton butt = validSpaces[x][y];
        		butt.setText("Test");
        		panel.add(butt);
        		butt.setBounds(0 + 30 * x, 0 + 30 * y, 30, 30);
        		butt.addActionListener(this);
            }

		pack();
		getContentPane().add(panel);
		panel.setVisible(true);
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
}
