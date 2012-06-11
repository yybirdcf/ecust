package com.binary.os.filesys.blocks;

import java.util.Date;

import com.binary.os.utils.ByteHelper;



public class SuperBlock extends Block{
	private int blockCount;
	private int emptyCount;
	private int[] emptyStack;
	private boolean isModified;
	private Date lastModTime;
	
	public SuperBlock(){
		super();
		this.setNo(0);
	}
	
	public SuperBlock(byte[] content){
		super(0, content);
		
		this.blockCount = ByteHelper.byteToUnsignedInt(content[0]);
		this.emptyCount = ByteHelper.byteToUnsignedInt(content[1]);
		this.emptyStack = new int[10];
		
		byte[] btemp;
		for(int i=0; i<10; i++){
			btemp = ByteHelper.getSub(content, 2+4*i, 4);
			this.emptyStack[i] = ByteHelper.byteToInt(btemp);
		}
		this.isModified = ByteHelper.byteToBoolean(content[42]);
		
		btemp = ByteHelper.getSub(content, 43, 8);
		this.lastModTime = new Date(ByteHelper.byteToLong(btemp));
	}
	
	public byte[] toByte(){
		byte[] content = new byte[128];
		content[0] = (byte) blockCount;
		content[1] = (byte) emptyCount;
		byte[] btemp;
		for(int i=0; i<10; i++){
			btemp = ByteHelper.intToByte(emptyStack[i]);
			for(int j=0; j<4; j++){
				content[2+4*i+j] = btemp[j];
			}
		}
		content[42] = ByteHelper.booleanToByte(isModified);
		btemp = ByteHelper.longToByte(lastModTime.getTime());
		for(int j=0; j<8; j++){
			content[43+j] = btemp[j];
		}
		for(int i=51; i<content.length; i++){
			content[i] = (byte)0;
		}
		
		this.setContent(content);
		return content;
	}
	
	public int pop(){
		int index = emptyStack[emptyCount-1];
		emptyStack[emptyCount-1] = 0;
		emptyCount--;
		isModified = true;
		lastModTime = new Date();
		return index;
	}
	
	public boolean push(int index){
		if(emptyCount >= 10){
			return false;
		}
		emptyStack[emptyCount] = index;
		emptyCount++;
		isModified = true;
		lastModTime = new Date();
		return true;
	}
	
	public void format(){
		this.blockCount = 255;
		this.emptyCount = 10;
		int[] temp = new int[10];
		for(int i=0,j=12;i<10;i++,j--){
			temp[i] = j;
		}
		this.emptyStack = temp;
		this.isModified = false;
		this.lastModTime = new Date();
	}
	
	public int getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
		isModified = true;
		lastModTime = new Date();
	}
	public int getEmptyCount() {
		return emptyCount;
	}
	public void setEmptyCount(int emptyCount) {
		this.emptyCount = emptyCount;
		isModified = true;
		lastModTime = new Date();
	}
	
	public int[] getEmptystack() {
		return emptyStack;
	}
	public void setEmptystack(int[] emptystack) {
		this.emptyStack = emptystack;
		isModified = true;
		lastModTime = new Date();
	}
	public boolean isModified() {
		return isModified;
	}
	public void setModified(boolean isModified) {
		this.isModified = isModified;
	}
	public Date getLastModTime() {
		return lastModTime;
	}
	public void setLastModTime(long lastModTime) {
		this.lastModTime = new Date(lastModTime);
	}

	

}
