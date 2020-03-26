package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public class Osobnik {

	private ArrayList<Miasto> miasta;
	
	public Osobnik(ArrayList<Miasto> m) {
		this.miasta = m;
	}

	public ArrayList<Miasto> getMiasta() {
		return miasta;
	}

	public void setMiasta(ArrayList<Miasto> miasta) {
		this.miasta = miasta;
	}
	
	public double obliczDlugoscTrasy(double[][] tablicaOdleglosci) {
		double dlugosc = 0.0;
		
		for(int i=0; i<miasta.size()-1; i++) {
			dlugosc += tablicaOdleglosci[miasta.get(i).getNumer()-1][miasta.get(i+1).getNumer()-1];
		}
		
		dlugosc += tablicaOdleglosci[miasta.get(0).getNumer()-1][miasta.get(miasta.size()-1).getNumer()-1];
		
		return new BigDecimal(dlugosc).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
}
