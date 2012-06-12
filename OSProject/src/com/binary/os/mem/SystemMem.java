package com.binary.os.mem;


public class SystemMem {

	public static byte[] pcbs;
	
	public static int[][] bitmap;
	
	public static void init(){
		
		pcbs = new byte[320];
		
		for(int i = 0; i < 320; i++)
			pcbs[i] = 0;
		
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
		if(MemGlobalVar.idleBlockNum > blockNum && MemGlobalVar.LimitBlock >= blockNum){
			for(int i = 0; i < MemGlobalVar.BitMap_y; i++){
				for(int j = 0; j < MemGlobalVar.BitMap_X; j++){
					if(bitmap[i][j] == 0){
						count++;
						int no = i * MemGlobalVar.BitMap_X + j;
						if(count == 1){
							//作为页表存放
							//将页表块号存放进pcb
							MemGlobalVar.StartNo = (byte) no;
						}else{
							UserMem.storeTable(no);
						}
						bitmap[i][j] = 1;
						if(count == blockNum + 1){
							MemGlobalVar.idleBlockNum -= count;
							UserMem.loadData(blockNum, data);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	public static void recycle(){
		if(MemGlobalVar.OffSet > 0){
			for(int i = 0; i < MemGlobalVar.OffSet; i++){
				int no = UserMem.users[MemGlobalVar.StartNo * 16 + i];
				int y = no / 8;
				int x = no % 8;
				bitmap[y][x] = 0;
			}
			//回收块内存
			UserMem.clearData();
			//回收页表占用内存
			UserMem.clearTable();
		}
	}
}
