package page.event;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import event.DisplayEvent;
import event.EventBase;
import page.JTG;

class FEvent extends ListFilter<DisplayEvent> {

	private static final long serialVersionUID = 1L;

	private static final int[] CODE = new int[] { 0, 1, 0, 0, 0 };

	private final JTG jtb0 = new JTG(get("perm"));
	private final JTG jtb1 = new JTG(get("situ"));
	private final JTG jtb2 = new JTG(get("newv"));
	private final JTG jtb3 = new JTG(get("ftre"));
	private final JTG jtb4 = new JTG(get("onet"));
	private final JTG[] jtbs = new JTG[] { jtb0, jtb1, jtb2, jtb3, jtb4 };

	protected FEvent(int[] datas, URLPage ep) {
		super(null, ep, EventBase.ebe);
		setDatas(datas);

		setBorder(BorderFactory.createEtchedBorder());
		ini();
		resized();
	}

	@Override
	protected int[] getDatas() {
		int[] ans = new int[jtbs.length];
		for (int i = 0; i < jtbs.length; i++)
			if (jtbs[i].isSelected())
				ans[i] = 1;
		return ans;
	}

	@Override
	protected List<DisplayEvent> getList() {
		List<DisplayEvent> list = new ArrayList<>();
		for (DisplayEvent de : EventBase.ebe.le) {
			int t = de.getType();
			boolean b = true;
			for (int i = 0; i < jtbs.length; i++)
				if (jtbs[i].isSelected() && ((t >> i) & 1) == 0)
					b = false;
			if (b)
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
		set(jtb4, x, y, 50, 450, 200, 50);
		set(conf, x, y, 50, 600, 200, 50);
		repaint();
	}

	@Override
	protected void setDatas(int[] datas) {
		int[] dt = datas;
		if (datas == null || datas.length != jtbs.length)
			dt = CODE;
		for (int i = 0; i < jtbs.length; i++)
			jtbs[i].setSelected(dt[i] == 1);
	}

	private void ini() {
		add(jtb0);
		add(jtb1);
		add(jtb2);
		add(jtb3);
		add(jtb4);
		add(conf);
		addListeners();
	}

}
