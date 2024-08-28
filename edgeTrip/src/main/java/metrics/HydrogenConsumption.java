package metrics;

public class HydrogenConsumption {
	
	private static final int B_FCSamplingTime = 500;
	private int B_FCSamplingCount;
	double tripConsumption = 0;

	public boolean updateB_FCToUpdate() {
		this.B_FCSamplingCount = this.B_FCSamplingCount - 100;
		if (this.B_FCSamplingCount <= 0) {
			this.B_FCSamplingCount = this.B_FCSamplingTime + B_FCSamplingCount;
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
	
	

}
