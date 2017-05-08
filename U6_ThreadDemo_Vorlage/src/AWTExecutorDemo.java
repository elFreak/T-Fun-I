import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AWTExecutorDemo extends Frame implements ActionListener,
		WindowListener {
	private static final long serialVersionUID = 1L;
	private Button start, stop;
	private RunnableBall[] ball = new RunnableBall[100];
	private int i = 0;
	private ExecutorService eService = Executors.newFixedThreadPool(8);

	public AWTExecutorDemo() {
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
			ball[i]=new RunnableBall(getGraphics(),i);
			eService.execute(ball[i]);
			i++;
		}
		if (e.getSource() == stop) {
			if (i > 0) {
				i--;
				ball[i].stopp();
			}
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
		AWTExecutorDemo f = new AWTExecutorDemo();
		f.setSize(400, 300);
		f.setVisible(true);
		f.setBackground(Color.white);
	}

}
