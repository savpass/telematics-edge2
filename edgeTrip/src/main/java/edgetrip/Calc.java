package edgetrip;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import tme.com.toyota.edgedc.can.TimedCanData;

public class Calc {
	
	private Context context;
	private Trip trip;
	private HDC hdc;
	private SignalHandler signals;
	
	
	public Calc(Context context, Trip trip, HDC hdc, SignalHandler signals) {
		
    	this.context = context;
    	this.hdc = hdc;
    	this.trip = trip;
    	this.signals = signals;
    }
	
	public void calc(Map<Integer, List<TimedCanData>> input_data) throws IOException {
		
		signals.setLatestInput(input_data);
		
	    boolean tripFinished = trip.updateTripState();
	    
	    if (tripFinished) {
	    	this.trip = new Trip(this.context, this.hdc, this.signals);
	    	this.trip.setSignalHandler(this.signals);
	    }
	    
	    if (signals.getIg() == 1 && trip.getTripOn()) 
	        trip.computeCycle();	        
	}
}
