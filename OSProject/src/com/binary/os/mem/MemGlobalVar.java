package com.binary.os.mem;

public class MemGlobalVar {

	public static int BitMap_X = 8;
	public static int BitMap_y = 4;
	
	public static int idleBlockNum = 32;
	
	public static int UnitBlock = 16;
	
	public static int LimitBlock = 16;
	
	public static byte MemStartNo = -1;//页表块号
	public static byte MemOffSet = -1;//占用内存块数
	
	public static void ResetMemStaticVar(){
		MemStartNo = -1;
		MemOffSet = -1;
	}
}
