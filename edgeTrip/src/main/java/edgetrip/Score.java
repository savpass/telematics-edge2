package edgetrip;

import java.util.List;

public class Score {

	public static double computeAcceleration(List<Event> list, int amountOfCycles) {		
		double count = countObjectsWithSpecificValues(list, "A");
		double score = (count * 100/amountOfCycles);
		
		return score;
	}
	
	public static double computeBreak(List<Event> list, int amountOfCycles) {
		int count = countObjectsWithSpecificValues(list, "B");
		return (count*100/amountOfCycles);
	}
	

	
	// Method to count objects with specific field values
    public static int countObjectsWithSpecificValues(List<Event> list, String type) {
        int count = 0;
        for (Event obj : list) {
            if (obj.isGood() && obj.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }

	public static double computeAccelerationABC(List<Event> list, int amountOfCycles) {
		
		double count = countObjectsWithSpecificValues(list, "A");
		double score = (count * 100/amountOfCycles);
		
		
		return score;
	}

	public static double computeBreakABC(List<Event> list, int amountOfCycles) {
		int count = countObjectsWithSpecificValues(list, "B");
		return (count*100/amountOfCycles);
	}

	public static double computeConstantABC(List<Event> list, int amountOfCycles) {
		int count = countObjectsWithSpecificValues(list, "C");
		return (count*100/amountOfCycles);
	}


}
