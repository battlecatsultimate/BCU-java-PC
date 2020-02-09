package page.info;

import static common.util.stage.SCDef.B;
import static common.util.stage.SCDef.C0;
import static common.util.stage.SCDef.C1;
import static common.util.stage.SCDef.E;
import static common.util.stage.SCDef.G;
import static common.util.stage.SCDef.L0;
import static common.util.stage.SCDef.L1;
import static common.util.stage.SCDef.M;
import static common.util.stage.SCDef.N;
import static common.util.stage.SCDef.R0;
import static common.util.stage.SCDef.R1;
import static common.util.stage.SCDef.S0;
import static common.util.stage.SCDef.S1;

import java.awt.Point;

import common.util.Data;
import common.util.stage.SCGroup;
import common.util.stage.Stage;
import common.util.unit.Enemy;
import common.util.unit.EnemyStore;
import page.MainFrame;
import page.Page;
import page.support.AbJTable;
import page.support.EnemyTCR;

public class StageTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	public static void redefine() {
		title = Page.get(1, "t", 9);
	}

	protected Object[][] data;

	private final Page page;

	protected StageTable(Page p) {
		page = p;

		setDefaultRenderer(Enemy.class, new EnemyTCR());
	}

	@Override
	public Class<?> getColumnClass(int c) {
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
	public int getRowCount() {
		if (data == null)
			return 0;
		return data.length;
	}

	@Override
	public Object getValueAt(int r, int c) {
		if (data == null || r < 0 || c < 0 || r >= data.length || c >= data[r].length)
			return null;
		if (c == 2)
			return data[r][c] + "%";
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
		MainFrame.changePanel(new EnemyInfoPage(page, e, (int) data[r][2]));
	}

	protected void setData(Stage st) {
		int[][] info = st.data.getSimple();
		data = new Object[info.length][9];
		for (int i = 0; i < info.length; i++) {
			int ind = info.length - i - 1;
			data[ind][1] = EnemyStore.getEnemy(info[i][E]);
			data[ind][0] = info[i][B] == 1 ? "boss" : "";
			data[ind][2] = info[i][M];
			data[ind][3] = info[i][N] == 0 ? "infinite" : info[i][N];
			if (info[i][C0] >= info[i][C1])
				data[ind][4] = info[i][C0] + "%";
			else
				data[ind][4] = info[i][C0] + "~" + info[i][C1] + "%";
			if (Math.abs(info[i][S0]) >= Math.abs(info[i][S1]))
				data[ind][5] = info[i][S0];
			else
				data[ind][5] = info[i][S0] + "~" + info[i][S1];

			if (info[i][R0] == info[i][R1])
				data[ind][6] = info[i][R0];
			else
				data[ind][6] = info[i][R0] + "~" + info[i][R1];

			data[ind][7] = info[i][L0] == info[i][L1] ? info[i][L0] : info[i][L0] + "~" + info[i][L1];
			int g = info[i][G];
			SCGroup scg = st.data.sub.get(g);
			data[ind][8] = scg == null ? g != 0 ? Data.trio(g) + " - invalid" : "" : scg.toString();

		}
	}

}