package com.binary.os.kernel;

import java.util.LinkedList;

import com.binary.os.filesys.manager.FileManager;
import com.binary.os.views.MainFrame;

public class GlobalStaticVar {

	public static byte PSW = 0;//1:有中断, 0无中断
	public static String IR = null;//指令寄存器，存一条将要执行的指令
	public static byte PC = 0;//程序计数器
	public static LinkedList<String> DR = new LinkedList<String>();//数据缓冲寄存器   		4*4 
	
	public static byte[] PIDS= {0,0,0,0,0,0,0,0,0,0};
	public static byte PID_NUM = 0;
	public static byte PID_NOW = -1;
	
	public static byte PCB_EMPTY = 0;
	public static byte PCB_READY = 1;
	public static byte PCB_BLOCK = 2;
	
	public static byte Result = 0;
	
	public static FileManager fm = null;
	
	public static byte ProcessStartNo = -1;//页表块号
	public static byte ProcessOffSet = -1;//占用内存块数
	public static byte ProcessPagePeek = -1;//页表游标
	
	public static void ResetGlobalStaticVar(){
		ProcessStartNo = -1;
		ProcessOffSet = -1;
		ProcessPagePeek = -1;
	}
	
	public static int ProcessCreateListener = -4;
	@SuppressWarnings("rawtypes")
	public static LinkedList createApply = new LinkedList();
	
	public static MainFrame mf = null;
	
	public static String IRTEMP = null;
}
