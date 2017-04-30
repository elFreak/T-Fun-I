import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
 
public class Speicher extends JFrame {
 
	private String text = "this is a Textpane";
    private JTextPane textPane;
    private JScrollPane scrollPane;
 
    public Speicher() {
    	
    	File f = new File("Speicher");
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
             
        // root
        Document doc = builder.newDocument();
        Element root = doc.createElement("root");
        doc.appendChild(root);
 
            // <value>1.0</value>
            Element v = doc.createElement("value");
            v.appendChild(doc.createTextNode("1.0"));
            root.appendChild(v);
    	
        //super("Speicher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        scrollPane = new JScrollPane();
 
        textPane = new JTextPane();
        textPane.setEditable(true);
 
      /*  try {
            textPane.setPage(new URL("C:/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
       
        textPane.setPreferredSize(new Dimension(320, 240));
        textPane.setText(text);
        scrollPane.getViewport().add(textPane);
       
        getContentPane().add(scrollPane);
       
        pack();
        setVisible(true);
        StatusBar.showStatus("Speicher initialisiert :P");
    }
 
    public static void main(String[] args) {
        new Speicher();
    }
}

