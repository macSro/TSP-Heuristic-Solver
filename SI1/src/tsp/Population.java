package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import utils.Utils;

public class Population {

	private int size;
	private InputData inputData;
	private ArrayList<Individual> individuals;

	public Population(InputData inputData) {
		this.inputData = inputData;
		this.size = 0;
		this.individuals = new ArrayList<Individual>();
	}
	
	public Population(int size, InputData inputData) {
		this.size = size;
		this.inputData = inputData;
		this.individuals = generateIndividuals();
	}
	
	private ArrayList<Individual> generateIndividuals() {
		ArrayList<Individual> individuals = new ArrayList<Individual>(this.size);
		ArrayList<City> cities = new ArrayList<City>(this.inputData.getCities().size());
		
		for(int i=0; i<this.size; i++) {
			cities = new ArrayList<City>(this.inputData.getCities().size());
			for(int j=0; j<this.inputData.getCities().size(); j++) {
				cities.add(new City(this.inputData.getCities().get(j).getId(), this.inputData.getCities().get(j).getX(), this.inputData.getCities().get(j).getY(), this.inputData.getCities().get(j).getCoordinatesSystem()));
			}
			individuals.add(new Individual(cities));
		}
		return individuals;
	}
	
	public void addIndividual(Individual o) {
		this.individuals.add(Utils.copy(o));
		this.size++;
	}
	
	public double calculateBestDistance() {
		double result = this.individuals.get(0).calculateDistance(this.inputData.getDistances());
		double distance;
		for(int i=1; i<this.individuals.size(); i++) {
			distance = this.individuals.get(i).calculateDistance(this.inputData.getDistances());
			if(distance<result) {
				result=distance;
			}
		}
		
		return result;
	}
	
	public double calculateAverageDistance() {
		double result=0;
		for(int i=0; i<this.individuals.size(); i++) {
			result+=this.individuals.get(i).calculateDistance(this.inputData.getDistances());
		}
		return new BigDecimal(result/this.size).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public double calculateWorstDistance() {
		double result = this.individuals.get(0).calculateDistance(this.inputData.getDistances());
		double distance;
		for(int i=1; i<this.individuals.size(); i++) {
			distance = this.individuals.get(i).calculateDistance(this.inputData.getDistances());
			if(distance>result) {
				result=distance;
			}
		}
		
		return result;
	}
	
	public ArrayList<Individual> getIndividuals() {
		return individuals;
	}

	public void setIndividuals(ArrayList<Individual> osobniki) {
		this.individuals = osobniki;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public InputData getInputData() {
		return inputData;
	}

	public void setInputData(InputData inputData) {
		this.inputData = inputData;
	}
	
}
