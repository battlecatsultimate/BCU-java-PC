package page.support;

import common.system.VImg;
import common.util.Animable;
import common.util.anim.AnimU;
import common.util.unit.AbEnemy;
import main.MainBCU;
import utilpc.Theme;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;

public class AnimLCR extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
		JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
		jl.setText(o.toString());
		jl.setIcon(null);
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);

		if (s && MainBCU.nimbus) {
			jl.setBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);
		}

		VImg v;
		if (o instanceof Animable<?, ?>)
			v = ((Animable<? extends AnimU<?>, ?>) o).anim == null ? null : ((Animable<? extends  AnimU<?>, ?>) o).anim.getEdi();
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