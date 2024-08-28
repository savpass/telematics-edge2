/*
 * -- This code has been generated. Please do not modify it! Modification is at your own risk!
 * 
 * Copyright (c) 2023, Toyota Motor Europe
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package edgetrip;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.ibm.automotive.logger.Logger;

import jp.co.toyota.sensor.CANDataFrame;
import utils.LoggerCustom;

public class EdgeTripImpl {

	private EdgeTrip edgeTrip;
	private Timer apptimer;
	private TimerTask apptimertask;
	
	private Context context;
	private Trip trip;
	private Calc calc;
	private CalcCaller caller;
	private Init init;
	private HDC hdc;
	private HTTPConnection http;

	
	private SecretKey secretKey;
	private SignalHandler signals;
	
	
	public EdgeTripImpl(LoggerCustom logger, EdgeTrip edgeTrip) {
	    super();
	    
	    this.context = new Context(logger);
	    this.hdc = new HDC(context);
	    this.http = new HTTPConnection(context);
	    this.apptimer = new Timer(true);
	    this.signals = new SignalHandler(context);
	    this.trip = new Trip(context, hdc, signals);
	    this.calc = new Calc(context, trip, hdc, signals);
	    this.caller = new CalcCaller(context, calc);
	    this.init = new Init(context);
	    //this.context.setOutputFile(newFile);
	    this.secretKey = generateKey("MySecretKeyForAES123");
	    this.edgeTrip = edgeTrip;
	    context.setEdgeTrip(edgeTrip);
	}
	
	
	// Example method to generate a SecretKey from a string (for demonstration)
		private SecretKey generateKey(String key) {
		    byte[] keyBytes = key.getBytes();
		    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
		}
		
		// Decrypt data
		private String decrypt(String encryptedData) throws Exception {
		    Cipher cipher = Cipher.getInstance("AES");
		    cipher.init(Cipher.DECRYPT_MODE, secretKey);
		    byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
		    return new String(decryptedBytes, "UTF-8");
		}
	
	public void startProcess() throws IOException {

	    init.callInit();
	    //caller.callSetParam();
		
	    startAppTimer();
	    
	}
	

	public void CANDataFrameUpdated(CANDataFrame arg0) throws IOException {
		
		context.getLogger().printLog(Logger.LOG_INFO, "CANDataFrameUpdated " + arg0.getId());
		
		
		//processNewCANDataFrame(arg0);
//		storeCANDataFrame(arg0);
	}
	
	private void startAppTimer() {
	    apptimertask = new TimerTask()
	    {
	        @Override
	        public void run() {
	        	
	        	try {
					caller.callCalc();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    };
	    apptimer.scheduleAtFixedRate(apptimertask, 0, 100);
	}
	
	
	public boolean callFinalise() throws IOException {
	    boolean ret = false;
	    this.finalise();
	    ret = true;
	
	    return ret;
	}
	
	private void finalise() throws IOException {
	    context.getLogger().printLog(Logger.LOG_INFO, "EdgeTrip.finalise called");
	}

	
	// Example usage of the decryption method
		public void exampleDecryptionUsage() throws IOException {
		    try {
		        String encryptedData = "EncryptedDataString"; // Replace with actual encrypted data
		        String decryptedData = decrypt(encryptedData);
		        context.getLogger().printLog(LoggerCustom.LOG_INFO, "Decrypted Data: " + decryptedData);
		    } catch (Exception e) {
		        context.getLogger().printLog(LoggerCustom.LOG_ERROR, "Decryption failed: " + e.getMessage());
		    }
		}
}


