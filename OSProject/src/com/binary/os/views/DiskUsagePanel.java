package com.binary.os.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.binary.os.filesys.manager.FileManager;

public class DiskUsagePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileManager fm;
	boolean[] usage = null;
	
	public DiskUsagePanel(FileManager fm){
		this.fm = fm;
	}

	public void paintComponent(Graphics g){
		usage = fm.getUsage();
		paintTable(15,15,240,240,g);
		for(int i=0; i<16; i++){
			for(int j=0; j<16; j++){
				if(16*i+j == 255){
					break;
				}
				if(usage[16*i+j]){
					fillTable(j*15,i*15,15,15,g, Color.RED, true);
				}else{
					fillTable(j*15,i*15,15,15,g, Color.WHITE, true);
				}
			}
		}
		
	}
	
    public void paintTable(int h,int w,int vertical,int horizontal, Graphics g){//单元格的高，单元格的宽，表的高度，表的宽度，表格所在的对象
        g.setColor(Color.RED);
        for(int i=0;i<vertical;i=i+h){//画表的高
            for(int j=0;j<horizontal;j=j+w){//画表的宽
                g.draw3DRect(50+j, 45+i, w, h, true);
            }
        }
        g.setColor(Color.YELLOW);
        for(int i=0;i<vertical/h;i++){
        	g.drawChars(String.format("%2d",i).toCharArray(), 0, 2, 50-20, 45+13+i*h);
        }
        for(int i=0;i<horizontal/w;i++){
        	g.drawChars(String.format("%2d",i).toCharArray(), 0, 2, 50+i*w, 45-5);
        }
        
    }
	
    public void fillTable(int x,int y,int h,int w,Graphics g,Color co, boolean isUp){//填充颜色
        g.setColor(co);
        g.fill3DRect(50+x, 45+y, w, h, isUp);
    }
   
}
