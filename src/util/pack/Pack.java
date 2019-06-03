package util.pack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import com.google.common.io.Files;

import io.BCJSON;
import io.InStream;
import io.OutStream;
import io.BCUException;
import io.Reader;
import io.Writer;
import main.MainBCU;
import main.Opts;
import page.LoadPage;
import util.Data;
import util.anim.AnimC;
import util.entity.data.CustomEnemy;
import util.entity.data.CustomEntity;
import util.entity.data.MaskAtk;
import util.stage.AbCastle;
import util.stage.Castles;
import util.stage.CharaGroup;
import util.stage.LvRestrict;
import util.stage.MapColc;
import util.stage.Stage;
import util.stage.StageMap;
import util.system.VImg;
import util.system.fake.FakeImage;
import util.unit.EneRand;
import util.unit.Enemy;
import util.unit.EnemyStore;
import util.unit.Form;
import util.unit.Unit;
import util.unit.UnitLevel;
import util.unit.UnitStore;

public class Pack extends Data {

	public static final Map<Integer, Pack> map = new TreeMap<>();

	public static final Pack def = new Pack();

	public static final int RELY_DEF = 0, RELY_CAS = 1, RELY_BG = 2, RELY_MUS = 3, RELY_ENE = 4, RELY_UNI = 5,
			RELY_CG = 6, RELY_LR = 7, RELY_ABI = 8;
	public static final int M_ES = 0, M_UL = 1, M_US = 2, M_CG = 3, M_LR = 4, M_BG = 5, M_CS = 6, M_MS = 7;

	public static String getAvailable(String str) {
		while (contains(str))
			str += "'";
		return str;
	}

	public static List<Pack> getEditable(int[] alr) {
		List<Pack> ans = new ArrayList<>();
		for (Pack p : map.values())
			if (p.editable) {
				boolean ava = true;
				for (int id : alr)
					ava &= p.id != id;
				if (ava)
					ans.add(p);
			}
		return ans;
	}

	public static Pack getNewPack() {
		return new Pack(new Random().nextInt(900000) + 100000);
	}

	public static void read() {
		File f = new File("./pack/");
		Map<Integer, File> fmap = new TreeMap<>();
		if (f.exists()) {
			for (File file : f.listFiles()) {
				String str = file.getName();
				if (!str.endsWith("bcupack"))
					continue;

				Pack pack;
				try {
					pack = new Pack(file);
				} catch (Exception e) {
					e.printStackTrace();
					Opts.loadErr("Error in reading pack " + str + " at initialization");
					continue;
				}

				if (fmap.containsKey(pack.id)) {
					String op = fmap.get(pack.id).getName();
					String np = file.getName();
					String msg = "pack " + op + " conflict with pack " + np + ". Do you want to use " + np
							+ " instead of " + op + "?";
					if (!Opts.packConf(msg)) {
						if (Opts.conf("Do you want to delete " + np + "?"))
							Writer.delete(file);
						continue;
					}
					if (Opts.conf("Do you want to delete " + op + "?"))
						Writer.delete(fmap.get(pack.id));
				}
				fmap.put(pack.id, file);
				map.put(pack.id, pack);

			}
		}
		List<Pack> list = new ArrayList<>();
		list.addAll(map.values());
		list.remove(def);
		f = new File("./res/enemy/");
		if (f.exists())
			for (File file : f.listFiles()) {
				String str = file.getName();
				if (!str.endsWith("bcuenemy"))
					continue;
				Pack pac;
				try {
					pac = new Pack(Reader.readBytes(file), true);
				} catch (Exception e) {
					e.printStackTrace();
					Opts.loadErr("Error in reading pack " + str + " at initialization");
					continue;
				}
				list.removeIf(p -> p.id == pac.id);
				list.add(pac);
			}
		int tot = list.size();
		while (list.size() > 0) {
			int rem = 0;
			for (Pack p : list) {
				boolean all = true;
				for (int pre : p.rely)
					if (!map.containsKey(pre) || map.get(pre).res != null)
						all = false;
				if (all) {
					LoadPage.prog("reading packs: " + (tot - list.size() + rem) + "/" + tot);
					if (p.bcuver > MainBCU.ver)
						Opts.verErr("BCU", revVer(p.bcuver));
					else
						try {
							if (p.editable)
								p.zreadt();
							else
								p.zreadp();
						} catch (Exception e) {
							Opts.loadErr("Error in loading custom pack: " + p.id + ", unknown cause");
							e.printStackTrace();
							System.exit(0);
						}
					rem++;

				}
			}
			list.removeIf(p -> p.res == null);
			if (rem == 0) {
				for (Pack p : list) {
					map.remove(p.id);
					String err = "pack " + p.id + " require parent packs: ";
					boolean b = false;
					for (int val : p.rely)
						if (!map.keySet().contains(val)) {
							if (b)
								err += ", ";
							err += val;
							b = true;
						}
					Opts.loadErr(err);
				}
				break;
			}
		}
	}

	public static Pack read(File f) {
		Pack p = new Pack(f);
		map.put(p.id, p);
		p.zreadp();
		return p;
	}

	public static void writeAll() {
		File f = new File("./res/enemy/");
		if (!f.exists())
			f.mkdirs();
		for (Pack p : map.values())
			if (p.editable)
				Writer.writeBytes(p.write(), "./res/enemy/" + hex(p.id) + ".bcuenemy");
	}

	private static boolean contains(String str) {
		for (Pack p : map.values())
			if (p.name.equals(str))
				return true;
		return false;
	}

	public final int id;

	public final CasStore cs;
	public final EnemyStore es = new EnemyStore(this);
	public final BGStore bg = new BGStore(this);
	public final UnitStore us = new UnitStore(this);
	public final SoulStore ss = new SoulStore(this);

	public final MusicStore ms = new MusicStore(this);
	public MapColc mc;
	public final List<Integer> rely = new ArrayList<>();
	public boolean editable = true;
	public String name = "custom pack", time = "", author = "";
	public File file;
	public int version;
	private InStream res;

	private int ver, bcuver;

	public Pack(InStream is, boolean reg) {
		ver = getVer(is.nextString());
		id = is.nextInt();
		res = is;
		int n = is.nextByte();
		for (int i = 0; i < n; i++)
			rely.add(is.nextInt());
		if (reg)
			map.put(id, this);
		cs = new CasStore(this, reg);
	}

	public Pack(int hash) {
		map.put(id = hash, this);
		name = getAvailable(name);
		mc = new MapColc(this);
		rely.add(0);
		cs = new CasStore(this, true);
	}

	private Pack() {
		map.put(id = 0, this);
		name = "default";
		editable = false;
		mc = null;
		cs = new CasStore(this, false);

	}

	private Pack(File f) {
		file = f;
		InStream is = Reader.readBytes(f);
		editable = false;
		ver = getVer(is.nextString());
		res = is;
		if (ver >= 400) {
			InStream head = is.subStream();
			id = head.nextInt();
			int n = head.nextByte();
			for (int i = 0; i < n; i++)
				rely.add(head.nextInt());
			try {
				bcuver = head.nextInt();
				// mistake handling
				if (bcuver == 406010)
					bcuver = 40610;
				time = head.nextString();
				version = head.nextInt();
				author = head.nextString();
			} catch (BCUException e) {
			}
		} else {
			id = is.nextInt();
			int n = is.nextByte();
			for (int i = 0; i < n; i++)
				rely.add(is.nextInt());
		}
		cs = new CasStore(this, true);
	}

	public Collection<AbCastle> casList() {
		List<AbCastle> ans = new ArrayList<>();
		ans.addAll(Castles.defcas);
		for (int i : rely)
			ans.add(map.get(i).cs);
		ans.add(cs);
		return ans;
	}

	public void delete() {
		Writer.delete(new File("./res/enemy/" + id + ".bcuenemy"));
		Writer.delete(new File("./res/img/" + id + "/"));
		map.remove(id);
		Castles.map.remove(id);
	}

	public void forceRemoveParent(int p) {
		if (!rely.contains(p))
			return;
		if (p < 1000)
			return;
		if (p == id)
			return;
		for (StageMap sm : mc.maps)
			for (Stage st : sm.list)
				st.removePack(p);
		for (CharaGroup cg : mc.groups.getList())
			cg.set.removeIf(u -> u.pack.id == p);
		for (LvRestrict lr : mc.lvrs.getList())
			lr.res.entrySet().removeIf(ent -> ent.getKey().pack.id == p);

		for (Enemy e : es.getList())
			for (int i = 0; i < e.de.getAtkCount(); i++) {
				MaskAtk am = e.de.getAtkModel(i);
				if (am.getProc(Data.P_SUMMON)[1] / 1000 == p)
					am.getProc(Data.P_SUMMON)[1] = 0;
				if (am.getProc(Data.P_THEME)[2] / 1000 == p)
					am.getProc(Data.P_THEME)[2] = 0;
			}
		for (Unit u : us.ulist.getList())
			for (Form e : u.forms)
				for (int i = 0; i < e.du.getAtkCount(); i++) {
					MaskAtk am = e.du.getAtkModel(i);
					if (am.getProc(Data.P_SUMMON)[1] / 1000 == p)
						am.getProc(Data.P_SUMMON)[1] = 0;
					if (am.getProc(Data.P_THEME)[2] / 1000 == p)
						am.getProc(Data.P_THEME)[2] = 0;
				}
	}

	public void merge(Pack p) {
		int[][] inds = new int[8][1000];

		Map<Integer, Enemy> esmap = p.es.getMap();
		for (Entry<Integer, Enemy> ent : esmap.entrySet()) {
			int eid = es.nextInd();
			es.add(new Enemy(eid, ent.getValue(), this));
			inds[M_ES][ent.getKey()] = eid;
		}

		Map<Integer, EneRand> ermap = p.es.ers.getMap();
		for (Entry<Integer, EneRand> ent : ermap.entrySet()) {
			int eid = es.ers.nextInd() + 500;
			es.ers.add(new EneRand(eid, inds, ent.getValue(), this, p));
			inds[M_ES][ent.getKey() + 500] = eid;
		}

		Map<Integer, UnitLevel> ulmap = p.us.lvlist.getMap();
		for (Entry<Integer, UnitLevel> ent : ulmap.entrySet()) {
			int ulid = us.lvlist.nextInd();
			us.lvlist.add(new UnitLevel(this, ulid, ent.getValue()));
			inds[M_UL][ent.getKey()] = ulid;
		}

		Map<Integer, Unit> usmap = p.us.ulist.getMap();
		for (Entry<Integer, Unit> ent : usmap.entrySet()) {
			int uid = us.ulist.nextInd();
			Unit u = ent.getValue();
			UnitLevel ul = u.lv;
			if (ul.id / 1000 == p.id)
				ul = us.lvlist.get(inds[M_UL][ul.id % 1000]);
			us.ulist.add(u = new Unit(uid, ent.getValue(), this, ul));
			inds[M_US][ent.getKey()] = uid;
		}

		Map<Integer, CharaGroup> cgmap = p.mc.groups.getMap();
		for (Entry<Integer, CharaGroup> ent : cgmap.entrySet()) {
			int cgid = mc.groups.nextInd();
			CharaGroup cg = ent.getValue();
			int[] units = new int[cg.set.size()];
			int i = 0;
			for (Unit u : cg.set) {
				int uid = u.id;
				if (uid / 1000 == p.id)
					uid = inds[M_US][uid % 1000] + id * 1000;
				units[i++] = uid;
			}
			mc.groups.add(new CharaGroup(this, cgid, cg.type, units));
			inds[M_CG][ent.getKey()] = cgid;
		}

		Map<Integer, LvRestrict> lrmap = p.mc.lvrs.getMap();
		for (Entry<Integer, LvRestrict> ent : lrmap.entrySet()) {
			int lrid = mc.lvrs.nextInd();
			LvRestrict lr = new LvRestrict(this, lrid, ent.getValue());
			lr.res.clear();
			for (Entry<CharaGroup, int[]> ets : lr.res.entrySet()) {
				CharaGroup cg = ets.getKey();
				if (cg.pack == p)
					lr.res.put(mc.groups.get(inds[M_CG][cg.id]), ets.getValue());
				else
					lr.res.put(cg, ets.getValue());
			}
			mc.lvrs.add(lr);
			inds[M_LR][ent.getKey()] = lrid;
		}

		Map<Integer, Background> bgmap = p.bg.getMap();
		for (Entry<Integer, Background> ent : bgmap.entrySet()) {
			int bgid = bg.nextInd();
			bg.add(ent.getValue().copy(this, bgid));
			inds[M_BG][ent.getKey()] = bgid;
			File f = new File("./res/img/" + hex(id) + "/bg/" + trio(bgid) + ".png");
			Writer.check(f);
			try {
				FakeImage.write(ent.getValue().img.getImg(), "PNG", f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		Map<Integer, VImg> csmap = p.cs.getMap();
		for (Entry<Integer, VImg> ent : csmap.entrySet()) {
			int csid = cs.nextInd();
			cs.add(new VImg(ent.getValue().getImg()));
			inds[M_CS][ent.getKey()] = csid;
			File f = new File("./res/img/" + hex(id) + "/cas/" + trio(csid) + ".png");
			Writer.check(f);
			try {
				FakeImage.write(ent.getValue().getImg(), "PNG", f);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		int n0 = mc.maps.length;
		int pid = p.id;
		mc.maps = Arrays.copyOf(mc.maps, n0 + p.mc.maps.length);
		for (int i = 0; i < p.mc.maps.length; i++) {
			mc.maps[n0 + i] = p.mc.maps[i].copy(mc);
			for (Stage st : mc.maps[n0 + i].list)
				st.merge(id, pid, p, inds);
		}

		for (Enemy e : es.getList())
			for (int[][] proc : ((CustomEntity) e.de).getAllProc()) {
				int eid = proc[Data.P_SUMMON][1];
				if (eid / 1000 == pid)
					proc[Data.P_SUMMON][1] = inds[M_ES][eid % 1000] + id * 1000;
				int bgid = proc[Data.P_THEME][2];
				if (bgid / 1000 == pid)
					proc[Data.P_THEME][2] = inds[M_BG][bgid % 1000] + id * 1000;
			}

		for (Unit u : us.ulist.getList())
			for (Form f : u.forms)
				for (int[][] proc : ((CustomEntity) f.du).getAllProc()) {
					int uid = proc[Data.P_SUMMON][1];
					if (uid / 1000 == pid)
						proc[Data.P_SUMMON][1] = inds[M_US][uid] + id * 1000;
					int bgid = proc[Data.P_THEME][2];
					if (bgid / 1000 == pid)
						proc[Data.P_THEME][2] = inds[M_BG][bgid] + id * 1000;
				}

		// TODO music
	}

	public void packUp() {
		OutStream os = OutStream.getIns();
		os.writeString("0.4.2");
		OutStream head = OutStream.getIns();
		head.writeInt(id);
		head.writeByte((byte) rely.size());
		for (int val : rely)
			head.writeInt(val);
		head.writeInt(MainBCU.ver);
		head.writeString(editable ? time = MainBCU.getTime() : time);
		head.writeInt(version);
		head.writeString(BCJSON.USERNAME);
		head.terminate();
		os.accept(head);
		os.writeString(name);
		os.accept(es.packup());
		os.accept(us.packup());
		os.accept(cs.packup());
		os.accept(bg.packup());
		os.accept(ms.packup());
		mc.write(os);
		os.terminate();
		Writer.writeBytes(os, "./pack/" + hex(id) + ".bcupack");
	}

	public int relyOn(int p) {
		if (!rely.contains(p))
			return -1;
		if (p < 1000)
			return 0;
		if (p == id)
			return -1;
		for (StageMap sm : mc.maps)
			for (Stage st : sm.list) {
				int rel = st.relyOn(p);
				if (rel >= 0)
					return rel;
			}
		for (CharaGroup cg : mc.groups.getList())
			for (Unit u : cg.set)
				if (u.pack.id == p)
					return RELY_UNI;
		for (LvRestrict lr : mc.lvrs.getList())
			for (CharaGroup cg : lr.res.keySet())
				if (cg.pack.id == p)
					return RELY_CG;
		for (Enemy e : es.getList())
			for (int i = 0; i < e.de.getAtkCount(); i++) {
				MaskAtk am = e.de.getAtkModel(i);
				if (am.getProc(Data.P_SUMMON)[1] / 1000 == p)
					return RELY_ABI;
				if (am.getProc(Data.P_THEME)[2] / 1000 == p)
					return RELY_ABI;
			}
		for (Unit u : us.ulist.getList())
			for (Form e : u.forms)
				for (int i = 0; i < e.du.getAtkCount(); i++) {
					MaskAtk am = e.du.getAtkModel(i);
					if (am.getProc(Data.P_SUMMON)[1] / 1000 == p)
						return RELY_ABI;
					if (am.getProc(Data.P_THEME)[2] / 1000 == p)
						return RELY_ABI;
				}

		return -1;
	}

	@Override
	public String toString() {
		return hex(id) + " - " + name;
	}

	public void unpack() {
		editable = true;
		cs.forEach((i, c) -> {
			String path = "./res/img/" + hex(id) + "/cas/" + trio(i) + ".png";
			try {
				FakeImage.write(c.getImg(), "PNG", new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		bg.forEach((i, c) -> {
			String path = "./res/img/" + hex(id) + "/bg/" + trio(i) + ".png";
			try {
				FakeImage.write(c.img.getImg(), "PNG", new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		File mf = new File("./res/img/" + hex(id) + "/music/");
		if (!mf.exists())
			mf.mkdirs();
		ms.forEach((i, c) -> {
			String src = "./pack/music/" + hex(id) + "/" + trio(i) + ".ogg";
			String dst = "./res/img/" + hex(id) + "/music/" + trio(i) + ".ogg";

			try {
				Files.move(new File(src), new File(dst));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	public boolean usable(int p) {
		if (p < 1000)
			return true;
		if (p == id)
			return true;
		for (int rel : rely)
			if (Pack.map.get(rel).id == p)
				return true;
		return false;
	}

	public OutStream write() {
		mc.name = name;
		OutStream os = OutStream.getIns();
		os.writeString("0.4.1");
		os.writeInt(id);
		os.writeByte((byte) rely.size());
		for (int val : rely)
			os.writeInt(val);
		os.writeString(name);
		os.accept(es.write());
		os.accept(cs.write());
		os.accept(bg.write());
		os.accept(us.write());
		mc.write(os);
		return os;
	}

	public void zreadt() {
		if (ver >= 400)
			zreadt$000400(res);
		else if (ver >= 308)
			zreadt$000308(res);
		else if (ver >= 306)
			zreadt$000306(res);
		else
			Opts.verErr("custom pack", "0-4-1-3");
		err("stages", () -> mc = new MapColc(this, res));
		err("music", () -> ms.load());
		res = null;
	}

	private void err(String str, Runnable c) {
		try {
			c.run();
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("error at reading " + str + " in pack " + id);
		}
	}

	private void zreadp() {
		if (ver == 402)
			zreadp$000402(res);
		else if (ver == 401)
			zreadp$000401(res);
		else if (ver >= 306)
			zreadp$000306(res);
		else if (ver >= 303)
			zreadp$000303(res);
		mc = new MapColc(this, res);
		res = null;
	}

	private void zreadp$000303(InStream is) {
		name = is.nextString();
		int n = is.nextInt();
		for (int i = 0; i < n; i++) {
			int hash = is.nextInt();
			String str = is.nextString();
			CustomEnemy ce = new CustomEnemy();
			ce.fillData(ver, is);
			AnimC ac = new AnimC(is.subStream());
			Enemy e = new Enemy(hash, ac, ce);
			e.name = str;
			es.set(hash % 1000, e);
		}
	}

	private void zreadp$000306(InStream is) {
		zreadp$000303(res);
		cs.zreadp(ver, is.subStream());
		bg.zreadp(ver, is.subStream());
	}

	private void zreadp$000401(InStream is) {
		name = is.nextString();
		err("enemies", () -> es.zreadp(is.subStream()));
		err("units", () -> us.zreadp(is.subStream()));
		err("castles", () -> cs.zreadp(ver, is.subStream()));
		err("backgrounds", () -> bg.zreadp(ver, is.subStream()));
	}

	private void zreadp$000402(InStream is) {
		name = is.nextString();
		err("enemies", () -> es.zreadp(is.subStream()));
		err("units", () -> us.zreadp(is.subStream()));
		err("castles", () -> cs.zreadp(ver, is.subStream()));
		err("backgrounds", () -> bg.zreadp(ver, is.subStream()));
		err("music", () -> ms.zreadp(is.subStream()));
	}

	private void zreadt$000306(InStream is) {
		name = is.nextString();
		es.zreadt(ver, is);
		cs.zreadt(ver, is);
		is.nextInt();
	}

	private void zreadt$000308(InStream is) {
		name = is.nextString();
		es.zreadt(ver, is);
		cs.zreadt(ver, is);
		bg.zreadt(ver, is);
		us.zreadt(is.subStream());
	}

	private void zreadt$000400(InStream is) {
		name = is.nextString();
		err("enemies", () -> es.zreadt(ver, is.subStream()));
		err("castles", () -> cs.zreadt(ver, is.subStream()));
		err("backgrounds", () -> bg.zreadt(ver, is.subStream()));
		err("units", () -> us.zreadt(is.subStream()));

	}

}