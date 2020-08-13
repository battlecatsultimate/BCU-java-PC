package page.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.pack.PackData.Identifier;
import common.pack.UserProfile;
import common.util.pack.Background;
import page.JBTN;
import page.Page;
import utilpc.UtilPC;

public class BGViewPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Background> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	public BGViewPage(Page p, String pac) {
		super(p);
		jlst.setListData(new Vector<>(UserProfile.getAll(pac, Background.class)));
		ini();
		resized();
	}

	public BGViewPage(Page front, String pac, Identifier bg) {
		this(front, pac);
		jlst.setSelectedValue(UserProfile.get(bg,Background.class), false);
	}

	public Background getSelected() {
		return jlst.getSelectedValue();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspst, x, y, 50, 100, 300, 1100);
		set(jl, x, y, 400, 50, 1800, 1100);
		Background s = jlst.getSelectedValue();
		if (s == null)
			return;
		jl.setIcon(UtilPC.getBg(s, jl.getWidth(), jl.getHeight()));
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				Background s = jlst.getSelectedValue();
				if (s == null)
					return;
				jl.setIcon(UtilPC.getBg(s, jl.getWidth(), jl.getHeight()));
			}

		});

	}

	private void ini() {
		add(back);
		add(jspst);
		add(jl);
		addListeners();

	}

}
