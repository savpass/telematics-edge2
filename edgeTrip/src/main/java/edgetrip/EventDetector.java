package edgetrip;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tme.com.toyota.edgedc.EdgeDC;

public class EventDetector {

	private SignalHandler signals;
	private List<Event> events = new ArrayList<>();
	private List<Event> eventsABC = new ArrayList<>();
	
	

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Event> getEventsABC() {
		return eventsABC;
	}

	public void setEventsABC(List<Event> eventsABC) {
		this.eventsABC = eventsABC;
	}

	public EventDetector(SignalHandler signals) {
    	this.signals = signals;
	}

	public  Optional<Event> detectEvent() throws IOException {
		
		double eaccp = signals.getEACCP();
		double acc = signals.getAccx();
		double pmc = signals.getPMC();
		
		double hvid = signals.getEspohvid();
		double hvid_prev = signals.getEspohvidPrev();
		double hvvl = signals.getEspohvvl();
		double sp1_prev = signals.getSp1Prev();
		double hvvl_prev = signals.getEspohvvlPrev();
		
		boolean eventDetected = false;
		double latitude = 0;
		double longitude = 0;
		double timestamp = 0;
		String type = "A";
		boolean isGood = false;

		
		latitude = EdgeDC.getLocationValue().getCoordinates().getLatitude();
//		longitude =  EdgeDC.getLocationValue().getCoordinates().getLongitude();
		timestamp = signals.getTimestampFromIGSignal();
		
		if (eaccp > 0) {
			eventDetected = true;
			type = "A";
			
			if (acc > 0.3 * 9.81) {
				isGood = false;
			}else {
				isGood = true;
			}
		}
		
		if (pmc > 0) {
			eventDetected = true;
			type = "B";
			
			if (acc >= -0.3 * 9.81) {
				isGood = true;
			}else {
				isGood = false;
			}
			
		}
		
        if (eventDetected) { 
            return Optional.of(new Event(latitude, longitude, timestamp, type, isGood));
        } else {
            
            return Optional.empty();
        }
    }

	public Optional<Event> detectABCEvent() throws IOException {
		
		double hvid = signals.getEspohvid();
		double hvid_prev = signals.getEspohvidPrev();
		double hvvl = signals.getEspohvvl();
		double sp1_prev = signals.getSp1Prev();
		double hvvl_prev = signals.getEspohvvlPrev();
		String type = "A";
		boolean isGood = false;
		boolean eventDetected = false;
		
		double latitude = 0;
		double longitude = 0;
		double timestamp = 0;
		
		latitude = EdgeDC.getLocationValue().getCoordinates().getLatitude();
//		longitude =  EdgeDC.getLocationValue().getCoordinates().getLongitude();
		timestamp = signals.getTimestampFromIGSignal();

		
		
		if (hvvl < 70) {
			isGood = false;
		} else {
			isGood = true;
	}
	
	if (hvid == hvid_prev && (hvid != 7 && hvid != 0)) {
		
		if (hvid == 1) {
			type = "A";
			eventDetected = true;
		} else if (hvid == 2) {
			type = "C";
			eventDetected = true;
		} else if(hvid == 4) {
			type = "B";
			eventDetected = true;
		}
		
	} else if(hvid_prev == 4 && hvid == 0) {
		type = "B";
		eventDetected = true;
	} else if(hvid_prev == 1 && (hvid != 1 && sp1_prev >= 20)) {
		type = "A";
		eventDetected = true;
	}
		
		
    if (eventDetected) { 
        return Optional.of(new Event(latitude, longitude, timestamp, type, isGood));
    } else {
        
        return Optional.empty();
    }
	}

	public void detectAndAddEvent() throws IOException {
	    Optional<Event> optionalEvent = detectEvent();
	    
	    if (optionalEvent.isPresent()) {
	        Event event = optionalEvent.get();
	        //context.getLogger().printLog(Logger.LOG_INFO,  "Event detected! Type " + event.getType() + " good: " + Boolean.toString(event.isGood()) + " timestamp: " + event.getTimestamp());
	        events.add(event);
	    } else {
	    }
	    
	    Optional<Event> optionalEventABC = detectABCEvent();
	    
	    if (optionalEventABC.isPresent()) {
	        Event event = optionalEventABC.get();
	        //context.getLogger().printLog(Logger.LOG_INFO,  "Event detected! Type " + event.getType() + " good: " + Boolean.toString(event.isGood()) + " timestamp: " + event.getTimestamp());
	        eventsABC.add(event);
	    } else {
	    }
		
	}

}

