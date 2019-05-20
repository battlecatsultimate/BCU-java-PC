package page.battle;

import java.awt.Point;
import java.awt.event.MouseEvent;
import util.Res;
import util.basis.SBCtrl;
import util.system.P;
import util.system.fake.FakeImage;
import util.unit.Form;

public class BBCtrl extends BattleBox {

	private static final long serialVersionUID = 1L;

	private final SBCtrl sbc;

	protected BBCtrl(BattleInfoPage bip, SBCtrl bas) {
		super(bip, bas);
		sbc = bas;
	}

	@Override
	protected synchronized void click(Point p, int button) {
		int w = getWidth();
		int h = getHeight();
		double hr = unir;
		for (int i = 0; i < 10; i++) {
			Form f = sbc.sb.b.lu.fs[i / 5][i % 5];
			FakeImage img = f == null ? Res.slot[0].getImg() : f.anim.uni.getImg();
			int iw = (int) (hr * img.getWidth());
			int ih = (int) (hr * img.getHeight());
			int x = (w - iw * 5) / 2 + iw * (i % 5);
			int y = h - ih * (2 - i / 5);
			if (!new P(p).out(new P(x, y), new P(x + iw, y + ih), 0))
				sbc.action.add(i);
			if (button != MouseEvent.BUTTON1)
				sbc.action.add(10);
		}
		hr = corr;
		FakeImage left = Res.battle[0][0].getImg();
		FakeImage right = Res.battle[1][0].getImg();
		int ih = (int) (hr * left.getHeight());
		int iw = (int) (hr * left.getWidth());
		if (!new P(p).out(new P(0, h - ih), new P(iw, h), 0))
			sbc.action.add(-1);
		iw = (int) (hr * right.getWidth());
		ih = (int) (hr * right.getHeight());
		if (!new P(p).out(new P(w - iw, h - ih), new P(w, h), 0))
			sbc.action.add(-2);

		if ((sbc.sb.conf[0] & 2) > 0) {
			FakeImage bimg = Res.battle[2][1].getImg();
			int cw = bimg.getWidth();
			int ch = bimg.getHeight();
			int mh = Res.num[0][0].getImg().getHeight();
			if (!new P(p).out(new P(w - cw, mh), new P(w, mh + ch), 0))
				sbc.action.add(-3);
		}
		reset();
	}

}
