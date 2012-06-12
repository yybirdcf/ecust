package com.binary.os.kernel;

import java.util.Timer;

public class ClockControl {

	public static void SystemStart(){
		Timer timer = new Timer();
		timer.schedule(new TaskTimer(), 0, Clock.CLOCKPERIOD * 1000);
	}
}
