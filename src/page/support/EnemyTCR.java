package page.support;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import common.system.VImg;
import common.util.unit.AbEnemy;
import utilpc.UtilPC;

public class EnemyTCR extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public EnemyTCR() {
	}

	@Override
	public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
		Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
		if (v != null && !(v instanceof AbEnemy))
			return comp;
		JLabel jl = (JLabel) comp;
		AbEnemy e = (AbEnemy) v;
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);
		jl.setIcon(null);
		if (e == null) {
			jl.setText("");
			return jl;
		}
		jl.setText(e.toString());
		VImg vimg = e.getIcon();
		if (vimg == null)
			return jl;
		jl.setIcon(UtilPC.getIcon(vimg));
		return jl;
	}

}
