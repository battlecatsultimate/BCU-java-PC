package decode;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import io.Writer;

public class CMD {

	private static Path ext = new File("./assets/libworkspace/").toPath();
	private static FileSystem fs = null;
	private static Path cur = ext;
	private static String[] strs;

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print(cur + " $");
			strs = sc.nextLine().split(" ");
			if (strs[0].equals("quit"))
				break;
			else if (strs[0].equals("pwd"))
				System.out.println(cur);
			else if (strs[0].equals("exit"))
				exit();
			else if (strs[0].equals("enter"))
				enter();
			else if (strs[0].equals("ls"))
				ls();
			else if (strs[0].equals("cat"))
				cat();
			else if (strs[0].equals("cd"))
				cd();
			else if (strs[0].equals("cp"))
				cp();
			else if (strs[0].equals("rm"))
				rm();
			else if (strs[0].equals("mv"))
				mv();
			else if (strs[0].equals("mkdir"))
				mkdir();
			else if (strs[0].equals("touch"))
				touch();
			else if (strs[0].equals("clean"))
				clean();
			else if (strs[0].equals("generate"))
				generate();
			else if (strs[0].equals("help")) {
				System.out.println("quit, pwd, help");
				System.out.println("cat, cd, cp, enter, exit, ls, cat, mkdir, mv, rm, touch");
				System.out.println("clean, generate");
			} else
				System.out.println("ERROR: unknown command");

		}
		if (fs != null)
			fs.close();
		sc.close();
	}

	protected static void clean() throws IOException {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		Path p = _getPath(strs[1]);
		FileSystem fs = FileSystems.newFileSystem(p, null);
		new LibInfo(fs).clean();
		fs.close();
	}

	protected static void generate() throws IOException {
		if (strs.length < 3)
			System.out.append("ERROR not enough parameters");
		String path = _getPath(strs[1]).toString();
		String ver = strs[2];
		File f = new File(path + "/org/");
		PrintStream ps = Writer.newFile(path + "/info/info.ini");
		ps.println("file_version = 00040510");
		ps.println("number_of_libs = 1");
		ps.println(ver);
		ps.close();

		ps = Writer.newFile(path + "/info/" + ver + ".verinfo");
		ps.println("file_version = 00040510");
		ps.println("lib_version = " + ver);
		List<String> ls = new ArrayList<>();

		Files.walk(f.toPath()).forEach(p -> {
			if (!Files.isDirectory(p) && !p.endsWith(".DS_Store") && !p.endsWith("desktop.ini"))
				ls.add("add:\t./org" + p.toString().split("org", 2)[1]);
		});
		ps.println("number_of_paths = " + ls.size());
		for (String str : ls)
			ps.println(str);
		ps.close();
	}

	private static Path _getPath(String str) {
		if (str.startsWith("~"))
			return new File("./assets/libworkspace/" + str.substring(1)).toPath();
		if (str.startsWith("/"))
			return new File("." + str).toPath();
		return cur.resolve(str);
	}

	private static void _rm(Path p) throws IOException {
		if (Files.isDirectory(p)) {
			List<Path> l = new ArrayList<>();
			Files.list(p).forEach(x -> l.add(x));
			for (Path x : l)
				_rm(x);
		}
		Files.deleteIfExists(p);
	}

	private static void cat() throws IOException {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		else {
			Path temp = _getPath(strs[1]);
			if (Files.exists(temp) && Files.isRegularFile(temp))
				Files.readAllLines(temp).forEach(s -> System.out.println(s));
			else
				System.out.println("ERROR: non-exist file");
		}
	}

	private static void cd() {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		else {
			Path temp = _getPath(strs[1]);
			if (Files.exists(temp) && Files.isDirectory(temp))
				cur = temp.normalize();
			else
				System.out.println("ERROR: non-exist folder");
			if (fs == null)
				ext = cur;
		}
	}

	private static void cp() throws IOException {
		if (strs.length < 3)
			System.out.append("ERROR not enough parameters");
		Path p0 = _getPath(strs[1]);
		Path p1 = _getPath(strs[2]);
		if (Files.isDirectory(p0)) {
			if (Files.exists(p1) && !Files.isDirectory(p1))
				System.out.println("ERROR file exists in place of target directory");
			else if (Files.exists(p1) && strs[2].endsWith("/"))
				p1 = p1.resolve(p0.getFileName().toString());
			List<Path> l = new ArrayList<>();
			Files.walk(p0).forEach(p -> l.add(p));
			for (Path p : l) {
				Path tar = p1.resolve(p0.relativize(p).toString());
				if (Files.isDirectory(p) && !Files.exists(tar))
					Files.createDirectory(tar);
				else
					Files.copy(p, tar, LibInfo.RE);
			}
		} else if (!Files.exists(p1) || Files.isRegularFile(p1))
			Files.copy(p0, p1, LibInfo.RE);
		else {
			p1 = p1.resolve(p0.getFileName());
			if (Files.isDirectory(p1))
				System.out.println("ERROR: directory of the same name exists");
			else
				Files.copy(p0, p1, LibInfo.RE);
		}
	}

	private static void enter() {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		else {
			Path temp = _getPath(strs[1]);
			if (Files.exists(temp) && Files.isRegularFile(temp))
				cur = temp.normalize();
			else
				System.out.println("ERROR: non-exist file");
			try {
				fs = FileSystems.newFileSystem(temp, null);
				cur = fs.getPath("/");
			} catch (Exception e) {
				fs = null;
				System.out.println("ERROR: failed to open");
			}
		}
	}

	private static void exit() throws IOException {
		if (fs == null)
			System.out.println("ERROR: not in zip");
		else {
			cur = ext;
			fs.close();
			fs = null;
		}
	}

	private static void ls() throws IOException {
		Path temp;
		if (strs.length == 1)
			temp = cur;
		else {
			temp = _getPath(strs[1]);
			if (!Files.exists(temp) || !Files.isDirectory(temp)) {
				temp = null;
				System.out.println("ERROR: non-exist file");
			}
		}
		if (temp != null)
			Files.list(temp).forEach(p -> System.out.println(p.getFileName()));
	}

	private static void mkdir() throws IOException {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		else {
			Path temp = _getPath(strs[1]);
			if (Files.exists(temp))
				System.out.println("ERROR: directory already exists");
			else
				Files.createDirectories(temp);
		}
	}

	private static void mv() throws IOException {
		if (strs.length < 3)
			System.out.append("ERROR not enough parameters");
		Path p0 = _getPath(strs[1]);
		Path p1 = _getPath(strs[2]);
		Files.move(p0, p1);
	}

	private static void rm() throws IOException {
		if (strs.length < 2)
			System.out.append("ERROR not enough parameters");
		Path p0 = _getPath(strs[1]);
		_rm(p0);
	}

	private static void touch() throws IOException {
		if (strs.length != 2)
			System.out.println("ERROR: need parameter");
		else {
			Path temp = _getPath(strs[1]);
			if (Files.exists(temp))
				System.out.println("ERROR: file already exists");
			else
				Files.createFile(temp);
		}
	}

}
