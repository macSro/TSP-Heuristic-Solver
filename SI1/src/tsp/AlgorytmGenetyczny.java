package tsp;


import java.util.ArrayList;
import java.util.Random;

import tsp_narzedzia.Narzedzia;

public class AlgorytmGenetyczny {
	
	private int rozmiarPopulacji;
	private int liczbaGeneracji;
	private double pX;
	private double pM;
	private Selekcja selekcja;
	private Krzyzowanie krzyzowanie;
	private Mutacja mutacja;
	
	private double najlepszyDystans;
	private ArrayList<Wynik> wyniki;
	
	private String plik;
	
	public AlgorytmGenetyczny(int rP, int lG, double pX, double pM, int n, String p, Selekcja s, Krzyzowanie k, Mutacja m) {
		this.rozmiarPopulacji = rP;
		this.liczbaGeneracji = lG;
		this.pX = pX;
		this.pM = pM;
		this.plik = p;
		this.selekcja = s;
		this.krzyzowanie = k;
		this.mutacja = m;
	}

	public void wykonaj() {
		ArrayList<Wynik> wyniki = new ArrayList<Wynik>(liczbaGeneracji);
		
		Instancja instancja = new Instancja(plik);
		Populacja populacja = inicjuj(instancja);
		
		//Narzedzia.zapiszLog(populacja,0);
		wyniki.add(new Wynik(populacja.obliczNajlepszyDystans(), populacja.obliczSredniDystans(), populacja.obliczNajgorszyDystans()));
		
		double najlepszy = ocen(populacja);
		double najgorszy = najlepszy;
		double suma = 0.0;
		Populacja nowa=null;
		for(int i=1; i<liczbaGeneracji; i++) {
			System.out.println("gen" + i);
			if(i!=1) populacja = nowa;
				nowa = new Populacja(instancja);
			
			while(nowa.getRozmiar()!=populacja.getRozmiar()) {
				
				Osobnik r1 = selekcja(populacja);
				Osobnik r2 = selekcja(populacja);
				
				Osobnik dziecko = krzyzuj(r1,r2);
				
				mutuj(dziecko);
				
				nowa.dodajOsobnika(dziecko);
			}
			//Narzedzia.zapiszLog(nowa,i);
			wyniki.add(new Wynik(nowa.obliczNajlepszyDystans(), nowa.obliczSredniDystans(), nowa.obliczNajgorszyDystans()));
			if(najlepszy > ocen(nowa)) {
				najlepszy = ocen(nowa);
			}
			double temp = nowa.obliczNajgorszyDystans();
			if(temp > najgorszy) {
				najgorszy = temp;
			}
			suma+=nowa.obliczSredniDystans();
		}
		
		this.wyniki = wyniki;
		this.najlepszyDystans = najlepszy;
	}
	
	private Populacja inicjuj(Instancja in) {
		AlgorytmLosowy algLos = new AlgorytmLosowy();
		AlgorytmZachlanny algZach = new AlgorytmZachlanny();
		Populacja populacja = new Populacja(rozmiarPopulacji, in);
		
		for(int i=0; i<populacja.getRozmiar(); i++) {
			if(i%10==0) {
				int randM = new Random().nextInt(populacja.getOsobniki().get(i).getMiasta().size());
				algZach.wykonaj(populacja.getOsobniki().get(i), populacja.getOsobniki().get(i).getMiasta().get(randM), in.getTabOdleglosci());
			}
			else
				algLos.wykonaj(populacja.getOsobniki().get(i));
		}
		return populacja;
		
	}
	
	private double ocen(Populacja p) {
		return p.obliczNajlepszyDystans();
	}
	
	private Osobnik selekcja(Populacja p) {
		return selekcja.wykonaj(p);
	}

	private Osobnik krzyzuj(Osobnik r1, Osobnik r2) {
		if(Math.random()<pX) {
			return krzyzowanie.krzyzuj(r1, r2);
		}
		else return r1;
	}
	
	private void mutuj(Osobnik o) {
		if(Math.random()<pM) {
			mutacja.mutuj(o);
		}
	}
	
	public int getRozmiarPopulacji() {
		return rozmiarPopulacji;
	}

	public void setRozmiarPopulacji(int rozmiarPopulacji) {
		this.rozmiarPopulacji = rozmiarPopulacji;
	}

	public int getLiczbaGeneracji() {
		return liczbaGeneracji;
	}

	public void setLiczbaGeneracji(int liczbaGeneracji) {
		this.liczbaGeneracji = liczbaGeneracji;
	}

	public double getpX() {
		return pX;
	}

	public void setpX(double pX) {
		this.pX = pX;
	}

	public double getpM() {
		return pM;
	}

	public void setpM(double pM) {
		this.pM = pM;
	}

	public double getNajlepszyDystans() {
		return najlepszyDystans;
	}

	public void setNajlepszyDystans(double najlepszyDystans) {
		this.najlepszyDystans = najlepszyDystans;
	}

	public ArrayList<Wynik> getWyniki() {
		return wyniki;
	}

	public void setWyniki(ArrayList<Wynik> wyniki) {
		this.wyniki = wyniki;
	}

	public String getPlik() {
		return plik;
	}

	public void setPlik(String plik) {
		this.plik = plik;
	}
	
	
}
