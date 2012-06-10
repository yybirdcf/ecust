package com.binary.os.kernel;

public class Clock {

	public static long ABSOLUTECLOCK = 0;//绝对时种
	public static long RELATIVECLOCK = 0;//相对时钟
	
	public static long TIMESLICE = 50;//时间片  50ms
	public static long CLOCKPERIOD = 10;//时钟周期  10ms
}
