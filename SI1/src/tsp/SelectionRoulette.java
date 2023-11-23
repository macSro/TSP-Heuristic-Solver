package tsp;

import java.util.Random;

import utils.Utils;

public class SelectionRoulette extends Selection{

	@Override
	public Individual select(Population population) {
		double sum = 0;

        for(Individual individual : population.getIndividuals()) {
            sum += individual.calculateDistance(population.getInputData().getDistances());
        }

        int rand = new Random().nextInt((int)sum);
       
        double sum2 = 0;

        for(Individual individual : population.getIndividuals()) {
            sum2 += individual.calculateDistance(population.getInputData().getDistances());
            
            if (sum2 >= rand) return Utils.copy(individual);
        }

        return null;
	}

}
