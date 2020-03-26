package tsp;
import tsp_narzedzia.Narzedzia;

import java.util.ArrayList;
import java.util.Random;

public class SelekcjaTurniej extends Selekcja{

	private int n;
	
	public SelekcjaTurniej(int n) {
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	@Override
	public Osobnik wykonaj(Populacja p) {
		int l_os = p.getOsobniki().size();
		
		if(this.n > l_os) this.n = l_os;
		
		Random r = new Random();
		
		ArrayList<Integer> wybId = new ArrayList<Integer>(l_os);

		int randId;
		while(wybId.size()!=this.n) {
			randId = r.nextInt(l_os);
			if(!wybId.contains(randId)){
				wybId.add(randId);
			}
		}
		
		//System.out.println("WYBRANE ID W SELEKCJI: " + wybId);
		
		int minId = wybId.get(0);
		Osobnik o = p.getOsobniki().get(minId);
		double minDyst = o.obliczDlugoscTrasy(p.getInstancja().getTabOdleglosci());
		
		int id;
		double dyst;
		for(int i=0; i<wybId.size(); i++) {
			id = wybId.get(i);
			o = p.getOsobniki().get(id);
			dyst = o.obliczDlugoscTrasy(p.getInstancja().getTabOdleglosci());
			
			if(dyst<minDyst) {
				minDyst = dyst;
				minId = id;
			}
		}
		
		//System.out.println("WYBRANY W SELEKCJI: " + minId + "\n");
		
		return Narzedzia.kopiuj(p.getOsobniki().get(minId));
	}

}
