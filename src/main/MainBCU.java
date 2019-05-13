package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import decode.ZipLib;
import io.BCJSON;
import io.Reader;
import io.Writer;
import page.MainFrame;
import page.MainPage;
import util.Data;

public class MainBCU {

	public static final int ver = 40605;

	public static int FILTER_TYPE = 0;
	public static boolean write = true, preload = false, trueRun = false, loaded = false;

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
		ZipLib.init();
		BCJSON.checkDownload();
		ZipLib.read();
		Reader.getData$1();
		loaded = true;
		MainFrame.changePanel(new MainPage());
	}

	public static String validate(String str) {
		char[] chs = new char[] { '.', '/', '\\', ':', '*', '?', '"', '<', '>', '|' };
		for (char c : chs)
			str = str.replace(c, '#');
		return str;
	}

}
