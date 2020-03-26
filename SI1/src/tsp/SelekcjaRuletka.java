package tsp;

import java.util.Random;
import tsp_narzedzia.Narzedzia;

public class SelekcjaRuletka extends Selekcja{

	@Override
	public Osobnik wykonaj(Populacja p) {
		double suma = 0;

        for(Osobnik o : p.getOsobniki()) {
            suma += o.obliczDlugoscTrasy(p.getInstancja().getTabOdleglosci());
        }

        int rand = new Random().nextInt((int)suma);
       
        double suma2 = 0;

        for(Osobnik o : p.getOsobniki()) {
            suma2 += o.obliczDlugoscTrasy(p.getInstancja().getTabOdleglosci());
            
            if (suma2 >= rand) return Narzedzia.kopiuj(o);
        }

        return null;
	}

}
