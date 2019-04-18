package page.event;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;

import event.EventBase;
import event.Gacha;
import page.JTG;

class FGacha extends ListFilter<Gacha> {

	private static final long serialVersionUID = 1L;

	private static final int[] CODE = new int[] { 0, 1, 1 };

<<<<<<< HEAD
	private final JTG jtb0 = new JTG(get(0, "nknn"));
	private final JTG jtb1 = new JTG(get(0, "usr"));
	private final JTG jtb2 = new JTG(get(0, "luk"));
=======
	private final JTG jtb0 = new JTG(get("nknn"));
	private final JTG jtb1 = new JTG(get("usr"));
	private final JTG jtb2 = new JTG(get("luk"));
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
	private final JTG[] jtbs = new JTG[] { jtb0, jtb1, jtb2 };

	protected FGacha(int[] datas, URLPage ep) {
		super(null, ep, EventBase.ebg);
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
	protected List<Gacha> getList() {
		List<Gacha> list = new ArrayList<>();
		for (Gacha de : EventBase.ebg.lg) {
			int t = de.getType();
			boolean b1 = false;
			for (int i = 0; i < jtbs.length; i++)
				if (jtbs[i].isSelected() && i == t)
					b1 = true;
			if (b1)
				list.add(de);
		}
		return list;
	}

	@Override
	protected void resized(int x, int y) {
		set(jtb0, x, y, 50, 50, 200, 50);
		set(jtb1, x, y, 50, 150, 200, 50);
		set(jtb2, x, y, 50, 250, 200, 50);
		set(conf, x, y, 50, 400, 200, 50);
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
		add(conf);
		addListeners();
	}

}
