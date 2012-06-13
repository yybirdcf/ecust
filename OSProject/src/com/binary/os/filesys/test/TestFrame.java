package com.binary.os.filesys.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.manager.FileManager;
import com.binary.os.kernel.ClockControl;

import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TestFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileManager fm = null;
	private String lastCommand = "";
	
	private JTextArea dirText;
	private JTextArea dentryText;
	private JTextArea resultText;
	private JTextArea usageText;
	private JTextField commandText;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	public TestFrame() {
		
		fm = new FileManager();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		resultText = new JTextArea();
		resultText.setEditable(false);
		resultText.setLineWrap(true);
		resultText.setBounds(10, 263, 454, 255);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 263, 454, 255);
		scrollPane.setViewportView(resultText);
		panel.add(scrollPane);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 5, 456, 24);
		panel.add(scrollPane_2);
		
		dirText = new JTextArea();
		scrollPane_2.setViewportView(dirText);
		dirText.setEditable(false);
		
		dirText.setText(fm.getStringCurrentPath());
		
		dentryText = new JTextArea();
		dentryText.setLineWrap(true);
		dentryText.setBounds(10, 39, 456, 57);
		dentryText.setEditable(false);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 39, 456, 57);
		scrollPane2.setViewportView(dentryText);
		panel.add(scrollPane2);
		
		getContentPane().add(panel, BorderLayout.CENTER);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 114, 454, 139);
		panel.add(scrollPane_1);
	
		usageText = new JTextArea();
		usageText.setLineWrap(true);
		scrollPane_1.setViewportView(usageText);
		usageText.setEditable(false);
		
		commandText = new JTextField();
		commandText.setRequestFocusEnabled(true);
		commandText.setBounds(10, 528, 454, 21);
		panel.add(commandText);
		commandText.setColumns(10);
		commandText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String command = commandText.getText();
					lastCommand = command;
					resultText.append(command+"\n");
					commandText.setText("");
					String result = fm.interpret(command);
					resultText.append(result+"\n");
					dirText.setText(fm.getStringCurrentPath());
					String dirs = "";
					ArrayList<Dentry> dentries = fm.getCurrentDir().getDentryList();
					for(Dentry den:dentries){
						String isFile = "文件夹";
						if(den.isFile()){
							isFile = "文件";
						}
						dirs = dirs + den.getFullName() + "   (" + isFile + ", " + den.getStringAttri() + ", " + den.getSize() + "字节)\n";
					}
					dentryText.setText(dirs);
					String usage = "";
					boolean[] res = fm.getUsage();
					for(int i=0; i<255; i++){
						if(res[i]){
							usage = usage + "1 ";
						}else{
							usage = usage + "0 ";
						}
					}
					usageText.setText(usage);
				}else if (e.getKeyCode() == KeyEvent.VK_UP) {
					commandText.setText(lastCommand);
				}
				
			}
		});

		refresh();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(492, 610));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		commandText.requestFocus();
		
		
		init(fm);
	}
	
	public static void init(FileManager fm){
		ClockControl.SystemStart(fm);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestFrame();
	}
	
	
	
	public void refresh(){
		String dirs = "";
		ArrayList<Dentry> dentries = fm.getCurrentDir().getDentryList();
		for(Dentry den:dentries){
			String isFile = "文件夹";
			if(den.isFile()){
				isFile = "文件";
			}
			dirs = dirs + den.getFullName() + "   (" + isFile + ", " + den.getStringAttri() + ", " + den.getSize() + "字节)\n";
		}
		dentryText.setText(dirs);
		String usage = "";
		boolean[] res = fm.getUsage();
		for(int i=0; i<255; i++){
			if(res[i]){
				usage = usage + "1 ";
			}else{
				usage = usage + "0 ";
			}
		}
		usageText.setText(usage);
	}
}
