package io;

import java.util.List;
import common.CommonStatic;
import common.io.assets.AssetLoader;
import common.io.assets.UpdateCheck;
import common.io.assets.UpdateCheck.Downloader;
import common.io.assets.UpdateCheck.UpdateJson;
import common.util.Data;
import main.Opts;
import page.LoadPage;

public class BCJSON {

	public static void check() {
		LoadPage.prog("checking update information");
		UpdateJson json = Data.ignore(UpdateCheck::checkUpdate);
		List<Downloader> assets = null, musics = null, libs = null;
		try {
			libs = UpdateCheck.checkPCLibs(json);
			assets = UpdateCheck.checkAsset(json, "pc");
			if (json != null)
				musics = UpdateCheck.checkMusic(json.music);
		} catch (Exception e) {
			Opts.pop(e.getMessage(), "FATAL ERROR");
			e.printStackTrace();
			CommonStatic.def.exit(false);
		}
		clearList(libs, true);
		clearList(assets, true);
		clearList(musics, false);
		while (!Data.err(AssetLoader::merge))
			if (!Opts.conf("failed to process assets, retry?"))
				CommonStatic.def.exit(false);
	}

	private static boolean clearList(List<Downloader> list, boolean quit) {
		boolean load = false;
		if (list != null)
			for (Downloader d : list) {
				LoadPage.prog(d.desc);
				boolean l;
				while (!(l = Data.err(() -> d.run(LoadPage.lp::accept))))
					if (!Opts.conf("failed to download, retry?"))
						if (quit)
							CommonStatic.def.exit(false);
						else
							break;
				load |= l;
			}
		return load;
	}

}
