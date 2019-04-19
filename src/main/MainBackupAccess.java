package main;

import io.Writer;
import page.MainFrame;
import page.pack.BackupTreePage;
import util.Data;

public class MainBackupAccess {

	public static void main(String[] str) {
		Writer.logSetup();
		new MainFrame(Data.revVer(MainBCU.ver)).initialize();
		new Timer().start();
		MainFrame.changePanel(new BackupTreePage(null, false));
	}

}
