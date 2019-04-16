package decode.admin;

import java.io.File;

class OrgData {

	public static void main(String[] args) {
		System.out.println("start");
		move(new File("./out/DataLocal/Map_option.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/enemy_dictionary_list.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/NyancomboData.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/NyancomboFilter.tsv"), "./pre-org/data/");
		move(new File("./out/DataLocal/NyancomboParam.tsv"), "./pre-org/data/");
		move(new File("./out/DataLocal/Stage_option.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/t_unit.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/unitbuy.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/unitexp.csv"), "./pre-org/data/");
		move(new File("./out/DataLocal/unitlevel.csv"), "./pre-org/data/");

		System.out.println("done");
	}

	private static void move(File f, String s) {
		if (f == null)
			return;
		rename(f, s + f.getName());
	}

	private static void rename(File f, String s) {
		if (f == null)
			return;
		File p = new File(s);
		if (p.exists())
			p.delete();
		if (!p.getParentFile().exists())
			p.getParentFile().mkdirs();
		boolean b = f.renameTo(p);
		if (!b)
			System.out.println("failed to move: " + f.getName() + " to " + s);
	}

}
