package edgetrip;

import java.util.List;

import utils.LoggerCustom;

public class Context {
	
	LoggerCustom logger;
	private String vehicleType;
	private List<String> canIds;
	private EdgeTrip edgeTrip;

	public Context(LoggerCustom logger) {
		this.logger = logger;
	}

	public LoggerCustom getLogger() {
		// TODO Auto-generated method stub
		return logger;
	}

	public long getRUN_LOGIC_CYCLE() {
		return 100;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String type) {
		this.vehicleType = type;
		
	}

	public void setCanIds(List<String> ids) {
		this.canIds = ids;
		
	}

	public List<String> getCanIds() {
		return this.canIds;
	}
	
	public char getVinChar(double vin) {
		
		int number = 0;

		number = (int) vin;

		
        char asciiChar = (char) (number); // Adding 64 because 'A' starts at 65 in ASCII
		return asciiChar;
	}

	public void setEdgeTrip(EdgeTrip edgeTrip) {
		this.edgeTrip = edgeTrip;
		
	}

	public EdgeTrip getEdgeTrip() {
		// TODO Auto-generated method stub
		return edgeTrip;
	}

}
