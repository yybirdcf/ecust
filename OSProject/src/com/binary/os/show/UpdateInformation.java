package com.binary.os.show;

import com.binary.os.kernel.GlobalStaticVar;

public class UpdateInformation {

	public static void update(){
		
		GlobalStaticVar.mf.memPanel.bitMap = RealInformation.getBitMap();
		GlobalStaticVar.mf.memPanel.bit = RealInformation.getCommandNow();
		GlobalStaticVar.mf.memPanel.repaint();
		
		
		
	}
}
