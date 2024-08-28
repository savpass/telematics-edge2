package edgetrip;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.ibm.automotive.logger.Logger;

import utils.Utility;

public class HTTPConnection {
	
    private Context context;


	public HTTPConnection(Context context) {
    	this.context = context;
    }
	
	public void uploadFile(String payload, String app) throws IOException {
		
		// Get the current UTC date time
	    ZonedDateTime currentUTCDateTime = ZonedDateTime.now(ZoneId.of("UTC"));
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    String formattedUTCDateTime = currentUTCDateTime.format(formatter);
	    
	    // Build JSON body
	    String jsonBody = String.format("{\"App\": \"%s\", \"Metadata\": {\"TimeAtCreation (UTC)\": \"%s\",  \"Target\": \"gcloud\"}, \"Message\": \"%s\"}", app, formattedUTCDateTime, payload);
	  //  context.getMylogger().printLog(LoggerClass.LOG_INFO, jsonBody);
		
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
			context.getLogger().printLog(Logger.LOG_INFO, "" + response.statusCode());
			context.getLogger().printLog(Logger.LOG_INFO, response.body());
//			context.getMylogger().printLog(LoggerClass.LOG_INFO, ""+response.statusCode());
//			context.getMylogger().printLog(LoggerClass.LOG_INFO, response.body());
		} catch (IOException e) {
			context.getLogger().printLog(e.getMessage());
		} catch (InterruptedException e) {
			context.getLogger().printLog(e.getMessage());
		}catch (Exception e) {
			context.getLogger().printLog(e.getMessage());
		}
	}

	public void upload() throws IOException {
//		context.getEdge_mes_buf().setMessageCreation((int)context.getCurrent_time());
//		context.getEdge_mes_buf().setDataVersion(1);
		//String serialized_msg = Utility.bytesToHexStringCustom(context.getEdge_mes_buf().build().toByteArray());
		String serialized_msg = "Try";
		this.uploadFile(serialized_msg, "edgetrip");
		
		
	}

}