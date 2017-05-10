package userInterface;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import projectT_Fun_I.GlobalSettings;

public class MyBorderFactory {

	public static Border createMyBorder(String title) {
		Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		Border titled = BorderFactory.createTitledBorder(loweredetched, title, 2, 2, GlobalSettings.fontText,
				GlobalSettings.colorText);
		return titled;
	}
}
