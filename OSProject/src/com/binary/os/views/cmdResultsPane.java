package com.binary.os.views;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class cmdResultsPane extends JPanel {
	
	public JScrollPane resultScrolPane;
	public JTextArea resultsText;
	public JLabel currDirLabel;
	public JTextField cmdText;

	/**
	 * Create the panel.
	 */
	public cmdResultsPane() {
		setBounds(222, 287, 554, 433);
		setOpaque(false);
		setLayout(null);
		
		resultScrolPane = new JScrollPane();
		resultScrolPane.setBounds(0, 0, 502, 392);
		add(resultScrolPane);
		resultScrolPane.setOpaque(false);
		
		resultsText = new JTextArea();
		resultScrolPane.setViewportView(resultsText);
		resultsText.setFont(new Font("Arial", Font.PLAIN, 15));
		resultsText.setEditable(false);
		resultsText.setOpaque(false);
		
		JLabel lblNewLabel_3 = new JLabel("\u5F53\u524D\u8DEF\u5F84\uFF1A");
		lblNewLabel_3.setFont(new Font("ËÎÌו", Font.BOLD, 15));
		lblNewLabel_3.setBounds(5, 394, 80, 18);
		add(lblNewLabel_3);
		
		currDirLabel = new JLabel("New Label");
		currDirLabel.setFont(new Font("Arial", Font.BOLD, 15));
		currDirLabel.setBounds(81, 394, 421, 18);
		add(currDirLabel);
		
		cmdText = new JTextField();
		cmdText.setFont(new Font("Arial", Font.BOLD, 15));
		cmdText.setBounds(0, 412, 502, 21);
		add(cmdText);
		cmdText.setColumns(10);
		resultScrolPane.getViewport().setOpaque(false); 
	}

}
