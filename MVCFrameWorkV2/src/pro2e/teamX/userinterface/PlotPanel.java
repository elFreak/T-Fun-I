package pro2e.teamX.userinterface;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import pro2e.teamX.DPIFixV3;
import pro2e.teamX.matlabfunctions.Matlab;

public class PlotPanel extends JPanel {
	private static final long serialVersionUID = -4522467773085225830L;
	private JFreeChart chart = ChartFactory.createXYLineChart("Titel", "", "", null, PlotOrientation.VERTICAL, false, false,
			false);

	public PlotPanel() {
		super(new BorderLayout());
		setPreferredSize(new Dimension(400, 150));

		// Farben und Settings
		chart.setBackgroundPaint(getBackground());
		XYPlot xyplot = chart.getXYPlot();
		xyplot.setBackgroundPaint(getBackground());
		xyplot.setRangeGridlinePaint(Color.black);
		xyplot.setDomainGridlinePaint(Color.black);

		ValueAxis xAxis = xyplot.getDomainAxis(0);
		xAxis.setRange(0, 1001);
		xAxis.setAutoRange(false);
		xAxis.setTickLabelsVisible(true);

		ValueAxis yAxis = xyplot.getRangeAxis(0);
		yAxis.setRange(-2.0, 2.0);
		yAxis.setAutoRange(false);
		yAxis.setTickLabelsVisible(true);

		XYItemRenderer renderer = new StandardXYItemRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		renderer.setSeriesPaint(0, Color.black);
		xyplot.setRenderer(0, renderer);

		renderer = new StandardXYItemRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
		renderer.setSeriesPaint(0, Color.green);
		xyplot.setRenderer(1, renderer);

		add(new ChartPanel(chart));
		JFreeChartDPIFix.applyChartTheme(chart);

		// Test:
		update(null, null);
	}

	public void setData(double[] x, double[] y1, double[] y2) {
		XYSeries series = new XYSeries("Plot1");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y1[i]);
		}
		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		chart.getXYPlot().setDataset(0, dataset);

		series = new XYSeries("Plot2");
		for (int i = 1; i < x.length; i++) {
			series.add(x[i], y2[i]);
		}
		dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		chart.getXYPlot().setDataset(1, dataset);

		repaint();
	}

	public void update(Observable obs, Object obj) {
		double f = 1e3;
		double fs = 1e4;
		double T = 1 / fs;

		double[] n = Matlab.linspace(0, 1000, 1001);

		double[] cos = new double[n.length];
		double[] sin = new double[n.length];

		for (int i = 0; i < n.length; i++) {
			cos[i] = Math.cos(2 * Math.PI * f * n[i] * T);
			sin[i] = Math.sin(2 * Math.PI * f * n[i] * T);
		}

		setData(n, cos, sin);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

				} catch (Exception exception) {
					exception.printStackTrace();
				}
				JFrame frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setTitle("MVCFramework");
				frame.add(new PlotPanel());
				DPIFixV3.scaleSwingFonts();
				SwingUtilities.updateComponentTreeUI(frame);
				frame.pack();
				frame.setVisible(true);
			}
		});
	}

}
