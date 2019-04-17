package decode.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.List;

public class DecodeBCU {

	private static boolean write = false;

	public static void main(String[] args) {
		PrintStream ps = null;
		if (write) {
			File log = new File("./log.txt");
			if (!log.getParentFile().exists())
				log.getParentFile().mkdirs();
			if (!log.exists())
				try {
					log.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
					return;
				}
			try {
				ps = new PrintStream(log);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return;
			}
			System.setOut(ps);
			System.setErr(ps);
		}
		try {
			File fl = new File("./raw/viewer.list");
			File fp = new File("./raw/viewer.pack");
			List<String> bs = null;
			try {
				bs = Files.readAllLines(fl.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] strs = bs.toArray(new String[0]);
			String sp = fl.getName();
			sp = sp.substring(0, sp.length() - 5);
			ListFileBCU lf = new ListFileBCU(sp, strs);
			lf.readBytes(fp);

			System.out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (write)
			ps.close();
	}

}
