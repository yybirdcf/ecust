package com.binary.os.kernel;

import java.util.LinkedList;
import java.util.Queue;

public class GlobalStaticVar {

	public static int PSW = 0;//1:有中断, 0无中断
	public static String IR = null;//指令寄存器，存一条将要执行的指令
	public static int PC = 0;//程序计数器
	public static Queue<String> DR = new LinkedList<String>();//数据缓冲寄存器
	
	public static int PCB_IDENTIFY = 0;
	
	public static int PCB_EMPTY = 0;
	public static int PCB_READY = 1;
	public static int PCB_BLOCK = 2;
}
