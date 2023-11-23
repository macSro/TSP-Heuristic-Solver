package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import tsp.City;
import tsp.Individual;
import tsp.Result;

public class Utils {

	public static Individual copy(Individual o) {
		Individual o_kopia;
		
		int l_miast = o.getCities().size();
		ArrayList<City> m_kopia = new ArrayList<City>(l_miast);
		
		City m;
		for(int i=0; i<l_miast; i++) {
			m = o.getCities().get(i);
			m_kopia.add(new City(m.getId(), m.getX(), m.getY(), m.getCoordinatesSystem()));
		}
		
		o_kopia = new Individual(m_kopia);
		
		return o_kopia;
	}
	
	public static void saveCsv(
		ArrayList<Result> results,
		String resultFileName,
		boolean shouldAppend,
		String dataFileName,
		String generations,
		String populationSize,
		String probCrossover,
		String probMutation,
		String tournamentSize
	){
		try {
			File fout = new File("results/" + resultFileName + ".csv");
			FileOutputStream fos = new FileOutputStream(fout,shouldAppend);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			bw.write("Data file;Generations;Population size;Crossover probability;Mutation probability;Tournament size");
			bw.newLine();
			bw.write(dataFileName + ";" + generations + ";" + populationSize + ";" + probCrossover + ";" + probMutation + ";" + tournamentSize);
			bw.newLine();
			bw.newLine();
			bw.write("Generation;Best;Average;Worst");
			bw.newLine();
			for (int i = 0; i < results.size(); i++) {
				bw.write((i+1) + ";" + results.get(i).getBest() + ";" + results.get(i).getAverage() + ";" + results.get(i).getWorst());
				bw.newLine();
			}
			bw.newLine();
			bw.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
