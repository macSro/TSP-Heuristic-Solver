package tsp;


import java.util.ArrayList;
import java.util.Random;

public class GeneticAlgorithm {
	
	private int generations;
	private int populationSize;
	private Selection selection;
	private Crossover crossover;
	private Mutation mutation;
	private double probabilityCrossover;
	private double probabilityMutation;
	
	private double bestDistance;
	private ArrayList<Result> results;
	
	private InputData inputData;
	
	public GeneticAlgorithm(int generations, int populationSize, Selection selection, Crossover crossover, Mutation mutation, double probabilityCrossover, double probabilityMutation, String inputFileName) {
		this.generations = generations;
		this.populationSize = populationSize;
		this.selection = selection;
		this.crossover = crossover;
		this.mutation = mutation;
		this.probabilityCrossover = probabilityCrossover;
		this.probabilityMutation = probabilityMutation;
		this.inputData = new InputData(inputFileName);
		
	}

	public void execute() {
		ArrayList<Result> results = new ArrayList<Result>(generations);

		Population population = generateInitialPopulation(this.inputData);
		
		results.add(new Result(population.calculateBestDistance(), population.calculateAverageDistance(), population.calculateWorstDistance()));
		
		double best = population.calculateBestDistance();
		double worst = best;
		
		Population newPopulation=null;
		for(int i=1; i<generations; i++) {
			if(i!=1) population = newPopulation;
			newPopulation = new Population(this.inputData);
			
			while(newPopulation.getSize()!=population.getSize()) {
				
				Individual parent1 = performSelection(population);
				Individual parent2 = performSelection(population);
				
				Individual child = performCrossover(parent1,parent2);
				
				performMutation(child);
				
				newPopulation.addIndividual(child);
			}
			
			results.add(new Result(newPopulation.calculateBestDistance(), newPopulation.calculateAverageDistance(), newPopulation.calculateWorstDistance()));
			if(best > newPopulation.calculateBestDistance()) {
				best = newPopulation.calculateBestDistance();
			}
			double temp = newPopulation.calculateWorstDistance();
			if(temp > worst) {
				worst = temp;
			}
		}
		
		this.results = results;
		this.bestDistance = best;
	}
	
	private Population generateInitialPopulation(InputData inputData) {
		Population population = new Population(populationSize, inputData);
		
		for(int i=0; i<population.getSize(); i++) {
			if(i%10==0) {
				int randM = new Random().nextInt(population.getIndividuals().get(i).getCities().size());
				new GreedyAlgorithm().generateCities(population.getIndividuals().get(i), population.getIndividuals().get(i).getCities().get(randM), inputData.getDistances());
			}
			else new RandomizedAlgorithm().generateCities(population.getIndividuals().get(i));
		}
		return population;
		
	}
	
	private Individual performSelection(Population population) {
		return selection.select(population);
	}

	private Individual performCrossover(Individual parent1, Individual parent2) {
		if(Math.random()<probabilityCrossover) {
			return crossover.crossover(parent1, parent2);
		}
		else return parent1;
	}
	
	private void performMutation(Individual individual) {
		if(Math.random()<probabilityMutation) {
			mutation.mutate(individual);
		}
	}
	
	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public int getGenerations() {
		return generations;
	}

	public void setGenerations(int generations) {
		this.generations = generations;
	}

	public double getProbabilityCrossover() {
		return probabilityCrossover;
	}

	public void setProbabilityCrossover(double probabilityCrossover) {
		this.probabilityCrossover = probabilityCrossover;
	}

	public double getProbabilityMutation() {
		return probabilityMutation;
	}

	public void setProbabilityMutation(double pM) {
		this.probabilityMutation = pM;
	}

	public double getBestDistance() {
		return bestDistance;
	}

	public void setBestDistance(double najlepszyDystans) {
		this.bestDistance = najlepszyDystans;
	}

	public ArrayList<Result> getResults() {
		return results;
	}

	public void setResults(ArrayList<Result> results) {
		this.results = results;
	}

	public InputData getInputData() {
		return inputData;
	}

	public void setInputData(InputData inputData) {
		this.inputData = inputData;
	}
	
	
}
