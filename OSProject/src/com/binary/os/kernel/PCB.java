package com.binary.os.kernel;

public class PCB {

	private int pid;
	private int status;//0:¿Õ°×, 1:¾ÍĞ÷, 2:×èÈû
	private String register;
	private String block;
	
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRegister() {
		return register;
	}
	public void setRegister(String register) {
		this.register = register;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	
	
}
