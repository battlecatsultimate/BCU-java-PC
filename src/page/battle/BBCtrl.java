package page.battle;

import common.CommonStatic;
import common.CommonStatic.BCAuxAssets;
import common.battle.SBCtrl;
import common.system.P;
import common.system.fake.FakeImage;
import common.util.unit.Form;
import page.battle.BattleBox.BBPainter;
import page.battle.BattleBox.OuterBox;
import utilpc.PP;

import java.awt.*;
import java.awt.event.MouseEvent;

public class BBCtrl extends BBPainter {

	private final SBCtrl sbc;

	//This section is for lineup changing, detecting dragging up
	/**
	 * Initial point where drag started and ended
	 */
	private Point dragInit, dragEnd;

	/**
	 * Boolean which tells dragging up/down is performed or not
	 */
	protected boolean performed = false;

	public BBCtrl(OuterBox bip, SBCtrl bas, BattleBox bb) {
		super(bip, bas, bb);
		sbc = bas;
	}

	@Override
	public synchronized void click(Point p, int button) {
		BCAuxAssets aux = CommonStatic.getBCAssets();
		int w = box.getWidth();
		int h = box.getHeight();
		double hr = unir;
		double term = hr * aux.slot[0].getImg().getWidth() * 0.2;
		for (int i = 0; i < 5; i++) {
			Form f = sbc.sb.b.lu.fs[sbc.sb.frontLineup][i % 5];
			FakeImage img = f == null ? aux.slot[0].getImg() : f.anim.getUni().getImg();
			int iw = (int) (hr * img.getWidth());
			int ih = (int) (hr * img.getHeight());
			int x = (w - iw * 5) / 2 + iw * (i % 5) + (int) (term * ((i % 5) -2) + (sbc.sb.frontLineup == 0 ? 0 : term/2));
			int y = h - (int) (ih * 1.1);
			if (!new PP(p).out(new P(x, y), new P(x + iw, y + ih), 0))
				sbc.action.add(i+sbc.sb.frontLineup*5);
			if (button != MouseEvent.BUTTON1)
				sbc.action.add(10);
		}
		hr = corr;
		FakeImage left = aux.battle[0][0].getImg();
		FakeImage right = aux.battle[1][0].getImg();
		int ih = (int) (hr * left.getHeight());
		int iw = (int) (hr * left.getWidth());
		if (!new PP(p).out(new P(0, h - ih), new P(iw, h), 0))
			sbc.action.add(-1);
		iw = (int) (hr * right.getWidth());
		ih = (int) (hr * right.getHeight());
		if (!new PP(p).out(new P(w - iw, h - ih), new P(w, h), 0))
			sbc.action.add(-2);

		if ((sbc.sb.conf[0] & 2) > 0) {
			FakeImage bimg = aux.battle[2][1].getImg();
			int cw = bimg.getWidth();
			int ch = bimg.getHeight();
			int mh = aux.num[0][0].getImg().getHeight();
			if (!new PP(p).out(new P(w - cw, mh), new P(w, mh + ch), 0))
				sbc.action.add(-3);
		}
		reset();
	}

	@Override
	protected synchronized void release() {
		super.release();
		performed = false;
		dragInit = null;
		dragEnd = null;
	}

	@Override
	protected synchronized void drag(Point p) {
		if(!dragging) {
			dragInit = p;
		}

		dragging = true;

		dragEnd = p;

		super.drag(p);

		checkDragUpDown();
	}

	private void checkDragUpDown() {
		if(bf.sb.isOneLineup || bf.sb.ubase.health == 0 || dragInit == null || dragEnd == null || dragFrame == 0 || performed)
			return;

		final double MINIMUM_DISTANCE = box.getHeight() * 0.2;
		final double MINIMUM_VELOCITY = MINIMUM_DISTANCE / 30; //px/f cursor must be dragged in 1 sec

		if(isInDragRange(MINIMUM_DISTANCE)) {
			double dy = dragEnd.y - dragInit.y;
			double velocity = dy / dragFrame;

			if(Math.abs(velocity) >= MINIMUM_VELOCITY && Math.abs(dy) >= MINIMUM_DISTANCE) {
				//Notice program dragging up/down is already performed
				//Won't process dragging up/down until drag is reset (mouse released)
				performed = true;

				if(velocity < 0)
					sbc.action.add(-4);
				else
					sbc.action.add(-5);
			}
		}
	}

	private boolean isInDragRange(double minD) {
		double dx = dragEnd.x - dragInit.x;

		//Drag up down, dx shouldn't exceed minimum off path
		return minD >= Math.abs(dx);
	}
}
