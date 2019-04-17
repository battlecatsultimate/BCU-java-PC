package util.pack;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import io.InStream;
import io.OutStream;
import util.Data;
import util.stage.AbCastle;
import util.stage.Castles;
import util.system.FixIndexList;
import util.system.VImg;

public class CasStore extends FixIndexList<VImg> implements AbCastle {

	private Pack pack;

	protected CasStore(Pack p, boolean reg) {
		super(new VImg[1000]);
		pack = p;
		if (reg)
			Castles.map.put(pack.id, this);
	}

	@Override
	public void add(VImg img) {
		String name = pack.id + Data.trio(nextInd());
		img.name = name;
		super.add(img);
	}

	@Override
	public int getCasID(VImg img) {
		int ind = indexOf(img);
		if (ind < 0 || img == null)
			ind = 0;
		return pack.id * 1000 + ind;
	}

	public String nameOf(VImg img) {
		return Data.trio(indexOf(img));
	}

	@Override
	public String toString() {
		return pack.toString();
	}

	protected OutStream packup() {
		OutStream cas = new OutStream();
		cas.writeString("0.3.7");
		Map<Integer, VImg> mcas = getMap();
		cas.writeInt(mcas.size());
		for (int ind : mcas.keySet()) {
			cas.writeInt(ind);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				ImageIO.write(mcas.get(ind).getImg(), "PNG", baos);
			} catch (IOException e1) {
				e1.printStackTrace();
				break;
			}
			cas.writeBytesI(baos.toByteArray());
		}
		cas.terminate();
		return cas;
	}

	protected OutStream write() {
		OutStream os = new OutStream();
		os.writeString("0.3.7");
		os.writeInt(0);
		os.terminate();
		return os;
	}

	protected void zread$p000306(InStream cas) {
		int n = cas.nextInt();
		for (int i = 0; i < n; i++) {
			int val = cas.nextInt();
			BufferedImage bimg;
			ByteArrayInputStream bais = new ByteArrayInputStream(cas.nextBytesI());
			try {
				bimg = ImageIO.read(bais);
				VImg vimg = new VImg(bimg);
				vimg.name = Data.trio(val);
				set(val, vimg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	protected void zread$t000306(InStream is) {
		is.nextInt();

		File f = new File("./res/img/" + pack.id + "/cas/");
		if (f.exists()) {
			File[] fs = f.listFiles();
			for (File fi : fs) {
				String str = fi.getName();
				if (str.length() != 7)
					continue;
				if (!str.endsWith(".png"))
					continue;
				int val = -1;
				BufferedImage bimg = null;
				try {
					val = Integer.parseInt(str.substring(0, 3));
				} catch (NumberFormatException e) {
					e.printStackTrace();
					continue;
				}
				try {
					bimg = ImageIO.read(fi);
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
				if (val >= 0 && bimg != null)
					set(val, new VImg(bimg));
			}
		}
	}

	protected void zreadp(int ver, InStream cas) {
		if (ver >= 307)
			ver = getVer(cas.nextString());

		if (ver >= 306)
			zread$p000306(cas);
	}

	protected void zreadt(int ver, InStream cas) {
		if (ver >= 307)
			ver = getVer(cas.nextString());

		if (ver >= 306)
			zread$t000306(cas);
	}

}