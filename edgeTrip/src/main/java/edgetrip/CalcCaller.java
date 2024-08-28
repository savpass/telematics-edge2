package edgetrip;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tme.com.toyota.edgedc.EdgeDC;
import tme.com.toyota.edgedc.can.TimedCanData;

import com.ibm.automotive.logger.Logger;

public class CalcCaller {

	private Context context;
	private Calc calc;


	public CalcCaller(Context context, Calc calc) {
    	this.context = context;
    	this.calc = calc;
    }


	public boolean callSetParam() {
		// TODO Auto-generated method stub
		return false;
	}

	public void callCalc() throws IOException {
		
		//context.getLogger().printLog(Logger.LOG_INFO, "callCalc called");
        
        Map<Integer, List<TimedCanData>> input_data = new HashMap<Integer, List<TimedCanData>>();

        long timestampPrev = 0;
        
        for (String id : context.getCanIds()) {
            try {
            	//context.getMylogger().printLog(LoggerClass.LOG_INFO, "Id: " + elem);
                //int myid = Integer.parseInt(elem, 16);
            	
                int myid = Integer.parseInt(id.replace("0x", ""), 16);

                int sampling_time = 100;
                double BUFFERWINDOW = 200;
                

                //context.lookupForDataCollector();
                int BUFFERSIZE = (int) Math.ceil(BUFFERWINDOW / sampling_time);
                
                //context.getLogger().printLog(Logger.LOG_INFO, "Buffer size" + Integer.toString(BUFFERSIZE));
                
                List<TimedCanData> buffer = EdgeDC.getCanDataFromBuffer(myid, BUFFERSIZE, sampling_time);

                //TimedCanData can = buffer.get(0);

                if (buffer.size() < BUFFERSIZE) {
                    // If not enough data to run application, do not call CALC
                    
                	//context.getLogger().printLog(Logger.LOG_DEBUG, "callCalc not enough records for " + id);
                  
                } else {
                    input_data.put(myid, buffer);
                }
            } finally {
        
            }
        }
        
//        for (int index : input_data.keySet()) {
//        	if(index == 215)
        		//context.getLogger().printLog(Logger.LOG_INFO, "" + input_data.get(215).get(0).getValue() +  " " + input_data.get(215).get(0).getTimestamp());
//        }
        
        
//      for (int index : input_data.keySet()) {
//    	if(index == 215) {
//    		  context.getLogger().printLog(Logger.LOG_INFO, "Diff: " + (input_data.get(215).get(0).getTimestamp() - input_data.get(215).get(1).getTimestamp()));
//    	        
//    	}
//    		
//     }
//        try {
//        	 context.getLogger().printLog(Logger.LOG_INFO, "" + (input_data.get(0x3c9).get(0).getValue()));
//        }catch (Exception e) {
//        	
//        }
       
        
      
        
        calc.calc(input_data);
        

	}

}

 
    









