package com.binary.os.kernel;

import java.util.LinkedList;

public class PCBManager {

	public static LinkedList<Integer> emptyQueue = new LinkedList<Integer>();
	public static LinkedList<Integer> readyQueue = new LinkedList<Integer>();
	public static LinkedList<Integer> blockQueue = new LinkedList<Integer>();
	
	public static void addToEmpty(int pid){
		if(emptyQueue.size() < 10){
			emptyQueue.add(pid);
		}
	}
	
	public static void addToReady(int pid){
		if(readyQueue.size() < 10){
			readyQueue.add(pid);
		}
	}
	
	public static void addToBlock(int pid){
		if(blockQueue.size() < 10){
			blockQueue.add(pid);
		}
	}
	
	public static int removeFromEmpty(){
		int pid = 0;
		if(emptyQueue.size() > 0){
			pid = emptyQueue.remove();
		}
		return pid;
	}
	
	public static int removeFromReady(){
		int pid = 0;
		if(readyQueue.size() > 0){
			pid = readyQueue.remove();
		}
		return pid;
	}
	
	public static int removeFromBlock(){
		int pid = 0;
		if(blockQueue.size() > 0){
			pid = blockQueue.remove();
		}
		return pid;
	}
	
	
	public static boolean removeFromBlock(int pid){
		if(blockQueue.size() > 0){
			return blockQueue.remove((Integer)pid);
		}
		return false;
	}
	
	public static void clearQueue(){
		emptyQueue.clear();
		readyQueue.clear();
		blockQueue.clear();
	}
}
