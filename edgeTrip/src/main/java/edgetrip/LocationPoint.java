package edgetrip;

public class LocationPoint {

	private double latitude;
	private double longitude;
	private double timestamp;
	private String hybridSystemMode;
	private String tractionType;

	public LocationPoint(double latitude, double longitude, double timestamp, String hybridSystemMode, String tractionType) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.timestamp = timestamp;
		this.hybridSystemMode = hybridSystemMode;
		this.tractionType = tractionType;
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

	public String getHybridSystemMode() {
		return hybridSystemMode;
	}

	public void setHybridSystemMode(String hybridSystemMode) {
		this.hybridSystemMode = hybridSystemMode;
	}

	public String getTractionType() {
		return tractionType;
	}

	public void setTractionType(String tractionType) {
		this.tractionType = tractionType;
	}
	
	

}
