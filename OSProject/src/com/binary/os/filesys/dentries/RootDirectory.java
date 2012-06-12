package com.binary.os.filesys.dentries;

import java.util.ArrayList;

public class RootDirectory extends Directory{
	
	public RootDirectory(){
		super();
		this.setRootName("root:");
		this.setAttribute(Dentry.D_S_W);
		this.setDirectAddr1(1);
		this.setDirectAddr2(2);
		this.setLev1Index(0);
		this.setLev2Index(0);
	}
	
	public RootDirectory(byte[] fcb){
		super(fcb);
		this.setRootName("root:");
		this.setAttribute(Dentry.D_S_W);
		this.setDirectAddr1(1);
		this.setDirectAddr2(2);
		this.setLev1Index(0);
		this.setLev2Index(0);
	}
	
	public boolean addDentry(Dentry dentry){
		ArrayList<Dentry> dentryList = this.getDentryList();
		if(dentryList.size()>=16){
			return false;
		}
		dentryList.add(dentry);
		return true;
	}
}
