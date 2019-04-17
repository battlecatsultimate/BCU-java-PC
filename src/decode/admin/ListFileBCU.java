package decode.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class ListFileBCU {

	private static boolean comp = true;

	private final int n;
	private String name;
	private final String[] strs;
	private final int[] len;

	public ListFileBCU(String na, String[] qs) {
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
					System.out.println("len err: " + i + ", " + act + ", " + len[i]);
				writeAll(strs[i], bs);
			}
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void writeAll(String path, byte[] bs) {

		File f = new File(path);
		File p = f.getParentFile();
		if (!p.exists())
			p.mkdirs();
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		PrintStream out = null;
		try {
			out = new PrintStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.write(bs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.close();

	}

}
