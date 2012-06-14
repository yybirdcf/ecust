package com.binary.os.show;

import com.binary.os.kernel.GlobalStaticVar;

public class UpdateInformation {

	public static void update(){
		
		updateClock();
		updateMem();
		updateProcess();
		updateProcessQueue();
		updateDevice();
		updateDeviceQueue();
	}
	
	private static void updateDeviceQueue() {
		// TODO Auto-generated method stub
		if(RealInformation.getWaitDeviceA().size() > 0){
			String str = "";
			for(int i = 0; i < RealInformation.getWaitDeviceA().size(); i++)
				str += "---"+RealInformation.getWaitDeviceA().get(i);
			GlobalStaticVar.mf.devAListLabel.setText(str);
		}else{
			GlobalStaticVar.mf.devAListLabel.setText("");
		}
		if(RealInformation.getWaitDeviceB().size() > 0){
			String str = "";
			for(int i = 0; i < RealInformation.getWaitDeviceB().size(); i++)
				str += "---"+RealInformation.getWaitDeviceB().get(i);
			GlobalStaticVar.mf.devBListLabel.setText(str);
		}else{
			GlobalStaticVar.mf.devBListLabel.setText("");
		}
		if(RealInformation.getWaitDeviceC().size() > 0){
			String str = "";
			for(int i = 0; i < RealInformation.getWaitDeviceC().size(); i++)
				str += "---"+RealInformation.getWaitDeviceC().get(i);
			GlobalStaticVar.mf.devCListLabel.setText(str);
		}else{
			GlobalStaticVar.mf.devCListLabel.setText("");
		}
	}

	private static void updateProcessQueue() {
		// TODO Auto-generated method stub
		if(RealInformation.getReadyProcesses().size() > 0){
			String str = "";
			for(int i = 0; i < RealInformation.getReadyProcesses().size(); i++)
				str += "---"+RealInformation.getReadyProcesses().get(i);
			GlobalStaticVar.mf.waitListLabel.setText(str);
		}else{
			GlobalStaticVar.mf.waitListLabel.setText("");
		}
		if(RealInformation.getBlockProcesses().size() > 0){
			String str = "";
			for(int i = 0; i < RealInformation.getBlockProcesses().size(); i++)
				str += "---"+RealInformation.getBlockProcesses().get(i);
			GlobalStaticVar.mf.blockListLabel.setText(str);
		}else{
			GlobalStaticVar.mf.blockListLabel.setText("");
		}
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
