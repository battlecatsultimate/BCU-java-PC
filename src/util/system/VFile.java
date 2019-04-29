package util.system;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class VFile implements Comparable<VFile>, Iterable<VFile> {

	public static VFileRoot root = new VFileRoot(0);

	public static VFile getFile(String str) {
		if (str.startsWith("./res/")) {
			File f = new File(str);
			if (!f.exists())
				return null;
			else
				return new VFile(f);
		}
		return root.getVFile(str);
	}

	public static VFile newFile(String str, byte[] bs) {
		return root.newVFile(str, bs);
	}

	public File f = null;
	public String name = "";
	public byte[] data = null;
	public List<VFile> child = null;
	public int mark = 0;
	public long size = -1;
	private boolean dirty = false;

	public final VFile parent;

	public VFile(File file) {
		parent = null;
		f = file;
		name = f.getName();
	}

	public VFile(VFile par, String n, byte[] bs) {
		parent = par;
		name = n;
		data = bs;
		if (par == null)
			return;
		par.child.add(this);
		par.dirty = true;
	}

	protected VFile() {
		parent = null;
		name = ".";
		child = new ArrayList<>();
	}

	protected VFile(VFile par, String n) {
		parent = par;
		name = n;
		child = new ArrayList<>();
		par.child.add(this);
		par.dirty = true;
	}

	@Override
	public int compareTo(VFile arg0) {
		return name.compareTo(arg0.name);
	}

	public void delete() {
		if (parent == null)
			return;
		parent.child.remove(this);
		parent.validate();
	}

	public InputStream getData() {
		if (f != null)
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		return new ByteArrayInputStream(data);

	}

	public List<VFile> getIf(Predicate<VFile> p) {
		List<VFile> prc = new ArrayList<>();
		for (VFile v : this)
			if (p.test(v))
				prc.add(v);
		return prc;
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		if (parent == null)
			return toString();
		return parent.getPath() + toString();
	}

	public VFileRoot getRoot() {
		if (parent == null)
			return null;
		return parent.getRoot();
	}

	@Override
	public Iterator<VFile> iterator() {
		return new VFItr(this);
	}

	public List<VFile> listFiles() {
		if (f != null) {
			File[] fs = f.listFiles();
			List<VFile> lf = new ArrayList<>();
			for (File f : fs)
				lf.add(new VFile(f));
			return lf;
		}
		if (dirty) {
			dirty = false;
			child.sort(null);
		}
		return child;
	}

	public List<String> listNames() {
		if (f != null)
			return Arrays.asList(f.list());
		if (dirty) {
			dirty = false;
			child.sort(null);
		}
		if (child == null)
			return null;
		List<String> ans = new ArrayList<>();
		for (VFile vf : child)
			ans.add(vf.name);
		return ans;
	}

	@Override
	public String toString() {
		if (child != null)
			return name + "\\";
		else
			return name;
	}

	public void validate() {
		if (child != null && child.isEmpty())
			delete();
	}

}

class VFItr implements Iterator<VFile> {

	private final VFile rt;
	private VFile on;

	protected VFItr(VFile root) {
		rt = root;
		if (rt.child == null || rt.child.size() == 0)
			on = null;
		else
			on = rt.child.get(0);
	}

	@Override
	public boolean hasNext() {
		return on != null;
	}

	@Override
	public VFile next() {
		VFile ans = on;
		if (on.child != null && on.child.size() > 0)
			on = on.child.get(0);
		else
			on = getRec(on);
		return ans;
	}

	private VFile getRec(VFile v) {
		if (v == rt)
			return null;
		int ind = v.parent.child.indexOf(v);
		if (ind < v.parent.child.size() - 1)
			return v.parent.child.get(ind + 1);
		return getRec(v.parent);
	}

}
