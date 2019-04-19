package decode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import io.Writer;
import main.MainBCU;
import page.LoadPage;

public class Decode {

	public static void main() {
		File f = new File("./lib/");
		if (!f.exists()) {
			MainBCU.pop("cannot find ./lib/", "liberary error");
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
				MainBCU.pop("cannot find " + strl + "list", "liberary error");
				System.exit(0);
			}
			if (!fp.exists()) {
				MainBCU.pop("cannot find " + strl + "pack", "liberary error");
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
