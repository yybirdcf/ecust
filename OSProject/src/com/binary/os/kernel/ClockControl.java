package com.binary.os.kernel;

import java.util.Timer;

public class ClockControl {

	private static Timer timer;
	
	public static void SystemStart(){
		timer = new Timer();
		timer.schedule(new TaskTimer(), 0, Clock.CLOCKPERIOD * 1000);
	}
	
	public static void SystemStop(){
		timer.cancel();
		//清空相关资源
		
	}
}
