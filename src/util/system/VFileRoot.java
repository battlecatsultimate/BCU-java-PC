package util.system;

public class VFileRoot extends VFile {

	public final int type;

	public VFileRoot(int t) {
		type = t;
	}

	@Override
	public VFileRoot getRoot() {
		return this;
	}

	public VFile getVFile(String str) {
		String[] strs = str.split("/|\\\\");
		VFile par = this;
		for (int i = 1; i < strs.length; i++) {
			VFile next = null;
			for (VFile ch : par.child)
				if (ch.name.equals(strs[i]))
					next = ch;
			if (next == null)
				return null;
			if (i == strs.length - 1)
				return next;
			par = next;
		}
		return null;
	}

	public VFile newVFile(String str, byte[] bs) {
		String[] strs = str.split("/|\\\\");
		VFile par = this;
		for (int i = 1; i < strs.length; i++) {
			VFile next = null;
			for (VFile ch : par.child)
				if (ch.name.equals(strs[i]))
					next = ch;
			if (next == null)
				if (i == strs.length - 1)
					if (bs != null)
						return new VFile(par, strs[i], bs);
					else
						return new VFile(par, strs[i]);
				else
					next = new VFile(par, strs[i]);
			if (i == strs.length - 1) {
				next.data = bs;
				return next;
			}
			par = next;
		}
		return null;
	}

}
