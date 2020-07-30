package main;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.CommonStatic;
import common.system.fake.ImageBuilder;
import common.util.Data;
import decode.ZipLib;
import io.BCJSON;
import io.Reader;
import io.Writer;
import jogl.GLBBB;
import jogl.util.GLIB;
import page.MainFrame;
import page.MainPage;
import page.Page;
import page.awt.AWTBBB;
import page.awt.BBBuilder;
import utilpc.Theme;
import utilpc.UtilPC;
import utilpc.awt.PCIB;

public class MainBCU {

	public static final int ver = 41000;

	public static int FILTER_TYPE = 0;
	public static final boolean WRITE = !new File("./.project").exists();
	public static boolean preload = false, trueRun = false, loaded = false, USE_JOGL = false;
	public static boolean light = false;

	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static void main(String[] args) {
		Writer.logPrepare();
		Writer.logSetup();
		Reader.getData$0();

		ImageBuilder.builder = USE_JOGL ? new GLIB() : new PCIB();
		BBBuilder.def = USE_JOGL ? new GLBBB() : AWTBBB.INS;
		CommonStatic.def = new UtilPC.PCItr();
		
		if(light) {
			Theme.LIGHT.setTheme();
			Page.BGCOLOR = new Color(255, 255, 255);
		} else {
			Theme.DARK.setTheme();
			Page.BGCOLOR = new Color(40, 40, 40);
		}

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
