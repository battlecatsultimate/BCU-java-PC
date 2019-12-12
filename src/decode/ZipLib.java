package decode;

import java.io.File;
import java.io.IOException;
import java.nio.file.ClosedFileSystemException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import common.CommonStatic;
import common.system.files.AssetData;
import common.system.files.VFile;
import main.Opts;
import page.LoadPage;

public class ZipLib {

	public static final String[] LIBREQS = { "000001", "000002", "000003", "080602", "080603", "080504", "080604",
			"080700", "080705", "080706", "080800", "080801", "080900", "080902", "081000", "081005", "090000",
			"090100", "090102" };

	public static final String[] OPTREQS = {};

	public static FileSystem lib;
	public static LibInfo info;

	private static FileSystem customFS;
	private static LibInfo customInfo;

	public static void check() {
		for (String req : LIBREQS)
			if (info == null || !info.merge.set.contains(req)) {
				Opts.loadErr("this version requires lib " + req);
				CommonStatic.def.exit(false);
			}
	}

	public static void init() {
		LoadPage.prog("finding library...");
		// ZipAdmin.fakeLoad();if(true)return;

		File f = new File("./assets/assets.zip");
		if (!f.exists())
			return;
		try {
			lib = FileSystems.newFileSystem(f.toPath(), null);
			info = new LibInfo(lib);
		} catch (Exception e) {
			e.printStackTrace();
			Opts.loadErr("cannot access ./assets/assets.zip");
			if (Opts.conf("do you want to re-download assets?"))
				new File("./assets/assets.zip").deleteOnExit();
			CommonStatic.def.exit(false);
		}
		f = new File("./assets/custom.zip");
		if (f.exists())
			try {
				customFS = FileSystems.newFileSystem(f.toPath(), null);
				customInfo = new LibInfo(customFS);
			} catch (Exception e) {
				customFS = null;
				customInfo = null;
			}
	}

	public static void merge(File f) {
		try {
			FileSystem temp = FileSystems.newFileSystem(f.toPath(), null);
			LibInfo nlib = new LibInfo(temp);
			info.merge(nlib);
			temp.close();
			f.delete();
		} catch (IOException e) {
			Opts.loadErr("failed to merge lib");
			e.printStackTrace();
		}
	}

	public static void read() {
		LoadPage.prog("reading assets...");
		try {
			int i = 0;
			int tot = info.merge.paths.size();
			for (PathInfo pi : info.merge.paths.values()) {
				if (pi.type != 0)
					continue;
				byte[] data = Files.readAllBytes(lib.getPath(pi.path));
				VFile.root.build(pi.path, AssetData.getAsset(data));
				LoadPage.prog("reading assets " + i++ + "/" + tot);
			}

			if (customInfo != null)
				for (PathInfo pi : customInfo.merge.paths.values()) {
					if (pi.type != 0)
						continue;
					byte[] data = Files.readAllBytes(customFS.getPath(pi.path));
					VFile.root.build(pi.path, AssetData.getAsset(data));
					LoadPage.prog("reading assets " + i++ + "/" + tot);
				}

			File f = new File("./assets/custom/org/");
			if (f.exists()) {
				List<Path> l = new ArrayList<Path>();
				Files.walk(f.toPath()).forEach(path -> {
					if (Files.isDirectory(path))
						return;
					if (path.getFileName().toString().startsWith("."))
						return;
					l.add(path);
				});
				for (Path path : l) {
					String str = "." + path.toString().substring(15);
					byte[] data = Files.readAllBytes(path);
					VFile.root.build(str, AssetData.getAsset(data));
					LoadPage.prog("reading assets " + i++ + "/" + tot);
				}
			}

			VFile.root.sort();
			VFile.root.getIf(p -> {
				if (p.list() == null)
					return false;
				for (VFile<AssetData> v : p.list())
					if (!v.getName().startsWith("__LANG_"))
						return false;
				return true;
			}).forEach(p -> p.replace(AssetData.getAsset(p)));
		} catch (IOException e) {
			Opts.loadErr("failed to access library");
			e.printStackTrace();
		} catch (ClosedFileSystemException e) {
		}
		if (customFS != null)
			try {
				customFS.close();
			} catch (IOException e) {
			}
	}

}
