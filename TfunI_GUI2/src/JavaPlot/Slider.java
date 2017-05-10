package JavaPlot;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;

public class Slider extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	public int positionValue = 0;
	public int positionPixel = 0;
	
	public int orienation;
	
	public Slider(int orientation, Subplot subplot) {
		this.orienation = orientation;
		this.setBackground(GlobalSettings.colorSliderBlue);
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
	}
	
	public void update(){
		
	}
	
}
