package tsp;

import java.util.Random;

import tsp_narzedzia.Narzedzia;

public class MutacjaSwap extends Mutacja{

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
		
		Miasto m = o.getMiasta().get(id1);
		o.getMiasta().set(id1, o.getMiasta().get(id2));
		o.getMiasta().set(id2, m);
	}
	
}
