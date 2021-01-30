package main;

import page.JTF;
import page.MainLocale;
import page.Page;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.io.File;

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
		return warning(Page.get(MainLocale.PAGE, "w0"), "confirmation");
	}

	public static boolean conf(File f) {
		return warning("Are you sure that you want to delete "+f.getName()+"? This can't be undone", "Confirmation");
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

	public static String read(String string) {
		return JOptionPane.showInputDialog(null, string, "");
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

	public static void verErr(String o, String v) {
		pop(o + " version is too old, use BCU " + v + " or " + (o.equals("BCU") ? "newer" : "older")
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

	public static Object[] showTextCheck(String title, String content, boolean defaultCheck) {
		JLabel contents = new JLabel(content);
		JTF text = new JTF();
		JCheckBox check = new JCheckBox("Allow users to copy animation without password");

		Border b = text.getBorder();
		Border e = new EmptyBorder(8, 0, 8 ,0);
		if(b == null) {
			text.setBorder(e);
		} else {
			text.setBorder(new CompoundBorder(e, b));
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(contents);
		panel.add(text);
		panel.add(check);

		check.setSelected(defaultCheck);

		int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {
			Object[] res = new Object[2];

			res[0] = text.getText();
			res[1] = check.isSelected();

			return res;
		} else {
			return null;
		}
	}
}
