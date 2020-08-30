package page.view;

import common.battle.data.CustomEnemy;
import common.pack.UserProfile;
import common.util.unit.Enemy;
import page.JBTN;
import page.Page;
import page.info.EnemyInfoPage;
import page.info.edit.EnemyEditPage;
import page.support.AnimLCR;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class EnemyViewPage extends AbViewPage {

	private static final long serialVersionUID = 1L;

	private final JList<Enemy> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JBTN stat = new JBTN(0, "stat");
	private final JLabel source = new JLabel("Source of enemy icon: DB");

	public EnemyViewPage(Page p, Enemy e) {
		this(p, e.getID().pack);
		jlu.setSelectedValue(e, true);
	}

	public EnemyViewPage(Page p, String pac) {
		super(p);
		jlu.setListData(new Vector<>(UserProfile.getPack(pac).enemies.getList()));
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jspu, x, y, 50, 100, 300, 1100);
		set(stat, x, y, 400, 1000, 300, 50);
		set(source, x, y, 0, 50, 600, 50);
		jlu.setFixedCellHeight(size(x, y, 50));
	}

	@Override
	protected void updateChoice() {
		Enemy u = jlu.getSelectedValue();
		if (u == null)
			return;
		setAnim(u.anim);
	}

	private void addListeners() {

		jlu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				updateChoice();
			}

		});

		stat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Enemy ene = jlu.getSelectedValue();
				if (ene == null)
					return;
				if (ene.de instanceof CustomEnemy) {
					changePanel(new EnemyEditPage(getThis(), ene));
				} else
					changePanel(new EnemyInfoPage(getThis(), ene));
			}

		});

	}

	private void ini() {
		preini();
		add(jspu);
		add(stat);
		add(source);
		jlu.setCellRenderer(new AnimLCR());

		addListeners();

	}

}
