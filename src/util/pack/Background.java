package util.pack;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import main.Printer;
import util.anim.AnimI;
import util.anim.EAnimD;
import util.anim.ImgCut;
import util.anim.MaAnim;
import util.anim.MaModel;
import util.system.VImg;
import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.awt.FG2D;
import util.system.files.AssetData;
import util.system.files.VFile;

public class Background extends AnimI {

	public static MaModel ewavm, uwavm;
	public static MaAnim ewava, uwava;

	private static final List<ImgCut> iclist = new ArrayList<>();

	private static final int BG = 0, TOP = 20, shift = 65; // in pix

	public static void read() {
		String path = "./org/battle/bg/";
		for (VFile<AssetData> vf : VFile.get("./org/battle/bg").list()) {
			String name = vf.getName();
			if (name.length() != 11 || !name.endsWith(".imgcut"))
				continue;
			iclist.add(ImgCut.newIns(path + name));
		}
		uwavm = MaModel.newIns("./org/battle/bg/bg_01.mamodel");
		ewavm = MaModel.newIns("./org/battle/bg/bg_02.mamodel");
		uwava = MaAnim.newIns("./org/battle/bg/bg_01.maanim");
		ewava = MaAnim.newIns("./org/battle/bg/bg_02.maanim");
		Queue<String> qs = VFile.readLine("./org/battle/bg/bg.csv");
		qs.poll();
		for (VFile<AssetData> vf : VFile.get("./org/img/bg/").list()) {
			int[] ints = new int[15];
			try {
				String[] strs = qs.poll().split(",");
				for (int i = 0; i < 15; i++)
					ints[i] = Integer.parseInt(strs[i]);
			} catch (Exception e) {
				Printer.p("BG", 53, e + "");
			}
			new Background(new VImg(vf), ints);
		}
	}

	public final Pack pack;
	public final int id;
	public final VImg img;
	public final int[][] cs = new int[4][3];
	private final WaveAnim uwav, ewav;

	public int ic;
	public boolean top;

	protected FakeImage[] parts = null;

	protected Background(Pack p, VImg vimg, int ID) {
		pack = p;
		id = p.id * 1000 + ID;
		img = vimg;
		ic = 1;
		top = true;
		uwav = BGStore.getBG(0).uwav;
		ewav = BGStore.getBG(0).ewav;
	}

	private Background(VImg vimg, int[] ints) {
		pack = Pack.def;
		id = pack.bg.size();
		img = vimg;
		top = ints[14] == 1;
		ic = ints[13];
		for (int i = 0; i < 4; i++)
			cs[i] = new int[] { ints[i * 3 + 1], ints[i * 3 + 2], ints[i * 3 + 3] };
		Pack.def.bg.add(this);
		if (id <= 107) {
			uwav = new WaveAnim(this, uwavm, uwava);
			ewav = new WaveAnim(this, ewavm, ewava);
		} else {
			uwav = BGStore.getBG(0).uwav;
			ewav = BGStore.getBG(0).ewav;
		}
	}

	@Override
	public void check() {
		if (parts != null)
			return;
		load();

	}

	public Background copy(Pack p, int id) {
		Background bg = new Background(p, new VImg(img.getImg()), id);
		for (int i = 0; i < 4; i++)
			bg.cs[i] = cs[i];
		bg.top = top;
		bg.ic = ic;
		return bg;
	}

	public void draw(FakeGraphics g, Point rect, final int pos, final int h, final double siz) {
		check();
		final int off = (int) (pos - shift * siz);
		final int fw = (int) (768 * siz);
		final int fh = (int) (510 * siz);

		g.gradRect(0, h, rect.x, rect.y - h, 0, h, cs[2], 0, h + fh, cs[3]);

		if (h > fh) {
			int y = h - fh * 2;
			if (top && parts.length > TOP) {
				for (int x = off; x < rect.x; x += fw)
					if (x + fw > 0)
						g.drawImage(parts[TOP], x, y, fw, fh);
			} else {
				g.gradRect(0, 0, rect.x, fh + y, 0, y, cs[0], 0, y + fh, cs[1]);
			}
		}
		for (int x = off; x < rect.x; x += fw)
			if (x + fw > 0)
				g.drawImage(parts[BG], x, h - fh, fw, fh);
	}

	public BufferedImage getBg(int w, int h) {
		double r = h / 1100.0;
		int fw = (int) (768 * r);
		int fh = (int) (510 * r);
		check();
		BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) temp.getGraphics();
		FG2D fg = new FG2D(g);
		if (top && parts.length > TOP)
			for (int i = 0; i * fw < w; i++)
				fg.drawImage(parts[TOP], fw * i, 0, fw, fh);
		else {
			fg.gradRect(0, 0, w, fh, 0, 0, cs[0], 0, fh, cs[1]);
		}
		for (int i = 0; i * fw < w; i++)
			fg.drawImage(parts[BG], fw * i, fh, fw, fh);
		fg.gradRect(0, fh * 2, w, h - fh * 2, 0, fh * 2, cs[2], 0, fh * 3, cs[3]);
		g.dispose();
		return temp;
	}

	@Override
	public EAnimD getEAnim(int t) {
		if (t == 1 || t == 0)
			return ewav.getEAnim(0);
		else if (t == 2)
			return uwav.getEAnim(0);
		else
			return null;
	}

	@Override
	public void load() {
		img.mark("BG");
		parts = iclist.get(ic).cut(img.getImg());
	}

	@Override
	public String[] names() {
		return new String[] { toString(), "enemy wave", "unit wave" };
	}

	@Override
	public FakeImage parts(int i) {
		return parts[i];
	}

	@Override
	public String toString() {
		return hex(pack.id) + "-" + trio(id % 1000);
	}

}
