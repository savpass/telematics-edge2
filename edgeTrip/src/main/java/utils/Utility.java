package utils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;


public class Utility {
	
    public static int getSignalValFromCanMsg(String canmsg, int lsbBit, int length) {
        int bytepos = (int) Math.floor((double) lsbBit / 8) + 1;
        int bitposinByte = lsbBit - (int) Math.floor((double) lsbBit / 8) * 8;
        int lengthinbyte = (int) (Math.ceil((double) length / 8));
        String substring = String.copyValueOf(canmsg.toCharArray(), (bytepos - lengthinbyte) * 2, lengthinbyte * 2);
        int substringindec = Integer.decode("0x" + substring);
        int maskindec = 0;
        int i = 0;
        while (i < length) {
            maskindec = maskindec + (int) Math.pow(2, i + bitposinByte);
            i = i + 1;
        }
        int val = (substringindec & maskindec) >> (bitposinByte);
        return val;
    }
    
    public static String bytesToHexStringCustom(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(String.format("%02X", b));
        }
        return stringBuilder.toString();
    }
    
    public static String convertEpochToDateAndTime(int date) {
        Date my_date = new Date(((long) date)*1000);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = format.format(my_date);
        return formatted;
    }
    
    
	public static HttpResponse<String> uploadFile(String payload, String app) {
		
		// Get the current UTC date time
	    ZonedDateTime currentUTCDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedUTCDateTime = currentUTCDateTime.format(formatter);
	    
	    // Build JSON body
	    String jsonBody = String.format("{\"App\": \"%s\", \"Metadata\": {\"TimeAtCreation (UTC)\": \"%s\",  \"Target\": \"gcloud\"}, \"Message\": \"%s\"}", app, formattedUTCDateTime, payload);
	    //mylogger.printLog(LoggerClass.LOG_INFO, jsonBody);
		
		// Build request
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://edge-server-4nfah32xkq-ew.a.run.app/ingest"))
				.header("Content-Type", "application/json")
				.method("POST", HttpRequest.BodyPublishers.ofString(jsonBody))
				.build();
		// Get response
		HttpResponse<String> response = null;
		try {
			response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			//mylogger.printLog(LoggerClass.LOG_INFO, ""+response.statusCode());
			//mylogger.printLog(LoggerClass.LOG_INFO, response.body());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return response;
	}

}
