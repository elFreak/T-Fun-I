package userInterface.JavaPlot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import userInterface.Controller;

/**
 * Stellt einen Plot zur Verfügung. Dieser Plot kann mehrere {@link Subplot}
 * beinhalten, welche miteinander verknüpft werden können.
 * 
 * @author Team 1
 *
 */
public class Plot extends JPanel {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// General:
	private Controller controller;

	// --------------------------------------------------------------------
	// Background:
	private Color backgroundColor = new Color(255, 255, 255); // @Approach

	// --------------------------------------------------------------------
	// Subplot:
	private int subplotMaxNumber = 3; // @Approach
	private int subplotActualNumber = 0;
	private int subplotSelected = 0;
	private Subplot[] subplot = new Subplot[subplotMaxNumber];

	// --------------------------------------------------------------------
	// Axes:
	public final static int XAXIS = Trace.XAXIS;
	public final static int Y1AXIS = Trace.Y1AXIS;
	public final static int Y2AXIS = Trace.Y2AXIS;

	// --------------------------------------------------------------------
	// Cursor:
	public boolean mouseIsPressed = false;

	// --------------------------------------------------------------------
	// Trace:
	@SuppressWarnings("unused")
	private static final int NOT_ENOUGHT_SPACE = Subplot.NOT_ENOUGHT_SPACE;

	// --------------------------------------------------------------------
	// Connecting:
	private boolean connected = false;
	private int borderWest;

	// --------------------------------------------------------------------
	// Initialize:
	/**
	 * Erzeugt einen leeren Plot.
	 */
	public Plot() {
		super(new GridLayout());

		// Generate first subplot:
		addSubplot();
	}

	/**
	 * Erzeugt einen leeren Plot und gibt diesem einen {@link Controller} mit.
	 * Dieser Plot kann später Informationen dem Controller übergeben.
	 * 
	 * @param controller
	 */
	public Plot(Controller controller) {
		super(new GridLayout());
		this.controller = controller;

		// Generate first subplot:
		addSubplot();
	}

	// --------------------------------------------------------------------
	// Paint:
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	// --------------------------------------------------------------------
	// Add new subplot:
	/**
	 * Fügt dem Plot einen neuen {@link Subplot} hinzu.
	 */
	public void addSubplot() {
		subplot[subplotActualNumber] = new Subplot();
		subplotSelected = subplotActualNumber;

		setLayout(new GridLayout(subplotActualNumber + 1, 1));
		for (int i = 0; i <= subplotActualNumber; i++) {
			add(subplot[i]);
		}

		subplot[subplotActualNumber].setRange(XAXIS, 0, 100);
		subplot[subplotActualNumber].setRange(Y1AXIS, 0, 100);
		subplot[subplotActualNumber].setRange(Y2AXIS, 0, 100);

		subplotActualNumber++;
		repaint();
	}

	// --------------------------------------------------------------------
	// Set current subplot:
	/**
	 * Wählt den aktuellen {@link Subplot} aus.
	 * 
	 * @param index
	 */
	public void setSubplot(int index) {
		subplotSelected = index;
	}

	// --------------------------------------------------------------------
	// Connect subplot X-Axis:
	/**
	 * Verbindet die X-Axen aller {@link Subplot} miteinander.
	 */
	public void connectSubplots() {
		connected = true;
		if (subplotActualNumber > 0) {
			subplot[subplotActualNumber - 1].setConnected(this, true, false);
			for (int i = 1; i < subplotActualNumber - 1; i++) {
				subplot[i].setConnected(this, false, false);
			}
			subplot[0].setConnected(this, false, true);
		}
	}

	// --------------------------------------------------------------------
	// Disconnect subplot X-Axis:
	/**
	 * Löst die X-Axen aller {@link Subplot} voneinander.
	 */
	public void disconnectSubplots() {
		connected = false;
		for (int i = 0; i < subplotActualNumber; i++) {
			subplot[i].setDisconnected();
		}
	}

	// --------------------------------------------------------------------
	// Add new trace:
	/**
	 * Fügt dem aktuallen {@link Subplot} einen {@link Trace} hinzu, falls
	 * dieser nicht bereits vorhanden ist.
	 * 
	 * @param trace
	 * @return done
	 */
	public int addTrace(Trace trace) {
		return subplot[subplotSelected].addTrace(trace);
	}

	// --------------------------------------------------------------------
	// Delete trace:
	/**
	 * Entfernt vom aktuallen {@link Subplot} einen {@link Trace}.
	 * 
	 * @param index
	 */
	public void clearTrace(int index) {
		subplot[subplotSelected].clearTrace(index);
	}

	// --------------------------------------------------------------------
	// Set the range of the axes:
	/**
	 * Setzt den Achsenberech des aktuallen {@link Subplot}. Falls die
	 * {@link Subplot} miteinander verbunden sind, werden alle X-Achsen zusammen
	 * verändert.
	 * 
	 * @param axis
	 * @param low
	 * @param high
	 */
	public void setRange(int axis, double low, double high) {
		if (connected && axis == XAXIS) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].setRange(axis, low, high);
			}
		} else {
			subplot[subplotSelected].setRange(axis, low, high);
		}
	}

	// --------------------------------------------------------------------
	// Set the ideal range of the axes:
	/**
	 * Setzt den angezeigten Berech des aktuallen {@link Subplot} automatisch.
	 */
	public void setRangeIdeal() {
		if (connected) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].setRangeIdeal();
			}
		} else {
			subplot[subplotSelected].setRangeIdeal();
		}
	}

	// --------------------------------------------------------------------
	// Set X-Cursor:
	/**
	 * Setzt die Position von X-Cursor.
	 * 
	 * @param x
	 */
	public void setCursorX(int x) {
		if (connected) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].setCursorX(x);
			}
			repaint();
		}
	}

	// --------------------------------------------------------------------
	// Set Axis-Label:
	/**
	 * Fügt dem aktuallen {@link Subplot} eine Achsenbeschriftung hinzu.
	 * 
	 * @param axis
	 * @param symbol
	 * @param index
	 * @param unit
	 */
	public void setAxisLabel(int axis, String symbol, String index, String unit) {
		if (connected) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].setAxisLabel(axis, symbol, index, unit);
			}
			repaint();
		} else {
			subplot[subplotSelected].setAxisLabel(axis, symbol, index, unit);
		}
	}

	// --------------------------------------------------------------------
	// Add new Slider:
	/**
	 * Fügt dem aktuellen {@link Subplot} einen {@link Slider} hinzu.
	 * 
	 * @param orientation
	 * @param tag
	 */
	public void addSlider(int orientation, String tag) {
		subplot[subplotSelected].addSlider(orientation, tag, this);
	}

	// --------------------------------------------------------------------
	// Update for Slider:
	/**
	 * Aktuallisiert die {@link Slider} vom aktuellen {@link Subplot}.
	 * 
	 * @param start
	 * @param end
	 * @param offset
	 */
	public void updateSliderValue(double start, double end, double offset) {
		controller.setRange(end, offset);
		controller.setStepTime(start);
	}

	// // --------------------------------------------------------------------
	// // Set Step Position:
	// public void setStepPosition(double position) {
	// subplot[subplotSelected].setStepPosition(position);
	// }

	// --------------------------------------------------------------------
	// Set Slider Position:
	/**
	 * Aktuallisiert den Sprung-{@link Slider} vom aktuellen {@link Subplot}.
	 * 
	 * @param tag
	 * @param value
	 */
	public void setSliderPosition(String tag, double value) {
		subplot[subplotSelected].setSliderPosition(tag, value);
	}

	int getBorderWest() {
		return borderWest;
	}

	void setBorderWest(int borderWest) {
		this.borderWest = borderWest;
	}
}
