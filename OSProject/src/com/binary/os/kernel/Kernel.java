package com.binary.os.kernel;

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
		
		if(GlobalStaticVar.IR.isEmpty() || GlobalStaticVar.IR == null){
			Register.loadDataToIR();
		}
			
		GlobalStaticVar.IR = GlobalStaticVar.DR.poll();
		Command(new String(GlobalStaticVar.IR));
		
		GlobalStaticVar.IR = null;
	}
	
	public static void Interrupt(){
		//中断处理函数
		
	}
	
	public static void Command(String IR){
		//解释执行指令
		if(IR.equals("end")){
			//进程结束
			//输出结果
			
			//清楚进程资源
			GlobalStaticVar.PSW = -1;
		}else if(IR.charAt(0) == '!'){
			
			
		}else if(IR.charAt(1) == '='){
		
			GlobalStaticVar.Result = (byte) IR.charAt(2);
		}else if(IR.charAt(1) == '+'){
			
			GlobalStaticVar.Result = GlobalStaticVar.Result++;
		}else if(IR.charAt(1) == '-'){
			
			GlobalStaticVar.Result = GlobalStaticVar.Result--;
		}
	}
}
