package com.binary.os.views;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeSelectionModel;

import com.binary.os.filesys.dentries.CurDirFile;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.dentries.Directory;
import com.binary.os.filesys.manager.FileManager;
import java.awt.BorderLayout;

public class TreePanel extends JPanel implements TreeWillExpandListener{
	
	private static final long serialVersionUID = 1L;
	
	
	private MainFrame mainFrame;
	private FileManager fm;
	private JTree tree;

	public TreePanel(FileManager fm, MainFrame mainFrame) {
		this.fm = fm;
		this.mainFrame = mainFrame;
		init();
	}
	
	public void init(){
		//根目录
		Directory root = fm.getRoot();
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(root);
		rootNode.add(new DefaultMutableTreeNode(new CurDirFile(root)));
		
		for(Dentry dentry:root.getDentryList()){//将根目录下目录项加入树
			DefaultMutableTreeNode dentryNode = new DefaultMutableTreeNode(dentry);
			rootNode.add(dentryNode);//加入子文件
			if(dentry.isFile() == false){//如果是目录的话，加一个当前目录的文件
				dentryNode.add(new DefaultMutableTreeNode(new CurDirFile((Directory) dentry)));//加“当前目录”文件
			}
		}
        tree = new JTree(rootNode);
        tree.addTreeWillExpandListener(this);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		JScrollPane treeView = new JScrollPane(tree);
		this.removeAll();
		setLayout(new BorderLayout(0, 0));
		this.add(treeView);
		this.validate();
	}

	@Override
	public void treeWillCollapse(TreeExpansionEvent arg0)
			throws ExpandVetoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeWillExpand(TreeExpansionEvent event)
			throws ExpandVetoException {
		//获得展开的节点
		DefaultMutableTreeNode dirNode = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
		//获得目录
		Directory dir = (Directory) dirNode.getUserObject();
		
		for(Dentry dentry:dir.getDentryList()){//将根目录下目录项加入树
			DefaultMutableTreeNode dentryNode = new DefaultMutableTreeNode(dentry);
			dirNode.add(dentryNode);//加入子文件
			if(dentry.isFile() == false){//如果是目录的话，加一个当前目录的文件
				dentryNode.add(new DefaultMutableTreeNode(new CurDirFile((Directory) dentry)));//加“当前目录”文件
			}
		}
		
		JOptionPane.showMessageDialog(null, dir.getStringAttri());
		
	}
}
