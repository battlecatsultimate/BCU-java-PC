package page.event;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import event.EventBase;
import event.Item;
import page.JTG;

class FItem extends ListFilter<Item> {

	private static final long serialVersionUID = 1L;

	private static final int[] CODE = new int[] { 0, 1, 0, 0, 1, 0, 1, 0 };

	private final JTG jtb0 = new JTG(get(0, "perm"));
	private final JTG jtb1 = new JTG(get(0, "situ"));
	private final JTG jtb2 = new JTG(get(0, "newv"));
	private final JTG jtb3 = new JTG(get(0, "ftre"));
	private final JTG[] jtbs = new JTG[] { jtb0, jtb1, jtb2, jtb3 };
	private final JTG jcb0 = new JTG(get(0, "nknn"));
	private final JTG jcb1 = new JTG(get(0, "popp"));
	private final JTG jcb2 = new JTG(get(0, "item"));
	private final JTG jcb3 = new JTG(get(0, "plat"));
	private final JTG[] jcbs = new JTG[] { jcb0, jcb1, jcb2, jcb3 };

	protected FItem(int[] datas, URLPage ep) {
		super(null, ep, EventBase.ebi);
		setDatas(datas);

		setBorder(BorderFactory.createEtchedBorder());
		ini();
		resized();
	}

	@Override
	protected int[] getDatas() {
		int[] ans = new int[jtbs.length + jcbs.length];
		for (int i = 0; i < jtbs.length; i++)
			if (jtbs[i].isSelected())
				ans[i] = 1;
		for (int i = 0; i < jcbs.length; i++)
			if (jcbs[i].isSelected())
				ans[i + jtbs.length] = 1;
		return ans;
	}

	@Override
	protected List<Item> getList() {
		List<Item> list = new ArrayList<>();
		for (Item de : EventBase.ebi.li) {
			int t = de.getType();
			boolean b0 = true;
			for (int i = 0; i < jtbs.length; i++)
				if (jtbs[i].isSelected() && ((t >> i) & 1) == 0)
					b0 = false;
			boolean b1 = false;
			int c = de.getClas();
			for (int i = 0; i < jcbs.length; i++)
				if (jcbs[i].isSelected() && i == c)
					b1 = true;
			if (b0 && b1)
				list.add(de);
		}
		return list;
	}

	@Override
	protected void resized(int x, int y) {
		set(jtb0, x, y, 50, 50, 200, 50);
		set(jtb1, x, y, 50, 150, 200, 50);
		set(jtb2, x, y, 50, 250, 200, 50);
		set(jtb3, x, y, 50, 350, 200, 50);
		set(jcb0, x, y, 300, 50, 200, 50);
		set(jcb1, x, y, 300, 150, 200, 50);
		set(jcb2, x, y, 300, 250, 200, 50);
		set(jcb3, x, y, 300, 350, 200, 50);
		set(conf, x, y, 50, 600, 450, 50);
		repaint();
	}

	@Override
	protected void setDatas(int[] datas) {
		int[] dt = datas;
		if (datas == null || datas.length != jtbs.length + jcbs.length)
			dt = CODE;
		for (int i = 0; i < jtbs.length; i++)
			jtbs[i].setSelected(dt[i] == 1);
		for (int i = 0; i < jcbs.length; i++)
			jcbs[i].setSelected(dt[i + jtbs.length] == 1);
	}

	private void ini() {
		add(jtb0);
		add(jtb1);
		add(jtb2);
		add(jtb3);
		add(jcb0);
		add(jcb1);
		add(jcb2);
		add(jcb3);
		add(conf);
		addListeners();
	}

}
