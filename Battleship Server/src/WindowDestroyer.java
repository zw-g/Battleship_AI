//
// This is a small class to properly dispose of a window on close.
//
// Vic Berry
// November 1, 2021
//

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowDestroyer extends WindowAdapter
{
    public void windowClosing(WindowEvent e) 
    {
    	//Main.vMain.shutDown();
        System.exit(0);
    }
}


