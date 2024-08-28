package edgetrip;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.automotive.logger.Logger;

import tme.edgetrip.protos.DrivingScore;

public class HDC {
	
	private Context context;
	
    private List<Double> scoreStartList = new ArrayList<>();
    private List<Double> scoreSteadyList = new ArrayList<>();
    private List<Double> scoreDecelList = new ArrayList<>();

    private List<Double> espoHvvLHoldStart = new ArrayList<>();
    private List<Double> espoHvvLHoldSteady = new ArrayList<>();
    private List<Double> espoHvvLHoldDecel = new ArrayList<>();

    private boolean twoSecondReached = false;
    private long lastUpdate;
    private long timeSteadyUpdate;
    
	private static final int B_FCSamplingTime = 300;
	private int B_FCSamplingCount = B_FCSamplingTime;
	double tripConsumption = 0;
	
	private double scoreSteady;
	private double scoreDecel;
	private double scoreStart;

	private double avgStart;
	private double avgSteady;
	private double avgDecel;
	private long globalScore;




	public HDC(Context context) {
    	this.context = context;
    }
	

	public void computeAvgHdc2() throws IOException{
	    if (!((scoreStartList.isEmpty()))) {
	    	
	    	avgStart = Math.round(scoreStartList.stream().mapToDouble(d -> d).average().orElse(0.0));
	        
	    } else {
	    	avgStart = 0;
	        context.getLogger().printLog(Logger.LOG_INFO, "    x No eco start score for trip");
	    }
	  
	    if (!((scoreSteadyList.isEmpty()))) {
	    	avgSteady = Math.round(scoreSteadyList.stream().mapToDouble(d -> d).average().orElse(0.0));
	    } else {
	    	avgSteady = 0;
	        context.getLogger().printLog(Logger.LOG_INFO, "    x No eco steady score for trip");
	    }
	    
	    
	    if (!((scoreDecelList.isEmpty()))) {
	    	avgDecel = Math.round(scoreDecelList.stream().mapToDouble(d -> d).average().orElse(0.0));
	    } else {
	    	avgDecel = 0;
	        context.getLogger().printLog(Logger.LOG_INFO, "    x No eco decel score for trip");
	    }
	    
	    this.globalScore = Math.round((avgStart + this.avgSteady + this.avgDecel)) / 3;
	    
	    
	    //context.setDriving_score_buf(DrivingScore.newBuilder().setEcoDecelScore(context.getAvg_decel()).setEcoStartScore(context.getAvg_start()).setEcoSteadyScore(context.getAvg_steady()).build());
	   
	    //context.getEdge_mes_buf().setDrivingScore(context.getDriving_score_buf());
	}
	
	
	public void computeHDCScores(double espo, double espoPrev, double hvvl, long currentTime, double prevSpeed, double hvvlPrev) throws IOException{	
	    if ((espo == espoPrev) && ((espo != 7) && (espo != 0))) {
	        if ((espo == 1)) {
	        	espoHvvLHoldStart.add(hvvl);
	        } else if ((espo == 2)) {
	        	espoHvvLHoldSteady.add(hvvl);
	            if ((currentTime - timeSteadyUpdate >= 2)) {
	            	scoreSteady = espoHvvLHoldSteady.stream().mapToDouble(d -> d).average().orElse(0.0); 
	            	scoreSteadyList.add(scoreSteady);
	            	timeSteadyUpdate = currentTime;
	            }
	        } else if ((espo == 4)) {
	        	espoHvvLHoldDecel.add(hvvl);
	        }
	    } else if ((espoPrev == 4) && (espo == 0)) {
	        this.scoreDecel = hvvlPrev ;
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "Score eco decel: " + context.getScore_decel() + " at time:" + context.getCurrent_time());
	        this.scoreDecelList.add(this.scoreDecel);
	    } else if ((espoPrev == 1) && ((espo != 1) && (prevSpeed >= 20))) {
	        this.scoreStart = espoHvvLHoldStart.stream().mapToDouble(d -> d).average().orElse(0.0);
	        context.getLogger().printLog(Logger.LOG_INFO, "Score eco start: " + this.scoreStart + " at time:" + currentTime);
	        scoreStartList.add(this.scoreStart);
	    } else if ((espoPrev == 0) && (espo == 1)) {
	    	espoHvvLHoldStart.clear();
	        this.espoHvvLHoldSteady.clear();
	        this.espoHvvLHoldDecel.clear();
	    }
}
	
	public double getAvgStart() {
		return avgStart;
	}


	public void setAvgStart(double avgStart) {
		this.avgStart = avgStart;
	}


	public double getAvgSteady() {
		return avgSteady;
	}


	public void setAvgSteady(double avgSteady) {
		this.avgSteady = avgSteady;
	}


	public double getAvgDecel() {
		return avgDecel;
	}


	public void setAvgDecel(double avgDecel) {
		this.avgDecel = avgDecel;
	}


	public long getGlobalScore() {
		return globalScore;
	}


	public void setGlobalScore(long globalScore) {
		this.globalScore = globalScore;
	}


	// Describe this function...
	public void computeAvgHdc(){
//	    if (!((context.getScore_start_list().isEmpty()))) {
//	    	
//	    	context.setAvg_start(Math.round(context.getScore_start_list().stream().mapToDouble(d -> d).average().orElse(0.0)));
//	        
//	    } else {
//	    	context.setAvg_start(0);
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "    x No eco start score for trip");
//	    }
//	  
//	    if (!((context.getScore_steady_list().isEmpty()))) {
//	        context.setAvg_steady(Math.round(context.getScore_steady_list().stream().mapToDouble(d -> d).average().orElse(0.0)));
//	    } else {
//	        context.setAvg_steady(0);
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "    x No eco steady score for trip");
//	    }
//	    
//	    
//	    if (!((context.getScore_decel_list().isEmpty()))) {
//	        context.setAvg_decel(Math.round(context.getScore_decel_list().stream().mapToDouble(d -> d).average().orElse(0.0)));
//	    } else {
//	        context.setAvg_decel(0);
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "    x No eco decel score for trip");
//	    }
//	    
//	    context.setAvg_tot(Math.round((context.getAvg_start() + (context.getAvg_steady() + context.getAvg_decel())) / 3));
//	    
//	    
//	    context.setDriving_score_buf(DrivingScore.newBuilder().setEcoDecelScore(context.getAvg_decel()).setEcoStartScore(context.getAvg_start()).setEcoSteadyScore(context.getAvg_steady()).build());
//	   
//	    context.getEdge_mes_buf().setDrivingScore(context.getDriving_score_buf());
//	}
//	
//	
//	public void computeHDCScores(double current_time){	
//	    if ((context.getEspohvid() == context.getEspohvid_prev()) && ((context.getEspohvid() != 7) && (context.getEspohvid() != 0))) {
//	        if ((context.getEspohvid() == 1)) {
//	            context.getEspohvvl_hold_start().add(context.getEspohvvl());
//	        } else if ((context.getEspohvid() == 2)) {
//	        	
//	            context.getEspohvvl_hold_steady().add(context.getEspohvvl());
//	            if ((context.getCurrent_time() - context.getTime_steady_update() >= 2)) {
//	                context.setScore_steady(context.getEspohvvl_hold_steady().stream().mapToDouble(d -> d).average().orElse(0.0)); 
//	                context.getScore_steady_list().add(context.getScore_steady());
//	                context.setTime_steady_update(context.getCurrent_time());
//	            }
//	        } else if ((context.getEspohvid() == 4)) {
//	            context.getEspohvvl_hold_decel().add(context.getEspohvvl());
//	        }
//	    } else if ((context.getEspohvid_prev() == 4) && (context.getEspohvid() == 0)) {
//	        context.setScore_decel(context.getEspohvvl_prev()) ;
////	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "Score eco decel: " + context.getScore_decel() + " at time:" + context.getCurrent_time());
//	        context.getScore_decel_list().add(context.getScore_decel());
//	    } else if ((context.getEspohvid_prev() == 1) && ((context.getEspohvid() != 1) && (context.getSp1_prev() >= 20))) {
//	        context.setScore_start(context.getEspohvvl_hold_start().stream().mapToDouble(d -> d).average().orElse(0.0));
//	        context.getMylogger().printLog(LoggerClass.LOG_INFO, "Score eco start: " + context.getScore_start() + " at time:" + context.getCurrent_time());
//	        context.getScore_start_list().add(context.getScore_start());
//	    } else if ((context.getEspohvid_prev() == 0) && (context.getEspohvid() == 1)) {
//	        context.getEspohvvl_hold_start().clear();
//	        context.getEspohvvl_hold_steady().clear();
//	        context.getEspohvvl_hold_decel().clear();
//	    }
}

}
