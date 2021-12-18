// Driver for the Battleship server
// Vic Berry
// November 1, 2021

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class BSDriver extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BSDriver me = new BSDriver();
		me.doIt();
	}

	public void doIt() {
		addWindowListener(new WindowDestroyer());

		new BSGame(BSGame.GAME_PLAYER1);
	}
}
