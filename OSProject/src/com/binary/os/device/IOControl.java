package com.binary.os.device;

import com.binary.os.kernel.Clock;
import com.binary.os.kernel.ProcessManager;

public class IOControl {

	public static void ApplyIO(int type,int pid,int time){
		DeviceGlobalVar.NUMOFAPPLYDEVICE++;
		if(!Device.allocDevToProcess(type, pid, time))
			Device.addListItem(type, pid, time);
	}
	
	private static void InnerApplyIO(int type,int pid,int time){
		if(!Device.allocDevToProcess(type, pid, time))
			Device.addListItem(type, pid, time);
	}
	
	public static void IORun(){
		IOSchedule();
	}
	
	private static void IOSchedule(){
		ScheduleA();
		ScheduleB();
		ScheduleC();
	}
	
	private static void ScheduleA(){
		for(int i = 0; i < 3; i++){
			if(DeviceGlobalVar.ABCTime[0][i] > 0){
				
				DeviceGlobalVar.ABCTime[0][i] -= Clock.CLOCKPERIOD;
			}
			if(DeviceGlobalVar.ABCTime[0][i] == 0){
				
				int pid = DeviceGlobalVar.devCurrPid[0][i];
				
				ProcessManager.Wakeup(pid);
				DeviceGlobalVar.NUMOFAPPLYDEVICE--;
				DeviceGlobalVar.ABCTime[0][i] = -1;
				DeviceGlobalVar.devCurrPid[0][i] = -1;
				if(DeviceGlobalVar.devAWaitList.size() > 0){
					int p = Device.delListItem(0);
					int time = Device.delProcessTime(0);
					InnerApplyIO(0, p, time);
				}
			}
		}
	}
	
	private static void ScheduleB(){
		for(int i = 0; i < 2; i++){
			if(DeviceGlobalVar.ABCTime[1][i] > 0){
				
				DeviceGlobalVar.ABCTime[1][i] -= Clock.CLOCKPERIOD;
			}
			if(DeviceGlobalVar.ABCTime[1][i] == 0){
				
				
				int pid = DeviceGlobalVar.devCurrPid[1][i];
				
				ProcessManager.Wakeup(pid);
				DeviceGlobalVar.NUMOFAPPLYDEVICE--;
				DeviceGlobalVar.ABCTime[1][i] = -1;
				DeviceGlobalVar.devCurrPid[1][i] = -1;
				if(DeviceGlobalVar.devBWaitList.size() > 0){
					int p = Device.delListItem(1);
					int time = Device.delProcessTime(1);
					InnerApplyIO(1, p, time);
				}
			}
		}
	}
	
	private static void ScheduleC(){
		if(DeviceGlobalVar.ABCTime[2][0] > 0){
			
			System.out.println(DeviceGlobalVar.ABCTime[2][0]);
			
			DeviceGlobalVar.ABCTime[2][0] -= Clock.CLOCKPERIOD;
		}
		if(DeviceGlobalVar.ABCTime[2][0] == 0){
			
			
			int pid = DeviceGlobalVar.devCurrPid[2][0];
			
			ProcessManager.Wakeup(pid);
			DeviceGlobalVar.NUMOFAPPLYDEVICE--;
			DeviceGlobalVar.ABCTime[2][0] = -1;
			DeviceGlobalVar.devCurrPid[2][0] = -1;
			if(DeviceGlobalVar.devCWaitList.size() > 0){
				int p = Device.delListItem(2);
				int time = Device.delProcessTime(2);
				InnerApplyIO(2, p, time);
			}
		}
	}
}
