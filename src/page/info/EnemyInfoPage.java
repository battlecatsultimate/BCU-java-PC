package page.info;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import common.battle.BasisSet;
import common.util.unit.Enemy;
import page.JBTN;
import page.Page;
import page.view.EnemyViewPage;

public class EnemyInfoPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN anim = new JBTN(0, "anim");
	private final JBTN find = new JBTN(0, "stage");
	private final JLabel source = new JLabel("Source of enemy icon: DB");
	private final EnemyInfoTable info;
	private final TreaTable trea;

	private final Enemy e;
	private BasisSet b = BasisSet.current;

	public EnemyInfoPage(Page p, Enemy de) {
		this(p, de, 100, 0);
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
		set(info, x, y, 50, 100, 1600, 800);
		set(trea, x, y, 1700, 100, 400, 1200);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getFront() instanceof StageFilterPage)
					changePanel(getFront().getFront());
				else
					changePanel(getFront());
			}
		});

		anim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (getFront() instanceof EnemyViewPage)
					changePanel(getFront());
				else
					changePanel(new EnemyViewPage(getThis(), e));
			}
		});

		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new StageFilterPage(getThis(), e.findApp()));
			}
		});

	}

	private void ini() {
		add(back);
		add(info);
		add(trea);
		add(anim);
		add(find);
		add(source);
		addListeners();
	}

}
