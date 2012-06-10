package com.binary.os.mem;

import java.util.ArrayList;
import java.util.List;

import com.binary.os.kernel.PCB;

public class SystemMem {

	private static List<PCB> pcbs;
	
	private static int[][] bitmap;
	
	public static void init(){
		
		pcbs = new ArrayList<PCB>();
		bitmap = new int[MemGlobalVar.BitMap_y][MemGlobalVar.BitMap_X];

		//初始化位视图
		for(int i = 0; i < MemGlobalVar.BitMap_y; i++){
			for(int j = 0; j < MemGlobalVar.BitMap_X; j++){
				bitmap[i][j] = 0;
			}
		}
		
		MemGlobalVar.idleBlockNum = 32;
	}
	
	public static boolean dispatch(byte[] data){
		int blockNum = (int) Math.ceil(data.length / 16);
		int count = 0;
		if(MemGlobalVar.idleBlockNum >= blockNum){
			for(int i = 0; i < MemGlobalVar.BitMap_y; i++){
				for(int j = 0; j < MemGlobalVar.BitMap_X; j++){
					if(bitmap[i][j] == 0){
						count++;
						int no = i * MemGlobalVar.BitMap_X + j;
						UserMem.nos.add(no);
						bitmap[i][j] = 1;
						if(count == blockNum){
							MemGlobalVar.idleBlockNum -= blockNum;
							UserMem.loadData(blockNum, data);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void recycle(List<Integer> blockNos){
		if(blockNos.size() > 0){
			for(int i = 0; i < blockNos.size(); i++){
				int no = blockNos.get(i);
				int y = no / 8;
				int x = no % 8;
				bitmap[y][x] = 0;
			}
			//回收页表占用内存
			UserMem.clearData();
		}
	}
}
