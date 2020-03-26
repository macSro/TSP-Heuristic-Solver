package tsp;

public class Wynik {

	private double najlepszy;
	private double sredni;
	private double najgorszy;
	
	public Wynik(double najlepszy, double sredni, double najgorszy) {
		this.najlepszy = najlepszy;
		this.sredni = sredni;
		this.najgorszy = najgorszy;
	}

	public double getNajlepszy() {
		return najlepszy;
	}

	public void setNajlepszy(double najlepszy) {
		this.najlepszy = najlepszy;
	}

	public double getSredni() {
		return sredni;
	}

	public void setSredni(double sredni) {
		this.sredni = sredni;
	}

	public double getNajgorszy() {
		return najgorszy;
	}

	public void setNajgorszy(double najgorszy) {
		this.najgorszy = najgorszy;
	}
	
	
	
}
