package edgetrip;

import java.io.IOException;

import com.ibm.automotive.logger.Logger;

public class EV {

	private boolean ev_on = false;
	private int count_tot = 0;
	private int count_ev_on = 0;
	private double distanceTravelledEVON = 0;
	private double evUsage = 0;
	private double evTime = 0;
	private double run_LOGIC_CYCLE;
	private double chargeTime = 0;
	private int count_charge_on = 0;
	private double distanceTravelledChargeON = 0;
	private double ecoTime = 0;
	private int countEcoON = 0;
	private double distanceTravelledECOON = 0;
	private double powerTime = 0;
	private int countPowerON = 0;
	private double distanceTravelledPowerON = 0;
	
	public static final String hybridSystemModeEco = "Eco";
	public static final String hybridSystemModeCharge = "Charge";
	public static final String hybridSystemModePower = "Power";
	public static final String tractionTypeEV = "EV";
	public static final String tractionTypeFuel = "Fuel";
	
	
	private String currentHybridSystemMode;
	private String currentTractionType;
	private Context context;
	
	
	public EV(Context context) {
		this.context = context;
		this.run_LOGIC_CYCLE = context.getRUN_LOGIC_CYCLE();
	
	}

	public void checkEVActive(double rpm, double distanceCycle) {
		
		count_tot++;
		
		if (rpm <= 200) {
			ev_on = true;
			count_ev_on++;
			distanceTravelledEVON = distanceTravelledEVON + distanceCycle;
			currentTractionType = tractionTypeEV;
			
		} else if (rpm >= 400) {
			ev_on = false;
			currentTractionType = tractionTypeFuel;
			
		} else if ( rpm < 400 && rpm > 200) {
			
		}
	}

	public void computeEVUsage() throws IOException {
		evUsage = (count_ev_on * 100/count_tot) ;
		context.getLogger().printLog(Logger.LOG_INFO,  "EV usage: " + Double.toString(evUsage));
		context.getLogger().printLog(Logger.LOG_INFO,  "EV distance: " + Double.toString(this.distanceTravelledEVON));
	}

	public void computeEVTime() throws IOException {
		evTime = count_ev_on * run_LOGIC_CYCLE/1000;
		context.getLogger().printLog(Logger.LOG_INFO,  "EV time: " + Double.toString(evTime));
		
	}
	
	public void checkEVCharge(double iecohvcr, double distanceCycle) {
		if ( iecohvcr <= -1 && iecohvcr >= -100) {
			count_charge_on++;
			distanceTravelledChargeON = distanceTravelledChargeON + distanceCycle;
			currentHybridSystemMode = hybridSystemModeCharge;
			
		} else if (iecohvcr <= 100 && iecohvcr >= 0) {
			countEcoON++;
			distanceTravelledECOON = distanceTravelledECOON + distanceCycle;
			currentHybridSystemMode = hybridSystemModeEco;
			
		} else if (iecohvcr <= 512 && iecohvcr >= 101) {
			countPowerON++;
			distanceTravelledPowerON = distanceTravelledPowerON + distanceCycle;
			currentHybridSystemMode = hybridSystemModePower;
		}
	}

	public void computeChargeTime() throws IOException {
		chargeTime = count_charge_on * run_LOGIC_CYCLE / 1000;
		context.getLogger().printLog(Logger.LOG_INFO,  "Charge time: " + Double.toString(chargeTime));
		context.getLogger().printLog(Logger.LOG_INFO,  "Charge distance: " + Double.toString(this.distanceTravelledChargeON));
		
	}

	public void computeECOTime() throws IOException {
		ecoTime = countEcoON * run_LOGIC_CYCLE / 1000;
		context.getLogger().printLog(Logger.LOG_INFO,  "Eco time: " + Double.toString(ecoTime));
		context.getLogger().printLog(Logger.LOG_INFO,  "Eco distance: " + Double.toString(this.distanceTravelledECOON));
		
	}

	public void computePowerTime() throws IOException {
		powerTime = countPowerON * run_LOGIC_CYCLE / 1000;
		context.getLogger().printLog(Logger.LOG_INFO,  "Power time: " + Double.toString(powerTime));
		context.getLogger().printLog(Logger.LOG_INFO,  "Power distance: " + Double.toString(this.distanceTravelledPowerON));
		
	}

	public String getHybridSystemMode() {
		return currentHybridSystemMode;
	}

	public String getTractionType() {
		// TODO Auto-generated method stub
		return currentTractionType;
	}
	

}


