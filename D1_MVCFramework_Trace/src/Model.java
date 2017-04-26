import java.util.Observable;

public class Model extends Observable {
	private Trace trace = new Trace(this);
	private String data = "";
	

	public Model() {
		trace.constructorCall();
	}

	public void setData(String data) {
		trace.methodeCall();
		this.data = data.toUpperCase();
		notifyObservers();
	}

	public String getData() {
		trace.methodeCall();
		return data;
	}

	public void notifyObservers() {
		trace.methodeCall();
		setChanged();
		super.notifyObservers();
	}
}