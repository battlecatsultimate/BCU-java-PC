package decode.admin;

import java.io.File;

class OrgMap {

	// private static File il = new File("./out/ImageLocal/");
	private static File is = new File("./out/ImageServer/");
	private static String[] strs = new String[] { "bg", "ec", "rc", "sc", "wc" };

	public static void main(String[] args) {
		System.out.println("start");
		for (String path : strs) {
			String abbr = "./pre-org/img/" + path + "/";
			/*
			 * for(File f:il.listFiles()) { String str=f.getName();
			 * if(!str.startsWith(path)||str.length()!=9) continue; move(f,abbr); }
			 */
			for (File f : is.listFiles()) {
				String str = f.getName();
				if (!str.startsWith(path) || str.length() != 12)
					continue;
				move(f, abbr);
			}
		}
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
