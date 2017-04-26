public class Controller {
	private Trace trace = new Trace(this);
	private Model model;
	private View view;

	public Controller(Model model) {
		super();
		trace.constructorCall();
		this.model = model;
	}

	public void btAction(String stInfo) {
		trace.methodeCall();
		model.setData(stInfo);
		view.tf1.setText("");
		view.tf1.requestFocus();
	}

	public void setView(View view) {
		trace.methodeCall();
		this.view = view;
	}
}
