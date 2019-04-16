package decode.admin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class ListFilePack {

	private static boolean comp = true;

	private final int n;
	private String name;
	private final String[] strs;
	private final int[] start, len;
	protected byte[][] bss;

	public ListFilePack(String na, String[] qs) {
		name = na;
		if (comp && Character.isUpperCase(name.charAt(1)))
			name = name.substring(1);
		n = Integer.parseInt(qs[0]);
		strs = new String[n];
		start = new int[n];
		len = new int[n];
		for (int i = 0; i < n; i++) {
			String[] str = qs[i + 1].trim().split(",");
			strs[i] = str[0].trim();
			start[i] = Integer.parseInt(str[1].trim());
			len[i] = Integer.parseInt(str[2].trim());
		}
	}

	protected void readBytes(File file) {
		bss = new byte[n][];
		InputStream in = null;
		try {
			in = new FileInputStream(file);
			in = new BufferedInputStream(in);
			int tot = 0;
			for (int i = 0; i < n; i++) {
				byte[] bs = new byte[len[i]];
				if (tot != start[i])
					System.out.println("start err: " + i + ", " + tot + ", " + start[i]);
				int act = in.read(bs);
				tot += act;
				if (act != len[i])
					System.out.println("len err: " + i + ", " + act + ", " + len[i]);
				if (name.equals("ImageDataLocal"))
					bss[i] = bs;
				else
					bss[i] = DecodePack.decrypt(DecodePack.pack, bs);
			}
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	protected void writeAll() {
		File p = new File("./out/" + name + "/");
		if (!p.exists())
			p.mkdirs();
		for (int i = 0; i < n; i++) {
			File f = new File("./out/" + name + "/" + strs[i]);
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
				out.write(bss[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		}
	}

}
