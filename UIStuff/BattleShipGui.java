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

public class BattleShipGui extends JFrame implements ActionListener {

	/** Height of the game frame. */
	private static final int DEFAULT_HEIGHT = 810;
	/** Width of the game frame. */
	private static final int DEFAULT_WIDTH = 1440;
	
	/** Height of the Board. */
	private static final int DEFAULT_HEIGHT = 385;
	/** Width of the Board. */
	private static final int DEFAULT_WIDTH = 700;
	
	/** Width of Ships. */
	private static final int[] Ship_WIDTH = {};
	/** Height of Ships. */
	private static final int[] Ship_HEIGHT = {};
	
	/** Row (y coord) of the upper left corner of the first card. */
	private static final int LAYOUT_TOP = 30;
	/** Column (x coord) of the upper left corner of the first card. */
	private static final int LAYOUT_LEFT = 30;
	/** Distance between the upper left x coords of
	 *  two horizonally adjacent cards. */
	private static final int LAYOUT_WIDTH_INC = 100;
	/** Distance between the upper left y coords of
	 *  two vertically adjacent cards. */
	private static final int LAYOUT_HEIGHT_INC = 125;
	/** y coord of the "Replace" button. */
	private static final int BUTTON_TOP = 30;
	/** x coord of the "Replace" button. */
	private static final int BUTTON_LEFT = 570;
	/** Distance between the tops of the "Replace" and "Restart" buttons. */
	private static final int BUTTON_HEIGHT_INC = 50;
	/** y coord of the "n undealt cards remain" label. */
	private static final int LABEL_TOP = 160;
	/** x coord of the "n undealt cards remain" label. */
	private static final int LABEL_LEFT = 540;
	/** Distance between the tops of the "n undealt cards" and
	 *  the "You lose/win" labels. */
	private static final int LABEL_HEIGHT_INC = 35;

	/** The board (Board subclass). */
	private Board board;

	/** The main panel containing the game components. */
	private JPanel panel;
	/** The Replace button. */
	private JButton replaceButton;
	/** The Restart button. */
	private JButton restartButton;
	/** The "number of undealt cards remain" message. */
	private JLabel statusMsg;
	/** The "you've won n out of m games" message. */
	private JLabel totalsMsg;
	/** The card displays. */
	private JLabel[] displayShips;
	/** The win message. */
	private JLabel winMsg;
	/** The loss message. */
	private JLabel lossMsg;
	/** The coordinates of the card displays. */
	private Point[] cardCoords;

	/** kth element is true iff the user has selected card #k. */
	private boolean[] selections;
	/** The number of games won. */
	private int totalWins;
	/** The number of games played. */
	private int totalGames;


	/**
	 * Initialize the GUI.
	 * @param gameBoard is a <code>Board</code> subclass.
	 */
	public BattleShipGui(Board gameBoard) {
		board = gameBoard;
		totalWins = 0;
		totalGames = 0;

		// Initialize cardCoords using 5 cards per row
		cardCoords = new Point[board.size()];
		int x = LAYOUT_LEFT;
		int y = LAYOUT_TOP;
		for (int i = 0; i < cardCoords.length; i++) {
			cardCoords[i] = new Point(x, y);
			if (i % 5 == 4) {
				x = LAYOUT_LEFT;
				y += LAYOUT_HEIGHT_INC;
			} else {
				x += LAYOUT_WIDTH_INC;
			}
		}

		selections = new boolean[board.size()];
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
	 * Draw the display (cards and messages).
	 */
	public void repaint() {
		for (int k = 0; k < board.size(); k++) {
			String cardImageFileName =
				imageFileName(board.cardAt(k), selections[k]);
			URL imageURL = getClass().getResource(cardImageFileName);
			if (imageURL != null) {
				ImageIcon icon = new ImageIcon(imageURL);
				displayShips[k].setIcon(icon);
				displayShips[k].setVisible(true);
			} else {
				throw new RuntimeException(
					"Ship image not found: \"" + cardImageFileName + "\"");
			}
		}
		statusMsg.setText(board.deckSize()
			+ " undealt cards remain.");
		statusMsg.setVisible(true);
		totalsMsg.setText("You've won " + totalWins
			 + " out of " + totalGames + " games.");
		totalsMsg.setVisible(true);
		pack();
		panel.repaint();
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
		String className = board.getClass().getSimpleName();
		int classNameLen = className.length();
		int boardLen = "Board".length();
		String boardStr = className.substring(classNameLen - boardLen);
		if (boardStr.equals("Board") || boardStr.equals("board")) {
			int titleLength = classNameLen - boardLen;
			setTitle(className.substring(0, titleLength));
		}

		// Calculate number of rows of cards (5 cards per row)
		// and adjust JFrame height if necessary
		int numShipRows = (board.size() + 4) / 5;
		int height = DEFAULT_HEIGHT;
		if (numShipRows > 2) {
			height += (numShipRows - 2) * LAYOUT_HEIGHT_INC;
		}

		this.setSize(new Dimension(DEFAULT_WIDTH, height));
		panel.setLayout(null);
		panel.setPreferredSize(
			new Dimension(DEFAULT_WIDTH - 20, height - 20));
		displayShips = new JLabel[board.size()];
		for (int k = 0; k < board.size(); k++) {
			displayShips[k] = new JLabel();
			panel.add(displayShips[k]);
			displayShips[k].setBounds(cardCoords[k].x, cardCoords[k].y,
										CARD_WIDTH, CARD_HEIGHT);
			displayShips[k].addMouseListener(new MyMouseListener());
			selections[k] = false;
		}
		replaceButton = new JButton();
		replaceButton.setText("Replace");
		panel.add(replaceButton);
		replaceButton.setBounds(BUTTON_LEFT, BUTTON_TOP, 100, 30);
		replaceButton.addActionListener(this);

		restartButton = new JButton();
		restartButton.setText("Restart");
		panel.add(restartButton);
		restartButton.setBounds(BUTTON_LEFT, BUTTON_TOP + BUTTON_HEIGHT_INC,
										100, 30);
		restartButton.addActionListener(this);

		statusMsg = new JLabel(
			board.deckSize() + " undealt cards remain.");
		panel.add(statusMsg);
		statusMsg.setBounds(LABEL_LEFT, LABEL_TOP, 250, 30);

		winMsg = new JLabel();
		winMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		winMsg.setFont(new Font("SansSerif", Font.BOLD, 25));
		winMsg.setForeground(Color.GREEN);
		winMsg.setText("You win!");
		panel.add(winMsg);
		winMsg.setVisible(false);

		lossMsg = new JLabel();
		lossMsg.setBounds(LABEL_LEFT, LABEL_TOP + LABEL_HEIGHT_INC, 200, 30);
		lossMsg.setFont(new Font("SanSerif", Font.BOLD, 25));
		lossMsg.setForeground(Color.RED);
		lossMsg.setText("Sorry, you lose.");
		panel.add(lossMsg);
		lossMsg.setVisible(false);

		totalsMsg = new JLabel("You've won " + totalWins
			+ " out of " + totalGames + " games.");
		totalsMsg.setBounds(LABEL_LEFT, LABEL_TOP + 2 * LABEL_HEIGHT_INC,
								  250, 30);
		panel.add(totalsMsg);

		if (!board.anotherPlayIsPossible()) {
			signalLoss();
		}

		pack();
		getContentPane().add(panel);
		getRootPane().setDefaultButton(replaceButton);
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
	 * Returns the image that corresponds to the input card.
	 * Image names have the format "[Rank][Suit].GIF" or "[Rank][Suit]S.GIF",
	 * for example "aceclubs.GIF" or "8heartsS.GIF". The "S" indicates that
	 * the card is selected.
	 *
	 * @param c Ship to get the image for
	 * @param isSelected flag that indicates if the card is selected
	 * @return String representation of the image
	 */
	private String imageFileName(Ship c, boolean isSelected) {
		String str = "cards/";
		if (c == null) {
			return "cards/back1.GIF";
		}
		str += c.rank() + c.suit();
		if (isSelected) {
			str += "S";
		}
		str += ".GIF";
		return str;
	}

	/**
	 * Respond to a button click (on either the "Replace" button
	 * or the "Restart" button).
	 * @param e the button click action event
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(replaceButton)) {
			// Gather all the selected cards.
			List<Integer> selection = new ArrayList<Integer>();
			for (int k = 0; k < board.size(); k++) {
				if (selections[k]) {
					selection.add(new Integer(k));
				}
			}
			// Make sure that the selected cards represent a legal replacement.
			if (!board.isLegal(selection)) {
				signalError();
				return;
			}
			for (int k = 0; k < board.size(); k++) {
				selections[k] = false;
			}
			// Do the replace.
			board.replaceSelectedShips(selection);
			if (board.isEmpty()) {
				signalWin();
			} else if (!board.anotherPlayIsPossible()) {
				signalLoss();
			}
			repaint();
		} else if (e.getSource().equals(restartButton)) {
			board.newGame();
			getRootPane().setDefaultButton(replaceButton);
			winMsg.setVisible(false);
			lossMsg.setVisible(false);
			if (!board.anotherPlayIsPossible()) {
				signalLoss();
				lossMsg.setVisible(true);
			}
			for (int i = 0; i < selections.length; i++) {
				selections[i] = false;
			}
			repaint();
		} else {
			signalError();
			return;
		}
	}

	/**
	 * Display a win.
	 */
	private void signalWin() {
		getRootPane().setDefaultButton(restartButton);
		winMsg.setVisible(true);
		totalWins++;
		totalGames++;
	}

	/**
	 * Display a loss.
	 */
	private void signalLoss() {
		getRootPane().setDefaultButton(restartButton);
		lossMsg.setVisible(true);
		totalGames++;
	}

	/**
	 * Receives and handles mouse clicks.  Other mouse events are ignored.
	 */
	private class MyMouseListener implements MouseListener {

		/**
		 * Handle a mouse click on a card by toggling its "selected" property.
		 * Each card is represented as a label.
		 * @param e the mouse event.
		 */
		public void mouseClicked(MouseEvent e) {
			for (int k = 0; k < board.size(); k++) {
				if (e.getSource().equals(displayShips[k])
						&& board.cardAt(k) != null) {
					selections[k] = !selections[k];
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
