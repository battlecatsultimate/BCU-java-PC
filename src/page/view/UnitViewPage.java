package page.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.system.Node;
import common.util.pack.Pack;
import common.util.unit.Form;
import common.util.unit.Unit;
import common.util.unit.UnitStore;
import page.JBTN;
import page.Page;
import page.info.UnitInfoPage;
import page.support.UnitLCR;

public class UnitViewPage extends AbViewPage {

	private static final long serialVersionUID = 2010L;

	private final JList<Unit> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JList<Form> jlf = new JList<>();
	private final JScrollPane jspf = new JScrollPane(jlf);
	private final JBTN stat = new JBTN(0, "stat");

	private final Pack pac;

	public UnitViewPage(Page p, Pack pack) {
		super(p);

		jlu.setListData(new Vector<>(UnitStore.getAll(pac = pack, false)));
		ini();
		resized();
	}

	public UnitViewPage(Page p, Unit u) {
		this(p, u.pack);
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
		setAnim(f);
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
				Node<Unit> n = Node.getList(UnitStore.getAll(pac, false), u);
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
