package de.splashfish.EvenLinux;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.iharder.dnd.FileDrop;

public class Gui extends JFrame {
	private static final long serialVersionUID = -8754743612234852087L;
	
	private final CardLayout 		cl			= new CardLayout();
	private final JPanel			pane		= new JPanel(cl);
	private final OverviewPane		overview;
	
	public Gui() {
		this.overview = new OverviewPane();
		
		this.setTitle("Lé-css v"+ LeCss.VERSION);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/res/less.png")).getImage());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().add(pane);
		
		JPanel dropPane = new JPanel(new GridLayout(1,1));
		dropPane.add(new JLabel(new ImageIcon(this.getClass().getResource("/res/drag_drop.png"))));
		
		pane.add(dropPane, "DropPane");
		pane.add(overview, "OverviewPane");
		
		new FileDrop(dropPane, new FileDrop.Listener() {
			public void filesDropped(final File[] files) {
				cl.show(pane, "OverviewPane");
				new Thread() {
					public void run() {
						if(!overview.listFiles(files)) {
							cl.show(pane, "DropPane");
						}
					}
				}.start();
			}
		});
			
		cl.show(pane, "DropPane");
		
		this.pack();
		this.setLocation(center(this.getSize()));
		this.validate();
	}
	
	private Point center(Dimension s){
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		return new Point(
					(int)(screensize.getWidth()/2 - s.getWidth()/2),
					(int)(screensize.getHeight()/2 - s.getHeight()/2)
				);
	}
	
}
