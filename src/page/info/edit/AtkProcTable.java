package page.info.edit;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import common.util.Data.Proc;
import common.util.lang.Formatter;
import common.util.lang.Editors.EditorGroup;
import page.JL;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;
import utilpc.UtilPC;

class AtkProcTable extends Page {

	private static final long serialVersionUID = 1L;

	private static final int LEN = 22, SEC = 16;
	private static final int[] INDS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 27, 29, 31, 32, 21, 22, 23, 24, 25, 26,
			35, 36 };

	private final JLabel[] jlm = new JLabel[LEN];
	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final EditorGroup[] group = new EditorGroup[INDS.length];
	private final boolean editable, isUnit;

	private Proc proc;

	protected AtkProcTable(Page p, boolean edit, boolean unit) {
		super(p);
		editable = edit;
		isUnit = unit;
		ini();
	}

	@Override
	public Component add(Component comp) {
		Component ret = super.add(comp);
		if (comp instanceof JTF)
			ljp.add((JTF) comp);
		return ret;
	}

	@Override
	protected void resized(int x, int y) {
		int h = 0;
		for (int i = 0; i < group.length; i++) {
			int c = i < SEC ? 0 : 400;
			set(jlm[i], x, y, c, h, 350, 50);
			h += 50;
			for (int j = 0; j < group[i].list.length; j++) {
				SwingEditor se = (SwingEditor) group[i].list[j];
				se.resize(x, y, c, h, 350, 50);
				h += 50;
			}
			if (i == SEC - 1)
				h = 0;
		}
	}

	protected void setData(Proc ints) {
		proc = ints;
		for (int i = 0; i < INDS.length; i++)
			group[i].setData(proc.getArr(INDS[i]));
	}

	private void ini() {
		Formatter.Context ctx = new Formatter.Context(!isUnit, false);
		for (int i = 0; i < group.length; i++) {
			group[i] = new EditorGroup(Proc.getName(INDS[i]), editable, () -> getFront().callBack(null));
			jlm[i] = new JL(group[i].getItem(ctx));
			BufferedImage v = UtilPC.getIcon(1, INDS[i]);
			if (v != null)
				jlm[i].setIcon(new ImageIcon(v));
			add(jlm[i]);
			for (int j = 0; j < group[i].list.length; j++) {
				SwingEditor se = (SwingEditor) group[i].list[j];
				se.add(this::add);
			}
		}
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
	}

}
