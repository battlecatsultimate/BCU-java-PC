package io;

import java.util.List;
import common.CommonStatic;
import common.io.assets.AssetLoader;
import common.io.assets.UpdateCheck;
import common.io.assets.UpdateCheck.Downloader;
import common.io.assets.UpdateCheck.UpdateJson;
import common.pack.Context.ErrType;
import common.util.Data;
import main.Opts;
import page.LoadPage;

public class BCJSON {

	public static final String[] PC_LANG_CODES = { "en", "jp", "kr", "zh" };
	public static final String[] PC_LANG_FILES = { "util.properties", "page.properties", "info.properties",
			"StageName.txt", "UnitName.txt", "EnemyName.txt", "proc.json", "animation_type.json" };

	public static void check() {
		LoadPage.prog("checking update information");
		UpdateJson json = Data.ignore(UpdateCheck::checkUpdate);
		List<Downloader> assets = null, musics = null, libs = null, lang = null;
		try {
			libs = UpdateCheck.checkPCLibs(json);
			assets = UpdateCheck.checkAsset(json, "pc");
		} catch (Exception e) {
			Opts.pop(e.getMessage(), "FATAL ERROR");
			e.printStackTrace();
			CommonStatic.def.exit(false);
		}
		int music = json != null ? json.music : Data.SE_ALL[Data.SE_ALL.length - 1] + 1;
		musics = UpdateCheck.checkMusic(music);
		String[] langs = new String[PC_LANG_CODES.length * PC_LANG_FILES.length];
		for (int i = 0; i < PC_LANG_CODES.length; i++)
			for (int j = 0; j < PC_LANG_FILES.length; j++)
				langs[i * PC_LANG_FILES.length + j] = PC_LANG_CODES[i] + "/" + PC_LANG_FILES[j];
		lang = Data.err(UpdateCheck.checkLang(langs));
		clearList(libs, true);
		clearList(assets, true);
		clearList(musics, false);
		clearList(lang, false);
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
				while (!(l = CommonStatic.ctx.noticeErr(() -> d.run(LoadPage.lp::accept), ErrType.DEBUG,
						"failed to download")))
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
