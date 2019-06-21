package utilpc;

import common.util.system.files.VFileRoot;

public class Backup {

	public final String time;

	public String name;

	public VFileRoot<BackupData> files;

	public Backup(String str) {
		name = time = str;
	}

	@Override
	public String toString() {
		return name;
	}

}