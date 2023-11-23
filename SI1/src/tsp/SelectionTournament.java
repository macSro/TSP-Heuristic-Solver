package tsp;
import java.util.ArrayList;
import java.util.Random;

import utils.Utils;

public class SelectionTournament extends Selection{

	private int size;
	
	public SelectionTournament(int size) {
		this.size = size;
	}
	
	public int getSize() {
		return size;
	}
	
	public void setSize(int n) {
		this.size = n;
	}
	
	@Override
	public Individual select(Population population) {
		int populationSize = population.getIndividuals().size();
		
		if(this.size > populationSize) this.size = populationSize;
		
		ArrayList<Integer> selectedIds = new ArrayList<Integer>(populationSize);

		int randomId;
		while(selectedIds.size()!=this.size) {
			randomId = new Random().nextInt(populationSize);
			if(!selectedIds.contains(randomId)){
				selectedIds.add(randomId);
			}
		}
		
		int minId = selectedIds.get(0);
		Individual bestIndividual = population.getIndividuals().get(minId);
		double minDistance = bestIndividual.calculateDistance(population.getInputData().getDistances());
		
		int id;
		double distance;
		for(int i=0; i<selectedIds.size(); i++) {
			id = selectedIds.get(i);
			bestIndividual = population.getIndividuals().get(id);
			distance = bestIndividual.calculateDistance(population.getInputData().getDistances());
			
			if(distance<minDistance) {
				minDistance = distance;
				minId = id;
			}
		}
		
		return Utils.copy(population.getIndividuals().get(minId));
	}

}
