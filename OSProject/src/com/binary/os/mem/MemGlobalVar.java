package com.binary.os.mem;

public class MemGlobalVar {

	public static int BitMap_X = 8;
	public static int BitMap_y = 4;
	
	public static int idleBlockNum = 0;
	
	public static int UnitBlock = 16;
	
	public static int LimitBlock = 16;
	
	public static byte StartNo = 0;//页表块号
	public static byte OffSet = 0;//占用内存块数
	public static byte PagePeek = 0;//页表游标
}
