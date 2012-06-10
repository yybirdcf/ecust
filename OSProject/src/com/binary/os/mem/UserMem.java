package com.binary.os.mem;

import java.util.ArrayList;
import java.util.List;

public class UserMem {

	public static byte[] users;
	
	public static void init(){
		users = new byte[512];
		nos = new ArrayList<Integer>();
	}
	
	public static List<Integer> nos;
	
	public static int count = 0;
	
	public static void loadData(int blockNum,byte[] data){
		labelA:
		for(int i = 0; i < blockNum; i++){
			int no = nos.get(i);
			for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
				users[no * MemGlobalVar.UnitBlock + j] = data[count++];
				if(count >= data.length)
					break labelA;
			}
		}
	}
	
	public static void clearData(){
		if(nos.size() > 0){
			for(int i = 0; i < nos.size(); i++){
				int no = nos.get(i);
				for(int j = 0; j < MemGlobalVar.UnitBlock; j++){
					users[no * MemGlobalVar.UnitBlock + j] = 0;
				}
			}
			nos.clear();
		}
	}
}
