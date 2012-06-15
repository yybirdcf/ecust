package com.binary.os.views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class DetailItemPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JLabel devNameLabel;
	public JLabel devTypeLabel;
	public JLabel devStatusLabel;
	public JLabel currPidLabel;
	
	public DetailItemPanel(){
		setLayout(null);
		setOpaque(false);
//		setBounds(14, 24, 460, 24);
		
		devNameLabel = new JLabel("New Label");
		devNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		devNameLabel.setBounds(0, 0, 115, 24);
		add(devNameLabel);
		devNameLabel.setForeground(Color.WHITE);
		devNameLabel.setBorder(new MatteBorder(1, 2, 1, 1, Color.WHITE));
		
		devTypeLabel = new JLabel("New Label");
		devTypeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devTypeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		devTypeLabel.setBounds(115, 0, 115, 24);
		add(devTypeLabel);
		devTypeLabel.setForeground(Color.WHITE);
		devTypeLabel.setBorder(new LineBorder(Color.WHITE, 1));
		
		devStatusLabel = new JLabel("New Label");
		devStatusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		devStatusLabel.setFont(new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 15));
		devStatusLabel.setBounds(230, 0, 115, 24);
		add(devStatusLabel);
		devStatusLabel.setForeground(Color.WHITE);
		devStatusLabel.setBorder(new LineBorder(Color.WHITE, 1));
		
		currPidLabel = new JLabel("");
		currPidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		currPidLabel.setFont(new Font("Arial", Font.BOLD, 15));
		currPidLabel.setBounds(345, 0, 115, 24);
		add(currPidLabel);
		currPidLabel.setForeground(Color.WHITE);
		currPidLabel.setBorder(new MatteBorder(1, 1, 1, 2, Color.WHITE));
	}

}
