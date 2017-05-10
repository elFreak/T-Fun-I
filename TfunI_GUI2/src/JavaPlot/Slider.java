package JavaPlot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;

public class Slider extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;
	
	public static int sliderThickness = GlobalSettings.traceThinkness*6;

	public int positionPixel = 0;
	
	public int orienation;
	
	public Slider(int orientation, Subplot subplot) {
		this.orienation = orientation;
		this.setBackground(new Color(0, 0, 0,0));
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(orientation == HORIZONTAL) {
					positionPixel+=e.getY();
				}
				else {
					positionPixel+=e.getX();
				}
				repaint();
				subplot.repaint();
			}
		});
	}
	
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		Stroke dashed = new BasicStroke(GlobalSettings.traceThinkness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{GlobalSettings.traceThinkness*3,GlobalSettings.traceThinkness}, 0);
		g2.setStroke(dashed);
		if(orienation == HORIZONTAL){
			g2.setColor(GlobalSettings.colorSliderBlue);
			g2.draw(new Line2D.Float(getHeight()/2,getHeight()/2,getWidth()-getHeight(),getHeight()/2));
		} else {
			g2.setColor(GlobalSettings.colorSliderViolet);
			g2.draw(new Line2D.Float(getWidth()/2,getWidth()/2,getWidth()/2,getHeight()-getWidth()/2));
		}
	}
}
