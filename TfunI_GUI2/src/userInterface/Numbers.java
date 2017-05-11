package userInterface;

public class Numbers {

	public double number;
	public String unit;

	public void Number(double number, String unit, int digits) {
		int factor = (int) (((int) Math.log10(number)) / 3) * 3;
		number *= Math.log10(-factor);
		if (number >= 100) {
			number = Math.floor(number * 1) / 1;
		}
		if (number >= 10) {
			number = Math.floor(number * 10) / 10;
		}
		if (number >= 1) {
			number = Math.floor(number * 100) / 100;
		}

		unit = ("" + getPrefix(factor) + unit);

	}

	// --------------------------------------------------------------------
	// Calculate Prefix:
	private String getPrefix(int potency) {
		String k = "";
		switch (potency) {
		case -12:
			k = "p";
			break;
		case -9:
			k = "n";
			break;
		case -6:
			k = "u";
			break;
		case -3:
			k = "m";
			break;
		case 0:
			k = "";
			break;
		case 3:
			k = "k";
			break;
		case 6:
			k = "M";
			break;
		case 9:
			k = "G";
			break;
		case 12:
			k = "T";
			break;
		}
		return k;
	}

}
