package page.basis;

import common.battle.BasisSet;
import common.util.stage.CharaGroup;
import common.util.unit.Combo;
import common.util.unit.Form;
import page.MainLocale;
import page.Page;
import page.support.SortTable;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class ComboListTable extends SortTable<Combo> {

	private static final long serialVersionUID = 1L;

	private static String[] tit;

	static {
		redefine();
	}

	public static void redefine() {
		String str = MainLocale.getLoc(MainLocale.INFO, "unit");
		tit = new String[] { "name", "Lv.", MainLocale.getLoc(MainLocale.INFO, "desc"),
				MainLocale.getLoc(MainLocale.INFO, "group"), str + " 1", str + " 2", str + " 3", str + " 4",
				str + " 5" };
	}

	private final Page fr;

	public ComboListTable(Page p) {
		super(tit);

		fr = p;

		setDefaultRenderer(CharaGroup.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
				CharaGroup group = (CharaGroup) o;
				if (group != null) {
					jl.setText(group.name.isEmpty() ? group.id.toString() : group.name + " - " + group.id);
					jl.setToolTipText(Interpret.getGroupTooltip(group));
				} else {
					jl.setText("");
					jl.setToolTipText(null);
				}
				return jl;
			}

		});

		setDefaultRenderer(Combo.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable l, Object o, boolean s, boolean f, int r, int c) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(l, c, s, f, r, c);
				Combo com = (Combo) o;
				jl.setText(com != null ? Interpret.comboInfo(com, BasisSet.current(), false) : "?");
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
				ImageIcon icon = UtilPC.getIcon(form.anim.getUni());
				if (icon != null) {
					Image img = icon.getImage().getScaledInstance(60, 45, java.awt.Image.SCALE_SMOOTH);
					jl.setIcon(new ImageIcon(img));
					jl.setHorizontalAlignment(SwingConstants.CENTER);
					jl.setVerticalAlignment(SwingConstants.CENTER);
				} else {
					jl.setIcon(null);
				}
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
		if (r < 0 || r >= list.size() || c <= 3)
			return;
		Form f = ((Form) get(list.get(r), c));
		if (f == null)
			return;
		fr.callBack(f.unit);
	}

	@Override
	public Class<?> getColumnClass(int c) {
		c = lnk[c];
		if (c == 2)
			return Combo.class;
		else if (c == 3)
			return CharaGroup.class;
		else if (c > 3)
			return Form.class;
		else
			return String.class;
	}

	@Override
	protected int compare(Combo e0, Combo e1, int c) {
		if (c == 0) {
			return e0.getID().compareTo(e1.getID());
		} else if (c == 2) {
			return Integer.compare(e0.type, e1.type);
		} else if (c == 3) {
			boolean b0 = e0.group == null;
			boolean b1 = e1.group == null;
			if (b0 && b1)
				return 0;
			else if (b0)
				return -1;
			else if (b1)
				return 1;
			else
				return e0.group.id.compareTo(e1.group.id);
		} else if (c >= 4 && c <= 8) {
			boolean b0 = e0.forms.length <= c - 3;
			boolean b1 = e1.forms.length <= c - 3;
			if (b0 && b1)
				return 0;
			else if (b0)
				return -1;
			else if (b1)
				return 1;
			Form f0 = e0.forms[c - 3];
			Form f1 = e1.forms[c - 3];
			int val = f0.uid.compareTo(f1.uid);
			return val != 0 ? val : Integer.compare(f0.fid, f1.fid);
		} else {
			return Integer.compare(e0.lv, e1.lv);
		}
	}

	@Override
	protected Object get(Combo t, int c) {
		if (c == 0)
			return t.toString();
		if (c == 1)
			return Interpret.lvl[t.lv];
		if (c == 2)
			return t;
		if (c == 3)
			return t.group;
		if (t.forms.length > c - 4) {
			return t.forms[c - 4];
		}
		return null;
	}

	public void refresh() {
		revalidate();
		repaint();
	}

	protected void setPreferredWidth(int x, int y) {
		getColumnModel().getColumn(0).setPreferredWidth(Math.min(200 * x / 2300, 200 * y / 1300));
		getColumnModel().getColumn(2).setPreferredWidth(Math.min(300 * x / 2300, 300 * y / 1300));
	}
}
