package com.binary.os.filesys.dentries;

import com.binary.os.utils.ByteHelper;

public class SFile extends Dentry{
	
	
	public SFile(){
		super();
	}
	
	public SFile(byte[] fcb){
		super(fcb);
	}
	
	public String toString(){
		String text = new String(this.getContent());
		return text;
	}
	
	public boolean setText(String text){
		byte[] content = text.getBytes();
		if(content.length>32256){
			return false;
		}
		super.setSize(content.length);
		super.setContent(content);
		return true;
	}
	
	public boolean setSize(int size) {
		if(size>32256){//超过磁盘能存的最大空间,252*128=32256
			return false;
		}
		super.setSize(size);
		byte[] btemp = new byte[size];
		btemp = ByteHelper.getSub(this.getContent(), 0, size);
		super.setContent(btemp);
		return true;
	}
	
	public void setContent(byte[] content) {//只截取与size相等的byte[]
		byte[] temp = new byte[this.getSize()];
		System.arraycopy(content, 0, temp, 0, this.getSize());
		super.setContent(content);
	}
}
