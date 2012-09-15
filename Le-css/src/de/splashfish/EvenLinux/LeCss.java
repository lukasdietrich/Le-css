package de.splashfish.EvenLinux;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class LeCss {

	public	final static float 	VERSION = 0.3f;
	
	public static void main(String[] args) {
		Gui gui = new Gui();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// using fallback >> crossplatform look and feel.
		} finally {
			SwingUtilities.updateComponentTreeUI(gui);
		}
		
		gui.setVisible(true);
		
	}
}
