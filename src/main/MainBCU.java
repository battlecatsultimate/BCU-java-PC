package main;

import common.CommonStatic;
import common.battle.BasisSet;
import common.io.PackLoader.ZipDesc.FileDesc;
import common.io.assets.Admin;
import common.io.assets.AssetLoader;
import common.pack.Context;
import common.pack.Source.Workspace;
import common.pack.UserProfile;
import common.system.fake.ImageBuilder;
import common.util.Data;
import io.BCJSON;
import io.BCUReader;
import io.BCUWriter;
import jogl.GLBBB;
import jogl.util.GLIB;
import page.LoadPage;
import page.MainFrame;
import page.MainPage;
import page.Page;
import page.awt.AWTBBB;
import page.awt.BBBuilder;
import utilpc.Theme;
import utilpc.UtilPC;
import utilpc.awt.PCIB;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainBCU {

	public static class AdminContext implements Context {

		@Override
		public boolean confirmDelete() {
			System.out.println("skip delete confirmation");
			return true;
		}

		@Override
		public File getAssetFile(String string) {
			return new File("./assets/" + string);
		}

		@Override
		public InputStream getLangFile(String file) {
			File f = new File("./assets/lang/en/" + file);
			if (!f.exists())
				return null;
			return Data.err(() -> new FileInputStream(f));
		}

		@Override
		public File getPackFolder() {
			return new File("./packs");
		}

		@Override
		public File getUserFile(String string) {
			return new File("./user/" + string);
		}

		@Override
		public File getWorkspaceFile(String relativePath) {
			return new File("./workspace/" + relativePath);
		}

		@Override
		public void initProfile() {
			LoadPage.prog("read assets");
			AssetLoader.load();
			LoadPage.prog("read BC data");
			UserProfile.getBCData().load(LoadPage::prog);
			LoadPage.prog("read basis");
			BasisSet.read();
			LoadPage.prog("read local animations");
			Workspace.loadAnimations(null);
			LoadPage.prog("read packs");
			UserProfile.loadPacks();
			LoadPage.prog("finish reading");
		}

		@Override
		public void noticeErr(Exception e, ErrType t, String str) {
			printErr(t, str);
			e.printStackTrace(t == ErrType.INFO ? System.out : System.err);
		}

		@Override
		public boolean preload(FileDesc desc) {
			return Admin.preload(desc);
		}

		@Override
		public void printErr(ErrType t, String str) {
			(t == ErrType.INFO ? System.out : System.err).println(str);
		}

	}

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
		CommonStatic.ctx = new AdminContext();

		BCUWriter.logPrepare();
		BCUWriter.logSetup();
		BCUReader.readInfo();

		ImageBuilder.builder = USE_JOGL ? new GLIB() : new PCIB();
		BBBuilder.def = USE_JOGL ? new GLBBB() : AWTBBB.INS;

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

		BCJSON.check();
		CommonStatic.ctx.initProfile();

		BCUReader.getData$1();
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
