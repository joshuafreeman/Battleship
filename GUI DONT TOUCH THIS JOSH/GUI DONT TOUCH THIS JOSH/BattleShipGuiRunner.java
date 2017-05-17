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
		PlayerBoard board = new PlayerBoard(10,10);
		//Get PlayerBoard from server
		PlayerBoard serverboard = new PlayerBoard();
		OpponentBoard board2 = new OpponentBoard(serverboard);
		BattleShipGameGUI gui = new BattleShipGameGUI(board, board2);
		gui.displayGame();
	}
}
