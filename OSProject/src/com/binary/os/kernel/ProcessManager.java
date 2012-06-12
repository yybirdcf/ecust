package com.binary.os.kernel;

import com.binary.os.mem.MemGlobalVar;
import com.binary.os.mem.SystemMem;

public class ProcessManager {
	
	private static Process[] processes = new Process[10];
 	
	public static void Schedule(){
		//进程调度函数
		//保存正在运行程序到进程控制块
		Process p = processes[GlobalStaticVar.PID_NOW];
		p.setMemInfo2(MemGlobalVar.PagePeek);
		p.setStatus(GlobalStaticVar.PCB_READY);
		p.setResult(GlobalStaticVar.Result);
		byte[] data;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < GlobalStaticVar.DR.size(); i++){
			sb.append(GlobalStaticVar.DR.poll());
		}
		String s = sb.toString();
		if(s != null && !s.equals("")){
			data = s.getBytes();
			p.saveDataBuffer(data);
		}
		PCBManager.addToReady(GlobalStaticVar.PID_NOW);
		GlobalStaticVar.PID_NOW = -1;
		//就绪队列选择一个进程
		if(!PCBManager.readyQueue.isEmpty()){
			Process ps = processes[PCBManager.removeFromReady()];
			//恢复这个进程的寄存器内容到各个寄存器
			GlobalStaticVar.PID_NOW = ps.getPID();
			GlobalStaticVar.Result = ps.getResult();
			MemGlobalVar.StartNo = ps.getStartNo();
			MemGlobalVar.OffSet = ps.getBlockNum();
			MemGlobalVar.PagePeek = ps.getPageOffset();
			byte[] datas = ps.restoreDataBuffer();
			if(datas.length > 0){
				for(int i = 0; i < datas.length / 4; i++){
					byte[] temp = new byte[4];
					for(int j = 0; j < 4; j++){
						temp[j] = datas[i * 4 + j];
					}
					GlobalStaticVar.DR.add(new String(temp));
				}
			}
		}
	}

	public static int Create(byte[] data){
		//进程创建
		//申请主存空间,若申请成功，转入主存
		//初始化进程控制块
		if(GlobalStaticVar.PID_NUM <= 10){
			//载入数据到内存
			//预先检查
			if(Compile.checkCommand(data)){
				//载入内存
				if(SystemMem.dispatch(data)){
					//分配成功
					Process p = new Process();
					//绑定内存与进程
					p.setMemInfo(MemGlobalVar.StartNo, MemGlobalVar.OffSet, (byte) 0);
					//就绪队列
					p.setStatus(GlobalStaticVar.PCB_READY);
					PCBManager.addToReady(p.getPID());
					processes[p.getPID()] = p;
					return p.getPID();
				}
				return -1;//内存空间不足
			}
			return -2;//检测语法
		}
		return -3;//进程数量最大
	}
	
	public static void Destory(){
		//进程销毁
		for(int i = 0; i < 32; i++){
			SystemMem.pcbs[GlobalStaticVar.PID_NOW * 32 + i] = 0;
		}
		GlobalStaticVar.PID_NUM--;
		GlobalStaticVar.PIDS[GlobalStaticVar.PID_NOW] = 0;
		GlobalStaticVar.PID_NOW = -1;
		GlobalStaticVar.PC = 0;
		for(int i =0; i < 3; i++){
			GlobalStaticVar.IR = null;
		}
	}
	
	public static void Block(){
		//进程阻塞
		Process p = processes[GlobalStaticVar.PID_NOW];
		//保存cpu现场
		p.setMemInfo2(MemGlobalVar.PagePeek);
		p.setResult(GlobalStaticVar.Result);
		byte[] data;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < GlobalStaticVar.DR.size(); i++){
			sb.append(GlobalStaticVar.DR.poll());
		}
		String s = sb.toString();
		if(s != null && !s.equals("")){
			data = s.getBytes();
			p.saveDataBuffer(data);
		}
		p.setStatus(GlobalStaticVar.PCB_BLOCK);
		PCBManager.addToBlock(p.getPID());
	}
	
	public static void Wakeup(int pid){
		//进程唤醒
		if(PCBManager.blockQueue.size() > 0){
			PCBManager.removeFromBlock(pid);
			Process p = processes[pid];
			p.setPSW((byte) 0);
			p.setStatus(GlobalStaticVar.PCB_READY);
			PCBManager.addToReady(p.getPID());
		}
	}
	
	public static void Display(){
		//显示
		
	}
}
