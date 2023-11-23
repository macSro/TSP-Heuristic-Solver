package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class City {

	
	private int id;
	private double x;
	private double y;
	private String coordinatesSystem;

	public City(int id, double x, double y, String coordinatesSystem) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.coordinatesSystem = coordinatesSystem;
	}
	
	public double calculateDistance(City city) {
		double distance=0.0;
		
		if(this.coordinatesSystem.equals("EUC_2D")) {
			distance = Math.sqrt(Math.pow(this.x - city.x, 2) + Math.pow(this.y - city.y, 2));
		}
		else if(this.coordinatesSystem.equals("GEO")){
			double R = 6371.0;
			
			double dLat = Math.PI / 180.0 * (this.x - city.x);
			double dLon = Math.PI / 180.0 * (this.y - city.y);
			
			double lat1 = Math.PI / 180.0 * this.x;
			double lat2 = Math.PI / 180.0 * city.x;
			
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					   Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
			distance = R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
		}
		
		return new BigDecimal(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public String getCoordinatesSystem() {
		return coordinatesSystem;
	}


	public void setCoordinatesSystem(String coordinatesSystem) {
		this.coordinatesSystem = coordinatesSystem;
	}
}
