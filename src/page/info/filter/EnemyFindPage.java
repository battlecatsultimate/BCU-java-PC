package page.info.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import common.util.pack.Pack;
import common.util.unit.Enemy;
import page.JBTN;
import page.JTG;
import page.Page;

public class EnemyFindPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JLabel source = new JLabel("Source of enemy icon: DB");
	private final JTG show = new JTG(0, "showf");
	private final EnemyListTable elt = new EnemyListTable(this);
	private final EnemyFilterBox efb;
	private final JScrollPane jsp = new JScrollPane(elt);

	public EnemyFindPage(Page p, Pack pack) {
		super(p);

		efb = EnemyFilterBox.getNew(this, pack);
		ini();
		resized();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void callBack(Object o) {
		elt.setList((List<Enemy>) o);
		resized();
	}

	public Enemy getEnemy() {
		int sel = elt.getSelectedRow();
		if (sel < 0)
			return null;
		return elt.list.get(sel);

	}

	public List<Enemy> getList() {
		return elt.list;
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
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (show.isSelected())
					add(efb);
				else
					remove(efb);
			}
		});

	}

	private void ini() {
		add(back);
		add(show);
		add(efb);
		add(jsp);
		add(source);
		show.setSelected(true);
		addListeners();
	}

}
