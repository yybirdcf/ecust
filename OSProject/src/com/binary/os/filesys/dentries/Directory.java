package com.binary.os.filesys.dentries;
import java.util.ArrayList;

import com.binary.os.utils.ByteContainer;


public class Directory extends Dentry{
	
	private ArrayList<Dentry> dentryList;
	
	public Directory(){
		super();
		this.setAttribute(D_S_W);
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
	
	public boolean checkName(String name, String exten){
		for(Dentry den:dentryList){
			if(den.getName().equals(name)){//同名
				if(den.getExtension().equals(exten)){//同后缀
					return true;
				}
			}
		}
		return false;
	}
	
	public SFile checkFileName(String name, String exten){
		for(Dentry den:dentryList){
			if(den.getName().equals(name)){//同名
				if(den.getExtension().equals(exten)){//同后缀
					if(den.isFile() == true){//是文件
						return (SFile) den;
					}
				}
			}
		}
		return null;
	}
	
	public Directory checkDirName(String dirName){
		for(Dentry den:dentryList){
			if(den.getName().equals(dirName)){//同名
				if(den.isFile() == false){//是目录
					return (Directory) den;
				}
			}
		}
		return null;
	}
	
	
	public boolean addDentry(Dentry dentry){
		dentryList.add(dentry);
		return true;
	}
	
	public boolean removeDentry(Dentry dentry){
		return dentryList.remove(dentry);
	}

	public int getSize() {
		return (16*dentryList.size());
	}
	
	public String getSizeString(){
		return "";
	}
	
	public ArrayList<Dentry> getDentryList() {
		return dentryList;
	}

	public void setDentryList(ArrayList<Dentry> dentryList) {
		this.dentryList = dentryList;
	}
	
}
