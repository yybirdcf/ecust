package com.binary.os.filesys.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.dentries.RootDirectory;
import com.binary.os.filesys.dentries.SFile;
import com.binary.os.kernel.ProcessManager;
import com.binary.os.views.EditTextDialog;
import com.binary.os.views.ShowTextDialog;

public class FileManager {
	
	public static int BYTE_PER_BLOCK = 128;

	private DiskManager disk;
	private RootDirectory root;
	private LinkedList<Directory> currentPath;
	
	private LinkedList<Directory>[] processFileAddr;
	private String[] processFileName;
	
	@SuppressWarnings("unchecked")
	public FileManager(){
		disk = new DiskManager();
		//初始化根目录
		root = new RootDirectory();
		disk.readDirectory(root);
		currentPath = new LinkedList<Directory>();
		currentPath.add(root);
		processFileAddr = (LinkedList<Directory>[]) new LinkedList[10];
		processFileName = new String[10];
	}
	
	
	//命令解释
	public String interpret(String commands){
		Interpreter cmd = new Interpreter(commands);//命令解释器
		
		String operation = cmd.getOperat();
		
		//创建文件
		if(operation.equals("create")){
			return create(cmd.getDirs(), cmd.getFileName());
		}
		
		//创建并编辑文件
		if(operation.equals("vi")){
			return vi(cmd.getDirs(), cmd.getFileName());
		}
		
		//拷贝文件
		if(operation.equals("copy")){
			return copy(cmd.getSrcDirs(), cmd.getSrcFileName(), cmd.getDesDirs() , cmd.getDesFileName());
		}

		//删除文件
		if(operation.equals("delete")){			
			return delete(cmd.getDirs(), cmd.getFileName());
		}
		
		//移动文件
		if(operation.equals("move")){	
			return move(cmd.getSrcDirs(), cmd.getSrcFileName(), cmd.getDesDirs() , cmd.getDesFileName());
		}
		
		//显示文件
		if(operation.equals("type")){
			return type(cmd.getDirs(), cmd.getFileName(), false);
		}
		
		//编辑文件
		if(operation.equals("edit")){
			return type(cmd.getDirs(), cmd.getFileName(), true);
		}
		
		//改变文件属性
		if(operation.equals("change")){
			return change(cmd.getDirs(), cmd.getFileName(), cmd.getAttrs());
		}
		
		//格式化
		if(operation.equals("format")){
			return format();
		}
			
		//创建目录
		if(operation.equals("makdir")){
			return makdir(cmd.getDirPath());
		}
		
		//更改当前目录
		if(operation.equals("chadir")){
			return chadir(cmd.getDirPath());
		}
		
		//删除空目录
		if(operation.equals("rdir")){
			return rdir(cmd.getDirPath());
		}
		
		//删除目录
		if(operation.equals("deldir")){
			return deldir(cmd.getDirPath());
		}
		
		//运行文件
		if(operation.equals("run")){
			return run(cmd.getDirs(), cmd.getFileName());
		}
		
		//刷新
		if(operation.equals("refresh")){
			return refresh();
		}
		
		
		return "不是合法的命令！";
	}
	
	//外部调用的时候要确认 创建文件
	private String create(String[] dirs, String fileName){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！文件创建失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		if(getCurrentDir().checkName(name, exten) == true){//同名
			SFile cFile = getCurrentDir().checkFileName(name, exten);
			if(cFile == null){
				return "存在目录与此文件重名！文件创建失败！";
			}
			
			int result = JOptionPane.showConfirmDialog(null, "存在文件重名！是否覆盖？", fileName, JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION){//覆盖
				getCurrentDir().removeDentry(cFile);//当前目录移除此文件
				if(saveCurrentDir() == false){//保存当前目录失败
					getCurrentDir().addDentry(cFile);//放回目录
					saveCurrentDir();//保存当前目录
					return "无法更新目录！覆盖文件失败！";
				}
				
				if(disk.recycleDentry(cFile) == false){//回收文件失败
					getCurrentDir().addDentry(cFile);//放回目录
					saveCurrentDir();//保存当前目录
					return "磁盘已满！无法回收文件！覆盖文件失败！";
				}
			}else{
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
		
		saveAllDirs();//保存全部路径
		
		return fileName + " 文件创建成功！"; 
	}
	
	//创建并编辑文件
	private String vi(String[] dirs, String fileName){
		//创建文件
		String result = create(dirs, fileName);
		if(result.equals(fileName + " 文件创建成功！") == false){//创建文件失败
			return result;
		}
		
		//编辑文件
		result = type(dirs, fileName, true);
		
		return result;
	}
	
	//复制文件
	private String copy(String[] srcDirs, String srcFileName, String[] desDirs, String desFileName){
		
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
		String srcExten = "";//后缀
		if(stemp.length > 1){//存在后缀
			srcExten = stemp[1];//后缀
		}
		
		SFile srcFile = getCurrentDir().checkFileName(srcName, srcExten);
		if(srcFile == null){
			return "源文件不存在！复制文件失败！";
		}
		
		disk.readFile(srcFile);//读取源文件
		
		if(acceDirs(desDirs) == false){//进入目录失败
			return "目标目录不存在！复制文件失败！";
		}
		
		//创建空文件
		SFile desFile = sCreate(desFileName, false);//初始创建目标文件
		if(desFile == null){//创建目标文件失败
			return "无法创建目标文件！复制文件失败！";
		}
		
		desFile.setSize(srcFile.getSize());//复制文件大小属性
		desFile.setAttribute(srcFile.getAttribute());//复制属性
		desFile.setContent(srcFile.getContent());//复制文件内容
		
		if(disk.saveDentry(desFile) == false){//保存文件
			return "磁盘空间不足！无法保存目标文件！复制文件失败！";
		}
		
		//保存目录
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(desFile);//回收为文件分配的盘块
			getCurrentDir().removeDentry(desFile);//删除目录项
			return "磁盘空间不足！无法更新目录！文件创建失败！";
		}
		
		saveAllDirs();//保存全部路径
		
		return "复制文件成功！";
	}
	
	//删除文件
	private String delete(String[] dirs, String fileName){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！删除文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
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
		
		saveAllDirs();//保存全部路径
		
		return fileName + " 文件删除成功！"; 
	}
		
	//移动文件
	private String move(String[] srcDirs, String srcFileName, String[] desDirs, String desFileName){
		
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
		
		LinkedList<Directory> oldPath = new LinkedList<Directory>(currentPath);//浅拷贝//备份源目录路径
		
		String[] stemp = srcFileName.split("\\.");//拆分文件名和后缀
		String srcName = stemp[0];//文件名
		String srcExten = "";//后缀
		if(stemp.length > 1){//存在后缀
			srcExten = stemp[1];//后缀
		}
		
		//查源文件是否存在于源目录
		SFile srcFile = getCurrentDir().checkFileName(srcName, srcExten);
		if(srcFile == null){
			return "源文件不存在！移动文件失败！";
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
			return "目标目录不存在！移动文件失败！";
		}
		
		//目标文件名
		stemp = desFileName.split("\\.");//拆分文件名和后缀
		String desName = stemp[0];//文件名
		String desExten = "";//后缀
		if(stemp.length > 1){//存在后缀
			desExten = stemp[1];//后缀
		}
		
		//检查重名
		if(getCurrentDir().checkName(desName, desExten) == true){//同名
			currentPath = oldPath; //回到源目录路径
			getCurrentDir().addDentry(srcFile);//放回目录
			saveCurrentDir();//保存当前目录
			return "目标目录存在同名文件夹或文件！移动文件失败！";
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
			return "超过目标目录项数限制！移动文件失败！";
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
		
		saveAllDirs();//保存全部路径
		
		return "移动文件成功！";
	}
	
	//显示文件
	private String type(String[] dirs, String fileName, boolean isForEdit){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！显示文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！显示文件失败！";
		}	
		
		disk.readFile(file);//读取文件
		
		//是否是为了编辑
		if(isForEdit){//是为了编辑，要判断属性
			if(file.getAttribute() == SFile.F_H_R || file.getAttribute() == SFile.F_S_R){//若文件属性是只读的
				return "文件属性为只读！无法编辑文件！";
			}
			new EditTextDialog(fileName, file.toString(), this);//编辑文件
			return "编辑文件结束！";
		}else{
			new ShowTextDialog(fileName, file.toString());//显示文件
			return "显示文件成功！";
		}
		
		
	}
	
	//编辑文件
	public String edit(String fileName, String text){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！编辑文件失败！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！编辑文件失败！";
		}	
		
		if(file.getAttribute() == SFile.F_H_R || file.getAttribute() == SFile.F_S_R){//若文件属性是只读的
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
		
		//保存目录
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return "磁盘空间不足！无法更新目录！保存文件失败！";
		}
		
		saveAllDirs();//保存全部路径
		
		return fileName + " 文件保存成功！ 文件大小为" + file.getSize() + "字节"; 
	}
		
	//改变文件属性
	private String change(String[] dirs, String fileName, String[] attrs){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！修改文件属性失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！修改文件属性失败！";
		}	
		
		if(attrs.length == 0){//没有要覆的属性
			return fileName + " 没有要修改的属性！  当前属性为 " + file.getStringAttri();
		}
		
		//更改属性
		for(String attri:attrs){//要更改成的属性队列
			file.changeAttri(attri);//更改属性
		}
		
		//保存目录
		if(saveCurrentDir() == false){//保存当前目录失败
			disk.recycleDentry(file);//回收为文件分配的盘块
			return "磁盘空间不足！无法更新目录！保存文件失败！";
		}
		
		String cAttri = file.getStringAttri();//获取属性信息
		
		return fileName + " 修改属性成功！  当前属性为 " + cAttri; 
	}
	
	private String format(){
		String result = "成功格式化磁盘！";
		if(!disk.format()){
			result = "格式化磁盘失败！";
		}
		disk.readDirectory(root);
		currentPath.clear();//清除当前路径
		currentPath.add(root);
		return result;
	}
	
	//创建目录
	private String makdir(String[] dirs){
		
		ArrayList<String> unCreatedDirs = checkDirs(dirs);//获得未创建目录树
		if(unCreatedDirs.size() == 0){//需要创建的目录树都已存在
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
		saveAllDirs();//保存全部路径
		
		return getCurrentDir().getName() + "文件夹创建成功！"; 
	}
	
	//更改当前目录
	private String chadir(String[] dirs){
		if(acceDirs(dirs) == false){//检查目录树失败
			return "目录不存在！";
		}
		return "进入目录成功！";
	}
	
	//删除空目录
	private String rdir(String[] dirs){
		if(dirs.length == 0){//不存在目录树
			return "没有输入要删除的目录！";
		}
		
		LinkedList<Directory> oldPath = new LinkedList<Directory>(currentPath);//浅拷贝
		
		//检查是不是根目录
		if(dirs[0].equals("root:")){//为绝对地址
			if(dirs.length ==1){//只有根目录
				return "无法删除根目录！";
			}
			currentPath.clear();//清除当前目录
			currentPath.add(root);//设当前目录为root
		}
		
		//检索路径
		String sDir = null;
		for(int i=0; i<dirs.length-1; i++){//检索到要删除目录的父目录
			sDir = dirs[i];
			if(sDir.equals("root:")){//忽略root地址
				continue;
			}
			Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
			if(tDir != null){//存在此名字目录
				disk.readDirectory(tDir);//读取目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				currentPath = oldPath;//恢复旧当前目录
				return "路径不存在！删除目录失败！";
			}
		}
		//获得目录
		sDir = dirs[dirs.length-1];//最后一个目录就是要删除的目录
		Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
		
		//检查目录是否存在
		if(tDir == null){//目录不存在
			currentPath = oldPath;//恢复旧当前目录
			return "目录不存在！删除目录失败！";
		}
		
		//检查是否是当前目录
		currentPath.add(tDir);//暂时先把要删的目录加到当前路径
		if(currentPath.equals(oldPath)){//等于原来当前目录
			return "无法删除当前目录！";
		}else{//恢复
			currentPath.pollLast();
		}
		
		//检查是不是空目录
		if(tDir.getSize() != 0){//不是空目录
			currentPath = oldPath;//恢复旧当前目录
			return "目录" + dirs[dirs.length-1] + "不是空目录！无法删除！";
		}
		
		//将目录从父目录删除，并保存父目录
		getCurrentDir().removeDentry(tDir);//当前目录移除此目录
		if(saveCurrentDir() == false){//保存当前目录失败
			getCurrentDir().addDentry(tDir);//放回目录
			saveCurrentDir();//保存当前目录
			return "无法更新父目录！删除目录失败！";
		}
		
		//回收目录所占空间
		if(disk.recycleDentry(tDir) == false){//回收目录失败
			getCurrentDir().addDentry(tDir);//放回目录
			saveCurrentDir();//保存当前目录
			return "磁盘已满！无法回收文件！删除文件失败！";
		}
		
		saveAllDirs();//保存全部路径
			
		return sDir + " 目录删除成功！"; 
	}
	
	//删除目录并删除子文件
	private String deldir(String[] dirs){
		if(dirs.length == 0){//不存在目录树
			return "没有输入要删除的目录！";
		}
		
		LinkedList<Directory> oldPath = new LinkedList<Directory>(currentPath);
		
		//检查是不是根目录
		if(dirs[0].equals("root:")){//为绝对地址
			if(dirs.length ==1){//只有根目录
				return "无法删除根目录！";
			}
			currentPath.clear();//清除当前目录
			currentPath.add(root);//设当前目录为root
		}
		
		//检索路径
		String sDir = null;
		for(int i=0; i<dirs.length-1; i++){//检索到要删除目录的父目录
			sDir = dirs[i];
			if(sDir.equals("root:")){//忽略root地址
				continue;
			}
			Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
			if(tDir != null){//存在此名字目录
				disk.readDirectory(tDir);//读取目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				currentPath = oldPath;//恢复旧当前目录
				return "路径不存在！删除目录失败！";
			}
		}
		//获得目录
		sDir = dirs[dirs.length-1];//最后一个目录就是要删除的目录
		Directory tDir = getCurrentDir().checkDirName(sDir);//查文件夹名
		
		//检查目录是否存在
		if(tDir == null){//目录不存在
			currentPath = oldPath;//恢复旧当前目录
			return "目录不存在！删除目录失败！";
		}
		
		//检查是否是当前目录
		currentPath.add(tDir);//暂时先把要删的目录加到当前路径
		if(currentPath.equals(oldPath)){//等于原来当前目录
			return "无法删除当前目录！";
		}else{//恢复
			currentPath.pollLast();
		}
		
		deleSub(tDir);//删除目录及子目录项
		getCurrentDir().removeDentry(tDir);//当前目录移除要删除目录的FCB
		saveAllDirs();//保存全部路径
			
		return sDir + " 目录删除成功！"; 
	}
	
	//执行可执行文件
	private String run(String[] dirs, String fileName){
		
		if(fileName.equals("")){//文件名为空
			return "文件名为空！运行文件失败！";
		}
		
		if(acceDirs(dirs) == false){//进入目录失败
			return "目录不存在！";
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		//检查后缀
		if(exten.equals("exe") == false){//不是可执行文件
			return "文件不是可执行文件！";
		}
		
		//获取文件
		SFile file = getCurrentDir().checkFileName(name, exten);
		if(file == null){
			return "文件不存在！运行文件失败！";
		}	
		
		disk.readFile(file);//读取文件
		
		//创建进程
		int result = ProcessManager.Create(file.getContent());
		if(result == -1){
			return "运行文件失败！无法创建进程！内存空间不足！";
		}
		if(result == -2){
			return "运行文件失败！存在语法错误！";
		}
		if(result == -3){
			return "运行文件失败！无法创建进程！进程数量已为最大！";
		}
		
		processFileAddr[result] = new LinkedList<Directory>(currentPath);//浅拷贝//将进程的运行文件的路径保存
		processFileName[result] = fileName;//保存文件名
		
		
		return "成功运行文件" + fileName + "！ 创建的进程的PID为" + result;
	}
	
	//检索目录树，进入目录
	private boolean acceDirs(String[] dirs){
		if(dirs.length == 0){//不存在目录树
			return true;
		}
		LinkedList<Directory> oldPath = new LinkedList<Directory>(currentPath);//浅拷贝
		
		if(dirs[0].equals("..")){//上一级目录
			if(getCurrentDir() == root){
				return true;
			}
			currentPath.pollLast();//向上一级
			return true;
		}
		
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
				disk.readDirectory(tDir);//读取目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				currentPath = oldPath;//恢复旧当前目录
				return false;
			}
		}
		return true;
	}
	
	//检索目录树，进入已存在目录，获得不存在的目录树
	private ArrayList<String> checkDirs(String[] dirs){
		boolean notExist = false;
		ArrayList<String> unCreatedDirs = new ArrayList<String>();//存放还未创建的目录
		
		if(dirs.length == 0){//不存在目录树
			return unCreatedDirs;
		}
		
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
				disk.readDirectory(tDir);//读取目录
				currentPath.add(tDir);//更新当前目录
			}else{//不存在
				notExist = true;
				unCreatedDirs.add(sDir);//放入还未创建的目录
			}
		}
		return unCreatedDirs;
	}

	//在当前目录创建文件
	private SFile sCreate(String fileName, boolean isOverWrite){
		
		if(fileName.equals("")){//文件名为空
			return null;
		}
		
		String[] stemp = fileName.split("\\.");//拆分文件名和后缀
		String name = stemp[0];//文件名
		String exten = "";//后缀
		if(stemp.length > 1){//存在后缀
			exten = stemp[1];//后缀
		}
		
		if(getCurrentDir().checkName(name, exten) == true){//同名
			SFile cFile = getCurrentDir().checkFileName(name, exten);
			if(cFile == null){
				return null;
			}
			
			if(isOverWrite){//覆盖
				getCurrentDir().removeDentry(cFile);//当前目录移除此文件
				if(saveCurrentDir() == false){//保存当前目录失败
					getCurrentDir().addDentry(cFile);//放回目录
					saveCurrentDir();//保存当前目录
					return null;
				}
				
				if(disk.recycleDentry(cFile) == false){//回收文件失败
					getCurrentDir().addDentry(cFile);//放回目录
					saveCurrentDir();//保存当前目录
					return null;
				}
			}else{
				return null;
			}
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
		
		saveAllDirs();//保存全部路径
		
		return file;
	}
	
	//删除目录下的子项，递归法
	private void deleSub(Directory dir){
		disk.readDirectory(dir);//读取目录
		for(Dentry dentry:dir.getDentryList()){//取出所有目录项
			if(dentry.isFile()){//如果是文件就删除
				disk.recycleDentry(dentry);
			}else{//是目录的话递归此方法
				deleSub((Directory)dentry);
			}
		}
		disk.recycleDentry(dir);//回收目录
	}
	
	private boolean saveCurrentDir(){
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
	
	//保存当前路径到根目录的所有目录，倒着保存
	private void saveAllDirs(){
		for(int i=currentPath.size()-1; i>0; i--){//保存除root目录外
			disk.saveDentry(currentPath.get(i));//保存目录
		}
		disk.saveRoot((RootDirectory)currentPath.get(0));//第一个一定是root目录
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
	
	//获得磁盘使用情况
	public boolean[] getUsage(){
		return disk.getUsage();
	}
	
	//保存out文件
	public boolean saveOut(int pid, byte result){
		LinkedList<Directory> oldPath = new LinkedList<Directory>(currentPath);//浅拷贝当前路径
		
		LinkedList<Directory> outPath  = processFileAddr[pid];//获得进程对应的路径
		if(outPath == null){//路径不存在就保存结果失败
			return false;
		}
		
		currentPath = outPath;
		
		SFile outFile = sCreate("out", true);//保存out文件
		if(outFile == null){
			return false;
		}
		
		String text = "执行文件路径为：" + getStringCurrentPath() + "\n结果为：x=" + result;
		
		//设置文件文本
		if(outFile.setText(text) == false){
			return false;
		}
		
		//保存文件
		if(disk.saveDentry(outFile) == false){//保存文件失败
			return false;
		}
		
		//保存目录
		if(saveCurrentDir() == false){//保存当前目录失败
			getCurrentDir().removeDentry(outFile);
			disk.recycleDentry(outFile);//回收为文件分配的盘块
			return false;
		}
		
		saveAllDirs();//保存全部路径
		
		currentPath = oldPath;//恢复当前路径
		
		return true;
	}
	
	//刷新 状态
	public String refresh(){
		//刷新磁盘，重新读取超级块
		disk.refresh();
		//重新读取根目录
		disk.readDirectory(root);
		
		
		String path = getStringCurrentPath();//当前目录String化
		//当前目录回到根目录
		this.currentPath.clear();
		this.currentPath.add(root);
		
		//调用命令解释器，来获得目录树
		Interpreter iper = new Interpreter("chadir " + path);
		//检查路径
		checkDirs(iper.getDirs());

		return "已刷新！";
	}
	
//	public RootDirectory getRoot() {
//		return root;
//	}
//
//	public DiskManager getDisk() {
//		return disk;
//	}
}
