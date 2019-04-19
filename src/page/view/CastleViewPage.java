package page.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import page.JBTN;
import page.Page;
import util.stage.AbCastle;
import util.stage.Castles;
import util.system.VImg;

public class CastleViewPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<AbCastle> jlsm = new JList<>();
	private final JScrollPane jspsm = new JScrollPane(jlsm);
	private final JList<VImg> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	public CastleViewPage(Page p) {
		this(p, Castles.map.values());
	}

	public CastleViewPage(Page p, AbCastle sele) {
		this(p, Castles.map.values());
		jlsm.setSelectedValue(sele, true);
	}

	public CastleViewPage(Page p, Collection<AbCastle> list) {
		super(p);

		Vector<AbCastle> vec = new Vector<>();
		vec.addAll(list);
		jlsm.setListData(vec);
		ini();
		resized();
	}

	public CastleViewPage(Page p, Collection<AbCastle> defcas, int ind) {
		this(p, defcas);
		jlsm.setSelectedValue(Castles.map.get(ind / 1000), true);
		jlst.setSelectedIndex(ind % 1000);
	}

	public int getVal() {
		AbCastle ac = jlsm.getSelectedValue();
		if (ac == null)
			return -1;
		return ac.getCasID(jlst.getSelectedValue());
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspsm, x, y, 50, 100, 300, 1100);
		set(jspst, x, y, 400, 550, 300, 650);
		set(jl, x, y, 800, 50, 1000, 1000);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jlsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				AbCastle sm = jlsm.getSelectedValue();
				if (sm == null)
					return;
				jlst.setListData(new Vector<>(sm.getList()));
				jlst.setSelectedIndex(0);
			}

		});

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				VImg s = jlst.getSelectedValue();
				if (s == null)
					jl.setIcon(null);
				else
					jl.setIcon(s.getIcon());
			}

		});

	}

	private void ini() {
		add(back);
		add(jspsm);
		add(jspst);
		add(jl);
		addListeners();

	}

}
