package edgetrip;

import tme.edgetrip.protos.CanData;
import tme.edgetrip.protos.EdgeTripMessage;
import tme.edgetrip.protos.EdgeTripMessage.Builder;
import tme.edgetrip.protos.LocationData;
import tme.edgetrip.protos.PositionInformation;

public class MessageBuffer {
	private EdgeTripMessage.Builder builder;
	private int time;
	private int version;
	
	public MessageBuffer() {
		//builder = EdgeTripMessage.newBuilder();
	}

	public void addCanData(CanData canData) {
		builder.addCanData(canData);
		
	}
	
	public void addLocationPoint(LocationPoint locationPoint) {
		PositionInformation pos_info = PositionInformation.newBuilder().setLatitude(locationPoint.getLatitude()).setLongitude(locationPoint.getLongitude()).build();
		LocationData loc_data = LocationData.newBuilder().setLocationTimestamp( (int) locationPoint.getTimestamp()).setPosition(pos_info).build();

		builder.addLocationData(loc_data);
		
	}

	public Builder getMessage() {
		return builder;
		
	}

	public void setTime(int timestampFromIGSignal) {
		this.time = timestampFromIGSignal;
		
	}

	public void setVersion(int i) {
		this.version = i;
		
	}

	public void newBuffer() {
		builder = EdgeTripMessage.newBuilder();
		
	}
	
}
