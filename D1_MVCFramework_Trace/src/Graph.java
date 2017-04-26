import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Graph extends JPanel {
	int x =1;
	
	public Graph(Controller controller){
		setBackground(Color.RED);
		switch(x){
		case 1:
			//Plot.plotfunktion.add(new JLabel("Graph 1"));
			break;
		case 2:
			//Plot.plotfunktion.add(new JLabel("Graph 2"));
			break;
		
		}
		
	}

    public void paintComponent(Graphics g) {
    	g.drawLine(10, 10, 10, 100);
    	g.drawLine(10, 100, 100, 100);
    }
}