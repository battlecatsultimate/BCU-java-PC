package page.pack;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.text.JTextComponent;

import common.CommonStatic;
import common.util.EREnt;
import common.util.unit.AbEnemy;
import common.util.unit.EneRand;
import common.util.unit.Enemy;
import common.util.unit.EnemyStore;
import page.MainFrame;
import page.Page;
import page.info.EnemyInfoPage;
import page.support.AbJTable;
import page.support.EnemyTCR;
import page.support.InTableTH;
import page.support.Reorderable;

class EREditTable extends AbJTable implements Reorderable {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	protected static void redefine() {
		title = Page.get(1, "er", 3);
	}

	private EneRand rand;
	private Page page;

	protected EREditTable(Page p) {
		page = p;
		setTransferHandler(new InTableTH(this));
		setDefaultRenderer(Integer.class, new EnemyTCR());
	}

	@Override
	public boolean editCellAt(int r, int c, EventObject e) {
		boolean result = super.editCellAt(r, c, e);
		Component editor = getEditorComponent();
		if (editor == null || !(editor instanceof JTextComponent))
			return result;
		JTextComponent jtf = ((JTextComponent) editor);
		if (e instanceof KeyEvent)
			jtf.selectAll();
		if (lnk[c] == 0 && jtf.getText().length() > 0)
			jtf.setText(((AbEnemy) get(r, c)).getID() + "");
		return result;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		return lnk[c] == 0 ? Integer.class : String.class;
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
		if (rand == null)
			return 0;
		return rand.list.size();
	}

	@Override
	public synchronized Object getValueAt(int r, int c) {
		if (rand == null || r < 0 || c < 0 || r >= rand.list.size() || c > lnk.length)
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
		rand.list.add(fin, rand.list.remove(ori));
	}

	@Override
	public synchronized void setValueAt(Object arg0, int r, int c) {
		if (rand == null)
			return;
		c = lnk[c];
		if (c > 0) {
			int[] is = CommonStatic.parseIntsN((String) arg0);
			if (is.length == 0)
				return;
			if (is.length == 1)
				set(r, c, is[0], -1);
			else
				set(r, c, is[0], is[1]);
		} else {
			int i = arg0 instanceof Integer ? (Integer) arg0 : CommonStatic.parseIntN((String) arg0);
			set(r, c, i, 0);
		}
	}

	protected synchronized int addLine(AbEnemy enemy) {
		if (rand == null)
			return -1;
		int ind = getSelectedRow();
		if (ind == -1)
			ind = 0;
		EREnt<Integer> er = new EREnt<>();
		rand.list.add(er);
		er.ent = enemy == null ? -1 : enemy.getID();
		return rand.list.size() - 1;
	}

	protected synchronized void clicked(Point p) {
		if (rand == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		c = lnk[c];
		if (c != 0)
			return;
		int r = p.y / getRowHeight();
		EREnt<Integer> er = rand.list.get(r);
		AbEnemy e = EnemyStore.getAbEnemy(er.ent, true);
		if (e != null && e instanceof Enemy)
			MainFrame.changePanel(new EnemyInfoPage(page, (Enemy) e, er.multi, er.mula));
	}

	protected synchronized int remLine() {
		if (rand == null)
			return -1;
		int ind = getSelectedRow();
		if (ind >= 0)
			rand.list.remove(ind);
		if (rand.list.size() > 0) {
			if (ind == 0)
				ind = 1;
			return ind - 1;
		}
		return -1;
	}

	protected synchronized void setData(EneRand st) {
		if (cellEditor != null)
			cellEditor.stopCellEditing();
		rand = st;
		clearSelection();
	}

	private Object get(int r, int c) {
		if (rand == null)
			return null;
		EREnt<Integer> er = rand.list.get(r);
		if (c == 0)
			return EnemyStore.getAbEnemy(er.ent, true);
		else if (c == 1)
			return er.multi + "%";
		else if (c == 2)
			return er.share;
		return null;
	}

	private void set(int r, int c, int v, int para) {
		if (rand == null)
			return;
		EREnt<Integer> er = rand.list.get(r);
		if (v < 0)
			v = 0;
		if (c == 0)
			er.ent = v;
		else if (c == 1)
			er.multi = v;
		else if (c == 2)
			er.share = v;
	}

}
