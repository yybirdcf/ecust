package com.binary.os.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private JPanel contentPane;
//	private JPanel dirTreePane;
	private CmdPanel cmdPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(43, 0, 1280, 768);
		setResizable(false);
		contentPane = new MyPanel("background.png");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel sysClockPane = new JPanel();
		sysClockPane.setBorder(new LineBorder(Color.WHITE, 2, true));
		sysClockPane.setBounds(10, 10, 438, 44);
		contentPane.add(sysClockPane);
		sysClockPane.setOpaque(false);
		sysClockPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(265, 0, 173, 44);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 18));
		sysClockPane.add(lblNewLabel);
		
		JLabel sysClockLabel = new JLabel("New label");
		sysClockLabel.setForeground(Color.RED);
		sysClockLabel.setFont(new Font("宋体", Font.BOLD, 18));
		sysClockLabel.setBounds(165, 0, 90, 44);
		sysClockPane.add(sysClockLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel_1.setBounds(5, 0, 160, 44);
		sysClockPane.add(lblNewLabel_1);
		
//		dirTreePane = new JPanel();
//		dirTreePane.setBounds(10, 54, 212, 666);
//		contentPane.add(dirTreePane);
//		dirTreePane.setOpaque(false);
//		dirTreePane.setLayout(null);
		
		JPanel detailInfoPane = new JPanel();
		detailInfoPane.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "详细信息", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		detailInfoPane.setBounds(222, 54, 226, 231);
		contentPane.add(detailInfoPane);
		detailInfoPane.setOpaque(false);
		detailInfoPane.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("\u540D\u79F0\uFF1A");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 31, 54, 16);
		detailInfoPane.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\u5927\u5C0F\uFF1A");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(10, 53, 54, 16);
		detailInfoPane.add(label);
		
		JLabel label_1 = new JLabel("\u7236\u76EE\u5F55\uFF1A");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(10, 75, 68, 16);
		detailInfoPane.add(label_1);
		
		JLabel label_2 = new JLabel("\u5C5E\u6027\uFF1A");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(10, 119, 54, 16);
		detailInfoPane.add(label_2);
		
		JLabel label_3 = new JLabel("\u76F4\u63A5\u5730\u57401\uFF1A");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(10, 141, 94, 16);
		detailInfoPane.add(label_3);
		
		JLabel label_4 = new JLabel("\u76F4\u63A5\u5730\u57402\uFF1A");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(10, 163, 94, 16);
		detailInfoPane.add(label_4);
		
		JLabel label_5 = new JLabel("\u4E00\u7EA7\u7D22\u5F15\uFF1A");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(10, 185, 85, 16);
		detailInfoPane.add(label_5);
		
		JLabel label_6 = new JLabel("\u4E8C\u7EA7\u7D22\u5F15\uFF1A");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("宋体", Font.BOLD, 15));
		label_6.setBounds(10, 208, 85, 16);
		detailInfoPane.add(label_6);
		
		JLabel nameLabel = new JLabel("New label");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		nameLabel.setBounds(105, 31, 111, 16);
		detailInfoPane.add(nameLabel);
		
		JLabel sizeLabel = new JLabel("New label");
		sizeLabel.setForeground(Color.WHITE);
		sizeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		sizeLabel.setBounds(105, 53, 111, 16);
		detailInfoPane.add(sizeLabel);
		
		JLabel fatherDirLabel = new JLabel("New label");
		fatherDirLabel.setForeground(Color.WHITE);
		fatherDirLabel.setFont(new Font("Arial", Font.BOLD, 15));
		fatherDirLabel.setBounds(105, 75, 111, 16);
		detailInfoPane.add(fatherDirLabel);
		
		JLabel attributeLabel = new JLabel("New label");
		attributeLabel.setForeground(Color.WHITE);
		attributeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		attributeLabel.setBounds(105, 119, 111, 16);
		detailInfoPane.add(attributeLabel);
		
		JLabel directAddr1Label = new JLabel("New label");
		directAddr1Label.setForeground(Color.WHITE);
		directAddr1Label.setFont(new Font("Arial", Font.BOLD, 15));
		directAddr1Label.setBounds(105, 141, 111, 16);
		detailInfoPane.add(directAddr1Label);
		
		JLabel directAddr2Label = new JLabel("New label");
		directAddr2Label.setForeground(Color.WHITE);
		directAddr2Label.setFont(new Font("Arial", Font.BOLD, 15));
		directAddr2Label.setBounds(105, 163, 111, 16);
		detailInfoPane.add(directAddr2Label);
		
		JLabel lev1IndexLabel = new JLabel("New label");
		lev1IndexLabel.setForeground(Color.WHITE);
		lev1IndexLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lev1IndexLabel.setBounds(105, 185, 111, 16);
		detailInfoPane.add(lev1IndexLabel);
		
		JLabel lev2IndexLabel = new JLabel("New label");
		lev2IndexLabel.setForeground(Color.WHITE);
		lev2IndexLabel.setFont(new Font("Arial", Font.BOLD, 15));
		lev2IndexLabel.setBounds(105, 208, 111, 16);
		detailInfoPane.add(lev2IndexLabel);
		
		JLabel label_7 = new JLabel("\u7C7B\u578B\uFF1A");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("宋体", Font.BOLD, 15));
		label_7.setBounds(10, 97, 68, 16);
		detailInfoPane.add(label_7);
		
		JLabel typeLabel = new JLabel("New label");
		typeLabel.setForeground(Color.WHITE);
		typeLabel.setFont(new Font("Arial", Font.BOLD, 15));
		typeLabel.setBounds(105, 97, 111, 16);
		detailInfoPane.add(typeLabel);
		
		cmdPanel = new CmdPanel();
		cmdPanel.cmdText.setBounds(0, 412, 554, 21);
		cmdPanel.currDirLabel.setBounds(81, 394, 473, 18);
		cmdPanel.resultScrolPane.setBounds(0, 0, 554, 392);
		cmdPanel.setBounds(222, 287, 554, 433);
		contentPane.add(cmdPanel);
		cmdPanel.setOpaque(false);
		cmdPanel.setLayout(null);
		
		JPanel diskUsagePane = new JPanel();
		diskUsagePane.setBounds(449, 10, 327, 275);
		contentPane.add(diskUsagePane);
		diskUsagePane.setLayout(null);
		diskUsagePane.setOpaque(false);
		diskUsagePane.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "磁盘使用情况", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
	}
}
