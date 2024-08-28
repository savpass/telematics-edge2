package metrics;

public class ElecricityConsumption {
	
	private static final int B_FCSamplingTime = 500;
	private int B_FCSamplingCount = B_FCSamplingTime;
	double tripConsumption = 0;

	public boolean updateB_FCToUpdate() {
		this.B_FCSamplingCount = this.B_FCSamplingCount - 100;
		if (this.B_FCSamplingCount <= 0) {
			
			this.B_FCSamplingCount = ElecricityConsumption.B_FCSamplingTime + B_FCSamplingCount;
			return true;
		}
		else {	
			return false;
		}
	}

	public void add(int updateB_FC) {
		if (this.updateB_FCToUpdate()) {
			tripConsumption += updateB_FC;
			
		}
	}

	public int getB_FCSamplingCount() {
		return B_FCSamplingCount;
	}

	public void setB_FCSamplingCount(int b_FCSamplingCount) {
		B_FCSamplingCount = b_FCSamplingCount;
	}

	public double getTripConsumption() {
		return tripConsumption;
	}

	public void setTripConsumption(double tripConsumption) {
		this.tripConsumption = tripConsumption;
	}

	public static int getbFcsamplingtime() {
		return B_FCSamplingTime;
	}
	
	
	

}
