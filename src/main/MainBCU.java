package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import decode.Decode;
import io.BCJSON;
import io.Reader;
import io.Writer;
import page.MainFrame;
import page.MainPage;
import util.Data;

public class MainBCU {

	public static final int ver = 40510, LIBREQ = 80503;

	public static int FILTER_TYPE = 0;
	public static boolean write = true, preload = false, trueRun = false;

	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static void main(String[] args) {
		trueRun = true;
		long mem = Runtime.getRuntime().maxMemory();
		if (mem >> 28 == 0) {
			Opts.pop(Opts.MEMORY, "" + (mem >> 20));
			System.exit(0);
		}
		Reader.getData$0();
		Writer.logSetup();
		new MainFrame(Data.revVer(MainBCU.ver)).initialize();
		new Timer().start();
		BCJSON.checkDownload();
		if (BCJSON.lib_ver < LIBREQ) {
			Opts.loadErr("this version requires new lib");
		}
		Decode.main();
		Reader.getData$1();
		MainFrame.changePanel(new MainPage());
	}

	public static String validate(String str) {
		char[] chs = new char[] { '.', '/', '\\', ':', '*', '?', '"', '<', '>', '|' };
		for (char c : chs)
			str = str.replace(c, '#');
		return str;
	}

}
