package com.binary.os.filesys.manager;

import java.util.ArrayList;
import java.util.LinkedList;
//import java.util.Stack;

import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.dentries.RootDirectory;
import com.binary.os.filesys.dentries.SFile;

public class FileManager {
	
	public static int BYTE_PER_BLOCK = 128;

	private DiskManager disk;
	
	private RootDirectory root;
	private LinkedList<Directory> currentPath;
	
	public FileManager(){
		disk = new DiskManager();
		//初始化根目录
		root = new RootDirectory();
		disk.readDirectory(root);
		currentPath.add(root);
	}
	
	//命令解释器
//	public String interpret(String command){
//		String[] words = command.trim().split("\\s+");//拆分成单词
//		
//	}
	
	//外部调用的时候要确认 创建文件
	public String create(String[] dirs, String fileName, boolean isOverWrite){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！文件创建失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		if(!isOverWrite){//不覆盖
			if(getCurrentDir().checkName(name, exten) == true){//同名
				return "存在重名！文件创建失败！";
			}
		}

		SFile file = new SFile();
		if(file.setName(name) == false){//文件名
			return "文件名超长或含有非法字符！文件名不得超过6个字母和数字，或3个汉字！";
		}
		if(file.setExtension(exten) == false){//后缀
			return "后缀名超长或含有非法字符！后缀名不得超过3个字母和数字！";
		}
		
		file.setAttribute(SFile.F_S_W);//文件属性初始可显示可写
		
		if(disk.saveDentry(file) == false){//保存文件
			return "磁盘空间不足！文件创建失败！";
		}
		
		if(getCurrentDir().addDentry(file) == false){//添加文件到目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return "将文件添加到目录失败！超过目录项数限制！";
		}
		
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return "磁盘空间不足！无法更新目录！文件创建失败！";
		}
		
		return fileName + " 文件创建成功！"; 
	}
	
	//复制文件
	public String copy(String[] srcDirs, String srcFileName, String[] desDirs, String desFileName){
		
		if(srcFileName.equals("")){//文件名为空
			return "源文件名为空！复制文件失败！";
		}
		
		if(desFileName.equals("")){//文件名为空
			return "目标文件名为空！复制文件失败！";
		}
		
		if(acceDirs(srcDirs) == false){//进入目录失败
			return "源目录不存在！复制文件失败！";
		}
		
		String[] stemp = srcFileName.split("\\.");//拆分文件名和后缀
		String srcName = stemp[0];//文件名
		String srcExten = stemp[1];//后缀
		if(srcExten == null){//若无后缀
			srcExten = "";
		}
		
		SFile srcFile = getCurrentDir().checkFileName(srcName, srcExten);
		if(srcFile == null){
			return "源文件不存在！复制文件失败！";
		}
		
		disk.readFile(srcFile);//读取源文件
		
		if(acceDirs(desDirs) == false){//进入目录失败
			return "目标目录不存在！复制文件失败！";
		}
		
		SFile desFile = sCreate(desFileName);//初始创建目标文件
		if(desFile == null){//创建目标文件失败
			return "无法创建目标文件！复制文件失败！";
		}
		
		desFile.setAttribute(srcFile.getAttribute());//复制属性
		desFile.setContent(srcFile.getContent());//复制文件内容
		
		if(disk.saveDentry(desFile) == false){//保存文件
			getCurrentDir().removeDentry(desFile);//当前目录移除此文件
			saveCurrentDir();//保存当前目录
			return "磁盘空间不足！无法保存目标文件！复制文件失败！";
		}
		return "复制文件成功！";
	}
	
	//删除文件
	public String delete(String[] dirs, String fileName){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！删除文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！删除文件失败！";
		}
		
		getCurrentDir().removeDentry(file);//当前目录移除此文件
		if(saveCurrentDir() == false){//保存当前目录失败
			getCurrentDir().addDentry(file);//放回目录
			saveCurrentDir();//保存当前目录
			return "无法更新目录！删除文件失败！";
		}
		
		if(disk.recycleDentry(file) == false){//回收文件失败
			getCurrentDir().addDentry(file);//放回目录
			saveCurrentDir();//保存当前目录
			return "磁盘已满！无法回收文件！删除文件失败！";
		}
		
		return fileName + " 文件删除成功！"; 
	}
		
	//移动文件
	public String move(String[] srcDirs, String srcFileName, String[] desDirs, String desFileName){
		
		if(srcFileName.equals("")){//文件名为空
			return "源文件名为空！移动文件失败！";
		}
		
		if(desFileName.equals("")){//文件名为空
			return "目标文件名为空！移动文件失败！";
		}
		
		//进入源目录
		if(acceDirs(srcDirs) == false){//进入目录失败
			return "源目录不存在！移动文件失败！";
		}
		
		LinkedList<Directory> oldPath = currentPath;//备份源目录路径
		
		String[] stemp = srcFileName.split("\\.");//拆分文件名和后缀
		String srcName = stemp[0];//文件名
		String srcExten = stemp[1];//后缀
		if(srcExten == null){//若无后缀
			srcExten = "";
		}
		
		//查源文件是否存在于源目录
		SFile srcFile = getCurrentDir().checkFileName(srcName, srcExten);
		if(srcFile == null){
			return "源文件不存在！移动文件失败";
		}
		
		//源目录移除源文件
		getCurrentDir().removeDentry(srcFile);//当前目录移除此文件
		if(saveCurrentDir() == false){//保存当前目录失败
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "无法更新源目录！移动文件失败！";
		}
		
		//进入目标目录
		if(acceDirs(desDirs) == false){//进入目录失败
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "目标目录不存在！移动文件失败";
		}
		
		//目标文件名
		stemp = desFileName.split("\\.");//拆分文件名和后缀
		String desName = stemp[0];//文件名
		String desExten = stemp[1];//后缀
		if(desExten == null){//若无后缀
			desExten = "";
		}
		
		//设目标文件名
		if(srcFile.setName(desName) == false){//目标文件名超长
			currentPath = oldPath; //回到源目录路径
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "目标文件名超长或含有非法字符！移动文件失败！";
		}
		
		//设目标后缀
		if(srcFile.setExtension(desExten) == false){//目标后缀超长
			currentPath = oldPath; //回到源目录路径
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "目标文件后缀名超长或含有非法字符！移动文件失败！";
		}
		
		//将源文件加到目标目录
		if(getCurrentDir().addDentry(srcFile) == false){//添加文件到目录失败
			currentPath = oldPath; //回到源目录路径
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "超过目标目录项数限制！移动文件失败";
		}
		
		//保存目标目录
		if(saveCurrentDir() == false){//保存当前目录失败
			getCurrentDir().removeDentry(srcFile);//移除先前的添加
			saveCurrentDir();//保存当前目录
			currentPath = oldPath; //回到源目录路径
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "无法更新目标目录！移动文件失败！";
		}
		
		return "移动文件成功！";
	}
	
	//显示文件
	public String type(String[] dirs, String fileName, boolean isForEdit){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！显示文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！显示文件失败！";
		}	
		
		//是否是为了编辑
		if(isForEdit){//是为了编辑，要判断属性
			if(file.getAttribute() == SFile.F_H_R || file.getAttribute() == SFile.F_S_W){//若文件属性是只读的
				return "文件属性为只读！无法编辑文件！";
			}
		}
		
		disk.readFile(file);//读取文件
		
		return file.toString();//返回文件文本内容
	}
	
	public String edit(String[] dirs, String fileName, String text){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！编辑文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！编辑文件失败！";
		}	
		
		if(file.getAttribute() == SFile.F_H_R || file.getAttribute() == SFile.F_S_W){//若文件属性是只读的
			return "文件属性为只读！无法编辑文件！";
		}
		
		//设置文件文本
		if(file.setText(text) == false){
			return "文本大小超过磁盘容量！保存文件失败！";
		}
		
		//保存文件
		if(disk.saveDentry(file) == false){//保存文件失败
			return "磁盘空间不足！保存文件失败！";
		}
		
		return fileName + " 文件创建成功！"; 
	}
	
	//改变文件属性
	public String change(String[] dirs, String fileName, String[] attrs){
		if(fileName.equals("")){//文件名为空
			return "文件名为空！修改文件属性失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！修改文件属性失败！";
		}	
		
		//获得旧属性
		int oldAttri = file.getAttribute();
		
		//更改属性
		for(String attri:attrs){//要更改成的属性队列
			file.changeAttri(attri);//更改属性
		}
		
		//保存文件
		if(disk.saveDentry(file) == false){//保存文件失败
			file.setAttribute(oldAttri);//恢复属性
			return "磁盘空间不足！无法保存文件修改！修改文件属性失败！";
		}
		
		String cAttri = file.getStringAttri();//获取属性信息
		
		return fileName + " 修改属性成功！  当前属性为 " + cAttri; 
	}
	
	public String format(){
		String result = "成功格式化磁盘！";
		if(!disk.format()){
			result = "格式化磁盘失败！";
		}
		disk.readDirectory(root);
		return result;
	}
	
	//创建目录
	public String makdir(String[] dirs){
		
		ArrayList<String> unCreatedDirs = checkDirs(dirs);//获得未创建目录树
		if(unCreatedDirs.size() == dirs.length){//需要创建的目录树都已存在
			return "需要创建的目录都已存在，不需要再创建！";
		}
		
		for(String dirName:unCreatedDirs){//循环创建未创建目录

			//检查同名
			if(getCurrentDir().checkName(dirName, "") == true){//同名
				return "存在重名！" + dirName + "文件夹创建失败！";
			}
			
			//覆文件夹名
			Directory dir = new Directory();
			if(dir.setName(dirName) == false){//文件夹名
				return "文件夹名" + dirName + "超长或含有非法字符！文件夹名不得超过6个字母和数字，或3个汉字！";
			}
			
			dir.setAttribute(SFile.D_S_W);//文件夹属性初始可显示可写
			
			//保存文件夹
			if(disk.saveDentry(dir) == false){//保存文件夹
				return "磁盘空间不足！文件夹" + dirName + "创建失败！";
			}
			
			//添加文件夹到当前目录
			if(getCurrentDir().addDentry(dir) == false){//添加文件夹到目录失败
				disk.recycleDentry(dir);//回收为文件夹分配的盘块
				return "将文件夹" + dirName + "添加到目录失败！超过目录项数限制！";
			}
			
			//保存当前目录
			if(saveCurrentDir() == false){//保存当前目录失败
				disk.recycleDentry(dir);//回收为文件夹分配的盘块
				return "磁盘空间不足！无法更新目录！文件夹" + dirName + "创建失败！";
			}
			
			currentPath.add(dir);//添加到当前路径中，更新当前目录为已创建目录
		}
		
		return "文件夹创建成功！"; 
	}
	
	//更改当前目录
	public String chadir(String[] dirs){
		if(acceDirs(dirs) == false){//检查目录树失败
			return "目录不存在！";
		}
		return "目录检索成功！";
	}
	
//	//删除空目录
//	public String rdir(String[] dirs){
//		LinkedList<Directory> oldPath = currentPath;
//		
//		if(dirs[0].equals("root:")){//为绝对地址
//			if(dirs.length ==1){//只有根目录
//				return "无法删除根目录！";
//			}
//			currentPath.clear();//清除当前目录
//			currentPath.add(root);//设当前目录为root
//		}
//		
//		String sDir = null;
//		for(int i=0; i<dirs.length-1; i++){//检索到要删除目录的父目录
//			sDir = dirs[i];
//			if(sDir.equals("root:")){//忽略root地址
//				continue;
//			}
//			Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
//			if(tDir != null){//存在此名字目录
//				currentPath.add(tDir);//更新当前目录
//			}else{//不存在
//				currentPath = oldPath;//恢复旧当前目录
//				return "路径不存在！删除目录失败！";
//			}
//		}
//		sDir = dirs[dirs.length-1];//最后一个目录就是要删除的目录
//		Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
//		if(tDir != null){//存在此名字目录
//			if(currentPath.equals(oldPath)){//等于原来当前目录
//				return "无法删除当前目录！";
//			}
//			if(tDir.getSize() != 0){//不是空目录
//				currentPath = oldPath;//恢复旧当前目录
//				return "目录" + dirs[dirs.length-1] + "不是空目录！无法删除！";
//			}
//			
//		}else{//不存在
//			currentDir = oldDir;//恢复旧当前目录
//			return "路径不存在！删除目录失败！";
//		}
//		return true;
//	}
//	
//	public String deldir(){
//		
//	}
//	
//	public boolean[] getUsage(){
//		return disk.getUsage();
//	}
	
	
	//检索目录树，进入目录
	public boolean acceDirs(String[] dirs){
		LinkedList<Directory> oldPath = currentPath;
		
		if(dirs[0].equals("root:")){//为绝对地址
			currentPath.clear();//清除当前目录
			currentPath.add(root);//设当前目录为root
		}
		
		for(String sDir:dirs){
			if(sDir.equals("root:")){//忽略root地址
				continue;
			}
			Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
			if(tDir != null){//存在此名字目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				currentPath = oldPath;//恢复旧当前目录
				return false;
			}
		}
		return true;
	}
	
	//检索目录树，进入已存在目录，获得不存在的目录树
	public ArrayList<String> checkDirs(String[] dirs){
		boolean notExist = false;
		ArrayList<String> unCreatedDirs = new ArrayList<String>();//存放还未创建的目录
		
		if(dirs[0].equals("root:")){//为绝对地址
			currentPath.clear();//清除当前目录
			currentPath.add(root);//设当前目录为root
		}
		
		for(String sDir:dirs){
			if(sDir.equals("root:")){//忽略root地址
				continue;
			}
			if(notExist){//若已经是不存在的目录
				unCreatedDirs.add(sDir);//放入还未创建的目录
				continue;
			}
			Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
			if(tDir != null){//存在此名字目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				notExist = true;
				unCreatedDirs.add(sDir);//放入还未创建的目录
			}
		}
		return unCreatedDirs;
	}

	public SFile sCreate(String fileName){
		
		if(fileName.equals("")){//文件名为空
			return null;
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = stemp[1];//后缀
		if(exten == null){//若无后缀
			exten = "";
		}
		
		if(getCurrentDir().checkName(name, exten) == true){//同名
			return null;
		}


		SFile file = new SFile();
		if(file.setName(name) == false){//文件名
			return null;
		}
		if(file.setExtension(exten) == false){//后缀
			return null;
		}
		
		file.setAttribute(SFile.F_S_W);//文件属性初始可显示可写
		
		if(disk.saveDentry(file) == false){//保存文件
			return null;
		}
		
		if(getCurrentDir().addDentry(file) == false){//添加文件到目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return null;
		}
		
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return null;
		}
		
		return file;
	}
	
	public Directory sMakdir(String dirName){
		
		if(dirName.equals("")){//文件夹名为空
			return null;
		}
		
		if(getCurrentDir().checkName(dirName, "") == true){//同名
			return null;
		}

		Directory dir = new Directory();
		if(dir.setName(dirName) == false){//文件夹名
			return null;
		}
		
		dir.setAttribute(SFile.D_S_W);//文件夹属性初始可显示可写
		

		
		if(disk.saveDentry(dir) == false){//保存文件夹
			return null;
		}
		
		if(getCurrentDir().addDentry(dir) == false){//添加文件夹到目录失败
			disk.recycleDentry(dir);//回收为文件夹分配的盘块
			return null;
		}
		
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(dir);//回收为文件夹分配的盘块
			return null;
		}
		
		return dir;
	}
	
	public boolean saveCurrentDir(){
		if(getCurrentDir() == root){//若当前目录为根目录
			if(disk.saveRoot(root) == false){//保存根目录失败
				return false;
			}
		}else{//非根目录
			if(disk.saveDentry(getCurrentDir()) == false){//保存目录失败
				return false;
			}
		}
		return true;
	}
	
	public Directory getCurrentDir(){
		return currentPath.getLast();//路径最后一个就是当前目录
	}
	
	//获取当前目录路径名
	public String getStringCurrentPath(){
		String path = "";
		for(Directory dir:currentPath){
			path = path + dir.getName() + "\\";
		}
		return path;
	}
	
	public RootDirectory getRoot() {
		return root;
	}

	public DiskManager getDisk() {
		return disk;
	}

}
