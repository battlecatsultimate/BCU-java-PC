package page.support;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.Reader;

public class Importer extends JFileChooser {

	private static final long serialVersionUID = 1L;

	public static final int IMP_DEF = 0, IMP_IMG = 1;

	public static final File[] curs = new File[2];

	public static void read(Queue<String> qs) {
		int n = Reader.parseIntN(qs.poll());
		for (int i = 0; i < n; i++) {
			String str = qs.poll().trim();
			if (str.length() > 0)
				curs[i] = new File(str);
		}
	}

	public static void write(PrintStream out) {
		out.println("Import path count: " + curs.length);
		for (int i = 0; i < curs.length; i++) {
			if (curs[i] == null)
				out.println("");
			else
				out.println(curs[i].getPath());
		}
	}

	public File file;

	public Importer(String str) {
		int t = IMP_IMG;
		setDialogTitle(str);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
		setCurrentDirectory(curs[t]);
		setFileFilter(filter);
		setDragEnabled(true);
		int returnVal = showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();
			curs[t] = getCurrentDirectory();
		}

	}

	public BufferedImage getImg() {
		if (file == null)
			return null;
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return bimg;
	}

}
