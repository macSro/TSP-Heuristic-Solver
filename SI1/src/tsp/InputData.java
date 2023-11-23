package tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class InputData {
	
	private String coordinatesSystem;
	private ArrayList<City> cities;
	private double[][] distances;
	
	public InputData(String fileName) {
		ArrayList<String> lines = readFile(fileName);
		this.coordinatesSystem = lines.get(4).split(" ")[1];
		
		this.cities = new ArrayList<City>();
		for(int i=5; i<lines.size(); i++) {
			String[] s = lines.get(i).split(" ");
			
			// Some files were inconsistent, this is a fix
			String[] fix = new String[3];
			int fixId=0;
			for(int j=0; j<s.length; j++) {
				if(!s[j].equals("")) {
					fix[fixId] = s[j];
					fixId+=1;
				}
			}
			cities.add(new City(Integer.parseInt(fix[0]), Double.parseDouble(fix[1]), Double.parseDouble(fix[2]), this.coordinatesSystem));
		}
		
		this.distances = calculateDistances();
	}
	
	private ArrayList<String> readFile(String fileName) {
		File file = new File("data/" + fileName + ".tsp");
		ArrayList<String> lines = new ArrayList<String>();
		
		try{
			FileInputStream fis = new FileInputStream(file);
			
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line;
			
			while(!(line = br.readLine()).equals("NODE_COORD_SECTION")) {
					lines.add(line);
			}
			if(lines.get(lines.size()-2).equals("EDGE_WEIGHT_TYPE: GEO")) {
				lines.remove(lines.size()-1);
			}
			while(!(line = br.readLine()).equals("EOF")) {
				lines.add(line);
			}
			
			br.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lines;
	}
	
	private double[][] calculateDistances(){
		double[][] distances = new double[cities.size()][cities.size()];
		
		for(int i=0; i<cities.size(); i++) {
			for(int j=i; j<cities.size(); j++) {
				double distance = cities.get(i).calculateDistance(cities.get(j));
				distances[i][j] = distance;
				distances[j][i] = distance;
			}
		}
		return distances;
	}
	
	public String getCoordinatesSystem() {
		return coordinatesSystem;
	}

	public void setCoordinatesSystem(String coordinatesSystem) {
		this.coordinatesSystem = coordinatesSystem;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	public double[][] getDistances() {
		return distances;
	}

	public void setDistances(double[][] distances) {
		this.distances = distances;
	}
}
