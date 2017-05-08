package pro2e.teamX.userinterface;

import pro2e.teamX.MVCFramework;
import pro2e.teamX.model.Model;

public class Controller {
	private Model model;
	private MVCFramework mvcFramework;

	public Controller(Model model, MVCFramework mvcFramework) {
		this.model = model;
		this.mvcFramework = mvcFramework;

	}
}
