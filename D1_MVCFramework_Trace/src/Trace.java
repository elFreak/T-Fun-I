class Trace {
	private String className;
	private String hashCode;
	private static boolean showTrace = true;

	// Some virtual machines may, under some circumstances, omit one or more
	// stack frames from the stack trace. In the extreme case, a virtual machine
	// that has no stack trace information concerning this thread is permitted
	// to return a zero-length array from this method.

	public Trace(String className, int hashCode) {
		this.className = className;
		this.hashCode = "" + hashCode;
		if (showTrace) {
//			System.out.println("Konstruktor " + className + "():" + className + "@" + hashCode + " wird aufgerufen ...");
			System.out.println("Attribute von " + className + "@" + hashCode + " werden erzeugt ...");
		}
	}

	public Trace(Object obj) {
		this.className = obj.getClass().getName();
		this.hashCode = "" + obj.hashCode();
		if (showTrace) {
//			System.out.println("Konstruktor " + className + "():" + className + "@" + hashCode + " wird aufgerufen ...");
			System.out.println("Attribute von " + className + "@" + hashCode + " werden erzeugt ...");
		}
	}

	public Trace(Object obj, boolean show) {
		this.className = obj.getClass().getName();
		this.hashCode = "" + obj.hashCode();
		showTrace = show;
		if (showTrace) {
//			System.out.println("Konstruktor " + className + "():" + className + "@" + hashCode + " wird aufgerufen ...");
			System.out.println("Attribute von " + className + "@" + hashCode + " werden erzeugt ...");
		}
	}

	public static void mainCall(boolean show) {
		showTrace = show;
		if (showTrace)
			System.out.println("main(String args[])");
	}

	public void constructorCall() {
		if (showTrace) {
			System.out.println("Konstruktor " + className + "():" + className + "@" + hashCode
					+ " wird ausgeführt ...");
		}
	}

	public void constructorCall(String constructor) {
		if (showTrace)
			System.out.println("Konstruktor "  + constructor + ":" + className + "@" + hashCode
					+ " wird ausgeführt ...");
	}

	public void methodeCall() {
		String methode = getMethodName(3);
		if (showTrace)
			System.out.println("Methode " + className + "@" + hashCode + "." + methode + "()" + " wird ausgeführt ...");
	}

	public void methodeCall(String methode) {
		if (showTrace)
			System.out.println("Methode " + className + "@" + hashCode + "." + methode + " wird ausgeführt ...");
	}

	public void eventCall() {
		String methode = getMethodName(3);
		if (showTrace)
			System.out.println("\n" + "Methode " + className + "@" + hashCode + "." + methode + "()"
					+ " wird durch Ereignis ausgelöst ...");
	}

	public void eventCall(String methode) {
		if (showTrace)
			System.out.println(
					"\n" + "Methode " + className + "@" + hashCode + "." + methode + " wird durch Ereignis ausgelöst ...");
	}

	public static String getMethodName(int depth) {
		StackTraceElement[] ste = Thread.currentThread().getStackTrace();

		// for (int i = 0; i < ste.length; i++) {
		// System.out.println(ste[i].getMethodName());
		// }

		if (ste != null)
			return ste[depth].getMethodName();

		return "VM does not provied a Stack-Trace";
	}
}