package page.info.edit;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.text.JTextComponent;

import io.Reader;
import page.MainFrame;
import page.Page;
import page.info.EnemyInfoPage;
import page.pack.EREditPage;
import page.support.AbJTable;
import page.support.EnemyTCR;
import page.support.InTableTH;
import page.support.Reorderable;
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

class StageEditTable extends AbJTable implements Reorderable {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	protected static void redefine() {
		title = Page.get(1, "t", 9);
	}

	private SCDef stage;
	private final Page page;
	private final Pack pack;

	protected StageEditTable(Page p, Pack pac) {
		page = p;
		pack = pac;
		setTransferHandler(new InTableTH(this));
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
		if (stage == null)
			return 0;
		return stage.datas.length;
	}

	@Override
	public synchronized Object getValueAt(int r, int c) {
		if (stage == null || r < 0 || c < 0 || r >= stage.datas.length || c > lnk.length)
			return null;
		return get(r, lnk[c]);
	}

	@Override
	public boolean isCellEditable(int r, int c) {
		return true;
	}

	@Override
	public synchronized void reorder(int ori, int fin) {
		if (fin > ori)
			fin--;
		if (fin == ori)
			return;
		int[][] info = stage.datas;
		int ior = info.length - ori - 1;
		int ifi = info.length - fin - 1;
		int[] temp = info[ior];
		if (ior < ifi)
			for (int i = ior; i < ifi; i++)
				info[i] = info[i + 1];
		else
			for (int i = ior; i > ifi; i--)
				info[i] = info[i - 1];
		info[ifi] = temp;
	}

	@Override
	public synchronized void setValueAt(Object arg0, int r, int c) {
		if (stage == null)
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
		if (stage == null)
			return -1;
		int ind = getSelectedRow();
		if (ind == -1)
			ind = 0;
		int[][] info = stage.datas;
		int len = info.length;
		int sind = len - ind - 1;
		int[][] ans = new int[len + 1][];
		if (sind >= 0) {
			for (int i = 0; i < sind; i++)
				ans[i] = info[i];
			for (int i = sind + 1; i < len + 1; i++)
				ans[i] = info[i - 1];
		} else
			sind = 0;
		if (enemy == null && sind < info.length && getSelectedRow() >= 0)
			ans[sind] = info[sind].clone();
		else {
			ans[sind] = new int[SCDef.SIZE];
			ans[sind][E] = enemy == null ? -1 : enemy.getID();
			ans[sind][N] = 1;
			ans[sind][C0] = 100;
			ans[sind][L1] = 9;
			ans[sind][M] = 100;
		}
		stage.datas = ans;
		ind++;
		if (ind >= ans.length)
			ind = ans.length - 1;
		for (int i = 0; i < ans.length; i++)
			if (ans[i] == null)
				ans[i] = new int[10];
		return ind;
	}

	protected synchronized void clicked(Point p) {
		if (stage == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		c = lnk[c];
		int r = p.y / getRowHeight();
		int[][] info = stage.datas;
		int len = info.length;
		if (r < 0 || r >= getRowCount() || c != 1)
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
		if (stage == null)
			return -1;
		int ind = getSelectedRow();
		int[][] info = stage.datas;
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
		stage.datas = ans;
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
		stage = st == null ? null : (SCDef) st.data;
		clearSelection();
	}

	private Object get(int r, int c) {
		int[][] info = stage.datas;
		int[] data = info[info.length - r - 1];
		if (data == null)
			return null;
		if (c == 0)
			return data[B] == 1 ? "boss" : "";
		else if (c == 1)
			return EnemyStore.getAbEnemy(data[E], true);
		else if (c == 2)
			return data[M] + "%";
		else if (c == 3)
			return data[N] == 0 ? "infinite" : data[N];
		else if (c == 4)
			return (data[C0] >= data[C1] ? data[C0] : data[C0] + "~" + data[C1]) + "%";
		else if (c == 5)
			return data[S0] >= data[S1] ? data[S0] : data[S0] + "~" + data[S1];
		else if (c == 6)
			return data[R0] == data[R1] ? data[R0] : data[R0] + "~" + data[R1];
		else if (c == 7)
			return data[L0] == data[L1] ? data[L0] : data[L0] + "~" + data[L1];
		else if(c==8){
			int g=data[G];
			SCGroup scg =stage.sub.get(g);
			return scg == null ? g != 0 ? Data.trio(g) + " - invalid" : "" : scg.toString();
		};
		return null;
	}

	private void set(int r, int c, int v, int para) {
		if (c != 5 && v < 0)
			v = 0;
		int[][] info = stage.datas;
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
		else if(c==8)
			data[G]=Math.max(0,v);
	}

}
