package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadData {

	public static void main(String args[]) {
		Packs packschreiben = new Packs();
		double[][] plotin = new double[2][1250];
		for(int i = 0; i<1250; i++){
			plotin[0][i]=1;
			plotin[1][i]=1.5+(i/6250);
		}
		
		Packs[] packlesen = new Packs[100];
		for (int i = 0; i < 100; i++) {
			packlesen[i] = new Packs();
		}

		// Packs beschreiben

		packschreiben.zaehler = 8;
		// Startwerte
		for (int i = 0; i < 10; i++) {
			packschreiben.startwerte[i] = 15-i;
		}
		// Sigma
		packschreiben.sigma = 6;
		// Messwerte
		for (int i = 0; i < 2500; i++) {
			packschreiben.doubl[i] = 1.5+i/12500.0;
		}

		 Datafile.setdata(packschreiben);
		//Saveplot.saveplot(packschreiben);
		packlesen = Datafile.getdata();

		for (int j = 0; j < 100; j++) {
			if (packlesen[j].zaehler != 0) {
				
				System.out.println(packlesen[j].zaehler + "    Zaehler");
				for (int i = 0; i < 10; i++) {
					System.out.println(packlesen[j].startwerte[i]);
				}
				System.out.println("--------------Startwerte------------");
				System.out.println(packlesen[j].sigma + " :   Sigma");
				for (int i = 0; i < 2500; i++) {
					System.out.println(packlesen[j].doubl[i]);
				}
				System.out.println("--------------Messwerte-------------");
			}
		}
	}

}
