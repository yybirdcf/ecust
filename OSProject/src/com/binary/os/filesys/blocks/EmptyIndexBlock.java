package com.binary.os.filesys.blocks;
import com.binary.os.utils.ByteHelper;

public class EmptyIndexBlock extends Block{
	private int emptyCount;
	private int[] emptyStack;
	
	public EmptyIndexBlock(){
		super();
	}
	
	public EmptyIndexBlock(int no){
		super(no);
		this.emptyCount = 0;
		this.emptyStack = new int[10];
	}
	
	public EmptyIndexBlock(int no, byte[] content){
		super(no, content);
		
		this.emptyCount = ByteHelper.byteToUnsignedInt(content[0]);
		this.emptyStack = new int[10];
		
		byte[] btemp;
		for(int i=0; i<10; i++){
			btemp = ByteHelper.getSub(content, 1+4*i, 4);
			this.emptyStack[i] = ByteHelper.byteToInt(btemp);
		}
	}
	
	public byte[] toByte(){
		byte[] content = new byte[128];
		content[0] = (byte) emptyCount;
		byte[] btemp;
		for(int i=0; i<10; i++){
			btemp = ByteHelper.intToByte(emptyStack[i]);
			for(int j=0; j<4; j++){
				content[1+4*i+j] = btemp[j];
			}
		}
		
		this.setContent(content);
		return content;
	}
	
	public int getEmptyCount() {
		return emptyCount;
	}
	public void setEmptyCount(int emptyCount) {
		this.emptyCount = emptyCount;
	}
	public int[] getEmptystack() {
		return emptyStack;
	}
	public void setEmptystack(int[] emptystack) {
		this.emptyStack = emptystack;
	}
}
