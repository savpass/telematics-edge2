package metrics;

public class TripDurationIdle {
	
	private int count = 0;

	public void checkIdle(char gear) {
		if (gear == 'N') {
			count++;
		}
		
		if (gear == 'P') {
			count++;
		}
		
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public double getDuration(long logicCycle) {
		
		return count * logicCycle  / 1000;
	}

}
