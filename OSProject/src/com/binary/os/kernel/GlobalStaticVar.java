package com.binary.os.kernel;

import java.util.LinkedList;
import java.util.Queue;

public class GlobalStaticVar {

	public static byte PSW = 0;//1:有中断, 0无中断
	public static String IR = null;//指令寄存器，存一条将要执行的指令
	public static int PC = 0;//程序计数器
	public static Queue<String> DR = new LinkedList<String>();//数据缓冲寄存器   		4*4 
	
	public static byte[] PIDS= {0,0,0,0,0,0,0,0,0,0};
	public static byte PID_NUM = 0;
	public static byte PID_NOW = -1;
	
	public static byte PCB_EMPTY = 0;
	public static byte PCB_READY = 1;
	public static byte PCB_BLOCK = 2;
	
	public static byte Result = 0;
}
