package tsp;

import java.util.ArrayList;

public class GreedyAlgorithm{
	
	public void generateCities(Individual individual, City start, double[][]distances) {
		ArrayList<City> cities = new ArrayList<City>(individual.getCities().size());
		
		cities.add(start);
		City current = start;
		City min = null;
		double midDistance = Integer.MAX_VALUE;
		
		while(cities.size()!=individual.getCities().size()) {
			for(City city : individual.getCities()) {
				if(!cities.contains(city)) {
					if(distances[current.getId()-1][city.getId()-1]<midDistance) {
						midDistance = distances[current.getId()-1][city.getId()-1];
						min = city;
					}
				}
			}
			
			if(min!=null) {
				cities.add(min);
				current = min;
				midDistance = Integer.MAX_VALUE;
			}
			
		}
		individual.setCities(cities);
	}

}
