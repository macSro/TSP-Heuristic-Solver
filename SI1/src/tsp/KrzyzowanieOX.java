package tsp;

import java.util.ArrayList;
import java.util.Random;

public class KrzyzowanieOX extends Krzyzowanie{

	@Override
	public Osobnik krzyzuj(Osobnik rodzic1, Osobnik rodzic2) {
		
		Random r = new Random();
		
		int dl = rodzic1.getMiasta().size();
		int id1 = r.nextInt(dl);
		int id2 = r.nextInt(dl);

		while(id1 >= id2) {
			id1 = r.nextInt(dl);
			id2 = r.nextInt(dl);
		}
		//System.out.println("KRZYZOWANIE   ID1: " + id1 + " ID2: " + id2 + "\n");
		
		ArrayList<Miasto> wynik = new ArrayList<Miasto>(dl);
		
		ArrayList<Miasto> temp1 = new ArrayList<Miasto>(rodzic1.getMiasta().subList(id1, id2));
		temp1.add(rodzic1.getMiasta().get(id2));
		
		ArrayList<Integer> temp1_id = new ArrayList<Integer>(temp1.size());
		for(int i=0; i<temp1.size(); i++) {
			temp1_id.add(temp1.get(i).getNumer());
		}
		
		int temp_id=0;
		int licznik=0;
		
		while(licznik<id1) {
			if(!temp1_id.contains(rodzic2.getMiasta().get(temp_id).getNumer())) {
				wynik.add(new Miasto(rodzic2.getMiasta().get(temp_id).getNumer(), rodzic2.getMiasta().get(temp_id).getX(), rodzic2.getMiasta().get(temp_id).getY(), rodzic2.getMiasta().get(temp_id).getTyp()));
				licznik++;
			}
			temp_id++;
		}
		for(int i=0; i<temp1.size(); i++) {
			wynik.add(new Miasto(temp1.get(i).getNumer(), temp1.get(i).getX(),temp1.get(i).getY(), temp1.get(i).getTyp()));
		}
		while(wynik.size()!=dl) {
			if(!temp1_id.contains(rodzic2.getMiasta().get(temp_id).getNumer())) {
				wynik.add(new Miasto(rodzic2.getMiasta().get(temp_id).getNumer(), rodzic2.getMiasta().get(temp_id).getX(), rodzic2.getMiasta().get(temp_id).getY(), rodzic2.getMiasta().get(temp_id).getTyp()));
				licznik++;
			}
			temp_id++;
		}
		
		return new Osobnik(wynik);
	}

}
