package tsp;

import java.util.Collections;

public class RandomizedAlgorithm{
	
	public void generateCities(Individual individual) {
		Collections.shuffle(individual.getCities());
	}
	
}
