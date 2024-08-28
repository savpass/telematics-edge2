package edgetrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.ibm.automotive.location.Location;
import com.ibm.automotive.logger.Logger;

import edgetrip.Event;
import jp.co.toyota.util.ToyotaBandOnscreen;
import metrics.AverageSpeed;
import metrics.EcoScoring;
import metrics.FuelConsumption;
import metrics.HydrogenConsumption;
import metrics.ElecricityConsumption;
import metrics.MaxSpeed;
import metrics.TravelledDistance;
import metrics.TripDurationIdle;
import tme.com.toyota.edgedc.EdgeDC;
import tme.edgetrip.protos.CanData;
import tme.edgetrip.protos.EdgeTripMessage;
import tme.edgetrip.protos.EdgeTripMessage.Builder;
import tme.edgetrip.protos.LocationData;
import tme.edgetrip.protos.PositionInformation;
import tme.edgetrip.protos.TripSummary;
import utils.Utility;
import jp.co.toyota.system.ToyotaParameters;




public class Trip {
	
	private Context context;
	private HDC hdc;
	private String tripId = new String("");
	private SignalHandler signals;
	private boolean gearDIsSetOnce;
	private boolean gearRIsSetOnce;
	private EV ev;
	private EventDetector eventDetector;
	private Output output;
	private long startTime;
	private long endTime;
	private boolean tripOn = false;
	private double startTripTime;
	private double endTripTime;
	private double distanceLocationPoint;

	private double distanceTravelled = 0;
	int samplingTime = 100;
	int countTimeTravelledDistance = 0;
	double sp1pPrev = 0;
	
	private HTTPConnection http;
	private double latitudeStart;
	private double longitudeStart;
	private double latitudeEnd;
	private double longitudeEnd;
	private double tripTime;
	private boolean isValid;
	
	private AverageSpeed averageSpeed = new AverageSpeed();
	private MaxSpeed maxSpeed = new MaxSpeed();
	private TripDurationIdle tripDurationIdle = new TripDurationIdle();
	private FuelConsumption fuelConsumption = new FuelConsumption();
	private EcoScoring ecoScoring = new EcoScoring();
	private ElecricityConsumption electricityConsumption = new ElecricityConsumption();
	private TravelledDistance travelledDistance;

	private List<LocationPoint> locationPoints = new LinkedList<>();
	
	private PositionInformation positionEnd;
	private PositionInformation positionStart;
	private Date startDate;
	private Date endDate;
	private double travelledDistanceCycle;
	private int cycleCount = 0;
	private boolean finishedForParking = false;
	
	private LocationInfo locationInfo = new LocationInfo();
	private MessageBuffer messageBuffer = new MessageBuffer();

	public Trip(Context context, HDC hdc, SignalHandler signals) {
    	this.context = context;
    	this.hdc = hdc;
    	this.ev = new EV(context);
    	this.eventDetector = new EventDetector(signals);
    	this.output = new Output(context);
    	http = new HTTPConnection(this.context);
    	this.signals = signals;
    	this.travelledDistance = new TravelledDistance(signals);
    	
    }
	
	public void tripStarted() throws IOException{
		
		
	
		EdgeDC.getLocationValue().getCoordinates().setLatitude(signals.getMMC1());
		EdgeDC.getLocationValue().getCoordinates().setLongitude(signals.getMMC2());
		
		
		latitudeStart = EdgeDC.getLocationValue().getCoordinates().getLatitude();
		longitudeStart = EdgeDC.getLocationValue().getCoordinates().getLongitude();
		
		startDate = new Date((long) startTripTime);
		
		
		//context.getLogger().printLog("lat: " + Double.toString(latitudeStart) + " long: " + Double.toString(longitudeStart));
		    
		messageBuffer.newBuffer();
		
		ToyotaBandOnscreen.getInstance().show("Trip Started", 3);
	    
	    try {
	    	context.getEdgeTrip().updatePanelEdgeTrip(String.format("%.2f", signals.getSpeed()), "" + signals.getGear(), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), "", "", String.format("%.0f", (double)0), String.format("%.0f", (double)0), "Drive and then select P to show context.getMetadata()");
	    	  }
	    catch (Exception e2) {
	    	context.getLogger().printLog("Exception " + e2.toString());
	    }
	    finally {
	    }
	    
	    
	    context.getLogger().printLog(Logger.LOG_INFO, "  + Trip started with type: " + context.getVehicleType() + " on " + startDate );
	}
	
	public void tripFinished() throws IOException{
		
		isTripValid();
		
		if (!isValid) {
			context.getLogger().printLog(Logger.LOG_INFO,  "Trip not valid!");
			return;
		}
		
		String type = context.getVehicleType();
		
		if (type.equals("HEV") || type.equals("PHEV")) {
		    ev.computeEVUsage();
		    ev.computeEVTime();
		    ev.computeChargeTime();
		    ev.computeECOTime();
		    ev.computePowerTime();
	    }
	    
		EdgeDC.getLocationValue().getCoordinates().setLatitude(signals.getMMC1());
		EdgeDC.getLocationValue().getCoordinates().setLongitude(signals.getMMC2());
		
		latitudeEnd = EdgeDC.getLocationValue().getCoordinates().getLatitude();
		longitudeEnd = EdgeDC.getLocationValue().getCoordinates().getLongitude();
		endDate = new Date((long) endTime);
		
		context.getLogger().printLog(Logger.LOG_INFO, "Trip Time: " + tripTime + " seconds");
		context.getLogger().printLog(Logger.LOG_INFO, "Number of location points: " + Integer.toString(locationPoints.size()));
		context.getLogger().printLog(Logger.LOG_INFO, "Distance travelled: " + Double.toString(this.distanceTravelled));
		context.getLogger().printLog(Logger.LOG_INFO, "Average speed: " + Double.toString(averageSpeed.getAverage()));
		context.getLogger().printLog(Logger.LOG_INFO, "Max speed: " + Double.toString( maxSpeed.getMax()));
		context.getLogger().printLog(Logger.LOG_INFO, "Count cycle idle: " + Double.toString(tripDurationIdle.getCount()));
		context.getLogger().printLog(Logger.LOG_INFO, "Duration idle: " + Double.toString(tripDurationIdle.getDuration(100)));
		context.getLogger().printLog("lat: " + Double.toString(latitudeEnd) + " long: " + Double.toString(longitudeEnd));
		
	    positionEnd = PositionInformation.newBuilder().setLatitude(latitudeEnd).setLongitude(longitudeEnd).build();
	    
	    //hdc.computeAvgHdc();
	    output.printScores(eventDetector.getEvents(), eventDetector.getEventsABC(), this.cycleCount);
	    output.printEvents(eventDetector.getEvents());
	    
	    try {
	    	//context.getEdgeTrip().updatePanelEdgeTrip(String.format("%.2f", (double)context.getSp1_list().get(context.getSp1_list().size()-1)), context.getGear_final(), String.format("%.5f", (double)context.getLatitude()), String.format("%.5f", (double)context.getLongitude()), context.getRoadtype(), context.getGpsSource(), String.format("%.0f", (double)context.getSoc_dsp()), String.format("%.0f", (double)context.getHttp_code()), (this.getMetaData()));
	    	context.getEdgeTrip().updatePanelEdgeTrip(String.format("%.2f", signals.getSpeed()), "" + signals.getGear(), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), "", "", String.format("%.0f", (double)0), String.format("%.0f", (double)0), "Drive and then select P to show context.getMetadata()");
	    	
	    }
	    catch (Exception e2) {
	    	context.getLogger().printLog("Exception " + e2.toString());
	    }
	    finally {
	    }

	    ToyotaBandOnscreen.getInstance().show("Trip Finished", 3);
	    
//		this.printDataBlocks();
		
		messageBuffer.setTime((int) signals.getTimestampFromIGSignal());
		messageBuffer.setVersion(1);
//		String serialized_msg = Utility.bytesToHexStringCustom(messageBuffer.getMessage().build().toByteArray());
//		http.uploadFile(serialized_msg, "edgetrip");
		
		
		output.printElectricityConsumption(electricityConsumption);
		fuelConsumption.compute(this.distanceTravelled);
		ecoScoring.computeAvgHdc();
		hdc.computeAvgHdc2();
		
		
		output.printFuelConsumption(fuelConsumption);
		output.printLocationPoints(this.locationPoints);
		output.printHDCScores(ecoScoring);
		output.printHDCScores(hdc);
		ev.computeECOTime();
		ev.computeChargeTime();
		ev.computeEVUsage();
		ev.computePowerTime();
		ev.computeEVTime();
		output.storeFile();
		
		context.getLogger().printLog("Trip with ID " + tripId +  " finished!");
		
	}
	
	public double computeTravelledDistance() throws IOException {
		
		double td = 0;

		double speed = signals.getSpeed();
		double sp1p = signals.getSp1p();	

		if (speed >= 1.75) {
			if (sp1p - sp1pPrev >= 0) {
				td = (sp1p - sp1pPrev)/5.096;
			} else {
				td = (sp1p - sp1pPrev +64)/5.096;
			}
			
		}
		sp1pPrev = sp1p;
		
		return td;
	}
	
	public void parkingSelected(double park_time) throws IOException{
		
		context.getLogger().printLog(Logger.LOG_INFO,  "  + P_PARK selected");
		context.getLogger().printLog(Logger.LOG_INFO,   "----------------------------------");

	    ToyotaBandOnscreen.getInstance().show("P_PARK detected", 3);

	}
	
	public String getMetaData(){
		String metadata = "TRIP METADATA" + "\n" + "    - Trip start date: " + this.startDate + "\n" + "    - Trip end date: " + this.endDate + "\n" + "    - Trip duration: " + String.format("%.0f", this.tripTime) + " s" + "\n" + "    - Distance travelled: " + String.format("%.3f", this.distanceTravelled) + " m" + "\n" + "    - Average speed: " + String.format("%.2f", this.averageSpeed.getAverage()) + " km/h" + "\n" + "    - Maximum speed: " + String.format("%.2f", this.maxSpeed.getMax()) + " km/h" + "\n" + "    - Start position: " + String.format("%.5f", this.latitudeStart) + ", " + String.format("%.5f", this.longitudeStart) + " (Latitude, Longitude)" + ", Source: " + "gps trip start" + "\n" + "    - End position: " + String.format("%.5f", this.latitudeEnd) + ", " + String.format("%.5f", this.longitudeEnd) + " (Latitude, Longitude)" + ", Source: " + "gps source" + "\n" + "    - Time on Idle: " + String.format("%.0f", this.tripDurationIdle) + " s" + "\n" + "    - Hybrid Score: " + "\n" + "        Avg trip eco score starting : " + "context.getAvg_start()" + "\n" + "        Avg trip eco score steady driving: " + "context.getAvg_steady()" + "\n" + "        Avg trip eco score deceleration : " + "context.getAvg_decel()" + "\n" + "        Total Avg trip eco score : " + "context.getAvg_tot()" + "\n" + "    - Time on highway: " + String.format("%.0f", (double)(1 * context.getRUN_LOGIC_CYCLE()) / 1000) + " s" + "\n" + "    - Distance on highway: " + String.format("%.3f", (double)0) + " m" + "\n" + "    - SOC level: " + String.format("%.0f", 0) + "%" + "\n" + "    - Network status: " + "context.getNETWORK_STATUS()" + "\n" + "    - Http response: " + String.format("%.0f", (double)2) + " " + "context.getHttp_msg()" + "\n" + "----------------------------------";
	    TripSummary.newBuilder().setTripStartTime((int)this.startTripTime).setTripStartLocation(this.positionStart).setTripEndTime((int)this.endTripTime)
				.setTripEndLocation(this.positionEnd).setTripLength(this.distanceTravelled).setTripDuration((int)this.tripTime)
				.setTripDurationIdle((int) this.tripDurationIdle.getDuration(context.getRUN_LOGIC_CYCLE())).setTripDurationHighway(0)
				.setTripMaxSpeed( (int) this.maxSpeed.getMax()).setTripAvgSpeed(this.averageSpeed.getAverage()).setSocEndTrip(0).setSocStartTrip(0).build();
		
//		context.getEdge_mes_buf().setTripSummary(context.getTrip_sum_buf());

		return metadata;
	}

	public void setTripId(String tripId2) {
		this.tripId = tripId2;
	}

	public void updateLocationOnSpeedTravelledDistance() throws IOException {

			    travelledDistanceCycle = this.computeTravelledDistance();
				
		        double speed = signals.getSpeed();
		        
		        this.distanceTravelled += travelledDistanceCycle;
//		        
//				context.setTrav_dist_for_loc(context.getTrav_dist_for_loc() + trav_dist_cycle);
//				
		        distanceLocationPoint += travelledDistanceCycle;
		        
				double distanceThreshold;
				if (speed >= 70) {
					distanceThreshold = 100;
				}
				
				else if ((35 < speed) && (speed < 70)) {
					distanceThreshold = 50;
				}
				
				else {
					distanceThreshold = 25;
				}
				
				
				if (distanceLocationPoint >= distanceThreshold) {
					distanceLocationPoint -= distanceThreshold;
					
					double latitude = EdgeDC.getLocationValue().getCoordinates().getLatitude();
					double longitude = EdgeDC.getLocationValue().getCoordinates().getLongitude();
					
					double time = signals.getTimestampFromIGSignal();
					
					LocationPoint locationPoint = new LocationPoint(latitude ,longitude, time, ev.getHybridSystemMode(), ev.getTractionType());
					locationPoints.add(locationPoint);
					
					addPositionInformationBuffer(locationPoint);
					
					//context.getLogger().printLog(Logger.LOG_INFO, "DistanceLocationPoint: " + Double.toString(distanceLocationPoint));
					
				}
	}

	public void checkGear() throws IOException {
		char gear = signals.getGear();
		
		if (gear == 'D') {
			this.gearDIsSetOnce = true;
		}
		
		if (gear == 'R') {
			this.gearRIsSetOnce = true;
		}
	}

	public boolean updateTripState() throws IOException {
		
		double ig = signals.getIg();
		double igPrev = signals.getIgPrev();
		
		if (igPrev != ig) {
			if (ig == 1  && !tripOn) {
				//context.getLogger().printLog("Trip started!");
				tripOn  = true;
				startTime = System.nanoTime();
				startTripTime = signals.getTimestampFromIGSignal();
				
				setTripId(Double.toString(signals.getTripCnt()));
				
				//context.getLogger().printLog("startTripTime: " + Double.toString(startTripTime));
				
				this.tripStarted();

			} else if (ig == 0 && tripOn) {
				
				
				tripOn = false;
				endTime = System.nanoTime();
				endTripTime = signals.getTimestampFromIGSignal();
				
				//context.getLogger().printLog("endTripTime: " + Double.toString(endTripTime));
				
				tripTime = (endTripTime - startTripTime)/1000;

				this.tripFinished();
				
				return true;		
			} else {
				
			}
		}
				
		return false;
	}

	public void setSignalHandler(SignalHandler signals) {
		this.signals = signals;
	}

	public void resetGearHistory() {
		this.gearDIsSetOnce = false;
		this.gearRIsSetOnce = false;
	}
	
	public String getTripId() {
		return this.tripId;
	}

	public boolean isValid() {
		return isValid;
	}
	
	private void metricsComputation() throws IOException {
		this.checkGear();
		this.cycleCount++;
		averageSpeed.addCycleSpeed(signals.getSpeed(), signals.getGear());
		maxSpeed.checkMax(signals.getSpeed());
		tripDurationIdle.checkIdle(signals.getGear());		
		fuelConsumption.add(signals.getB_FC());
		electricityConsumption.add(signals.getEC_MON());
		ecoScoring.add(signals.getEspohvid(), signals.getEspohvidPrev(), signals.getEspohvvl(), (long) signals.getTimestampFromIGSignal());
		hdc.computeHDCScores(signals.getEspohvid(), signals.getEspohvidPrev(), signals.getEspohvvl(), (long) signals.getTimestampFromIGSignal(), signals.getSpeedPrev(), signals.getEspohvvlPrev());
	}
	
	public void computeCycle() throws IOException {
		metricsComputation();
		updateLocationOnSpeedTravelledDistance();
		locationInfo.updateLocationInfo();
		buildCanData();
		checkParkingSelected();

		ev.checkEVActive(signals.getRPM(), this.travelledDistanceCycle);
		ev.checkEVCharge(signals.getECOHVCR(), this.travelledDistanceCycle);
		eventDetector.detectAndAddEvent();
		
		context.getEdgeTrip().updatePanelEdgeTrip(String.format("%.2f", signals.getSpeed()), "" + signals.getGear(), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), String.format("%.5f", EdgeDC.getLocationValue().getCoordinates().getLatitude()), "" + locationInfo.getRoadInfo().getType(), locationInfo.getGpsSource(), String.format("%.0f", (double)0), String.format("%.0f", (double)0), "Drive and then select P to show context.getMetadata()");
	}

	private void checkParkingSelected() throws IOException {
		if (signals.getGearPrev() != 'P' && signals.getGear() == 'P') {
	    	parkingSelected(signals.getTimestampFromIGSignal());
	    }
		
	}

	private void buildCanData() throws IOException {
		CanData canData= CanData.newBuilder().setCanTimestamp((int) signals.getTimestampFromIGSignal()).setSpeed(signals.getSpeed()).setAccFb(signals.getAccx())
		.setAccRl(signals.getAccy()).setGearValue(signals.getGear()).setStateOfCharge(0).build();
		
		messageBuffer.addCanData(canData);
		
	}
	
	private void addPositionInformationBuffer(LocationPoint locationPoint) {
		messageBuffer.addLocationPoint(locationPoint);
	}
	
	private void isTripValid() {
		
	    if (tripTime < 10) {
	    	isValid = false;
	    	return;
	    }
	    
	    if (this.distanceTravelled < 50) {
	    	isValid = false;
	    	return;
	    }
	    
	    if (!this.gearDIsSetOnce && !this.gearRIsSetOnce) {
	    	isValid = false;
	    	return;
	    }
	    
	    isValid = true;
	    return;
		
	}
	
	
	public boolean getTripOn() {
		return tripOn;
	}

	public boolean isFinishedForParking() {
		return finishedForParking;
	}

	public void setFinishedForParking(boolean finishedForParking) {
		this.finishedForParking = finishedForParking;
	}
}



