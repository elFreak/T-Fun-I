package userInterface.JavaPlot;

import java.awt.Color;

public class Trace {
	// --------------------------------------------------------------------
	// Data Management:
	public boolean dataValid = false;

	// --------------------------------------------------------------------
	// Axes Management:
	public static final int XAXIS = 0;
	public static final int Y1AXIS = 1;
	public static final int Y2AXIS = 2;
	public int yaxis = Y1AXIS;

	// Label:
	public String name = "";

	// --------------------------------------------------------------------
	// Data:
	public static final int X = 0;
	public static final int Y = 1;
	public double[][] data;
	
	// --------------------------------------------------------------------
	// Line Type:
	public static final int LINE_CONTINOUS = 0;
	public static final int LINE_NONE = 1;
	public int lineType = LINE_CONTINOUS;
	
	// --------------------------------------------------------------------
	// Point Type:
	public static final int POINT_NONE = 0;
	public static final int POINT_BULLET = 1;
	public static final int POINT_CROSS = 2;
	public int pointType = POINT_NONE;
	
	// --------------------------------------------------------------------
	// Color:
	public boolean usePreferedColor = false;
	public Color preferedColor;
}
