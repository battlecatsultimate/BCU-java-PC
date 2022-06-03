package page.battle;

import common.CommonStatic;
import common.CommonStatic.BCAuxAssets;
import common.CommonStatic.BattleConst;
import common.battle.BattleField;
import common.battle.StageBasis;
import common.battle.attack.ContAb;
import common.battle.attack.ContWaveAb;
import common.battle.entity.*;
import common.pack.Identifier;
import common.system.P;
import common.system.SymCoord;
import common.system.VImg;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import common.system.fake.FakeTransform;
import common.util.Data;
import common.util.ImgCore;
import common.util.Res;
import common.util.pack.EffAnim;
import common.util.pack.bgeffect.BackgroundEffect;
import common.util.stage.CastleImg;
import common.util.unit.Form;
import main.MainBCU;
import page.RetFunc;
import utilpc.PP;
import utilpc.awt.FG2D;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public interface BattleBox {

	class BBPainter implements BattleConst {

		private static final float exp = 0.9f, sprite = 0.8f;
		private static final short road_h = 156; // in p
		private static final short off = 200;
		private static final byte DEP = 4, wave = 28;
		private static final short castw = 128, casth = 256;
		private static final short c0y = -130, c1y = -130, c2y = -258;
		private static final short[] cany = new short[] { -134, -134, -134, -250, -250, -134, -134, -134 };
		private static final byte[] canx = new byte[] { 0, 0, 0, 64, 64, 0, 0, 0 };
		private static final DecimalFormat df = new DecimalFormat("00.00");
		private static final byte bar = 8;
		private static final byte BOTTOM_GAP = 48;

		public static void drawNyCast(FakeGraphics gra, int y, int x, double siz, int[] inf) {
			BCAuxAssets aux = CommonStatic.getBCAssets();
			FakeImage bimg = aux.main[2][inf[2]].getImg();
			int bw = bimg.getWidth();
			int bh = bimg.getHeight();
			int cy = (int) (y + c0y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
			bimg = aux.main[0][inf[0]].getImg();
			bw = bimg.getWidth();
			bh = bimg.getHeight();
			cy = (int) (y + c2y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
			bimg = aux.main[1][inf[1]].getImg();
			bw = bimg.getWidth();
			bh = bimg.getHeight();
			cy = (int) (y + c1y * siz);
			gra.drawImage(bimg, x, cy, (int) (bw * siz), (int) (bh * siz));
		}

		public final BattleField bf;

		public int pt = -1;

		protected final OuterBox page;
		protected final BattleBox box;

		protected double corr, unir; // siz = pix/p;

		private StageBasis sb;
		private final int maxW;
		private final int maxH = 510 * 3;
		private final int minH = 510; // in p
		private int midh, prew, preh; // in pix
		private final StageNamePainter snam;

		private double minSiz = -1;
		private double maxSiz = -1;

		private double groundHeight = -1;

		private P mouse; // in pix

		/**
		 * Dragged time for calculating velocity of cursor
		 */
		public int dragFrame = 0;
		/**
		 * Boolean which tells mouse is dragging or not
		 */
		public boolean dragging = false;

		private final BCAuxAssets aux = CommonStatic.getBCAssets();

		private final ArrayList<ContAb> efList = new ArrayList<>();

		private final SymCoord sym = new SymCoord(null, 0, 0, 0, 0);
		private final P p = new P(0, 0);

		public BBPainter(OuterBox bip, BattleField bas, BattleBox bb) {
			page = bip;
			bf = bas;
			box = bb;
			maxW = (int) (bas.sb.st.len * ratio + off * 2);
			snam = new StageNamePainter(bas.sb.st.toString());
		}

		public void click(Point p, int button) {
		}

		public void draw(FakeGraphics g) {
			int w = box.getWidth();
			int h = box.getHeight();

			sb = bf.sb;

			calculateSiz(w, h);

			if (prew != w || preh != h) {
				clear();
				prew = w;
				preh = h;
			}

			regulate();

			ImgCore.set(g);
			P rect = setP(box.getWidth(), box.getHeight());
			sb.bg.draw(g, rect, sb.pos, midh, bf.sb.siz, (int) (groundHeight + (CommonStatic.getConfig().twoRow ? (h * 0.75 / 10.0) : 0)));

			double midY = groundHeight / minSiz;
			double y = maxH * bf.sb.siz - midh;

			if(CommonStatic.getConfig().drawBGEffect)
				sb.registerBattleDimension(midY, h / minSiz);

			if(CommonStatic.getConfig().twoRow)
				midY += (h * 0.75 / 10.0);

			if(CommonStatic.getConfig().drawBGEffect && sb.bgEffect != null) {
				sb.bgEffect.preDraw(g, setP(sb.pos, y), bf.sb.siz, midY);
			}

			drawCastle(g);
			if(sb.cannon == sb.maxCannon && sb.canon.id == 0) {
				drawCannonRange(g);
			}

			drawEntity(g);

			if(CommonStatic.getConfig().drawBGEffect && sb.bgEffect != null) {
				sb.bgEffect.postDraw(g, setP(sb.pos, y), bf.sb.siz, midY);
			}

			if(sb.bg.overlay != null) {
				drawBGOverlay(g, midY);
			}

			drawBtm(g);
			drawTop(g);
		}

		public double getX(double x) {
			return (x * ratio + off) * bf.sb.siz + sb.pos;
		}

		public void calculateSiz(int w, int h) {
			minSiz = 0;
			maxSiz = Double.MAX_VALUE;

			minSiz = getReulatedSiz(minSiz, w, h);
			maxSiz = getReulatedSiz(maxSiz, w, h);

			groundHeight = (h * 2 / 10.0) * (1 - minSiz/maxSiz);
		}

		private double getReulatedSiz(double size, int w, int h) {
			if (size * minH > h)
				size = 1.0 * h / minH;
			if (size * maxH < h)
				size = 1.0 * h / maxH;
			if (size * maxW < w)
				size = 1.0 * w / maxW;

			return size;
		}

		public void regulate() {
			int w = box.getWidth();
			int h = box.getHeight();

			if (bf.sb.siz < minSiz)
				bf.sb.siz = minSiz;

			if (bf.sb.siz >= maxSiz)
				bf.sb.siz = maxSiz;

			if (sb.pos > 0)
				sb.pos = 0;

			if (maxW * bf.sb.siz + sb.pos < w)
				sb.pos = (int) (w - maxW * bf.sb.siz);

			midh = h + (int) (groundHeight * (bf.sb.siz - maxSiz) / (maxSiz - minSiz));

			if(CommonStatic.getConfig().twoRow)
				midh -= h * 0.75 / 10.0;
		}

		public void reset() {
			pt = bf.sb.time;
			box.reset();
		}

		private void adjust(int w, int s) {
			int h = box.getHeight();

			sb.pos += w;

			bf.sb.siz *= Math.pow(exp, s);

			if(bf.sb.siz * minH > h) {
				bf.sb.siz = maxSiz;
			}

			if(bf.sb.siz * maxH < h) {
				bf.sb.siz = minSiz;
			}

			if(bf.sb.siz * maxW < w) {
				bf.sb.siz = w * 1.0 / maxW;
			}
		}

		private void clear() {
			pt = -1;
			bf.sb.siz = 0;
			sb.pos = 0;
			midh = 0;
		}

		private SymCoord setSym(FakeGraphics g, double r, double x, double y, int t) {
			sym.g = g;
			sym.r = r;
			sym.x = x;
			sym.y = y;
			sym.type = t;

			return sym;
		}

		private P setP(double x, double y) {
			p.x = x;
			p.y = y;

			return p;
		}

		private void drawBtm(FakeGraphics g) {
			int w = box.getWidth();
			int h = box.getHeight();
			int cw = 0;
			int time = (sb.time / 5) % 2;
			int mtype = sb.money < sb.upgradeCost ? 0 : time == 0 ? 1 : 2;
			if (sb.work_lv == 8)
				mtype = 2;
			FakeImage left = aux.battle[0][mtype].getImg();
			int ctype = sb.cannon == sb.maxCannon && time == 0 ? 1 : 0;
			FakeImage right = aux.battle[1][ctype].getImg();
			cw += left.getWidth();
			cw += right.getWidth();
			cw += aux.slot[0].getImg().getWidth() * 5;
			double r = 1.0 * w / cw;
			double avah = h * (10 - bar) / 10.0;
			double hr = avah / left.getHeight();
			corr = hr = Math.min(r, hr);
			int ih = (int) (hr * left.getHeight());
			int iw = (int) (hr * left.getWidth());
			g.drawImage(left, - BOTTOM_GAP * hr, h - ih, iw, ih);
			iw = (int) (hr * right.getWidth());
			ih = (int) (hr * right.getHeight());
			g.drawImage(right, w - iw + BOTTOM_GAP * hr, h - ih, iw, ih);
			Res.getCost(sb.getUpgradeCost(), mtype > 0, setSym(g, hr, hr * 5, h - hr * 5, 2));
			Res.getWorkerLv(sb.work_lv, mtype > 0, setSym(g, hr, hr * 5, h - hr * 130, 0));
			int hi = h;
			double marg = 0;
			if (ctype == 0)
				for (int i = 0; i < 10 * sb.cannon / sb.maxCannon; i++) {
					FakeImage img = aux.battle[1][2 + i].getImg();
					iw = (int) (hr * img.getWidth());
					ih = (int) (hr * img.getHeight());
					marg += hr * img.getHeight() - ih;
					if (marg > 0.5) {
						marg--;
						ih++;
					}
					hi -= ih;
					g.drawImage(img, w - iw + BOTTOM_GAP * hr, hi, iw, ih);
				}
			if(sb.cannon == sb.maxCannon) {
				FakeImage fire = aux.battle[1][getFireLang()+ctype].getImg();

				int fw = (int) (hr * fire.getWidth());
				int fh = (int) (hr * fire.getHeight());

				g.drawImage(fire, w - fw - 4 * hr, h - fh - 4 * hr, fw, fh);
			}
			//Decide lineup icon's size, 0.675 is guessed value by comparing BC and BCU
			hr = avah * 0.675 / aux.slot[0].getImg().getHeight();
			//Make lineup won't cover cannon button and money upgrade button
			hr = Math.min(hr, (box.getWidth()-iw*2.0)/aux.slot[0].getImg().getWidth()/5.9);

			double term = hr * aux.slot[0].getImg().getWidth() * 0.2;

			if(!CommonStatic.getConfig().twoRow) {
				if(sb.isOneLineup) {
					drawLineup(g, w, h, hr, term, false, 0);
				} else {
					drawLineup(g, w, h, hr, term, true, 1-sb.frontLineup);
					drawLineup(g, w, h, hr, term, false, sb.frontLineup);
				}
			} else {
				double termh = hr * aux.slot[0].getImg().getHeight() * 0.1;

				drawLineupWithTwoRows(g, w, h ,hr, term, termh);
			}

			unir = hr;
		}

		private int getFireLang() {
			switch (CommonStatic.getConfig().lang) {
				case 1:
					return 18;
				case 2:
					return 16;
				case 3:
					return 12;
				default:
					return 14;
			}
		}

		private void drawLineupWithTwoRows(FakeGraphics g, int w, int h, double hr, double term, double termh) {
			int iw;
			int ih;
			int imw;
			int imh;

			for (int i = 0; i < 2; i++) {
				for(int j = 0; j < 5; j++) {
					Form f = sb.b.lu.fs[i][j];
					FakeImage img = f == null ? aux.slot[0].getImg() : f.anim.getUni().getImg();

					iw = (int) (hr * img.getWidth());
					ih = (int) (hr * img.getHeight());

					imw = iw;
					imh = ih;

					if(sb.selectedUnit[0] != -1 && sb.selectedUnit[0] == i && sb.selectedUnit[1] == j) {
						switch (sb.buttonDelay) {
							case 3:
								imw *= 0.95;
								imh *= 0.95;
								break;
							case 4:
								imw *= 1.05;
								imh *= 1.05;
						}
					}

					int x = (w - iw * 5) / 2 + iw * (j % 5) + (int) (term * ((j % 5) - 2));
					int y = (int) (h - (2 - i) * (ih + termh));

					g.drawImage(img, x - (imw - iw) / 2.0, y - (imh - ih) / 2.0, imw, imh);

					if(f == null)
						continue;

					int pri = sb.elu.price[i][j];
					if (pri == -1)
						g.colRect(x, y, iw, ih, 255, 0, 0, 100);
					int cool = sb.elu.cool[i][j];
					boolean b = pri > sb.money || cool > 0;
					if (b)
						g.colRect((int) (x - (imw - iw) / 2.0), (int) (y - (imh - ih) / 2.0), imw, imh, 0, 0, 0, 100);
					if (sb.locks[i][j])
						g.colRect((int) (x - (imw - iw) / 2.0), (int) (y - (imh - ih) / 2.0), imw, imh, 0, 255, 0, 100);
					if (cool > 0) {
						int dw = (int) (hr * 10);
						int dh = (int) (hr * 12);
						double cd = 1.0 * cool / sb.elu.maxC[i][j];
						int xw = (int) (cd * (iw - dw * 2));
						g.colRect(x + iw - dw - xw, y + ih - dh * 2, xw, dh, 0, 0, 0, -1);
						g.colRect(x + dw, y + ih - dh * 2, iw - dw * 2 - xw, dh, 100, 212, 255, -1);
					} else
						Res.getCost(pri == -1 ? -1 : pri / 100, !b, setSym(g, hr, x + iw * 1.05, y + ih * 1.05, 3));
				}
			}
		}

		private void drawLineup(FakeGraphics g, int w, int h, double hr, double term, boolean isBehind, int index) {
			int iw;
			int ih;
			int imw;
			int imh;

			for (int i = 0; i < 5; i++) {
				Form f = sb.b.lu.fs[index][i];
				FakeImage img = f == null ? aux.slot[0].getImg() : f.anim.getUni().getImg();
				iw = (int) (hr * img.getWidth());
				ih = (int) (hr * img.getHeight());

				imw = iw;
				imh = ih;

				if(sb.selectedUnit[0] != -1 && sb.selectedUnit[0] == index && sb.selectedUnit[1] == i) {
					switch (sb.buttonDelay) {
						case 3:
							imw *= 0.95;
							imh *= 0.95;
							break;
						case 4:
							imw *= 1.05;
							imh *= 1.05;
					}
				}

				int x = (w - iw * 5) / 2 + iw * i + (int) (term * (i - 2) + (index == 0 ? 0 : (term / 2)));
				int y = h - ih - (isBehind ? 0 : (int) (ih * 0.1));

				//Check if lineup is changing
				if(sb.changeFrame != -1) {
					if(sb.changeFrame >= sb.changeDivision) {
						double dis = isBehind ? ih * 0.5 : sb.goingUp ? ih * 0.4 : ih * 0.6;

						y += (dis / sb.changeDivision) * (sb.changeDivision * 2 - sb.changeFrame) * (isBehind ? 1 : -1) * (sb.goingUp ? 1 : -1);
					} else {
						double dis = isBehind ? ih * 0.5 : sb.goingUp ? ih * 0.6 : ih * 0.4;

						y +=  (dis - (dis / sb.changeDivision) * (sb.changeDivision - sb.changeFrame)) * (isBehind ? -1 : 1) * (sb.goingUp ? 1 : -1);
					}
				}

				g.drawImage(img, x - (imw - iw) / 2.0, y - (imh - ih) / 2.0, imw, imh);
				if (f == null)
					continue;
				int pri = sb.elu.price[index][i];
				if (pri == -1)
					g.colRect(x, y, iw, ih, 255, 0, 0, 100);
				int cool = sb.elu.cool[index][i];
				boolean b = isBehind || pri > sb.money || cool > 0;
				if (b)
					g.colRect((int) (x - (imw - iw) / 2.0), (int) (y - (imh - ih) / 2.0), imw, imh, 0, 0, 0, 100);
				if (sb.locks[index][i])
					g.colRect((int) (x - (imw - iw) / 2.0), (int) (y - (imh - ih) / 2.0), imw, imh, 0, 255, 0, 100);
				if(!isBehind) {
					if (cool > 0) {
						int dw = (int) (hr * 10);
						int dh = (int) (hr * 12);
						double cd = 1.0 * cool / sb.elu.maxC[index][i];
						int xw = (int) (cd * (iw - dw * 2));
						g.colRect(x + iw - dw - xw, y + ih - dh * 2, xw, dh, 0, 0, 0, -1);
						g.colRect(x + dw, y + ih - dh * 2, iw - dw * 2 - xw, dh, 100, 212, 255, -1);
					} else
						Res.getCost(pri == -1 ? -1 : pri / 100, !b, setSym(g, hr, x + iw, y + ih, 3));
				}
			}
		}

		private void drawCannonRange(FakeGraphics g) {
			FakeImage range = aux.battle[1][20].getImg();
			FakeImage cann = aux.battle[1][21].getImg();

			double rang = sb.ubase.pos + 100 + 56 * 4;

			for(int i = 0; i < sb.b.t().tech[Data.LV_CRG]+2; i++) {
				rang -= 405;
			}

			rang = Math.max(rang, sb.ebase.pos * ratio - off / 2.0);

			rang = getX(rang);

			double rw = range.getWidth() * 0.75 * bf.sb.siz;
			double rh = range.getHeight()  * 0.85 * bf.sb.siz;

			//102 is guessed value, making range indicator on ground
			g.drawImage(range, rang, midh - rh - 102 * bf.sb.siz, rw, rh);

			int rtime = (int) (sb.time / 1.5) % 4;

			double canw = cann.getWidth() * 0.75 * bf.sb.siz;
			double canh = cann.getHeight() * 0.75 * bf.sb.siz;

			g.drawImage(cann, rang + rw / 2.0 - canw / 2.0, midh - canh - rh - 102 * bf.sb.siz - Math.abs(rtime - 2) * 8 * bf.sb.siz, canw, canh);
		}

		private void drawCastle(FakeGraphics gra) {
			FakeTransform at = gra.getTransform();
			boolean drawCast = sb.ebase instanceof Entity;
			int posy = (int) (midh - road_h * bf.sb.siz);
			int posx = (int) ((sb.ebase.pos * ratio + off) * bf.sb.siz + sb.pos);

			double shake = 0.0;

			if(sb.ebase.health <= 0 || (drawCast ? ((EEnemy) sb.ebase).hit : ((ECastle) sb.ebase).hit) > 0) {
				shake = (2 + (sb.time % 2 * -4)) * bf.sb.siz;
			}

			if (!drawCast) {
				Identifier<CastleImg> cind = sb.st.castle;
				VImg cast = Identifier.getOr(cind, CastleImg.class).img;
				FakeImage bimg = cast.getImg();
				int bw = (int) (bimg.getWidth() * bf.sb.siz);
				int bh = (int) (bimg.getHeight() * bf.sb.siz);
				gra.drawImage(bimg, posx - bw + shake, posy - bh, bw, bh);
			} else {
				if(sb.temp_inten == 0 || (sb.ebase.getAbi() & Data.AB_TIMEI) == 0) {
					posx = (int) getX(sb.ebase.pos);

					((Entity) sb.ebase).anim.draw(gra, setP(posx + shake, posy), bf.sb.siz * sprite);

					if(sb.ebase.health > 0)
						((Entity) sb.ebase).anim.drawEff(gra, setP(posx + shake, posy), bf.sb.siz * sprite);
				}
			}
			gra.setTransform(at);
			posx -= castw * bf.sb.siz / 2;
			posy -= casth * bf.sb.siz;
			Res.getBase(sb.ebase, setSym(gra, bf.sb.siz, posx, posy, 0), bf.sb.st.trail);
			posx = (int) (((sb.st.len - 800) * ratio + off) * bf.sb.siz + sb.pos);

			shake = 0.0;

			if(sb.ubase.health <= 0 || ((ECastle)sb.ubase).hit > 0) {
				shake = (2 + (sb.time % 2 * -4)) * bf.sb.siz;
			}

			drawNyCast(gra, (int) (midh - road_h * bf.sb.siz), (int) (posx + shake), bf.sb.siz, sb.nyc);
			posx += castw * bf.sb.siz / 2;
			Res.getBase(sb.ubase, setSym(gra, bf.sb.siz, posx, posy, 1), false);
		}

		@SuppressWarnings("UseBulkOperation")
		private void drawEntity(FakeGraphics gra) {
			for(int i = 0; i < sb.lw.size(); i++) {
				efList.add(sb.lw.get(i));
			}

			int w = box.getWidth();
			int h = box.getHeight();

			FakeTransform at = gra.getTransform();

			double psiz = bf.sb.siz * sprite;

			CommonStatic.getConfig().battle = true;

			for(int i = 0; i < sb.le.size(); i++) {
				Entity e = sb.le.get(i);

				int dep = e.layer * DEP;

				while(efList.size() > 0) {
					ContAb wc = efList.get(0);

					if(wc.layer + 1 <= e.layer) {
						drawEff(gra, wc, at, psiz);
						efList.remove(0);
					} else
						break;
				}

				gra.setTransform(at);

				double p = getX(e.pos);
				double y = midh - (road_h - dep) * bf.sb.siz;

				e.anim.draw(gra, setP(p, y), psiz);

				gra.setTransform(at);

				if(e.anim.corpse == null || e.anim.corpse.type == EffAnim.ZombieEff.BACK) {
					e.anim.drawEff(gra, setP(p, y), bf.sb.siz);
				}
			}

			for(int i = 0; i < sb.le.size(); i++) {
				Entity e = sb.le.get(i);

				if(e.anim.smoke != null && !e.anim.smoke.done()) {
					gra.setTransform(at);

					double sx = getX(e.anim.smokeX);
					double sy = midh - (road_h - e.anim.smokeLayer * DEP + 75.0) * bf.sb.siz;

					e.anim.smoke.draw(gra, setP(sx, sy), psiz * 1.2);
				}
			}

			if(sb.ebase instanceof Entity) {
				if(sb.temp_inten == 0 || (sb.ebase.getAbi() & Data.AB_TIMEI) > 0) {
					if(((Entity) sb.ebase).anim.smoke != null && !((Entity) sb.ebase).anim.smoke.done()) {
						gra.setTransform(at);

						double sx = getX(((Entity) sb.ebase).anim.smokeX);
						double sy = midh - (road_h - ((Entity) sb.ebase).anim.smokeLayer * DEP + 100.0) * bf.sb.siz;

						((Entity) sb.ebase).anim.smoke.draw(gra, setP(sx, sy), psiz * 1.2);
					}
				}
			} else if(sb.ebase instanceof ECastle) {
				if(sb.temp_inten == 0 && ((ECastle) sb.ebase).smoke != null && !((ECastle) sb.ebase).smoke.done()) {
					gra.setTransform(at);

					double sx = getX(((ECastle) sb.ebase).smokeX);
					double sy = midh - (road_h - ((ECastle) sb.ebase).smokeLayer * DEP + 100.0) * bf.sb.siz;

					((ECastle) sb.ebase).smoke.draw(gra, setP(sx, sy), psiz * 1.2);
				}
			}

			if(sb.ubase instanceof ECastle) {
				if(sb.temp_inten == 0 && ((ECastle) sb.ubase).smoke != null && !((ECastle) sb.ubase).smoke.done()) {
					gra.setTransform(at);

					double sx = getX(((ECastle) sb.ubase).smokeX);
					double sy = midh - (road_h - ((ECastle) sb.ubase).smokeLayer * DEP + 100.0) * bf.sb.siz;

					((ECastle) sb.ubase).smoke.draw(gra, setP(sx, sy), psiz * 1.2);
				}
			}

			while(efList.size() > 0) {
				drawEff(gra, efList.get(0), at, psiz);
				efList.remove(0);
			}

			for(int i = 0; i < sb.lea.size(); i++) {
				EAnimCont eac = sb.lea.get(i);

				int dep = eac.layer * DEP;

				gra.setTransform(at);
				double p = getX(eac.pos);
				double y = midh - (road_h - dep) * bf.sb.siz;

				if (eac instanceof WaprCont) {
					double dx = ((WaprCont) eac).dire == -1 ? -27 * bf.sb.siz : -24 * bf.sb.siz;
					eac.draw(gra, setP(p + dx, y - 24 * bf.sb.siz), psiz);
				} else {
					eac.draw(gra, setP(p, y), psiz);
				}
			}

			if(sb.ebase.health <= 0) {
				for(int i = 0; i < sb.ebaseSmoke.size(); i++) {
					EAnimCont eac = sb.ebaseSmoke.get(i);

					gra.setTransform(at);
					double p = getX(eac.pos);
					double y = midh - (road_h - DEP * eac.layer) * bf.sb.siz;

					eac.draw(gra, setP(p, y), psiz * 1.2);
				}
			}

			if(sb.ubase.health <= 0) {
				for(int i = 0; i < sb.ubaseSmoke.size(); i++) {
					EAnimCont eac = sb.ubaseSmoke.get(i);

					gra.setTransform(at);
					double p = getX(eac.pos);
					double y = midh - (road_h - DEP * eac.layer) * bf.sb.siz;

					eac.draw(gra, setP(p, y), psiz * 1.2);
				}
			}

			gra.setTransform(at);
			int can = cany[sb.canon.id];
			int disp = canx[sb.canon.id];
			setP(getX(sb.ubase.pos) + disp * bf.sb.siz, midh + (can - road_h) * bf.sb.siz);
			sb.canon.drawBase(gra, p, psiz);
			gra.setTransform(at);
			setP(getX(sb.canon.pos), midh - road_h * bf.sb.siz);
			sb.canon.drawAtk(gra, p, psiz);
			gra.setTransform(at);
			if (sb.sniper != null && sb.sniper.enabled) {
				setP(getX(sb.sniper.getPos()), midh - road_h * bf.sb.siz);
				sb.sniper.drawBase(gra, p, psiz);
				gra.setTransform(at);
			}

			if (sb.temp_inten > 0) {
				gra.setComposite(FakeGraphics.GRAY, 0, 0);
				gra.fillRect(0, 0, w, h);

				if((sb.ebase.getAbi() * Data.AB_TIMEI) != 0) {
					double shake = 0.0;

					if(sb.ebase.health <= 0 || (sb.ebase instanceof ECastle && ((ECastle) sb.ebase).hit > 0) || (sb.ebase instanceof EEnemy && ((EEnemy) sb.ebase).hit > 0)) {
						shake = (2 + (sb.time % 2 * -4)) * bf.sb.siz;
					}

					if (sb.ebase instanceof Entity) {
						int posx = (int) getX(sb.ebase.pos);
						int posy = (int) (midh - road_h * bf.sb.siz);

						((Entity) sb.ebase).anim.draw(gra, setP(posx + shake, posy), bf.sb.siz * sprite);

						if(((Entity) sb.ebase).anim.smoke != null) {
							((Entity) sb.ebase).anim.smoke.draw(gra, setP(posx + shake, posy), bf.sb.siz * sprite);
						}

						if (sb.ebase.health > 0 && (((Entity) sb.ebase).anim.corpse == null || ((Entity) sb.ebase).anim.corpse.type == EffAnim.ZombieEff.BACK))
							((Entity) sb.ebase).anim.drawEff(gra, setP(posx + shake, posy), bf.sb.siz * sprite);
					}
				}

				for(int i = 0; i < sb.le.size(); i ++) {
					Entity e = sb.le.get(i);

					if ((e.getAbi() & Data.AB_TIMEI) > 0) {
						int dep = e.layer * DEP;

						gra.setTransform(at);

						double p = getX(e.pos);
						double y = midh - (road_h - dep) * bf.sb.siz;

						e.anim.draw(gra, setP(p, y), psiz);

						if(e.anim.smoke != null && e.anim.smokeLayer != -1 && !e.anim.smoke.done()) {
							gra.setTransform(at);

							e.anim.smoke.draw(gra, setP(p, midh - (road_h - e.anim.smokeLayer * DEP + 75.0) * bf.sb.siz), psiz);
						}

						gra.setTransform(at);

						if(e.anim.corpse == null || e.anim.corpse.type == EffAnim.ZombieEff.BACK) {
							e.anim.drawEff(gra, setP(p, y), bf.sb.siz);
						}
					}
				}
			}
			gra.setTransform(at);
			CommonStatic.getConfig().battle = false;
		}

		private void drawEff(FakeGraphics gra, ContAb wc, FakeTransform at, double pSiz) {
			int dep = wc.layer * DEP;

			gra.setTransform(at);

			double p = (wc.pos * ratio + off) * bf.sb.siz + sb.pos;

			if(wc instanceof ContWaveAb)
				p -= wave * bf.sb.siz;

			double y = midh - (road_h - dep) * bf.sb.siz;

			wc.draw(gra, setP(p, y), pSiz);
		}

		private void drawTop(FakeGraphics g) {
			int w = box.getWidth();
			SymCoord sym = setSym(g, 1, w-aux.num[0][0].getImg().getHeight()*0.2, aux.num[0][0].getImg().getHeight()*0.2, 1);
			P p = Res.getMoney(sb.getMoney(), sb.getMaxMoney(), sym);
			int ih = (int) p.y + (int) (aux.num[0][0].getImg().getHeight()*0.2);
			int n = 0;
			FakeImage bimg = aux.battle[2][1].getImg();
			int cw = bimg.getWidth();
			if ((sb.conf[0] & 2) > 0 && sb.sniper != null) {
				bimg = aux.battle[2][sb.sniper.enabled ? 2 : 4].getImg();
				g.drawImage(bimg, w - cw, ih);
				n++;
			}
			bimg = aux.battle[2][1].getImg();
			if ((sb.conf[0] & 1) > 0) {
				g.drawImage(bimg, w - cw * (n + 1), ih);
				n++;
			}
			bimg = aux.battle[2][page.getSpeed() > 0 ? 0 : 3].getImg();
			for (int i = 0; i < Math.abs(page.getSpeed()); i++)
				g.drawImage(bimg, w - cw * (i + 1 + n), ih);

			if (snam.img != null) {
				g.drawImage(snam.img, box.getHeight() * 0.005, box.getHeight() * 0.01, snam.img.getWidth() * 1.25, snam.img.getHeight() * 1.125);
				if(bf.sb.st.timeLimit != 0)
					drawTime(g, snam.img.getHeight() * 0.9);
			} else if(bf.sb.st.timeLimit != 0)
				drawTime(g, 0);
		}

		private void drawTime(FakeGraphics g, double nameheight) {
			P p = P.newP(box.getHeight() * 0.01, box.getHeight() * 0.01 + nameheight);
			double ratio = box.getHeight() * 0.1 / aux.timer[0].getImg().getHeight();

			double timeLeft = bf.sb.st.timeLimit * 60.0 - bf.sb.time / 30.0;

			int min = (int) timeLeft / 60;

			timeLeft -= min * 60.0;

			FakeImage separator = aux.timer[10].getImg();
			FakeImage zero = aux.timer[0].getImg();

			if(timeLeft < 0 || min < 0) {
				for(int i = 0; i < 3; i ++) {
					g.drawImage(zero, p.x, p.y, zero.getWidth() * ratio, zero.getHeight() * ratio);
					p.x += zero.getWidth() * ratio;
					g.drawImage(zero, p.x, p.y, zero.getWidth() * ratio, zero.getHeight() * ratio);
					p.x += zero.getWidth() * ratio;
					if(i != 2) {
						g.drawImage(separator, p.x, p.y, separator.getWidth() * ratio, separator.getHeight() * ratio);
						p.x += separator.getWidth() * ratio;
					}
				}

				return;
			}

			if(min < 10) {
				FakeImage m = aux.timer[min].getImg();

				g.drawImage(zero, p.x, p.y, zero.getWidth() * ratio, zero.getHeight() * ratio);
				p.x += zero.getWidth() * ratio;

				g.drawImage(m, p.x, p.y, m.getWidth()*ratio, m.getHeight()*ratio);
				p.x += m.getWidth() * ratio;
			}

			g.drawImage(separator, p.x, p.y, separator.getWidth() * ratio, separator.getHeight() * ratio);
			p.x += separator.getWidth() * ratio;

			FakeImage m;

			String time = df.format(timeLeft);

			for(int i = 0; i < time.length(); i++) {
				if((time.charAt(i)) == '.' || (time.charAt(i)) == ',') {
					g.drawImage(separator, p.x, p.y, separator.getWidth() * ratio, separator.getHeight() * ratio);
					p.x += separator.getWidth() * ratio;
				} else {
					int index = Character.getNumericValue(time.charAt(i));

					if(index == -1) {
						throw new IllegalStateException("Invalid index : "+index+" | Tried to convert char : " + time.charAt(i));
					}

					m = aux.timer[index].getImg();

					g.drawImage(m, p.x, p.y, m.getWidth()*ratio, m.getHeight()*ratio);
					p.x += m.getWidth() * ratio;
				}
			}

			P.delete(p);
		}

		protected synchronized void drawBGOverlay(FakeGraphics gra, double midY) {
			if(sb.bg.overlay == null)
				return;

			gra.gradRectAlpha(sb.pos, - (int) (maxH * bf.sb.siz - midh - midY * bf.sb.siz), (int) ((sb.st.len * ratio + 400) * bf.sb.siz), (int) ((BackgroundEffect.BGHeight * 3 + midY) * bf.sb.siz), sb.pos, 0, sb.bg.overlayAlpha, sb.bg.overlay[1], sb.pos, (int) (BackgroundEffect.BGHeight * 3 * bf.sb.siz - maxH * bf.sb.siz + midh + midY * bf.sb.siz), sb.bg.overlayAlpha, sb.bg.overlay[0]);
		}

		protected synchronized void drag(Point p) {
			if (mouse != null) {
				P temp = new PP(p);
				adjust((int) (temp.x - mouse.x), 0);
				mouse.setTo(temp);
				reset();
			}
		}

		private synchronized void press(Point p) {
			mouse = new PP(p);
		}

		protected synchronized void release() {
			dragging = false;
			dragFrame = 0;
			mouse = null;
		}

		private synchronized void wheeled(Point p, int ind) {
			int w = box.getWidth();
			int h = box.getHeight();
			double psiz = bf.sb.siz * Math.pow(exp, ind);

			if(psiz * minH > h)
				psiz = maxSiz / bf.sb.siz;
			else if(psiz * maxH < h)
				psiz = minSiz / bf.sb.siz;
			else if(psiz * maxW < w)
				psiz = minSiz / bf.sb.siz;
			else
				psiz = Math.pow(exp, ind);

			int dif = -(int) ((p.x - sb.pos) * (psiz - 1));
			adjust(dif, ind);
			reset();
		}

	}

	class StageNamePainter {
		private final FakeImage img;
		private static Font font;
		private static final float strokeWidth = 12f;
		private static final byte space = 30;
		private static final byte xGap = 5;
		private static final byte yGap = 2;

		public static void read() {
			try {
				File f = CommonStatic.ctx.getAssetFile("/fonts/stage_font.otf");
				if (!f.exists()) {
					System.out.println("Error creating font: Couldn't find stage font file");
					return;
				}

				font = Font.createFont(Font.TRUETYPE_FONT, f).deriveFont(102f);
			} catch (Exception e) {
				System.out.println("Failed to initialize font");
				e.printStackTrace();
			}
		}

		public StageNamePainter(String str) {
			BufferedImage result = font != null && str.length() != 0 ? generateImage(str) : null;
			if (result != null)
				img = MainBCU.builder.build(result);
			else
				img = null;
		}

		private BufferedImage generateBufferedImage(String message) {
			if(valid(message)) {
				AffineTransform affine = new AffineTransform();

				FontRenderContext frc = new FontRenderContext(affine, true, false);

				float w = generateWidth(message, frc);
				float[] h = generateHeight(message, frc, affine);

				BufferedImage img = new BufferedImage((int) (w + strokeWidth * 2 + xGap * 2), (int) (h[0] + h[1] + strokeWidth * 2 + yGap * 2), BufferedImage.TYPE_INT_ARGB_PRE);

				FG2D g = new FG2D(img.getGraphics());

				g.setRenderingHint(3, 2);
				g.enableAntialiasing();

				float pad = 0.0f;

				for(int i = 0; i < message.length(); i++) {
					String str = Character.toString(message.charAt(i));

					if(str.equals(" ")) {
						pad += space;
						continue;
					}

					Shape outline = font.createGlyphVector(frc, str).getGlyphOutline(0);

					float[] offset = decideOffset(pad, h[0] + h[1], h[1]);
					float left = getLeftPoint(outline.getPathIterator(affine));

					offset[0] -= left - strokeWidth - xGap;
					offset[1] += strokeWidth;

					Path2D path = generatePath2D(offset, outline.getPathIterator(affine));

					g.drawFontOutline(path, strokeWidth * 2);

					pad += generateLetterWidth(str, frc) + 4;
				}

				pad = 0.0f;

				for(int i = 0; i < message.length(); i++) {
					String str = Character.toString(message.charAt(i));

					if(str.equals(" ")) {
						pad += space;
						continue;
					}

					Shape outline = font.createGlyphVector(frc, str).getGlyphOutline(0);

					float[] offset = decideOffset(pad, h[0] + h[1], h[1]);
					float left = getLeftPoint(outline.getPathIterator(affine));

					offset[0] -= left - strokeWidth - xGap;
					offset[1] += strokeWidth + 1;

					Path2D path = generatePath2D(offset, outline.getPathIterator(affine));

					g.setGradient(0, 0, 0, (int) offset[1], new Color(255, 245, 0), new Color(236, 156, 0));
					g.fillPath2D(path);

					pad += generateLetterWidth(str, frc) + 4;
				}

				return img;
			} else {
				System.out.println(message + " contains invalid characters: " + getInvalids(message));
				return null;
			}
		}

		public BufferedImage generateImage(String message) {
			try {
				BufferedImage img = generateBufferedImage(message);

				if(img == null)
					return null;

				BufferedImage real = new BufferedImage(256, 64, BufferedImage.TYPE_INT_ARGB_PRE);
				FG2D g = new FG2D(real.getGraphics());

				g.setRenderingHint(3, 1);
				g.enableAntialiasing();
				float ratio = 42.0f / img.getHeight();

				BufferedImage scaled = new BufferedImage((int) (img.getWidth() * ratio), 42, BufferedImage.TYPE_INT_ARGB_PRE);
				FG2D sg = new FG2D(scaled.getGraphics());

				sg.setRenderingHint(3, 1);
				sg.enableAntialiasing();

				sg.drawImage(MainBCU.builder.build(img), 0, 0, scaled.getWidth(), scaled.getHeight());

				if(scaled.getWidth() > 228)
					ratio = 228.0f / scaled.getWidth();
				else
					ratio = 1.0f;

				g.drawImage(MainBCU.builder.build(scaled), 3, 2, scaled.getWidth() * ratio, scaled.getHeight());
				return real;
			} catch (Exception e) {
				System.out.println("Failed to generate display name for " + message);
				e.printStackTrace();
			}
			return null;
		}

		public ArrayList<String> getInvalids(String message) {
			ArrayList<String> res = new ArrayList<>();

			for(int i = 0; i < message.length(); i++) {
				String str = Character.toString(message.charAt(i));

				if(str.equals(" "))
					continue;

				if(!font.canDisplay(message.charAt(i)))
					res.add(str);
			}

			return res;
		}

		private boolean valid(String message) {
			for(int i = 0; i < message.length(); i++) {
				if(message.charAt(i) == ' ')
					continue;

				if(!font.canDisplay(message.charAt(i)))
					return false;
			}

			return true;
		}

		private float[] getAscendDescend(PathIterator path) {
			float[] d = new float[6];

			float descend = 0;
			float ascend = 0;

			while(!path.isDone()) {
				path.currentSegment(d);

				descend = Math.min(d[1] * -1.0f, descend);
				ascend = Math.max(d[1] * -1.0f, ascend);

				if(!path.isDone())
					path.next();
			}

			return new float[] {ascend, descend};
		}

		private float generateWidth(String message, FontRenderContext frc) {
			float w = 0.0f;

			for(int i = 0; i < message.length(); i++) {
				String str = Character.toString(message.charAt(i));

				if(str.equals(" ")) {
					w += space;
					continue;
				}

				GlyphVector glyph = font.createGlyphVector(frc, str);

				w += glyph.getVisualBounds().getWidth() + 4;
			}

			return w - 4;
		}

		private float[] generateHeight(String message, FontRenderContext frc, AffineTransform aff) {
			GlyphVector glyph = font.createGlyphVector(frc, message);

			float[] res = new float[2];

			for(int i = 0; i < message.length(); i++) {
				Shape outline = glyph.getGlyphOutline(i);

				PathIterator path = outline.getPathIterator(aff);

				float[] result = getAscendDescend(path);

				res[0] = Math.max(res[0], result[0]);
				res[1] = Math.min(res[1], result[1]);
			}

			res[1] *= -1.0;

			return res;
		}

		private float[] decideOffset(float padding, float h, float base) {
			return new float[] {padding, h - base};
		}

		private double generateLetterWidth(String str, FontRenderContext frc) {
			GlyphVector glyph = font.createGlyphVector(frc, str);

			return glyph.getVisualBounds().getWidth();
		}

		private float getLeftPoint(PathIterator path) {
			float res = Float.MAX_VALUE;

			float[] d = new float[6];

			while(!path.isDone()) {
				path.currentSegment(d);

				res = Math.min(res, d[0]);

				if(!path.isDone())
					path.next();
			}

			return res;
		}

		private Path2D generatePath2D(float[] offset, PathIterator path) {
			Path2D path2D = new Path2D.Float();

			float[] d = new float[6];

			while(!path.isDone()) {
				switch (path.currentSegment(d)) {
					case PathIterator.SEG_MOVETO:
						path2D.moveTo(d[0] + offset[0], d[1] + offset[1]);
						break;
					case PathIterator.SEG_LINETO:
						path2D.lineTo(d[0] + offset[0], d[1] + offset[1]);
						break;
					case PathIterator.SEG_QUADTO:
						path2D.quadTo(d[0] + offset[0], d[1] + offset[1], d[2] + offset[0], d[3] + offset[1]);
						break;
					case PathIterator.SEG_CUBICTO:
						path2D.curveTo(d[0] + offset[0], d[1] + offset[1], d[2] + offset[0], d[3] + offset[1], d[4] + offset[0], d[5] + offset[1]);
						break;
					case PathIterator.SEG_CLOSE:
						path2D.closePath();
						break;
				}

				if(!path.isDone())
					path.next();
			}

			return path2D;
		}
	}

	interface OuterBox extends RetFunc {

		int getSpeed();

	}

	default void click(Point p, int button) {
		getPainter().click(p, button);
	}

	default void drag(Point p) {
		getPainter().drag(p);
	}

	int getHeight();

	BBPainter getPainter();

	int getWidth();

	void paint();

	default void press(Point p) {
		getPainter().press(p);
	}

	default void release() {
		getPainter().release();
	}

	void reset();

	default void wheeled(Point p, int ind) {
		getPainter().wheeled(p, ind);
	}

	void releaseData();
}
