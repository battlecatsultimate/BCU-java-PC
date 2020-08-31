package utilpc;

import com.google.common.io.Files;
import common.CommonStatic;
import common.CommonStatic.ImgReader;
import common.CommonStatic.Itf;
import common.battle.data.PCoin;
import common.io.InStream;
import common.io.OutStream;
import common.pack.Context;
import common.pack.Context.ErrType;
import common.pack.Source;
import common.pack.Source.ResourceLocation;
import common.system.VImg;
import common.system.fake.FakeImage;
import common.system.fake.ImageBuilder;
import common.system.files.FDByte;
import common.system.files.VFile;
import common.util.Data;
import common.util.anim.ImgCut;
import common.util.anim.MaAnim;
import common.util.anim.MaModel;
import common.util.pack.Background;
import common.util.stage.Music;
import common.util.unit.Form;
import io.BCMusic;
import io.BCUReader;
import io.BCUWriter;
import main.MainBCU;
import page.LoadPage;
import utilpc.awt.FG2D;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.function.Function;

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
				Data.err(() -> Context.check(f));
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

		private static class PCAL implements Source.AnimLoader {

			private String name;
			private FakeImage num;
			private final ImgCut imgcut;
			private final MaModel mamodel;
			private final MaAnim[] anims;
			private VImg uni, edi;

			private PCAL(InStream is) {
				name = "local animation";
				num = FakeImage.read(is.nextBytesI());
				imgcut = ImgCut.newIns(new FDByte(is.nextBytesI()));
				mamodel = MaModel.newIns(new FDByte(is.nextBytesI()));
				int n = is.nextInt();
				anims = new MaAnim[n];
				for (int i = 0; i < n; i++)
					anims[i] = MaAnim.newIns(new FDByte(is.nextBytesI()));
				if (!is.end()) {
					VImg vimg = ImageBuilder.toVImg(is.nextBytesI());
					if (vimg.getImg().getHeight() == 32)
						edi = vimg;
					else
						uni = vimg;
				}
				if (!is.end())
					uni = ImageBuilder.toVImg(is.nextBytesI());
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
			public ResourceLocation getName() {
				return new ResourceLocation(null, name);
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
		public void exit(boolean save) {
			BCUWriter.logClose(save);
			System.exit(0);
		}

		@Override
		public long getMusicLength(Music f) {
			if (f.data == null) {
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

		@Override
		public ImgReader getMusicReader(int pid, int mid) {
			return new MusicReader(pid, mid);
		}

		@Override
		public ImgReader getReader(File f) {
			return null;
		}

		@Override
		public Source.AnimLoader loadAnim(InStream is, ImgReader r) {
			return r == null ? new PCAL(is) : new PCAL(is, r);
		}

		@Override
		public void prog(String str) {
			LoadPage.prog(str);

		}

		@Override
		public InStream readBytes(File fi) {
			return BCUReader.readBytes(fi);
		}

		@Override
		public VImg readReal(File fi) {
			try {
				return new VImg(MainBCU.builder.build(ImageIO.read(fi)));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public <T> T readSave(String path, Function<Queue<String>, T> func) {
			return CommonStatic.ctx.noticeErr(() -> func.apply(VFile.getFile(path).getData().readLine()), ErrType.ERROR,
					"failed to read " + path);
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
			return BCUWriter.writeBytes(os, path);
		}

		@Override
		public void writeErrorLog(Exception e) {
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
		if (CommonStatic.getBCAssets().icon[type][id] == null)
			return null;
		return (BufferedImage) CommonStatic.getBCAssets().icon[type][id].getImg().bimg();
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

}
