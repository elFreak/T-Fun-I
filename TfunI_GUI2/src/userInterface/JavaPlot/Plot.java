package userInterface.JavaPlot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JPanel;

import userInterface.Controller;

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

	// --------------------------------------------------------------------
	// Initialize:
	public Plot() {
		super(new GridLayout());

		// Generate first subplot:
		addSubplot();
	}

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
	public void setSubplot(int index) {
		subplotSelected = index;
	}

	// --------------------------------------------------------------------
	// Connect subplot X-Axis:
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
	public void disconnectSubplots() {
		connected = false;
		for (int i = 0; i < subplotActualNumber; i++) {
			subplot[i].setDisconnected();
		}
	}

	// --------------------------------------------------------------------
	// Add new trace:
	public int addTrace(Trace trace) {
		return subplot[subplotSelected].addTrace(trace);
	}

	// --------------------------------------------------------------------
	// Delete trace:
	public void clearTrace(int index) {
		subplot[subplotSelected].clearTrace(index);
	}

	// --------------------------------------------------------------------
	// Set the range of the axes:
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
	public void setCursorX(int x) {
		if (connected) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].setCursorX(x);
			}
			repaint();
		}
	}

	// --------------------------------------------------------------------
	// Clear Axis-Label:
	public void clearAxisLabel(int axis) {
		if (connected && axis == XAXIS) {
			for (int i = 0; i < subplotActualNumber; i++) {
				subplot[i].clearAxisLabel(axis);
			}
			repaint();
		} else {
			subplot[subplotSelected].clearAxisLabel(axis);
		}
	}

	// --------------------------------------------------------------------
	// Set Axis-Label:
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
	public void addSlider(int orientation, String tag) {
		subplot[subplotSelected].addSlider(orientation, tag, this);
	}

	// --------------------------------------------------------------------
	// Update for Slider:
	public void updateSliderValue(double start, double end, double offset) {
		controller.setRange(start, end, offset);
	}

	// --------------------------------------------------------------------
	// Set Step Position:
	public void setStepPosition(double position) {
		subplot[subplotSelected].setStepPosition(position);
	}

	// --------------------------------------------------------------------
	// Set Slider Position:
	public void setSliderPosition(String tag, double value) {
		subplot[subplotSelected].setSliderPosition(tag, value);
	}
}
