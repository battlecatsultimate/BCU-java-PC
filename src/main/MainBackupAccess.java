package main;

import common.util.Data;
import io.Writer;
import page.MainFrame;
import page.pack.BackupTreePage;

public class MainBackupAccess {

	public static void main(String[] str) {
		Writer.logPrepare();
		new MainFrame(Data.revVer(MainBCU.ver)).initialize();
		new Timer().start();
		MainFrame.changePanel(new BackupTreePage(null, false));
	}

}
