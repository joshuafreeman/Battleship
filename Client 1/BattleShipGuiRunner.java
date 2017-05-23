import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;
import java.util.*;
/**
 * This is a class that plays the GUI version of the BattleShip game.
 * See accompanying documents for a description of how BattleShip is played.
 */
public class BattleShipGuiRunner {

	/**
	 * Plays the GUI version of BattleShip.
	 * @param args is not used.
	 */
	public static void main(String[] args) 
	{
		JPanel uniPanel = new JPanel(){
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
		
        StartGUI anton = new StartGUI(uniPanel);
		anton.displayGame();
        
	    PlayerBoard serverboard = new PlayerBoard();
        PlayerBoard board = new PlayerBoard(10,10);
		OpponentBoard board2 = new OpponentBoard(serverboard);
		BattleShipGameGUI gui = new BattleShipGameGUI(board, board2, uniPanel);
		gui.displayGame();
	}
}
