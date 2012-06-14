package com.binary.os.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import com.binary.os.filesys.manager.FileManager;

public class CmdPanel extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private FileManager fm = null;
	private String lastCommand = "";
	
	public JScrollPane resultScrolPane;
	public JTextArea resultsText;
	public JLabel currDirLabel;
	public JTextField cmdText;

	/**
	 * Create the panel.
	 */
	public CmdPanel(FileManager fm, MainFrame mainFrame) {
		this.fm = fm;
		this.mainFrame = mainFrame;
		
		setBounds(224, 287, 550, 432);
		setOpaque(false);
		setLayout(new BorderLayout(0, 0));
		
		resultScrolPane = new JScrollPane();
		add(resultScrolPane);
		resultScrolPane.setBounds(0, 0, 550, 402);
		resultScrolPane.setOpaque(false);
		resultScrolPane.setBorder(new MatteBorder(2, 2, 2, 2, new Color(240, 240, 240)));
		
		resultsText = new JTextArea();
		resultsText.setLineWrap(true);
		resultScrolPane.setViewportView(resultsText);
		resultsText.setFont(new Font("微软雅黑", Font.BOLD, 15));
		resultsText.setEditable(false);
		resultsText.setOpaque(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 402, 550, 30);
		add(panel, BorderLayout.SOUTH);
		panel.setOpaque(false);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(new MatteBorder(0, 2, 2, 2, new Color(240, 240, 240)));
		
		currDirLabel = new JLabel("New Label");
		currDirLabel.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 18));
		panel.add(currDirLabel, BorderLayout.WEST);
		currDirLabel.setForeground(Color.RED);
		
		cmdText = new JTextField();
		panel.add(cmdText, BorderLayout.CENTER);
		cmdText.setFont(new Font("微软雅黑", Font.BOLD | Font.ITALIC, 18));
		cmdText.setForeground(Color.RED);
		cmdText.setBorder(null);
		cmdText.setOpaque(false);
		cmdText.setColumns(10);
		cmdText.addKeyListener(this);

		currDirLabel.setText(fm.getStringCurrentPath() + ">");
		resultScrolPane.getViewport().setOpaque(false); 
	}

	public void scroll(){
		resultScrolPane.validate();
		JScrollBar sBar = resultScrolPane.getVerticalScrollBar();
		sBar.setValue(sBar.getMaximum());//滚动条到底部
		resultScrolPane.validate();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {//回车键
			String command = cmdText.getText();//命令
			lastCommand = command;//保存命令
			resultsText.append(fm.getStringCurrentPath() + ">" + command + "\n");//加命令
			cmdText.setText("");//命令框清空
			
			String result = fm.interpret(command);//运行命令
			
			resultsText.append(result+"\n\n");//显示结果
			scroll();//滚动条到底部
			currDirLabel.setText(fm.getStringCurrentPath() + ">");
			mainFrame.dirTreePanel.refresh();
		}else if (e.getKeyCode() == KeyEvent.VK_UP) {
			cmdText.setText(lastCommand);//获取上一条指令
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}