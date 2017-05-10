package JavaPlot;

import javax.swing.JPanel;

import projectTfunI.GlobalSettings;

public class Slider extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	public int orienation;
	
	public Slider(int orientation) {
		this.orienation = orientation;
		this.setBackground(GlobalSettings.colorSliderBlue);
	}
}
