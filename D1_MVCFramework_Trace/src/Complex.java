/**
 * Klasse zur Repräsentation von komplexen Zahlen mit zugehörigen Methoden.
 * 
 * @author Richard Gut
 * 
 */
public class Complex {
	public double re;
	public double im;

	public Complex() {
		this(0.0, 0.0);
	}

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public Complex(double re) {
		this(re, 0.0);
	}

	public Complex(Complex z) {
		this(z.re, z.im);
	}

	public Complex pow(double x) {
		return new Complex(Math.pow(this.abs(), x) * Math.cos(x * angle()),
				Math.pow(this.abs(), x) * Math.sin(x * angle()));
	}

	public Complex add(Complex z) {
		return new Complex(this.re + z.re, this.im + z.im);
	}

	public Complex sub(Complex z) {
		return new Complex(this.re - z.re, this.im - z.im);
	}

	public Complex mul(Complex z) {
		return new Complex((re * z.re) - (im * z.im), (re * z.im) + (im * z.re));
	}

	public Complex mul(double z) {
		return new Complex((re * z), (im * z));
	}

	public Complex div(Complex b) {
		return new Complex((this.abs() / b.abs()) * Math.cos(angle() - b.angle()),
				(this.abs() / b.abs()) * Math.sin(angle() - b.angle()));
	}

	public Complex div(double b) {
		return new Complex(this.re / b, this.im / b);
	}

	public double abs() {
		return Math.sqrt(re * re + im * im);
	}

	public double angle() {
		return Math.atan2(im, re);
	}

	public static double[] angle(Complex[] c) {
		double[] res = new double[c.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = c[i].angle();
		}
		return res;
	}

	public static double[] abs(Complex[] c) {
		double[] res = new double[c.length];
		for (int i = 0; i < c.length; i++) {
			res[i] = c[i].abs();
		}
		return res;
	}

	public String toString() {
		return "Re: " + this.re + " Im: " + this.im;
	}
}