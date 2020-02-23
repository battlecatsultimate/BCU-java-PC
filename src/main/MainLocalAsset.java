package main;

import java.io.File;
import java.io.IOException;
import decode.ZipLib;

public class MainLocalAsset {

	public static void main(String[] args) {
		try {
			Opts.pop("Merging ./assets/local/*.zip with ./assets/assets.zip", "begin");
			File dir = new File("./assets/local/");
			if (dir.exists() && dir.listFiles() != null) {
				ZipLib.init();
				for (File f : dir.listFiles())
					if (f.getName().endsWith(".zip")) {
						Opts.pop("Merging ./assets/local/" + f.getName() + " with ./assets/assets.zip", "processing");
						ZipLib.merge(f);
					}
				try {
					ZipLib.lib.close();
				} catch (IOException e) {
					Opts.ioErr("failed to save downloads");
					e.printStackTrace();
				}
			}

			Opts.pop("Merging finished", "end");
		} catch (Exception e) {
			Opts.pop(e.toString(), "Unknown Error");
			e.printStackTrace();
		}
	}

}
