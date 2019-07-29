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

import io.Writer;

public class ZipAdmin {

	public static void main(String[] args) throws IOException {
		File f = new File("./assets/custom/org/");
		if (f.exists()) {
			Files.walk(f.toPath()).forEach(path -> {
				if (Files.isDirectory(path))
					return;
				String str = "." + path.toString().substring(15);

				System.out.println(str);
			});
		}
	}

	protected static void addFile() throws IOException {
		File f0 = new File("./assets/assets.zip");
		File f1 = new File("./assets/080503.verinfo");
		FileSystem fs = FileSystems.newFileSystem(f0.toPath(), null);
		// Files.move(fs.getPath("/info.ini"),fs.getPath("info/info.ini"));
		Files.move(f1.toPath(), fs.getPath("info/080503.verinfo"), LibInfo.RE);
		// Files.createDirectory(fs.getPath("/info"));
		// Files.copy(f1.toPath(),fs.getPath("/080503.verinfo"),StandardCopyOption.REPLACE_EXISTING);
		fs.close();
	}

	protected static void clean(String str) throws IOException {
		FileSystem fs = FileSystems.newFileSystem(new File("./assets/" + str + ".zip").toPath(), null);
		new LibInfo(fs).clean();
		fs.close();
	}

	protected static void fakeLoad() {
		try {
			ZipLib.lib = FileSystems.newFileSystem(new File("./assets/libworkspace/assets.zip").toPath(), null);
			ZipLib.info = new LibInfo(ZipLib.lib);
			// ZipLib.merge(new File("./assets/libworkspace/080603.zip"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected static void generate() throws IOException {
		String path = "./assets/libworkspace/prelib/";
		String ver = "000003";
		File f = new File(path + "org/");
		PrintStream ps = Writer.newFile(path + "info/info.ini");
		ps.println("file_version = 00040510");
		ps.println("number_of_libs = 1");
		ps.println(ver);
		ps.close();

		ps = Writer.newFile(path + "info/" + ver + ".verinfo");
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

	protected static void merge() throws IOException {
		Path f0 = new File("./assets/libworkspace/080504.zip").toPath();
		Path f1 = new File("./assets/libworkspace/080505.zip").toPath();
		LibInfo li0 = new LibInfo(FileSystems.newFileSystem(f0, null));
		LibInfo li1 = new LibInfo(FileSystems.newFileSystem(f1, null));
		li0.merge(li1);
		li0.fs.close();
		li1.fs.close();

	}

	protected static void newFile() throws IOException {
		Files.createFile(new File("./assets/prelib/org/unprocessed/tempfile").toPath());
	}

}
