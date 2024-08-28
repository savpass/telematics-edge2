/**
 * 
 */
package edgetrip;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import com.ibm.automotive.ScreenConfiguration;
import com.ibm.automotive.UIVehiclet;
import com.ibm.automotive.uitoolkit.*;

import utils.LoggerCustom;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.time.LocalDateTime;
import java.security.Permission;
import com.ibm.automotive.logger.Logger;


/**
 * @author root
 *
 */
public class EdgeTrip extends UIVehiclet {
	
	
	private LoggerCustom logger;
	private VRootPane rp;
	private EdgeTripImpl service;
	
	public EdgeTrip() throws IOException {
		
		
		File file = getDataStore();
		
		File newFile = null;
		
		try {
            // Create the new file
	    	
	    	newFile = new File(file, "/output" + LocalDateTime.now());
	    	
            boolean created = newFile.createNewFile();
            
            logger = new LoggerCustom(newFile);
        
        }  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e) {
            // Handle SecurityException which can be caused by lack of write permissions
			logger.printLog(Logger.LOG_INFO, "Permission denied: " + e.getMessage());
           try {
			logger.printLog(Logger.LOG_INFO, "Permission denied: " + e.getMessage());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
            e.printStackTrace();
        }
		
		 
	   
	}

	@Override
	public void onFocusGained() {
		// TODO Auto-generated method stub
		
			try {
				logger.printLog(LoggerCustom.LOG_DEBUG,  "EdgeTrip.onFocusGained called");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	@Override
	public void onFocusLost() {
		// TODO Auto-generated method stub
		try {
			logger.printLog(LoggerCustom.LOG_DEBUG, "EdgeTrip.onFocusLost called");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onHide() {
		// TODO Auto-generated method stub
		//mylogger.printLog(LoggerClass.LOG_INFO, "EdgeTrip.onHide called");
	}

	@Override
	public void onInterruptEnd() {
		// TODO Auto-generated method stub
		//mylogger.printLog(LoggerClass.LOG_INFO, "EdgeTrip.onInterruptEnd called");

	}

	@Override
	public void onShow(ScreenConfiguration arg0, int arg1) {
		//mylogger.printLog(LoggerClass.LOG_INFO, "EdgeTrip.onShow called");

	}

	@Override
	public void onCreate(int arg0)  {
		try {
			logger.printLog(LoggerCustom.LOG_DEBUG, "EdgeTrip.onCreate called");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		service = new EdgeTripImpl(logger, this);
		
		
		
		updatePanelEdgeTrip("00", "P_PARK", "NA", "NA", "NA", "NA", "NA", "NA", "No trip meta data yet.\nMeta data will be reported when IG-ON --> IG-OFF is detected or\nif P_PARK is selected after driving.");
		
		try {
			service.startProcess();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//mylogger.printLog(LoggerClass.LOG_INFO, "ciao" + file.isDirectory());
		
		
		
	    
		
		
		
		//mylogger.printLog(LoggerClass.LOG_INFO, "ciao" + file.canWrite());
		

//	        if (file.exists()) {
//	            // Attempt to make the file writable
//	            boolean success = file.setWritable(true);
//	            if (success) {
//	                System.out.println("File permissions changed to writable.");
//	            } else {
//	                System.out.println("Failed to change file permissions.");
//	            }
//	        } else {
//	            System.out.println("File does not exist.");
//	        }
	        

		
		//mylogger.printLog(LoggerClass.LOG_INFO, file.getName());
		//setVRootPane(rp);
	}


	@Override
	public void onDestroy(int arg0) {
		//mylogger.printLog(LoggerClass.LOG_INFO, "EdgeTrip.onDestroy called");
//		stopService();
		//service.callFinalise();
	}
	
	
	public void updatePanelEdgeTrip(String speed, String gear, String latitude, String longitude, String roadtype, String gps_source, String soc_dsp, String http_code, String metaData) {
//		mylogger.printLog(Logger.LOG_WARNING, "EdgeTrip.updatePanelEdgeTrip called");

		VUserInterfaceRestrictionPolicy policy = VUserInterfaceRestrictionPolicy.RestrictNone;
		
		rp = VRootPane.createVRootPane();
		rp.setUserInterfaceRestrictionPolicy(policy);
		rp.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		VPanel panel = new VPanel();
		panel.setUserInterfaceRestrictionPolicy(policy);

	    panel.setLayout(new GridLayout(4, 2));
	    
	    VScrollPane sp = new VScrollPane();
	    sp.setUserInterfaceRestrictionPolicy(policy);
	    sp.setLayout(new FlowLayout(FlowLayout.RIGHT));
	    
        VTextArea textArea = new VTextArea();
//        textArea.setLineWrap(true);
        textArea.setUserInterfaceRestrictionPolicy(policy);
        textArea.setFont(new Font ("TimesRoman", Font.PLAIN , 16));
        textArea.setText(metaData);
        textArea.setEditable(false);
        sp.add(textArea);
        
        
        VHSplitPane vhp = new VHSplitPane(0.35f);
        vhp.setUserInterfaceRestrictionPolicy(policy);
        vhp.setPreferredSize(new Dimension(1000, 500));
        vhp.setLayout(new FlowLayout(FlowLayout.CENTER));

        Font p_font = new Font ("TimesRoman", Font.PLAIN , 18);
        
	    VTextField speed_text_field = new VTextField(speed);
	    speed_text_field.setUserInterfaceRestrictionPolicy(policy);
	    speed_text_field.setEditable(false);
	    speed_text_field.setFont(p_font);
	    
	    VTextField gear_text_field = new VTextField(gear);
	    gear_text_field.setUserInterfaceRestrictionPolicy(policy);
	    gear_text_field.setEditable(false);
	    gear_text_field.setFont(p_font);
	    
	    VTextField latitude_text_field = new VTextField(latitude);
	    latitude_text_field.setUserInterfaceRestrictionPolicy(policy);
	    latitude_text_field.setEditable(false);
	    latitude_text_field.setFont(p_font);
	    
	    VTextField longitude_text_field = new VTextField(longitude);
	    longitude_text_field.setUserInterfaceRestrictionPolicy(policy);
	    longitude_text_field.setEditable(false);
	    longitude_text_field.setFont(p_font);
	    
	    VTextField roadtype_text_field = new VTextField(roadtype);
	    roadtype_text_field.setUserInterfaceRestrictionPolicy(policy);
	    roadtype_text_field.setEditable(false);
	    roadtype_text_field.setFont(p_font);
	    
	    VTextField gps_source_text_field = new VTextField(gps_source);
	    gps_source_text_field.setUserInterfaceRestrictionPolicy(policy);
	    gps_source_text_field.setEditable(false);
	    gps_source_text_field.setFont(p_font);
	    
	    VTextField soc_level_text_field = new VTextField(soc_dsp);
	    soc_level_text_field.setUserInterfaceRestrictionPolicy(policy);
	    soc_level_text_field.setEditable(false);
	    soc_level_text_field.setFont(p_font);
	    
	    VTextField http_code_text_field = new VTextField(http_code);
	    http_code_text_field.setUserInterfaceRestrictionPolicy(policy);
	    http_code_text_field.setEditable(false);
	    http_code_text_field.setFont(p_font);
	    
        VPanel p1 = new VPanel();
        p1.setUserInterfaceRestrictionPolicy(policy);
        p1.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p1_lab = new VLabel("Speed");
        p1_lab.setFont(p_font);
        p1_lab.setUserInterfaceRestrictionPolicy(policy);
        p1.add(p1_lab);
        p1.add(speed_text_field);
        panel.add(p1);
        
        VPanel p2 = new VPanel();
        p2.setUserInterfaceRestrictionPolicy(policy);
        p2.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p2_lab = new VLabel("Gear");
        p2_lab.setFont(p_font);
        p2_lab.setUserInterfaceRestrictionPolicy(policy);
        p2.add(p2_lab);
        p2.add(gear_text_field);
        panel.add(p2);
        
        VPanel p3 = new VPanel();
        p3.setUserInterfaceRestrictionPolicy(policy);
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p3_lab = new VLabel("Latitude");
        p3_lab.setFont(p_font);
        p3_lab.setUserInterfaceRestrictionPolicy(policy);
        p3.add(p3_lab);
        p3.add(latitude_text_field);
        panel.add(p3);
        
        VPanel p4 = new VPanel();
        p4.setUserInterfaceRestrictionPolicy(policy);
        p4.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p4_lab = new VLabel("Longitude");
        p4_lab.setFont(p_font);
        p4_lab.setUserInterfaceRestrictionPolicy(policy);
        p4.add(p4_lab);
        p4.add(longitude_text_field);
        panel.add(p4);
        
        VPanel p5 = new VPanel();
        p5.setUserInterfaceRestrictionPolicy(policy);
        p5.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p5_lab = new VLabel("Road type");
        p5_lab.setFont(p_font);
        p5_lab.setUserInterfaceRestrictionPolicy(policy);
        p5.add(p5_lab);
        p5.add(roadtype_text_field);
        panel.add(p5);
        
        VPanel p6 = new VPanel();
        p6.setUserInterfaceRestrictionPolicy(policy);
        p6.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p6_lab = new VLabel("GPS source");
        p6_lab.setFont(p_font);
        p6_lab.setUserInterfaceRestrictionPolicy(policy);
        p6.add(p6_lab);
        p6.add(gps_source_text_field);
        panel.add(p6);
        
        VPanel p7 = new VPanel();
        p7.setUserInterfaceRestrictionPolicy(policy);
        p7.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p7_lab = new VLabel("SOC level");
        p7_lab.setFont(p_font);
        p7_lab.setUserInterfaceRestrictionPolicy(policy);
        p7.add(p7_lab);
        p7.add(soc_level_text_field);
        panel.add(p7);
        
        VPanel p8 = new VPanel();
        p8.setUserInterfaceRestrictionPolicy(policy);
        p8.setLayout(new FlowLayout(FlowLayout.LEFT));
        VLabel p8_lab = new VLabel("Http code");
        p8_lab.setFont(p_font);
        p8_lab.setUserInterfaceRestrictionPolicy(policy);
        p8.add(p8_lab);
        p8.add(http_code_text_field);
        panel.add(p8);
        
        vhp.setLeftWidget(panel);
        vhp.setRightWidget(sp);
        
        rp.add(vhp);
        
        setVRootPane(rp);
	}

}
