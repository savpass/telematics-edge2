package edgetrip;


//import com.ibm.automotive.location.RoadInfo;

import com.ibm.automotive.location.RoadInfo;

import tme.com.toyota.edgedc.EdgeDC;

public class LocationInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1732273647625110477L;
	
	// class Address
	protected String[] addressAddressInfo = new String[4];
	public static final short ADDRESSFIELD_1 = 0;
	public static final short ADDRESSFIELD_2 = 1;
	public static final short ADDRESSFIELD_3 = 2;
	public static final short ADDRESSFIELD_4 = 3;
	public static final int MAX_ADDRESSFIELD_LENGTH = 256;
	
	// class Coordinates
	protected double coordinatesLatitude = 0;
	protected double coordinatesLongitude = 0;
	public static final int INVALID_COORDINATE = 999;
	
	protected float locationCourse = 0;

	// class RoadInfo
	protected String roadInfoName = "";
	protected short roadInfoType = 0;
	protected String[] roadInfoAddressInfo = new String[4];
//	public static final short ROADTYPE_1 = RoadInfo.ROADTYPE_1;
	
	public static final short ROADTYPE_1 = RoadInfo.ROADTYPE_1;
	public static final short ROADTYPE_2 = RoadInfo.ROADTYPE_2;
	public static final short ROADTYPE_3 = RoadInfo.ROADTYPE_3;
	public static final short ROADTYPE_4 = RoadInfo.ROADTYPE_4;
	public static final short ROADTYPE_5 = RoadInfo.ROADTYPE_5;
	public static final short ROADTYPE_6 = RoadInfo.ROADTYPE_6;
	public static final short ROADTYPE_7 = RoadInfo.ROADTYPE_7;
	
	public static final String INVALID_INFO = "NA";
	public static final short  INVALID_ROADTYPE = ROADTYPE_6;
	public static final float  INVALID_COURSE = -1;	
	
	protected long locationTimestamp = 0;

	private String gpsSource;

	private RoadInfo roadInfo;
	
	

	public String getGpsSource() {
		return gpsSource;
	}

	public void setGpsSource(String gpsSource) {
		this.gpsSource = gpsSource;
	}

	public RoadInfo getRoadInfo() {
		return roadInfo;
	}

	public void setRoadInfo(RoadInfo roadInfo) {
		this.roadInfo = roadInfo;
	}

	private int onHighway = 0;

	public String getAddressField(short field) {
		if (field > ADDRESSFIELD_4) {
			return "INVALID";
		} else {
			return addressAddressInfo[field];
		}
	}

	public double getCoordinatesLatitude() {
		return coordinatesLatitude;
	}

	public double getCoordinatesLongitude() {
		return coordinatesLongitude;
	}

	public float getLocationCourse() {
		return locationCourse;
	}

	public String getRoadInfoName() {
		return roadInfoName;
	}

	public String getRoadInfoType() {
		String ret = "NA for EU";
//		if (roadInfoType == 0 ) {
//			ret = "Intercity express-way";
//		}
//		else if (roadInfoType == 1) {
//			ret = "City express-way";
//		}
//		else if (roadInfoType == 2) {
//			ret = "Toll road";
//		}
//		else if (roadInfoType == 3) {
//			ret = "National road";
//		}
//		else if (roadInfoType == 4) {
//			ret = "Perfectural road";
//		}
//		else if (roadInfoType == 5) {
//			ret = "General road";
//		}
//		else if (roadInfoType == 6) {
//			ret = "Unspecified road (not in table)";
//		}
//		else {
//			ret = "Road type unknown/not retrieved";
//		}
		return ret;
	}

	public String getRoadInfoAddressField(short field) {
		if (field > ADDRESSFIELD_4) {
			return "INVALID";
		} else {
			return roadInfoAddressInfo[field];
		}
	}

	public long getLocationTimestamp() {
		return locationTimestamp;
	}
	
	public void updateLocationInfo(){
//	    try {

		
	       if (EdgeDC.getLocationValue().getRoadInfo().equals("City express-way")) {
		         onHighway++;
		        }
		           	
	        this.roadInfo = EdgeDC.getLocationValue().getRoadInfo();
	        
	        if ((EdgeDC.getLocationValue().getCoordinates().getLatitude() != INVALID_COORDINATE) && (EdgeDC.getLocationValue().getCoordinates().getLongitude() != INVALID_COORDINATE)) {
	        	 this.gpsSource = "MM";
	        } else {
	        	this.gpsSource = "NA";
	        }
//	    }
//	    catch (Exception e2) {
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "udpdate LocationInfo" + e2.toString());
//	    }
//	    finally {
//	    }
	}
}