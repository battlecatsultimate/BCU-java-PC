package decode;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import main.Printer;
import util.system.VFile;

class ListFile {

	private static boolean comp = true;

	private final int n;
	private String name;
	private final String[] strs;
	private final int[] len;

	public ListFile(String na, String[] qs) {
		name = na;
		if (comp && Character.isUpperCase(name.charAt(1)))
			name = name.substring(1);
		n = qs.length;
		strs = new String[n];
		len = new int[n];
		for (int i = 0; i < n; i++) {
			String[] str = qs[i].trim().split(",");
			strs[i] = str[0].trim();
			len[i] = Integer.parseInt(str[1].trim());
		}
	}

	protected void readBytes(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			in = new BufferedInputStream(in);
			for (int i = 0; i < n; i++) {
				byte[] bs = new byte[len[i]];
				int act = in.read(bs);
				if (act != len[i])
					Printer.e("ListFile", 43, "len err: " + i + ", " + act + ", " + len[i]);
				VFile.newFile(strs[i], bs);
			}
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
