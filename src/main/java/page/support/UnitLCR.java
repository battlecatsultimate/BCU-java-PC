package page.support;

import common.system.VImg;
import common.util.unit.Form;
import common.util.unit.Unit;
import main.MainBCU;
import utilpc.Theme;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;

public class UnitLCR extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
		Form form;
		if (o instanceof Unit)
			form = ((Unit) o).forms[0];
		else
			form = (Form) o;
		JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
		jl.setText(o.toString());
		jl.setIcon(null);
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);

		if(form.anim == null) {
			jl.setEnabled(false);
			jl.setText(o + " (Error - Corrupted)");
			return jl;
		}

		VImg v = form.anim.getEdi();
		if (v == null)
			return jl;
		jl.setIcon(UtilPC.getIcon(v));
		if (s && MainBCU.nimbus) {
			jl.setBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);
		}
		return jl;
	}
}
