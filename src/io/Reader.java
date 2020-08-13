package io;

import static java.lang.Character.isDigit;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import common.CommonStatic;
import common.CommonStatic.Config;
import common.io.DataIO;
import common.io.InStream;
import common.pack.UserProfile;
import common.util.lang.MultiLangCont;
import common.util.stage.MapColc;
import common.util.stage.MapColc.DefMapColc;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import common.util.unit.Enemy;
import common.util.unit.Unit;
import main.MainBCU;
import main.Opts;
import page.LoadPage;
import page.MainFrame;
import page.MainLocale;
import page.battle.BattleInfoPage;
import page.support.Exporter;
import page.support.Importer;
import page.view.ViewBox;

public class Reader extends DataIO {

	public static void getData$0() {
		readInfo();
	}

	public static void getData$1() {
		readLang();
		BCMusic.preload();
	}

	public static InStream readBytes(File file) {
		try {
			byte[] bs = Files.readAllBytes(file.toPath());
			return InStream.getIns(bs);
		} catch (IOException e) {
			Opts.ioErr("failed to read file " + file);
			e.printStackTrace();
		}
		return null;
	}

	public static void readLang() {
		LoadPage.prog("reading language information");
		File f = new File("./assets/lang/");
		if (!f.exists())
			return;
		for (File fi : f.listFiles()) {
			String ni = fi.getName();
			if (!fi.isDirectory())
				continue;
			if (ni.length() != 2)
				continue;
			for (File fl : fi.listFiles())
				try {
					String nl = fl.getName();

					if (nl.equals("tutorial.txt")) {
						Queue<String> qs = readLines(fl);
						for (String line : qs) {
							String[] strs = line.trim().split("\t");
							if (strs.length != 3)
								continue;
							MainLocale.addTTT(ni, strs[0].trim(), strs[1].trim(), strs[2].trim());
						}
						continue;
					}
					if (nl.equals("StageName.txt")) {
						Queue<String> qs = readLines(fl);
						if (qs != null)
							for (String str : qs) {
								String[] strs = str.trim().split("\t");
								if (strs.length == 1)
									continue;
								String idstr = strs[0].trim();
								String name = strs[strs.length - 1].trim();
								if (idstr.length() == 0 || name.length() == 0)
									continue;
								String[] ids = idstr.split("-");
								int id0 = CommonStatic.parseIntN(ids[0]);
								MapColc mc = DefMapColc.getMap(id0 * 1000).mc;
								if (mc == null)
									continue;
								if (ids.length == 1) {
									MultiLangCont.getStatic().MCNAME.put(ni, mc, name);
									continue;
								}
								int id1 = CommonStatic.parseIntN(ids[1]);
								if (id1 >= mc.maps.length || id1 < 0)
									continue;
								StageMap sm = mc.maps[id1];
								if (sm == null)
									continue;
								if (ids.length == 2) {
									MultiLangCont.getStatic().SMNAME.put(ni, sm, name);
									continue;
								}
								int id2 = CommonStatic.parseIntN(ids[2]);
								if (id2 >= sm.list.size() || id2 < 0)
									continue;
								Stage st = sm.list.get(id2);
								MultiLangCont.getStatic().STNAME.put(ni, st, name);
							}
						continue;
					}
					if (nl.equals("UnitName.txt")) {
						Queue<String> qs = readLines(fl);
						for (String str : qs) {
							String[] strs = str.trim().split("\t");
							Unit u = UserProfile.getBCData().units.get(CommonStatic.parseIntN(strs[0]));
							if (u == null)
								continue;
							for (int i = 0; i < Math.min(u.forms.length, strs.length - 1); i++)
								MultiLangCont.getStatic().FNAME.put(ni, u.forms[i], strs[i + 1].trim());
						}
						continue;
					}
					if (nl.equals("EnemyName.txt")) {
						Queue<String> qs = readLines(fl);
						for (String str : qs) {
							String[] strs = str.trim().split("\t");
							Enemy e = UserProfile.getBCData().enemies.get(CommonStatic.parseIntN(strs[0]));
							if (e == null || strs.length < 2)
								continue;
							MultiLangCont.getStatic().ENAME.put(ni, e, strs[1].trim());
						}
						continue;
					}
					if (!nl.endsWith(".properties"))
						continue;
					MainLocale ml = new MainLocale(nl.split("\\.")[0] + "_" + ni);
					Queue<String> qs = readLines(fl);
					for (String line : qs) {
						String[] strs = line.split("=|\t", 2);
						if (strs.length < 2)
							continue;
						ml.res.put(strs[0], strs[1]);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

		}
	}

	public static Queue<String> readLines(File file) {
		try {
			return new ArrayDeque<>(Files.readAllLines(file.toPath()));
		} catch (IOException e) {
			Opts.ioErr("failed to read file " + file);
			e.printStackTrace();
			return null;
		}

	}

	private static int parseInt(String str) {
		return parseInts(1, str)[0];
	}

	private static int[] parseInts(int n, String str) {
		ArrayList<String> lstr = new ArrayList<>();
		int t = -1;
		for (int i = 0; i < str.length(); i++)
			if (t == -1) {
				if (isDigit(str.charAt(i)) || str.charAt(i) == '-' || str.charAt(i) == '+')
					t = i;
			} else if (!isDigit(str.charAt(i))) {
				lstr.add(str.substring(t, i));
				t = -1;
			}
		if (t != -1)
			lstr.add(str.substring(t));
		int ind = 0;
		while (ind < lstr.size()) {
			if (isDigit(lstr.get(ind).charAt(0)) || lstr.get(ind).length() > 1)
				ind++;
			else
				lstr.remove(ind);
		}
		int[] ans = new int[n];
		for (int i = lstr.size() - n; i < lstr.size(); i++)
			ans[i - lstr.size() + n] = Integer.parseInt(lstr.get(i));
		return ans;
	}

	private static void readInfo() {
		try {
			File f = new File("./user/data.ini");
			if (f.exists()) {
				try {
					Config cfg = CommonStatic.getConfig();
					Queue<String> qs = readLines(f);
					cfg.lang = parseInt(qs.poll());
					int[] r = parseInts(4, qs.poll());
					MainFrame.crect = new Rectangle(r[0], r[1], r[2], r[3]);
					MainBCU.preload = parseInt(qs.poll()) == 1;
					cfg.ints = parseInts(4, qs.poll());
					ViewBox.Conf.white = parseInt(qs.poll()) == 1;
					cfg.ref = parseInt(qs.poll()) == 1;
					MainBCU.USE_JOGL = parseInt(qs.poll()) == 1;
					cfg.deadOpa = parseInt(qs.poll());
					cfg.fullOpa = parseInt(qs.poll());
					MainBCU.FILTER_TYPE = parseInt(qs.poll());
					parseInt(qs.poll());
					qs.poll().trim(); // username
					Long.parseLong(qs.poll()); // TODO password
					qs.poll();// place holder
					BCJSON.cal_ver = parseInt(qs.poll());
					int[] ints = CommonStatic.parseIntsN(qs.poll());
					BCMusic.play = ints[0] == 1;
					if (ints.length == 3) {
						BCMusic.VOL_BG = ints[1];
						BCMusic.VOL_SE = ints[2];
					}
					MainLocale.exLang = parseInt(qs.poll()) == 1;
					MainLocale.exTTT = parseInt(qs.poll()) == 1;
					Exporter.read(qs);
					Importer.read(qs);
					qs.poll();// place holder
					BattleInfoPage.DEF_LARGE = parseInt(qs.poll()) == 1;
					MainBCU.light = parseInt(qs.poll()) == 1;
					MainBCU.nimbus = parseInt(qs.poll()) == 1;
				} catch (Exception e) {
				}
			} else {
				f.getParentFile().mkdirs();
				try {
					f.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
