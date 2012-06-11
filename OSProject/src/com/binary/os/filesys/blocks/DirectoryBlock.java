package com.binary.os.filesys.blocks;

import java.util.ArrayList;

import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.dentries.SFile;
import com.binary.os.utils.ByteHelper;



public class DirectoryBlock extends Block{
	
	private ArrayList<Dentry> dentryList;
	
	public DirectoryBlock(){
		super();
		dentryList = new ArrayList<Dentry>();
	}
	
	public DirectoryBlock(int no){
		super(no);
		dentryList = new ArrayList<Dentry>();
	}
	
	public DirectoryBlock(int no, byte[] content){
		super(no, content);
		
		dentryList = new ArrayList<Dentry>();
		
		byte[] fcb;
		for(int i=0; i<8; i++){
			fcb = ByteHelper.getSub(content, 16*i, 16);
			if(fcb[9]!=0){
				if(fcb[9]>8){//ÊÇÎÄ¼þ
					SFile file = new SFile(fcb);
					dentryList.add(file);
				}else{
					Directory dir = new Directory(fcb);
					dentryList.add(dir);
				}
			}
		}
	}
	
	public byte[] toByte(){
		byte[] content = new byte[128];
		for(int i=0; i<content.length; i++){
			content[i] = 0;
		}
		byte[] btemp;
		for(int i=0; i<dentryList.size(); i++){
			btemp = dentryList.get(i).getFCB();
			for(int j=0; j<btemp.length; j++){
				content[16*i+j] = btemp[j];
			}
		}
		
		this.setContent(content);
		return content;
	}
	
	public boolean addDentry(Dentry dentry){
		if(dentryList.size()>=8){
			return false;
		}
		dentryList.add(dentry);
		return true;
	}

	public ArrayList<Dentry> getDentryList() {
		return dentryList;
	}
	
	public int getCount(){
		return dentryList.size();
	}
}
