package util.system;

public class Backup {

	public final String time;

	public String name;

	public VFileRoot files;

	public Backup(String str) {
		name = time = str;
	}

	@Override
	public String toString() {
		return name;
	}

}