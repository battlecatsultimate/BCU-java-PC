package page.view;

import common.pack.Identifier;
import common.util.stage.CastleImg;
import common.util.stage.CastleList;
import page.JBTN;
import page.Page;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Vector;

public class CastleViewPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<CastleList> jlsm = new JList<>();
	private final JScrollPane jspsm = new JScrollPane(jlsm);
	private final JList<CastleImg> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();
	private final JLabel bs = new JLabel();

	public CastleViewPage(Page p) {
		this(p, CastleList.map().values());
	}

	public CastleViewPage(Page p, CastleList sele) {
		this(p);
		jlsm.setSelectedValue(sele, true);
	}

	public CastleViewPage(Page p, Collection<CastleList> list) {
		super(p);
		Vector<CastleList> vec = new Vector<>(list);
		jlsm.setListData(vec);
		ini();
		resized();
	}

	public CastleViewPage(Page p, Collection<CastleList> defcas, Identifier<CastleImg> id) {
		this(p, defcas);
		if (id != null) {
			CastleList abcas = (CastleList) id.getCont();
			jlsm.setSelectedValue(abcas, true);
			jlst.setSelectedValue(id.get(), true);
		}
	}

	public Identifier<CastleImg> getVal() {
		CastleImg img = jlst.getSelectedValue();
		return img == null ? null : img.getID();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspsm, x, y, 50, 100, 300, 1100);
		set(jspst, x, y, 400, 550, 300, 650);
		set(jl, x, y, 800, 50, 1000, 1000);
		set(bs, x, y, 400, 500, 300, 50);
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
				CastleList sm = jlsm.getSelectedValue();
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
				CastleImg s = jlst.getSelectedValue();
				if (s == null)
					jl.setIcon(null);
				else {
					jl.setIcon(UtilPC.getIcon(s.img));
					bs.setText("Boss Spawn: " + s.boss_spawn);
				}
			}

		});

	}

	private void ini() {
		add(back);
		add(jspsm);
		add(jspst);
		add(jl);
		add(bs);
		addListeners();

	}

}
