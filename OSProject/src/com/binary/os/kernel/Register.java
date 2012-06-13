package com.binary.os.kernel;

import com.binary.os.mem.MemGlobalVar;
import com.binary.os.mem.UserMem;

public class Register {

	public static void loadDataToDataBuffer(){
		
		int pageNo = GlobalStaticVar.ProcessStartNo;
		byte no = UserMem.users[MemGlobalVar.UnitBlock * pageNo + GlobalStaticVar.ProcessPagePeek];
		
		byte[] data = new byte[MemGlobalVar.UnitBlock];
		for(int i = 0; i < MemGlobalVar.UnitBlock; i++){
			data[i] = UserMem.users[MemGlobalVar.UnitBlock * no + i];
		}
		for(int i = 0; i < data.length / 4; i++){
			if(data[i * 4] == 0)
				continue;
			byte[] temp = new byte[4];
			for(int j = 0; j < 4; j++){
				temp[j] = data[i * 4 + j];
			}
			GlobalStaticVar.DR.add(new String(temp));
		}
		
		GlobalStaticVar.ProcessPagePeek++;
	}
	
	public static void loadDataToIR(){
			
		if(GlobalStaticVar.DR.size() == 0)
			loadDataToDataBuffer();
		
		String comm = GlobalStaticVar.DR.removeFirst();
		String[] strs = comm.split("[;]|[.]");
		GlobalStaticVar.IR = strs[0];
	}
}
