package com.binary.os.filesys.manager;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

import com.binary.os.filesys.blocks.Block;
import com.binary.os.filesys.blocks.DirectoryBlock;
import com.binary.os.filesys.blocks.EmptyIndexBlock;
import com.binary.os.filesys.blocks.IndexBlock;
import com.binary.os.filesys.blocks.SuperBlock;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.dentries.RootDirectory;
import com.binary.os.filesys.dentries.SFile;
import com.binary.os.utils.ByteContainer;


public class DiskManager {

	public static String DISK_PATH = "disk";
	public static int BYTE_PER_BLOCK = 128;
	
	private SuperBlock sBlock;
	
	public DiskManager(){
		sBlock = new SuperBlock(readBlock(0));
	}
	
	private byte[] readBlock(int no){
		byte[] content = new byte[BYTE_PER_BLOCK];
		RandomAccessFile disk = null;
		try {
			File diskFile = new File(DISK_PATH);
			disk  = new RandomAccessFile(diskFile,"rw");
			disk.seek(no*BYTE_PER_BLOCK);
			disk.read(content);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(disk!=null){
				try {
					disk.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return content;
	}
	
	private boolean saveBlock(Block block){
		int no = block.getNo();
		RandomAccessFile disk = null;
		try {
			File diskFile = new File(DISK_PATH);
			disk  = new RandomAccessFile(diskFile,"rw");
			disk.seek(no*BYTE_PER_BLOCK);
			disk.write(block.toByte());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(disk!=null){
				try {
					disk.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	private int getAnEmptyBlock(){
		int index;
		if(sBlock.getEmptyCount()==1){//当前栈中最后一个可分配盘块号
			index = sBlock.getEmptystack()[0];
			if(index == 0){//0为空闲链结尾
				return index;
			}
			EmptyIndexBlock eib = new EmptyIndexBlock(index, readBlock(index));
			sBlock.setEmptystack(eib.getEmptystack());
			sBlock.setEmptyCount(eib.getEmptyCount());
		}else{
			index = sBlock.pop();
		}
		saveSuperBlock();
		return index;
	}
	
	private boolean addAnEmptyBlock(int index){
		boolean isOK = true;
		if(index == 0){//0为空闲链结尾
			return false;
		}
		if(sBlock.getEmptyCount()==10){
			EmptyIndexBlock eib = new EmptyIndexBlock(index);
			eib.setEmptyCount(sBlock.getEmptyCount());
			eib.setEmptystack(sBlock.getEmptystack());
			saveBlock(eib);
			int[] stack = new int[10];
			stack[0] = index;
			sBlock.setEmptystack(stack);
			sBlock.setEmptyCount(1);
		}else{
			isOK = sBlock.push(index);
		}
		saveSuperBlock();
		return isOK;
	}
	
	private LinkedList<Integer> getEmptyBlock(int count){
		LinkedList<Integer> indexs = new LinkedList<Integer>();
		for(int i=0; i<count; i++){
			int index = getAnEmptyBlock();
			if(index == 0){//0为空闲链结尾
				break;
			}
			indexs.add(index);
		}
		if(indexs.size() < count){
			addEmptyBlock(indexs);
			indexs.clear();
		}
		return indexs;
	}
	
	private boolean addEmptyBlock(LinkedList<Integer> indexs){
		while(!indexs.isEmpty()){
			if(addAnEmptyBlock(indexs.pollLast())==false){//倒着回收块
				return false;
			}
		}
		return true;
	}
	
	private boolean saveSuperBlock(){
		sBlock.setModified(false);
		return saveBlock(sBlock);
	}
		
	public void readFile(SFile file){

		ByteContainer content = new ByteContainer();
				
		int index = file.getDirectAddr1();//直接地址1
		if(index > 0){//存在直接地址1
	
			content.add(readBlock(index));
			
			index = file.getDirectAddr2();//直接地址2
			if(index > 0){//存在直接地址2
				
				content.add(readBlock(index));
			
				index = file.getLev1Index();//一级索引地址
				if(index > 0){//存在一级索引地址

					IndexBlock iBlock = new IndexBlock(index, readBlock(index));//一级索引块
					
					for(int i:iBlock.getIndexList()){
						content.add(readBlock(i));
					}
			
					index = file.getLev2Index();//二级索引地址
					if(index > 0){//存在二级索引地址

						iBlock = new IndexBlock(index, readBlock(index));//一级索引块
						
						for(int i:iBlock.getIndexList()){
							
							IndexBlock iBlock2 = new IndexBlock(i, readBlock(i));//二级索引块
							ArrayList<Integer> indexList2 = iBlock2.getIndexList();
							
							for(int j:indexList2){
								content.add(readBlock(j));
							}	
						}
					}
				}
			}
		}
		file.setContent(content.get());
	}
	
	public void readDirectory(Directory dir){
		
		ArrayList<Dentry> dentryList = dir.getDentryList();
		dentryList.clear();//清空重置
		
		int index = dir.getDirectAddr1();
		if(index>0){
			DirectoryBlock dBlock = new DirectoryBlock(index, readBlock(index));
			dentryList.addAll(dBlock.getDentryList());
			
			index = dir.getDirectAddr2();
			if(index>0){
				dBlock = new DirectoryBlock(index, readBlock(index));
				dentryList.addAll(dBlock.getDentryList());
			
				index = dir.getLev1Index();
				if(index>0){
					IndexBlock iBlock = new IndexBlock(index, readBlock(index));
					
					for(int i:iBlock.getIndexList()){
						dBlock = new DirectoryBlock(i, readBlock(i));
						dentryList.addAll(dBlock.getDentryList());
					}
					
					index = dir.getLev2Index();
					if(index>0){
						iBlock = new IndexBlock(index, readBlock(index));
						
						for(int i:iBlock.getIndexList()){
							IndexBlock iBlock2 = new IndexBlock(i, readBlock(i));
	
							for(int j:iBlock2.getIndexList()){
								dBlock = new DirectoryBlock(j, readBlock(j));
								dentryList.addAll(dBlock.getDentryList());
							}	
						}
					}
				}
			}
		}
	}
	
	public boolean saveDentry(Dentry dentry){
		
		recycleDentry(dentry);//写文件前先把文件原来占用的块会收重新分配
		
		int fileSize = dentry.getSize();//文件字节数
		int count = (int) Math.ceil(fileSize/128f);//需要实体块数
		if(count == 0){
			return true;
		}
		ByteContainer fileContent = new ByteContainer(dentry.toByte());
		
		int scount = count;//全部需要的块
		int tcount = count;//用来遍历的
		if(tcount>0){
			tcount = tcount - 2;//两个直接块
			if(tcount>0){
				scount++;//第一个一级索引块
				tcount = tcount - 32;//一级索引的32个实体块
				if(tcount>0){
					scount = scount + 2;//第二个一级索引块和一级索引块的第一个二级索引块
					tcount = tcount - 32;//一级索引块的第一个二级索引块的32个实体块
					while(tcount>0){
						scount++;//增加一个二级索引块
						tcount = tcount - 32;//每个二级索引块能索引32个实体块
					}
				}
			}
		}
		
		LinkedList<Integer> indexs = getEmptyBlock(scount);//预先获取需要的空闲块
		if(indexs.size() == 0){//分配所需空闲块失败
			return false;
		}
		
		if(count > 0){//直接1
		
			int index = indexs.poll();
			dentry.setDirectAddr1(index);//将地址写入文件
			Block block = new Block(index, fileContent.poll(BYTE_PER_BLOCK));
			saveBlock(block);
			
			count = count - 1;//分配一块
			
			if(count > 0){//直接2
				
				index = indexs.poll();
				dentry.setDirectAddr2(index);//将地址写入文件
				block = new Block(index, fileContent.poll(BYTE_PER_BLOCK));
				saveBlock(block);
				
				count = count - 1;//分配一块
				
				if(count > 0){//一次间接
					
					index = indexs.poll();
					dentry.setLev1Index(index);//将一次索引地址写入文件
					IndexBlock iBlock = new IndexBlock(index);//一次间接的索引块
					
					tcount = count;//一级索引实际需要的实体块数
					if(tcount > 32){//最多不能超过32块
						tcount = 32;
					}
			
					for(int i=0; i<tcount; i++){
						index = indexs.poll();
						iBlock.addIndex(index);//讲物理块号加入索引块
						block = new Block(index, fileContent.poll(BYTE_PER_BLOCK));
						saveBlock(block);
					}
			
					saveBlock(iBlock);//写入一级索引块
					
					count = count - 32;//一个索引块能容32块实体块
					
					if(count > 0){//二级间接
						
						index = indexs.poll();
						dentry.setLev2Index(index);//将二次索引地址写入文件
						iBlock = new IndexBlock(index);//二次间接的第一级索引块
						
						while(count>0){//若还有剩余块则需要建新的二级索引块
							index = indexs.poll();
							iBlock.addIndex(index);//将第二级索引块号加入第一级索引块
							
							IndexBlock iBlock2 = new IndexBlock(index);//第二级索引块
							
							tcount = count;//二级索引实际需要的实体块数
							if(tcount > 32){//最多不能超过32块
								tcount = 32;
							}
							
							for(int i=0;i<tcount; i++){
								index = indexs.poll();
								iBlock2.addIndex(index);//将实体块号加入第二级索引
								block = new Block(index, fileContent.poll(BYTE_PER_BLOCK));
								saveBlock(block);//第二级索引的实体块
							}
							saveBlock(iBlock2);//将第二级索引块写入磁盘
							
							count = count - 32;//一个索引块能容32块实体块
						}
						saveBlock(iBlock);//将第一级索引块写入磁盘
					}
				}
			}
		}
		
		return true;
	}
	
	public boolean saveRoot(RootDirectory root){
		boolean isOk = true;
		
		ByteContainer fileContent = new ByteContainer(root.toByte());
		
		Block block = new Block(1, fileContent.poll(BYTE_PER_BLOCK));
		if(!saveBlock(block)){
			isOk = false;
		}
			
		block = new Block(2, fileContent.poll(BYTE_PER_BLOCK));
		if(!saveBlock(block)){
			isOk = false;
		}

		return isOk;
	}
	
	public boolean recycleDentry(Dentry dentry){
		
		LinkedList<Integer> indexs = new LinkedList<Integer>();//待回收块号队列
				
		int index = dentry.getDirectAddr1();//直接地址1
		if(index > 0){//存在直接地址1
	
			indexs.add(index);
			dentry.setDirectAddr1(0);//直接地址1置0
			
			index = dentry.getDirectAddr2();//直接地址2
			if(index > 0){//存在直接地址2
				
				indexs.add(index);
				dentry.setDirectAddr2(0);//直接地址2置0
			
				index = dentry.getLev1Index();//一次索引地址
				if(index > 0){//存在一次索引地址
					
					indexs.add(index);
					dentry.setLev1Index(0);//一次索引地址置0

					IndexBlock iBlock = new IndexBlock(index, readBlock(index));//一级索引块
					ArrayList<Integer> indexList = iBlock.getIndexList();
					
					for(int i:indexList){
						indexs.add(i);
					}
			
					index = dentry.getLev2Index();//二次索引地址
					if(index > 0){//存在二次索引地址

						indexs.add(index);
						dentry.setLev2Index(0);//二次索引地址置0
						
						iBlock = new IndexBlock(index, readBlock(index));//一级索引块
						indexList = iBlock.getIndexList();
						
						IndexBlock iBlock2;
						ArrayList<Integer> indexList2;
						for(int i:indexList){
							
							indexs.add(i);
							
							iBlock2 = new IndexBlock(i, readBlock(i));//二级索引块
							indexList2 = iBlock2.getIndexList();
							
							for(int j:indexList2){
								indexs.add(j);
							}	
						}
					}
				}
			}
		}
		
		return addEmptyBlock(indexs);
	}
	
	public boolean format(){
		
		//清空磁盘
		byte[] content = new byte[255*128];
		RandomAccessFile disk = null;
		try {
			File diskFile = new File(DISK_PATH);
			disk  = new RandomAccessFile(diskFile,"rw");
			disk.seek(0);
			disk.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}finally{
			if(disk!=null){
				try {
					disk.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//初始时超级块
		SuperBlock sBlock = new SuperBlock();
		sBlock.format();
		saveBlock(sBlock);
		
		//初始每个空闲索引块，除最后一个
		for(int i=12; i<243; i=i+10){
			EmptyIndexBlock eib = new EmptyIndexBlock(i);
			int[] temp = new int[10];
			for(int j=0; j<10; j++){
				temp[j] =  i + 10 - j;
			}
			eib.setEmptystack(temp);
			eib.setEmptyCount(10);
			saveBlock(eib);
		}
		
		//最后一个空闲索引块只有两个索引
		EmptyIndexBlock eib = new EmptyIndexBlock(252);
		int[] temp = new int[10];
		temp[0] = 0;
		temp[1] = 254;
		temp[2] = 253;
		eib.setEmptystack(temp);
		eib.setEmptyCount(3);
		saveBlock(eib);
		
		this.sBlock = new SuperBlock(readBlock(0));//重新读取SuperBlock
		
		return true;
	}
	
	public boolean[] getUsage(){
		boolean[] usage = new boolean[255];//使用情况数组
		for(int i=0; i<255; i++){//初始化全为已使用
			usage[i] = true;
		}
		
		int emptyCount = sBlock.getEmptyCount();//空闲数
		int[] emptyStack = sBlock.getEmptystack();//空闲块号栈

		int index = 0;
		do{
			for(int i=emptyCount-1; i>=0; i--){//置栈内每个空闲块号对应的使用情况为false;
				index = emptyStack[i];
				if(index == 0){//若为0 则为结尾
					break;
				}
				usage[index] = false;
			}
			if(index != 0){
				EmptyIndexBlock eib = new EmptyIndexBlock(index, readBlock(index));//读取下一个空闲索引块
				emptyStack = eib.getEmptystack();//赋新空闲块号栈
				emptyCount = eib.getEmptyCount();//赋新空闲数
			}
		}while(index != 0);
		
		usage[0] = true;
		usage[1] = true;
		usage[2] = true;
		
		return usage;
	}
	
	//刷新
	public void refresh(){
		this.sBlock = new SuperBlock(readBlock(0));//重新读取SuperBlock
	}
}
