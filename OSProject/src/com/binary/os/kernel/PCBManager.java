package com.binary.os.kernel;

import java.util.LinkedList;
import java.util.Queue;

public class PCBManager {

	public static int PCBNUM = 0;
	public static PCB[] pcbs = new PCB[10];
	
	public static Queue<PCB> emptyQueue = new LinkedList<PCB>();
	public static Queue<PCB> readyQueue = new LinkedList<PCB>();
	public static Queue<PCB> blockQueue = new LinkedList<PCB>();
	
	public static int PCBNOW = 0;
	
	public static void addToEmpty(PCB p){
		if(emptyQueue.size() < 10){
			emptyQueue.add(p);
		}
	}
	
	public static void addToReady(PCB p){
		if(readyQueue.size() < 10){
			readyQueue.add(p);
		}
	}
	
	public static void addToBlock(PCB p){
		if(blockQueue.size() < 10){
			blockQueue.add(p);
		}
	}
	
	public static PCB removeFromEmpty(){
		PCB p = null;
		if(emptyQueue.size() > 0){
			p = emptyQueue.poll();
		}
		return p;
	}
	
	public static PCB removeFromReady(){
		PCB p = null;
		if(readyQueue.size() > 0){
			p = readyQueue.poll();
		}
		return p;
	}
	
	public static PCB removeFromBlock(){
		PCB p = null;
		if(blockQueue.size() > 0){
			p = blockQueue.poll();
		}
		return p;
	}
}
