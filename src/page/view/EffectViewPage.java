package page.view;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.CommonStatic;
import common.pack.UserProfile;
import common.util.anim.AnimI;
import page.Page;

public class EffectViewPage extends AbViewPage {

	private static final long serialVersionUID = 1L;

	private final JList<AnimI<?, ?>> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);

	public EffectViewPage(Page p) {
		super(p);

		Vector<AnimI<?, ?>> va = new Vector<>();
		for (AnimI<?, ?> a : CommonStatic.getBCAssets().effas.values())
			va.add(a);
		for (AnimI<?, ?> a : CommonStatic.getBCAssets().atks)
			va.add(a);
		va.addAll(UserProfile.getBCData().souls.getList());
		jlu.setListData(va);
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jspu, x, y, 50, 100, 300, 1100);

	}

	@Override
	protected void updateChoice() {
		AnimI<?, ?> u = jlu.getSelectedValue();
		if (u == null)
			return;
		setAnim(u);
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

	}

	private void ini() {
		preini();
		add(jspu);
		addListeners();

	}

}
