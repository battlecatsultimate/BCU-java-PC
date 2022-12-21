package page.view;

import common.pack.Identifier;
import common.pack.PackData;
import common.pack.UserProfile;
import common.util.pack.Background;
import page.JBTN;
import page.Page;
import page.SupPage;
import utilpc.UtilPC;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class BGViewPage extends Page implements SupPage<Background> {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<Background> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);
	private final JLabel jl = new JLabel();

	public BGViewPage(Page p) {
		super(p);
		List<Background> bgs = new ArrayList<>();
		for (PackData pac : UserProfile.getAllPacks())
			bgs.addAll(pac.bgs.getList());

		jlst.setListData(bgs.toArray(new Background[0]));
		ini();
		resized();
	}

	public BGViewPage(Page p, String pac) {
		super(p);
		jlst.setListData(new Vector<>(UserProfile.getAll(pac, Background.class)));
		ini();
		resized();
	}

	public BGViewPage(Page front, String pac, Identifier<Background> bg) {
		this(front, pac);
		jlst.setSelectedValue(Identifier.get(bg), false);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
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
		back.addActionListener(arg0 -> changePanel(getFront()));

		jlst.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			Background s = jlst.getSelectedValue();
			if (s == null)
				return;
			jl.setIcon(UtilPC.getBg(s, jl.getWidth(), jl.getHeight()));
		});

	}

	private void ini() {
		add(back);
		add(jspst);
		add(jl);
		addListeners();

	}

}
