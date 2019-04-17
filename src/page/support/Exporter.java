package page.support;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Queue;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.OutStream;
import io.Reader;
import io.Writer;
import io.ZipAccess;

public class Exporter extends JFileChooser {

	private static final long serialVersionUID = 1L;

	public static final int EXP_DEF = 0, EXP_IMG = 1, EXP_BAC = 2, EXP_ERR = 3, EXP_RES = 4;

	public static final File[] curs = new File[5];

	public static void read(Queue<String> qs) {
		int n = Reader.parseIntN(qs.poll());
		for (int i = 0; i < n; i++) {
			String str = qs.poll().trim();
			if (str.length() > 0)
				curs[i] = new File(str);
		}
	}

	public static void write(PrintStream out) {
		out.println("Export path count: " + curs.length);
		for (int i = 0; i < curs.length; i++) {
			if (curs[i] == null)
				out.println("");
			else
				out.println(curs[i].getPath());
		}
	}

	public File file;

	public Exporter(BufferedImage bimg, int t) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		setCurrentDirectory(curs[t]);
		setFileFilter(filter);
		setDragEnabled(true);
		int returnVal = showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
			Writer.writeImage(bimg, file);
		}

	}

	public Exporter(int t) {
		setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		setCurrentDirectory(curs[t]);
		setDragEnabled(true);
		int returnVal = showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
			if (!file.isDirectory())
				file = file.getParentFile();
		}

	}

	public Exporter(OutStream os, int t) {
		setDragEnabled(true);
		setCurrentDirectory(curs[t]);
		int returnVal = showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
			Writer.writeBytes(os, file.getPath());
		}
	}

	public Exporter(String time, String path, String ext, int t) {
		FileNameExtensionFilter filter = new FileNameExtensionFilter("file", ext);
		setFileFilter(filter);
		setCurrentDirectory(curs[t]);
		setDragEnabled(true);
		int returnVal = showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
			try {
				ZipAccess.export(time, path, file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
