package util.pack;

import java.util.ArrayList;
import java.util.List;

import util.system.FixIndexList;

public class SoulStore extends FixIndexList<Soul> {

	public static List<Soul> getAll(Pack p) {
		List<Soul> ans = new ArrayList<>();
		if (p != null) {
			ans.addAll(p.ss.getList());
			for (int rel : p.rely)
				ans.addAll(Pack.map.get(rel).ss.getList());
		} else
			for (Pack pac : Pack.map.values())
				ans.addAll(pac.ss.getList());
		return ans;
	}

	public static Soul getSoul(int ind) {
		int pid = ind / 1000;
		Pack pack = Pack.map.get(pid);
		if (pack == null)
			return null;
		return pack.ss.get(ind % 1000);
	}
	
	private final Pack pack;
	
	public SoulStore(Pack p) {
		super(new Soul[1000]);
		pack=p;
	}
	
	public int getID(Soul s) {
		int ind=indexOf(s);
		if(ind<0)
			return -1;
		return pack.id*1000+ind;
	}
	
	

}
