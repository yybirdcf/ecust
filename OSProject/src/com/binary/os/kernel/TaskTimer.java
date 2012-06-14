package com.binary.os.kernel;

import java.util.TimerTask;

import com.binary.os.device.DeviceGlobalVar;
import com.binary.os.device.IOControl;
import com.binary.os.show.UpdateInformation;

public class TaskTimer extends TimerTask {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Clock.ABSOLUTECLOCK++;
		
		if(GlobalStaticVar.createApply.size() > 0)
			ProcessManager.newProcess((byte[]) GlobalStaticVar.createApply.remove());
		
		if(Clock.RELATIVECLOCK <= 0){
			//进程调度
			Clock.RELATIVECLOCK = Clock.CLOCKPERIOD * 5;
			GlobalStaticVar.PSW = 1;
		}
		//CPU执行计算任务
		if(GlobalStaticVar.PID_NOW == -1){
			if(!PCBManager.readyQueue.isEmpty()){
				ProcessManager.Schedule();
				Clock.RELATIVECLOCK = Clock.CLOCKPERIOD * 5;
				Kernel.CPU();
				Clock.RELATIVECLOCK -= Clock.CLOCKPERIOD;
			}
		}else{
			Kernel.CPU();
			Clock.RELATIVECLOCK -= Clock.CLOCKPERIOD;
		}
		
		if(DeviceGlobalVar.NUMOFAPPLYDEVICE > 0)
			IOControl.IORun();
		
		//更新面板
		UpdateInformation.update();
	}

}
