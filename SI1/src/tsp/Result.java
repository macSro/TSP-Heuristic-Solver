package tsp;

public class Result {

	private double best;
	private double average;
	private double worst;
	
	public Result(double best, double average, double worst) {
		this.best = best;
		this.average = average;
		this.worst = worst;
	}

	public double getBest() {
		return best;
	}

	public void setBest(double najlepszy) {
		this.best = najlepszy;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double sredni) {
		this.average = sredni;
	}

	public double getWorst() {
		return worst;
	}

	public void setWorst(double najgorszy) {
		this.worst = najgorszy;
	}
	
	
	
}
