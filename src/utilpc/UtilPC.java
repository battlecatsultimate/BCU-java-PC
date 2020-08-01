package utilpc;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.google.common.io.Files;

import common.CommonStatic;
import common.CommonStatic.ImgReader;
import common.CommonStatic.Itf;
import common.battle.data.PCoin;
import common.io.InStream;
import common.io.OutStream;
import common.system.VImg;
import common.system.fake.FakeImage;
import common.system.files.FDByte;
import common.system.files.FileData;
import common.system.files.VFile;
import common.util.Data;
import common.util.Res;
import common.util.anim.AnimCI;
import common.util.anim.AnimU;
import common.util.anim.ImgCut;
import common.util.anim.MaAnim;
import common.util.anim.MaModel;
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

		private static class MusicReader implements ImgReader {

			private final int pid, mid;

			private MusicReader(int p, int m) {
				pid = p;
				mid = m;
			}

			@Override
			public File readFile(InStream is) {
				byte[] bs = is.subStream().nextBytesI();
				String path = "./pack/music/" + Data.hex(pid) + "/" + Data.trio(mid) + ".ogg";
				File f = CommonStatic.def.route(path);
				CommonStatic.def.check(f);
				try {
					Files.write(bs, f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return f;
			}

			@Override
			public FakeImage readImg(String str) {
				return null;
			}

			@Override
			public VImg readImgOptional(String str) {
				return null;
			}

		}

		private static class PCAL implements AnimCI.AnimLoader {

			private String name;
			private FakeImage num;
			private ImgCut imgcut;
			private MaModel mamodel;
			private MaAnim anims[];
			private VImg uni, edi;

			private PCAL(InStream is) {
				name = "local animation";

				try {
					num = FakeImage.read(is.nextBytesI());
				} catch (IOException e) {
					e.printStackTrace();
				}
				imgcut = ImgCut.newIns(new FDByte(is.nextBytesI()));
				mamodel = MaModel.newIns(new FDByte(is.nextBytesI()));
				int n = is.nextInt();
				anims = new MaAnim[n];
				for (int i = 0; i < n; i++)
					anims[i] = MaAnim.newIns(new FDByte(is.nextBytesI()));
				if (!is.end()) {
					VImg vimg = new VImg(is.nextBytesI());
					if (vimg.getImg().getHeight() == 32)
						edi = vimg;
					else
						uni = vimg;
				}
				if (!is.end())
					uni = new VImg(is.nextBytesI());
			}

			private PCAL(InStream is, ImgReader r) {
				is.nextString();
				num = r.readImg(is.nextString());
				edi = r.readImgOptional(is.nextString());
				uni = r.readImgOptional(is.nextString());
				imgcut = ImgCut.newIns(new FDByte(is.nextBytesI()));
				mamodel = MaModel.newIns(new FDByte(is.nextBytesI()));
				int n = is.nextInt();
				anims = new MaAnim[n];
				for (int i = 0; i < n; i++)
					anims[i] = MaAnim.newIns(new FDByte(is.nextBytesI()));
			}

			@Override
			public VImg getEdi() {
				return edi;
			}

			@Override
			public ImgCut getIC() {
				return imgcut;
			}

			@Override
			public MaAnim[] getMA() {
				return anims;
			}

			@Override
			public MaModel getMM() {
				return mamodel;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public FakeImage getNum(boolean load) {
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
		public ImgReader getMusicReader(int pid, int mid) {
			return new MusicReader(pid, mid);
		}

		@Override
		public ImgReader getReader(File f) {
			return null;
		}

		@Override
		public AnimCI.AnimLoader loadAnim(InStream is, ImgReader r) {
			return r == null ? new PCAL(is) : new PCAL(is, r);
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
		
		@Override
		public void writeErrorLog(Exception e) {}

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

		@Override
		public long getMusicLength(File f) {
			if(!f.exists()) {
				return -1;
			}
			
			try {
				OggTimeReader otr = new OggTimeReader(f);
				
				return otr.getTime();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return -1;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
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
