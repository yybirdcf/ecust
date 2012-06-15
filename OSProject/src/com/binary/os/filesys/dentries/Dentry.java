package com.binary.os.filesys.dentries;

import com.binary.os.utils.ByteHelper;

public abstract class Dentry {
	public static final int D_H_R = 1;
	public static final int D_H_W = 3;
	public static final int D_S_R = 5;
	public static final int D_S_W = 7;
	public static final int F_H_R = 9;
	public static final int F_H_W = 11;
	public static final int F_S_R = 13;
	public static final int F_S_W = 15;
	
	private String name;
	private String extension;
	private int attribute;
	private int size;
	private int directAddr1;
	private int directAddr2;
	private int lev1Index;
	private int lev2Index;
	
	private byte[] fcb;
	private byte[] content;

	public Dentry(){
		super();
		this.fcb = new byte[16];
		this.content = new byte[0];
		
		this.name = "";
		this.extension = "";
		this.attribute = F_S_W;
		this.size = 0;
		this.directAddr1 = 0;
		this.directAddr2 = 0;
		this.lev1Index = 0;
		this.lev2Index = 0;
	}
	
	public Dentry(byte[] fcb){
		this.fcb = fcb;
		this.content = new byte[0];
		
		byte[] btemp = ByteHelper.getSub(fcb, 0, 6);
		this.name = new String(btemp).trim();
		btemp = ByteHelper.getSub(fcb, 6, 3);
		this.extension = new String(btemp).trim();
		this.attribute = ByteHelper.byteToUnsignedInt(fcb[9]);
		btemp = ByteHelper.getSub(fcb, 10, 2);
		this.size = ByteHelper.byteToShortInt(btemp);
		this.directAddr1 = ByteHelper.byteToUnsignedInt(fcb[12]);
		this.directAddr2 = ByteHelper.byteToUnsignedInt(fcb[13]);
		this.lev1Index = ByteHelper.byteToUnsignedInt(fcb[14]);
		this.lev2Index = ByteHelper.byteToUnsignedInt(fcb[15]);
	}
	
	public byte[] getFCB(){
		for(int i=0; i<fcb.length; i++){
			fcb[i] = 0;
		}
		byte[] btemp = name.getBytes();
		for(int i=0; i<btemp.length; i++){
			fcb[i] = btemp[i];
		}
		btemp = extension.getBytes();
		for(int i=0; i<btemp.length; i++){
			fcb[6+i] = btemp[i];
		}
		fcb[9] = (byte) attribute;
		btemp = ByteHelper.shortIntToByte(size);
		for(int i=0; i<2; i++){
			fcb[10+i] = btemp[i];
		}
		fcb[12] = (byte) directAddr1;
		fcb[13] = (byte) directAddr2;
		fcb[14] = (byte) lev1Index;
		fcb[15] = (byte) lev2Index;
		
		return fcb;
	}
	
	public byte[] toByte(){
		return content;
	}
	
	public String toString(){
		return getFullName();
	}
	
	public boolean isFile(){
		if(attribute>8){
			return true;
		}else{
			return false;
		}
	}
	
	public String getTypeString(){
		if(isFile()){
			return "文件";
		}else{
			return "目录";
		}
	}
	
	public boolean changeAttri(String attri){
		if(attri.equals("r")){
			this.attribute = this.attribute & 0xd;
		}else if(attri.equals("w")){
			this.attribute = this.attribute | 0x2;
		}else if(attri.equals("h")){
			this.attribute = this.attribute & 0xb;
		}else if(attri.equals("s")){
			this.attribute = this.attribute | 0x4;
		}else{
			return false;
		}
		return true;
	}
		
	public String getName() {
		return name;
	}

	public boolean setName(String name) {
		if(name.getBytes().length>6){
			return false;
		}
		if(name.contains(":")){
			return false;
		}
		this.name = name;
		return true;
	}
	
	public String getFullName(){
		if(extension.equals("")){
			return name;
		}
		return name + "." + extension;
	}
	
	protected void setSuperName(String name){
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public boolean setExtension(String extension) {
		if(extension.getBytes().length>3){
			return false;
		}
		if(extension.contains(":")){
			return false;
		}
		this.extension = extension;
		return true;
	}

	public int getAttribute() {
		return attribute;
	}
	
	public String getStringAttri(){
		if(attribute == D_H_R || attribute == F_H_R){
			return "隐藏与只读";
		}else if(attribute == D_H_W || attribute == F_H_W){
			return "隐藏与可写";
		}else if(attribute == D_S_R || attribute == F_S_R){
			return "显示与只读";
		}else if(attribute == D_S_W || attribute == F_S_W){
			return "显示与可写";
		}else{
			return "非法属性";
		}
	}

	public void setAttribute(int attribute) {
		this.attribute = attribute;
	}
	
	public boolean isHide(){
		if(attribute == D_H_R || attribute == D_H_W || attribute == F_H_R || attribute == F_H_W){
			return true;
		}
		return false;
	}

	public int getSize() {
		return size;
	}
	
	public String getSizeString(){
		return String.valueOf(size) + "字节";
	}

	public boolean setSize(int size) {
		if(size>32256){//超过磁盘能存的最大空间,252*128=32256
			return false;
		}
		this.size = size;
		return true;
	}

	public int getDirectAddr1() {
		return directAddr1;
	}

	public void setDirectAddr1(int directAddr1) {
		this.directAddr1 = directAddr1;
	}

	public int getDirectAddr2() {
		return directAddr2;
	}

	public void setDirectAddr2(int directAddr2) {
		this.directAddr2 = directAddr2;
	}

	public int getLev1Index() {
		return lev1Index;
	}

	public void setLev1Index(int lev1Index) {
		this.lev1Index = lev1Index;
	}

	public int getLev2Index() {
		return lev2Index;
	}

	public void setLev2Index(int lev2Index) {
		this.lev2Index = lev2Index;
	}
	
	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {//只截取与size相等的byte[]
		this.content = content;
	}
	
}
