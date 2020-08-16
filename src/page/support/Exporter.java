package page.support;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import common.io.OutStream;
import io.BCUWriter;

public class Exporter extends JFileChooser {

	private static final long serialVersionUID = 1L;

	public static final int EXP_DEF = 0, EXP_IMG = 1, EXP_BAC = 2, EXP_ERR = 3, EXP_RES = 4;

	public static final File[] curs = new File[5];

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
			BCUWriter.writeImage(bimg, file);
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
			BCUWriter.writeBytes(os, file.getPath());
		}
	}

}
