package io;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Queue;

import javax.imageio.ImageIO;

import common.CommonStatic;
import common.CommonStatic.Account;
import common.battle.BasisSet;
import common.io.DataIO;
import common.io.OutStream;
import common.util.Data;
import common.util.ImgCore;
import common.util.stage.Recd;
import common.util.unit.DIYAnim;
import decode.ZipLib;
import event.EventReader;
import main.MainBCU;
import main.Opts;
import main.Printer;
import page.MainFrame;
import page.MainLocale;
import page.battle.BattleInfoPage;
import page.support.Exporter;
import page.support.Importer;
import page.view.ViewBox;
import res.AnimatedGifEncoder;

public class Writer extends DataIO {

	private static File log, ph;
	private static WriteStream ps;

	public static boolean check(File f) {
		boolean suc = true;
		if (!f.getParentFile().exists())
			suc &= f.getParentFile().mkdirs();
		if (suc)
			try {
				if (!f.exists())
					if (f.isDirectory())
						suc &= f.mkdir();
					else
						suc &= f.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
				suc = false;
			}
		if (!suc) {
			ps.println("failed to create file: " + f.getPath());
			Opts.ioErr("failed to create file: " + f.getPath());
		}
		return suc;
	}

	public static void delete(File f) {
		if (f == null || !f.exists())
			return;
		if (f.isDirectory())
			for (File i : f.listFiles())
				delete(i);
		if (!f.delete())
			Opts.ioErr("failed to delete " + f);
	}

	public static void logClose(boolean save) {
		try {
			writeOptions();
			if (save && MainBCU.loaded && MainBCU.trueRun) {
				writeData();
			}
			if (ZipLib.lib != null)
				ZipLib.lib.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		delete(new File("./lib/"));
		if (ps.writed) {
			ps.println("version: " + Data.revVer(MainBCU.ver));
			ps.println("user: " + Account.USERNAME);
		}
		ps.close();
		ph.deleteOnExit();
		if (log.length() == 0)
			log.deleteOnExit();
		else
			BCJSON.report(log);
	}

	public static void logPrepare() {
		String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		log = new File("./logs/" + str + ".log");
		ph = new File("./logs/placeholder");

		if (!log.getParentFile().exists())
			log.getParentFile().mkdirs();
		try {
			if (!log.exists())
				log.createNewFile();
			ps = new WriteStream(log);
		} catch (IOException e) {
			e.printStackTrace();
			Opts.pop(Opts.SECTY);
			System.exit(0);
		}
		try {
			if (ph.exists()) {
				if (!Opts.conf("<html>" + "Another BCU is running in this folder or last BCU doesn't close properly. "
						+ "<br> Are you sure to run? It might damage your save.</html>")) {
					System.exit(0);
				}
			}
			ph.createNewFile();
		} catch (IOException e) {
		}
	}

	public static void logSetup() {
		if (MainBCU.WRITE) {
			System.setErr(ps);
			System.setOut(ps);
		}
	}

	public static PrintStream newFile(String str) {
		File f = new File(str);
		check(f);
		PrintStream out = null;
		try {
			out = new PrintStream(f, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public static boolean writeBytes(byte[] bs, String path) {
		File f = new File(path);
		check(f);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(bs);
			fos.close();
			return true;
		} catch (IOException e) {
			Printer.w(130, "IOE!!!");
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e1) {
					Printer.w(131, "cannot close fos");
					e1.printStackTrace();
				}
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e1) {
					Printer.w(139, "finally cannot close fos neither!");
					e1.printStackTrace();
				}
		}
		return false;
	}

	public static boolean writeBytes(OutStream os, String path) {
		os.terminate();
		byte[] md5 = os.MD5();
		File f = new File(path);
		boolean suc;
		if (!(suc = writeFile(os, f, md5))) {
			ps.println("failed to write file: " + f.getPath());
			if (Opts.writeErr0(f.getPath()))
				if (!(suc = writeFile(os, f, md5)))
					if (Opts.writeErr1(f.getPath()))
						new Exporter(os, Exporter.EXP_ERR);
		}
		return suc;
	}

	public static void writeData() {
		try {
			writeBytes(BasisSet.writeAll(), "./user/basis.v");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			// TODO write all packs
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			for (Recd r : Recd.map.values())
				if (r.marked)
					r.write();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (DIYAnim da : DIYAnim.map.values())
			if (!da.getAnimC().isSaved())
				try {
					da.getAnimC().save();
				} catch (Exception e) {
					e.printStackTrace();
				}
		try {
			ZipAccess.saveWork(MainBCU.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			MainLocale.saveWorks();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void writeGIF(AnimatedGifEncoder age, String path) {
		if (path == null)
			path = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		File f = new File("./img/" + path + ".gif");
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		age.start("./img/" + path + ".gif");
	}

	public static boolean writeImage(BufferedImage bimg, File f) {
		if (bimg == null)
			return false;
		boolean suc = Writer.check(f);
		if (suc)
			try {
				suc = ImageIO.write(bimg, "PNG", f);
			} catch (IOException e) {
				e.printStackTrace();
				suc = false;
			}
		if (!suc) {
			ps.println("failed to write image: " + f.getPath());
			if (Opts.writeErr1(f.getPath()))
				new Exporter(bimg, Exporter.EXP_ERR);
		}
		return suc;
	}

	public static void writeURL(Queue<String> qs, String name) {
		File f = new File("./url/" + name + "/" + MainBCU.getTime() + ".tsv");
		if (!f.getParentFile().exists())
			f.getParentFile().mkdirs();
		if (!f.exists())
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		PrintStream out = null;
		try {
			out = new PrintStream(new FileOutputStream(f), true, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (out == null)
			return;
		out.println("[start]");
		for (String s : qs)
			out.println(s);
		out.println("[end]");
		out.close();
	}

	private static boolean writeFile(OutStream os, File f, byte[] md5) {
		boolean suc = check(f);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			os.flush(fos);
			fos.close();
		} catch (IOException e) {
			suc = false;
			e.printStackTrace();
			Printer.w(130, "IOE!!!");
			Opts.ioErr("failed to write file " + f);
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			e.printStackTrace();
		} finally {
			if (fos != null)
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		}
		try {
			byte[] cont = Files.readAllBytes(f.toPath());
			byte[] nmd = MessageDigest.getInstance("MD5").digest(cont);
			suc &= Arrays.equals(md5, nmd);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return suc;
	}

	private static void writeOptions() {
		PrintStream out = newFile("./user/data.ini");
		out.println("lang= " + CommonStatic.Lang.lang);
		Rectangle r = MainFrame.crect;
		out.println("rect= {" + r.x + ',' + r.y + ',' + r.width + ',' + r.height + '}');
		out.println("preload= " + (MainBCU.preload ? 1 : 0));
		int[] is = ImgCore.ints;
		out.println("render= {" + is[0] + "," + is[1] + "," + is[2] + "," + is[3] + "}");
		out.println("white bg= " + (ViewBox.Conf.white ? 1 : 0));
		out.println("show axis= " + (ImgCore.ref ? 1 : 0));
		out.println("use OpenGL: " + (MainBCU.USE_JOGL ? 1 : 0));
		out.println("min opacity= " + ImgCore.deadOpa);
		out.println("max opacity= " + ImgCore.fullOpa);
		out.println("filter= " + MainBCU.FILTER_TYPE);
		out.println("location= " + EventReader.loc);
		out.println(Account.USERNAME);
		out.println(Account.PASSWORD);
		out.println("--- place holder ---");
		out.println("calendar version= " + BCJSON.cal_ver);
		out.println("BG and SE volume= " + (BCMusic.play ? 1 : 0) + ", " + BCMusic.VOL_BG + ", " + BCMusic.VOL_SE);
		out.println("edit name= " + (MainLocale.exLang ? 1 : 0));
		out.println("edit tip= " + (MainLocale.exTTT ? 1 : 0));
		Exporter.write(out);
		Importer.write(out);
		out.println("--- place holder ---");
		out.println("large screen: " + (BattleInfoPage.DEF_LARGE ? 1 : 0));
		out.println("theme= " + (MainBCU.light ? 1 : 0));
		out.println("nimbus=" + (MainBCU.nimbus ? 1 : 0));
		out.close();
	}

}

class WriteStream extends PrintStream {

	protected boolean writed = false;

	public WriteStream(File file) throws FileNotFoundException {
		super(file);
	}

	@Override
	public void println(Object str) {
		super.println(str);
		writed = true;
	}

	@Override
	public void println(String str) {
		super.println(str);
		writed = true;
	}

}
