package edgetrip;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


import com.ibm.automotive.logger.Logger;

import jp.co.toyota.system.ToyotaParameters;
import tme.com.toyota.edgedc.EdgeDC;
import utils.LoggerCustom;

public class Init {
	
	private Context context;
	
	public Init(Context context) {
    	this.context = context;
    }
	
	private  List<String> init() throws RemoteException, IOException  {
		List<String> l_input_data_config = new ArrayList<String>();	    
	    List<String> canIds = new LinkedList<>();
//	    
//	    canIds.add("0x0432");
//	    l_input_data_config.add("0x0432");
	    
	    canIds.add("0x03bf");
	    l_input_data_config.add("0x03bf");
	    canIds.add("0x0090");
	    l_input_data_config.add("0x0090");
	    canIds.add("0x00d7");
	    l_input_data_config.add("0x00d7");
	    
	    canIds.add("0x03fa");
	    l_input_data_config.add("0x03fa");
	    canIds.add("0x02d1");
	    l_input_data_config.add("0x02d1");
	    canIds.add("0x580");
	    l_input_data_config.add("0x580");
	    canIds.add("0x581");
	    l_input_data_config.add("0x581");
//	    
//	    canIds.add("0x582");
//	    l_input_data_config.add("0x582");
	    canIds.add("0x3d3");
	    l_input_data_config.add("0x3d3");
	    
	    canIds.add("0x115");
	    l_input_data_config.add("0x115");
	    canIds.add("0x247");
	    l_input_data_config.add("0x247");
	    
	    canIds.add("0x116");
	    l_input_data_config.add("0x116");
	    canIds.add("0x101");
	    l_input_data_config.add("0x101");
	    canIds.add("0x3f4");
	    l_input_data_config.add("0x3f4");
	    canIds.add("0x63b");
	    l_input_data_config.add("0x63b");
	    
	    canIds.add("0x3c9");
	    l_input_data_config.add("0x3c9");
	    
	    canIds.add("0x58a");
	    
	   
	    EdgeDC.registerLocation();
		EdgeDC.registerCanIds(canIds, true);

//	    EdgeDC.registerCanIds(canIds);
		
		
	    
	    return l_input_data_config;
	}

	public void preInit() throws IOException {
		
		//trip_ongoing = false;
		
	    context.getLogger().printLog(Logger.LOG_DEBUG, "Initializing app global variables");
	    
	    //COLLECTION_CYCLE = 5000;
//	    PANEL_UPDATE_CYCLE = 500;
//	    running_displayed = false;
//	    time_steady_update = Instant.now().getEpochSecond();
//	    espohvid_prev = 7;
//	    espohvvl_prev = 0;
//	    sp1_prev = 0;
//	    ig = 0;
//	    ig_prev = 0;
//	    gear = "P_PARK";
//	    gear_prev = "P_PARK";
//	    score_start_list = new ArrayList<Double>();
//	    score_steady_list = new ArrayList<Double>();
//	    score_decel_list = new ArrayList<Double>();
//	    espohvvl_hold_start = new ArrayList<Double>();
//	    espohvvl_hold_steady = new ArrayList<Double>();
//	    espohvvl_hold_decel = new ArrayList<Double>();
	    
	    String vehicle_type = ToyotaParameters.getParameter(ToyotaParameters.KEY_VEHICLE_TYPE);
	    
	    
	    if (vehicle_type.equals("0")) {
	    	
	    	context.setVehicleType("UNKNOWN");
	    	
	    	
	    	
	    } else if(vehicle_type.equals("1")) {
	    	 
	    	context.setVehicleType("ICE");
	    	
	    	
	    } else if (vehicle_type.equals("2")) {
	    	
	    	context.setVehicleType("HEV");

	    	
	    	
	    }else if (vehicle_type.equals("3")) {
	    	
	    	context.setVehicleType("PHEV");
	    	
	    	
	    	
	    }else if (vehicle_type.equals("4")) {
	    	
	    	context.setVehicleType("BEV");
	    	
	    	
	    	
	    }else if (vehicle_type.equals("5")) {
	    	
	    	context.setVehicleType("FCEV");
	    	
	    	
	    	
	    }
	    
	    
	    
	    context.getLogger().printLog(Logger.LOG_INFO, "----------------------------------");
	    context.getLogger().printLog(Logger.LOG_INFO, "Edge Trip Application Initialized");
	    context.getLogger().printLog(Logger.LOG_INFO, "----------------------------------");
	    
	    
		
	}
	
	
	public void callInit() {
	    
	    try {
	        this.preInit();
	        context.setCanIds((List<String>) this.init());
	        
	        //context.lookupForDataCollector();
	        //ret = context.getDtcservice().insertCanIdToListToRecord(context.get_input_data_config());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	   
	}
	
	
}
