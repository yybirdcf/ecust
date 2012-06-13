package com.binary.os.kernel;

import java.util.regex.Pattern;

public class Compile {

	public static boolean checkCommand(byte[] data){
		
		String str = new String(data);
		String[] strs = str.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < strs.length; i++){
			sb.append(strs[i]);
		}
		String s = sb.toString();
		String reg = "((x|!)(=|\\+|-|[A-C])([0-9]|\\+|-);)*(end.)$";

		boolean tag = false;
		tag = Pattern.matches(reg, s);
		return tag;
	}
	
	public static byte[] filterCommand(byte[] data){
		
		String str = new String(data);
		String[] strs = str.split("\\s+");
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < strs.length; i++){
			sb.append(strs[i]);
		}
		String s = sb.toString();
		return s.getBytes();
	}
}
