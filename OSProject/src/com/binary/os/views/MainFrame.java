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
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.binary.os.filesys.manager.FileManager;
import com.binary.os.kernel.ClockControl;

public class MainFrame extends JFrame {

	private FileManager fm;
	
	private JPanel contentPane;
	public  TreePanel dirTreePanel;
//	public CmdPanelold cmdPanel;
	public CmdPanel cmdPanel;
	public DetailItemPanel[] devDetail = new DetailItemPanel[6];
	public JLabel nameLabel;
	public JLabel sizeLabel;
	public JLabel typeLabel;
	public JLabel attributeLabel;
	public JLabel directAddr1Label;
	public JLabel directAddr2Label;
	public JLabel lev1IndexLabel;
	public JLabel lev2IndexLabel;
	public DiskUsagePanel diskUsagePanel;
	public JPanel currProcPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
		
		fm = new FileManager();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(43, 0, 1280, 768);
		setResizable(false);
		contentPane = new MyPanel("backgrounds.png");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel sysClockPanel = new JPanel();
		sysClockPanel.setBorder(new LineBorder(Color.WHITE, 2, true));
		sysClockPanel.setBounds(10, 20, 438, 34);
		contentPane.add(sysClockPanel);
		sysClockPanel.setOpaque(false);
		sysClockPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(265, 0, 173, 34);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 18));
		sysClockPanel.add(lblNewLabel);
		
		JLabel sysClockLabel = new JLabel("New label");
		sysClockLabel.setForeground(Color.RED);
		sysClockLabel.setFont(new Font("宋体", Font.BOLD, 18));
		sysClockLabel.setBounds(165, 0, 90, 34);
		sysClockPanel.add(sysClockLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 18));
		lblNewLabel_1.setBounds(5, 0, 160, 34);
		sysClockPanel.add(lblNewLabel_1);
		
		dirTreePanel = new TreePanel(fm, this);
		dirTreePanel.setBounds(10, 54, 212, 666);
		contentPane.add(dirTreePanel);
		dirTreePanel.setOpaque(false);
		
		JPanel detailInfoPanel = new JPanel();
		detailInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "详细信息", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		detailInfoPanel.setBounds(222, 54, 226, 241);
		contentPane.add(detailInfoPanel);
		detailInfoPanel.setOpaque(false);
		detailInfoPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("\u540D\u79F0\uFF1A");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("宋体", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 31, 54, 16);
		detailInfoPanel.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\u5927\u5C0F\uFF1A");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(10, 55, 54, 16);
		detailInfoPanel.add(label);
		
		JLabel label_2 = new JLabel("\u5C5E\u6027\uFF1A");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(10, 103, 54, 16);
		detailInfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u76F4\u63A5\u5730\u57401\uFF1A");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("宋体", Font.BOLD, 15));
		label_3.setBounds(10, 127, 94, 16);
		detailInfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u76F4\u63A5\u5730\u57402\uFF1A");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("宋体", Font.BOLD, 15));
		label_4.setBounds(10, 151, 94, 16);
		detailInfoPanel.add(label_4);
		
		JLabel label_5 = new JLabel("\u4E00\u7EA7\u7D22\u5F15\uFF1A");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("宋体", Font.BOLD, 15));
		label_5.setBounds(10, 175, 85, 16);
		detailInfoPanel.add(label_5);
		
		JLabel label_6 = new JLabel("\u4E8C\u7EA7\u7D22\u5F15\uFF1A");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("宋体", Font.BOLD, 15));
		label_6.setBounds(10, 199, 85, 16);
		detailInfoPanel.add(label_6);
		
		nameLabel = new JLabel("");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		nameLabel.setBounds(105, 31, 111, 16);
		detailInfoPanel.add(nameLabel);
		
		sizeLabel = new JLabel("");
		sizeLabel.setForeground(Color.WHITE);
		sizeLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		sizeLabel.setBounds(105, 55, 111, 16);
		detailInfoPanel.add(sizeLabel);
		
		attributeLabel = new JLabel("");
		attributeLabel.setForeground(Color.WHITE);
		attributeLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		attributeLabel.setBounds(105, 103, 111, 16);
		detailInfoPanel.add(attributeLabel);
		
		directAddr1Label = new JLabel("");
		directAddr1Label.setForeground(Color.WHITE);
		directAddr1Label.setFont(new Font("微软雅黑", Font.BOLD, 15));
		directAddr1Label.setBounds(105, 127, 111, 16);
		detailInfoPanel.add(directAddr1Label);
		
		directAddr2Label = new JLabel("");
		directAddr2Label.setForeground(Color.WHITE);
		directAddr2Label.setFont(new Font("微软雅黑", Font.BOLD, 15));
		directAddr2Label.setBounds(105, 151, 111, 16);
		detailInfoPanel.add(directAddr2Label);
		
		lev1IndexLabel = new JLabel("");
		lev1IndexLabel.setForeground(Color.WHITE);
		lev1IndexLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lev1IndexLabel.setBounds(105, 175, 111, 16);
		detailInfoPanel.add(lev1IndexLabel);
		
		lev2IndexLabel = new JLabel("");
		lev2IndexLabel.setForeground(Color.WHITE);
		lev2IndexLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		lev2IndexLabel.setBounds(105, 199, 111, 16);
		detailInfoPanel.add(lev2IndexLabel);
		
		JLabel label_7 = new JLabel("\u7C7B\u578B\uFF1A");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("宋体", Font.BOLD, 15));
		label_7.setBounds(10, 79, 68, 16);
		detailInfoPanel.add(label_7);
		
		typeLabel = new JLabel("");
		typeLabel.setForeground(Color.WHITE);
		typeLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
		typeLabel.setBounds(105, 79, 111, 16);
		detailInfoPanel.add(typeLabel);
		
		cmdPanel = new CmdPanel(fm, this);
		contentPane.add(cmdPanel);
		cmdPanel.setOpaque(false);
		cmdPanel.setLayout(null);
		
		diskUsagePanel = new DiskUsagePanel();
		diskUsagePanel.setBounds(449, 10, 327, 285);
		contentPane.add(diskUsagePanel);
		diskUsagePanel.setLayout(null);
		diskUsagePanel.setOpaque(false);
		diskUsagePanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "磁盘使用情况", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		
		JPanel devicePanel = new JPanel();
		devicePanel.setBounds(777, 318, 487, 403);
		contentPane.add(devicePanel);
		devicePanel.setOpaque(false);
		devicePanel.setLayout(null);
		devicePanel.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2, new Color(240, 240, 240)), "设备信息", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		
		JPanel devDetailPanel = new JPanel();
		devDetailPanel.setBounds(0, 25, 487, 168);
		devicePanel.add(devDetailPanel);
		devDetailPanel.setOpaque(false);
		devDetailPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(14, 0, 460, 24);
		devDetailPanel.add(panel);
		panel.setOpaque(false);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("\u8BBE\u5907\u540D");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("宋体", Font.BOLD, 15));
		lblNewLabel_3.setBounds(0, 0, 115, 24);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBorder(new MatteBorder(2, 2, 1, 1, Color.WHITE));
		
		JLabel label_8 = new JLabel("\u8BBE\u5907\u7C7B\u578B");
		label_8.setFont(new Font("宋体", Font.BOLD, 15));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(115, 0, 115, 24);
		panel.add(label_8);
		label_8.setForeground(Color.WHITE);
		label_8.setBorder(new MatteBorder(2, 1, 1, 1, Color.WHITE));
		
		JLabel label_9 = new JLabel("\u72B6\u6001");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("宋体", Font.BOLD, 15));
		label_9.setBounds(230, 0, 115, 24);
		panel.add(label_9);
		label_9.setForeground(Color.WHITE);
		label_9.setBorder(new MatteBorder(2, 1, 1, 1, Color.WHITE));
		
		JLabel label_10 = new JLabel("\u5F53\u524D\u8FDB\u7A0B");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("宋体", Font.BOLD, 15));
		label_10.setBounds(345, 0, 115, 24);
		panel.add(label_10);
		label_10.setForeground(Color.WHITE);
		label_10.setBorder(new MatteBorder(2, 1, 1, 2, Color.WHITE));
		
		for(int i=0; i<6; i++){
			devDetail[i] = new DetailItemPanel();
			devDetail[i].setBounds(14, 24*(i+1), 460, 24);
			devDetailPanel.add(devDetail[i]);
		}
		devDetail[5].setBorder(new MatteBorder(0, 0, 2, 0, Color.WHITE));
		for(int i=0; i<3; i++){
			devDetail[i].devNameLabel.setText("A" + i);
			devDetail[i].devTypeLabel.setText("A");
			devDetail[i].devStatusLabel.setText("闲");
		}
		for(int i=0; i<2; i++){
			devDetail[i+3].devNameLabel.setText("B" + i);
			devDetail[i+3].devTypeLabel.setText("B");
			devDetail[i+3].devStatusLabel.setText("闲");
		}
		devDetail[5].devNameLabel.setText("C0");
		devDetail[5].devTypeLabel.setText("C");
		devDetail[5].devStatusLabel.setText("闲");
		
		JPanel devAListPanel = new JPanel();
		devAListPanel.setBounds(0, 193, 487, 70);
		devicePanel.add(devAListPanel);
		devAListPanel.setOpaque(false);
		
		JPanel devBListPanel = new JPanel();
		devBListPanel.setBounds(0, 263, 487, 70);
		devicePanel.add(devBListPanel);
		devBListPanel.setOpaque(false);
		
		JPanel devCListPanel = new JPanel();
		devCListPanel.setBounds(0, 333, 487, 70);
		devicePanel.add(devCListPanel);
		devCListPanel.setOpaque(false);
		
		JPanel procPanel = new JPanel();
		procPanel.setBounds(777, 10, 487, 307);
		contentPane.add(procPanel);
		procPanel.setLayout(null);
		procPanel.setOpaque(false);
		
		currProcPanel = new JPanel();
		currProcPanel.setBounds(0, 0, 163, 167);
		procPanel.add(currProcPanel);
		currProcPanel.setOpaque(false);
		currProcPanel.setLayout(null);
		currProcPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "当前进程", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		
		JLabel label_11 = new JLabel("\u8FDB\u7A0B\u540D\uFF1A");
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("宋体", Font.BOLD, 15));
		label_11.setBounds(10, 35, 64, 16);
		currProcPanel.add(label_11);
		
		JLabel label_12 = new JLabel("\u5F53\u524D\u6307\u4EE4\uFF1A");
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("宋体", Font.BOLD, 15));
		label_12.setBounds(10, 66, 80, 16);
		currProcPanel.add(label_12);
		
		JLabel label_13 = new JLabel("\u4E2D\u95F4\u7ED3\u679C\uFF1A");
		label_13.setForeground(Color.WHITE);
		label_13.setFont(new Font("宋体", Font.BOLD, 15));
		label_13.setBounds(10, 97, 80, 16);
		currProcPanel.add(label_13);
		
		JLabel label_14 = new JLabel("\u5269\u4F59\u65F6\u949F\uFF1A");
		label_14.setForeground(Color.WHITE);
		label_14.setFont(new Font("宋体", Font.BOLD, 15));
		label_14.setBounds(10, 128, 80, 16);
		currProcPanel.add(label_14);
		
		JLabel procNameLabel = new JLabel("");
		procNameLabel.setForeground(Color.WHITE);
		procNameLabel.setFont(new Font("宋体", Font.BOLD, 15));
		procNameLabel.setBounds(89, 35, 64, 16);
		currProcPanel.add(procNameLabel);
		
		JLabel currInstructLabel = new JLabel("");
		currInstructLabel.setForeground(Color.WHITE);
		currInstructLabel.setFont(new Font("宋体", Font.BOLD, 15));
		currInstructLabel.setBounds(89, 66, 64, 16);
		currProcPanel.add(currInstructLabel);
		
		JLabel tempResultLabel = new JLabel("");
		tempResultLabel.setForeground(Color.WHITE);
		tempResultLabel.setFont(new Font("宋体", Font.BOLD, 15));
		tempResultLabel.setBounds(89, 97, 64, 16);
		currProcPanel.add(tempResultLabel);
		
		JLabel remainTLabel = new JLabel("");
		remainTLabel.setForeground(Color.WHITE);
		remainTLabel.setFont(new Font("宋体", Font.BOLD, 15));
		remainTLabel.setBounds(89, 128, 64, 16);
		currProcPanel.add(remainTLabel);
		
		JPanel memPanel = new JPanel();
		memPanel.setBounds(163, 0, 314, 167);
		procPanel.add(memPanel);
		memPanel.setOpaque(false);
		memPanel.setLayout(null);
		memPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "主存使用情况", TitledBorder.LEADING, TitledBorder.TOP,new Font("宋体",Font.BOLD,18), Color.WHITE));
		
		JPanel waitList = new JPanel();
		waitList.setOpaque(false);
		waitList.setBounds(0, 169, 487, 70);
		procPanel.add(waitList);
		
		JPanel blockList = new JPanel();
		blockList.setOpaque(false);
		blockList.setBounds(0, 238, 487, 70);
		procPanel.add(blockList);
		
		setVisible(true);
		
		ClockControl.SystemStart(fm, this);
	}
}

class DetailItemPanel extends JPanel {
	
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
		devStatusLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
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