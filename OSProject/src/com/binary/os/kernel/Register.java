package com.binary.os.kernel;

import com.binary.os.mem.MemGlobalVar;
import com.binary.os.mem.UserMem;

public class Register {

	public static void loadDataToDataBuffer(){
		
		int pageNo = MemGlobalVar.StartNo;
		byte no = UserMem.users[MemGlobalVar.UnitBlock * pageNo + MemGlobalVar.PagePeek];
		
		byte[] data = new byte[MemGlobalVar.UnitBlock];
		for(int i = 0; i < MemGlobalVar.UnitBlock; i++){
			data[i] = UserMem.users[MemGlobalVar.UnitBlock * no + i];
		}
		for(int i = 0; i < data.length / 4; i++){
			byte[] temp = new byte[4];
			for(int j = 0; j < 4; j++){
				temp[j] = data[i * 4 + j];
			}
			GlobalStaticVar.DR.add(new String(temp));
		}
		
		MemGlobalVar.PagePeek++;
	}
	
	public static void loadDataToIR(){
		String comm = GlobalStaticVar.DR.poll();
		if(comm != null && comm !="" && !comm.equals("")){
			String[] strs = comm.split("[;]|[.]");
			GlobalStaticVar.IR = strs[0];
		}
	}
}
