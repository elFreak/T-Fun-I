package model;


public class GetStartValue {
	
	public static double[] startvalue(double[][] plot,  boolean[] ordnung ) {
		
		//Plot auf 2500 werte normen.
		double[] plotn = PlotNorm.plotnorm(plot);
		
		//Datenbank laden.
		Packs[] plotpack = Datafile.getdata();
		
		
		double[] korrKoeffa = new double[plotpack.length];
		double korrKoeff = 0.0;
		int position = 0;
		
		//Ordnung überprüfen und Korrelationskoeffizient überprüfen
		for (int i = 0; i < plotpack.length; i++) {
			if (ordnung[4]==(plotpack[i].ordnung==5) || ordnung[5]==(plotpack[i].ordnung==6) || ordnung[6]==(plotpack[i].ordnung==7) || ordnung[7]==(plotpack[i].ordnung==8) || ordnung[8]==(plotpack[i].ordnung==9) || ordnung[9]==(plotpack[i].ordnung==10)) {
				korrKoeffa[i]= Korrelation.korrKoeff(plotn, plotpack[i].doubl);
				
				//Wenn Korrelationskoeffizient grösser als 0.8, berechnung abbrechen und position speichern
				if (korrKoeffa[i]>0.8){
					korrKoeff = korrKoeffa[i];
					position = i;
					break;
				}
			}
		}
		
		//Wenn kein Korrelationskoeffizient grösser als 0.8, den grössten herausfinden und position abspeichern.
		if (korrKoeff==0) {
			for (int i = 1; i < korrKoeffa.length-1; i++) {
				if (korrKoeffa[i]>korrKoeffa[i-1]){
					korrKoeff=korrKoeffa[i];
					position = i;
				}
			}
		}
		
		//geeignete Startwerte ausgeben mit der vorhin ermittelten position.
		double[] startvalue = new double[12];
		startvalue[0]=plotpack[position].zaehler;
		for (int i = 1; i < 11; i++) {
			startvalue[i]=plotpack[position].startwerte[i-1];
		}
		startvalue[11]=plotpack[position].sigma;
		return startvalue;
	}

	
}
