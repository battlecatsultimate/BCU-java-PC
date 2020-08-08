package page.info.filter;

import static utilpc.Interpret.ABIIND;
import static utilpc.Interpret.EABIIND;
import static utilpc.Interpret.SABIS;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import main.MainBCU;
import page.JTG;
import page.Page;
import utilpc.Theme;
import utilpc.UtilPC;

class AttList extends JList<String> {

	private static final long serialVersionUID = 1L;

	protected static void btnDealer(int x, int y, JTG[][] btns, JTG[] orop, int... ord) {
		int h = 0, or = 0;
		for (JTG[] sub : btns) {
			int w = 0, j = 0;
			if (ord[or] >= 0)
				Page.set(orop[ord[or]], x, y, 0, h, 200, 50);
			while (j < sub.length) {
				if (!sub[j].getText().equals("(null)")) {
					Page.set(sub[j], x, y, 250 + w % 10 * 175, h + w / 10 * 50, 175, 50);
					w++;
				}
				j++;
			}
			h += (w - 1) / 10 * 50 + 50;
			or++;
		}

	}

	protected AttList(int type, int para) {
		if (MainBCU.nimbus) {
			setSelectionBackground(MainBCU.light ? Theme.LIGHT.NIMBUS_SELECT_BG : Theme.DARK.NIMBUS_SELECT_BG);
		}
		setCellRenderer(new DefaultListCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getListCellRendererComponent(JList<?> l, Object o, int ind, boolean s, boolean f) {
				JLabel jl = (JLabel) super.getListCellRendererComponent(l, o, ind, s, f);
				BufferedImage v;
				if (type == -1) {
					v = ind < para ? UtilPC.getIcon(0, EABIIND[ind]) : UtilPC.getIcon(1, ind - para);
				} else if (type == 0) {
					int len = SABIS.length;
					if (para == 0) {
						v = ind < len ? UtilPC.getIcon(0, ind) : UtilPC.getIcon(1, ind - len);
					} else {
						v = ind < len ? UtilPC.getIcon(0, ind) : UtilPC.getIcon(0, ABIIND[ind - len]);
					}
				} else
					v = UtilPC.getIcon(type, ind);
				if (v == null)
					return jl;
				jl.setIcon(new ImageIcon(v));
				return jl;
			}

		});
	}

}