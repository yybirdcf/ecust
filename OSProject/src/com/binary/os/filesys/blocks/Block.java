package com.binary.os.filesys.blocks;

public class Block {
	private int no;
	private byte[] content;
	
	public Block(){
		super();
	}
	
	public Block(int no){
		this.no = no;
	}
	
	public Block(int no, byte[] content){
		this.no = no;
		this.content = content;
	}
	
	public byte[] toByte(){
		return content;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
}
