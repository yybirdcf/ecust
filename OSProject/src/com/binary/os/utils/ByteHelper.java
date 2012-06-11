package com.binary.os.utils;

public class ByteHelper {
	
	//获得指定开始与长度的子byte数组,超出范围的返回0
	public static byte[] getSub(byte b[], int start, int length){
		byte[] temp = new byte[length];
		if(start>=b.length){//如果起始位置超过原数组长度，返回全0数组
			return temp;
		}
		if(start+length>=b.length){//如果获取长度超过原数组范围，超出范围数组元素置0
			for(int i=start,j=0; i<b.length; i++,j++){
				temp[j] = b[i];
			}
			return temp;
		}
		for(int i=0; i<length; i++){
			temp[i] = b[start + i];
		}
		return temp;
	}
	
	//long类型转成byte数组 
	public static byte[] longToByte(long number) { 
		long temp = number; 
		byte[] b = new byte[8]; 
		for (int i = 0; i < b.length; i++) { 
			b[7-i] = new Long(temp & 0xff).byteValue();// b[0]存最高位
			temp = temp >> 8; // 向右移8位 
		} 
		return b; 
	} 
	
	//byte数组转成long 
	public static long byteToLong(byte[] b) { 
		long s = 0; 
		long s0 = b[0] & 0xff;// 最高位 
		long s1 = b[1] & 0xff; 
		long s2 = b[2] & 0xff; 
		long s3 = b[3] & 0xff; 
		long s4 = b[4] & 0xff;// 最高位 
		long s5 = b[5] & 0xff; 
		long s6 = b[6] & 0xff; 
		long s7 = b[7] & 0xff; 
		
		s0 <<= 8 * 7;
		s1 <<= 8 * 6; 
		s2 <<= 8 * 5; 
		s3 <<= 8 * 4; 
		s4 <<= 24; 
		s5 <<= 16; 
		s6 <<= 8;  
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7; 
		return s; 
	} 
	
	
	public static byte[] intToByte(int number) { 
		int temp = number; 
		byte[] b = new byte[4]; 
		for (int i = 0; i < b.length; i++) { 
			b[3-i] = new Integer(temp & 0xff).byteValue();// b[0]存最高位
			temp = temp >> 8; // 向右移8位 
		} 
		return b; 
	} 
	
	
	public static int byteToInt(byte[] b) { 
		int s = 0; 
		int s0 = b[0] & 0xff;// 最高位 
		int s1 = b[1] & 0xff; 
		int s2 = b[2] & 0xff; 
		int s3 = b[3] & 0xff; 
		s0 <<= 24; 
		s1 <<= 16; 
		s2 <<= 8; 
		s = s0 | s1 | s2 | s3; 
		return s; 
	} 
	
	//byte转换成无符号整数
	public static int byteToUnsignedInt(byte b) { 
		int s = b & 0xff; 
		return s; 
	} 
	
	public static byte[] shortToByte(short number) { 
		int temp = number; 
		byte[] b = new byte[2]; 
		for (int i = 0; i < b.length; i++) { 
			b[1-i] = new Integer(temp & 0xff).byteValue();//b[0]存最高位
			temp = temp >> 8; // 向右移8位 
		} 
		return b; 
	} 
	
	
	public static short byteToShort(byte[] b) { 
		short s = 0; 
		short s0 = (short) (b[0] & 0xff);// 最低位 
		short s1 = (short) (b[1] & 0xff); 
		s0 <<= 8; 
		s = (short) (s0 | s1); 
		return s; 
	}
	
	//两个字节大小整数，不超过65535，转换成字节数组
	public static byte[] shortIntToByte(int number) { 
		int temp = number; 
		byte[] b = new byte[2]; 
		if(temp>65535){//如果数字大小超过两个字节能存储的无符号数的大小 则返回0数组
			return b;
		}
		for (int i = 0; i < b.length; i++) { 
			b[1-i] = new Integer(temp & 0xff).byteValue();//b[0]存最高位
			temp = temp >> 8; // 向右移8位 
		} 
		return b; 
	} 
	
	//两个字节转换成无符号的整数
	public static int byteToShortInt(byte[] b) { 
		int s = 0; 
		int s0 = b[0] & 0xff;// 最低位 
		int s1 = b[1] & 0xff; 
		s0 <<= 8; 
		s =  (s0 | s1) & 0xffff;
		return s; 
	}
	
	public static byte booleanToByte(boolean bool){
		if(bool){
			return (byte)1;
	    }else{
			return (byte)0;
		}
	}
	
	public static boolean byteToBoolean(byte b){
		if(b==0){
			return false;
	    }else{
			return true;
		}
	}
}
