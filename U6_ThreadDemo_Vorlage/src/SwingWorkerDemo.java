import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class SwingWorkerDemo extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;
	private Button start, stop;
	private int i = 0;
	public SwingWorkerBall[] ball = new SwingWorkerBall[100];

	public SwingWorkerDemo() {
		setLayout(new FlowLayout());
		start = new Button("Start");
		add(start);
		start.addActionListener(this);
		stop = new Button("Stop");
		add(stop);
		stop.addActionListener(this);
		this.addWindowListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			ball[i]=new SwingWorkerBall(getGraphics(), i);
			ball[i++].execute();
		}
		if (e.getSource() == stop) {
			if (i == 0)
				return;
			ball[--i].stopp();
		}
	}

	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
				SwingWorkerDemo f = new SwingWorkerDemo();
				f.setSize(400, 300);
				f.setVisible(true);
				f.setBackground(Color.white);
			}
		});
	}

}
