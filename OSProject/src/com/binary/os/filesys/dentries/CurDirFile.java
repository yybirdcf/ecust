package com.binary.os.filesys.dentries;

public class CurDirFile extends SFile{
	
	public CurDirFile(Directory dir){
		super();
		this.setSuperName(dir.getName());
		this.setExtension(dir.getExtension());
		this.setAttribute(dir.getAttribute());
		this.setDirectAddr1(dir.getDirectAddr1());
		this.setDirectAddr2(dir.getDirectAddr2());
		this.setLev1Index(dir.getLev1Index());
		this.setLev2Index(dir.getLev2Index());
	}

	public String getSizeString(){
		return "";
	}
	
	public String getTypeString(){
		return "µ±Ç°Ä¿Â¼";
	}
	
	public String toString(){
		return ".";
	}
}
