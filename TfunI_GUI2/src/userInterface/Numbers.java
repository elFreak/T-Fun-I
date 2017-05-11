package userInterface;

public class Numbers {

	public double number;
	public String unit;

	public Numbers(double number) {
		int factor=0;
		if (number == 0) {
			number = 0;
		} else {
			boolean negative = false;
			if(number<0){
				negative = true;
				number*=-1;
			}
			factor = (int)(Math.floor(Math.floor(Math.log10(number))/3)*3);
			number *= Math.pow(10,-factor);
			if (number >= 100) {
				number = Math.floor(number * 1) / 1;
			}
			if (number >= 10) {
				number = Math.floor(number * 10) / 10;
			}
			if (number >= 1) {
				number = Math.floor(number * 100) / 100;
			}
			if(negative == true){
				number*=-1;
			}

		}
		this.number=number;
		unit = getPrefix(factor);
	}

	// --------------------------------------------------------------------
	// Calculate Prefix:
	private String getPrefix(int potency) {
		String k = "";
		switch (potency) {
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
