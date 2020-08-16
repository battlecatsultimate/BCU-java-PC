package page.support;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Importer extends JFileChooser {

	private static final long serialVersionUID = 1L;

	public static final int IMP_DEF = 0, IMP_IMG = 1;

	public static final File[] curs = new File[2];

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
