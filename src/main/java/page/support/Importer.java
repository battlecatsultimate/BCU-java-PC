package page.support;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Importer extends JFileChooser {
	public enum FileType {
		PNG("PNG Images", "png"),
		MUS("OGG Music", "ogg");

		public final String[] ext;
		public final String desc;

		FileType(String description, String... extension) {
			ext = extension;
			desc = description;
		}
	}

	private static final long serialVersionUID = 1L;

	public static final int IMP_DEF = 0, IMP_IMG = 1;

	public static final File[] curs = new File[2];

	public File file;

	public Importer(String str, FileType ft) {
		int t = IMP_IMG;
		setDialogTitle(str);
		FileNameExtensionFilter filter = new FileNameExtensionFilter(ft.desc, ft.ext);
		setCurrentDirectory(curs[t]);
		setFileFilter(filter);
		setDragEnabled(true);
		int returnVal = showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
		}

	}

	public boolean exists() {
		return file != null;
	}

	public BufferedImage getImg() {
		if (file == null)
			return null;

		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
