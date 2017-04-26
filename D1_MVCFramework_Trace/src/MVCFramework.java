import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MVCFramework extends Frame {
	private Trace trace = new Trace(this);
	private static final long serialVersionUID = 1L;
	
	public static int fullscreen = 0;
	
	public MVCFramework() {
		trace.constructorCall();
		setSize(750,600);
		Model model = new Model();
		Controller controller = new Controller(model);
		View view = new View(controller);
		controller.setView(view);
		model.addObserver(view);
		add(view);
		
		setMinimumSize(new Dimension(300,300));
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = getSize();
		if (frameSize.height > screenSize.height) {				
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		setLocation((screenSize.width - frameSize.width), (screenSize.height - frameSize.height) / 2-15);
	}
	
	public void init(){
		
	}
	
/*	public void mvcsize(int full){
		if(full == 1){
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setSize(screenSize.height, screenSize.width);
			setLocation(0,0);
		}
		else{
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = new Dimension(750,600);
			if (frameSize.height > screenSize.height) {				
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			}
			setLocation((screenSize.width - frameSize.width), (screenSize.height - frameSize.height) / 2-15);
		}
	}*/
	
	public static void main(String args[]) {
		Trace.mainCall(true);
		MVCFramework frame = new MVCFramework();
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		frame.setTitle("Pro2E | Team 1 | Project X | GUI | V3");
		frame.setVisible(true);
		
	}
}
