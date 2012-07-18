package de.splashfish.EvenLinux;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class OverviewItem extends JPanel {
	private static final long serialVersionUID = -8774860483875890657L;

	private static Font 	font_less;
	private static Font 	font_css;
	private static Color	dark;
	private static Color	light;
	
	static {
		dark 	= new Color(180, 180, 180);
		light 	= new Color(220, 220, 220);
		try {
			font_less = Font.createFont(Font.TRUETYPE_FONT, OverviewItem.class.getResourceAsStream("/font/Ubuntu-R.ttf")).deriveFont(Font.PLAIN, 20f);
			font_css = font_less.deriveFont(Font.PLAIN, 14f);
		} catch (Exception e) {
			font_less = new Font(Font.SERIF, Font.PLAIN, 20);
			font_css = new Font(Font.SERIF, Font.PLAIN, 16);
		}
	}
	
	@Override public void paint(Graphics g) {
		Graphics2D enhanced = (Graphics2D) g;
		enhanced.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		enhanced.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		enhanced.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		super.paint(enhanced);
	}
	
	public OverviewItem(ImageIcon state, String less, String additional) {
		JLabel lessLabel = new JLabel(less);
		JLabel additionalLabel  = new JLabel(additional);
		
		lessLabel.setFont(font_less);
		additionalLabel.setFont(font_css);
		
		JPanel lessandcss = new JPanel(new BorderLayout());
		lessandcss.add(lessLabel, BorderLayout.NORTH);
		lessandcss.add(additionalLabel, BorderLayout.CENTER);
		lessandcss.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		
		JLabel stateLabel = new JLabel(state);
		state.setImageObserver(stateLabel);
		stateLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		
		this.setLayout(new BorderLayout(10, 10));
		this.add(stateLabel, BorderLayout.WEST);
		this.add(lessandcss, BorderLayout.CENTER);
		
		lessLabel.setOpaque(false);
		additionalLabel.setOpaque(false);
		stateLabel.setOpaque(false);
		
		lessandcss.setOpaque(false);
		this.setBackground(light);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, dark));
	}
	
}
