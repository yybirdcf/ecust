package com.binary.os.mem;

import com.binary.os.kernel.GlobalStaticVar;

public class UserMem {

	public static byte[] users;
	
	public static void init(){
		users = new byte[512];
	}
	
	
	public static int count = 0;
	
	public static void loadData(int blockNum,byte[] data){
		labelA:
		for(int i = 0; i < blockNum; i++){
			int no = users[MemGlobalVar.MemStartNo * 16 + i];
			for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
				users[no * MemGlobalVar.UnitBlock + j] = data[count++];
				if(count >= data.length)
					break labelA;
			}
		}
	}
	
	public static void clearData(){
		for(int i = 0; i < GlobalStaticVar.ProcessOffSet; i++){
			int no = users[GlobalStaticVar.ProcessStartNo * 16 + i];
			for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
				users[no * MemGlobalVar.UnitBlock + j] = 0;
			}
		}
	}
	
	public static void storeTable(int no){
		users[MemGlobalVar.MemStartNo * 16 + MemGlobalVar.MemOffSet] = (byte) no;
		MemGlobalVar.MemOffSet++;
	}
	
	public static void clearTable(){
		for(int i = 0; i < MemGlobalVar.UnitBlock; i++)
			users[GlobalStaticVar.ProcessStartNo * 16 + i] = 0;
	}
	
}
