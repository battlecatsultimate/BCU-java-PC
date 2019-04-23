package main;

import javax.swing.JOptionPane;

import page.Page;

public class Opts {

	private static boolean nshowi, nshowu;

	public static void p$b(String t) {
		pop("failed to " + t + " backup", "backup access error");
	}

	public static void p$d(String text) {
		pop("failed to download " + text, "download error");
	}

	public static void p$l(String text) {
		pop(text, "loading error");
	}

	public static void p$rp(String name, String suf) {
		pop("replay " + name + " uses unavailable " + suf, "replay read error");
	}

	public static void p$srv(String text) {
		pop(text, "server error");
	}

	public static void p$v(String o, String v) {
		Opts.pop(o + " version is too old, use BCU " + v + " or newer version to open it", "version error");
	}

	public static void pop(String text, String title) {
		int opt = JOptionPane.PLAIN_MESSAGE;
		JOptionPane.showMessageDialog(null, text, title, opt);
	}

	public static boolean w$c() {
		return warning(Page.get(0, "w0"), "confirmation");
	}

	public static boolean w$c(String text) {
		return warning(text, "confirmation");
	}

	public static void w$i(String f) {
		if (nshowi)
			return;
		nshowi = !warning("error in reading file " + f + ", Click Cancel to supress this popup?", "IO error");
	}

	public static boolean w$o(String f) {
		return Opts.warning("failed to write file: " + f + " do you want to save it in another place?", "IO error");
	}

	public static boolean w$pc(String text) {
		return warning(text, "pack conflict");
	}

	public static void w$u(String f) {
		if (nshowu)
			return;
		nshowu = !warning(f + ", Click Cancel to supress this popup?", "can't find unit");
	}

	public static boolean w$upd(String s, String p) {
		return Opts.warning(s + " update available. do you want to update? " + p, "update check");
	}

	private static boolean warning(String text, String title) {
		int opt = JOptionPane.OK_CANCEL_OPTION;
		int val = JOptionPane.showConfirmDialog(null, text, title, opt);
		return val == JOptionPane.OK_OPTION;
	}

}
