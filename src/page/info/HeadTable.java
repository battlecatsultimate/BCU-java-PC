package page.info;

import java.awt.Point;
import java.text.DecimalFormat;

import common.util.stage.Castles;
import common.util.stage.CharaGroup;
import common.util.stage.Limit;
import common.util.stage.LvRestrict;
import common.util.stage.Stage;
import page.MainFrame;
import page.MainLocale;
import page.Page;
import page.pack.CharaGroupPage;
import page.pack.LvRestrictPage;
import page.support.AbJTable;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.MusicPage;

public class HeadTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static String[] infs, limits, rarity;

	static {
		redefine();
	}

	public static void redefine() {
		infs = Page.get(1, "ht0", 6);
		limits = Page.get(1, "ht1", 7);
		rarity = new String[] { "N", "EX", "R", "SR", "UR", "LR" };
	}

	private Object[][] data;
	private Stage sta;
	private final Page page;

	protected HeadTable(Page p) {
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

	protected void clicked(Point p) {
		if (data == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		int r = p.y / getRowHeight();
		if (r == 1 && c == 5)
			MainFrame.changePanel(new MusicPage(page, sta.mus0));
		if (r == 1 && c == 7)
			MainFrame.changePanel(new MusicPage(page, sta.mus1));
		if (r == 2 && c == 1)
			MainFrame.changePanel(new BGViewPage(page, null, sta.bg));
		if (r == 2 && c == 3)
			MainFrame.changePanel(new CastleViewPage(page, Castles.map.values(), sta.getCastle()));
		if (r == 2 && c == 5 && data[r][c] != null && data[r][c] instanceof LvRestrict)
			MainFrame.changePanel(new LvRestrictPage(page, (LvRestrict) data[r][c]));
		if (r == 2 && c == 7 && data[r][c] != null)
			MainFrame.changePanel(new CharaGroupPage(page, (CharaGroup) data[r][c]));
	}

	protected void setData(Stage st) {
		sta = st;
		Object[][] lstr = new Object[5][8];
		Object[] tit, bas, img, rar, reg;
		tit = lstr[0];
		bas = lstr[1];
		img = lstr[2];
		rar = lstr[3];
		reg = lstr[4];
		tit[0] = "ID:";
		tit[1] = st.map.mc.name + "-" + st.map.id + "-" + st.id();
		String star = Page.get(1, "star");
		for (int i = 0; i < st.map.stars.length; i++)
			tit[2 + i] = (i + 1) + star + ": " + st.map.stars[i] + "%";
		bas[0] = infs[0];
		bas[1] = st.health;
		bas[2] = infs[1] + ": " + st.len;
		bas[3] = infs[2] + ": " + st.max;
		bas[4] = Page.get(1, "mus") + ":";
		bas[5] = st.mus0;
		bas[6] = "<" + st.mush + "%:";
		bas[7] = st.mus1;
		img[0] = infs[4];
		img[1] = st.bg;
		img[2] = infs[5];
		img[3] = st.getCastle();
		img[4] = MainLocale.getLoc(1, "lop");
		img[5] = convertTime(st.loop0);
		img[6] = MainLocale.getLoc(1, "lop1");
		img[7] = convertTime(st.loop1);
		Limit lim = st.getLim(0);
		if (lim != null) {
			if (lim.rare != 0) {
				rar[0] = limits[0];
				int j = 1;
				for (int i = 0; i < rarity.length; i++)
					if (((lim.rare >> i) & 1) > 0)
						rar[j++] = rarity[i];
			}
			if (lim.lvr != null) {
				img[4] = limits[6];
				img[5] = lim.lvr;
			}
			if (lim.group != null) {
				img[6] = limits[5];
				img[7] = lim.group;
			}
			if (lim.min + lim.max + lim.max + lim.line + lim.num > 0) {
				int i = 0;
				if (lim.min > 0) {
					reg[0] = limits[3];
					reg[1] = "" + lim.min;
					i = 2;
				}
				if (lim.max > 0) {
					reg[i] = limits[4];
					reg[i + 1] = "" + lim.max;
					i += 2;
				}
				if (lim.num > 0) {
					reg[i] = limits[1];
					reg[i + 1] = "" + lim.num;
					i += 2;
				}
				if (lim.line > 0)
					reg[i] = limits[2];
			}
		}
		data = lstr;
	}

	private String convertTime(long milli) {
		long min = milli / 60 / 1000;

		double time = milli - (double) min * 60000;

		time /= 1000;

		DecimalFormat df = new DecimalFormat("#.###");

		double s = Double.parseDouble(df.format(time));

		if (s >= 60) {
			s -= 60;
			min += 1;
		}

		if (s < 10) {
			return min + ":" + "0" + df.format(s);
		} else {
			return min + ":" + df.format(s);
		}
	}

}