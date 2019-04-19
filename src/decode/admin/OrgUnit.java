package decode.admin;

import java.io.File;

class OrgUnit {

	public static final String[] strs = new String[] { "f", "c", "s" };

	private static File unil = new File("./out/UnitLocal/");
	private static File unis = new File("./out/UnitServer/");
	private static File numl = new File("./out/NumberLocal/");
	private static File nums = new File("./out/NumberServer/");
	private static File idl = new File("./out/ImageDataLocal/");
	private static File dl = new File("./out/DataLocal/");
	private static File[][][] lfs = new File[500][4][];

	public static void main(String[] args) {
		System.out.println("start");
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 3; j++)
				lfs[i][j] = new File[9];
			lfs[i][3] = new File[3];
		}
		udi(unil);
		udi(unis);
		uni(unil);
		uni(unis);
		num(numl);
		num(nums);
		idl();
		dl();
		System.out.println("data found");
		write();
		System.out.println("done");
	}

	public static String trio(int i) {
		String str = "";
		if (i < 100)
			str += "0";
		if (i < 10)
			str += "0";
		return str + i;
	}

	private static void dl() {
		for (File f : dl.listFiles()) {
			String str = f.getName();
			if (str.length() != 11)
				continue;
			if (!str.startsWith("unit"))
				continue;
			int id = -1;
			try {
				id = Integer.parseInt(str.substring(4, 7));
			} catch (Exception e) {
				continue;
			}
			if (str.endsWith(".csv"))
				lfs[id - 1][3][2] = f;
		}
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
				if (str.endsWith("_f.imgcut"))
					lfs[id][0][2] = f;
				if (str.endsWith("_c.imgcut"))
					lfs[id][1][2] = f;
				if (str.endsWith("_s.imgcut"))
					lfs[id][2][2] = f;
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
				if (str.endsWith("_f.mamodel"))
					lfs[id][0][3] = f;
				if (str.endsWith("_c.mamodel"))
					lfs[id][1][3] = f;
				if (str.endsWith("_s.mamodel"))
					lfs[id][2][3] = f;
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
				for (int i = 0; i < 4; i++) {
					if (str.endsWith("_f0" + i + ".maanim"))
						lfs[id][0][4 + i] = f;
					if (str.endsWith("_c0" + i + ".maanim"))
						lfs[id][1][4 + i] = f;
					if (str.endsWith("_s0" + i + ".maanim"))
						lfs[id][2][4 + i] = f;
				}
			}
		}
	}

	private static void move(File f, String s) {
		if (f == null)
			return;
		rename(f, s + f.getName());
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
				if (str.endsWith("_f.png"))
					lfs[id][0][1] = f;
				if (str.endsWith("_c.png"))
					lfs[id][1][1] = f;
				if (str.endsWith("_s.png"))
					lfs[id][2][1] = f;
			} else
				f.deleteOnExit();
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

	private static void udi(File uni) {
		if (!uni.exists())
			return;
		for (File f : uni.listFiles())
			if (f.length() != 2789) {
				String str = f.getName();
				if (str.length() != 12)
					continue;
				if (!str.startsWith("udi"))
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(3, 6));
				} catch (Exception e) {
					continue;
				}
				if (str.endsWith("_f.png"))
					lfs[id][0][0] = f;
				if (str.endsWith("_c.png"))
					lfs[id][1][0] = f;
				if (str.endsWith("_s.png"))
					lfs[id][2][0] = f;
			} else
				f.deleteOnExit();
	}

	private static void uni(File uni) {
		if (!uni.exists())
			return;
		for (File f : uni.listFiles())
			if (f.length() != 2789) {
				String str = f.getName();
				if (str.length() != 14)
					continue;
				if (!str.startsWith("uni"))
					continue;
				int id = -1;
				try {
					id = Integer.parseInt(str.substring(3, 6));
				} catch (Exception e) {
					continue;
				}
				if (str.endsWith("_f00.png"))
					lfs[id][0][8] = f;
				if (str.endsWith("_c00.png"))
					lfs[id][1][8] = f;
				if (str.endsWith("_s00.png"))
					lfs[id][2][8] = f;
			} else
				f.deleteOnExit();
	}

	private static void write() {
		for (int i = 0; i < 500; i++) {
			String str = "./pre-org/unit/";
			String nam = trio(i);
			str += nam + "/";
			move(lfs[i][3][0], str);
			move(lfs[i][3][1], str);
			rename(lfs[i][3][2], str + "unit" + nam + ".csv");
			for (int j = 0; j < 3; j++) {
				String str1 = str + strs[j] + "/";
				for (int k = 0; k < 9; k++)
					move(lfs[i][j][k], str1);
			}
			System.out.println("move: " + (i + 1));
		}

	}

}
