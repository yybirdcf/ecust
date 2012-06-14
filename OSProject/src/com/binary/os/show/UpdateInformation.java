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
		int[][] detail = RealInformation.getDeviceUsed();
		if(detail[0][0] != -1){
			GlobalStaticVar.mf.devDetail[0].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[0].currPidLabel.setText(detail[0][0]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[0].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[0].currPidLabel.setText(""); 
		}
		if(detail[0][1] != -1){
			GlobalStaticVar.mf.devDetail[1].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[1].currPidLabel.setText(detail[0][1]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[1].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[1].currPidLabel.setText(""); 
		}
		if(detail[0][2] != -1){
			GlobalStaticVar.mf.devDetail[2].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[2].currPidLabel.setText(detail[0][2]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[2].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[2].currPidLabel.setText(""); 
		}
		if(detail[1][0] != -1){
			GlobalStaticVar.mf.devDetail[3].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[3].currPidLabel.setText(detail[1][0]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[3].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[3].currPidLabel.setText(""); 
		}
		if(detail[1][1] != -1){
			GlobalStaticVar.mf.devDetail[4].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[4].currPidLabel.setText(detail[1][1]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[4].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[4].currPidLabel.setText(""); 
		}
		if(detail[2][0] != -1){
			GlobalStaticVar.mf.devDetail[5].devStatusLabel.setText("ц╕");
			GlobalStaticVar.mf.devDetail[5].currPidLabel.setText(detail[2][0]+""); 
		}else{
			GlobalStaticVar.mf.devDetail[5].devStatusLabel.setText("оп");
			GlobalStaticVar.mf.devDetail[5].currPidLabel.setText(""); 
		}
	}
	
	private static void updateProcess(){
		GlobalStaticVar.mf.procNameLabel.setText(RealInformation.getProcess()+"");
		GlobalStaticVar.mf.currInstructLabel.setText(RealInformation.getIR());
		GlobalStaticVar.mf.tempResultLabel.setText(RealInformation.getResult()+"");
		GlobalStaticVar.mf.remainTLabel.setText(RealInformation.getSystemRelativeClock()+"");
	}
}
