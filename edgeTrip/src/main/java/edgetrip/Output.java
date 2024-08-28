package edgetrip;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.ibm.automotive.logger.Logger;

import metrics.EcoScoring;
import metrics.ElecricityConsumption;
import metrics.FuelConsumption;


public class Output {
	private Context context;
	private double scoreAcc;
	private double scoreBreak;
	private double globalScore;
	private double scoreAccABC;
	private double scoreBreakABC;
	private double scoreConstantABC;
	private double globalScoreABC;

	public Output(Context context) {
		this.context = context;
	}

	public void printScores(List<Event> events, List<Event> eventsABC, int cycleCount) throws IOException {
		
	     scoreAcc = Score.computeAcceleration(events, cycleCount);
	     scoreBreak = Score.computeBreak(events, cycleCount);
	     globalScore = (scoreAcc + scoreBreak)/2;
	    
	     scoreAccABC = Score.computeAccelerationABC(eventsABC, cycleCount);
	     scoreBreakABC = Score.computeBreakABC(eventsABC, cycleCount);
	     scoreConstantABC = Score.computeConstantABC(eventsABC, cycleCount);
	     globalScoreABC = (scoreAcc + scoreBreak + scoreConstantABC)/3;
	    
	    String type = context.getVehicleType(); 
	     
	    
	    if (type.equals("ICE") || type.equals("FCEV")) {
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score acceleration: " + scoreAcc);
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score break: " + scoreBreak);
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score global break and acc: " + globalScore);
	    }
	    
	    if (type.equals("HEV") || type.equals("PHEV") || type.equals("BEV")) {
	    	
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score acceleration: " + scoreAcc);
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score break: " + scoreBreak);
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score global break and acc: " + globalScore);
	    	
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score acceleration: " + scoreAccABC );
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score break: " + scoreBreakABC );
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score constant: " + scoreConstantABC );
	    	context.getLogger().printLog(Logger.LOG_INFO, "Score global break, acc and constant: " + globalScoreABC);
	    }
	}

	public void printFuelConsumption(FuelConsumption fuelConsumption) throws IOException {
		String type = context.getVehicleType();
		
		if (type.equals("HEV") || type.equals("PHEV") || type.equals("ICE")) {
			context.getLogger().printLog(Logger.LOG_INFO, "Fuel consumption: " + fuelConsumption.getTripConsumption());
		}
		
		if (type.equals("FCEV")) {
			context.getLogger().printLog(Logger.LOG_INFO, "Hydrogen consumption: " + fuelConsumption.getTripConsumption());
		}
	}

	public void printElectricityConsumption(ElecricityConsumption electricityConsumption) throws IOException {
		String type = context.getVehicleType();
		
		if (type.equals("HEV") || type.equals("PHEV") || type.equals("BEV")) {
			//context.getLogger().printLog(Logger.LOG_INFO, "Score break: " + scoreBreakABC );
			context.getLogger().printLog(Logger.LOG_INFO, "Electricity consumption: " + electricityConsumption.getTripConsumption());
		}
	}

	public void printLocationPoints(List<LocationPoint> locationPoints) throws IOException {
		List<LocationPoint> points = locationPoints;
		
		context.getLogger().printLog(Logger.LOG_INFO, "Number of location points: " + points.size());
		
		int count = 0;
		 for (LocationPoint item : points) {
			 context.getLogger().printLog(Logger.LOG_INFO,  "Coordinate " + Integer.toString(count) + ": " + Double.toString(item.getLatitude() ) + " " + Double.toString(item.getLongitude()));
			  count++;
	            
	        }
		
	}

	public void storeFile() {
//		File file = this.context.getOutputFile();
//		
//		try (FileWriter writer = new FileWriter(file)) {
//            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		
	}

	public void printEvents(List<Event> events) throws IOException {
		int count = 0;
		for (Event ev : events) {
                count++;
                //context.getLogger().printLog(Logger.LOG_INFO,  "Event " + count + ": type " + ev.getType() + " good: " + Boolean.toString(ev.isGood()) + " timestamp: " + ev.getTimestamp());
            
        }
		context.getLogger().printLog(Logger.LOG_INFO,  "Count events: " + Integer.toString(count));
		 
	}

	public void printEV() {
		
		
	}

	public void printHDCScores(EcoScoring ecoScoring) throws IOException {
		context.getLogger().printLog(Logger.LOG_INFO, "Average trip eco score starting " + Double.toString(ecoScoring.getAvgStart()));
		context.getLogger().printLog(Logger.LOG_INFO, "Average trip eco steady driving " + Double.toString(ecoScoring.getAvgSteady()));
		context.getLogger().printLog(Logger.LOG_INFO, "Average trip eco score deceleration " + Double.toString(ecoScoring.getAvgDecel()));
		context.getLogger().printLog(Logger.LOG_INFO, "Total average trip eco score " + Double.toString(ecoScoring.getGlobalScore()));
		
	}

	public void printHDCScores(HDC hdc) throws IOException {
		context.getLogger().printLog(Logger.LOG_INFO, "Average2 trip eco score starting " + Double.toString(hdc.getAvgStart()));
		context.getLogger().printLog(Logger.LOG_INFO, "Average2 trip eco steady driving " + Double.toString(hdc.getAvgSteady()));
		context.getLogger().printLog(Logger.LOG_INFO, "Average2 trip eco score deceleration " + Double.toString(hdc.getAvgDecel()));
		context.getLogger().printLog(Logger.LOG_INFO, "Total2 average trip eco score " + Double.toString(hdc.getGlobalScore()));
		
	}


}
