package page.battle;

import java.awt.Point;

import common.CommonStatic.BattleConst;
import common.util.Data;
import common.util.ImgCore;
import common.util.Res;
import common.util.basis.BattleField;
import common.util.basis.StageBasis;
import common.util.entity.EAnimCont;
import common.util.entity.Entity;
import common.util.entity.attack.ContAb;
import common.util.pack.NyCastle;
import common.util.stage.Castles;
import common.util.system.P;
import common.util.system.SymCoord;
import common.util.system.VImg;
import common.util.system.fake.FakeGraphics;
import common.util.system.fake.FakeImage;
import common.util.system.fake.FakeTransform;
import common.util.unit.Form;
import page.RetFunc;

public interface BattleBox {

	static class BBPainter implements BattleConst {

		private static final double exp = 0.9, sprite = 0.8;
		private static final int road_h = 156; // in p
		private static final int off = 200;
		private static final int DEP = 4;
		private static final int bar = 8, wave = 28, castw = 128, casth = 256;
		private static final int c0y = -130, c1y = -130, c2y = -258;
		private static final int[] cany = new int[] { -134, -134, -134, -250, -250, -134, -134 };
		private static final int[] canx = new int[] { 0, 0, 0, 64, 64, 0, 0 };

		public static void drawNyCast(FakeGraphics gra, int y, int x, double siz, int[] inf) {
			FakeImage bimg = NyCastle.main[2][inf[2]].getImg();
			int bw = bimg.getWidth();
			int bh = bimg.getHeight();
			int cy = (int) (y + c0y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
			bimg = NyCastle.main[0][inf[0]].getImg();
			bw = bimg.getWidth();
			bh = bimg.getHeight();
			cy = (int) (y + c2y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
			bimg = NyCastle.main[1][inf[1]].getImg();
			bw = bimg.getWidth();
			bh = bimg.getHeight();
			cy = (int) (y + c1y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
		}

		public final BattleField bf;

		public int pt = -1;

		protected final OuterBox page;
		protected final BattleBox box;

		protected double siz, corr, unir; // siz = pix/p;

		private StageBasis sb;
		private int maxW, maxH, minH; // in p
		private int pos, midh, prew, preh; // in pix

		private P mouse; // in pix

		public BBPainter(OuterBox bip, BattleField bas, BattleBox bb) {
			page = bip;
			bf = bas;
			box = bb;
			maxW = (int) (bas.sb.st.len * ratio + off * 2);
			maxH = 510 * 3;
			minH = 510;
		}

		public void click(Point p, int button) {
		}

		public void draw(FakeGraphics g) {
			int w = box.getWidth();
			int h = box.getHeight();
			sb = bf.sb;
			if (prew != w || preh != h) {
				clear();
				prew = w;
				preh = h;
			}
			regulate();

			ImgCore.set(g);
			Point rect = new Point(box.getWidth(), box.getHeight());
			sb.bg.draw(g, rect, pos, midh, siz);
			drawCastle(g);
			drawEntity(g);
			drawBtm(g);
			drawTop(g);
			sb = null;
		}

		public double getX(double x) {
			return (x * ratio + off) * siz + pos;
		}

		public void regulate() {
			int w = box.getWidth();
			int h = box.getHeight();
			if (siz * minH > h * bar / 10)
				siz = 1.0 * h / minH;
			if (siz * maxH < h)
				siz = 1.0 * h / maxH;
			if (siz * maxW < w)
				siz = 1.0 * w / maxW;
			if (pos > 0)
				pos = 0;
			if (maxW * siz + pos < w)
				pos = (int) (w - maxW * siz);
			midh = h * bar / 10;
			if (midh > siz * minH * 2)
				midh = (int) (siz * minH * 2);

		}

		public void reset() {
			pt = bf.sb.time;
			box.reset();
		}

		private void adjust(int w, int s) {
			pos += w;
			siz *= Math.pow(exp, s);
		}

		private void clear() {
			pt = -1;
			siz = 0;
			pos = 0;
			midh = 0;
		}

		private synchronized void drag(Point p) {
			if (mouse != null) {
				P temp = new P(p);
				adjust((int) (temp.x - mouse.x), 0);
				mouse.setTo(temp);
				reset();
			}
		}

		private void drawBtm(FakeGraphics g) {
			int w = box.getWidth();
			int h = box.getHeight();
			int cw = 0;
			int time = (sb.time / 5) % 2;
			int mtype = sb.mon < sb.next_lv ? 0 : time == 0 ? 1 : 2;
			if (sb.work_lv == 8)
				mtype = 2;
			FakeImage left = Res.battle[0][mtype].getImg();
			int ctype = sb.can == sb.max_can && time == 0 ? 1 : 0;
			FakeImage right = Res.battle[1][ctype].getImg();
			cw += left.getWidth();
			cw += right.getWidth();
			cw += Res.slot[0].getImg().getWidth() * 5;
			double r = 1.0 * w / cw;
			double avah = h * (10 - bar) / 10;
			double hr = avah / left.getHeight();
			corr = hr = Math.min(r, hr);
			int ih = (int) (hr * left.getHeight());
			int iw = (int) (hr * left.getWidth());
			g.drawImage(left, 0, h - ih, iw, ih);
			iw = (int) (hr * right.getWidth());
			ih = (int) (hr * right.getHeight());
			g.drawImage(right, w - iw, h - ih, iw, ih);
			Res.getCost(sb.next_lv, mtype > 0, new SymCoord(g, hr, hr * 5, h - hr * 5, 2));
			Res.getWorkerLv(sb.work_lv, mtype > 0, new SymCoord(g, hr, hr * 5, h - hr * 130, 0));
			int hi = h;
			double marg = 0;
			if (ctype == 0)
				for (int i = 0; i < 10 * sb.can / sb.max_can; i++) {
					FakeImage img = Res.battle[1][2 + i].getImg();
					iw = (int) (hr * img.getWidth());
					ih = (int) (hr * img.getHeight());
					marg += hr * img.getHeight() - ih;
					if (marg > 0.5) {
						marg--;
						ih++;
					}
					hi -= ih;
					g.drawImage(img, w - iw, hi, iw, ih);
				}
			hr = avah / 2 / Res.slot[0].getImg().getHeight();
			hr = Math.min(r, hr);
			for (int i = 0; i < 10; i++) {
				Form f = sb.b.lu.fs[i / 5][i % 5];
				FakeImage img = f == null ? Res.slot[0].getImg() : f.anim.uni.getImg();
				iw = (int) (hr * img.getWidth());
				ih = (int) (hr * img.getHeight());
				int x = (w - iw * 5) / 2 + iw * (i % 5);
				int y = h - ih * (2 - i / 5);
				g.drawImage(img, x, y, iw, ih);
				if (f == null)
					continue;
				int pri = sb.elu.price[i / 5][i % 5];
				if (pri == -1)
					g.colRect(x, y, iw, ih, 255, 0, 0, 100);
				int cool = sb.elu.cool[i / 5][i % 5];
				boolean b = pri > sb.mon || cool > 0;
				if (b)
					g.colRect(x, y, iw, ih, 0, 0, 0, 100);
				if (sb.locks[i / 5][i % 5])
					g.colRect(x, y, iw, ih, 0, 255, 0, 100);
				if (cool > 0) {
					int dw = (int) (hr * 10);
					int dh = (int) (hr * 12);
					double cd = 1.0 * cool / sb.elu.maxC[i / 5][i % 5];
					int xw = (int) (cd * (iw - dw * 2));
					g.colRect(x + iw - dw - xw, y + ih - dh * 2, xw, dh, 0, 0, 0);
					g.colRect(x + dw, y + ih - dh * 2, iw - dw * 2 - xw, dh, 100, 212, 255);
				} else
					Res.getCost(pri, !b, new SymCoord(g, hr, x += iw, y += ih, 3));
			}
			unir = hr;
		}

		private void drawCastle(FakeGraphics gra) {
			FakeTransform at = gra.getTransform();
			boolean drawCast = sb.ebase instanceof Entity;
			int posy = (int) (midh - road_h * siz);
			int posx = (int) ((800 * ratio + off) * siz + pos);
			if (!drawCast) {
				int cind = sb.st.getCastle();
				if (cind == -1)
					cind = 0;
				VImg cast = Castles.getCastle(cind);
				FakeImage bimg = cast.getImg();
				int bw = (int) (bimg.getWidth() * siz);
				int bh = (int) (bimg.getHeight() * siz);
				gra.drawImage(bimg, posx - bw, posy - bh, bw, bh);
			} else
				((Entity) sb.ebase).anim.draw(gra, new P(posx, posy), siz * sprite);
			gra.setTransform(at);
			posx -= castw * siz / 2;
			posy -= casth * siz;
			Res.getBase(sb.ebase, new SymCoord(gra, siz, posx, posy, 0));
			posx = (int) (((sb.st.len - 800) * ratio + off) * siz + pos);
			drawNyCast(gra, (int) (midh - road_h * siz), posx, siz, sb.nyc);
			posx += castw * siz / 2;
			Res.getBase(sb.ubase, new SymCoord(gra, siz, posx, posy, 1));
		}

		private void drawEntity(FakeGraphics gra) {
			int w = box.getWidth();
			int h = box.getHeight();
			FakeTransform at = gra.getTransform();
			double psiz = siz * sprite;
			ImgCore.battle = true;
			for (int i = 0; i < 10; i++) {
				int dep = i * DEP;
				for (Entity e : sb.le)
					if (e.layer == i && (sb.s_stop == 0 || (e.getAbi() & Data.AB_TIMEI) == 0)) {
						gra.setTransform(at);
						double p = getX(e.pos);
						double y = midh - (road_h - dep) * siz;
						e.anim.draw(gra, new P(p, y), psiz);
						gra.setTransform(at);
						e.anim.drawEff(gra, new P(p, y), siz);
					}
				for (ContAb wc : sb.lw)
					if (wc.layer == i) {
						gra.setTransform(at);
						double p = (wc.pos * ratio + off - wave) * siz + pos;
						double y = midh - (road_h - DEP * wc.layer) * siz;
						wc.draw(gra, new P(p, y), psiz);
					}
				for (EAnimCont eac : sb.lea)
					if (eac.layer == i) {
						gra.setTransform(at);
						double p = getX(eac.pos);
						double y = midh - (road_h - DEP * eac.layer) * siz;
						eac.draw(gra, new P(p, y), psiz);
					}
			}

			gra.setTransform(at);
			int can = cany[sb.canon.id];
			int disp = canx[sb.canon.id];
			P ori = new P(getX(sb.ubase.pos) + disp * siz, midh + (can - road_h) * siz);
			sb.canon.drawBase(gra, ori, psiz);
			gra.setTransform(at);
			ori = new P(getX(sb.canon.pos), midh - road_h * siz);
			sb.canon.drawAtk(gra, ori, psiz);
			gra.setTransform(at);
			if (sb.sniper != null && sb.sniper.enabled) {
				ori = new P(getX(sb.sniper.getPos()), midh - road_h * siz);
				sb.sniper.drawBase(gra, ori, psiz);
				gra.setTransform(at);
			}

			if (sb.s_stop > 0) {
				gra.setComposite(FakeGraphics.GRAY, 0);
				gra.fillRect(0, 0, w, h);
				for (int i = 0; i < 10; i++) {
					int dep = i * DEP;
					for (Entity e : sb.le)
						if (e.layer == i && (e.getAbi() & Data.AB_TIMEI) > 0) {
							gra.setTransform(at);
							double p = getX(e.pos);
							double y = midh - (road_h - dep) * siz;
							e.anim.draw(gra, new P(p, y), psiz);
							gra.setTransform(at);
							e.anim.drawEff(gra, new P(p, y), siz);
						}
				}
			}
			gra.setTransform(at);
			ImgCore.battle = false;
		}

		private void drawTop(FakeGraphics g) {
			int w = box.getWidth();
			P p = Res.getMoney((int) sb.mon, sb.max_mon, new SymCoord(g, 1, w, 0, 1));
			int ih = (int) p.y;
			int n = 0;
			FakeImage bimg = Res.battle[2][1].getImg();
			int cw = bimg.getWidth();
			if ((sb.conf[0] & 2) > 0) {
				bimg = Res.battle[2][sb.sniper.enabled ? 2 : 4].getImg();
				g.drawImage(bimg, w - cw, ih);
				n++;
			}
			bimg = Res.battle[2][1].getImg();
			if ((sb.conf[0] & 1) > 0) {
				g.drawImage(bimg, w - cw * (n + 1), ih);
				n++;
			}
			bimg = Res.battle[2][page.getSpeed() > 0 ? 0 : 3].getImg();
			for (int i = 0; i < Math.abs(page.getSpeed()); i++)
				g.drawImage(bimg, w - cw * (i + 1 + n), ih);
		}

		private synchronized void press(Point p) {
			mouse = new P(p);
		}

		private synchronized void release(Point p) {
			mouse = null;
		}

		private synchronized void wheeled(Point p, int ind) {
			int w = box.getWidth();
			int h = box.getHeight();
			double psiz = siz * Math.pow(exp, ind);
			if (psiz * minH > h * bar / 10 || psiz * maxH < h || psiz * maxW < w)
				return;
			int dif = -(int) ((p.x - pos) * (Math.pow(exp, ind) - 1));
			adjust(dif, ind);
			reset();
		}

	}

	static interface OuterBox extends RetFunc {

		public int getSpeed();

	}

	public default void click(Point p, int button) {
		getPainter().click(p, button);
	}

	public default void drag(Point p) {
		getPainter().drag(p);
	}

	public int getHeight();

	public BBPainter getPainter();

	public int getWidth();

	public void paint();

	public default void press(Point p) {
		getPainter().press(p);
	}

	public default void release(Point p) {
		getPainter().release(p);
	}

	public void reset();

	public default void wheeled(Point p, int ind) {
		getPainter().wheeled(p, ind);
	}

}
