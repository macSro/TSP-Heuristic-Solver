	package tsp;

import java.util.Collections;
import java.util.Random;

public class MutationInversion extends Mutation{

	@Override
	public void mutate(Individual individual) {
		
		Random r = new Random();
		
		int dl = individual.getCities().size();
		int id1 = r.nextInt(dl);
		int id2 = r.nextInt(dl);

		while(id1 >= id2) {
			id1 = r.nextInt(dl);
			id2 = r.nextInt(dl);
		}
		
		int middle = id1 + ((id2 + 1) - id1) / 2;
		int end = id2;
		
		for(int i = id1; i<middle; i++) {
			Collections.swap(individual.getCities(), i, end);
			end--;
		}
	}

}
