package util.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.system.VFile;
import util.system.VImg;

public class Castles implements AbCastle {

	public static Map<Integer, AbCastle> map = new TreeMap<>();

	public static List<AbCastle> defcas = new ArrayList<>();

	public static VImg getCastle(int cind) {
		int set = cind / 1000;
		int id = cind % 1000;
		AbCastle c = map.get(set);
		int n = c.size();
		VImg ans = null;
		if (n == 0)
			ans = getCastle(0);
		else if (id < n)
			ans = c.get(id);
		else
			ans = c.get(0);
		if (ans == null)
			ans = getCastle(0);
		return ans;
	}

	public final List<VImg> list = new ArrayList<>();
	public final String str;

	public final int id;

	public Castles(int hash, String name) {
		id = hash;
		str = name;
		String path = "./org/img/" + name + "/";
		List<VFile> lf = VFile.getFile(path).listFiles();
		for (VFile vf : lf)
			list.add(new VImg(vf));
		map.put(id, this);
		defcas.add(this);
	}

	@Override
	public VImg get(int ind) {
		return list.get(ind);
	}

	@Override
	public int getCasID(VImg img) {
		return id * 1000 + list.indexOf(img);
	}

	@Override
	public List<VImg> getList() {
		return list;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public String toString() {
		return str + " (" + list.size() + ")";
	}

}
