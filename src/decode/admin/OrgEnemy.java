package decode.admin;

import java.io.File;

class OrgEnemy {

	private static File numl = new File("./out/NumberLocal/");
	private static File nums = new File("./out/NumberServer/");
	private static File idl = new File("./out/ImageDataLocal/");
	private static File[][] lfs = new File[500][7];

	public static void main(String[] args) {
		System.out.println("start");
		num(numl);
		num(nums);
		idl();
		System.out.println("data found");
		write();
		System.out.println("done");
	}

	private static void idl() {
		for (File f : idl.listFiles()) {
			String str = f.getName();
			if (str.endsWith(".imgcut")) {
				if (str.length() != 12)
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(0, 3));
				} catch (Exception e) {
					continue;
				}
				if (id > 500)
					continue;
				if (str.endsWith("_e.imgcut"))
					lfs[id][1] = f;
			}
			if (str.endsWith(".mamodel")) {
				if (str.length() != 13)
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(0, 3));
				} catch (Exception e) {
					continue;
				}
				if (str.endsWith("_e.mamodel"))
					lfs[id][2] = f;
			}
			if (str.endsWith(".maanim")) {
				if (str.length() != 14)
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(0, 3));
				} catch (Exception e) {
					continue;
				}
				for (int i = 0; i < 4; i++)
					if (str.endsWith("_e0" + i + ".maanim"))
						lfs[id][3 + i] = f;
			}
		}
	}

	private static void move(File f, String s) {
		if (f == null)
			return;
		File p = new File(s);
		if (!p.exists())
			p.mkdirs();
		p = new File(s + f.getName());
		if (p.exists())
			p.delete();
		boolean b = f.renameTo(p);
		if (!b)
			System.out.println("failed to move: " + f.getName() + " to " + s);
	}

	private static void num(File num) {
		if (!num.exists())
			return;
		for (File f : num.listFiles())
			if (f.length() != 2789) {
				String str = f.getName();
				if (str.length() != 9)
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(0, 3));
				} catch (Exception e) {
					continue;
				}
				if (str.endsWith("_e.png"))
					lfs[id][0] = f;
			} else
				f.deleteOnExit();
	}

	private static void write() {
		for (int i = 0; i < 500; i++) {
			String str = "./pre-org/enemy/";
			if (i < 100)
				str += 0;
			if (i < 10)
				str += 0;
			str += i + "/";
			for (int j = 0; j < 7; j++)
				move(lfs[i][j], str);
			System.out.println("move: " + (i + 1));
		}

	}

}
