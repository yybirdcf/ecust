package com.binary.os.views;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ShowTextDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static Frame frame = null;
	
	public ShowTextDialog(String fileName, String text){
		
		super(frame);
		this.setTitle(fileName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
		JTextArea textArea = new JTextArea(text);
		textArea.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,15)); 
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(480,450));
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		
		this.getContentPane().add(panel);
		
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
