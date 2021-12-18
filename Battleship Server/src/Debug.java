//
// Debug
// Vic Berry
// November 1, 2021

//
// This is the debug output for the game.  You can use it to verify your player code.
//
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

import java.awt.BorderLayout;
import java.awt.Color;

public class Debug extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2970251366454790619L;
	private boolean visible;
	private JTextPane textPane;
	
	public Debug() {
	    setSize(600, 600);
	    setLocation(1000,10);
	    setTitle("Debug Output");
	    
	    textPane = new JTextPane();
	    textPane.setForeground(Color.ORANGE);
	    textPane.setBackground(Color.DARK_GRAY);
	    
	    JScrollPane scrollPane = new JScrollPane(textPane);
	    
	    getContentPane().add(scrollPane, BorderLayout.CENTER);
	    visible = false;
	}
	
	public void addText(String text) {
		StyledDocument doc = textPane.getStyledDocument();
		try
		{
		    doc.insertString(doc.getLength(), text + "\n", null);
		}
		catch(Exception e) { System.out.println(e); }
	}
	
	public void clearText() {
		textPane.setText("");
	}
	
	public void toggleDisplay() {
		if (visible) {
			visible = false;
			this.setVisible(visible);
		} else {
			visible = true;
			this.setVisible(visible);
		}
	}
}
