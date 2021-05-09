package page.info;

import common.battle.BasisSet;
import common.util.unit.Enemy;
import page.JBTN;
import page.JTG;
import page.Page;
import page.view.EnemyViewPage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyInfoPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN anim = new JBTN(0, "anim");
	private final JBTN find = new JBTN(0, "stage");
	private final JTG extr = new JTG(0, "extra");
	private final JLabel source = new JLabel("Source of enemy icon: DB");
	private final JPanel cont = new JPanel();
	private final JScrollPane jsp = new JScrollPane(cont);
	private final EnemyInfoTable info;
	private final TreaTable trea;

	private final Enemy e;
	private final BasisSet b = BasisSet.current();

	public EnemyInfoPage(Page p, Enemy de) {
		this(p, de, 100, 100);
	}

	public EnemyInfoPage(Page p, Enemy de, int mul, int mula) {
		super(p);
		e = de;

		info = new EnemyInfoTable(this, de, mul, mula);
		trea = new TreaTable(this, b);
		ini();
		resized();
	}

	@Override
	public void callBack(Object o) {
		info.reset();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(source, x, y, 0, 50, 600, 50);
		set(anim, x, y, 600, 0, 200, 50);
		set(find, x, y, 1200, 0, 200, 50);
		set(extr, x, y, 1500, 0, 200, 50);
		set(jsp, x, y, 50, 100, 1650, 1150);
		set(trea, x, y, 1700, 100, 400, 1200);
		int ih = info.getH();
		cont.setPreferredSize(size(x, y, 1600, ih - 50).toDimension());
		jsp.getVerticalScrollBar().setUnitIncrement(size(x, y, 50));
		set(info, x, y, 0, 0, 1600, ih);
		info.resized();
		jsp.revalidate();
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		anim.addActionListener(arg0 -> {
			if (getFront() instanceof EnemyViewPage)
				changePanel(getFront());
			else
				changePanel(new EnemyViewPage(getThis(), e));
		});

		find.addActionListener(arg0 -> changePanel(new StageFilterPage(getThis(), e.findApp())));

		extr.addActionListener(arg0 -> {
			info.setDisplaySpecial(extr.isSelected());
		});
	}

	private void ini() {
		add(back);
		cont.add(info);
		cont.setLayout(null);
		add(jsp);
		add(trea);
		add(anim);
		add(find);
		add(extr);
		add(source);
		addListeners();
	}

}
