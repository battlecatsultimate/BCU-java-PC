package util.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import main.MainBCU;
import util.Data;
import util.anim.AnimC;
import util.basis.Combo;
import util.entity.data.CustomUnit;
import util.entity.data.PCoin;
import util.pack.Pack;
import util.system.FixIndexList;
import util.system.MultiLangCont;
import util.system.VFile;

public class Unit extends Data implements Comparable<Unit> {

	public static void readLevel() {
		VFile vf = VFile.getFile("./org/data/unitlevel.csv");
		Queue<String> qs = readLine(vf);
		List<Unit> lu = Pack.def.us.ulist.getList();
		FixIndexList<UnitLevel> l = Pack.def.us.lvlist;
		for (Unit u : lu) {
			String[] strs = qs.poll().split(",");
			int[] lv = new int[20];
			for (int i = 0; i < 20; i++)
				lv[i] = Integer.parseInt(strs[i]);
			UnitLevel ul = new UnitLevel(lv);
			if (!l.contains(ul)) {
				ul.id = l.size();
				l.add(ul);
			}
			int ind = l.indexOf(ul);
			u.lv = l.get(ind);
			u.lv.units.add(u);
		}
		UnitLevel.def = l.get(2);
		vf = VFile.getFile("./org/data/unitbuy.csv");
		qs = readLine(vf);
		for (Unit u : lu) {
			String[] strs = qs.poll().split(",");
			u.rarity = Integer.parseInt(strs[13]);
			u.max = Integer.parseInt(strs[50]);
			u.maxp = Integer.parseInt(strs[51]);
		}

	}

	public final Pack pack;
	public final int id;
	public int rarity, max, maxp;
	public Form[] forms;
	public UnitLevel lv;

	public Unit(int ID) {
		pack = Pack.def;
		id = ID;
		Pack.def.us.ulist.add(this);
		String str = "./org/unit/" + trio(id) + "/";
		VFile fu = VFile.getFile(str + "unit" + trio(id) + ".csv");
		Queue<String> qs = readLine(fu);
		forms = new Form[exist(str, "s") ? 3 : exist(str, "c") ? 2 : exist(str, "f") ? 1 : 0];
		for (int i = 0; i < forms.length; i++)
			forms[i] = new Form(this, i, str + SUFX[i] + "/", qs.poll());
		if (MainBCU.preload)
			for (Form f : forms)
				f.anim.edi.check();
	}

	public Unit(int ID, Unit old, Pack p, UnitLevel ul) {
		pack = p;
		id = ID;
		rarity = old.rarity;
		max = old.max;
		maxp = old.maxp;
		lv = ul;
		forms = new Form[old.forms.length];
		for (int i = 0; i < forms.length; i++)
			forms[i] = old.forms[i].copy(this);
	}

	protected Unit(Pack p, DIYAnim da, CustomUnit cu) {
		pack = p;
		id = p.id * 1000 + p.us.ulist.nextInd();
		forms = new Form[] { new Form(this, 0, "new unit", da.getAnimC(), cu) };
		max = 50;
		maxp = 0;
		rarity = 4;
		lv = UnitLevel.def;
		lv.units.add(this);
	}

	protected Unit(Pack p, int ID) {
		pack = p;
		id = ID;
	}

	protected Unit(Pack p, Unit u) {
		pack = p;
		id = p.id * 1000 + p.us.ulist.nextInd();
		p.us.ulist.add(this);
		rarity = u.rarity;
		max = u.max;
		maxp = u.maxp;
		lv = u.lv;
		lv.units.add(u);
		forms = new Form[u.forms.length];
		for (int i = 0; i < forms.length; i++) {
			String str = AnimC.getAvailable(id + "-" + i);
			AnimC ac = new AnimC(str, u.forms[i].anim);
			DIYAnim da = new DIYAnim(str, ac);
			DIYAnim.map.put(str, da);
			CustomUnit cu = new CustomUnit();
			cu.importData(u.forms[i].du);
			forms[i] = new Form(this, i, str, ac, cu);
		}
	}

	public List<Combo> allCombo() {
		List<Combo> ans = new ArrayList<>();
		for (Combo[] cs : Combo.combos)
			for (Combo c : cs)
				for (int[] is : c.units)
					if (is[0] == id) {
						ans.add(c);
						break;
					}
		return ans;
	}

	@Override
	public int compareTo(Unit u) {
		return id > u.id ? 1 : id < u.id ? -1 : 0;
	}

	public int getPrefLv() {
		return max + (rarity < 2 ? maxp : 0);
	}

	public int[] getPrefLvs() {
		int[] ans = new int[6];
		if (forms.length >= 3) {
			PCoin pc = forms[2].getPCoin();
			if (pc != null)
				ans = pc.max.clone();
		}
		ans[0] = getPrefLv();
		return ans;
	}

	@Override
	public String toString() {
		String desp = MultiLangCont.get(forms[0]);
		if (desp != null && desp.length() > 0)
			return desp;
		if (forms[0].name.length() > 0)
			return forms[0].name;
		return id + "";
	}

	private boolean exist(String str, String suf) {
		VFile f = VFile.getFile(str + suf + "/");
		if (f == null)
			return false;
		List<String> l = f.listNames();
		String nam = trio(id) + "_" + suf;
		if (!l.contains(nam + ".png"))
			return false;
		if (!l.contains(nam + ".imgcut"))
			return false;
		if (!l.contains(nam + ".mamodel"))
			return false;
		for (int i = 0; i < 4; i++)
			if (!l.contains(nam + "0" + i + ".maanim"))
				return false;
		return true;
	}

}
