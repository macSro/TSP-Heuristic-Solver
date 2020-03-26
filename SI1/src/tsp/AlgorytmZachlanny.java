package tsp;

import java.util.ArrayList;

import tsp_narzedzia.Narzedzia;

public class AlgorytmZachlanny{
	
	public void wykonaj(Osobnik o, Miasto mStart, double[][]tabOdl) {
		ArrayList<Miasto> noweMiasta = new ArrayList<Miasto>(o.getMiasta().size());
		
		noweMiasta.add(mStart);
		Miasto aktualneM = mStart;
		Miasto minM = null;
		double minOdleglosc = Integer.MAX_VALUE;
		
		while(noweMiasta.size()!=o.getMiasta().size()) {
			for(Miasto m : o.getMiasta()) {
				if(!noweMiasta.contains(m)) {
					if(tabOdl[aktualneM.getNumer()-1][m.getNumer()-1]<minOdleglosc) {
						minOdleglosc = tabOdl[aktualneM.getNumer()-1][m.getNumer()-1];
						minM = m;
					}
				}
			}
			
			if(minM!=null) {
				noweMiasta.add(minM);
				aktualneM = minM;
				minOdleglosc = Integer.MAX_VALUE;
			}
			
		}
		o.setMiasta(noweMiasta);
	}

}
