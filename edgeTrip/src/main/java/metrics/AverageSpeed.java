package metrics;

public class AverageSpeed {
	
	private int count;
	private double sumSpeeds;

	public void addCycleSpeed(double speed, char gear) {
		if (gear != 'N' || gear != 'P') {
			count++;
			sumSpeeds += speed;
		}
	}

	public double getAverage() {
		return sumSpeeds/count;
	}
	
	

}
