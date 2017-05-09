package userInterface;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;

import javax.swing.JButton;
import javax.swing.JPanel;
import projectTfunI.GlobalSettings;
import projectTfunI.Utility;

public class ProgramFlow extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private Controller controller;

	// --------------------------------------------------------------------
	// ProgrammFlow:
	private JButton btEinlesen = new JButton("<html><center> <b>Einlesen</b> <p/> " + "(Messwerte)</html>");
	private JButton btBearbeiten = new JButton("<html><center> <b>Bearbeiten</b> <p/> " + "(Messwerte)</html>");
	private JButton btBerechnen = new JButton(
			"<html><center> <b>Berechnen</b> <p/> " + "(Übertragungsfunktion)</html>");
	private JButton btVertifizieren = new JButton(
			"<html><center><b>Vertifizieren</b> <p/> " + "(Übertragungsfunktion)</html>");

	// --------------------------------------------------------------------
	// Initialize:
	public ProgramFlow(Controller controller) {
		this.controller = controller;

		setLayout(null);

		this.setFont(GlobalSettings.fontText);
		this.setPreferredSize(new Dimension(0, getFont().getSize() * 6));
		btEinlesen.setFont(getFont());
		btBearbeiten.setFont(getFont());
		btBerechnen.setFont(getFont());
		btVertifizieren.setFont(getFont());

		Utility.setAllBackgrounds(this, GlobalSettings.colorBackgroundBlueDark);

		// User-Interface:
		btEinlesen.setEnabled(true);
		btBearbeiten.setEnabled(true);
		btBerechnen.setEnabled(true);
		btVertifizieren.setEnabled(true);

		add(btEinlesen);
		add(btBearbeiten);
		add(btBerechnen);
		add(btVertifizieren);

		btEinlesen.addActionListener(this);
		btBearbeiten.addActionListener(this);
		btBerechnen.addActionListener(this);
		btVertifizieren.addActionListener(this);
	}

	// --------------------------------------------------------------------
	// Paint:
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Get Dimensions:
		int actualWidth = getWidth();
		int actualHeight = getHeight();

		FontMetrics fm = g2.getFontMetrics();
		g2.setFont(GlobalSettings.fontText);
		fm = g2.getFontMetrics();
		int textWidthMax = (fm.stringWidth(" (Übertragungsfunktion) "));
		int textHighMax = 3 * getFont().getSize();

		// Calculate Positions:
		int blockWidth = (int) (textWidthMax * 1.1);
		int blockHight = (int) (textHighMax * 1.5);
		int centerX = actualWidth / 2;
		int centerY = actualHeight / 2;
		int emptySpace = (int) (blockWidth * 0.1);

		int blockDistance = blockWidth + emptySpace;

		btEinlesen.setBounds((int) (centerX - blockDistance * 1.5 - blockWidth / 2), centerY - blockHight / 2,
				blockWidth, blockHight);
		btBearbeiten.setBounds((int) (centerX - blockDistance * 0.5 - blockWidth / 2), centerY - blockHight / 2,
				blockWidth, blockHight);
		btBerechnen.setBounds((int) (centerX + blockDistance * 0.5 - blockWidth / 2), centerY - blockHight / 2,
				blockWidth, blockHight);
		btVertifizieren.setBounds((int) (centerX + blockDistance * 1.5 - blockWidth / 2), centerY - blockHight / 2,
				blockWidth, blockHight);

		// Paint Arrows:
		paintArrow(g, (int) (centerX - blockDistance * 1.5 + blockWidth / 2), centerY,
				(int) (centerX - blockDistance * 0.5 - blockWidth / 2), centerY);
		paintArrow(g, (int) (centerX - blockDistance * 0.5 + blockWidth / 2), centerY,
				(int) (centerX + blockDistance * 0.5 - blockWidth / 2), centerY);
		paintArrow(g, (int) (centerX + blockDistance * 0.5 + blockWidth / 2), centerY,
				(int) (centerX + blockDistance * 1.5 - blockWidth / 2), centerY);

		// Paint Frame:
		g2.setColor(GlobalSettings.colorBackgroundGreyBright);
		g2.setStroke(new BasicStroke(actualHeight / 15));
		g2.draw(new Line2D.Float(0, actualHeight, actualWidth, actualHeight));
	}

	private void paintArrow(Graphics g, int x1, int y1, int x2, int y2) {
		Graphics2D g2 = (Graphics2D) g;
		int textHight = g2.getFont().getSize();
		g2.setColor(GlobalSettings.colorTextGrey);
		g2.setStroke(new BasicStroke(textHight / 4));
		g2.draw(new Line2D.Float(x1, y1, x2 - textHight / 2, y2));
		Polygon polygon = new Polygon();
		polygon.addPoint(x2, y2);
		polygon.addPoint(x2, y2);
		polygon.addPoint(x2 - (int) (textHight * 2), y2 - (int) (textHight * 1));
		polygon.addPoint(x2 - (int) (textHight * 2), y2 + (int) (textHight * 1));
		g2.fillPolygon(polygon);
	}

	public void setActualMode(int mode) {
		switch (mode) {
		case Controller.EINLESEN:
			break;
		case Controller.BEARBEITEN:
			break;

		case Controller.BERECHNEN:

			break;
		case Controller.VERTIFIZIEREN:

			break;

		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btEinlesen)) {
			controller.setActualMode(Controller.EINLESEN);
		}
		if (e.getSource().equals(btBearbeiten)) {
			controller.setActualMode(Controller.BEARBEITEN);
		}
		if (e.getSource().equals(btBerechnen)) {
			controller.setActualMode(Controller.BERECHNEN);
		}
		if (e.getSource().equals(btVertifizieren)) {
			controller.setActualMode(Controller.VERTIFIZIEREN);
		}
	}
}
