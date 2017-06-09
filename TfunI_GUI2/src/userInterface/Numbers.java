package userInterface;

/**
 * Ermöglicht das automatische skalieren und normieren einer Dezimalzahl.
 * 
 * @author Team 1
 *
 */
public class Numbers {

	public double number;
	public String unit;

	/**
	 * Skaliert und normiert die Übergebene Zahl anhand der Argumente.
	 * 
	 * @param number
	 * @param digits
	 */
	public Numbers(double number, int digits) {
		int factor = 0;
		if (number == 0) {
			number = 0;
		} else {
			boolean negative = false;
			if (number < 0) {
				negative = true;
				number *= -1;
			}
			factor = (int) (Math.floor(Math.floor(Math.log10(number)) / 3.0) * 3.0);
			number *= Math.pow(10, -factor);
			if (number >= 100) {
				number = Math.floor(number * 1 * Math.pow(10, digits / 3)) / (1 * Math.pow(10, digits / 3));
			}
			if (number >= 10) {
				number = Math.floor(number * 10 * Math.pow(10, digits / 3)) / (10 * Math.pow(10, digits / 3));
			}
			if (number >= 1) {
				number = Math.floor(number * 100 * Math.pow(10, digits / 3)) / (100 * Math.pow(10, digits / 3));
			}
			if (negative == true) {
				number *= -1;
			}

		}
		this.number = number;
		unit = getPrefix(factor);
	}

	// --------------------------------------------------------------------
	// Calculate Prefix:
	private String getPrefix(int potency) {
		String k = "";
		switch (potency) {
		case -24:
			k = "e-24";
			break;
		case -21:
			k = "e-21";
			break;
		case -18:
			k = "e-18";
			break;
		case -15:
			k = "e-15";
			break;
		case -12:
			k = "e-12";
			break;
		case -9:
			k = "e-9";
			break;
		case -6:
			k = "e-6";
			break;
		case -3:
			k = "e-3";
			break;
		case 0:
			k = "";
			break;
		case 3:
			k = "e3";
			break;
		case 6:
			k = "e6";
			break;
		case 9:
			k = "e9";
			break;
		case 12:
			k = "e12";
			break;

		}
		return k;
	}

}
