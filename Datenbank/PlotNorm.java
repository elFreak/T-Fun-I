package model;

public class PlotNorm {
	
	//Methode plotnorm um die Messpunkte auf einen bestimmten Wert zu normieren
	public static double[] plotnorm(double[][] plot) {
		//Wert value für die Anzahl Messewerte die herausgegeben werden
		final int value = 2500;
		double plotn[]=new double[value];
		
		
		//Anzahl Messwerte, durch verdoppeln einzelner Messwerte, auf den Wert value setzen.
		if (plot[1].length <value && (value/2)<=plot[1].length) {
			
			int k[] = new int[value-plot[1].length];
			
			//Messpunkte die verdoppelt werden ermitteln und in k schreiben
			for (int i = 0; i < (value-plot[1].length); i++) {
				k[i] = (int)(Math.abs((1/(1-(plot[1].length/(double)value))*(i+1))));
			}
			
			//Messpunkte im Punkt k verdoppeln und alle weiterfolgenden dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < (value*2)+plot[1].length; i++) {
				for (int j = 0; j < k.length; j++) {
					if(k[j]==(i+1)){
						t++;
					}
				}
				if((i-t)<plot[1].length)
				plotn[i] = plot[1][i-t];
			}
			
		}
		
		//Anzahl Messwerte, durch verdoppeln aller Messwerte und verdrei- und vervierfachen einzelner Werte,
		//auf den Wert value setzen.
		else if ((value/2)>plot[1].length) {
			
			double plott[]=new double[plot[1].length*2];
			
			int k[] = new int[(plot[1].length)];
			
			//Messpunkte die verdoppelt werden ermitteln und in k schreiben.
			for (int i = 0; i < (plot[1].length); i++) {
				k[i] = (int)(Math.abs((1/(1-(plot[1].length/(double)(plot[1].length*2)))*(i+1))));
			}
			
			//Messpunkte im Punkt k verdoppeln und alle weiterfolgenden dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < (plot[1].length*4)+(plot[1].length); i++) {
				for (int j = 0; j < (plot[1].length); j++) {
					if(k[j]==(i+1)){
						t++;
					}
				}
				if((i-t)<(plot[1].length))
				plott[i] = plot[1][i-t];
			}
			
			//Messpunkte die verdrei- und vervierfacht werden ermitteln und in kk schreiben.
			int kk[] = new int[value-plott.length];
			for (int i = 0; i < (value-plott.length); i++) {
				kk[i] = (int)(Math.abs((1/(1-(plott.length/(double)value))*(i+1))));
			}
			
			//Messpunkte im Punkt kk verdoppeln und alle weiterfolgenden dementsprechend verschieben.
			int tt = 0;
			for (int i = 0; i < (value*2)+plott.length; i++) {
				for (int j = 0; j < kk.length; j++) {
					if(kk[j]==(i+1)){
						tt++;
					}
				}
				if((i-tt)<plott.length)
				plotn[i] = plott[i-tt];
			}
		}
		
		//Anzahl Messwerte, durch wegfallen einzelner Werte auf Wert value setzten.
		else if (plot[1].length >value) {
			
			int k[] = new int[plot[1].length-value];
			
			//Messpunkte die gesstrichen werden ermitteln und in k schreiben.
			for (int i = 0; i < (plot[1].length-value); i++) {
				k[i] = (int)(Math.abs((1/(1-(plot[1].length/(double)value))*(i+1))));
			}
			
			//Messpunkte im Punkt k verdoppeln und alle weiterfolgenden dementsprechend verschieben.
			int t = 0;
			for (int i = 0; i < plot[1].length; i++) {
				for (int j = 0; j < k.length; j++) {
					if(k[j]==i){
						t++;
					}
				}
				if((i+t)<plot[1].length)
				plotn[i] = plot[1][i+t];
			}
		}
		
		//Messwerte alle übernehmen.
		else {
			for (int i = 0; i < plot[1].length; i++) {
				plotn[i] = plot[1][i];
			}
		}
		
		//neue Messpunkte übernehmen.
		return plotn;
	}
}
