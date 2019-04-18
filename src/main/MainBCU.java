package main;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import decode.Decode;
import io.BCJSON;
import io.Reader;
import io.Writer;
import page.MainFrame;
import page.MainPage;
import util.Data;

public class MainBCU {

	public static final int ver = 40405, LIBREQ = 80400;

	public static int FILTER_TYPE = 0;
	public static boolean write = true, preload = false, trueRun = false;

	public static String getTime() {
		return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	}

	public static void main(String[] args) {
		//TEST REBASE
		trueRun = true;
		long mem = Runtime.getRuntime().maxMemory();
		if (mem >> 28 == 0) {
			pop("not enough memory. Current memory: " + (mem >> 20) + "MB.", "not enough memory");
			System.exit(0);
		}
		Reader.getData$0();
		Writer.logSetup();
		new MainFrame(Data.revVer(MainBCU.ver)).initialize();
		new Timer().start();
		BCJSON.checkDownload();
		if (BCJSON.lib_ver < LIBREQ) {
			pop("this version require new lib", "library error");
			System.exit(0);
		}
		Decode.main();
		Reader.getData$1();
		MainFrame.changePanel(new MainPage());
	}

	public static void pop(String text, String title) {
		int opt = JOptionPane.PLAIN_MESSAGE;
		JOptionPane.showMessageDialog(null, text, title, opt);
	}

	public static String validate(String str) {
		char[] chs = new char[] { '.', '/', '\\', ':', '*', '?', '"', '<', '>', '|' };
		for (char c : chs)
			str = str.replace(c, '#');
		return str;
	}

	public static boolean warning(String text, String title) {
		int opt = JOptionPane.OK_CANCEL_OPTION;
		int val = JOptionPane.showConfirmDialog(null, text, title, opt);
		return val == JOptionPane.OK_OPTION;
	}

}
