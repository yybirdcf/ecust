package com.binary.os.kernel;

import com.binary.os.device.IOControl;

public class Kernel {

	public static void CPU(){

		if (GlobalStaticVar.PSW == 1) {
			// 跳到中断处理
			Interrupt();
			GlobalStaticVar.PSW = 0;
		}

		if (GlobalStaticVar.DR.isEmpty()) {
			Register.loadDataToDataBuffer();
		}
		
		if(GlobalStaticVar.IR == null){
			Register.loadDataToIR();
		}
			
		GlobalStaticVar.IR = GlobalStaticVar.DR.poll();
		Command(GlobalStaticVar.IR);
		
		GlobalStaticVar.IR = null;
	}
	
	private static void Interrupt(){
		//中断处理函数
		ProcessManager.Schedule();
	}
	
	private static void Command(String IR){
		
		GlobalStaticVar.PC++;
		
		//解释执行指令
		if(IR.equals("end")){
			//进程结束
			//输出结果
			GlobalStaticVar.fm.saveOut(GlobalStaticVar.PID_NOW, GlobalStaticVar.Result);
			//清楚进程资源
			ProcessManager.Destory();
		}else if(IR.charAt(0) == '!'){
			//申请IO设备
			int time = Integer.parseInt(IR.charAt(2)+"");
			int type = -1;
			if(IR.charAt(1) == 'A'){
				//申请设备A
				type = 0;
			}else if(IR.charAt(1) == 'B'){
				//申请设备B
				type = 1;
			}else if(IR.charAt(1) == 'C'){
				//申请设备C
				type = 2;
			}
			
			IOControl.ApplyIO(type, GlobalStaticVar.PID_NOW, time);
			ProcessManager.Block();
			
		}else if(IR.charAt(1) == '='){
		
			GlobalStaticVar.Result = (byte)(Integer.parseInt(IR.charAt(2)+""));
		}else if(IR.charAt(1) == '+'){
			
			GlobalStaticVar.Result = GlobalStaticVar.Result++;
		}else if(IR.charAt(1) == '-'){
			
			GlobalStaticVar.Result = GlobalStaticVar.Result--;
		}
	}
}
