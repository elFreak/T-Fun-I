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
import javax.swing.SwingConstants;

import model.Model;
import model.UTFDatatype;
import projectT_Fun_I.GlobalSettings;
import userInterface.Controller;
import userInterface.MyBorderFactory;
import userInterface.Numbers;
import userInterface.StatusBar;

public class KoefPanel extends JPanel implements ActionListener, MouseWheelListener {
	private static final long serialVersionUID = 1L;

	private JLabel lbKorel = new JLabel("null");
	private JScrollPane spKoef;
	private JPanel pnInfo = new JPanel();
	private JPanel pnKoef = new JPanel();
	private JLabel lbwp[] = new JLabel[5];
	private JTextField tfwp[] = new JTextField[5];
	private JLabel lbqp[] = new JLabel[5];
	private JTextField tfqp[] = new JTextField[5];
	private JLabel lbsigma = new JLabel("<html>&#x03c3;</html>", SwingConstants.CENTER);
	private JTextField tfsigma = new JTextField();
	private JLabel lbk = new JLabel("K", SwingConstants.CENTER);
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
		setLayout(new GridBagLayout());
		setBackground(GlobalSettings.colorBackgroundWhite);

		// ------------------------------------------------------------------------------------------------------------------------------
		// pnInfo
		pnInfo.setLayout(new GridBagLayout());
		pnInfo.setBackground(GlobalSettings.colorBackgroundWhite);

		pnInfo.add(new JLabel("Korrelationskoeffizient: ", SwingConstants.LEFT), new GridBagConstraints(0, 0, 1, 1, 0.0,
				0.0, GridBagConstraints.LINE_START, GridBagConstraints.NONE, new Insets(5, 5, 20, 5), 0, 0));
		pnInfo.add(lbKorel, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0, GridBagConstraints.LINE_START,
				GridBagConstraints.HORIZONTAL, new Insets(5, 5, 20, 5), 0, 0));

		pnInfo.setBorder(MyBorderFactory.createMyBorder("Kenngrössen"));

		// ------------------------------------------------------------------------------------------------------------------------------
		// pnKoef
		pnKoef.setLayout(new GridLayout(7, 2, 5, 5));
		pnKoef.setBackground(GlobalSettings.colorBackgroundWhite);

		lbk.setFont(GlobalSettings.fontMath);
		pnKoef.add(lbk);

		tfk.setEditable(false);
		tfk.addActionListener(this);
		tfk.addMouseWheelListener(this);
		pnKoef.add(tfk);

		lbsigma.setFont(GlobalSettings.fontMath);
		pnKoef.add(lbsigma);

		tfsigma.setEditable(false);
		tfsigma.addActionListener(this);
		tfsigma.addMouseWheelListener(this);
		pnKoef.add(tfsigma);

		for (int i = 0; i < tfwp.length; i++) {
			lbwp[i] = new JLabel("<html>&#x3c9;<sub>p" + i + "</sub> </html>", SwingConstants.CENTER);
			lbwp[i].setFont(GlobalSettings.fontMath);
			pnKoef.add(lbwp[i]);

			tfwp[i] = new JTextField();
			tfwp[i].setEditable(false);
			tfwp[i].addActionListener(this);
			tfwp[i].addMouseWheelListener(this);

			pnKoef.add(tfwp[i]);

			lbqp[i] = new JLabel("<html>q<sub>p" + i + "</sub> </html>", SwingConstants.CENTER);
			lbqp[i].setFont(GlobalSettings.fontMath);
			pnKoef.add(lbqp[i]);

			tfqp[i] = new JTextField();
			tfqp[i].setEditable(false);
			tfqp[i].addActionListener(this);
			tfqp[i].addMouseWheelListener(this);
			pnKoef.add(tfqp[i]);
		}

		// ------------------------------------------------------------------------------------------------------------------------------

		pnKoef.setBorder(MyBorderFactory.createMyBorder("Übertragungsfunktion"));
		add(pnKoef, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));
		add(pnInfo, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 10, 10), 0, 0));

	}

	public void setOrdnung(int order) {
		this.order = order;
	}

	public void update(Object obs, Object obj) {
		Model model = (Model) obs;
		if (order < 1 || order > 10)
			return;
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
			Numbers koeff = new Numbers(korrKoef, 12);
			lbKorel.setText(" " + koeff.number + koeff.unit);
			for (int i = 0; i < tfwp.length; i++) {
				if (i < utf.ordnung / 2) {
					tfwp[i].setEditable(true);
					Numbers wp = new Numbers(utf.koeffWQ[i * 2], 3);
					tfwp[i].setText(" " + wp.number + wp.unit);
					tfqp[i].setEditable(true);
					Numbers qp = new Numbers(utf.koeffWQ[i * 2 + 1], 3);
					tfqp[i].setText(" " + qp.number + qp.unit);

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
				Numbers sigma = new Numbers(utf.sigma, 3);
				tfsigma.setText(" " + sigma.number + sigma.unit);
			}
			tfk.setEditable(true);
			Numbers K = new Numbers(utf.zaehler, 3);
			tfk.setText(" " + K.number + K.unit);
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		JTextField tF = (JTextField) e.getSource();
		double value = Double.parseDouble(tF.getText());
		if (tF.getText().isEmpty())
			tF.setText("0.0");

		value += e.getWheelRotation() * value * 0.01;

		tF.setText(String.valueOf(value));

		UTFDatatype new_utf = new UTFDatatype();
		new_utf.ordnung = order;
		new_utf.zaehler = Double.parseDouble(tfk.getText());
		new_utf.koeffWQ = new double[order / 2 * 2];
		if (new_utf.ordnung % 2 == 1) {
			new_utf.sigma = Double.parseDouble(tfsigma.getText());
		}
		for (int i = 0; i < order / 2; i++) {
			if (tfwp[i].getText() != "") {
				new_utf.koeffWQ[i * 2] = Double.parseDouble(tfwp[i].getText());
				new_utf.koeffWQ[i * 2 + 1] = Double.parseDouble(tfqp[i].getText());
			}
		}
		controller.changeApproximation(new_utf);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		UTFDatatype new_utf = new UTFDatatype();
		try {
			if(Double.parseDouble(((JTextField)e.getSource()).getText())==0) {
				throw new NumberFormatException();
			}
			new_utf.ordnung = order;
			new_utf.zaehler = Double.parseDouble(tfk.getText());
			new_utf.koeffWQ = new double[order / 2 * 2];
			if (new_utf.ordnung % 2 == 1) {
				new_utf.sigma = Double.parseDouble(tfsigma.getText());
			}
			for (int i = 0; i < order / 2; i++) {
				if (tfwp[i].getText() != "") {
					new_utf.koeffWQ[i * 2] = Double.parseDouble(tfwp[i].getText());
					new_utf.koeffWQ[i * 2 + 1] = Double.parseDouble(tfqp[i].getText());
				}
			}
			controller.changeApproximation(new_utf);
		} catch (NumberFormatException b) {
			StatusBar.showStatus("Ungültige Eingabe", StatusBar.FEHLER);
			controller.changeApproximation(utf);
		}
		

	}
}
