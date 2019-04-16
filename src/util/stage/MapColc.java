package util.stage;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import io.InStream;
import io.OutStream;
import io.Reader;
import util.Data;
import util.pack.Pack;
import util.system.FixIndexList;
import util.system.VFile;

public class MapColc extends Data {

	private static String[] strs = new String[] { "rc", "ec", "sc", "wc" };

	public static final Map<Integer, MapColc> MAPS = new TreeMap<>();

	public static Iterable<Stage> getAllStage() {
		return new StItr();
	}

	public static StageMap getMap(int id) {
		int ix = id / 1000;
		MapColc mc = MAPS.get(ix);
		if (mc == null)
			return null;
		if (id % 1000 < mc.maps.length)
			return mc.maps[id % 1000];
		return null;
	}

	public static MapColc getMap(String str) {
		for (MapColc mc : MAPS.values())
			if (mc.name.equals(str))
				return mc;
		return null;
	}

	public static void read() {
		Map<String, Integer> idmap = new TreeMap<>();
		idmap.put("E", 4);
		idmap.put("N", 0);
		idmap.put("S", 1);
		idmap.put("C", 2);
		idmap.put("CH", 3);
		idmap.put("T", 6);
		idmap.put("V", 7);
		idmap.put("R", 11);
		idmap.put("M", 12);
		idmap.put("A", 13);
		idmap.put("B", 14);
		for (int i = 0; i < strs.length; i++)
			new Castles(i, strs[i]);
		VFile f = VFile.getFile("./org/stage/");
		if (f == null)
			return;
		List<VFile> list = f.listFiles();
		for (VFile fi : list) {
			if (fi.getName().equals("CH"))
				continue;
			List<VFile> fs = fi.listFiles();
			VFile stage, map;
			if (fs.get(0).getName().startsWith("stage")) {
				stage = fs.get(0);
				map = fs.get(1);
			} else {
				stage = fs.get(1);
				map = fs.get(0);
			}
			new MapColc(fi.getName(), idmap.get(fi.getName()), stage, map);
		}
		new MapColc();
		Queue<String> qs = readLine(VFile.getFile("./org/data/Map_option.csv"));
		qs.poll();
		for (String str : qs) {
			String[] strs = str.trim().split(",");
			int id = Integer.parseInt(strs[0]);
			StageMap sm = getMap(id);
			int len = Integer.parseInt(strs[1]);
			sm.stars = new int[len];
			for (int i = 0; i < len; i++)
				sm.stars[i] = Integer.parseInt(strs[2 + i]);
			sm.set = Integer.parseInt(strs[6]);
			sm.retyp = Integer.parseInt(strs[7]);
			sm.pllim = Integer.parseInt(strs[8]);
			sm.name += strs[10];
		}
		qs = Reader.readLines(new File("./lib/calendar/stage name.txt"));
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
				MapColc mc = MAPS.get(id0);
				if (mc == null)
					continue;
				if (ids.length == 1) {
					mc.desp = name;
					continue;
				}
				int id1 = Reader.parseIntN(ids[1]);
				if (id1 >= mc.maps.length || id1 < 0)
					continue;
				StageMap sm = mc.maps[id1];
				if (sm == null)
					continue;
				if (ids.length == 2) {
					sm.desp = name;
					continue;
				}
				int id2 = Reader.parseIntN(ids[2]);
				if (id2 >= sm.list.size() || id2 < 0)
					continue;
				Stage st = sm.list.get(id2);
				st.desp = name;
			}
	}

	public final Pack pack;
	public final int id;
	public String name, desp = "";
	public FixIndexList<CharaGroup> groups = new FixIndexList<>(new CharaGroup[1000]);
	public FixIndexList<LvRestrict> lvrs = new FixIndexList<>(new LvRestrict[1000]);
	public StageMap[] maps;

	public MapColc(Pack pac) {
		pack = pac;
		id = pack.id;
		MAPS.put(id, this);
		name = pack.name;
		maps = new StageMap[0];
	}

	public MapColc(Pack pac, InStream is) {
		pack = pac;
		id = pack.id;
		MAPS.put(id, this);
		String ver = is.nextString();
		zread(ver, is);
	}

	protected MapColc(String str, int ID) {
		pack = null;
		id = ID;
		name = str;
		maps = new StageMap[1];
		maps[0] = new StageMap(this);
	}

	private MapColc() {
		pack = Pack.def;
		id = 3;
		MAPS.put(id, this);
		name = "CH";
		maps = new StageMap[12];
		String abbr = "./org/stage/CH/stageNormal/stageNormal";
		for (int i = 0; i < 3; i++) {
			VFile vf = VFile.getFile(abbr + "0_" + i + "_Z.csv");
			maps[i] = new StageMap(this, i, vf, 1);
			maps[i].name = "EoC " + (i + 1) + " Zombie";
			vf = VFile.getFile(abbr + "1_" + i + ".csv");
			maps[3 + i] = new StageMap(this, 3 + i, vf, 2);
			maps[i + 3].name = "ItF " + (i + 1);
			vf = VFile.getFile(abbr + "2_" + i + ".csv");
			maps[6 + i] = new StageMap(this, 6 + i, vf, 3);
			maps[i + 6].name = "CotC " + (i + 1);
		}
		VFile stn = VFile.getFile(abbr + "0.csv");
		maps[9] = new StageMap(this, 9, stn, 1);
		maps[9].name = "EoC 1-3";
		stn = VFile.getFile(abbr + "1_0_Z.csv");
		maps[10] = new StageMap(this, 10, stn, 2);
		maps[10].name = "ItF 1 Zombie";
		stn = VFile.getFile(abbr + "2_2_Invasion.csv");
		maps[11] = new StageMap(this, 11, stn, 2);
		maps[11].name = "CotC 3 Invasion";
		VFile stz = VFile.getFile("./org/stage/CH/stageZ/");
		List<VFile> list = stz.listFiles();
		for (VFile vf : list) {
			String str = vf.getName();
			int id0 = -1, id1 = -1;
			try {
				id0 = Integer.parseInt(str.substring(6, 8));
				id1 = Integer.parseInt(str.substring(9, 11));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if (id0 < 3)
				maps[id0].add(new Stage(maps[id0], id1, vf, 0));
			else
				maps[id0 + 6].add(new Stage(maps[id0 + 6], id1, vf, 0));

		}
		VFile stw = VFile.getFile("./org/stage/CH/stageW/");
		list = stw.listFiles();
		for (VFile vf : list) {
			String str = vf.getName();
			int id0 = -1, id1 = -1;
			try {
				id0 = Integer.parseInt(str.substring(6, 8));
				id1 = Integer.parseInt(str.substring(9, 11));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			maps[id0 - 1].add(new Stage(maps[id0 - 1], id1, vf, 1));
		}
		VFile sts = VFile.getFile("./org/stage/CH/stageSpace/");
		list = sts.listFiles();
		for (VFile vf : list) {
			String str = vf.getName();
			if (str.length() > 20) {
				maps[11].add(new Stage(maps[11], 0, vf, 0));
				continue;
			}
			int id0 = -1, id1 = -1;
			try {
				id0 = Integer.parseInt(str.substring(10, 12));
				id1 = Integer.parseInt(str.substring(13, 15));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			maps[id0 - 1].add(new Stage(maps[id0 - 1], id1, vf, 1));
		}

		VFile st = VFile.getFile("./org/stage/CH/stage/");
		list = st.listFiles();
		for (VFile vf : list) {
			String str = vf.getName();
			int id0 = -1;
			try {
				id0 = Integer.parseInt(str.substring(5, 7));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			maps[9].add(new Stage(maps[9], id0, vf, 2));
		}
		maps[9].stars = new int[] { 100, 200, 400 };
	}

	private MapColc(String st, int ID, VFile stage, VFile map) {
		pack = Pack.def;
		name = st;
		MAPS.put(id = ID, this);
		List<VFile> mps = map.listFiles();
		StageMap[] sms = new StageMap[mps.size()];
		for (VFile m : mps) {
			String str = m.getName();
			int len = str.length();
			int id = -1;
			try {
				id = Integer.parseInt(str.substring(len - 7, len - 4));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			sms[id] = new StageMap(this, id, m);
		}
		maps = sms;

		List<VFile> sts = stage.listFiles();
		for (VFile s : sts) {
			String str = s.getName();
			int len = str.length();
			int id0 = -1, id1 = -1;
			try {
				id0 = Integer.parseInt(str.substring(len - 10, len - 7));
				id1 = Integer.parseInt(str.substring(len - 6, len - 4));
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			sms[id0].add(new Stage(sms[id0], id1, s, 0));
		}
	}

	@Override
	public String toString() {
		if (desp.length() > 0)
			return desp + " (" + maps.length + ")";
		return name + " (" + maps.length + ")";
	}

	public void write(OutStream os) {
		os.writeString("0.3.8");
		os.writeString(name);

		List<CharaGroup> cglist = groups.getList();
		os.writeInt(cglist.size());
		for (CharaGroup cg : cglist)
			cg.write(os);

		List<LvRestrict> lvlist = lvrs.getList();
		os.writeInt(lvlist.size());
		for (LvRestrict lr : lvlist)
			lr.write(os);

		os.writeInt(maps.length);
		for (StageMap sm : maps) {
			os.writeString(sm.name);
			os.writeIntB(sm.stars);
			os.writeInt(sm.list.size());
			for (Stage st : sm.list)
				os.accept(st.write());
			os.writeInt(sm.lim.size());
			for (Limit l : sm.lim)
				l.write(os);
		}
	}

	private void zread(String ver, InStream is) {
		int val = getVer(ver);
		if (val >= 308)
			zread$000308(is);
		else if (val >= 307)
			zread$000307(is);
		else if (val >= 301)
			zread$000301(is);
	}

	private void zread$000301(InStream is) {
		name = is.nextString();
		int n = is.nextInt();
		maps = new StageMap[n];
		for (int i = 0; i < n; i++) {
			StageMap sm = new StageMap(this);
			maps[i] = sm;
			sm.name = is.nextString();
			sm.stars = is.nextIntsB();
			int m = is.nextInt();
			for (int j = 0; j < m; j++) {
				InStream sub = is.subStream();
				String ver = sub.nextString();
				sm.add(new Stage(sm, ver, sub));
			}
		}
	}

	private void zread$000307(InStream is) {
		name = is.nextString();

		int n = is.nextInt();
		for (int i = 0; i < n; i++) {
			int ind = is.nextInt();
			int m = is.nextInt();
			int[] ints = new int[m];
			for (int j = 0; j < m; j++)
				ints[j] = is.nextInt();
			groups.set(ind, new CharaGroup(pack, ind, 0, ints));
		}

		is.nextInt();

		n = is.nextInt();
		maps = new StageMap[n];
		for (int i = 0; i < n; i++) {
			StageMap sm = new StageMap(this);
			maps[i] = sm;
			sm.name = is.nextString();
			sm.stars = is.nextIntsB();
			int m = is.nextInt();
			for (int j = 0; j < m; j++) {
				InStream sub = is.subStream();
				String ver = sub.nextString();
				sm.add(new Stage(sm, ver, sub));
			}
			m = is.nextInt();
			for (int j = 0; j < m; i++)
				sm.lim.add(new Limit(this, 307, is));
		}

	}

	private void zread$000308(InStream is) {
		name = is.nextString();

		int n = is.nextInt();
		for (int i = 0; i < n; i++) {
			CharaGroup cg = new CharaGroup(pack, is);
			groups.set(cg.id, cg);
		}

		n = is.nextInt();
		for (int i = 0; i < n; i++) {
			LvRestrict lr = new LvRestrict(this, is);
			lvrs.set(lr.id, lr);
		}

		n = is.nextInt();
		maps = new StageMap[n];
		for (int i = 0; i < n; i++) {
			StageMap sm = new StageMap(this);
			maps[i] = sm;
			sm.name = is.nextString();
			sm.stars = is.nextIntsB();
			int m = is.nextInt();
			for (int j = 0; j < m; j++) {
				InStream sub = is.subStream();
				String ver = sub.nextString();
				sm.add(new Stage(sm, ver, sub));
			}
			m = is.nextInt();
			for (int j = 0; j < m; i++)
				sm.lim.add(new Limit(this, 307, is));
		}

	}

}

class StItr implements Iterator<Stage>, Iterable<Stage> {

	private Iterator<MapColc> imc;
	private MapColc mc;
	private int ism, is;

	protected StItr() {
		imc = MapColc.MAPS.values().iterator();
		mc = imc.next();
		ism = is = 0;
		validate();
	}

	@Override
	public boolean hasNext() {
		return imc != null;
	}

	@Override
	public Iterator<Stage> iterator() {
		return this;
	}

	@Override
	public Stage next() {
		Stage ans = mc.maps[ism].list.get(is);
		is++;
		validate();
		return ans;
	}

	private void validate() {
		while (is >= mc.maps[ism].list.size()) {
			is = 0;
			ism++;
			while (ism >= mc.maps.length) {
				ism = 0;

				if (!imc.hasNext()) {
					imc = null;
					return;
				}
				mc = imc.next();
			}
		}
	}

}