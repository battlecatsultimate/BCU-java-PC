package io;

import common.CommonStatic;
import common.io.assets.AssetLoader;
import common.io.assets.UpdateCheck;
import common.io.assets.UpdateCheck.Downloader;
import common.io.assets.UpdateCheck.UpdateJson;
import common.pack.Context.ErrType;
import common.util.Data;
import main.MainBCU;
import main.Opts;
import page.LoadPage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BCJSON {

	public static final String[] PC_LANG_CODES = { "en", "jp", "kr", "zh", "fr", "it", "es", "de", "ru" };
	public static final String[] PC_LANG_FILES = { "util.properties", "page.properties", "info.properties",
			"StageName.txt", "UnitName.txt", "UnitExplanation.txt", "EnemyName.txt", "EnemyExplanation.txt", "ComboName.txt", "RewardName.txt", "proc.json", "animation_type.json", "CatFruitExplanation.txt" };
	public static final String JAR_LINK = "https://github.com/battlecatsultimate/bcu-assets/raw/master/jar/BCU-";
	public static final String ALT_LINK = "https://gitee.com/lcy0x1/bcu-assets/raw/master/jar/BCU-";

	public static void check() {
		LoadPage.prog("checking update information");
		UpdateJson json = Data.silent(UpdateCheck::checkUpdate);

		CommonStatic.Config cfg = CommonStatic.getConfig();
		List<Downloader> assets = null, musics = null, custMusics = null, libs = null, lang;
		UpdateJson.JarJson[] jars = null;
		try {
			for (UpdateJson.AnnouncementJson announce : json.pc_announcement) {
				if (cfg.receivedAnnouncements.contains(announce.id + "_PC") || MainBCU.ver < announce.min_ver || MainBCU.ver > announce.max_ver)
					continue;

                if (Opts.warningLong(announce.title + "\n" + String.join("\n\n", announce.text), "Announcement", 700, 350))
					cfg.receivedAnnouncements.add(announce.id + "_PC");
			}
			jars = getLatestJars(json);
			libs = UpdateCheck.checkPCLibs(json);
			assets = UpdateCheck.checkAsset(json, "pc");
		} catch (Exception e) {
			Opts.pop(e.getMessage(), "FATAL ERROR");
			e.printStackTrace();
			CommonStatic.def.save(false, true);
		}

		if (json != null) {
			int count = json.music;
			if (cfg.updateOldMusic) {
				try {
					musics = UpdateCheck.checkMusic(count).get();
				} catch (Exception ignored) {
					musics = new ArrayList<>();
				}
			} else
				musics = UpdateCheck.checkNewMusic(count);

			int customCount = json.customMusic;
			custMusics = UpdateCheck.checkNewCustomMusic(customCount);
		}

		ArrayList<String> langList = new ArrayList<>();
		for (String pcLangCode : PC_LANG_CODES) {
			for (String pcLangFile : PC_LANG_FILES)
				langList.add(pcLangCode + "/" + pcLangFile);
		}

		try {
			lang = UpdateCheck.checkLang(langList.toArray(new String[0])).get();
		} catch (Exception ignored) {
			lang = new ArrayList<>();
		}

		clearList(libs, true);
		clearList(assets, true);
		clearList(musics, false);
		clearList(custMusics, false);
		clearList(lang, false);

		Downloader font = UpdateCheck.checkFont();
		if (font != null) {
			LoadPage.prog(font.desc);
			while (!CommonStatic.ctx.noticeErr(() -> font.run(LoadPage.lp::accept), ErrType.DEBUG, "failed to download"))
				if (!Opts.conf("failed to download, retry?"))
					break;
		}

		while (!Data.err(AssetLoader::merge))
			if (!Opts.conf("failed to process assets, retry?"))
				CommonStatic.def.save(false, true);

		if (jars != null && jars.length != 0) {
			StringBuilder sb = new StringBuilder("New jar " + Data.revVer(jars[0].ver) + " is out. Update notes since current jar:\n\n");
			for (UpdateJson.JarJson info : jars) {
				sb.append(Data.revVer(info.ver))
						.append(": ")
						.append(info.desc)
						.append("\n");
			}
			sb.append("\nDo you want to update jar?");
			boolean updateIt = Opts.confLong(sb.toString(), 1000, 400);

			if (updateIt) {
				UpdateJson.JarJson jar = jars[0];
				String ver = Data.revVer(jar.ver);
				File target = new File(CommonStatic.ctx.getBCUFolder(), "./BCU-" + ver + ".jar");
				File temp = new File(CommonStatic.ctx.getBCUFolder(), "./temp.temp");
				String url = JAR_LINK + ver + ".jar";
				String alt = ALT_LINK + ver + ".jar";

				Downloader down = new Downloader(target, temp, "Downloading BCU-" + ver + ".jar...", false, url, alt);

				LoadPage.prog(down.desc);

				boolean done;

				while (!(done = CommonStatic.ctx.noticeErr(() -> down.run(LoadPage.lp::accept), ErrType.DEBUG,
						"failed to download")))
					if (!Opts.conf("failed to download, retry?"))
						break;

				if (done) {
					Opts.pop("Finished downloading BCU-" + ver + ".jar. Run this jar file from now on",
							"Download finished");

					CommonStatic.def.save(false, true);
				}
			}
		}
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
							CommonStatic.def.save(false, true);
						else
							break;
				load |= l;
			}
		return load;
	}

	private static UpdateJson.JarJson[] getLatestJars(UpdateJson json) {
		if (json == null)
			return null;

		List<UpdateJson.JarJson> jars = new ArrayList<>();
		for (UpdateJson.JarJson jar : json.pc_update) {
			if (jar == null)
				continue;
			if (MainBCU.ver < jar.ver)
				jars.add(jar);
		}

		return jars.toArray(new UpdateJson.JarJson[0]);
	}

	private static UpdateJson.JarJson getLatestJar(UpdateJson json) {
		if (json == null)
			return null;

		for (UpdateJson.JarJson jar : json.pc_update) {
			if (jar == null)
				continue;

			if (MainBCU.ver < jar.ver)
				return jar;
		}

		return null;
	}
}
