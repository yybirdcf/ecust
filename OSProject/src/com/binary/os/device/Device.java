package com.binary.os.device;


class deviceProcessControl //设备进程控制类
{
	public static void addListItem(int deviceType, int pid){
		DeviceGlobalVar.devWaitList[deviceType].addLast(pid);
    }
    
    public static int delListItem(int deviceType){
    	return (Integer) DeviceGlobalVar.devWaitList[deviceType].remove();
    }
    
	public static boolean allocDevToProcess(int deviceType, int pid){
		int i;
		for(i=0; i < DeviceGlobalVar.totalDev[deviceType] && !DeviceGlobalVar.devAvail[deviceType][i]; i++);
		if(i == DeviceGlobalVar.totalDev[deviceType])
			return false;
		DeviceGlobalVar.devCurrPid[deviceType][i] = pid;
		DeviceGlobalVar.devAvail[deviceType][i] = false;
		return true;
	}
	
	public static void reclaimDev(int deviceType, int deviceId){   //回收设备后，要注意唤醒等待设备的进程。
		DeviceGlobalVar.devAvail[deviceType][deviceId] = true;
		DeviceGlobalVar.devCurrPid[deviceType][deviceId] = -1;
	}
}
