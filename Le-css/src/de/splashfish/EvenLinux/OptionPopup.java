package de.splashfish.EvenLinux;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import de.splashfish.util.FileShift;

public class OptionPopup extends JPopupMenu {
	private static final long serialVersionUID = -2235681521093236786L;

	public OptionPopup(final ItemDefinition definition, final Runnable disablecall, final Runnable forcerender) {
		
		if(Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			JMenuItem item1 = new JMenuItem("open "+ definition.getLessName());
			this.add(item1);
		
			item1.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().browse(definition.getShift().get(FileShift.FROM_FILE).toURI());
					} catch (IOException e1) { }
				}
			});
		}
		
		JMenuItem item2 = new JMenuItem("force a compilation of "+ definition.getLessName());
		this.add(item2);
		
		item2.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new Thread(forcerender).start();
			}
		});
		
		JMenuItem item3 = new JMenuItem("disable compilation for "+ definition.getLessName());
		this.add(item3);
		
		item3.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				new Thread(disablecall).start();
			}
		});
		
		this.validate();
	}
	
}
