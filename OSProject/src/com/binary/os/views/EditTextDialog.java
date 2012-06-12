package com.binary.os.views;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import com.binary.os.filesys.manager.FileManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditTextDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static Frame frame = null;
	private JTextArea textArea;
	private String fileName;
	private FileManager fileManager;
	
	
	public EditTextDialog(String fileName, String text, FileManager fileManager){
		
		super(frame);
		
		this.fileManager = fileManager;
		this.fileName = fileName;
		
		this.setTitle(fileName);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	
		textArea = new JTextArea(text);
		textArea.setFont(new Font("Î¢ÈíÑÅºÚ",Font.BOLD,15)); 
		textArea.setLineWrap(true);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(480,430));
		scrollPane.setViewportView(textArea);
		
		JPanel panel = new JPanel();
		panel.add(scrollPane);
		
		JButton button = new JButton("±£´æ±à¼­");
		button.addActionListener(new buttonAct());
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(button);
		this.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
		
		this.getContentPane().add(panel);
		
		this.setSize(500, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	class buttonAct implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String result = fileManager.edit(fileName, textArea.getText());//±£´æÎÄ¼þ
			JOptionPane.showMessageDialog(null, result, fileName, JOptionPane.INFORMATION_MESSAGE);
		}
		
	}
}
