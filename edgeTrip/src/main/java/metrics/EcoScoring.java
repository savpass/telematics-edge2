package metrics;

import java.util.ArrayList;
import java.util.List;

public class EcoScoring {

    private List<Double> scoreStartList = new ArrayList<>();
    private List<Double> scoreSteadyList = new ArrayList<>();
    private List<Double> scoreDecelList = new ArrayList<>();

    private List<Double> espoHvvLHoldStart = new ArrayList<>();
    private List<Double> espoHvvLHoldSteady = new ArrayList<>();
    private List<Double> espoHvvLHoldDecel = new ArrayList<>();

    private boolean twoSecondReached = false;
    private long lastUpdate;
    private long timeSteadyUpdate;
    
	private static final int B_FCSamplingTime = 100;
	private int B_FCSamplingCount = B_FCSamplingTime;
	double tripConsumption = 0;

	private double avgStart;
	private double avgSteady;
	private double avgDecel;
	private long globalScore;

	public boolean updateB_FCToUpdate() {
		this.B_FCSamplingCount = this.B_FCSamplingCount - 100;
		if (this.B_FCSamplingCount <= 0) {
			//System.out.println("Sampling Count: " + B_FCSamplingCount);
			this.B_FCSamplingCount = EcoScoring.B_FCSamplingTime + B_FCSamplingCount;
			return true;
		}
		else {	
			return false;
		}
	}

	public void add(double currentEspoHvid, double previousEspoHvid, double espoHvvL, long currentTime) {
		if (this.updateB_FCToUpdate()) {
			computeHDCScores(currentEspoHvid, previousEspoHvid, espoHvvL, currentTime);
		}
	}


    public void computeHDCScores(double currentEspoHvid, double previousEspoHvid, double espoHvvL, long currentTime) {
        if (currentEspoHvid == previousEspoHvid && (currentEspoHvid != 7 && currentEspoHvid != 0)) {
            if (currentEspoHvid == 4) {
                espoHvvLHoldDecel.add(espoHvvL);
            }
        } else if (previousEspoHvid == 4 && currentEspoHvid == 0) {
            scoreDecelList.add(average(espoHvvLHoldDecel));
        } else if (previousEspoHvid == 1 && (currentEspoHvid == 2 || currentEspoHvid == 4)) {
            scoreStartList.add(average(espoHvvLHoldStart));
        } else if (previousEspoHvid == 0 && currentEspoHvid == 1) {
            espoHvvLHoldStart.clear();
            espoHvvLHoldSteady.clear();
            espoHvvLHoldDecel.clear();
            twoSecondReached = false;
        }

        if (currentEspoHvid == 1) {
            espoHvvLHoldStart.add(espoHvvL);
        }

        if (currentEspoHvid == 2) {
            espoHvvLHoldSteady.add(espoHvvL);
            if (previousEspoHvid != 2 && !twoSecondReached) {
                lastUpdate = currentTime;
                timeSteadyUpdate = currentTime;
            } else if ((currentTime - lastUpdate) >= 2000 && (currentTime - timeSteadyUpdate) >= 2000) {
                scoreSteadyList.add(average(espoHvvLHoldSteady));
                twoSecondReached = true;
                lastUpdate += 2000;
            }
        } else if (currentEspoHvid != 2 && previousEspoHvid == 2 && !twoSecondReached) {
            espoHvvLHoldSteady.clear();
        }
    }

    public double computeAverage(List<Double> scores) {
        return scores.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    private double average(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    public void computeAvgHdc() {
        avgStart = !scoreStartList.isEmpty() ? Math.round(computeAverage(scoreStartList)) : 0;
        avgSteady = !scoreSteadyList.isEmpty() ? Math.round(computeAverage(scoreSteadyList)) : 0;
        avgDecel = !scoreDecelList.isEmpty() ? Math.round(computeAverage(scoreDecelList)) : 0;

        globalScore = Math.round((avgStart + avgSteady + avgDecel) / 3.0);
        
    }
    
    
    
	public long getGlobalScore() {
		return globalScore;
	}

	public void setGlobalScore(long globalScore) {
		this.globalScore = globalScore;
	}

	public double getAvgStart() {
		return avgStart;
	}

	public void setAvgStart(double avgStart) {
		this.avgStart = avgStart;
	}

	public double getAvgSteady() {
		return avgSteady;
	}

	public void setAvgSteady(double avgSteady) {
		this.avgSteady = avgSteady;
	}

	public double getAvgDecel() {
		return avgDecel;
	}

	public void setAvgDecel(double avgDecel) {
		this.avgDecel = avgDecel;
	}
}
