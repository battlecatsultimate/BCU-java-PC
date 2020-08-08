package main;

import javax.swing.JOptionPane;

import page.Page;

public class Opts {

	public static final int MEMORY = 1001, SECTY = 1002, REQITN = 1003, INSTALL = 1004;

	private static boolean nshowi, nshowu;

	public static void animErr(String f) {
		if (nshowi)
			return;
		nshowi = !warning("error in reading file " + f + ", Click Cancel to supress this popup?", "IO error");
	}

	public static void backupErr(String t) {
		pop("failed to " + t + " backup", "backup access error");
	}

	public static boolean conf() {
		return warning(Page.get(0, "w0"), "confirmation");
	}

	public static boolean conf(String text) {
		return warning(text, "confirmation");
	}

	public static void dloadErr(String text) {
		pop("failed to download " + text, "download error");
	}

	public static void ioErr(String text) {
		pop(text, "IO error");
	}

	public static void loadErr(String text) {
		pop(text, "loading error");
	}

	public static boolean packConf(String text) {
		return warning(text, "pack conflict");
	}

	public static void pop(int id, String... is) {
		if (id == MEMORY)
			pop("not enough memory. Current memory: " + is[0] + "MB.", "not enough memory");
		if (id == SECTY)
			pop("Failed to access files. Please move BCU folder to another place", "file permission error");
		if (id == REQITN)
			pop("failed to connect to internet while download is necessary", "download error");
		if (id == INSTALL)
			pop("<html>BCU library is not properly installed.<br>Download and run BCU-Installer to install BCU library</html>",
					"library not installed");
	}

	public static void pop(String text, String title) {
		int opt = JOptionPane.PLAIN_MESSAGE;
		JOptionPane.showMessageDialog(null, text, title, opt);
	}

	public static void recdErr(String name, String suf) {
		pop("replay " + name + " uses unavailable " + suf, "replay read error");
	}

	public static void servErr(String text) {
		pop(text, "server error");
	}

	public static void success(String text) {
		pop(text, "success");
	}

	public static void unitErr(String f) {
		if (nshowu)
			return;
		nshowu = !warning(f + ", Click Cancel to supress this popup?", "can't find unit");
	}

	public static boolean updateCheck(String s, String p) {
		return warning(s + " update available. do you want to update? " + p, "update check");
	}

	public static void verErr(String o, String v, String p) {
		pop(o + " version is too old" + (p.isEmpty() ? "" : " for " + p) + ", use BCU " + v + " or " + (o.equals("BCU") ? "newer" : "older")
				+ " version to open it", "version error");
	}

	public static boolean writeErr0(String f) {
		return Opts.warning("failed to write file: " + f + " do you want to retry?", "IO error");
	}

	public static boolean writeErr1(String f) {
		return Opts.warning("failed to write file: " + f + " do you want to save it in another place?", "IO error");
	}

	private static boolean warning(String text, String title) {
		int opt = JOptionPane.OK_CANCEL_OPTION;
		int val = JOptionPane.showConfirmDialog(null, text, title, opt);
		return val == JOptionPane.OK_OPTION;
	}

}
