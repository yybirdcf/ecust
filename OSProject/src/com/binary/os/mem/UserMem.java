package com.binary.os.mem;

public class UserMem {

	public static byte[] users;
	
	public static void init(){
		users = new byte[512];
	}
	
	
	public static int count = 0;
	
	public static void loadData(int blockNum,byte[] data){
		labelA:
		for(int i = 0; i < blockNum; i++){
			int no = users[MemGlobalVar.StartNo * 16 + i];
			for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
				users[no * MemGlobalVar.UnitBlock + j] = data[count++];
				if(count >= data.length)
					break labelA;
			}
		}
	}
	
	public static void clearData(){
		for(int i = 0; i < MemGlobalVar.OffSet; i++){
			int no = users[MemGlobalVar.StartNo * 16 + i];
			for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
				users[no * MemGlobalVar.UnitBlock + j] = 0;
			}
		}
	}
	
	public static void storeTable(int no){
		users[MemGlobalVar.StartNo * 16 + MemGlobalVar.OffSet] = (byte) no;
		MemGlobalVar.OffSet++;
	}
	
	public static void clearTable(){
		for(int i = 0; i < MemGlobalVar.UnitBlock; i++)
			users[MemGlobalVar.StartNo * 16 + i] = 0;
	}
}
