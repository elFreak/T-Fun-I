package JavaPlot;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import projectT_Fun_I.GlobalSettings;

public class Slider extends JPanel {
	private static final long serialVersionUID = 1L;

	public static final int HORIZONTAL = 1;
	public static final int VERTICAL = 2;

	public static int sliderThickness = GlobalSettings.traceThinkness * 6;

	public int positionPixel = 0;
	public Slider slider;
	public int orienation;
	public String tag;
	private boolean highlight = false;
	

	public Slider(int orientation, Subplot subplot, String tag) {
		this.tag = tag;
		this.orienation = orientation;
		this.setOpaque(false);
		this.slider = this;
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				highlight = false;
				repaint();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				highlight = true;
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (orientation == HORIZONTAL) {
					positionPixel += e.getY() - getHeight() / 2;
				} else {
					positionPixel += e.getX() - getWidth() / 2;
				}
				
				repaint();
				subplot.repaint();
				subplot.updateSliderValue(slider);
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		Stroke dashed;
		if(highlight) {
			dashed = new BasicStroke(GlobalSettings.traceThinkness*2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { GlobalSettings.traceThinkness * 3, GlobalSettings.traceThinkness }, 0);
		}else
		{
			dashed = new BasicStroke(GlobalSettings.traceThinkness, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
					new float[] { GlobalSettings.traceThinkness * 3, GlobalSettings.traceThinkness }, 0);
		}

		g2.setStroke(dashed);
		if (orienation == HORIZONTAL) {
			g2.setColor(GlobalSettings.colorSliderBlue);
			g2.draw(new Line2D.Float(getHeight() / 2, getHeight() / 2, getWidth() - getHeight(), getHeight() / 2));
		} else {
			g2.setColor(GlobalSettings.colorSliderViolet);
			g2.draw(new Line2D.Float(getWidth() / 2, getWidth() / 2, getWidth() / 2, getHeight() - getWidth() / 2));
		}
	}
}
