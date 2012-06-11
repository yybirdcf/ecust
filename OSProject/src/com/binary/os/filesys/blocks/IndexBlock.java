package com.binary.os.filesys.blocks;

import java.util.ArrayList;

import com.binary.os.utils.ByteHelper;



public class IndexBlock extends Block{
	private ArrayList<Integer> indexList;
	
	public IndexBlock(){
		super();
	}
	
	public IndexBlock(int no){
		super(no);
		indexList = new ArrayList<Integer>();
	}
	
	public IndexBlock(int no, byte[] content){
		super(no, content);
		
		indexList = new ArrayList<Integer>();
		
		byte[] bIndex;
		int index;
		for(int i=0; i<32; i++){
			bIndex = ByteHelper.getSub(content, 4*i, 4);
			index = ByteHelper.byteToInt(bIndex);
			if(index!=0){
				indexList.add(index);
			}
		}
	}
	
	public byte[] toByte(){
		byte[] content = new byte[128];
		for(int i=0; i<content.length; i++){
			content[i] = 0;
		}
		byte[] btemp;
		for(int i=0; i<indexList.size(); i++){
			btemp = ByteHelper.intToByte(indexList.get(i));
			for(int j=0; j<btemp.length; j++){
				content[4*i+j] = btemp[j];
			}
		}

		this.setContent(content);
		return content;
	}
	
	public int getIndex(int at){
		return indexList.get(at);
	}
	
	public boolean addIndex(int index){
		if(indexList.size()==32){
			return false;
		}
		indexList.add(index);
		return true;
	}
	
	public ArrayList<Integer> getIndexList() {
		return indexList;
	}
	
	public int getCount(){
		return indexList.size();
	}
}
