package tsp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Miasto {

	
	private int numer;
	private double x;
	private double y;
	private String typ;

	public Miasto(int n, double x, double y, String t) {
		this.numer = n;
		this.x = x;
		this.y = y;
		this.typ = t;
	}
	
	public int getNumer() {
		return numer;
	}


	public void setNumer(int numer) {
		this.numer = numer;
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


	public String getTyp() {
		return typ;
	}


	public void setTyp(String typ) {
		this.typ = typ;
	}
	
	public double obliczOdleglosc(Miasto m) {
		double odleglosc=0.0;
		
		if(this.typ.equals("EUC_2D")) {
			odleglosc = Math.sqrt(Math.pow(this.x - m.x, 2) + Math.pow(this.y - m.y, 2));
		}
		else if(this.typ.equals("GEO")){
			double R = 6371.0;
			
			double dLat = Math.PI / 180.0 * (this.x - m.x);
			double dLon = Math.PI / 180.0 * (this.y - m.y);
			
			double lat1 = Math.PI / 180.0 * this.x;
			double lat2 = Math.PI / 180.0 * m.x;
			
			double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
					   Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
			odleglosc = R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0-a));
		}
		
		return new BigDecimal(odleglosc).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
}
