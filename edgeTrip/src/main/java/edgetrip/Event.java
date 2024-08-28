package edgetrip;

public class Event {

	private String type;
	private boolean isGood;
	private double latitude;
	private double longitude;
	private double timestamp;




	public Event(double latitude2, double longitude2, double timestamp2, String type2, boolean isGood2) {
		this.latitude = latitude2;
		this.longitude = longitude2;
		this.timestamp = timestamp2;
		this.type = type2;
		this.isGood = isGood2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isGood() {
		return isGood;
	}

	public void setGood(boolean isGood) {
		this.isGood = isGood;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(double timestamp) {
		this.timestamp = timestamp;
	}

	
}
