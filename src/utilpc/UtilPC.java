package utilpc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Queue;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import common.CommonStatic.Itf;
import common.battle.data.PCoin;
import common.io.InStream;
import common.io.OutStream;
import common.system.VImg;
import common.system.fake.FakeImage;
import common.system.files.FDByte;
import common.system.files.FileData;
import common.system.files.VFile;
import common.util.Res;
import common.util.anim.AnimC.AnimLoader;
import common.util.anim.AnimC;
import common.util.anim.AnimU;
import common.util.pack.Background;
import common.util.unit.Form;
import io.BCMusic;
import io.Reader;
import io.Writer;
import main.Opts;
import page.LoadPage;
import page.MainLocale;
import utilpc.awt.FG2D;

public class UtilPC {

	public static class PCItr implements Itf {

		private static class PCAL implements AnimC.AnimLoader {

			private String name;
			private FakeImage num;
			private FileData imgcut, mamodel, anims[];
			private VImg uni, edi;

			private PCAL(InStream is) {
				name = "local animation";

				try {
					num = FakeImage.read(is.nextBytesI());
				} catch (IOException e) {
					e.printStackTrace();
				}
				imgcut = new FDByte(is.nextBytesI());
				mamodel = new FDByte(is.nextBytesI());
				int n = is.nextInt();
				anims = new FileData[n];
				for (int i = 0; i < n; i++)
					anims[i] = new FDByte(is.nextBytesI());
				if (!is.end()) {
					VImg vimg = new VImg(is.nextBytesI());
					vimg.mark("uni or edi");
					if (vimg.getImg().getHeight() == 32)
						edi = vimg;
					else
						uni = vimg;
				}
				if (!is.end())
					uni = new VImg(is.nextBytesI());
				if (uni != null && uni != Res.slot[0])
					uni.mark("uni");
				if (edi != null)
					edi.mark("edi");
			}

			@Override
			public VImg getEdi() {
				return edi;
			}

			@Override
			public FileData getIC() {
				return imgcut;
			}

			@Override
			public FileData[] getMA() {
				return anims;
			}

			@Override
			public FileData getMM() {
				return mamodel;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public FakeImage getNum() {
				return num;
			}

			@Override
			public int getStatus() {
				return 1;
			}

			@Override
			public VImg getUni() {
				return uni;
			}

		}

		@Override
		public void check(File f) {
			Writer.check(f);
		}

		@Override
		public void delete(File file) {
			Writer.delete(file);
		}

		@Override
		public void exit(boolean save) {
			Writer.logClose(save);
			System.exit(0);
		}

		@Override
		public AnimLoader loadAnim(InStream is) {
			return new PCAL(is);
		}

		@Override
		public void prog(String str) {
			LoadPage.prog(str);

		}

		@Override
		public InStream readBytes(File fi) {
			return Reader.readBytes(fi);
		}

		@Override
		public VImg readReal(File fi) {
			try {
				return new VImg(ImageIO.read(fi));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public <T> T readSave(String path, Function<Queue<String>, T> func) {
			return UtilPC.readSave(path, func);
		}

		@Override
		public void redefine(Class<?> cls) {
			if (cls == AnimU.class)
				red$AnimU();
		}

		@Override
		public File route(String path) {
			return new File(path);
		}

		@Override
		public void setSE(int ind) {
			BCMusic.setSE(ind);
		}

		@Override
		public boolean writeBytes(OutStream os, String path) {
			return Writer.writeBytes(os, path);
		}

		private void red$AnimU() {
			String s0 = "move";
			String s1 = "wait";
			String s2 = "atk";
			String s3 = "kb";
			String s4 = "down";
			String s5 = "under";
			String s6 = "up";
			String s7 = "entry";
			AnimU.strs0 = MainLocale.getLoc(0, s0, s1, s2, s3);
			AnimU.strs1 = MainLocale.getLoc(0, s0, s1, s2, s3, s4, s5, s6);
			AnimU.strs2 = MainLocale.getLoc(0, s0, s1, s2, s3, s7);
		}

	}

	public static ImageIcon getBg(Background bg, int w, int h) {
		double r = h / 1100.0;
		int fw = (int) (768 * r);
		int fh = (int) (510 * r);
		bg.check();
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) temp.getGraphics();
		FG2D fg = new FG2D(g);
		if (bg.top && bg.parts.length > Background.TOP)
			for (int i = 0; i * fw < w; i++)
				fg.drawImage(bg.parts[Background.TOP], fw * i, 0, fw, fh);
		else {
			fg.gradRect(0, 0, w, fh, 0, 0, bg.cs[0], 0, fh, bg.cs[1]);
		}
		for (int i = 0; i * fw < w; i++)
			fg.drawImage(bg.parts[Background.BG], fw * i, fh, fw, fh);
		fg.gradRect(0, fh * 2, w, h - fh * 2, 0, fh * 2, bg.cs[2], 0, fh * 3, bg.cs[3]);
		g.dispose();
		return new ImageIcon(temp);
	}

	public static BufferedImage getIcon(int type, int id) {
		type += id / 100;
		id %= 100;
		if (Res.icon[type][id] == null)
			return null;
		return (BufferedImage) Res.icon[type][id].getImg().bimg();
	}

	public static ImageIcon getIcon(VImg v) {
		v.check();
		if (v.bimg == null || v.bimg.bimg() == null)
			return null;
		return new ImageIcon((Image) v.bimg.bimg());
	}

	public static String[] lvText(Form f, int[] lvs) {
		PCoin pc = f.getPCoin();
		if (pc == null)
			return new String[] { "Lv." + lvs[0], "" };
		else {
			String lab = Interpret.PCTX[pc.info[0][0]];
			String str = "Lv." + lvs[0] + ", {";
			for (int i = 1; i < 5; i++) {
				str += lvs[i] + ",";
				lab += ", " + Interpret.PCTX[pc.info[i][0]];
			}
			str += lvs[5] + "}";
			return new String[] { str, lab };
		}
	}

	private static <T> T readSave(String path, Function<Queue<String>, T> func) {
		VFile<? extends FileData> f = VFile.getFile(path);
		VFile<BackupData> b = Reader.alt == null ? null : Reader.alt.find(path);
		int ind = 0;
		while (true) {
			if (f != null && f.getData() != null) {
				T ic = null;
				Queue<String> qs = f.getData().readLine();
				if (qs != null)
					try {
						ic = func.apply(qs);
					} catch (Exception e) {
						e.printStackTrace();
						ic = null;
					}
				if (ic != null)
					return ic;
			}
			if (b == null)
				break;
			if (b.list() == null)
				if (b != f)
					f = b;
				else
					break;
			else if (ind < b.list().size())
				f = b.list().get(ind++);
			else
				break;
		}
		if (f != null)
			Opts.animErr(path);
		return func.apply(null);
	}

}
