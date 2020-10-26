package page.view;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.system.Node;
import common.util.unit.Form;
import common.util.unit.Unit;
import page.JBTN;
import page.Page;
import page.info.UnitInfoPage;
import page.support.UnitLCR;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class UnitViewPage extends AbViewPage {

	private static final long serialVersionUID = 2010L;

	private final JList<Unit> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JList<Form> jlf = new JList<>();
	private final JScrollPane jspf = new JScrollPane(jlf);
	private final JBTN stat = new JBTN(0, "stat");

	private final String pac;

	public UnitViewPage(Page p, String pack) {
		super(p);
		pac = pack;
		jlu.setListData(new Vector<>(UserProfile.getPack(pack).units.getList()));
		ini();
		resized();
	}

	public UnitViewPage(Page p) {
		super(p);
		pac = null;
		Vector<Unit> v = new Vector<>();

		for(PackData pack : UserProfile.getAllPacks()) {
			v.addAll(pack.units.getList());
		}

		jlu.setListData(v);

		ini();
		resized();
	}

	public UnitViewPage(Page p, Unit u) {
		this(p, u.getID().pack);
		jlu.setSelectedValue(u, true);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jspu, x, y, 50, 100, 300, 1100);
		set(jspf, x, y, 400, 100, 300, 400);
		set(stat, x, y, 400, 1000, 300, 50);
	}

	@Override
	protected void updateChoice() {
		Form f = jlf.getSelectedValue();
		if (f == null)
			return;
		setAnim(f.anim);
	}

	private void addListeners() {

		jlu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				Unit u = jlu.getSelectedValue();
				if (u == null)
					return;
				int ind = jlf.getSelectedIndex();
				if (ind == -1)
					ind = 0;
				jlf.setListData(u.forms);
				jlf.setSelectedIndex(ind < u.forms.length ? ind : 0);
			}

		});

		jlf.addListSelectionListener(new ListSelectionListener() {

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
				Unit u = jlu.getSelectedValue();
				if (u == null)
					return;
				Node<Unit> n;

				if(pac == null) {
					n = Node.getList(UserProfile.getAll(u.id.pack, Unit.class), u);
				} else {
					n = Node.getList(UserProfile.getAll(pac, Unit.class), u);
				}

				changePanel(new UnitInfoPage(getThis(), n));
			}

		});

	}

	private void ini() {
		preini();
		add(jspu);
		add(jspf);
		add(stat);
		jlu.setCellRenderer(new UnitLCR());
		jlf.setCellRenderer(new UnitLCR());
		addListeners();

	}

}
