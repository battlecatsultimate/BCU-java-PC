package main;

import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import common.CommonStatic;
import common.io.assets.Admin;
import common.pack.UserProfile;
import common.system.fake.ImageBuilder;
import common.util.Data;
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

	public static final int ver = 50000;

	public static int FILTER_TYPE = 0;
	public static final boolean WRITE = !new File("./.project").exists();
	public static boolean preload = false, trueRun = true, loaded = false, USE_JOGL = true;
	public static boolean light = false, nimbus = true;

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

		UserProfile.profile();
		CommonStatic.def = new UtilPC.PCItr();

		// Writer.logPrepare();
		// Writer.logSetup();
		// Reader.getData$0();

		ImageBuilder.builder = USE_JOGL ? new GLIB() : new PCIB();
		BBBuilder.def = USE_JOGL ? new GLBBB() : AWTBBB.INS;
		CommonStatic.ctx = new Admin.AdminContext();
		CommonStatic.ctx.initProfile();

		if (nimbus) {
			if (light) {
				Theme.LIGHT.setTheme();
				Page.BGCOLOR = new Color(255, 255, 255);
			} else {
				Theme.DARK.setTheme();
				Page.BGCOLOR = new Color(40, 40, 40);
			}
		} else {
			Page.BGCOLOR = new Color(255, 255, 255);
		}

		new MainFrame(Data.revVer(MainBCU.ver)).initialize();
		new Timer().start();
		// AssetLoader.previewAssets();
		// BCJSON.checkDownload();
		// Reader.getData$0();
		// Reader.getData$1();
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
