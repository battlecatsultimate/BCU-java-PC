package decode.admin;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Encoder {

	public static void main(String[] args) {
		File f = new File("./org/");
		if (!f.exists())
			return;
		Deque<File> df = new ArrayDeque<>();
		df.addLast(f);
		List<File> lf = new ArrayList<>();
		while (df.size() > 0) {
			f = df.pollLast();
			for (File fi : f.listFiles())
				if (fi.isDirectory())
					df.addLast(fi);
				else
					lf.add(fi);
		}
		File out = new File("./lib/");
		if (!out.exists())
			out.mkdirs();
		File list = new File("./lib/001/viewer.list");
		File pack = new File("./lib/001/viewer.pack");
		try {
			if (!list.exists())
				list.createNewFile();
			if (!pack.exists())
				pack.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		PrintStream ps;
		try {
			ps = new PrintStream(list);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return;
		}
		for (File fi : lf)
			ps.println(fi.getPath() + "," + fi.length());
		ps.close();
		FileOutputStream fis;
		try {
			fis = new FileOutputStream(pack);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		BufferedOutputStream bof = new BufferedOutputStream(fis);
		for (File fi : lf)
			try {
				bof.write(Files.readAllBytes(fi.toPath()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		try {
			bof.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("done");
	}

}
