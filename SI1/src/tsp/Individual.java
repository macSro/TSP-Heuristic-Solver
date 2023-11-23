package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Individual {

	private ArrayList<City> cities;
	
	public Individual(ArrayList<City> cities) {
		this.cities = cities;
	}
	
	public double calculateDistance(double[][] distances) {
		double distance = 0.0;
		
		for(int i=0; i<cities.size()-1; i++) {
			distance += distances[cities.get(i).getId()-1][cities.get(i+1).getId()-1];
		}
		
		distance += distances[cities.get(0).getId()-1][cities.get(cities.size()-1).getId()-1];
		
		return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}
}
