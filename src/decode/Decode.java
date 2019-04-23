package decode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import io.Writer;
import main.Opts;
import page.LoadPage;

public class Decode {

	public static void main() {
		File f = new File("./lib/");
		if (!f.exists()) {
			Opts.loadErr("cannot find ./lib/");
			Writer.logClose(false);
			System.exit(0);
		}
		LoadPage.prog(1, 1, 0);
		File[] fs = f.listFiles();
		int i = 0;
		for (File fi : fs) {
			LoadPage.prog(0, i++, fs.length);
			String str = fi.getName();
			if (str.length() != 3)
				continue;
			String strl = "./lib/" + str + "/viewer.";
			File fl = new File(strl + "list");
			File fp = new File(strl + "pack");
			if (!fl.exists()) {
				Opts.loadErr("cannot find " + strl + "list");
				System.exit(0);
			}
			if (!fp.exists()) {
				Opts.loadErr("cannot find " + strl + "pack");
				System.exit(0);
			}
			List<String> bs = null;
			try {
				bs = Files.readAllLines(fl.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			String[] strs = bs.toArray(new String[0]);
			String sp = fl.getName();
			sp = sp.substring(0, sp.length() - 5);
			ListFile lf = new ListFile(sp, strs);
			lf.readBytes(fp);
		}
		LoadPage.prog(1, 1, 0);
	}

}
