
package page.basis;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.LineUp;
import common.battle.data.Orb;
import common.system.P;
import common.system.SymCoord;
import common.system.VImg;
import common.system.fake.FakeGraphics;
import common.system.fake.FakeImage;
import common.util.Data;
import common.util.Res;
import common.util.stage.Limit;
import common.util.stage.StageLimit;
import common.util.unit.*;
import page.Page;
import utilpc.PP;
import utilpc.awt.FG2D;

import java.awt.*;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class LineUpBox extends Canvas {

	private static final long serialVersionUID = 1L;

	private static final float ORB_SIZE_MULTIPLIER = 1.2f;

	private Form[] backup = new Form[5];
	private final Page page;
	protected BasisLU blu;
	private int pt = 0, time = 0;
	private Combo sc;
	private PP relative, mouse;
	protected boolean swap = false;

	protected Limit lim;
	protected int price = 1;

	protected Form sf;

	public LineUpBox(Page p) {
		page = p;
		setIgnoreRepaint(true);
	}

	@Override
	public void paint(Graphics g) {
		boolean hasLimit = lim != null && lim.stageLimit != null;
		VImg[] slot = CommonStatic.getBCAssets().slot;
		Image bimg = createImage(600, 300);
		if (bimg == null)
			return;
		FakeGraphics gra = new FG2D(bimg.getGraphics());
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 5; j++) {
				Form f = getForm(i, j);
				VImg img;
				if (f == null)
					img = slot[0];
				else
					img = f.anim.getUni();
				int baseX = 120 * j;
				if (sf == null || sf != f || relative == null)
					gra.drawImage(img.getImg(), baseX, 100 * i);
				if (f == null)
					continue;
				if (time == 0 && sc != null)
					for (Form fc : sc.forms)
						if (f.unit == fc.unit && f.fid >= fc.fid)
							gra.drawImage(slot[2].getImg(), baseX, 100 * i);
				if (sf != null && f.unit == sf.unit && relative == null)
					if(time == 1)
						gra.drawImage(slot[1].getImg(), baseX, 100 * i);
					else
						gra.drawImage(slot[2].getImg(), baseX, 100 * i);
				if (sf == null || sf != f || relative == null) {
					EForm ef = i != 2 ? blu.lu.efs[i][j] : new EForm(f, blu.lu.getLv(f));
					if (lim != null && ((lim.line == 1 && i == 1) || lim.unusable(ef.du, price))) {
						gra.colRect(baseX, 100 * i, img.getImg().getWidth(), img.getImg().getHeight(), 255, 0, 0, 100);
						Res.getCost(-1, false,
							new SymCoord(gra, 1, baseX, 100 * i + img.getImg().getHeight(), 2));
					} else {
						int cost = lim != null && hasLimit && lim.stageLimit.globalCost > -1 ? lim.stageLimit.globalCost : (int) ef.getPrice(price);
						if (hasLimit)
							cost = cost * lim.stageLimit.costMultiplier[f.unit.rarity] / 100;
						if (!StageLimit.isComboBanned(lim, Data.C_DISCOUNT))
							cost -= cost * blu.getInc(Data.C_DISCOUNT, f.unit) / 100;

						int lv = blu.lu.getLv(f).getLv() + blu.lu.getLv(f).getPlusLv();
						Res.getCost(cost, true,
								new SymCoord(gra, 1, 120 * j, 100 * i + img.getImg().getHeight(), 2));
						Res.getLv(lv,
								new SymCoord(gra, 0.8f, 120 * j, 100 * i + (img.getImg().getHeight() / 3.5f), 2));
						float orbX = 85f;
						int[][] orbs = ef.getLevel().getOrbs();
						if (orbs != null)
							for (int orbId = orbs.length - 1; orbId > -1; orbId--) {
								int[] orb = orbs[orbId];
								if (orb.length == 0)
									continue;
								FakeImage orbBall = CommonStatic.getBCAssets().TRAITS[1][Orb.reverse(orb[1])];
								FakeImage orbIcon = CommonStatic.getBCAssets().TYPES[1][orb[0]];
								float ballW = orbBall.getWidth() * ORB_SIZE_MULTIPLIER;
								float iconW = orbIcon.getWidth() * ORB_SIZE_MULTIPLIER;
								float ballH = orbBall.getHeight() * ORB_SIZE_MULTIPLIER;
								float iconH = orbIcon.getHeight() * ORB_SIZE_MULTIPLIER;
								float x = baseX - 4f + orbX;
								float y = 100 * i + 10f - (ballH / 3f);
								gra.drawImage(orbBall, x, y, ballW, ballH);
								gra.drawImage(orbIcon, x + (ballW - iconW) / 2f, y + (ballH - iconH) / 2f, iconW, iconH);
								if (f.unit.orbs.get(orbId).isRestricted(f.fid, lv) || (hasLimit && lim.stageLimit.bannedOrb.contains(orb[0]))) {
									gra.setColor(FakeGraphics.RED);
									gra.setComposite(FakeGraphics.TRANS, 100, 0);
									gra.fillOval(x, y, ballW, ballH);
									gra.setComposite(FakeGraphics.DEF, 0, 0);
								}
								orbX -= ballW;
							}
						}
				}
			}
		if (relative != null && sf != null) {
			Point p = relative.sf(mouse).toPoint();
			FakeImage uni = sf.anim.getUni().getImg();
			gra.drawImage(uni, p.x, p.y);
			EForm ef = new EForm(sf, blu.lu.getLv(sf));
			if (lim != null && lim.unusable(ef.du, price)) {
				gra.colRect(p.x, p.y, uni.getWidth(), uni.getHeight(), 255, 0, 0, 100);
				Res.getCost(-1, true, new SymCoord(gra, 1, p.x, p.y + uni.getHeight(), 2));
			}
			Res.getCost((int) ef.getPrice(price), true,
					new SymCoord(gra, 1, p.x, p.y + uni.getHeight(), 2));
			Res.getLv(blu.lu.getLv(sf).getLv() + blu.lu.getLv(sf).getPlusLv(),
					new SymCoord(gra, 0.8f, p.x, p.y + (uni.getHeight() / 3.5f), 2));
			Res.getRarity(sf.unit.rarity,
					new SymCoord(gra, 0.9f, p.x + 50, p.y + (uni.getHeight() / 3.5f), 2));

		}
		g.drawImage(bimg, 0, 0, getWidth(), getHeight(), null);
		pt++;
		if (pt == 5)
			time = 1 - time;
		pt %= 5;
	}

	public void setLU(BasisLU l) {
		blu = l;
		backup = new Form[5];
	}

	public void setLimit(Limit l, int price) {
		lim = l;
		this.price = price;
		paint(getGraphics());
	}

	protected void adjForm() {
		if (sf == null || getPos(sf) == -1)
			return;
		int i = getPos(sf);
		if (getForm(i).unit == sf.unit) {
			Form[] ufs = sf.unit.forms;
			sf = ufs[(getForm(i).fid + 1) % ufs.length];
			setForm(i, sf);
			blu.lu.renew();
			page.callBack(null);
		}

	}

	protected void click(Point p) {
		p = getPos(p);
		select(getForm(p.y, p.x));
	}

	protected void drag(Point p) {
		if (relative == null || sf == null)
			return;
		mouse = new PP(p).divide(getScale());
		int ori = getPos(sf);
		Point pf = getPos(mouse);
		int fin = pf.x + pf.y * 5;
		if (ori != fin)
			jump(ori, fin);
	}

	protected void press(Point p) {
		click(p);
		PP ul = new PP(getPos(p)).times(new P(120, 100));
		relative = ul.sf(mouse = new PP(p).divide(getScale()));

	}

	protected void release() {
		relative = null;
		mouse = null;
	}

	protected void select(Combo c) {
		sc = c;
		time = 0;
		paint(getGraphics());
	}

	protected void select(Form f) {
		sf = f;
		if (f == null)
			return;
		if (getPos(f) == -1) {
			boolean b = false;
			for (int i = 0; i < 5; i++)
				if (backup[i] == null) {
					backup[i] = f;
					b = true;
					break;
				}
			if (!b)
				backup[4] = f;
		}
		time = 1;
		paint(getGraphics());
		page.callBack(f);
	}

	protected void setLv(Level lv) {
		if (sf == null)
			return;

		blu.lu.setLv(sf.unit, sf.regulateLv(lv, blu.lu.getLv(sf)));
	}

	protected void setPos(int pos) {
		if (sf == null || getPos(sf) == -1)
			return;
		int p = getPos(sf);
		jump(p, pos);
	}

	protected void updateLU() {
		Set<Unit> su = new TreeSet<>();
		for (int i = 0; i < 10; i++)
			if (getForm(i) != null)
				su.add(getForm(i).unit);
		for (int i = 0; i < 5; i++)
			if (backup[i] != null && su.contains(backup[i].unit))
				backup[i] = null;
	}

	protected void resetBackup() {
		Arrays.fill(backup, null);
	}

	private Form getForm(int pos) {
		return pos < 10 ? blu.lu.fs[pos / 5][pos % 5] : backup[pos % 5];
	}

	private Form getForm(int i, int j) {
		return i < 2 ? blu.lu.fs[i][j] : backup[j];
	}

	private int getPos(Form f) {
		if (f == null)
			return -1;
		for (int i = 0; i < 15; i++)
			if (getForm(i) != null && getForm(i).unit == f.unit)
				return i;
		return -1;
	}

	private Point getPos(Point p) {
		PP siz = new PP(getSize());
		PP ans = new PP(p).times(new P(5, 3)).divide(siz);
		ans.limit(new PP(4, 2));
		return ans.toPoint();
	}

	private Point getPos(PP p) {
		PP ans = p.copy().times(new P(5, 3)).divide(new P(600, 300));
		ans.limit(new P(4, 2));
		return ans.toPoint();
	}

	private P getScale() {
		return new PP(getSize()).divide(new P(600, 300));
	}

	private void jump(int origin, int dest) {
		Form f = getForm(origin);
		if (origin > dest)
			for (int i = dest; i <= origin; i++)
				f = setForm(i, f);
		else {
			if (dest > 9) {
				f = setForm(dest, f);
				if (origin > 9)
					setForm(origin, f);
				else
					for (int i = 0; i < 5; i++)
						if (backup[i] == null) {
							setForm(10 + i, f);
							break;
						}
				dest = 9;
				f = null;
			}
			for (int i = dest; i >= origin; i--)
				f = setForm(i, f);
		}
		blu.lu.arrange();
		blu.lu.renew();
		page.callBack(null);
	}

	private Form setForm(int pos, Form f) {
		Form ans = getForm(pos);
		if (pos < 10)
			blu.lu.fs[pos / 5][pos % 5] = f;
		else
			backup[pos % 5] = f;
		return ans;
	}

	public LineUp getLU() {
		return blu == null ? null : blu.lu;
	}

	public Limit getLim() {
		return lim;
	}

}
