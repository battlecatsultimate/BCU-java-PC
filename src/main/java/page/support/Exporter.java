package page.support;

import common.CommonStatic;
import common.io.OutStream;
import common.pack.Context;
import io.BCUWriter;
import main.Opts;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

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
			String s = file.getName();
			if (!s.toLowerCase(Locale.ROOT).endsWith(".png")) {
				File fpng = new File(s + ".png");
				boolean success = file.renameTo(fpng);
				if (!success)
					System.out.println("Failed to add .png extension to file");
				fpng.delete();
			}
			if (file.exists()) {
				boolean verifyFile = Opts.conf("A file with this name already exists. Save anyway?");
				if (!verifyFile)
					return;
			}
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

	public Exporter(InputStream ins, String name) {
		setFileSelectionMode(DIRECTORIES_ONLY);
		setCurrentDirectory(curs[EXP_BAC]);
		setDragEnabled(true);

		int returnval = showSaveDialog(null);

		if(returnval == JFileChooser.APPROVE_OPTION) {
			file = getSelectedFile();

			String[] f = name.split("\\.");

			File target = getSafeFile(file, f[0], f[1]);

			CommonStatic.ctx.noticeErr(() -> {
				if(!target.exists() && !target.createNewFile())
					throw new IOException("Couldn't create file : "+target);

				FileOutputStream fos = new FileOutputStream(target);

				byte[] b = new byte[65536];
				int len;

				while((len = ins.read(b)) != -1) {
					fos.write(b, 0, len);
				}

				fos.close();

				Opts.pop(target.getName()+" is successfully exported", "Exporting complete");
			}, Context.ErrType.WARN, "Failed to export file");
		}
	}

	private File getSafeFile(File folder, String name, String extension) {
		int i = 0;

		while(true) {
			if(i == 0) {
				String fileName = name+"."+extension;

				File f = new File(folder, fileName);

				if(!f.exists())
					return f;
			} else {
				String fileName = name+"-"+i+"."+extension;

				File f = new File(folder, fileName);

				if(!f.exists())
					return f;
			}

			i++;
		}
	}
}
