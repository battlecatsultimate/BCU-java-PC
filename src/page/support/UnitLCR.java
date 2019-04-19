package page.support;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import util.system.VImg;
import util.unit.Form;
import util.unit.Unit;

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
		jl.setText(form.toString());
		jl.setIcon(null);
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);
		VImg v = form.anim.edi;
		if (v == null)
			return jl;
		jl.setIcon(v.getIcon());
		return jl;
	}
}
