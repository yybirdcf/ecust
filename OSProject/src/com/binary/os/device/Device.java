package com.binary.os.device;

import java.util.LinkedList;

class deviceProcessControl //设备进程控制类
{
	public static void addListItem(int deviceType, String process){
		DeviceGlobalVar.devWaitList[deviceType].addLast(process);
    }
    
    public static void delListItem(int deviceType, String process){
    	DeviceGlobalVar.devWaitList[deviceType].remove(process);
    }
    
	public static boolean allocDevToProcess(int deviceType, String process){
		int i;
		for(i=0; i < DeviceGlobalVar.totalDev[deviceType] && !DeviceGlobalVar.devAvail[deviceType][i]; i++);
		if(i == DeviceGlobalVar.totalDev[deviceType])
			return false;
		DeviceGlobalVar.devCurrProc[deviceType][i] = process;
		DeviceGlobalVar.devAvail[deviceType][i] = false;
		return true;
	}
	
	public static void reclaimDev(int deviceType, int deviceId){   //回收设备后，要注意唤醒等待设备的进程。
		DeviceGlobalVar.devAvail[deviceType][deviceId] = true;
		DeviceGlobalVar.devCurrProc[deviceType][deviceId] = null;
	}
}
