package page.support;

import common.system.VImg;
import common.util.unit.Form;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class UnitTCR extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	private final int[] lnk;
	private final int manualIndex;

	public UnitTCR(int[] ints) {
		lnk = ints;
		manualIndex = 1;
	}

	public UnitTCR(int[] ints, int manualIndex) {
		lnk = ints;
		this.manualIndex = manualIndex;
	}

	@Override
	public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
		Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
		if (lnk != null)
			c = lnk[c];
		if (c != manualIndex)
			return comp;
		JLabel jl = (JLabel) comp;
		Form e = (Form) v;
		if (e == null)
			return jl;
		jl.setText(e.toString());
		jl.setIcon(null);
		jl.setHorizontalTextPosition(SwingConstants.RIGHT);
		VImg vimg = e.anim.getEdi();
		if (vimg == null)
			return jl;
		jl.setIcon(UtilPC.getIcon(vimg));
		return jl;
	}

}
