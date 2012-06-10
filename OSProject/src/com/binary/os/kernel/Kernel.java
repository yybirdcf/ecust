package com.binary.os.kernel;

public class Kernel {

	public static void CPU(){
		
		while(true){
			
			if(GlobalStaticVar.PSW == 1){
				//跳到中断处理
				Interrupt();
				GlobalStaticVar.PSW = 0;
			}
			
			if(!GlobalStaticVar.DR.isEmpty()){
				
				GlobalStaticVar.IR = GlobalStaticVar.DR.poll();
				
				Command(GlobalStaticVar.IR);
			}
			
		}
		
	}
	
	public static void Interrupt(){
		//中断处理函数
		
	}
	
	public static void Command(String IR){
		//解释执行指令
		
	}
}
