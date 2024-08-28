package edgetrip;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ibm.automotive.logger.Logger;

import tme.com.toyota.edgedc.can.TimedCanData;
import utils.LoggerCustom;

public class SignalHandler {
	
	private Map<Integer, List<TimedCanData>> input_data;
	private Context context;
	
	
	public SignalHandler(Context context) {
		this.context = context;
	}	
	
	private double getSignalValFromCanMsg2(Map<Integer, List<TimedCanData>> input_data, int canid,
		            int lsbBit, int length,
		            boolean signed, double resolution, double offset) throws IOException {
		String canmsg;

	    // Get CAN message
	    try {
	        canmsg = input_data.get(canid).get(0).getValue();
	    } catch (Exception e) {
	        //context.getLogger().printLog(Logger.LOG_DEBUG, "Value null");
	        return 0;
	    }

	    // Ensure the CAN message is not longer than 64 bits (16 hex characters)
	    if (canmsg.length() > 16) {
	        throw new IllegalArgumentException("CAN message longer than 64 bits is not supported");
	    }

	    // Parse the CAN message as a long integer
	    long canmsgLong = Long.parseUnsignedLong(canmsg, 16);

	    // Create a mask for the bits we are interested in
	    long mask = (1L << length) - 1;

	    // Extract the value
	    long val = (canmsgLong >> lsbBit) & mask;

	    // Log the extracted value for debugging
	    if (canid == 0x3c9) {
	        context.getLogger().printLog(Logger.LOG_DEBUG, Long.toString(val));
	    }

	    // Apply conversion if signed
	    if (signed && (val >= (1L << (length - 1)))) {
	        val = val - (1L << length);
	    }

	    // Convert to physical value
	    double physical_val = (val * resolution) + offset;

	    // Return the signal value
	    return physical_val;
}
	
	
	private double getSignalValFromCanMsg(Map<Integer, List<TimedCanData>> input_data, int canid,
            int lsbBit, int length,
            boolean signed, double resolution, double offset) throws IOException {
		String canmsg;
        // Get CAN message
		try {
			
			canmsg = input_data.get(canid).get(0).getValue();
			
		}catch (Exception e){
			context.getLogger().printLog(Logger.LOG_DEBUG, "Value for " + Integer.toString(canid) + " returned null");
			
			return -1;
		}
        
        
     
        // Extract signal
        int bytepos = (int) Math.floor((double) lsbBit / 8) + 1;
        int bitposinByte = lsbBit - (int) Math.floor((double) lsbBit / 8) * 8;
        int lengthinbyte = (int) (Math.ceil((double) length / 8));
        String substring = String.copyValueOf(canmsg.toCharArray(), (bytepos - lengthinbyte) * 2,
                lengthinbyte * 2);
        int substringindec = Integer.decode("0x" + substring);
        int maskindec = 0;
        int i = 0;
        
       
        
        while (i < length) {
            maskindec = maskindec + (int) Math.pow(2, i + bitposinByte);
            i = i + 1;
        }
        int val = (substringindec & maskindec) >> (bitposinByte);
        
//        if (canid == 0x3c9)
//        	context.getLogger().printLog(Logger.LOG_DEBUG,"Value: " + Integer.toString(val) + "; Substring: " + Integer.toString(substringindec) + "; maskindec: " + Integer.toString(maskindec) + "; bitposinByte: " + Integer.toString(bitposinByte));
        
        // Apply conversion if signed
        if ((signed) && (val >= ((int) Math.pow(2, length) / 2))) {
            val = -((int) Math.pow(2, length) - val);
        }
// 
//        if (canid == 0x3d3) {
//        	context.getLogger().printLog(Logger.LOG_DEBUG, canmsg);
//        	context.getLogger().printLog(Logger.LOG_DEBUG, Double.toString(val));
//        	
//        }
        // Convert to physical value
        double physical_val = (val * resolution) + offset;
 
        // Return signal value
        return physical_val;
    }
	
	private double getPrevSignalValFromCanMsg(Map<Integer, List<TimedCanData>> input_data, int canid,
            int lsbBit, int length,
            boolean signed, double resolution, double offset) throws IOException {
 
		
		String canmsg;
        // Get CAN message
		try {
			canmsg = input_data.get(canid).get(1).getValue();
			
		}catch (Exception e){
			//context.getLogger().printLog(Logger.LOG_DEBUG, "Value null");
			return 0;
		}
	
 
        // Extract signal
        int bytepos = (int) Math.floor((double) lsbBit / 8) + 1;
        int bitposinByte = lsbBit - (int) Math.floor((double) lsbBit / 8) * 8;
        int lengthinbyte = (int) (Math.ceil((double) length / 8));
        String substring = String.copyValueOf(canmsg.toCharArray(), (bytepos - lengthinbyte) * 2,
                lengthinbyte * 2);
        int substringindec = Integer.decode("0x" + substring);
        int maskindec = 0;
        int i = 0;
        while (i < length) {
            maskindec = maskindec + (int) Math.pow(2, i + bitposinByte);
            i = i + 1;
        }
        int val = (substringindec & maskindec) >> (bitposinByte);
 
        // Apply conversion if signed
        if ((signed) && (val >= ((int) Math.pow(2, length) / 2))) {
            val = -((int) Math.pow(2, length) - val);
        }
 
        // Convert to physical value
        double physical_val = (val * resolution) + offset;
 
        // Return signal value
        return physical_val;
    }


	
	public void setLatestInput(Map<Integer, List<TimedCanData>> input_data) {
		this.input_data = input_data;	
	}
	
	

	public void setIg() {
		//context.setIg(getSignalValFromCanMsg(input_data, 0x02d1, 8, 1, false, 1.0, 0.0));
}

	public void update() {        
	}
	
	public double getTripCnt() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x63b, 24, 16, false, 1.0, 0.0);
	}
	
	public double getVin1() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 0, 8, false, 1.0, 0.0);
	}
	
	public double getVin2() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 8, 8, false, 1.0, 0.0);
	}
	
	public double getVin3() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 16, 8, false, 1.0, 0.0);
	}
	
	public double getVin4() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 24, 8, false, 1.0, 0.0);
	}
	
	public double getVin5() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 32, 8, false, 1.0, 0.0);
	}
	
	public double getVin6() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 40, 8, false, 1.0, 0.0);
	}
	
	public double getVin7() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 48, 8, false, 1.0, 0.0);
	}
	
	public double getVin8() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0580, 56, 8, false, 1.0, 0.0);
	}
	
	public double getVin9() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 0, 8, false, 1.0, 0.0);
	}
	
	public double getVin10() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 8, 8, false, 1.0, 0.0);
	}
	
	public double getVin11() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 16, 8, false, 1.0, 0.0);
	}
	
	public double getVin12() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 24, 8, false, 1.0, 0.0);
	}
	
	public double getVin13() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 32, 8, false, 1.0, 0.0);
	}
	
	public double getVin14() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 40, 8, false, 1.0, 0.0);
	}
	
	public double getVin15() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 48, 8, false, 1.0, 0.0);
	}
	
	public double getVin16() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0581, 56, 8, false, 1.0, 0.0);
	}
	
	public double getVin17() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0582, 0, 8, false, 1.0, 0.0);
	}

	public void updateVIN() {
	}

	public double getTimestampFromIGSignal() {
		return input_data.get(0x02d1).get(0).getTimestamp();
	}
	
	public double getPrevTimestampFromIGSignal() {
		return input_data.get(0x02d1).get(1).getTimestamp();
	}

	public double getTimestampFromESPOSignal() {
		return input_data.get(0x03fa).get(0).getTimestamp();
	}
 
	public double getB_FC() throws IOException {
		return  getSignalValFromCanMsg(input_data, 0x3d3, 8, 16, false, 0.000488, 0.0);
	}
	
	public double getPrevB_FC() throws IOException {
		return  getPrevSignalValFromCanMsg(input_data, 0x3d3, 8, 16, false, 0.0, 0.0);
	}
	
	public void updateEC_MON() {
	}

	public void updateRPM() throws IOException {
		double rpm = getSignalValFromCanMsg(input_data, 0x115, 8, 16, false, 1.0, 0.0);
	}

	public void updateIECOHVCR() throws IOException {
		double iecohvcr = getSignalValFromCanMsg(input_data, 0x247, 8, 10, false, 1.0, 0.0);
	}

	public double getEACCP() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x116, 0, 8, false, 1.0, 0.0);
	}

	public double getPMC() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x101, 0, 8, false, 1.0, 0.0);
	}
	
	


//	public Optional<Double> getIg() {
//		Optional<Double> optionalIg = Optional.of(getSignalValFromCanMsg(input_data, 0x02d1, 8, 1, false, 1.0, 0.0));
//		
//		return optionalIg;
//		
//	}
	
	public double getIg() throws IOException {
		
		double ig = getSignalValFromCanMsg(input_data, 0x02d1, 8, 1, false, 1.0, 0.0);
		//context.getLogger().printLog("IG: " +  Double.toString(ig));
		return ig;
		
	}
	
	public double getIgPrev() throws IOException {
		try {
		return getPrevSignalValFromCanMsg(input_data, 0x02d1, 8, 1, false, 1.0, 0.0);
	} catch(Exception e) {
		context.getLogger().printLog("Prev value for IG doesn't exist! Expection:" + e.toString());
	}
	return 0;
	}


	public char getGear() throws IOException {
		
	  if ((getSignalValFromCanMsg(input_data, 0x03bf, 7, 1, false, 1.0, 0.0)) != 0) {
		  return 'P';
	  }
	  
	  if ((getSignalValFromCanMsg(input_data, 0x03bf, 6, 1, false, 1.0, 0.0)) != 0) {
		  return 'R';
	  }
	  
	  if ((getSignalValFromCanMsg(input_data, 0x03bf, 5, 1, false, 1.0, 0.0)) != 0) {
		  return 'N';
	  }
	  
	  if ((getSignalValFromCanMsg(input_data, 0x03bf, 4, 1, false, 1.0, 0.0)) != 0) {
		  return 'D';
	  }
	  
	  if ((getSignalValFromCanMsg(input_data, 0x03bf, 15, 1, false, 1.0, 0.0)) != 0) {
		  return 'B';
	  }
	return 0;
	
	}
	
	public char getGearPrev() throws IOException {
		
		  if ((getPrevSignalValFromCanMsg(input_data, 0x03bf, 7, 1, false, 1.0, 0.0)) != 0) {
			  return 'P';
		  }
		  
		  if ((getPrevSignalValFromCanMsg(input_data, 0x03bf, 6, 1, false, 1.0, 0.0)) != 0) {
			  return 'R';
		  }
		  
		  if ((getPrevSignalValFromCanMsg(input_data, 0x03bf, 5, 1, false, 1.0, 0.0)) != 0) {
			  return 'N';
		  }
		  
		  if ((getPrevSignalValFromCanMsg(input_data, 0x03bf, 4, 1, false, 1.0, 0.0)) != 0) {
			  return 'D';
		  }
		  
		  if ((getPrevSignalValFromCanMsg(input_data, 0x03bf, 15, 1, false, 1.0, 0.0)) != 0) {
			  return 'B';
		  }
		return 0;
		
		}


	public double getSpeed() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x00d7, 16, 16, false, 0.01, 0.0);
		
	}

	public double getSpeedPrev() throws IOException {
		return getPrevSignalValFromCanMsg(input_data, 0x00d7, 16, 16, false, 0.01, 0.0);
		
	}


	public double getSp1p() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x00d7, 0, 6, false, 1.0, 0.0);
		
	}


	public double getSp1pPrev() throws IOException {
		return getPrevSignalValFromCanMsg(input_data, 0x00d7, 0, 6, false, 1.0, 0.0);
		
	}


	public double getMMC1() throws IOException {
		double latitude = getSignalValFromCanMsg(input_data, 0x3c9, 24, 32, false, 1.0, 0.0);
		
//		double latNormalized = utils.CoordinateNormalizer.normalizeLatitude(latitude);
//		return latNormalized;
		
		return latitude/128/3600;
		
	}


	public double getMMC2() throws IOException {
		double longitude = getSignalValFromCanMsg(input_data, 0x3c9, 56, 32, false, 1.0, 0.0);
		
//		double longNormalized = utils.CoordinateNormalizer.normalizeLongitude(longitude);
//		return longNormalized;
		
		return longitude/128/3600;
		
	}

	public int getEC_MON() throws IOException {
		
		return (int) getSignalValFromCanMsg(input_data, 0x3f4, 8, 16, false, 0.0, 0.0);
	}

	public int getH2CONSU() throws IOException {
		
		return (int) getSignalValFromCanMsg(input_data, 0x3f4, 8, 16, false, 0.0, 0.0);
	}
	
	public double getRPM() throws IOException {
		double rpm = getSignalValFromCanMsg(input_data, 0x115, 8, 16, false, 1.0, 0.0);
		return rpm;
	}
	
	public double getECOHVCR() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x247, 8, 10, true, 1.0, 0.0);
		
	}

	public double getAccx() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0090, 8, 10, false, 0.03589, -18.375);
	}
	
	public double getAccy() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x0090, 24, 10, false, 0.03589, -18.375);
		
	}

	public double getEspohvid() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x03fa, 0, 3, false, 1.0, 0.0);
	}

	public double getEspohvidPrev() throws IOException {
		return getPrevSignalValFromCanMsg(input_data, 0x03fa, 0, 3, false, 1.0, 0.0);
	}

	public double getEspohvvl() throws IOException {
		
		return getSignalValFromCanMsg(input_data, 0x03fa, 3, 5, false, 1.0, 0.0);
	}

	public double getSp1Prev() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getEspohvvlPrev() throws IOException {
		// TODO Auto-generated method stub
		return getPrevSignalValFromCanMsg(input_data, 0x03fa, 3, 5, false, 1.0, 0.0);
	}

	public double get58A() throws IOException {
		return getSignalValFromCanMsg(input_data, 0x58a, 4, 4, false, 1.0, 0.0);
		
	}



}
