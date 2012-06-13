package com.binary.os.views;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	
	String image;
	
	public MyPanel(String imageLoc){
		image = imageLoc;
		setOpaque(false);
	}
	
	public void paintComponent(Graphics g) {
		ImageIcon background = new ImageIcon(image);//º”‘ÿÕº∆¨
		Image im=Toolkit.getDefaultToolkit().getImage(image);
		g.drawImage(im, 0, 0, null);
	}

}
