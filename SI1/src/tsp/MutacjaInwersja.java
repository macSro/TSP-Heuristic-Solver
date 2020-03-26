	package tsp;

import java.util.Collections;
import java.util.Random;

import tsp_narzedzia.Narzedzia;

public class MutacjaInwersja extends Mutacja{

	@Override
	public void mutuj(Osobnik o) {
		
		Random r = new Random();
		
		int dl = o.getMiasta().size();
		int id1 = r.nextInt(dl);
		int id2 = r.nextInt(dl);

		while(id1 >= id2) {
			id1 = r.nextInt(dl);
			id2 = r.nextInt(dl);
		}
		
		//System.out.println("MUTACJA   ID1: " + id1 + " ID2: " + id2 + "\n");
		
		int srodek = id1 + ((id2 + 1) - id1) / 2;
		int koniec = id2;
		
		for(int i = id1; i<srodek; i++) {
			Collections.swap(o.getMiasta(), i, koniec);
			koniec--;
		}
	}

}
