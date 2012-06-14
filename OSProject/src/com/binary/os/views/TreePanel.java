package com.binary.os.views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.binary.os.filesys.dentries.CurDirFile;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.manager.FileManager;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class TreePanel extends JPanel implements TreeWillExpandListener,TreeSelectionListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	
	private MainFrame mainFrame;
	private FileManager fm;
	private JTree tree;

	public TreePanel(FileManager fm, MainFrame mainFrame) {
		this.fm = fm;
		this.mainFrame = mainFrame;
		refresh();
	}
	
	//初始化
	public void init(){
		//根目录
		Directory root = fm.getRoot();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
		rootNode.add(new DefaultMutableTreeNode(new CurDirFile(root)));
		
		for(Dentry dentry:root.getDentryList()){//将根目录下目录项加入树
			if(dentry.isHide()){//不显示隐藏文件
				continue;
			}
			DefaultMutableTreeNode dentryNode = new DefaultMutableTreeNode(dentry);
			rootNode.add(dentryNode);//加入子文件
			if(dentry.isFile() == false){//如果是目录的话，加一个当前目录的文件
				dentryNode.add(new DefaultMutableTreeNode(new CurDirFile((Directory) dentry)));//加“当前目录”文件
			}
		}
        tree = new JTree(rootNode);
        tree.addTreeWillExpandListener(this);
        tree.addTreeSelectionListener(this);
        tree.addMouseListener(this);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane treeView = new JScrollPane(tree);
		this.removeAll();
		setLayout(new BorderLayout(0, 0));
		this.add(treeView);
		this.validate();
	}
	
	//刷新
	public void refresh(){
		
		//浅拷贝当前路径队列
		LinkedList<Directory> currentPath = new LinkedList<Directory>(fm.getCurrentPath());
		
		//初始化
		init();

		//当前行数
		int count = tree.getRowCount();
		//旧行数
		int oldcount = 0;
		//如果新行数不等旧行数 循环展开
		while(oldcount!=count){
			for(int i=0; i<count; i++){
				tree.expandRow(i);//展开
			}
			oldcount = count;
			count = tree.getRowCount();
		}
		
		//恢复路径
		fm.setCurrentPath(currentPath);
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent arg0)
			throws ExpandVetoException {
		
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent event)
			throws ExpandVetoException {
		
		//获得节点路径
		TreePath treePath = event.getPath();
		
		//获得节点数组
		Object[] dirNodes = treePath.getPath();
		
		//获得路径名数组
		String[] dirs = new String[dirNodes.length];
		for(int i=0; i<dirNodes.length; i++){
			DefaultMutableTreeNode dirNode = (DefaultMutableTreeNode) dirNodes[i];
			Directory dir = (Directory) dirNode.getUserObject();
			dirs[i] = dir.getName();
		}
		
		//进入点开的目录
		fm.acceDirs(dirs);
		
		//获得当前目录
		Directory currentDir = fm.getCurrentDir();
			
		//获得展开的节点
		DefaultMutableTreeNode currentDirNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();

		//将当前目录下目录项加入树
		for(Dentry dentry:currentDir.getDentryList()){
			if(dentry.isHide()){//不显示隐藏文件
				continue;
			}
			DefaultMutableTreeNode dentryNode = new DefaultMutableTreeNode(dentry);
			currentDirNode.add(dentryNode);//加入子文件
			if(dentry.isFile() == false){//如果是目录的话，加一个当前目录的文件
				dentryNode.add(new DefaultMutableTreeNode(new CurDirFile((Directory) dentry)));//加“当前目录”文件
			}
		}
		
	}
	
	

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode note = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent(); 
		Dentry dentry = (Dentry) note.getUserObject();

		fm.setInfo(dentry);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			TreePath path = tree.getSelectionPath();// 获取选中节点路径
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();// 通过路径将指针指向该节点
			if (node.isLeaf())// 如果该节点是叶子节点
			{
				if(((Dentry)node.getUserObject()).equals("此级目录")){
					return;
				}
				String spath = "";
				Object[] nodes = path.getPath();
				Dentry dentry = null;
				for (Object n : nodes) {
					try{
						dentry = (Dentry) ((DefaultMutableTreeNode) n).getUserObject();
					}catch(Exception e1){
						e1.printStackTrace();
						return;
					}
					spath = spath + dentry.getFullName() + "\\";
				}
				if(dentry.getExtension().equals("exe")){
					String result = fm.interpret("run " + spath);
					mainFrame.cmdPanel.resultsText.append(result+"\n\n");//显示结果
					mainFrame.cmdPanel.scroll();//滚动条到底部
					mainFrame.cmdPanel.currDirLabel.setText(fm.getStringCurrentPath() + ">");
					mainFrame.dirTreePanel.refresh();
					mainFrame.diskUsagePanel.repaint();
				}else{
					String result = fm.interpret("edit " + spath);
					mainFrame.cmdPanel.resultsText.append(result+"\n\n");//显示结果
					mainFrame.cmdPanel.scroll();//滚动条到底部
					mainFrame.cmdPanel.currDirLabel.setText(fm.getStringCurrentPath() + ">");
					mainFrame.dirTreePanel.refresh();
					mainFrame.diskUsagePanel.repaint();
				}
			}

		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
