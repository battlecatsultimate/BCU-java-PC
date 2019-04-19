package page.basis;

import java.awt.Component;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import page.MainLocale;
import page.Page;
import page.support.SortTable;
import util.Interpret;
import util.basis.Combo;
import util.basis.LineUp;
import util.unit.Form;
import util.unit.UnitStore;

public class ComboListTable extends SortTable<Combo> {

	private static final long serialVersionUID = 1L;

	private static String[] tit;

	static {
		redefine();
	}

	public static void redefine() {
		String str = MainLocale.getLoc(1, "unit");
		tit = new String[] { "Lv.", MainLocale.getLoc(1, "desc"), MainLocale.getLoc(1, "occu"), str + " 1", str + " 2",
				str + " 3", str + " 4", str + " 5" };
	}

	private final LineUp lu;
	private final Page fr;

	protected ComboListTable(Page p, LineUp line) {
		fr = p;
		lu = line;

		setDefaultRenderer(Combo.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
				Combo com = (Combo) o;
				jl.setText(Interpret.comboInfo(com));
				return jl;
			}

		});

		setDefaultRenderer(Form.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
				Form form = (Form) o;
				jl.setText("");
				if (form == null) {
					jl.setIcon(null);
					return jl;
				}
				jl.setIcon(form.anim.uni.getIcon());
				return jl;
			}

		});
	}

	public void clicked(Point p) {
		if (list == null)
			return;
		int c = getColumnModel().getColumnIndexAtX(p.x);
		c = lnk[c];
		int r = p.y / getRowHeight();
		if (r < 0 || r >= list.size() || c < 3)
			return;
		Form f = ((Form) get(list.get(r), c));
		if (f == null)
			return;
		fr.callBack(f.unit);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		c = lnk[c];
		if (c == 1)
			return Combo.class;
		if (c > 2)
			return Form.class;
		return String.class;
	}

	@Override
	protected int compare(Combo e0, Combo e1, int c) {
		if (c == 2) {

			int o0 = lu.occupance(e0);
			int o1 = lu.occupance(e1);
			return o0 > o1 ? 1 : o0 < o1 ? -1 : 0;
		}
		return e0.lv > e1.lv ? 1 : e0.lv < e1.lv ? -1 : 0;
	}

	@Override
	protected Object get(Combo t, int c) {
		if (c == 0)
			return t.lv;
		if (c == 1)
			return t;
		if (c == 2)
			return lu.occupance(t);
		if (t.units.length > c - 3)
			return UnitStore.get(t.units[c - 3]);
		return null;
	}

	@Override
	protected String[] getTit() {
		return tit;
	}

}
