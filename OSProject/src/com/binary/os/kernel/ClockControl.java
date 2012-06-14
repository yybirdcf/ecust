package com.binary.os.kernel;

import java.util.Timer;

import com.binary.os.device.Device;
import com.binary.os.filesys.manager.FileManager;
import com.binary.os.mem.SystemMem;
import com.binary.os.mem.UserMem;
import com.binary.os.views.MainFrame;

public class ClockControl {

	private static Timer timer;
	
	public static void SystemStart(){
		//初始化系统资源
		
		SystemMem.init();
		UserMem.init();
		
		timer = new Timer();
		timer.schedule(new TaskTimer(), 0, Clock.CLOCKPERIOD * 1000);
	}
	
	public static void SystemStop(){
		timer.cancel();
		//清空相关资源
		
		Device.clearDevice();
		GlobalStaticVar.fm = null;
		GlobalStaticVar.mf = null;
	}
}
