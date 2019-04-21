package page.info.edit;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.text.JTextComponent;

import io.Reader;
import page.MainFrame;
import page.MainLocale;
import page.Page;
import page.info.EnemyInfoPage;
import page.pack.EREditPage;
import page.support.AbJTable;
import page.support.EnemyTCR;
import util.Data;
import util.pack.Pack;
import util.stage.SCDef;
import util.stage.SCGroup;
import util.stage.Stage;
import util.unit.AbEnemy;
import util.unit.EneRand;
import util.unit.Enemy;
import util.unit.EnemyStore;
import static util.stage.SCDef.*;

class SCGroupEditTable extends AbJTable {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	protected static void redefine() {
		title = MainLocale.getLoc(1, "t1", "group");
	}

	private SCDef scd;
	private final Page page;
	private final Pack pack;

	protected SCGroupEditTable(Page p, Pack pac) {
		page = p;
		pack = pac;
		setDefaultRenderer(Integer.class, new EnemyTCR());
	}

	@Override
	public boolean editCellAt(int row, int column, EventObject e) {
		boolean result = super.editCellAt(row, column, e);
		Component editor = getEditorComponent();
		if (editor == null || !(editor instanceof JTextComponent))
			return result;
		if (e instanceof KeyEvent)
			((JTextComponent) editor).selectAll();
		return result;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return lnk[c] == 1 ? Integer.class : String.class;
	}

	@Override
	public int getColumnCount() {
		return title.length;
	}

	@Override
	public String getColumnName(int c) {
		return title[lnk[c]];
	}

	@Override
	public synchronized int getRowCount() {
		if (scd == null)
			return 0;
		return scd.datas.length;
	}

	@Override
	public synchronized Object getValueAt(int r, int c) {
		if (scd == null || r < 0 || c < 0 || r >= scd.datas.length || c > lnk.length)
			return null;
		return get(r, lnk[c]);
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return true;
	}

	@Override
	public synchronized void setValueAt(Object arg0, int r, int c) {
		if (scd == null)
			return;
		c = lnk[c];
		if (c > 3) {
			int[] is = Reader.parseIntsN((String) arg0);
			if (is.length == 0)
				return;
			if (is.length == 1)
				set(r, c, is[0], -1);
			else
				set(r, c, is[0], is[1]);
		} else if (c == 0) {
			int i = ((String) arg0).length();
			set(r, c, i > 0 ? 1 : 0, 0);

		} else {
			int i = arg0 instanceof Integer ? (Integer) arg0 : Reader.parseIntN((String) arg0);
			set(r, c, i, 0);
		}
	}

	protected synchronized int addLine(AbEnemy enemy) {
		if (scd == null)
			return -1;
		int ind = getSelectedRow();
		int eid = 0;
		if (enemy == null)
			while (scd.smap.containsKey(eid++))
				;
		else
			eid = enemy.getID();
		if (scd.smap.containsKey(eid))
			return ind;
		if (ind == -1)
			ind = 0;
		scd.smap.put(eid, 0);
		ind++;
		return ind;
	}

	protected synchronized void clicked(Point p) {
		if (scd == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		c = lnk[c];
		int r = p.y / getRowHeight();
		int[][] info = scd.datas;
		int len = info.length;
		if (r < 0 || r >= getRowCount() || c != 0)
			return;
		int ind = len - r - 1;
		if (info[ind] == null)
			return;
		AbEnemy e = EnemyStore.getAbEnemy(info[ind][0], true);
		if (e != null && e instanceof Enemy)
			MainFrame.changePanel(new EnemyInfoPage(page, (Enemy) e, info[ind][9]));
		if (e != null && e instanceof EneRand)
			MainFrame.changePanel(new EREditPage(page, pack, (EneRand) e));
	}

	protected synchronized int remLine() {
		if (scd == null)
			return -1;
		int ind = getSelectedRow();
		int[][] info = scd.datas;
		if (info.length == 0)
			return -1;
		if (ind == -1)
			ind = 0;
		int sind = info.length - ind - 1;
		int[][] ans = new int[info.length - 1][];
		for (int i = 0; i < ans.length; i++)
			if (i < sind)
				ans[i] = info[i];
			else
				ans[i] = info[i + 1];
		scd.datas = ans;
		if (ans.length > 0) {
			if (ind == 0)
				ind = 1;
			return ind - 1;
		}
		return -1;
	}

	protected synchronized void setData(Stage st) {
		if (cellEditor != null)
			cellEditor.stopCellEditing();
		scd = st == null ? null : (SCDef) st.data;
		clearSelection();
	}

	private Object get(int r, int c) {
		int[][] info = scd.datas;
		int[] data = info[info.length - r - 1];
		if (data == null)
			return null;
		if (c == 0)
			return EnemyStore.getAbEnemy(data[E], true);
		else if (c == 1) {
			SCGroup scg = scd.sub.get(data[G]);
			return scg == null ? data[G] != 0 ? Data.trio(data[G]) + " - invalid" : "" : scg.toString();
		}
		return null;
	}

	private void set(int r, int c, int v, int para) {
		if (c != 5 && v < 0)
			v = 0;
		int[][] info = scd.datas;
		int[] data = info[info.length - r - 1];
		if (c == 0)
			data[B] = v;
		else if (c == 1)
			data[E] = v;
		else if (c == 2)
			data[M] = v;
		else if (c == 3)
			data[N] = v;
		else if (c == 4)
			if (para == -1)
				data[C0] = data[C1] = v;
			else {
				data[C0] = Math.min(v, para);
				data[C1] = Math.max(v, para);
			}
		else if (c == 5)
			if (para == -1)
				data[S0] = data[S1] = v;
			else {
				data[S0] = Math.min(v, para);
				data[S1] = Math.max(v, para);
			}
		else if (c == 6)
			if (para == -1)
				data[R0] = data[R1] = v;
			else {
				data[R0] = Math.min(v, para);
				data[R1] = Math.max(v, para);
			}
		else if (c == 7)
			if (para == -1)
				data[L0] = data[L1] = v;
			else {
				data[L0] = Math.min(v, para);
				data[L1] = Math.max(v, para);
			}
	}

}
