package io;

import static java.lang.Character.isDigit;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import event.EventReader;
import event.GroupPattern;
import event.HourGrouper;
import event.Namer;
import main.MainBCU;
import main.Opts;
import page.LoadPage;
import page.MainFrame;
import page.MainLocale;
import page.support.Exporter;
import page.support.Importer;
import page.view.ViewBox;
import util.ImgCore;
import util.Res;
import util.basis.BasisSet;
import util.basis.Combo;
import util.entity.data.PCoin;
import util.pack.Background;
import util.pack.EffAnim;
import util.pack.NyCastle;
import util.pack.Pack;
import util.pack.Soul;
import util.stage.CharaGroup;
import util.stage.Limit;
import util.stage.MapColc;
import util.stage.Recd;
import util.stage.Stage;
import util.stage.StageMap;
import util.system.MultiLangCont;
import util.system.VFile;
import util.system.VFileRoot;
import util.unit.DIYAnim;
import util.unit.Enemy;
import util.unit.Unit;

public class Reader extends DataIO {

	public static VFileRoot alt;

	public static void getData$0() {
		try {
			readInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void getData$1() {
		try {
			Res.readData();
			readUnit();
			LoadPage.prog(1, 1, 0);
			readEnemy();
			LoadPage.prog(1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error in reading: reading basic data at " + LoadPage.num);
			System.exit(0);
		}

		try {
			readOthers();
			LoadPage.prog(1, 1, 0);
			readID();
			readGroup();
			readLang();
			LoadPage.prog(1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error in reading: reading additional data at " + LoadPage.num);
			System.exit(0);
		}

		try {
			readCustom();
			LoadPage.prog(1, 1, 0);
			BCMusic.read();
			LoadPage.prog(1, 1, 0);
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error in reading: reading custom data at " + LoadPage.num);
			System.exit(0);
		}
	}

	public static int parseIntN(String str) {
		int ans;
		try {
			ans = parseIntsN(str)[0];
		} catch (Exception e) {
			ans = -1;
		}
		return ans;
	}

	public static int[] parseIntsN(String str) {
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
		int[] ans = new int[lstr.size()];
		for (int i = 0; i < lstr.size(); i++)
			ans[i] = Integer.parseInt(lstr.get(i));
		return ans;
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
		File f = new File("./lib/lang/");
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
								int id0 = Reader.parseIntN(ids[0]);
								MapColc mc = MapColc.MAPS.get(id0);
								if (mc == null)
									continue;
								if (ids.length == 1) {
									MultiLangCont.MCNAME.put(ni, mc, name);
									continue;
								}
								int id1 = Reader.parseIntN(ids[1]);
								if (id1 >= mc.maps.length || id1 < 0)
									continue;
								StageMap sm = mc.maps[id1];
								if (sm == null)
									continue;
								if (ids.length == 2) {
									MultiLangCont.SMNAME.put(ni, sm, name);
									continue;
								}
								int id2 = Reader.parseIntN(ids[2]);
								if (id2 >= sm.list.size() || id2 < 0)
									continue;
								Stage st = sm.list.get(id2);
								MultiLangCont.STNAME.put(ni, st, name);
							}
						continue;
					}
					if (nl.equals("UnitName.txt")) {
						Queue<String> qs = readLines(fl);
						for (String str : qs) {
							String[] strs = str.trim().split("\t");
							Unit u = Pack.def.us.ulist.get(Reader.parseIntN(strs[0]));
							if (u == null)
								continue;
							for (int i = 0; i < Math.min(u.forms.length, strs.length - 1); i++)
								MultiLangCont.FNAME.put(ni, u.forms[i], strs[i + 1].trim());
						}
						continue;
					}
					if (nl.equals("EnemyName.txt")) {
						Queue<String> qs = readLines(fl);
						for (String str : qs) {
							String[] strs = str.trim().split("\t");
							Enemy e = Pack.def.es.get(Reader.parseIntN(strs[0]));
							if (e == null || strs.length < 2)
								continue;
							MultiLangCont.ENAME.put(ni, e, strs[1].trim());
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
		Queue<String> ans = new ArrayDeque<>();
		BufferedReader reader = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			reader = new BufferedReader(isr);
			String temp = null;
			while ((temp = reader.readLine()) != null)
				ans.add(temp);
			reader.close();
		} catch (Exception e) {
			Opts.ioErr("failed to read file " + file);
			e.printStackTrace();
		}
		return ans;
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

	private static void readCustom() {
		alt = null;
		try {
			ZipAccess.getList();
			alt = ZipAccess.extractAllList();
		} catch (Exception e1) {
			e1.printStackTrace();
			Opts.loadErr("error in reading: reading backup");
		}
		try {
			DIYAnim.read();
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error in reading: reading custom animation");
			System.exit(0);
		}
		LoadPage.prog(1, 0, 0);
		try {
			Pack.read();
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error in reading: reading custom pack");
			System.exit(0);
		}
		Recd.read();
		File file = new File("./user/basis.v");
		if (file.exists())
			try {
				BasisSet.read(readBytes(file));
			} catch (Exception e) {
				e.printStackTrace();
				Opts.loadErr("error in reading: reading basis");
				System.exit(0);
			}
	}

	private static void readEnemy() {
		VFile f = VFile.getFile("./org/enemy/");
		List<VFile> list = f.listFiles();
		int i = 0;
		for (VFile fi : list) {
			LoadPage.prog(0, list.size(), i++);
			new Enemy(Integer.parseInt(fi.getName()));
		}
		LoadPage.prog(0, list.size(), i++);
		Enemy.readData();
	}

	private static void readGroup() {
		File f = new File("./lib/calendar/group event.txt");
		Queue<String> qs = readLines(f);
		try {
			while (qs.size() > 0) {
				String[] str = qs.poll().trim().split("\t");
				int n = Integer.parseInt(str[0].trim());
				String[] strs = new String[n];
				for (int i = 0; i < n; i++)
					strs[i] = qs.poll().trim();
				new GroupPattern(str[1].trim(), strs);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		f = new File("./lib/calendar/group hour.txt");
		qs = readLines(f);
		try {
			HourGrouper.process(qs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void readID() {
		File f = new File("./lib/calendar/event ID.txt");
		Queue<String> qs = readLines(f);
		for (String str : qs) {
			String[] strs = str.trim().split("\t");
			if (strs.length != 2)
				continue;
			int id = -2;
			try {
				id = Integer.parseInt(strs[0].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Namer.EMAP.put(id, strs[1]);
		}
		f = new File("./lib/calendar/gacha ID.txt");
		qs = readLines(f);
		for (String str : qs) {
			String[] strs = str.trim().split("\t");
			if (strs.length != 2)
				continue;
			int id = -2;
			try {
				id = Integer.parseInt(strs[0].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Namer.GMAP.put(id, strs[1]);
		}
		f = new File("./lib/calendar/item ID.txt");
		qs = readLines(f);
		for (String str : qs) {
			String[] strs = str.trim().split("\t");
			if (strs.length != 2)
				continue;
			int id = -2;
			try {
				id = Integer.parseInt(strs[0].trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			Namer.IMAP.put(id, strs[1]);
		}
	}

	private static void readInfo() {
		try {
			File f = new File("./user/data.ini");
			if (f.exists()) {
				try {
					Queue<String> qs = readLines(f);
					MainLocale.lang = parseInt(qs.poll());
					int[] r = parseInts(4, qs.poll());
					MainFrame.crect = new Rectangle(r[0], r[1], r[2], r[3]);
					MainBCU.preload = parseInt(qs.poll()) == 1;
					ImgCore.ints = parseInts(4, qs.poll());
					ViewBox.white = parseInt(qs.poll()) == 1;
					ImgCore.ref = parseInt(qs.poll()) == 1;
					qs.poll();
					MainBCU.write = !new File("./lib/httpcore-4.4.10.jar").exists();
					ImgCore.deadOpa = parseInt(qs.poll());
					ImgCore.fullOpa = parseInt(qs.poll());
					MainBCU.FILTER_TYPE = parseInt(qs.poll());
					EventReader.loc = parseInt(qs.poll());
					BCJSON.USERNAME = qs.poll().trim();
					BCJSON.PASSWORD = Long.parseLong(qs.poll());
					BCJSON.lib_ver = parseInt(qs.poll());
					BCJSON.cal_ver = parseInt(qs.poll());
					BCMusic.play = parseInt(qs.poll()) == 1;
					MainLocale.exLang = parseInt(qs.poll()) == 1;
					MainLocale.exTTT = parseInt(qs.poll()) == 1;
					Exporter.read(qs);
					Importer.read(qs);

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

	private static void readOthers() {
		Combo.readFile();
		Unit.readLevel();
		PCoin.read();
		EffAnim.read();
		Background.read();
		MapColc.read();
		CharaGroup.read();
		Limit.read();
		NyCastle.read();
		Soul.read();
	}

	private static void readUnit() {
		VFile f = VFile.getFile("./org/unit/");
		List<VFile> list = f.listFiles();
		int i = 0;
		for (VFile fi : list) {
			LoadPage.prog(0, list.size(), i++);
			new Unit(Integer.parseInt(fi.getName()));
		}
	}

}
