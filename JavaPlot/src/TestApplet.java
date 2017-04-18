import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JApplet;



import JavaPlot.Plot;
import JavaPlot.Trace;

public class TestApplet extends JApplet {
	private static final long serialVersionUID = 1L;

	// --------------------------------------------------------------------
	// Test class Plot:
	private Plot testPlot = new Plot();

	// --------------------------------------------------------------------
	// Initialize the object:
	public TestApplet() {
		setLayout(new GridLayout(1, 1));
		add(testPlot);
		
		
		//Test:!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Trace testTrace = new Trace();
		testTrace.yaxis = Trace.Y1AXIS;
		testTrace.data = new double[1500][2];
		testTrace.preferedColor = new Color(50, 50, 240);
		testTrace.usePreferedColor = true;
		
		for(int i=0;i<testTrace.data.length;i++) {
			testTrace.data[i][Trace.X] = i;
			testTrace.data[i][Trace.Y] = Math.sin(i/10.0);
		}
		
		Trace testTrace2 = new Trace();
		testTrace2.yaxis = Trace.Y2AXIS;
		testTrace2.data = new double[1500][2];
		
		for(int i=0;i<testTrace2.data.length;i++) {
			testTrace2.data[i][Trace.X] = i;
			testTrace2.data[i][Trace.Y] = 100000+(50*Math.sin(i/50.0*Math.PI));
		}
		
		Trace testTrace3 = new Trace();
		testTrace3.yaxis = Trace.Y1AXIS;
		testTrace3.data = new double[1500][2];
		
		for(int i=0;i<testTrace2.data.length;i++) {
			testTrace3.data[i][Trace.X] = i;
			testTrace3.data[i][Trace.Y] = 18600+i;
		}
		testTrace2.lineType = Trace.LINE_NONE;
		testTrace2.pointType = Trace.POINT_CROSS;
		
		testPlot.addTrace(testTrace3);
		testPlot.addTrace(testTrace2);
		testPlot.addSubplot();
		testPlot.addTrace(testTrace);
		testPlot.connectSubplots();
		testPlot.setRangeIdeal();
		testPlot.setAxisLabel(Plot.XAXIS,"Zeit t", "", "s");
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	// --------------------------------------------------------------------
	// Paint the object:
	public void paint(Graphics g) {
		super.paint(g);
	}
}
