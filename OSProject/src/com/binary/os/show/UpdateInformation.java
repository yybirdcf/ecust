package com.binary.os.show;

import com.binary.os.views.MainFrame;

public class UpdateInformation {

	public static void update(MainFrame mf){
		
		mf.memPanel.bitMap = RealInformation.getBitMap();
		mf.memPanel.bit = RealInformation.getCommandNow();
		mf.memPanel.repaint();
		
		
		
	}
}
