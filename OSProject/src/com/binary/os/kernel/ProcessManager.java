package com.binary.os.kernel;

public class ProcessManager {
	
	public static void Schedule(){
		//进程调度函数
		
	}

	public static void Create(){
		//进程创建
		PCB p = new PCB();
		
		//申请主存空间,若申请成功，转入主存
		
		//初始化进程控制块
		p.setPid(GlobalStaticVar.PCB_IDENTIFY++);
		p.setStatus(GlobalStaticVar.PCB_EMPTY);
		
		//就绪队列
		PCBManager.addToReady(p);
	}
	
	public static void Destory(){
		//进程销毁
		
	}
	
	public static void Block(){
		//进程阻塞
		
	}
	
	public static void Wakeup(){
		//进程唤醒
		
	}
	
	public static void Display(){
		//显示
		
	}
}
