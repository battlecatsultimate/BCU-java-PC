package page.awt;

import static page.anim.IconBox.IBConf.glow;
import static page.anim.IconBox.IBConf.line;
import static page.anim.IconBox.IBConf.mode;
import static page.anim.IconBox.IBConf.type;

import java.awt.image.BufferedImage;

import page.anim.IconBox;
import util.ImgCore;
import util.Res;
import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.FakeTransform;

class IconBoxDef extends ViewBoxDef implements IconBox {

	private static final long serialVersionUID = 1L;

	protected IconBoxDef() {
		super(new IBCtrl());
		setFocusable(true);
		glow = 0;
		changeType();
	}

	@Override
	public void changeType() {
		FakeImage bimg = Res.ico[mode][type].getImg();
		line[2] = bimg.getWidth();
		line[3] = bimg.getHeight();
	}

	@Override
	public synchronized void draw(FakeGraphics gra) {
		boolean b = ImgCore.ref;
		ImgCore.ref = false;
		if (blank) {
			if (mode == 0 && type > 1 || mode == 1) {
				FakeImage bimg = Res.ico[mode][type].getImg();
				int bw = bimg.getWidth();
				int bh = bimg.getHeight();
				double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
				gra.drawImage(bimg, line[0], line[1], bw * r, bh * r);
				if (glow == 1) {
					gra.setComposite(FakeGraphics.BLEND, 256, -1);
					bimg = Res.ico[0][4].getImg();
					gra.drawImage(bimg, line[0], line[1], bw * r, bh * r);
					gra.setComposite(FakeGraphics.DEF);
				}
			}
		}
		FakeTransform at = gra.getTransform();
		super.draw(gra);
		gra.setTransform(at);
		ImgCore.ref = b;
		if (blank) {
			int t = mode == 0 ? type == 1 ? 1 : 0 : 3;
			FakeImage bimg = Res.ico[mode][t].getImg();
			int bw = bimg.getWidth();
			int bh = bimg.getHeight();
			double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
			gra.setColor(FakeGraphics.BLACK);
			gra.drawRect(line[0] - 1, line[1] - 1, line[2] + 1, line[3] + 1);
			if (glow == 1) {
				gra.setComposite(FakeGraphics.BLEND, 256, 1);
				bimg = Res.ico[0][4].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
				gra.setComposite(FakeGraphics.DEF);
			}
			if (mode == 0 && type > 1) {
				bimg = Res.ico[0][5].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
			} else {
				bimg = Res.ico[mode][t].getImg();
				gra.drawImage(bimg, line[0], line[1], (int) (bw * r), (int) (bh * r));
			}

		}
	}

	@Override
	public BufferedImage getClip() {
		FakeImage bimg = Res.ico[mode][type].getImg();
		int bw = bimg.getWidth();
		int bh = bimg.getHeight();
		double r = Math.min(1.0 * line[2] / bw, 1.0 * line[3] / bh);
		BufferedImage clip = prev.getSubimage(line[0], line[1], (int) (bw * r), (int) (bh * r));
		BufferedImage ans = new BufferedImage(bw, bh, BufferedImage.TYPE_3BYTE_BGR);
		ans.getGraphics().drawImage(clip, 0, 0, bw, bh, null);
		return ans;
	}

	@Override
	public IBCtrl getCtrl() {
		return (IBCtrl) ctrl;
	}

	@Override
	public void setBlank(boolean selected) {
		blank = selected;
	}

}
