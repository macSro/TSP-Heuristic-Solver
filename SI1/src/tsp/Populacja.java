package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import tsp_narzedzia.Narzedzia;

public class Populacja {

	private int rozmiar;
	private Instancja instancja;
	private ArrayList<Osobnik> osobniki;

	public Populacja(Instancja i) {
		this.instancja = i;
		this.rozmiar = 0;
		this.osobniki = new ArrayList<Osobnik>();
	}
	
	public Populacja(int r, Instancja i) {
		this.rozmiar = r;
		this.instancja = i;
		this.osobniki = stworzOsobniki();
	}
	
	private ArrayList<Osobnik> stworzOsobniki() {
		ArrayList<Osobnik> osobniki = new ArrayList<Osobnik>(this.rozmiar);
		ArrayList<Miasto> miasta = new ArrayList<Miasto>(this.instancja.getMiasta().size());
		
		for(int i=0; i<this.rozmiar; i++) {
			miasta = new ArrayList<Miasto>(this.instancja.getMiasta().size());
			for(int j=0; j<this.instancja.getMiasta().size(); j++) {
				miasta.add(new Miasto(this.instancja.getMiasta().get(j).getNumer(), this.instancja.getMiasta().get(j).getX(), this.instancja.getMiasta().get(j).getY(), this.instancja.getMiasta().get(j).getTyp()));
			}
			osobniki.add(new Osobnik(miasta));
		}
		return osobniki;
	}
	
	public void dodajOsobnika(Osobnik o) {
		this.osobniki.add(Narzedzia.kopiuj(o));
		this.rozmiar++;
	}
	
	public double obliczNajlepszyDystans() {
		double wynik = this.osobniki.get(0).obliczDlugoscTrasy(this.instancja.getTabOdleglosci());
		double dyst;
		for(int i=1; i<this.osobniki.size(); i++) {
			dyst = this.osobniki.get(i).obliczDlugoscTrasy(this.instancja.getTabOdleglosci());
			if(dyst<wynik) {
				wynik=dyst;
			}
		}
		
		return wynik;
	}
	
	public double obliczSredniDystans() {
		double wynik=0;
		for(int i=0; i<this.osobniki.size(); i++) {
			wynik+=this.osobniki.get(i).obliczDlugoscTrasy(this.instancja.getTabOdleglosci());
		}
		return new BigDecimal(wynik/this.rozmiar).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public double obliczNajgorszyDystans() {
		double wynik = this.osobniki.get(0).obliczDlugoscTrasy(this.instancja.getTabOdleglosci());
		double dyst;
		for(int i=1; i<this.osobniki.size(); i++) {
			dyst = this.osobniki.get(i).obliczDlugoscTrasy(this.instancja.getTabOdleglosci());
			if(dyst>wynik) {
				wynik=dyst;
			}
		}
		
		return wynik;
	}
	
	public ArrayList<Osobnik> getOsobniki() {
		return osobniki;
	}

	public void setOsobniki(ArrayList<Osobnik> osobniki) {
		this.osobniki = osobniki;
	}

	public int getRozmiar() {
		return rozmiar;
	}

	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
	}

	public Instancja getInstancja() {
		return instancja;
	}

	public void setInstancja(Instancja instancja) {
		this.instancja = instancja;
	}
	
}
