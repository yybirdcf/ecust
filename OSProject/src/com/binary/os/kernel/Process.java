package com.binary.os.kernel;

import com.binary.os.mem.SystemMem;

public class Process {
	
	public Process(){
		GlobalStaticVar.PID_NUM++;
		init();
	}
	
	private byte newpid;
	
	private void init() {
		// TODO Auto-generated method stub
		for(byte i = 0 ; i < 10 ; i++){
			if(GlobalStaticVar.PIDS[i] == 0){
				newpid = i;
				GlobalStaticVar.PIDS[i] = 1;
				SystemMem.pcbs[32 * i + 0] = i;
				SystemMem.pcbs[32 * i + 1] = GlobalStaticVar.PCB_EMPTY;
				SystemMem.pcbs[32 * i + 2] = 0;//发生中断变成1
				PCBManager.addToEmpty(i);
				break;
			}
		}
	}
	
	public void clearDataBufferArea(){
		for(int i = 0; i < 16; i++)
			SystemMem.pcbs[32 * newpid + 6 + i] = 0;
	}

	public byte getPID(){
		return newpid;
	}
	
	public byte getStatus(){
		return SystemMem.pcbs[32 * newpid + 1];
	}
	
	public void setStatus(byte status){
		SystemMem.pcbs[32 * newpid + 1] = status;
	}
	
	public byte getPSW(){
		return SystemMem.pcbs[32 * newpid + 2];
	}
	
	public void setPSW(byte psw){
		SystemMem.pcbs[32 * newpid + 2] = psw;
	}
	

	public void setMemInfo(byte startNo, byte OffSet, byte PagePeek){
		SystemMem.pcbs[32 * newpid + 3] = startNo; 
		SystemMem.pcbs[32 * newpid + 4] = OffSet; 
		SystemMem.pcbs[32 * newpid + 5] = PagePeek; 
	}
	
	public void setPageOffset(byte PagePeek){
		SystemMem.pcbs[32 * newpid + 5] = PagePeek;
	}
	
	public byte getStartNo(){
		return SystemMem.pcbs[32 * newpid + 3];
	}
	
	public byte getBlockNum(){
		return SystemMem.pcbs[32 * newpid + 4];
	}
	
	public byte getPageOffset(){
		return SystemMem.pcbs[32 * newpid + 5];
	}
	
	public void saveDataBuffer(byte[] data){
		for(int i = 0; i < data.length; i++)
			SystemMem.pcbs[32 * newpid + 6 + i] = data[i];
	}
	
	public byte[] restoreDataBuffer(){
		byte[] data = new byte[16];
		for(int i = 0; i < 16; i++){
			data[i] = SystemMem.pcbs[32 * newpid + 6 + i];
		}
		return data;
	}
	
	public void setResult(byte result){
		SystemMem.pcbs[32 * newpid + 31] = result;
	}
	
	public byte getResult(){
		return SystemMem.pcbs[32 * newpid + 31];
	}
	
	public void setPC(byte pc){
		SystemMem.pcbs[32 * newpid + 30] = pc;
	}
	
	public byte getPC(){
		return SystemMem.pcbs[32 * newpid + 30];
	}
}
