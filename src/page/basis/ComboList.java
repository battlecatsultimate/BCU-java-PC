package page.basis;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import util.Interpret;
import util.basis.Combo;

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
