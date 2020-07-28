package page.battle;

import static common.util.stage.SCDef.C0;
import static common.util.stage.SCDef.C1;

import java.awt.Point;

import common.CommonStatic;
import common.util.stage.EStage;
import common.util.stage.SCDef;
import common.util.stage.Stage;
import common.util.unit.Enemy;
import common.util.unit.EnemyStore;
import page.MainFrame;
import page.Page;
import page.info.EnemyInfoPage;
import page.support.AbJTable;
import page.support.EnemyTCR;

class ComingTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	protected static void redefine() {
		title = Page.get(1, "c", 6);
	}

	private Object[][] data;
	private int[] link;

	private final Page page;

	protected ComingTable(Page p) {
		page = p;
		setDefaultRenderer(Enemy.class, new EnemyTCR());
	}

	@Override
	public Class<?> getColumnClass(int c) {
		c = lnk[c];
		if (c == 1)
			return Enemy.class;
		else
			return Object.class;
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public String getColumnName(int arg0) {
		return title[arg0];
	}

	@Override
	public synchronized int getRowCount() {
		if (data == null)
			return 0;
		return data.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		c = lnk[c];
		if (data == null || r < 0 || c < 0 || r >= data.length || c >= data[r].length)
			return null;
		if (c == 2) {
			return data[r][c] + "%";
		}
		if (data[r][c] == null)
			return "";
		else
			return data[r][c];
	}

	protected void clicked(Point p) {
		if (data == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		c = lnk[c];
		int r = p.y / getRowHeight();
		if (r < 0 || r >= data.length || c != 1)
			return;
		Enemy e = (Enemy) data[r][c];
		int[] d = CommonStatic.parseIntsN((String) data[r][2]);
		MainFrame.changePanel(new EnemyInfoPage(page, e, d[0], d[1]));

	}

	protected void setData(Stage st) {
		int[][] info = st.data.getSimple();
		data = new Object[info.length][6];
		link = new int[info.length];
		for (int i = 0; i < info.length; i++) {
			int ind = info.length - i - 1;
			link[i] = ind;
			data[link[i]][1] = EnemyStore.getAbEnemy(info[i][0], false);
			data[link[i]][0] = info[i][8] == 1 ? "boss" : "";
			data[link[i]][2] = CommonStatic.toArrayFormat(info[i][9], info[i][SCDef.M1]);
			data[link[i]][3] = info[i][1] == 0 ? "infinite" : info[i][1];
			if (info[i][C0] >= info[i][C1])
				data[link[i]][4] = info[i][C0] + "%";
			else
				data[link[i]][4] = info[i][C0] + "~" + info[i][C1] + "%";
		}
	}

	protected synchronized void update(EStage est) {
		for (int i = 0; i < link.length; i++)
			if (link[i] != -1) {
				data[link[i]][5] = est.rem[i];
				data[link[i]][3] = est.num[i] == 0 ? "infinite" : est.num[i];
				if (est.num[i] == -1)
					data[link[i]] = null;
			}
		int sum = 0;
		for (int i = link.length - 1; i >= 0; i--) {
			if (link[i] == -1)
				continue;
			Object[] dat = data[link[i]];
			link[i] -= sum;
			data[link[i]] = dat;
			if (data[link[i]] == null) {
				sum++;
				link[i] = -1;
			}
		}
		Object[][] tem = new Object[data.length - sum][];
		for (int i = 0; i < tem.length; i++)
			tem[i] = data[i];
		data = tem;
	}

}