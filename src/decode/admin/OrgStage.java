package decode.admin;

import java.io.File;

class OrgStage {

	private static File dl = new File("./out/DataLocal/");

	public static void main(String[] args) {
		System.out.println("start");
		String abbr = "./pre-org/stage/";

		for (File f : dl.listFiles()) {
			String str = f.getName();
			if (str.startsWith("stageW"))
				move(f, abbr + "CH/stageW/");
			else if (str.startsWith("stageZ"))
				move(f, abbr + "CH/stageZ/");
			else if (str.startsWith("stageSpace"))
				move(f, abbr + "CH/stageSpace/");
			else if (str.startsWith("stageNormal"))
				move(f, abbr + "CH/stageNormal/");
			else if (str.startsWith("stageEX"))
				move(f, abbr + "E/stageEX/");
			else if (str.startsWith("stageRNA"))
				move(f, abbr + "A/stageRNA/");
			else if (str.startsWith("stageRN"))
				move(f, abbr + "N/stageRN/");
			else if (str.startsWith("stageRB"))
				move(f, abbr + "B/stageRB/");
			else if (str.startsWith("stageRC"))
				move(f, abbr + "C/stageRC/");
			else if (str.startsWith("stageRM"))
				move(f, abbr + "M/stageRM/");
			else if (str.startsWith("stageRR"))
				move(f, abbr + "R/stageRR/");
			else if (str.startsWith("stageRS"))
				move(f, abbr + "S/stageRS/");
			else if (str.startsWith("stageRT"))
				move(f, abbr + "T/stageRT/");
			else if (str.startsWith("stageRV"))
				move(f, abbr + "V/stageRV/");
			else if (str.startsWith("stage") && str.length() == 11)
				move(f, abbr + "CH/stage/");
			else if (str.startsWith("MapStageDataRE"))
				move(f, abbr + "E/MSDRE/");
			else if (str.startsWith("MapStageDataNA"))
				move(f, abbr + "A/MSDNA/");
			else if (str.startsWith("MapStageDataN"))
				move(f, abbr + "N/MSDN/");
			else if (str.startsWith("MapStageDataB"))
				move(f, abbr + "B/MSDB/");
			else if (str.startsWith("MapStageDataC"))
				move(f, abbr + "C/MSDC/");
			else if (str.startsWith("MapStageDataM"))
				move(f, abbr + "M/MSDM/");
			else if (str.startsWith("MapStageDataR"))
				move(f, abbr + "R/MSDR/");
			else if (str.startsWith("MapStageDataS"))
				move(f, abbr + "S/MSDS/");
			else if (str.startsWith("MapStageDataT"))
				move(f, abbr + "T/MSDT/");
			else if (str.startsWith("MapStageDataV"))
				move(f, abbr + "V/MSDV/");
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
