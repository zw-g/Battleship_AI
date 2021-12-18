//
// This is the tournament support class.  It is not complete yet.
//
// Vic Berry
// November 1, 2021
//

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;

public class Tournament extends JFrame implements MouseListener {
	
	private BSGame game;

	private static final long serialVersionUID = -1530947879480379666L;

	private Player []players = {new Player1(1), new Player2(1)};
	
	public Tournament(BSGame game) {
		this.game = game;
		Container cp = getContentPane();
	    setSize(400, 800);
	    setLocation(100,10);
	    setResizable(false);

	    //setTitle("Java Battleship Server (Rev = " + revision + ")");

	    cp.setBackground(Color.black);
	    cp.setLayout(new BorderLayout());
	    cp.addMouseListener(this);
	    
	    //setVisible(true);
	    
	    Random gen = new Random();
	    // Randomize the players
	    for (int i = 0; i < 1000; i++) {
	    	int num1 = gen.nextInt(players.length);
	    	int num2 = gen.nextInt(players.length);
	    	
	    	Player tmp = players[num1];
	    	players[num1] = players[num2];
	    	players[num1] = tmp;
	    }
	    
	    for (int i = 0; i < players.length; i++) {
	    	System.out.println(i + ") " + players[i].getName());
	    }
	    
	    System.out.println(play(0,1));

	}

	private int p1 = 0;
	private int p2 = 1;
	private void randomize(int a, int b) {
		Random gen = new Random();
		boolean aFirst = gen.nextBoolean();
		
		if (aFirst) {
			p1 = a;
			p2 = b;
		} else {
			p1 = b;
			p2 = a;
		}
	}
	private int play(int a, int b) {
		int playerAWins = 0;
		int playerBWins = 0;
		
		boolean done = false;
		
		System.out.println(players[a].getName() + " versus " + players[b].getName());
		while (!done) {
			// Random who goes first
			randomize(a,b);
			
			game.setup(BSGame.GAME_PLAYER1, players[p1], players[p2]);
			game.initializeGame();
			
			game.playGame(BSGame.MODE_TWO_COMPUTER_LOCAL, players[p1], players[p2]);
			
			if (game.player1Won()) {
				System.out.println("\t" + players[p1].getName() + " won in " + players[a].numMoves + " Moves.");
				if (p1 == a) {
					playerAWins++;
				} else {
					playerBWins++;
				}
			}
			
			if (game.player2Won()) {
				System.out.println("\t" + players[p2].getName() + " won in " + players[a].numMoves + " Moves.");
				if (p1 == b) {
					playerAWins++;
				} else {
					playerBWins++;
				}
			}
			
			if ((playerAWins == 4) || (playerBWins == 4)) {
				System.out.println(players[a].getName() + " won " + playerAWins + " " + players[b].getName() 
						           + " won " + playerBWins);
				done = true;
			}
			
		}
		
		if (playerAWins == 4) {
			return a;
		} else {
			return b;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
