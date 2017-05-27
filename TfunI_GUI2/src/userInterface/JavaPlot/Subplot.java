package userInterface.JavaPlot;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import projectT_Fun_I.GlobalSettings;
import userInterface.IOCard.OutputCardBearbeiten;
import userInterface.JavaPlot.Trace;

import javax.swing.JPanel;

public class Subplot extends JPanel implements MouseMotionListener, MouseListener {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// Background:
	private Color backgroundColor = new Color(250, 250, 250); // @Apprach

	// --------------------------------------------------------------------
	// Trace:
	private int traceMaxNumber = 10; // @Approach
	private static final int traceColorsNumber = 3;
	private static final Color[] traceColors = new Color[traceColorsNumber];
	private Trace[] trace = new Trace[traceMaxNumber];
	public static final int NOT_ENOUGHT_SPACE = -1;
	private int traceThickness = GlobalSettings.traceThinkness;

	// --------------------------------------------------------------------
	// Axes:
	public final static int XAXIS = Trace.XAXIS;
	public final static int Y1AXIS = Trace.Y1AXIS;
	public final static int Y2AXIS = Trace.Y2AXIS;

	// --------------------------------------------------------------------
	// Axis Range:
	private int axisRangeTargetSectorNumber = 5; // @Approach
	private int[] axisRangeActualSectorNumber = { 1, 1, 1 };
	private int[] axisRangeSectorZeroPoint = new int[3];
	public static final int ZERO_IN_RANGE = 0;
	public static final int NO_ZERO_IN_RANGE = -1;
	private double[][] axisRangeSector = new double[3][axisRangeTargetSectorNumber + 2];

	// --------------------------------------------------------------------
	// Axis Range Nummeration:
	private double[] axisRangeScaleFactor = new double[3];
	private int[] axisRangeRoundFactor = new int[3];
	private Color nummerationColor = new Color(20, 20, 20); // @Approach
	private double nummerationTextHeight = 1.6; // @Approach
	private int nummberationDistance = 7; // @Approach

	// --------------------------------------------------------------------
	// Board for the Plot:
	private double boardBorderFactor = 9;
	public static final int X = Trace.X;
	public static final int Y = Trace.Y;
	private int[][] boardCorner = new int[4][2];
	private Color boardColor = new Color(45, 45, 45); // @Approach
	private boolean doScale = false; // @Approach

	// --------------------------------------------------------------------
	// Grid:
	private Color gridColor = new Color(245, 245, 245); // @Approach
	private int gridThickness = 1;
	private int gridMainGridThickness = 3;
	private Color gridHelpGridColor = new Color(150, 150, 150); // @Approach

	// --------------------------------------------------------------------
	// Marker-Dashes:
	private int markerDasheslenght = 6; // @Approach

	// --------------------------------------------------------------------
	// Connecting:
	private boolean connected = false;
	private boolean connectedLowSubplot = false;
	private boolean connectedHighSubplot = false;
	private Plot plot;

	// --------------------------------------------------------------------
	// Zoom:
	private boolean mouseIsPressed = false;
	private Color zoomFrameColor = new Color(250, 250, 20);
	private final static int PIXEL = 0;
	private final static int VALUE = 1;
	private double[][] zoomTopLeft = new double[2][3];
	private double[][] zoomDownRight = new double[2][3];
	private int zoomFrameThickness = 2; // @Approach

	// --------------------------------------------------------------------
	// Slider:
	private final static int MAX_SLIDER = 3;
	private Slider[] sliders = new Slider[MAX_SLIDER];
	private int sliderActualNumber = 0;
	private double sliderStartValue = 0;
	private double sliderEndValue = 0;
	private double sliderOffsetValue = 0;
	// private double stepTime = 0;

	// --------------------------------------------------------------------
	// Axis Label:
	private boolean axisLabelValid[] = { false, false, false };
	public String axisLabelSymbol[] = { "", "", "" };
	public String axisLabelIndex[] = { "", "", "" };
	public String axisLabelUnit[] = { "", "", "" };

	// --------------------------------------------------------------------
	// Cursor:
	private int cursorX;

	// --------------------------------------------------------------------
	// Initialize:
	public Subplot() {
		setLayout(null);

		// initialize -> traces
		for (int i = 0; i < trace.length; i++) {
			trace[i] = new Trace();
		}

		// initialize -> trace-colors:
		traceColors[0] = new Color(210, 40, 40); // red
		traceColors[1] = new Color(40, 210, 40); // green
		traceColors[2] = new Color(40, 40, 210); // blue

		// initialize -> zoom:
		this.addMouseListener(this);
		this.addMouseMotionListener(this);

	}

	// --------------------------------------------------------------------
	// Paint:
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		// Measure the size of the panel:
		int width = getSize().width;
		int height = getSize().height;

		// Calculate general Values:
		double scaleFactor = Math.min(Math.min(width / 35, height / 35), 20);
		int border;
		if (doScale) {
			border = (int) (scaleFactor * boardBorderFactor);
		} else {
			FontMetrics fm = g2.getFontMetrics();
			g2.setFont(GlobalSettings.fontTextSmall);
			fm = g2.getFontMetrics();
			int widthSymbol = (fm.stringWidth("00000"));
			border = widthSymbol;
		}

		// Calculate ... Y2-Axis
		int yAxisActualNumber = axisRangeActualSectorNumber[Y1AXIS];
		// Is there a second Y-axis in the plot:
		boolean yAxis2Availabe = false;
		for (int i = 0; i < trace.length; i++) {
			if (trace[i].dataValid && trace[i].yaxis == Trace.Y2AXIS) {
				yAxis2Availabe = true;
				yAxisActualNumber = Math.max(axisRangeActualSectorNumber[Y1AXIS], axisRangeActualSectorNumber[Y2AXIS]);
				axisRangeActualSectorNumber[Y1AXIS] = yAxisActualNumber;
				axisRangeActualSectorNumber[Y2AXIS] = yAxisActualNumber;
			}
		}

		int borderSouth;
		int borderNorth;
		if (connected && !connectedHighSubplot) {
			borderNorth = markerDasheslenght;
		} else {
			borderNorth = border / 6;
		}
		if (connected && !connectedLowSubplot) {
			borderSouth = markerDasheslenght;
		} else {
			borderSouth = border / 2;
		}

		// calculate boardCorners:
		boardCorner[0][X] = border; // top-left (X)
		boardCorner[0][Y] = borderNorth; // top-left (Y)

		boardCorner[1][X] = border; // down-left (X)
		boardCorner[1][Y] = height - borderSouth; // down-left (Y)

		boardCorner[2][X] = width - border; // down-left (X)
		boardCorner[2][Y] = height - borderSouth; // down-left (Y)

		boardCorner[3][X] = width - border; // top-right (X)
		boardCorner[3][Y] = borderNorth; // top-right (Y)

		// Paint Background:
		g2.setColor(backgroundColor);
		g2.fillRect(0, 0, width, height);

		// paint board:
		g2.setColor(boardColor);
		Polygon p = new Polygon();
		p.addPoint(boardCorner[0][X], boardCorner[0][Y]);
		p.addPoint(boardCorner[1][X], boardCorner[1][Y]);
		p.addPoint(boardCorner[2][X], boardCorner[2][Y]);
		p.addPoint(boardCorner[3][X], boardCorner[3][Y]);
		g2.fillPolygon(p);

		// Paint Grid:
		paintGrid(g2, XAXIS, yAxis2Availabe);
		paintGrid(g2, Y1AXIS, yAxis2Availabe);

		// Paint Border of the board:
		g2.setColor(nummerationColor);
		g2.setStroke(new BasicStroke(gridMainGridThickness));
		g2.draw(new Line2D.Float(boardCorner[0][X], boardCorner[0][Y], boardCorner[1][X], boardCorner[1][Y]));
		g2.draw(new Line2D.Float(boardCorner[1][X], boardCorner[1][Y], boardCorner[2][X], boardCorner[2][Y]));
		g2.draw(new Line2D.Float(boardCorner[2][X], boardCorner[2][Y], boardCorner[3][X], boardCorner[3][Y]));
		g2.draw(new Line2D.Float(boardCorner[3][X], boardCorner[3][Y], boardCorner[0][X], boardCorner[0][Y]));

		// paint traces:
		for (int i = 0; i < trace.length; i++) {
			if (trace[i].dataValid && trace[i].data != null) {
				int provColor = i % traceColorsNumber;
				if (trace[i].usePreferedColor) {
					g2.setColor(trace[i].preferedColor);
				} else {
					g2.setColor(traceColors[provColor]);
				}
				g2.setStroke(new BasicStroke(traceThickness));

				for (int j = 0; j < trace[i].data[0].length; j++) {
					int x1, y1, x2, y2;
					x1 = (int) ((double) (trace[i].data[Trace.X][j] - axisRangeSector[XAXIS][0])
							* (double) (boardCorner[2][X] - boardCorner[1][X])
							/ (double) (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1]
									- axisRangeSector[XAXIS][0]));
					y1 = (int) ((double) (trace[i].data[Trace.Y][j] - axisRangeSector[trace[i].yaxis][0])
							* (double) (boardCorner[1][Y] - boardCorner[0][Y])
							/ (double) (axisRangeSector[trace[i].yaxis][yAxisActualNumber - 1]
									- axisRangeSector[trace[i].yaxis][0]));
					x1 = boardCorner[1][X] + x1;
					y1 = boardCorner[1][Y] - y1;

					if (j < trace[i].data[0].length - 1 && trace[i].lineType == Trace.LINE_CONTINOUS) {
						x2 = (int) ((double) (trace[i].data[Trace.X][j + 1] - axisRangeSector[XAXIS][0])
								* (double) (boardCorner[2][X] - boardCorner[1][X])
								/ (double) (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1]
										- axisRangeSector[XAXIS][0]));
						y2 = (int) ((double) (trace[i].data[Trace.Y][j + 1] - axisRangeSector[trace[i].yaxis][0])
								* (double) (boardCorner[1][Y] - boardCorner[0][Y])
								/ (double) (axisRangeSector[trace[i].yaxis][yAxisActualNumber - 1]
										- axisRangeSector[trace[i].yaxis][0]));
						x2 = boardCorner[1][X] + x2;
						y2 = boardCorner[1][Y] - y2;
						g2.draw(new Line2D.Float(x1, y1, x2, y2));
					}

					if (trace[i].pointType == Trace.POINT_BULLET) {
						g2.fillOval(x1 - traceThickness * 3, y1 - traceThickness * 3, traceThickness * 6,
								traceThickness * 6);
					}
					if (trace[i].pointType == Trace.POINT_CROSS) {
						g2.draw(new Line2D.Float(x1 - traceThickness * 3, y1 - traceThickness * 3,
								x1 + traceThickness * 3, y1 + traceThickness * 3));
						g2.draw(new Line2D.Float(x1 - traceThickness * 3, y1 + traceThickness * 3,
								x1 + traceThickness * 3, y1 - traceThickness * 3));
					}
				}
			}

		}

		// Paint Cursor-X:
		if (connected) {
			if (plot.mouseIsPressed) {
				g2.setColor(zoomFrameColor);
				g2.setStroke(new BasicStroke(zoomFrameThickness));
				g2.draw(new Line2D.Float(cursorX, 0, (int) cursorX, height * 10));
			}
		}
		// Paint Zoom-Frame:^
		Polygon polygon;
		if (mouseIsPressed) {
			g2.setColor(zoomFrameColor);
			g2.setStroke(new BasicStroke((int) (zoomFrameThickness * 1.5)));
			polygon = new Polygon();
			polygon.addPoint((int) zoomTopLeft[PIXEL][X], (int) zoomTopLeft[PIXEL][Y]);
			polygon.addPoint((int) zoomTopLeft[PIXEL][X], (int) zoomDownRight[PIXEL][Y]);
			polygon.addPoint((int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y]);
			polygon.addPoint((int) zoomDownRight[PIXEL][X], (int) zoomTopLeft[PIXEL][Y]);
			g2.drawPolygon(polygon);

			g2.setColor(zoomFrameColor);
			g2.setStroke(new BasicStroke(zoomFrameThickness / 2));
			g2.draw(new Line2D.Float((int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y],
					(int) zoomDownRight[PIXEL][X] + width, (int) zoomDownRight[PIXEL][Y]));
			g2.draw(new Line2D.Float((int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y],
					(int) zoomDownRight[PIXEL][X] - width, (int) zoomDownRight[PIXEL][Y]));
			g2.draw(new Line2D.Float((int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y],
					(int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y] + height));
			g2.draw(new Line2D.Float((int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y],
					(int) zoomDownRight[PIXEL][X], (int) zoomDownRight[PIXEL][Y] - height));
		}

		// Paint border:
		polygon = new Polygon();
		polygon.addPoint(0, boardCorner[0][Y]);
		polygon.addPoint(0, height);
		polygon.addPoint(width, height);
		polygon.addPoint(width, 0);
		polygon.addPoint(0, 0);
		polygon.addPoint(0, boardCorner[0][Y]);
		polygon.addPoint(boardCorner[0][X], boardCorner[0][Y]);
		polygon.addPoint(boardCorner[3][X], boardCorner[3][Y]);
		polygon.addPoint(boardCorner[2][X], boardCorner[2][Y]);
		polygon.addPoint(boardCorner[1][X], boardCorner[1][Y]);
		polygon.addPoint(boardCorner[0][X], boardCorner[0][Y]);
		g2.setColor(backgroundColor);
		g2.fillPolygon(polygon);

		// Paint Marker-Dashes:
		paintMarkerDashes(g2, XAXIS, yAxis2Availabe);
		paintMarkerDashes(g2, Y1AXIS, yAxis2Availabe);

		// paint Nummeration:
		paintNummeration(g2, XAXIS, scaleFactor);
		paintNummeration(g2, Y1AXIS, scaleFactor);
		if (yAxis2Availabe) {
			paintNummeration(g2, Y2AXIS, scaleFactor);
		}

		// Paint Axes-Label (X):
		if (axisLabelValid[XAXIS] && !(connected && !connectedLowSubplot)) {

			String unitWithPrefix = "[" + getPrefix((int) (axisRangeScaleFactor[XAXIS])) + axisLabelUnit[XAXIS] + "]";

			g2.setColor(nummerationColor);
			Font fontSymbol;
			Font fontIndex;
			if (doScale) {
				int heightSymbol = (int) (nummerationTextHeight * 1.4 * scaleFactor);
				int heightIndex = (int) (nummerationTextHeight * 1 * scaleFactor);
				fontSymbol = new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, heightSymbol);
				fontIndex = new Font(Font.DIALOG_INPUT, Font.CENTER_BASELINE, heightIndex);
			} else {
				fontSymbol = GlobalSettings.fontTextSmall;
				fontIndex = new Font(fontSymbol.getName(), fontSymbol.getStyle(), (int) (fontSymbol.getSize() * 0.8));
			}

			FontMetrics fm = g2.getFontMetrics();
			g2.setFont(fontSymbol);
			fm = g2.getFontMetrics();
			int widthSymbol = (fm.stringWidth(axisLabelSymbol[XAXIS]));
			int widthUnit = (fm.stringWidth(unitWithPrefix));
			g2.setFont(fontIndex);
			fm = g2.getFontMetrics();
			int widthIndex = (fm.stringWidth(axisLabelIndex[XAXIS]));
			int widthLabel = widthSymbol + widthIndex + widthUnit;
			int XCenter = (boardCorner[2][X] - boardCorner[1][X]) / 2 + boardCorner[1][X];

			g2.setFont(fontSymbol);
			g2.drawString(axisLabelSymbol[XAXIS], XCenter - widthLabel / 2,
					boardCorner[1][Y] + (int) (borderSouth * 0.9));
			g2.setFont(fontIndex);
			g2.drawString(axisLabelIndex[XAXIS], XCenter - widthLabel / 2 + widthSymbol,
					boardCorner[1][Y] + (int) (borderSouth * 0.9));
			g2.setFont(fontSymbol);
			g2.drawString(unitWithPrefix, XCenter - widthLabel / 2 + widthSymbol + widthIndex,
					boardCorner[1][Y] + (int) (borderSouth * 0.9));
		}

		// Paint Slider:
		setSliderPosition(OutputCardBearbeiten.KEY_START);
		setSliderPosition(OutputCardBearbeiten.KEY_END);
		setSliderPosition(OutputCardBearbeiten.KEY_OFFSET);

		for (int i = 0; i < sliderActualNumber; i++) {

			if (sliders[i].orienation == Slider.HORIZONTAL) {
				if (sliders[i].positionPixel < boardCorner[0][Y]) {
					sliders[i].positionPixel = boardCorner[0][Y];
				}
				if (sliders[i].positionPixel > boardCorner[1][Y]) {
					sliders[i].positionPixel = boardCorner[1][Y];
				}
				sliders[i].setBounds(boardCorner[0][X], sliders[i].positionPixel - Slider.sliderThickness / 2,
						boardCorner[2][X] - boardCorner[0][X], Slider.sliderThickness);
			} else {
				if (sliders[i].positionPixel < boardCorner[0][X]) {
					sliders[i].positionPixel = boardCorner[0][X];
				}
				if (sliders[i].positionPixel > boardCorner[2][X]) {
					sliders[i].positionPixel = boardCorner[2][X];
				}
				sliders[i].setBounds(sliders[i].positionPixel - Slider.sliderThickness / 2, boardCorner[0][Y],
						Slider.sliderThickness, boardCorner[2][Y] - boardCorner[0][Y]);
			}
		}
	}

	// --------------------------------------------------------------------
	// Paint Grid:
	private void paintGrid(Graphics2D g2, int axis, boolean yAxis2Available) {

		int deltaGrid = ((boardCorner[2][axis] - boardCorner[0][axis]) / (axisRangeActualSectorNumber[axis] - 1));
		for (int i = 0; i < axisRangeActualSectorNumber[axis]; i++) {

			// Paint Normal-Grid:
			g2.setColor(gridColor);
			g2.setStroke(new BasicStroke(gridThickness));
			if (i < axisRangeActualSectorNumber[axis] - 1) {
				if (axis == XAXIS) {
					g2.draw(new Line2D.Float(boardCorner[1][X] + i * deltaGrid, boardCorner[1][Y],
							boardCorner[0][X] + i * deltaGrid, boardCorner[0][Y]));
				} else {
					g2.draw(new Line2D.Float(boardCorner[0][X], boardCorner[0][Y] + i * deltaGrid, boardCorner[3][X],
							boardCorner[3][Y] + i * deltaGrid));
				}

			} else {
				if (axis == XAXIS) {
					g2.draw(new Line2D.Float(boardCorner[1][X], boardCorner[1][Y], boardCorner[0][X],
							boardCorner[0][Y]));
				} else {
					g2.draw(new Line2D.Float(boardCorner[1][X], boardCorner[1][Y], boardCorner[1][X],
							boardCorner[1][Y]));
				}
			}

			// Paint Main-Grid:
			g2.setColor(gridColor);
			g2.setStroke(new BasicStroke(gridMainGridThickness));
			if (i == axisRangeSectorZeroPoint[axis] && !yAxis2Available) {
				if (i < axisRangeActualSectorNumber[axis] - 1) {
					if (axis == XAXIS) {
						g2.draw(new Line2D.Float(boardCorner[0][X] + i * deltaGrid, boardCorner[0][Y],
								boardCorner[1][X] + i * deltaGrid, boardCorner[1][Y]));
					} else {
						g2.draw(new Line2D.Float(boardCorner[0][X],
								boardCorner[0][Y] + (axisRangeActualSectorNumber[axis] - 1 - i) * deltaGrid,
								boardCorner[3][X],
								boardCorner[3][Y] + (axisRangeActualSectorNumber[axis] - 1 - i) * deltaGrid));
					}
				}
			}

			// Paint Help-Grid:
			g2.setColor(gridHelpGridColor);
			float[] dash = { 6 };
			float dash_phase = 0;
			g2.setStroke(new BasicStroke(gridThickness / 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, dash,
					dash_phase));
			if (i < axisRangeActualSectorNumber[axis] - 1) {
				if (axis == XAXIS) {
					g2.draw(new Line2D.Float(boardCorner[1][X] + deltaGrid / 2 + i * deltaGrid, boardCorner[1][Y],
							boardCorner[0][X] + deltaGrid / 2 + i * deltaGrid, boardCorner[0][Y]));
				} else {
					g2.draw(new Line2D.Float(boardCorner[0][X], boardCorner[0][Y] + deltaGrid / 2 + i * deltaGrid,
							boardCorner[3][X], boardCorner[3][Y] + deltaGrid / 2 + i * deltaGrid));
				}

			}
		}
	}

	// --------------------------------------------------------------------
	// Paint Marker-Dashes:
	private void paintMarkerDashes(Graphics2D g2, int axis, boolean yAxis2Available) {
		int deltaGrid = ((boardCorner[2][axis] - boardCorner[0][axis]) / (axisRangeActualSectorNumber[axis] - 1));
		for (int i = 0; i < axisRangeActualSectorNumber[axis]; i++) {

			g2.setColor(nummerationColor);
			g2.setStroke(new BasicStroke(gridMainGridThickness));
			if (i < axisRangeActualSectorNumber[axis] - 1) {
				if (axis == XAXIS) {
					g2.draw(new Line2D.Float(boardCorner[1][X] + i * deltaGrid, boardCorner[1][Y],
							boardCorner[1][X] + i * deltaGrid, boardCorner[1][Y] + markerDasheslenght));
					if (connected && !connectedHighSubplot) {
						g2.draw(new Line2D.Float(boardCorner[0][X] + i * deltaGrid, boardCorner[0][Y],
								boardCorner[0][X] + i * deltaGrid, boardCorner[0][Y] - markerDasheslenght));
					}
				} else {
					g2.draw(new Line2D.Float(boardCorner[0][X], boardCorner[0][Y] + i * deltaGrid,
							boardCorner[0][X] - markerDasheslenght, boardCorner[0][Y] + i * deltaGrid));
					if (yAxis2Available) {
						g2.draw(new Line2D.Float(boardCorner[3][X], boardCorner[3][Y] + i * deltaGrid,
								boardCorner[3][X] + markerDasheslenght, boardCorner[3][Y] + i * deltaGrid));
					}
				}

			} else {
				if (axis == XAXIS) {
					g2.draw(new Line2D.Float(boardCorner[2][X], boardCorner[2][Y], boardCorner[2][X],
							boardCorner[2][Y] + markerDasheslenght));
					if (connected && !connectedHighSubplot) {
						g2.draw(new Line2D.Float(boardCorner[3][X], boardCorner[3][Y], boardCorner[3][X],
								boardCorner[3][Y] - markerDasheslenght));
					}
				} else {
					g2.draw(new Line2D.Float(boardCorner[1][X], boardCorner[1][Y],
							boardCorner[1][X] - markerDasheslenght, boardCorner[1][Y]));
					if (yAxis2Available) {
						g2.draw(new Line2D.Float(boardCorner[2][X], boardCorner[2][Y],
								boardCorner[2][X] + markerDasheslenght, boardCorner[2][Y]));
					}
				}
			}
		}
	}

	// --------------------------------------------------------------------
	// Paint Nummeration:
	private void paintNummeration(Graphics2D g2, int axis, double scaleFactor) {

		g2.setColor(nummerationColor);
		int nummerationTestHeightNormed = 0;
		if (doScale == true) {
			nummerationTestHeightNormed = (int) (nummerationTextHeight * scaleFactor);
		} else {
			nummerationTestHeightNormed = GlobalSettings.fontTextSmall.getSize();
		}
		Font font = new Font(GlobalSettings.fontTextSmall.getName(), GlobalSettings.fontTextSmall.getStyle(),
				nummerationTestHeightNormed);

		g2.setFont(font);

		int axisIsAYAxis = (int) Math.ceil(axis / 2.0);
		int deltaGrid = ((boardCorner[2][axisIsAYAxis] - boardCorner[0][axisIsAYAxis])
				/ (axisRangeActualSectorNumber[axis] - 1));
		DecimalFormatSymbols s = new DecimalFormatSymbols();
		s.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat();

		int stringWidthMax = 0;
		for (int i = 0; i < axisRangeActualSectorNumber[axis]; i++) {
			double number = (Math.round(axisRangeSector[axis][i] / Math.pow(10.0, axisRangeScaleFactor[axis])
					* Math.pow(10, axisRangeRoundFactor[axis]))) / Math.pow(10, axisRangeRoundFactor[axis]);

			switch (axisRangeRoundFactor[axis]) {
			case 0:
				df = new DecimalFormat("0");
				break;
			case 1:
				df = new DecimalFormat("0.0");
				break;
			case 2:
				df = new DecimalFormat("0.00");
				break;
			case 3:
				df = new DecimalFormat("0.000");
				break;
			case 4:
				df = new DecimalFormat("0.0000");
				break;
			case 5:
				df = new DecimalFormat("0.00000");
				break;
			}

			df.setDecimalFormatSymbols(s);
			String text = df.format(number);

			g2.setFont(font);
			FontMetrics fm = g2.getFontMetrics();
			int stringWidth = fm.stringWidth(text);
			if (stringWidth > stringWidthMax) {
				stringWidthMax = stringWidth;
			}
		}

		for (int i = 0; i < axisRangeActualSectorNumber[axis]; i++) {
			double number = (Math.round(axisRangeSector[axis][i] / Math.pow(10.0, axisRangeScaleFactor[axis])
					* Math.pow(10, axisRangeRoundFactor[axis]))) / Math.pow(10, axisRangeRoundFactor[axis]);

			switch (axisRangeRoundFactor[axis]) {
			case 0:
				df = new DecimalFormat("0");
				break;
			case 1:
				df = new DecimalFormat("0.0");
				break;
			case 2:
				df = new DecimalFormat("0.00");
				break;
			case 3:
				df = new DecimalFormat("0.000");
				break;
			case 4:
				df = new DecimalFormat("0.0000");
				break;
			case 5:
				df = new DecimalFormat("0.00000");
				break;
			}

			df.setDecimalFormatSymbols(s);
			String text = df.format(number);

			g2.setFont(font);
			FontMetrics fm = g2.getFontMetrics();
			int stringWidth = fm.stringWidth(text);

			if (axis == XAXIS) {
				g2.drawString(text, boardCorner[1][X] + i * deltaGrid - stringWidth / 2,
						boardCorner[1][Y] + nummerationTestHeightNormed + nummberationDistance);
			}
			if (i == axisRangeActualSectorNumber[axis] - 1) {
				if (axis == Y1AXIS) {
					g2.drawString(text, boardCorner[1][X] - stringWidth - nummberationDistance * 2,
							boardCorner[1][Y] - i * deltaGrid + nummerationTestHeightNormed / 2);
				}
				if (axis == Y2AXIS) {
					g2.drawString(text, boardCorner[2][X] + nummberationDistance * 2 + stringWidthMax - stringWidth,
							boardCorner[2][Y] - i * deltaGrid + nummerationTestHeightNormed / 2);
				}
			} else {
				if (axis == Y1AXIS) {
					g2.drawString(text, boardCorner[1][X] - stringWidth - nummberationDistance * 2,
							boardCorner[1][Y] - i * deltaGrid);
				}
				if (axis == Y2AXIS) {
					g2.drawString(text, boardCorner[2][X] + nummberationDistance * 2 + stringWidthMax - stringWidth,
							boardCorner[2][Y] - i * deltaGrid);
				}
			}
		}
	}

	// --------------------------------------------------------------------
	// Calculate Prefix:
	private String getPrefix(int potency) {
		String k = "";
		switch (potency) {
		case -12:
			k = "p";
			break;
		case -9:
			k = "n";
			break;
		case -6:
			k = "u";
			break;
		case -3:
			k = "m";
			break;
		case 0:
			k = "";
			break;
		case 3:
			k = "k";
			break;
		case 6:
			k = "M";
			break;
		case 9:
			k = "G";
			break;
		case 12:
			k = "T";
			break;
		}
		return k;
	}

	// --------------------------------------------------------------------
	// Set Axis-Label:
	public void setAxisLabel(int axis, String symbol, String index, String unit) {
		this.axisLabelValid[axis] = true;
		this.axisLabelSymbol[axis] = symbol;
		this.axisLabelIndex[axis] = index;
		this.axisLabelUnit[axis] = unit;
		repaint();
	}

	// --------------------------------------------------------------------
	// Clear Axis-Label:
	public void clearAxisLabel(int axis) {
		this.axisLabelValid[axis] = false;
		repaint();
	}

	// --------------------------------------------------------------------
	// Set Cursor-X:
	public void setCursorX(int x) {
		cursorX = x;
	}

	// --------------------------------------------------------------------
	// Add new trace:
	public int addTrace(Trace trace) {
		for (int i = 0; i < this.trace.length; i++) {
			if (this.trace[i].dataValid == false) {
				this.trace[i] = trace;
				this.trace[i].dataValid = true;
				return i; // If there is enough space, then return the index for
							// the given trace.
			}
		}
		return NOT_ENOUGHT_SPACE;
	}

	// --------------------------------------------------------------------
	// Delete trace:
	public void clearTrace(int index) {
		trace[index].dataValid = false;
	}

	// --------------------------------------------------------------------
	// Set the range of the axes:
	public void setRange(int axis, double low, double high) {

		// Calculate the size between the sections:
		double deltaRange = Math.abs(high - low);
		double scaleFactor10 = Math.floor(Math.log10(deltaRange));
		double deltaRangeNormed = deltaRange * Math.pow(10, -scaleFactor10);

		double deltaSectionNormed = 0.2;
		if (deltaRangeNormed > 0.2 * axisRangeTargetSectorNumber) {
			deltaSectionNormed = 0.5;
			if (deltaRangeNormed > 0.5 * axisRangeTargetSectorNumber) {
				deltaSectionNormed = 1;
				if (deltaRangeNormed > 1 * axisRangeTargetSectorNumber) {
					deltaSectionNormed = 1.5;
					if (deltaRangeNormed > 1.5 * axisRangeTargetSectorNumber) {
						deltaSectionNormed = 2;
					}
				}
			}
		}
		double deltaSection = deltaSectionNormed * Math.pow(10, scaleFactor10);

		// Calculate the sections:
		double sectionStart;

		// Is there a zero-crossing?:
		if (low > 0) {
			sectionStart = Math.floor(low / deltaSection) * deltaSection;
			axisRangeSectorZeroPoint[axis] = NO_ZERO_IN_RANGE; // Axis is not on
																// plot.
		} else if (high < 0) {
			sectionStart = -Math.floor(-high / deltaSection) * deltaSection;
			axisRangeSectorZeroPoint[axis] = NO_ZERO_IN_RANGE; // Axis is not on
																// plot.
		} else { // is there a zero-crossing?
			sectionStart = 0;
			axisRangeSectorZeroPoint[axis] = ZERO_IN_RANGE; // Axis is on plot.
		}

		int borderHigh = 0; // Temporary variable
		int borderLow = 0; // Temporary variable

		for (int i = 0; sectionStart + i * deltaSection < high; i++) { // count
																		// up
																		// ...
			borderHigh = i + 1;
		}
		for (int i = 0; sectionStart + i * deltaSection > low; i--) { // count
																		// down
																		// ...
			borderLow = i - 1;
		}

		if (axisRangeSectorZeroPoint[axis] == ZERO_IN_RANGE) { // subsectorAchtualPos0
																// is set ...
			axisRangeSectorZeroPoint[axis] = -borderLow;
		}

		for (int i = 0; i < axisRangeSector[axis].length; i++) {
			axisRangeSector[axis][i] = sectionStart + deltaSection * (i + borderLow);
		}

		// Calculate important Values for painting:
		axisRangeActualSectorNumber[axis] = borderHigh - borderLow + 1;
		axisRangeScaleFactor[axis] = Math.floor(
				(Math.floor(Math.log10(Math.max(Math.abs(axisRangeSector[axis][axisRangeActualSectorNumber[axis] - 1]),
						Math.abs(axisRangeSector[axis][0]))))) / 3)
				* 3;

		axisRangeRoundFactor[axis] = (int) Math.max(Math.min(axisRangeScaleFactor[axis] - scaleFactor10 + 1, 5), 0);

		repaint();
	}

	// --------------------------------------------------------------------
	// Set the ideal range of the axes:
	public void setRangeIdeal() {

		int zaehler = 0;
		for (int i = 0; i < trace.length; i++) {
			if (trace[i].dataValid == true) {
				zaehler++;
			}
		}
		if (zaehler == 0) {
			return;
		}

		double xMin = 0;
		double xMax = 100;
		double y1Min = 0;
		double y1Max = 100;
		double y2Min = 0;
		double y2Max = 100;
		for (int iTrace = 0; iTrace < trace.length; iTrace++) {
			if (trace[iTrace].dataValid && trace[iTrace].data != null) {
				xMin = trace[iTrace].data[X][0];
				xMax = trace[iTrace].data[X][0];
				if (trace[iTrace].yaxis == Y1AXIS) {
					y1Min = trace[iTrace].data[Y][0];
					y1Max = trace[iTrace].data[Y][0];
				}
				if (trace[iTrace].yaxis == Y2AXIS) {
					y2Min = trace[iTrace].data[Y][0];
					y2Max = trace[iTrace].data[Y][0];
				}
			}
		}
		for (int iTrace = 0; iTrace < trace.length; iTrace++) {
			if (trace[iTrace].dataValid && trace[iTrace].data != null) {
				for (int i = 0; i < trace[iTrace].data[0].length; i++) {
					if (trace[iTrace].data[X][i] < xMin) {
						xMin = trace[iTrace].data[X][i];
					}
					if (trace[iTrace].data[X][i] > xMax) {
						xMax = trace[iTrace].data[X][i];
					}
					if (trace[iTrace].yaxis == Y1AXIS) {
						if (trace[iTrace].data[Y][i] < y1Min) {
							y1Min = trace[iTrace].data[Y][i];
						}
						if (trace[iTrace].data[Y][i] > y1Max) {
							y1Max = trace[iTrace].data[Y][i];
						}
					}
					if (trace[iTrace].yaxis == Y2AXIS) {
						if (trace[iTrace].data[Y][i] < y2Min) {
							y2Min = trace[iTrace].data[Y][i];
						}
						if (trace[iTrace].data[Y][i] > y2Max) {
							y2Max = trace[iTrace].data[Y][i];
						}
					}
				}
			}
		}

		if (y1Min == y1Max) {
			if (y1Max > 0) {
				y1Min = 0;
			} else {
				y1Max = 0;
			}
		}
		if (y2Min == y2Max) {
			if (y2Max > 0) {
				y2Min = 0;
			} else {
				y2Max = 0;
			}
		}
		
		if(y1Min==y1Max) {
			y1Min -= 0.1;
			y1Max += 0.1;
		}
		if(y2Min==y2Max) {
			y2Min -= 0.1;
			y2Max += 0.1;
		}

		double deltaY1 = y1Max - y1Min;
		double deltaY2 = y2Max - y2Min;
		y1Min = y1Min - deltaY1 / 50;
		y1Max = y1Max + deltaY1 / 50;
		y2Min = y2Min - deltaY2 / 50;
		y2Max = y2Max + deltaY2 / 50;

		if (trace[0].lineType == Trace.LINE_NONE) {
			xMax = (xMax - xMin) * 0.2;
			y1Min *= 1.2;
			y1Max *= 1.2;
		}

		setRange(Y1AXIS, y1Min, y1Max);
		setRange(Y2AXIS, y2Min, y2Max);
		if (connected) {
			plot.setRange(XAXIS, xMin, xMax);
		} else {
			setRange(XAXIS, xMin, xMax);
		}
	}

	// --------------------------------------------------------------------
	// Connect all X-Axes of the Subplots:
	public void setConnected(Plot plot, boolean lowSubplot, boolean highSubplot) {
		connected = true;
		connectedLowSubplot = lowSubplot;
		connectedHighSubplot = highSubplot;
		this.plot = plot;
	}

	// --------------------------------------------------------------------
	// Disconnect all X-Axes of the Subplots:
	public void setDisconnected() {
		connected = false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseIsPressed = true;
		if (connected) {
			plot.mouseIsPressed = true;
		}

		zoomTopLeft[PIXEL][X] = e.getX();
		zoomTopLeft[PIXEL][Y] = e.getY();
		zoomTopLeft[VALUE][XAXIS] = axisRangeSector[XAXIS][0]
				+ ((e.getX() - boardCorner[0][X]) / (double) (boardCorner[3][X] - boardCorner[0][X])
						* (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1] - axisRangeSector[XAXIS][0]));
		zoomTopLeft[VALUE][Y1AXIS] = axisRangeSector[Y1AXIS][0] + ((boardCorner[1][Y] - e.getY())
				/ (double) (boardCorner[1][Y] - boardCorner[0][Y])
				* (axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1] - axisRangeSector[Y1AXIS][0]));
		zoomTopLeft[VALUE][Y2AXIS] = axisRangeSector[Y2AXIS][0] + ((boardCorner[1][Y] - e.getY())
				/ (double) (boardCorner[1][Y] - boardCorner[0][Y])
				* (axisRangeSector[Y2AXIS][axisRangeActualSectorNumber[Y2AXIS] - 1] - axisRangeSector[Y2AXIS][0]));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseIsPressed = false;
		if (connected) {
			plot.mouseIsPressed = false;
		}

		zoomDownRight[VALUE][XAXIS] = axisRangeSector[XAXIS][0]
				+ ((e.getX() - boardCorner[0][X]) / (double) (boardCorner[3][X] - boardCorner[0][X])
						* (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1] - axisRangeSector[XAXIS][0]));
		zoomDownRight[VALUE][Y1AXIS] = axisRangeSector[Y1AXIS][0] + ((boardCorner[1][Y] - e.getY())
				/ (double) (boardCorner[1][Y] - boardCorner[0][Y])
				* (axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1] - axisRangeSector[Y1AXIS][0]));
		zoomDownRight[VALUE][Y2AXIS] = axisRangeSector[Y2AXIS][0] + ((boardCorner[1][Y] - e.getY())
				/ (double) (boardCorner[1][Y] - boardCorner[0][Y])
				* (axisRangeSector[Y2AXIS][axisRangeActualSectorNumber[Y2AXIS] - 1] - axisRangeSector[Y2AXIS][0]));

		if (zoomTopLeft[VALUE][Y] > zoomDownRight[VALUE][Y] && zoomTopLeft[VALUE][X] < zoomDownRight[VALUE][X]) {
			setRange(Y1AXIS, zoomDownRight[VALUE][Y1AXIS], zoomTopLeft[VALUE][Y1AXIS]);
			setRange(Y2AXIS, zoomDownRight[VALUE][Y2AXIS], zoomTopLeft[VALUE][Y2AXIS]);
			if (connected) {
				plot.setRange(XAXIS, zoomTopLeft[VALUE][XAXIS], zoomDownRight[VALUE][XAXIS]);
			} else {
				setRange(XAXIS, zoomTopLeft[VALUE][XAXIS], zoomDownRight[VALUE][XAXIS]);
			}
		}
		if (zoomTopLeft[VALUE][Y] < zoomDownRight[VALUE][Y] && zoomTopLeft[VALUE][X] > zoomDownRight[VALUE][X]) {
			setRangeIdeal();
		}
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		zoomDownRight[PIXEL][X] = e.getX();
		zoomDownRight[PIXEL][Y] = e.getY();

		if (connected) {
			plot.setCursorX((int) zoomDownRight[PIXEL][X]);
		}

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	// --------------------------------------------------------------------
	// Add new Slider:
	public void addSlider(int orientation, String tag, Plot plot) {
		this.plot = plot;
		if (sliderActualNumber < MAX_SLIDER) {
			sliders[sliderActualNumber] = new Slider(orientation, this, tag);
			this.add(sliders[sliderActualNumber]);
			sliderActualNumber++;
			repaint();
		}
	}

	// --------------------------------------------------------------------
	// Update Value of Slider:
	public void updateSliderValue(Slider slider) {
		double actualValue;
		if (slider.orienation == Slider.HORIZONTAL) {
			actualValue = axisRangeSector[Y1AXIS][0] + ((boardCorner[1][Y] - slider.positionPixel)
					/ (double) (boardCorner[1][Y] - boardCorner[0][Y])
					* (axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1] - axisRangeSector[Y1AXIS][0]));

			if (actualValue < axisRangeSector[Y1AXIS][0]) {
				actualValue = axisRangeSector[Y1AXIS][0];
			}
			if (actualValue > axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1]) {
				actualValue = axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1];
			}

		} else {
			actualValue = axisRangeSector[XAXIS][0] + ((slider.positionPixel - boardCorner[0][X])
					/ (double) (boardCorner[3][X] - boardCorner[0][X])
					* (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1] - axisRangeSector[XAXIS][0]));

			if (actualValue < axisRangeSector[XAXIS][0]) {
				actualValue = axisRangeSector[XAXIS][0];
			}
			if (actualValue > axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1]) {
				actualValue = axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1];
			}
		}

		switch (slider.tag) {
		case "Start":
			sliderStartValue = actualValue;
			break;
		case "End":
			sliderEndValue = actualValue;
			break;
		case "Offset":
			sliderOffsetValue = actualValue;
			break;
		}

		plot.updateSliderValue(sliderStartValue/* - stepTime */, sliderEndValue, sliderOffsetValue);

	}

	// // --------------------------------------------------------------------
	// // Set Step Position:
	// public void setStepPosition(double position) {
	// stepTime = position;
	// }

	// --------------------------------------------------------------------
	// Set Slider Position:
	public void setSliderPosition(String tag, double value) {
		for (int i = 0; i < sliderActualNumber; i++) {
			if (sliders[i].tag == tag) {

				switch (sliders[i].tag) {
				case OutputCardBearbeiten.KEY_START:
					sliderStartValue = value;// + stepTime;
					break;
				case OutputCardBearbeiten.KEY_END:
					sliderEndValue = value;
					break;
				case OutputCardBearbeiten.KEY_OFFSET:
					sliderOffsetValue = value;
					break;
				}
			}
		}
	}

	// --------------------------------------------------------------------
	// Set Slider Position:
	private void setSliderPosition(String tag) {
		for (int i = 0; i < sliderActualNumber; i++) {
			if (sliders[i].tag == tag) {

				double value = 0;
				switch (sliders[i].tag) {
				case OutputCardBearbeiten.KEY_START:
					value = sliderStartValue;
					break;
				case OutputCardBearbeiten.KEY_END:
					value = sliderEndValue;
					break;
				case OutputCardBearbeiten.KEY_OFFSET:
					value = sliderOffsetValue;
					break;
				}

				if (sliders[i].orienation == Slider.HORIZONTAL) {
					sliders[i].positionPixel = -(int) ((((value - axisRangeSector[Y1AXIS][0])
							/ (axisRangeSector[Y1AXIS][axisRangeActualSectorNumber[Y1AXIS] - 1]
									- axisRangeSector[Y1AXIS][0]))
							* ((double) (boardCorner[1][Y] - boardCorner[0][Y]))) - boardCorner[1][Y]);
				} else {

					sliders[i].positionPixel = (int) ((value - axisRangeSector[XAXIS][0])
							/ (axisRangeSector[XAXIS][axisRangeActualSectorNumber[XAXIS] - 1]
									- axisRangeSector[XAXIS][0])
							* ((double) (boardCorner[3][X] - boardCorner[0][X])) + boardCorner[0][X]);

				}
			}
		}
	}
}
