package com.binary.os.device;

import java.util.LinkedList;

public class DeviceGlobalVar {
	public static final int devA = 0; //A设备类型号为0,数量为3
	public static final int devB = 1; //B设备类型号为0,数量为2
	public static final int devC = 2; //C设备类型号为0,数量为1
	public static final int totalDev[] = {3, 2, 1};
	public static boolean devAvail[][] = {{true,true,true}, {true,true}, {true}};
	public static LinkedList devWaitList[] = new LinkedList[3];
	public static int devCurrPid[][] = {{-1,-1,-1}, {-1,-1}, {-1}};
}
