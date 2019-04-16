package util.pack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.system.FixIndexList;

public class MusicStore extends FixIndexList<File> {

	public static List<File> getAll(Pack p) {
		List<File> ans = new ArrayList<>();
		if (p != null) {
			ans.addAll(p.ms.getList());
			for (int rel : p.rely)
				ans.addAll(Pack.map.get(rel).ms.getList());
		} else
			for (Pack pac : Pack.map.values())
				ans.addAll(pac.ms.getList());
		return ans;
	}

	public static File getMusic(int ind) {
		int pid = ind / 1000;
		Pack pack = Pack.map.get(pid);
		if (pack == null)
			return null;
		return pack.ms.get(ind % 1000);
	}

	private final Pack pack;

	public MusicStore(Pack p) {
		super(new File[1000]);
		pack = p;
	}

	public String nameOf(File f) {
		return pack.id + trio(indexOf(f));
	}

}
