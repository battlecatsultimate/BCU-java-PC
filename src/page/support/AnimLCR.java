package page.support;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import common.system.VImg;
import common.util.Animable;
import common.util.anim.AnimU;
import common.util.unit.AbEnemy;
import utilpc.UtilPC;

public class AnimLCR extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
		JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
		jl.setText(o.toString());
		jl.setIcon(null);
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);
		VImg v;
		if (o instanceof Animable<?>)
			v = ((Animable<? extends AnimU>) o).anim.edi;
		else if (o instanceof AbEnemy)
			v = ((AbEnemy) o).getIcon();
		else
			v = null;

		if (v == null)
			return jl;
		jl.setIcon(UtilPC.getIcon(v));
		return jl;
	}

}