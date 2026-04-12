package page.info;

import common.util.stage.*;
import common.util.unit.Enemy;
import main.MainBCU;
import org.jetbrains.annotations.NotNull;
import page.MainFrame;
import page.MainLocale;
import page.Page;
import page.pack.CharaGroupPage;
import page.pack.LvRestrictPage;
import page.support.AbJTable;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.MusicPage;

import java.awt.*;
import java.awt.event.MouseEvent;

public class HeadTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static String[] infs, limits, rarity, climits;

	static {
		redefine();
	}

	public static void redefine() {
		infs = Page.get(MainLocale.INFO, "ht0", 6);
		limits = Page.get(MainLocale.INFO, "ht1", 7);
		rarity = new String[] { "N", "EX", "R", "SR", "UR", "LR" };
		climits = Page.get(MainLocale.INFO, "ht2", 2);
	}

	private Object[][] data;
	private Stage sta;
	private final Page page;

	protected HeadTable(Page p) {
		super(new String[8]);

		page = p;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}

	@Override
	public String getColumnName(int arg0) {
		return "";
	}

	@Override
	public int getRowCount() {
		if (data == null)
			return 0;
		return data.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		if (data == null || r < 0 || c < 0 || r >= data.length || c >= data[r].length)
			return null;
		return data[r][c];
	}

	protected synchronized void hover(Point p) {
		if (data == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		int r = p.y / getRowHeight();
		if (r == 0 && c > 1 && c < Math.min(sta.getCont().stars.length + 2, 6))
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 1 && c == 5 && sta.mus0 != null)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 1 && c == 7 && sta.mus1 != null)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 3 && c == 1)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 3 && c == 3)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 3 && c == 5)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 4 && c == 7 && data[r][c] instanceof LvRestrict)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else if (r == 3 && c == 7 && data[r][c] instanceof CharaGroup)
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		else
			setCursor(Cursor.getDefaultCursor());
	}

	protected void clicked(Point p) {
		if (data == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		int r = p.y / getRowHeight();
		if (r == 0 && c > 1 && c < Math.min(sta.getCont().stars.length + 2, 6))
			page.callBack(c - 2);
		if (r == 1 && c == 5 && sta.mus0 != null)
			MainFrame.changePanel(new MusicPage(page, sta.mus0));
		if (r == 1 && c == 7 && sta.mus1 != null)
			MainFrame.changePanel(new MusicPage(page, sta.mus1));
		if (r == 3 && c == 1)
			MainFrame.changePanel(new BGViewPage(page, null, sta.bg));
		if (r == 3 && c == 3)
			MainFrame.changePanel(new BGViewPage(page, null, sta.bg1));
		if (r == 3 && c == 5)
			MainFrame.changePanel(new CastleViewPage(page, CastleList.from(sta), sta.castle));
		if (r == 4 && c == 7 && data[r][c] instanceof LvRestrict)
			MainFrame.changePanel(new LvRestrictPage(page, (LvRestrict) data[r][c]));
		if (r == 3 && c == 7 && data[r][c] instanceof CharaGroup) // todo: fix so charagroup doesn't replace global cooldown
			MainFrame.changePanel(new CharaGroupPage(page, (CharaGroup) data[r][c]));
	}

	protected void setData(Stage st, int star) {
		sta = st;
		Object[][] lstr = new Object[6][8];
		Object[] tit, bas, bas2, img, rar, reg;
		tit = lstr[0];
		bas = lstr[1];
		bas2 = lstr[2];
		img = lstr[3];
		rar = lstr[4];
		reg = lstr[5];
		tit[0] = "ID:";
		tit[1] = st.getCont().id + "-" + st.id();
		String starStr = Page.get(MainLocale.INFO, "star");
		for (int i = 0; i < st.getCont().stars.length; i++)
			tit[2 + i] = (i + 1) + starStr + ": " + st.getCont().stars[i] + "%";
		tit[6] = Page.get(MainLocale.INFO, "chcos");
		tit[7] = st.getCont().price + 1;
		if (st.timeLimit != 0) {
			bas[0] = Page.get(MainLocale.INFO, "time");
			bas[1] = st.timeLimit +" min";
		} else {
			bas[0] = infs[0];
			SCDef.Line[] lines = st.data.getSimple();
			if (st.getCont().getCont().getSID().equals("000003"))
				bas[1] = st.health * (star + 1);
			else if (lines.length != 0 && lines[lines.length - 1].castle_0 == 0 && lines[lines.length - 1].enemy != null && lines[lines.length - 1].enemy.cls == Enemy.class)
				bas[1] = ((Enemy) lines[lines.length - 1].enemy.get()).de.getHp() * lines[lines.length - 1].multiple / 100 * st.getCont().stars[star] / 100;
			else
				bas[1] = st.health;
		}
		bas[2] = infs[1] + ": " + st.len;
		bas[3] = infs[2] + ": " + st.max;
		bas[4] = Page.get(MainLocale.INFO, "mus") + ":";
		bas[5] = st.mus0;
		bas[6] = "<" + st.mush + "%:";
		bas[7] = st.mus1;

		bas2[0] = Page.get(MainLocale.INFO, "minspawn");
		if(st.minSpawn == st.maxSpawn)
			bas2[1] = st.minSpawn + "f";
		else
			bas2[1] = st.minSpawn + "f ~ " + st.maxSpawn + "f";
		bas2[2] = MainLocale.getLoc(MainLocale.INFO, "ht03");
		bas2[3] = !st.non_con;
		bas2[4] = Page.get(MainLocale.INFO, "bossguard");
		bas2[5] = st.bossGuard;

		img[0] = infs[4];
		img[1] = st.bg;
		img[2] = "<" + st.bgh + "%";
		img[3] = st.bg1;
		img[4] = infs[5];
		img[5] = st.castle;

		Limit lim = st.getLim(star);
		if (lim.stageLimit != null) {
			if (lim.stageLimit.maxMoney > 0) {
				bas2[6] = climits[0];
				bas2[7] = lim.stageLimit.maxMoney;
			}
			if (lim.stageLimit.globalCooldown > 0) {
				img[6] = climits[1];
				img[7] = MainBCU.seconds
						? MainBCU.toSeconds(lim.stageLimit.globalCooldown) + "s"
						: lim.stageLimit.globalCooldown + "f";
			}
		}
        if (lim.rare != 0) {
            rar[0] = limits[0];
            int j = 1;
            for (int i = 0; i < rarity.length; i++)
                if (((lim.rare >> i) & 1) > 0)
                    rar[j++] = rarity[i];
        }
        if (lim.lvr != null) {
            rar[6] = limits[6];
            rar[7] = lim.lvr;
        }
        if (lim.group != null) {
            img[6] = limits[5];
            img[7] = lim.group;
        }
        if (lim.min + lim.max + lim.max + lim.line + lim.num > 0) {
            int i = 0;
            if (lim.min > 0) {
                reg[0] = limits[3];
                reg[1] = String.valueOf(lim.min);
                i = 2;
            }
            if (lim.max > 0) {
                reg[i] = limits[4];
                reg[i + 1] = String.valueOf(lim.max);
                i += 2;
            }
            if (lim.num > 0) {
                reg[i] = limits[1];
                reg[i + 1] = String.valueOf(lim.num);
                i += 2;
            }
            if (lim.line > 0)
                reg[i] = limits[2];
        }
        data = lstr;
	}

	@Override
	public String getToolTipText(@NotNull MouseEvent e) {
		java.awt.Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		Object val = getValueAt(rowIndex, colIndex);

		return val == null ? null : val.toString();
	}
}