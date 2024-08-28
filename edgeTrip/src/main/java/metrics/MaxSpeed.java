package metrics;

public class MaxSpeed {

	private double max = 0;

	public void checkMax(double speed) {
		if (speed > max ) {
			max = speed;
		}
		
	}

	public double getMax() {
		// TODO Auto-generated method stub
		return max;
	}

}
