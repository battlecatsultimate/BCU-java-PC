package decode;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import io.Writer;
import main.Opts;
import page.LoadPage;
import util.system.VFile;

public class ZipLib {

	public static FileSystem lib;

	public static void init() {
		LoadPage.prog("finding library...");
		File f = new File("./assets/assets.zip");
		if (!f.exists())
			return;
		try {
			lib = FileSystems.newFileSystem(f.toPath(), null);
		} catch (IOException e) {
			e.printStackTrace();
			Opts.loadErr("cannot access ./assets/assets.zip");
			Writer.logClose(false);
			System.exit(0);
		}
	}

	public static void read() {
		LoadPage.prog("reading assets...");
		try {
			Files.walk(lib.getPath("org")).forEach(p -> {
				if (Files.isDirectory(p))
					return;
				try {
					VFile.newFile(p.toString(), Files.readAllBytes(p));
				} catch (IOException e) {
					Opts.loadErr("failed to read " + p.toString());
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			Opts.loadErr("failed to access library");
			e.printStackTrace();
		}
	}

}
