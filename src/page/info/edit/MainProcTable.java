package page.info.edit;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import common.util.Data.Proc;
import common.util.lang.Formatter;
import common.util.lang.Editors.EditorGroup;
import page.JL;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;
import utilpc.UtilPC;

class MainProcTable extends Page {

	private static final long serialVersionUID = 1L;

	private static final int[] INDS = { 9, 10, 11, 12, 28, 30 };
	private final JL[] tits = new JL[INDS.length];
	private final EditorGroup[] group = new EditorGroup[INDS.length];
	private final ListJtfPolicy ljp = new ListJtfPolicy();

	private Proc proc;

	private final boolean editable;

	protected MainProcTable(Page p, boolean edit) {
		super(p);

		editable = edit;
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
		setPreferredSize(size(x, y, 300, 1000).toDimension());
		int h = 0;
		for (int i = 0; i < INDS.length; i++) {
			set(tits[i], x, y, 0, h, 300, 50);
			h += 50;
			for (int j = 0; j < group[i].list.length; j++) {
				SwingEditor se = (SwingEditor) group[i].list[j];
				se.resize(x, y, 0, h, 300, 50);
				h += 50;
			}
		}

	}

	protected void setData(Proc ints) {
		proc = ints;
		for (int i = 0; i < INDS.length; i++)
			group[i].setData(proc.getArr(INDS[i]));
	}

	private void ini() {
		Formatter.Context ctx = new Formatter.Context(false, false);
		for (int i = 0; i < group.length; i++) {
			group[i] = new EditorGroup(Proc.getName(INDS[i]), editable, () -> getFront().callBack(null));
			tits[i] = new JL(group[i].getItem(ctx));
			BufferedImage v = UtilPC.getIcon(1, INDS[i]);
			if (v != null)
				tits[i].setIcon(new ImageIcon(v));
			add(tits[i]);
			for (int j = 0; j < group[i].list.length; j++) {
				SwingEditor se = (SwingEditor) group[i].list[j];
				se.add(this::add);
			}
		}
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);

	}

}
