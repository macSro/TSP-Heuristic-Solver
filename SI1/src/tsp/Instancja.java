package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Instancja {

	private String nazwa;
	private String typ;
	private String komentarz;
	private int rozmiar;
	private String typEW;
	private ArrayList<Miasto> miasta;
	private double[][] tabOdleglosci;
	
	public Instancja(String fileName) {
		ArrayList<String> lines = plik2string(fileName);
		
		this.nazwa = lines.get(0).split(" ")[1];
		this.typ = lines.get(1).split(" ")[1];
		this.komentarz = lines.get(2).split(" ",2)[1];
		this.rozmiar = Integer.parseInt(lines.get(3).split(" ")[1]);
		this.typEW = lines.get(4).split(" ")[1];
		this.miasta = new ArrayList<Miasto>();
		
		for(int i=5; i<lines.size(); i++) {
			String[] s = lines.get(i).split(" ");
			
			String[] sNaprawa = new String[3];
			int indeksNaprawa=0;
			for(int j=0; j<s.length; j++) {
				if(!s[j].equals("")) {
					sNaprawa[indeksNaprawa] = s[j];
					indeksNaprawa+=1;
				}
			}
			miasta.add(new Miasto(Integer.parseInt(sNaprawa[0]), Double.parseDouble(sNaprawa[1]), Double.parseDouble(sNaprawa[2]), this.typEW));
		}
		
		this.tabOdleglosci = wygenerujOdleglosci();
	}
	
	private ArrayList<String> plik2string(String fileName) {
		String path = "/Users/macie/eclipse-workspace/SI1/tsp/" + fileName + ".tsp";
		
		File file = new File(path);
		ArrayList<String> lines = new ArrayList<String>();
		
		try{
			FileInputStream fis = new FileInputStream(file);
			
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line;
			
			while(!(line = br.readLine()).equals("NODE_COORD_SECTION")) {
					lines.add(line);
			}
			if(lines.get(lines.size()-2).equals("EDGE_WEIGHT_TYPE: GEO")) {
				lines.remove(lines.size()-1);
			}
			while(!(line = br.readLine()).equals("EOF")) {
				lines.add(line);
			}
			
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lines;
	}
	
	private double[][] wygenerujOdleglosci(){
		double[][] odleglosci = new double[miasta.size()][miasta.size()];
		
		for(int i=0; i<miasta.size(); i++) {
			for(int j=i; j<miasta.size(); j++) {
				double odl = miasta.get(i).obliczOdleglosc(miasta.get(j));
				odleglosci[i][j] = odl;
				odleglosci[j][i] = odl;
			}
		}
		return odleglosci;
	}
	
	public void pokazTab() {
		for(int i=0; i<miasta.size(); i++) {
			for(int j=0; j<miasta.size(); j++) {
				System.out.print(tabOdleglosci[i][j] + " ");
			}
			System.out.println();
		}
	}

	public String getNazwa() {
		return nazwa;
	}

	public void setNazwa(String nazwa) {
		this.nazwa = nazwa;
	}

	public String getTyp() {
		return typ;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public String getKomentarz() {
		return komentarz;
	}

	public void setKomentarz(String komentarz) {
		this.komentarz = komentarz;
	}

	public int getRozmiar() {
		return rozmiar;
	}

	public void setRozmiar(int rozmiar) {
		this.rozmiar = rozmiar;
	}

	public String getTypEW() {
		return typEW;
	}

	public void setTypEW(String typEW) {
		this.typEW = typEW;
	}

	public ArrayList<Miasto> getMiasta() {
		return miasta;
	}

	public void setMiasta(ArrayList<Miasto> miasta) {
		this.miasta = miasta;
	}

	public double[][] getTabOdleglosci() {
		return tabOdleglosci;
	}

	public void setTabOdleglosci(double[][] tabOdleglosci) {
		this.tabOdleglosci = tabOdleglosci;
	}
	
	
	
}
