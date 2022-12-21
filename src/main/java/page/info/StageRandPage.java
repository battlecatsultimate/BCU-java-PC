package page.info;

import common.util.stage.MapColc;
import common.util.stage.RandStage;
import common.util.stage.Stage;
import page.JBTN;
import page.Page;
import page.battle.BattleSetupPage;

import javax.swing.*;

public class StageRandPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");

	private final JList<String> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final MapColc stages;

	protected StageRandPage(Page p, MapColc mc) {
		super(p);
		stages = mc == null ? MapColc.DefMapColc.getMap("N") : mc;

		ini();
		resized();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(strt, x, y, 300, 50, 200, 50);
		set(jsps, x, y, 50, 100, 200, 600);
	}

	private void addListeners() {
		back.setLnr(e -> changePanel(getFront()));

		jls.addListSelectionListener(e -> {
			if (jls.getValueIsAdjusting())
				return;
			if (jls.getSelectedValue() == null)
				jls.setSelectedIndex(0);
		});

		strt.setLnr(x -> {
			int s = jls.getSelectedIndex();
			Stage sta = RandStage.getStage(stages, s);
			changePanel(new BattleSetupPage(getThis(), sta, 0));
		});
	}

	private void ini() {
		add(back);
		add(strt);
		add(jsps);
		String[] as = new String[stages.maps.size()];
		for (int i = 0; i < as.length; i++)
			as[i] = "level " + (i + 1);
		jls.setListData(as);
		String[] ts = new String[5];
		for (int i = 0; i < 5; i++)
			ts[i] = "attempt " + (i + 1);
		jls.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jls.setSelectedIndex(0);
		addListeners();
	}

}
