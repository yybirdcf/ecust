package com.binary.os.show;

import com.binary.os.kernel.GlobalStaticVar;

public class UpdateInformation {

	public static void update(){
		
		updateClock();
		updateMem();
		updateProcess();
		updateDevice();
	}
	
	private static void updateMem(){
		GlobalStaticVar.mf.memPanel.bitMap = RealInformation.getBitMap();
		GlobalStaticVar.mf.memPanel.bit = RealInformation.getCommandNow();
		GlobalStaticVar.mf.memPanel.repaint();
	}
	
	private static void updateClock(){
		GlobalStaticVar.mf.sysClockLabel.setText(RealInformation.getSystemClock()+"");
	}
	
	private static void updateDevice(){
		
	}
	
	private static void updateProcess(){
		GlobalStaticVar.mf.procNameLabel.setText(RealInformation.getProcess()+"");
		GlobalStaticVar.mf.currInstructLabel.setText(RealInformation.getIR());
		GlobalStaticVar.mf.tempResultLabel.setText(RealInformation.getResult()+"");
		GlobalStaticVar.mf.remainTLabel.setText(RealInformation.getSystemRelativeClock()+"");
	}
}
