package main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import decode.ZipLib;
import io.BCJSON;
import io.Reader;
import io.Writer;
import page.MainFrame;
import page.MainPage;
import page.battle.AWTBBB;
import page.battle.BBBuilder;
import util.Data;
import util.system.fake.ImageBuilder;
import util.system.fake.awt.PCIB;

public class MainBCU {

	public static final int ver = 40613;

	public static int FILTER_TYPE = 0;
	public static final boolean write = !new File("./.project").exists();
	public static boolean preload = false, trueRun = false, loaded = false;

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
		ImageBuilder.builder = new PCIB();
		BBBuilder.def = AWTBBB.INS;
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
