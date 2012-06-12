package com.binary.os.filesys.test;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Color;
import com.binary.os.filesys.dentries.Dentry;
import com.binary.os.filesys.manager.FileManager;

import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class TestFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileManager fm = new FileManager();
	private JTextArea dirText;
	private JTextArea dentryText;
	private JTextArea resultText;
	private JTextArea usageText;
	private JTextField commandText;
	public TestFrame() {
		
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
		
		dirText = new JTextArea();
		dirText.setEditable(false);
		dirText.setBounds(10, 5, 456, 24);
		panel.add(dirText);
		
		dentryText = new JTextArea();
		dentryText.setEditable(false);
		dentryText.setBackground(Color.WHITE);
		dentryText.setBounds(10, 39, 456, 57);
		panel.add(dentryText);
		
		getContentPane().add(panel, BorderLayout.CENTER);
	
		usageText = new JTextArea();
		usageText.setEditable(false);
		usageText.setBounds(10, 114, 454, 139);
		panel.add(usageText);
		
		commandText = new JTextField();
		commandText.setBounds(10, 528, 454, 21);
		panel.add(commandText);
		commandText.setColumns(10);
		commandText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String command = commandText.getText();
					resultText.append(command+"\n");
					commandText.setText("");
					String result = fm.interpret(command);
					resultText.append(result+"\n");
					dirText.setText(fm.getStringCurrentPath());
					String dirs = "";
					ArrayList<Dentry> dentries = fm.getCurrentDir().getDentryList();
					for(Dentry den:dentries){
						dirs = dirs + den.getFullName() + "    ";
					}
					dentryText.setText(dirs);
					String usage = "";
					boolean[] res = fm.getUsage();
					for(int i=0; i<8; i++){
						for(int j=0; j<32; j++){
							if(i*32+j == 255){
								break;
							}
							if(res[i*32+j]){
								usage = usage + "1 ";
							}else{
								usage = usage + "0 ";
							}
						}
						usage = usage + "\n";
					}
					usageText.setText(usage);
				}
			}
		});
		
		dirText.setText(fm.getStringCurrentPath());
		String dirs = "";
		ArrayList<Dentry> dentries = fm.getCurrentDir().getDentryList();
		for(Dentry den:dentries){
			dirs = dirs + den.getFullName() + "    ";
		}
		dentryText.setText(dirs);
		String usage = "";
		boolean[] res = fm.getUsage();
		for(int i=0; i<8; i++){
			for(int j=0; j<32; j++){
				if(i*32+j == 255){
					break;
				}
				if(res[i*32+j]){
					usage = usage + "1 ";
				}else{
					usage = usage + "0 ";
				}
			}
			usage = usage + "\n";
		}
		usageText.setText(usage);
	
		this.setSize(new Dimension(492, 610));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestFrame();

	}
}
