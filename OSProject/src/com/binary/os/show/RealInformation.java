package com.binary.os.show;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.binary.os.device.DeviceGlobalVar;
import com.binary.os.kernel.Clock;
import com.binary.os.kernel.GlobalStaticVar;
import com.binary.os.kernel.PCBManager;
import com.binary.os.mem.SystemMem;
import com.binary.os.mem.UserMem;

public class RealInformation {
	
	public static int[][] getBitMap(){
		return SystemMem.bitmap;
	}
	
	public static int getCommandNow(){
		try{
			int no = UserMem.users[GlobalStaticVar.ProcessStartNo * 16 + GlobalStaticVar.ProcessPagePeek - 1];
			return no;
		}catch(Exception e){
			return -1;
		}
	}
	
	public static int[][] getDeviceUsed(){
		return DeviceGlobalVar.devCurrPid;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedList<Integer> getWaitDeviceA(){
		return DeviceGlobalVar.devAWaitList;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedList<Integer> getWaitDeviceB(){
		return DeviceGlobalVar.devBWaitList;
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedList<Integer> getWaitDeviceC(){
		return DeviceGlobalVar.devCWaitList;
	}
	
	public static long getSystemClock(){
		return Clock.ABSOLUTECLOCK;
	}
	
	public static String getIR(){
		return GlobalStaticVar.IRTEMP;
	}
	
	public static int getResult(){
		return GlobalStaticVar.Result;
	}
	
	public static long getSystemRelativeClock(){
		return Clock.RELATIVECLOCK;
	}
	
	public static int getProcess(){
		return GlobalStaticVar.PID_NOW;
	}
	
	public static List<Integer> getReadyProcesses(){
		List<Integer> list = new ArrayList<Integer>();
		if(!PCBManager.readyQueue.isEmpty()){
			for(int i = 0; i < PCBManager.readyQueue.size(); i++){
				int pid = PCBManager.readyQueue.get(i);
				list.add(pid);
			}
		}
		return list;
	}
	
	public static List<Integer> getBlockProcesses(){
		List<Integer> list = new ArrayList<Integer>();
		if(!PCBManager.blockQueue.isEmpty()){
			for(int i = 0; i < PCBManager.blockQueue.size(); i++){
				int pid = PCBManager.blockQueue.get(i);
				list.add(pid);
			}
		}
		return list;
	}
}
