package page.basis;

import common.battle.BasisSet;
import common.util.unit.Combo;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class ComboList extends JList<Combo> {
	private BasisSet lineup;

	private static final long serialVersionUID = 1L;

	protected ComboList() {
		setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
				Combo c = (Combo) o;
				JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
				jl.setText(Interpret.comboInfo(c, lineup));
				return jl;
			}

		});
	}

	public void setBasis(BasisSet b) {
		lineup = b;
	}
}
