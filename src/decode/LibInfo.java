package decode;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import io.Reader;
import io.Writer;

public class LibInfo {

	protected static final CopyOption RE = StandardCopyOption.REPLACE_EXISTING;

	protected final int ver;

	protected final FileSystem fs;

	protected final Map<String, VerInfo> libver = new TreeMap<>();

	protected final MergedLib merge = new MergedLib();

	protected LibInfo(FileSystem sys) throws IOException {
		fs = sys;
		List<String> ls = Files.readAllLines(fs.getPath("/info/info.ini"));
		Queue<String> qs = new ArrayDeque<>(ls);
		ver = Reader.parseIntN(qs.poll());
		int n = Reader.parseIntN(qs.poll());
		for (int i = 0; i < n; i++) {
			String v = qs.poll().trim();
			VerInfo vi = new VerInfo(fs, v);
			libver.put(v, vi);
			merge.add(vi);
		}
	}

	public void merge(LibInfo li) throws IOException {
		List<PathInfo> ls = merge.merge(li.merge);
		for (PathInfo p : ls)
			if (p.type == 0) {
				Path target = fs.getPath(p.path);
				Files.createDirectories(target.getParent());
				Files.copy(li.fs.getPath(p.path), target, RE);
			} else if (p.type == 1)
				Files.deleteIfExists(fs.getPath(p.path));
		for (String v : li.libver.keySet())
			if (!libver.containsKey(v)) {
				Files.copy(li.fs.getPath("/info/" + v + ".verinfo"), fs.getPath("/info/" + v + ".verinfo"));
				libver.put(v, li.libver.get(v));
			}
		Path pini = fs.getPath("/info/info.ini");
		Files.deleteIfExists(pini);
		BufferedWriter bw = Files.newBufferedWriter(pini);
		bw.write("file_version = 00040510\r\n");
		bw.write("number_of_libs = " + libver.size() + "\r\n");
		for (VerInfo vi : libver.values())
			bw.write(vi.ver + "\r\n");
		bw.close();
	}

	public List<String> update(Collection<String> list) {
		List<String> ans = new ArrayList<>();
		for (String s : list)
			if (!merge.set.contains(s))
				ans.add(s);
		return ans;
	}

	protected void clean() throws IOException {
		List<Path> list = new ArrayList<>();
		Files.walk(fs.getPath("/org")).forEach(p -> {
			if (p.endsWith(".DS_Store") || p.endsWith("desktop.ini") || p.startsWith("/org/unprocessed"))
				list.add(0, p);
		});
		for (Path p : list) {
			Files.delete(p);
			System.out.println("Delete: " + p);
		}
	}

}

class MergedLib {

	protected final Set<String> set = new TreeSet<>();

	protected final Map<String, PathInfo> paths = new TreeMap<>();

	protected void add(VerInfo vi) {
		set.add(vi.ver);
		for (PathInfo p : vi.paths)
			if (isNew(p))
				paths.put(p.path, p);

	}

	protected List<PathInfo> merge(MergedLib ml) {
		List<PathInfo> ans = new ArrayList<>();
		ml.paths.forEach((p, i) -> {
			if (isNew(i)) {
				ans.add(i);
				paths.put(p, i);
			}
		});
		return ans;
	}

	private boolean isNew(PathInfo p) {
		return !paths.containsKey(p.path) || paths.get(p.path).update(p);
	}

}

final class PathInfo implements Comparable<PathInfo> {

	private static final String[] types = { "add:", "delete:" };

	private static int getType(String str) {
		for (int i = 0; i < types.length; i++)
			if (str.equals(types[i]))
				return i;
		return -1;
	}

	protected final String path;
	protected final String ver;
	protected final int type;

	protected PathInfo(String input, String v) {
		ver = v;
		String[] strs = input.split("\t");
		path = strs[1].trim();
		type = getType(strs[0].trim());
	}

	protected PathInfo(String p, String v, int t) {
		path = p;
		ver = v;
		type = t;
	}

	@Override
	public int compareTo(PathInfo o) {
		return path.compareTo(o.path);
	}

	@Override
	public String toString() {
		return types[type] + "\t" + path;
	}

	protected boolean update(PathInfo p) {
		assert path.equals(p.path);
		return Reader.parseIntN(ver) < Reader.parseIntN(p.ver);
	}

}

class VerInfo implements Comparable<VerInfo> {

	protected final String ver;

	protected final Set<PathInfo> paths = new TreeSet<>();

	protected VerInfo(FileSystem fs, String v) throws IOException {
		ver = v;
		List<String> ls = Files.readAllLines(fs.getPath("/info/" + v + ".verinfo"));
		Queue<String> qs = new ArrayDeque<>(ls);
		qs.poll();// file ver
		qs.poll();// lib ver
		int n = Reader.parseIntN(qs.poll());
		for (int i = 0; i < n; i++)
			paths.add(new PathInfo(qs.poll(), v));
	}

	protected VerInfo(String v) throws IOException {
		ver = v;
		Path org = ZipLib.lib.getPath("/org");
		Files.walk(org).forEach(p -> {
			if (!Files.isDirectory(p) && !p.endsWith(".DS_Store") & !p.endsWith("desktop.ini"))
				paths.add(new PathInfo(p.toString(), v, 0));
		});
	}

	@Override
	public int compareTo(VerInfo o) {
		return ver.compareTo(o.ver);
	}

	protected void write(String str) {
		PrintStream ps = Writer.newFile(str);
		ps.println("file_version = 00040510");
		ps.println("lib_version = " + ver);
		ps.println("number_of_paths = " + paths.size());
		for (PathInfo p : paths)
			ps.println(p.toString());
		ps.close();
	}

}