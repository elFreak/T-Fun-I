package userInterface.IOCard;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import model.Model;
import model.UTFDatatype;
import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;

public class KoefPanel extends JPanel implements ActionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;

	private JPanel pnEquation = new JPanel();
	private JLabel lbKorel = new JLabel("null");
	private JScrollPane spKoef;
	private JPanel pnKoef = new JPanel();
	private JLabel lbwp[] = new JLabel[5];
	private JTextField tfwp[] = new JTextField[5];
	private JLabel lbqp[] = new JLabel[5];
	private JTextField tfqp[] = new JTextField[5];
	private JLabel lbsigma = new JLabel("<html>&#x03c3;</html>");
	private JTextField tfsigma = new JTextField();
	private JLabel lbk = new JLabel("k");
	private JTextField tfk = new JTextField();

	private UTFDatatype utf;
	private int order = 0;

	private double korrKoef;

	private Controller controller;

	/**
	 * 
	 */
	public KoefPanel(Controller controller) {
		this.controller = controller;
		setLayout(new GridLayout(2, 1));
		// ------------------------------------------------------------------------------------------------------------------------------
		// pnEquation
		pnEquation.setLayout(new GridBagLayout());

		// try {
		// equation = ImageIO.read(new File("path-to-file"));
		pnEquation.add(new JLabel("equation"/* new ImageIcon(equation) */), new GridBagConstraints(0, 0, 1, 3, 1.0, 1.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		// } catch (IOException e) {
		// pnKoef.add(new JLabel(""), new GridBagConstraints(0, 0, 1, 1, 1.0,
		// 1.0, GridBagConstraints.CENTER,
		// GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		// }
		// pnKoef.add(new JLabel(new ImageIcon(equation)), new
		// GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
		// GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10,
		// 10, 10, 10), 0, 0));
		pnEquation.add(new JLabel("Korrelation:"), new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0,
				GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		pnEquation.add(lbKorel, new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		pnEquation.add(new JLabel(""), new GridBagConstraints(1, 2, 1, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		// ------------------------------------------------------------------------------------------------------------------------------
		// pnKoef
		pnKoef.setLayout(new GridBagLayout());

		for (int i = 0; i < tfwp.length; i++) {
			lbwp[i] = new JLabel("<html>&#x3c9;<sub>p" + i + "</sub> </html>");
			lbwp[i].setFont(GlobalSettings.fontMath);
			pnKoef.add(lbwp[i], new GridBagConstraints(0, i, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

			tfwp[i] = new JTextField();
			tfwp[i].setEditable(false);
			tfwp[i].addActionListener(this);
			tfwp[i].addMouseWheelListener(this);
			pnKoef.add(tfwp[i], new GridBagConstraints(1, i, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

			lbqp[i] = new JLabel("<html>q<sub>p" + i + "</sub> </html>");
			lbqp[i].setFont(GlobalSettings.fontMath);
			pnKoef.add(lbqp[i], new GridBagConstraints(2, i, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

			tfqp[i] = new JTextField();
			tfqp[i].setEditable(false);
			tfqp[i].addActionListener(this);
			tfqp[i].addMouseWheelListener(this);
			pnKoef.add(tfqp[i], new GridBagConstraints(3, i, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
					GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		}

		lbsigma.setFont(GlobalSettings.fontMath);
		pnKoef.add(lbsigma, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

		tfsigma.setEditable(false);
		tfsigma.addActionListener(this);
		tfsigma.addMouseWheelListener(this);
		pnKoef.add(tfsigma, new GridBagConstraints(5, 0, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		lbk.setFont(GlobalSettings.fontMath);
		pnKoef.add(lbk, new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

		tfk.setEditable(false);
		tfk.addActionListener(this);
		tfk.addMouseWheelListener(this);
		pnKoef.add(tfk, new GridBagConstraints(5, 1, 1, 1, 1.0, 0.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

		pnKoef.add(new JLabel(""), new GridBagConstraints(0, 5, 6, 1, 1.0, 1.0, GridBagConstraints.FIRST_LINE_START,
				GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
		// ------------------------------------------------------------------------------------------------------------------------------

		spKoef = new JScrollPane(pnKoef);
		pnKoef.setBorder(MyBorderFactory.createMyBorder("Koeffizienten"));

		this.add(pnEquation);
		this.add(spKoef);

	}

	public void setOrdnung(int order) {
		System.out.println("order" + order);
		this.order = order;
	}

	public void update(Object obs) {
		Model model = (Model) obs;
		if (order < 1 || order > 10)
			return;
		// else
		if (model.network.getApprox(order) == null) {
			for (int i = 0; i < tfwp.length; i++) {
				tfwp[i].setEditable(false);
				tfwp[i].setText("");
				tfqp[i].setEditable(false);
				tfqp[i].setText("");
			}
			tfsigma.setEditable(false);
			tfsigma.setText("");
			tfk.setEditable(false);
			tfk.setText("");
		} else {
			utf = model.network.getApprox(order).getUtf();
			korrKoef = model.network.getApprox(order).getKorrKoef();
			lbKorel.setText("" + korrKoef);
			for (int i = 0; i < tfwp.length; i++) {
				if (i < utf.ordnung / 2) {
					tfwp[i].setEditable(true);
					tfwp[i].setText("" + utf.koeffWQ[i * 2]);
					tfqp[i].setEditable(true);
					tfqp[i].setText("" + utf.koeffWQ[i * 2 + 1]);

				} else {
					tfwp[i].setEditable(false);
					tfwp[i].setText("");
					tfqp[i].setEditable(false);
					tfqp[i].setText("");
				}
			}
			if (utf.ordnung % 2 == 0) {
				tfsigma.setEditable(false);
				tfsigma.setText("");
			} else {
				tfsigma.setEditable(true);
				tfsigma.setText("" + utf.sigma);
			}
			tfk.setEditable(true);

		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		UTFDatatype utf = new UTFDatatype();
		utf.ordnung = order;
		utf.zaehler = Double.parseDouble(tfk.getText());
		for (int i = 0; i < tfwp.length; i++) {
			utf.koeffWQ = new double[10];
			utf.koeffWQ[i * 2] = Double.parseDouble(tfwp[i].getText());
			utf.koeffWQ[i * 2 + 1] = Double.parseDouble(tfqp[i].getText());
		}
		if (utf.ordnung % 2 == 0) {
			utf.sigma = Double.parseDouble(tfsigma.getText());
		}
		controller.changeApproximation(utf);

	}
}
