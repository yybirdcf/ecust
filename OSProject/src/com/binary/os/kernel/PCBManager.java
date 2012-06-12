package com.binary.os.kernel;

import java.util.LinkedList;
import java.util.Queue;

public class PCBManager {

	public static Queue<Integer> emptyQueue = new LinkedList<Integer>();
	public static Queue<Integer> readyQueue = new LinkedList<Integer>();
	public static Queue<Integer> blockQueue = new LinkedList<Integer>();
	
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
			pid = emptyQueue.poll();
		}
		return pid;
	}
	
	public static int removeFromReady(){
		int pid = 0;
		if(readyQueue.size() > 0){
			pid = readyQueue.poll();
		}
		return pid;
	}
	
	public static int removeFromBlock(){
		int pid = 0;
		if(blockQueue.size() > 0){
			pid = blockQueue.poll();
		}
		return pid;
	}
	
	public static void clearQueue(){
		emptyQueue.clear();
		readyQueue.clear();
		blockQueue.clear();
	}
}
