package page.basis;

import common.util.unit.Combo;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.*;
import java.util.List;

class ComboList extends JList<Combo> {

	private static final long serialVersionUID = 1L;

	protected ComboList() {
		setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
				Combo c = (Combo) o;
				JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
				jl.setText(Interpret.comboInfo(c));
				return jl;
			}

		});
	}

	protected void setList(List<Combo> lf) {
		setListData(lf.toArray(new Combo[0]));
	}

}
