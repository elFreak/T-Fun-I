package pro2e.teamX;
import java.awt.Color;
import java.io.InputStream;
import java.util.Properties;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class MyNimRODLookAndFeel extends NimRODLookAndFeel {
	private static final long serialVersionUID = 2482367511240474028L;

	public MyNimRODLookAndFeel(String nomFich) {
		String p1, p2, p3, s1, s2, s3, selection, background, w, b, opMenu, opFrame;
		NimRODTheme nt = new NimRODTheme();

		try {
			Properties props = new Properties();
			InputStream res = null;
			try {
				res = MyNimRODLookAndFeel.class.getResourceAsStream("nimRodThemes/" + nomFich);
			} catch (Exception ex) {
				nomFich = (nomFich.equals("NimRODThemeFile.theme") ? "/" + nomFich : nomFich);
				res = this.getClass().getResourceAsStream(nomFich);
			}
			props.load(res);
			selection = props.getProperty("nimrodlf.selection");
			background = props.getProperty("nimrodlf.background");
			p1 = props.getProperty("nimrodlf.p1");
			p2 = props.getProperty("nimrodlf.p2");
			p3 = props.getProperty("nimrodlf.p3");
			s1 = props.getProperty("nimrodlf.s1");
			s2 = props.getProperty("nimrodlf.s2");
			s3 = props.getProperty("nimrodlf.s3");
			w = props.getProperty("nimrodlf.w");
			b = props.getProperty("nimrodlf.b");
			opMenu = props.getProperty("nimrodlf.menuOpacity");
			opFrame = props.getProperty("nimrodlf.frameOpacity");

			if (selection != null) nt.setPrimary(Color.decode(selection));

			if (background != null) nt.setSecondary(Color.decode(background));

			if (p1 != null) nt.setPrimary1(Color.decode(p1));

			if (p2 != null) nt.setPrimary2(Color.decode(p2));

			if (p3 != null) nt.setPrimary3(Color.decode(p3));

			if (s1 != null) nt.setSecondary1(Color.decode(s1));

			if (s2 != null) nt.setSecondary2(Color.decode(s2));

			if (s3 != null) nt.setSecondary3(Color.decode(s3));

			if (w != null) nt.setWhite(Color.decode(w));

			if (b != null) nt.setBlack(Color.decode(b));

			if (opMenu != null) nt.setMenuOpacity(Integer.parseInt(opMenu));

			if (opFrame != null) nt.setFrameOpacity(Integer.parseInt(opFrame));

			NimRODLookAndFeel.setCurrentTheme(nt);

		} catch (Exception ex) {
			nt = new NimRODTheme();
		}
	}
}
