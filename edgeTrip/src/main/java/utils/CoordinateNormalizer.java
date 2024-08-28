package utils;

public class CoordinateNormalizer {

    public static double normalizeLatitude(double latitude) {
    	  // Normalize to -360 to 360
        latitude = latitude % 360;
        // Adjust to -180 to 180
        if (latitude > 180) {
            latitude -= 360;
        } else if (latitude < -180) {
            latitude += 360;
        }
        // Reflect to -90 to 90
        if (latitude > 90) {
            latitude = 180 - latitude;
        } else if (latitude < -90) {
            latitude = -180 - latitude;
        }
        return latitude;
    }

    public static double normalizeLongitude(double longitude) {
        // Normalize the longitude to the range -180 to 180
        longitude = ((longitude + 180) % 360 + 360) % 360 - 180;
        return longitude;
    }
}
