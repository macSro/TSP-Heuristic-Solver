package tsp;

import java.util.Random;

public class MutationSwap extends Mutation{

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
		
		City city = individual.getCities().get(id1);
		individual.getCities().set(id1, individual.getCities().get(id2));
		individual.getCities().set(id2, city);
	}
	
}
