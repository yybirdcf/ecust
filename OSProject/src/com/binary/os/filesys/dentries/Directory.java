package com.binary.os.filesys.dentries;
import java.util.ArrayList;

import com.binary.os.utils.ByteContainer;


public class Directory extends Dentry{
	
	private ArrayList<Dentry> dentryList;
	
	public Directory(){
		super();
		this.setDentryList(new ArrayList<Dentry>());
	}
	
	public Directory(byte[] fcb){
		super(fcb);
		this.setDentryList(new ArrayList<Dentry>());
	}
	
	public byte[] toByte(){
		ByteContainer content = new ByteContainer();
		
		for(Dentry dentry:dentryList){
			content.add(dentry.getFCB());
		}
		
		this.setContent(content.get());
		return content.get();
	}
	
	public boolean checkName(Dentry dentry){
		for(Dentry den:dentryList){
			if(den.getName().equals(dentry.getName())){
				return true;
			}
		}
		return false;
	}
	
	public boolean addDentry(Dentry dentry){
		dentryList.add(dentry);
		return true;
	}

	public int getSize() {
		return (16*dentryList.size());
	}
	
	public ArrayList<Dentry> getDentryList() {
		return dentryList;
	}

	public void setDentryList(ArrayList<Dentry> dentryList) {
		this.dentryList = dentryList;
	}
	
}
