package com.binary.os.device;


public class Device //设备进程控制类
{
	@SuppressWarnings("unchecked")
	public static void addListItem(int deviceType, int pid,int time){
		DeviceGlobalVar.devWaitList[deviceType].addLast(pid);
		DeviceGlobalVar.processIOTime[deviceType].addLast(time);
    }
    
    public static int delListItem(int deviceType){
    	return (Integer) DeviceGlobalVar.devWaitList[deviceType].remove();
    }
    
    public static int delProcessTime(int deviceType){
    	return (Integer) DeviceGlobalVar.processIOTime[deviceType].remove();
    }
    
	public static boolean allocDevToProcess(int deviceType, int pid, int time){
		for(int i=0; i < DeviceGlobalVar.totalDev[deviceType] && DeviceGlobalVar.devCurrPid[deviceType][i] == -1; i++){
			DeviceGlobalVar.devCurrPid[deviceType][i] = pid;
			DeviceGlobalVar.ABCTime[deviceType][i] = time;
			return true;
		}
		return false;
	}
	
	public static void clearDevice(){
		for(int i =0 ; i < 3; i++){
			DeviceGlobalVar.devWaitList[i].clear();
			DeviceGlobalVar.processIOTime[i].clear();
		}
			
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < DeviceGlobalVar.devCurrPid[i].length; j++){
				DeviceGlobalVar.devCurrPid[i][j] = -1;
				DeviceGlobalVar.ABCTime[i][j] = -1;
			}
	}
}
