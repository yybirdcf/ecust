package com.binary.os.filesys.manager;

import java.util.ArrayList;
import java.util.LinkedList;

//解释器
public class Interpreter {
	
	ArrayList<String> words = new ArrayList<String>();

	public Interpreter(String commands){
		String[] wordsArray = commands.trim().split("\\s+");//拆分成单词
		for(String word:wordsArray){//去除空字符
			if(!word.equals("")){//不为空
				words.add(word);
			}
		}
	}
	
	//获得操作符
	public String getOperat(){
		if(words.size() == 0){//不存在操作
			return "";
		}
		String operation = words.get(0);//第一个单词为操作
		return operation;
	}

	//单文件名时，获得文件名
	public String getFileName(){
		if(words.size() < 2){//不存在文件名
			return "";
		}
		String[] pathArray = words.get(1).split("\\\\|/");//拆分路径
		LinkedList<String> path = new LinkedList<String>();
		for(String p:pathArray){//去除空字符
			if(!p.equals("")){//不为空
				path.add(p);
			}
		}
		
		return path.peekLast();
	}
	
	//单文件名时，获得路径树
	public String[] getDirs(){
		if(words.size() < 2){//不存在文件名
			return new String[0];
		}
		String[] pathArray = words.get(1).split("\\\\|/");//拆分路径
		LinkedList<String> path = new LinkedList<String>();
		for(String p:pathArray){//去除空字符
			if(!p.equals("")){//不为空
				path.add(p);
			}
		}
		
		if(path.size() == 1){//只有文件名
			return new String[0];
		}else{
			path.pollLast();//删除文件名
			String[] dirs = new String[path.size()];
			dirs = path.toArray(dirs);
			return dirs;
		}
	}

	//双文件名时，获得源文件名
	public String getSrcFileName(){
		return getFileName();
	}
	
	//双文件名时，获得源路径树
	public String[] getSrcDirs(){
		return getDirs();
	}
	
	//双文件名时，获得目标文件名
	public String getDesFileName(){
		if(words.size() < 3){//不存在文件名
			return "";
		}
		String[] pathArray = words.get(2).split("\\\\|/");//拆分路径
		LinkedList<String> path = new LinkedList<String>();
		for(String p:pathArray){//去除空字符
			if(!p.equals("")){//不为空
				path.add(p);
			}
		}
		
		return path.peekLast();
	}
	
	//双文件名时，获得目标路径树
	public String[] getDesDirs(){
		if(words.size() < 3){//不存在文件名
			return new String[0];
		}
		String[] pathArray = words.get(2).split("\\\\|/");//拆分路径
		LinkedList<String> path = new LinkedList<String>();
		for(String p:pathArray){//去除空字符
			if(!p.equals("")){//不为空
				path.add(p);
			}
		}
		
		
		if(path.size() == 1){//只有文件名
			return new String[0];
		}else{
			path.pollLast();//删除文件名
			String[] dirs = new String[path.size()];
			dirs = path.toArray(dirs);
			return dirs;
		}
	}
	
	//获得目录
	public String[] getDirPath(){
		if(words.size() < 2){//不存在目录
			return new String[0];
		}
		String[] pathArray = words.get(1).split("\\\\|/");//拆分路径
		LinkedList<String> path = new LinkedList<String>();
		for(String p:pathArray){//去除空字符
			if(!p.equals("")){//不为空
				path.add(p);
			}
		}
		
		String[] dirs = new String[path.size()];
		dirs = path.toArray(dirs);
		return dirs;
	}
	
	//获得要修改的属性
	public String[] getAttrs(){
		if(words.size() < 3){//不存在属性
			return new String[0];
		}
		
		ArrayList<String> attrsList = new ArrayList<String>();
		//判断从第三个单词开始是不是属性
		for(int i=2; i<words.size(); i++){
			String attri = words.get(i);
			if(attri.equals("r") || attri.equals("w") || attri.equals("s") || attri.equals("h")){//是属性
				attrsList.add(attri);//添加属性
			}
		}
		
		String[] attrs = new String[attrsList.size()];
		attrs = attrsList.toArray(attrs);
		return attrs;
	}
	
	
}
