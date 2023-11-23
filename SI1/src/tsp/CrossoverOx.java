package tsp;

import java.util.ArrayList;
import java.util.Random;

public class CrossoverOx extends Crossover{

	@Override
	public Individual crossover(Individual parent1, Individual parent2) {
		
		Random r = new Random();
		
		int length = parent1.getCities().size();
		int id1 = r.nextInt(length);
		int id2 = r.nextInt(length);

		while(id1 >= id2) {
			id1 = r.nextInt(length);
			id2 = r.nextInt(length);
		}
		
		ArrayList<City> result = new ArrayList<City>(length);
		
		ArrayList<City> temp1 = new ArrayList<City>(parent1.getCities().subList(id1, id2));
		temp1.add(parent1.getCities().get(id2));
		
		ArrayList<Integer> temp1_ids = new ArrayList<Integer>(temp1.size());
		for(int i=0; i<temp1.size(); i++) {
			temp1_ids.add(temp1.get(i).getId());
		}
		
		int temp_id=0;
		int count=0;
		
		while(count<id1) {
			if(!temp1_ids.contains(parent2.getCities().get(temp_id).getId())) {
				result.add(new City(parent2.getCities().get(temp_id).getId(), parent2.getCities().get(temp_id).getX(), parent2.getCities().get(temp_id).getY(), parent2.getCities().get(temp_id).getCoordinatesSystem()));
				count++;
			}
			temp_id++;
		}
		for(int i=0; i<temp1.size(); i++) {
			result.add(new City(temp1.get(i).getId(), temp1.get(i).getX(),temp1.get(i).getY(), temp1.get(i).getCoordinatesSystem()));
		}
		while(result.size()!=length) {
			if(!temp1_ids.contains(parent2.getCities().get(temp_id).getId())) {
				result.add(new City(parent2.getCities().get(temp_id).getId(), parent2.getCities().get(temp_id).getX(), parent2.getCities().get(temp_id).getY(), parent2.getCities().get(temp_id).getCoordinatesSystem()));
				count++;
			}
			temp_id++;
		}
		
		return new Individual(result);
	}

}
