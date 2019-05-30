package jogl.awt;

import static page.anim.IconBox.IBConf.glow;
import static page.anim.IconBox.IBConf.line;
import static page.anim.IconBox.IBConf.mode;
import static page.anim.IconBox.IBConf.type;

import java.awt.AWTException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import page.anim.IconBox;
import util.ImgCore;
import util.Res;
import util.system.fake.FakeGraphics;
import util.system.fake.FakeImage;
import util.system.fake.FakeTransform;

class GLIconBox extends GLViewBox implements IconBox {

	private static final long serialVersionUID = 1L;

	protected GLIconBox() {
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
	public void draw(FakeGraphics gra) {
		boolean b = ImgCore.ref;
		ImgCore.ref = false;
		getCtrl().predraw(gra);
		FakeTransform at = gra.getTransform();
		super.draw(gra);
		gra.setTransform(at);
		ImgCore.ref = b;
		getCtrl().postdraw(gra);
	}

	@Override
	public BufferedImage getClip() {
		Rectangle r = getBounds();
		Point p = getLocationOnScreen();
		r.x = p.x + line[0];
		r.y = p.y + line[1];
		r.width = line[2];
		r.height = line[3];
		try {
			return new Robot().createScreenCapture(r);
		} catch (AWTException e) {
			e.printStackTrace();
			return null;
		}
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