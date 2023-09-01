package page.info.filter;

import common.util.unit.AbEnemy;
import common.util.unit.Enemy;
import page.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

public class EnemyFindPage extends Page implements SupPage<AbEnemy> {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JLabel source = new JLabel("Source of enemy icon: DB");
	private final JTG show = new JTG(0, "showf");
	private final EnemyListTable elt = new EnemyListTable(this);
	private final EnemyFilterBox efb;
	private final JScrollPane jsp = new JScrollPane(elt);
	private final JTF seatf = new JTF();

	public EnemyFindPage(Page p) {
		super(p);

		efb = EnemyFilterBox.getNew(this);
		ini();
	}

	public EnemyFindPage(Page p, String pack, String... parents) {
		super(p);

		efb = EnemyFilterBox.getNew(this, pack, parents);
		ini();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void callBack(Object o) {
		elt.setList((List<Enemy>) o);
	}

	public List<Enemy> getList() {
		return elt.list;
	}

	@Override
	public Enemy getSelected() {
		int sel = elt.getSelectedRow();
		if (sel < 0)
			return null;
		return elt.list.get(sel);

	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() != elt)
			return;
		elt.clicked(e.getPoint());
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50);
		set(source, x, y, 0, 50, 600, 50);
		set(show, x, y, 250, 0, 200, 50);
		set(seatf, x, y, 550, 0, 1000, 50);
		if (show.isSelected()) {
			int[] siz = efb.getSizer();

			set(efb, x, y, 50, 100, siz[0], siz[1]);

			int mx = 0, my = 0;

			if (siz[2] == 0)
				mx = siz[3];
			else
				my = siz[3];
			set(jsp, x, y, 50 + mx, 100 + my, 2200 - mx, 1150 - my);
		} else
			set(jsp, x, y, 50, 100, 2200, 1150);

		elt.setRowHeight(size(x, y, 50));
	}

	private void addListeners() {
		back.addActionListener(arg0 -> {
			int r = elt.getSelectedRow();
			getFront().callBack(r >= 0 && r < elt.list.size() ? elt.list.get(r) : null);
			changePanel(getFront());
		});

		show.addActionListener(arg0 -> {
			if (show.isSelected())
				add(efb);
			else
				remove(efb);
		});

		seatf.setTypeLnr(x -> setSearch(seatf.getText()));
	}

	private void ini() {
		add(back);
		add(show);
		add(efb);
		add(jsp);
		add(source);
		add(seatf);
		show.setSelected(true);
		seatf.setHint("Search enemy");
		addListeners();
	}

	public void setSearch(String t) {
		if (efb != null) {
			efb.name = t;
			efb.callBack(1);
		}
	}
}
