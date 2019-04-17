package page.support;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

import util.Animable;
import util.anim.AnimU;
import util.system.VImg;

public class AnimLCR extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
		@SuppressWarnings("unchecked")
		Animable<? extends AnimU> form = (Animable<? extends AnimU>) o;
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