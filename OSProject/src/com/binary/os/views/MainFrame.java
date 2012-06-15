package com.binary.os.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import com.binary.os.filesys.manager.FileManager;
import com.binary.os.kernel.ClockControl;
import com.binary.os.kernel.GlobalStaticVar;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileManager fm;
	
	private MyPanel contentPane;
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
	public MemPanel memPanel;
	public JLabel sysClockLabel;
	public JLabel procNameLabel;
	public JLabel currInstructLabel;
	public JLabel tempResultLabel;
	public JLabel remainTLabel;
	public JLabel waitListLabel;
	public JLabel blockListLabel;
	public JLabel devAListLabel;
	public JLabel devBListLabel;
	public JLabel devCListLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainFrame();
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
		
		this.fm = new FileManager();
		
		GlobalStaticVar.fm = fm;
		GlobalStaticVar.mf = this;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(43, 0, 1280, 768);
		setResizable(false);
		contentPane = new MyPanel("1.jpg");
	//	contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel sysClockPanel = new JPanel();
		sysClockPanel.setBorder(new LineBorder(Color.WHITE, 2, true));
		sysClockPanel.setBounds(10, 20, 438, 34);
		contentPane.add(sysClockPanel);
		sysClockPanel.setOpaque(false);
		sysClockPanel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u4E2A\u65F6\u949F\u5355\u4F4D");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(278, 0, 160, 34);
		lblNewLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 18));
		sysClockPanel.add(lblNewLabel);
		
		sysClockLabel = new JLabel("New label");
		sysClockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sysClockLabel.setForeground(Color.RED);
		sysClockLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 20));
		sysClockLabel.setBounds(165, 0, 90, 34);
		sysClockPanel.add(sysClockLabel);
		
		JLabel sysClock = new JLabel("\u7CFB\u7EDF\u5DF2\u542F\u52A8\uFF1A");
		sysClock.setHorizontalAlignment(SwingConstants.CENTER);
		sysClock.setForeground(Color.WHITE);
		sysClock.setFont(new Font("ÀŒÃÂ", Font.BOLD, 18));
		sysClock.setBounds(5, 0, 160, 34);
		sysClockPanel.add(sysClock);
		
		dirTreePanel = new TreePanel(fm, this);
		dirTreePanel.setBounds(10, 63, 212, 656);
		dirTreePanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.WHITE));
		contentPane.add(dirTreePanel);
		dirTreePanel.setBackground(null);
		dirTreePanel.setOpaque(false);
		
		JPanel detailInfoPanel = new JPanel();
		detailInfoPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "œÍœ∏–≈œ¢", TitledBorder.LEADING, TitledBorder.TOP,new Font("ÀŒÃÂ",Font.BOLD,18), Color.WHITE));
		detailInfoPanel.setBounds(222, 54, 226, 251);
		contentPane.add(detailInfoPanel);
		detailInfoPanel.setOpaque(false);
		detailInfoPanel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("\u540D\u79F0\uFF1A");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		lblNewLabel_2.setBounds(10, 33, 54, 16);
		detailInfoPanel.add(lblNewLabel_2);
		
		JLabel label = new JLabel("\u5927\u5C0F\uFF1A");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label.setBounds(10, 59, 54, 16);
		detailInfoPanel.add(label);
		
		JLabel label_2 = new JLabel("\u5C5E\u6027\uFF1A");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_2.setBounds(10, 111, 54, 16);
		detailInfoPanel.add(label_2);
		
		JLabel label_3 = new JLabel("\u76F4\u63A5\u5730\u57401\uFF1A");
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_3.setBounds(10, 137, 94, 16);
		detailInfoPanel.add(label_3);
		
		JLabel label_4 = new JLabel("\u76F4\u63A5\u5730\u57402\uFF1A");
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_4.setBounds(10, 163, 94, 16);
		detailInfoPanel.add(label_4);
		
		JLabel label_5 = new JLabel("\u4E00\u7EA7\u7D22\u5F15\uFF1A");
		label_5.setForeground(Color.WHITE);
		label_5.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_5.setBounds(10, 189, 85, 16);
		detailInfoPanel.add(label_5);
		
		JLabel label_6 = new JLabel("\u4E8C\u7EA7\u7D22\u5F15\uFF1A");
		label_6.setForeground(Color.WHITE);
		label_6.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_6.setBounds(10, 215, 85, 16);
		detailInfoPanel.add(label_6);
		
		nameLabel = new JLabel("");
		nameLabel.setForeground(Color.WHITE);
		nameLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		nameLabel.setBounds(105, 33, 111, 16);
		detailInfoPanel.add(nameLabel);
		
		sizeLabel = new JLabel("");
		sizeLabel.setForeground(Color.WHITE);
		sizeLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		sizeLabel.setBounds(105, 59, 111, 16);
		detailInfoPanel.add(sizeLabel);
		
		attributeLabel = new JLabel("");
		attributeLabel.setForeground(Color.WHITE);
		attributeLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		attributeLabel.setBounds(105, 111, 111, 16);
		detailInfoPanel.add(attributeLabel);
		
		directAddr1Label = new JLabel("");
		directAddr1Label.setForeground(Color.WHITE);
		directAddr1Label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		directAddr1Label.setBounds(105, 137, 111, 16);
		detailInfoPanel.add(directAddr1Label);
		
		directAddr2Label = new JLabel("");
		directAddr2Label.setForeground(Color.WHITE);
		directAddr2Label.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		directAddr2Label.setBounds(105, 163, 111, 16);
		detailInfoPanel.add(directAddr2Label);
		
		lev1IndexLabel = new JLabel("");
		lev1IndexLabel.setForeground(Color.WHITE);
		lev1IndexLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		lev1IndexLabel.setBounds(105, 189, 111, 16);
		detailInfoPanel.add(lev1IndexLabel);
		
		lev2IndexLabel = new JLabel("");
		lev2IndexLabel.setForeground(Color.WHITE);
		lev2IndexLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		lev2IndexLabel.setBounds(105, 215, 111, 16);
		detailInfoPanel.add(lev2IndexLabel);
		
		JLabel label_7 = new JLabel("\u7C7B\u578B\uFF1A");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_7.setBounds(10, 85, 68, 16);
		detailInfoPanel.add(label_7);
		
		typeLabel = new JLabel("");
		typeLabel.setForeground(Color.WHITE);
		typeLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 15));
		typeLabel.setBounds(105, 85, 111, 16);
		detailInfoPanel.add(typeLabel);
		
		cmdPanel = new CmdPanel(fm, this);
		contentPane.add(cmdPanel);
		cmdPanel.setOpaque(false);
		cmdPanel.setLayout(null);
		
		diskUsagePanel = new DiskUsagePanel(fm);
		diskUsagePanel.setBounds(449, 10, 327, 295);
		contentPane.add(diskUsagePanel);
		diskUsagePanel.setLayout(null);
		diskUsagePanel.setOpaque(false);
		diskUsagePanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "¥≈≈Ã π”√«Èøˆ", TitledBorder.LEADING, TitledBorder.TOP,new Font("ÀŒÃÂ",Font.BOLD,18), Color.WHITE));
		
		JPanel devicePanel = new JPanel();
		devicePanel.setBounds(777, 318, 487, 403);
		contentPane.add(devicePanel);
		devicePanel.setOpaque(false);
		devicePanel.setLayout(null);
		devicePanel.setBorder(new TitledBorder(new MatteBorder(2, 2, 2, 2,Color.WHITE), "…Ë±∏–≈œ¢", TitledBorder.LEADING, TitledBorder.TOP,new Font("ÀŒÃÂ",Font.BOLD,18), Color.WHITE));
		
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
		lblNewLabel_3.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		lblNewLabel_3.setBounds(0, 0, 115, 24);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBorder(new MatteBorder(2, 2, 1, 1, Color.WHITE));
		
		JLabel label_8 = new JLabel("\u8BBE\u5907\u7C7B\u578B");
		label_8.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setBounds(115, 0, 115, 24);
		panel.add(label_8);
		label_8.setForeground(Color.WHITE);
		label_8.setBorder(new MatteBorder(2, 1, 1, 1, Color.WHITE));
		
		JLabel label_9 = new JLabel("\u72B6\u6001");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_9.setBounds(230, 0, 115, 24);
		panel.add(label_9);
		label_9.setForeground(Color.WHITE);
		label_9.setBorder(new MatteBorder(2, 1, 1, 1, Color.WHITE));
		
		JLabel label_10 = new JLabel("\u5F53\u524D\u8FDB\u7A0B");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
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
			devDetail[i].devStatusLabel.setText("œ–");
		}
		for(int i=0; i<2; i++){
			devDetail[i+3].devNameLabel.setText("B" + i);
			devDetail[i+3].devTypeLabel.setText("B");
			devDetail[i+3].devStatusLabel.setText("œ–");
		}
		devDetail[5].devNameLabel.setText("C0");
		devDetail[5].devTypeLabel.setText("C");
		devDetail[5].devStatusLabel.setText("œ–");
		
		JPanel devAListPanel = new JPanel();
		devAListPanel.setBounds(0, 193, 487, 70);
		devicePanel.add(devAListPanel);
		devAListPanel.setOpaque(false);
		devAListPanel.setLayout(null);
		
		JTextArea txtrA = new JTextArea("  A\u8BBE\u5907\r\n\u7B49\u5F85\u961F\u5217");
		txtrA.setOpaque(false);
		txtrA.setForeground(Color.WHITE);
		txtrA.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		txtrA.setEditable(false);
		txtrA.setBounds(5, 10, 68, 50);
		devAListPanel.add(txtrA);
		
		devAListLabel = new JLabel("");
		devAListLabel.setForeground(Color.WHITE);
		devAListLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		devAListLabel.setBounds(83, 0, 402, 70);
		devAListPanel.add(devAListLabel);
		
		JPanel devBListPanel = new JPanel();
		devBListPanel.setBounds(0, 263, 487, 70);
		devicePanel.add(devBListPanel);
		devBListPanel.setOpaque(false);
		devBListPanel.setLayout(null);
		
		JTextArea txtrB = new JTextArea("  B\u8BBE\u5907\r\n\u7B49\u5F85\u961F\u5217");
		txtrB.setOpaque(false);
		txtrB.setForeground(Color.WHITE);
		txtrB.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		txtrB.setEditable(false);
		txtrB.setBounds(5, 10, 68, 50);
		devBListPanel.add(txtrB);
		
		devBListLabel = new JLabel("");
		devBListLabel.setForeground(Color.WHITE);
		devBListLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		devBListLabel.setBounds(83, 0, 402, 70);
		devBListPanel.add(devBListLabel);
		
		
		JPanel devCListPanel = new JPanel();
		devCListPanel.setBounds(0, 333, 487, 70);
		devicePanel.add(devCListPanel);
		devCListPanel.setOpaque(false);
		devCListPanel.setLayout(null);
		
		JTextArea txtrC = new JTextArea("  C\u8BBE\u5907\r\n\u7B49\u5F85\u961F\u5217");
		txtrC.setOpaque(false);
		txtrC.setForeground(Color.WHITE);
		txtrC.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		txtrC.setEditable(false);
		txtrC.setBounds(5, 10, 68, 50);
		devCListPanel.add(txtrC);
		
		devCListLabel = new JLabel("");
		devCListLabel.setForeground(Color.WHITE);
		devCListLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		devCListLabel.setBounds(83, 0, 437, 70);
		devCListPanel.add(devCListLabel);
		
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
		currProcPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "µ±«∞Ω¯≥Ã", TitledBorder.LEADING, TitledBorder.TOP,new Font("ÀŒÃÂ",Font.BOLD,18), Color.WHITE));
		
		JLabel label_11 = new JLabel("\u8FDB\u7A0B\u540D\uFF1A");
		label_11.setForeground(Color.WHITE);
		label_11.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_11.setBounds(10, 35, 64, 16);
		currProcPanel.add(label_11);
		
		JLabel label_12 = new JLabel("\u5F53\u524D\u6307\u4EE4\uFF1A");
		label_12.setForeground(Color.WHITE);
		label_12.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_12.setBounds(10, 66, 80, 16);
		currProcPanel.add(label_12);
		
		JLabel label_13 = new JLabel("\u4E2D\u95F4\u7ED3\u679C\uFF1A");
		label_13.setForeground(Color.WHITE);
		label_13.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_13.setBounds(10, 97, 80, 16);
		currProcPanel.add(label_13);
		
		JLabel label_14 = new JLabel("\u5269\u4F59\u65F6\u949F\uFF1A");
		label_14.setForeground(Color.WHITE);
		label_14.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		label_14.setBounds(10, 128, 80, 16);
		currProcPanel.add(label_14);
		
		procNameLabel = new JLabel("");
		procNameLabel.setForeground(Color.WHITE);
		procNameLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		procNameLabel.setBounds(89, 35, 64, 16);
		currProcPanel.add(procNameLabel);
		
		currInstructLabel = new JLabel("");
		currInstructLabel.setForeground(Color.WHITE);
		currInstructLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		currInstructLabel.setBounds(89, 66, 64, 16);
		currProcPanel.add(currInstructLabel);
		
		tempResultLabel = new JLabel("");
		tempResultLabel.setForeground(Color.WHITE);
		tempResultLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		tempResultLabel.setBounds(89, 97, 64, 16);
		currProcPanel.add(tempResultLabel);
		
		remainTLabel = new JLabel("");
		remainTLabel.setForeground(Color.WHITE);
		remainTLabel.setFont(new Font("ÀŒÃÂ", Font.BOLD, 15));
		remainTLabel.setBounds(89, 128, 64, 16);
		currProcPanel.add(remainTLabel);
		
		memPanel = new MemPanel();
		memPanel.setBounds(163, 0, 314, 167);
		procPanel.add(memPanel);
		memPanel.setOpaque(false);
		memPanel.setLayout(null);
		memPanel.setBorder(new TitledBorder(new LineBorder(Color.WHITE, 2, true), "÷˜¥Ê π”√«Èøˆ", TitledBorder.LEADING, TitledBorder.TOP,new Font("ÀŒÃÂ",Font.BOLD,18), Color.WHITE));
		
		JPanel waitList = new JPanel();
		waitList.setOpaque(false);
		waitList.setBounds(0, 169, 487, 70);
		procPanel.add(waitList);
		waitList.setLayout(null);
		
		JTextArea textArea = new JTextArea("æÕ–˜\n∂”¡–");
		textArea.setEditable(false);
		textArea.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		textArea.setForeground(Color.WHITE);
		textArea.setBounds(5, 10, 40, 60);
		waitList.add(textArea);
		textArea.setOpaque(false);
		
		waitListLabel = new JLabel("");
		waitListLabel.setForeground(Color.WHITE);
		waitListLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		waitListLabel.setBounds(50, 0, 437, 70);
		waitList.add(waitListLabel);
		
		JPanel blockList = new JPanel();
		blockList.setOpaque(false);
		blockList.setBounds(0, 238, 487, 70);
		procPanel.add(blockList);
		blockList.setLayout(null);
		
		JTextArea textArea_1 = new JTextArea("\u963B\u585E\r\n\u961F\u5217");
		textArea_1.setOpaque(false);
		textArea_1.setForeground(Color.WHITE);
		textArea_1.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		textArea_1.setEditable(false);
		textArea_1.setBounds(5, 10, 40, 60);
		blockList.add(textArea_1);
		
		blockListLabel = new JLabel("");
		blockListLabel.setForeground(Color.WHITE);
		blockListLabel.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 16));
		blockListLabel.setBounds(50, 0, 437, 70);
		blockList.add(blockListLabel);
		
		setLocationRelativeTo(null);
		setVisible(true);
		
		cmdPanel.cmdText.requestFocus();
		
		ClockControl.SystemStart();
	}
}
