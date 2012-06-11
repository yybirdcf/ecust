package com.binary.os.utils;

import java.util.Iterator;
import java.util.LinkedList;

public class ByteContainer {
	LinkedList<Byte> byteList;
	
	public ByteContainer(){
		byteList = new LinkedList<Byte>();
	}
	
	public ByteContainer(byte[] bytes){
		byteList = new LinkedList<Byte>();
		for(byte b:bytes){
			byteList.add(b);
		}
	}
	
	public void add(byte[] bytes){
		for(byte b:bytes){
			byteList.add(b);
		}
	}
	
	public byte[] get(){
		byte[] bytes = new byte[byteList.size()];
		Iterator<Byte> iter = byteList.iterator();
		for(int i=0; i<bytes.length; i++){
			bytes[i] = iter.next();
		}
		return bytes;
	}
	
	public byte[] poll(int count){
		byte[] bytes = new byte[count];
		for(int i=0; i<count; i++){
			Byte b = byteList.poll();
			if(b == null){
				b = 0;
			}
			bytes[i] = b;
		}
		return bytes;
	}
}
