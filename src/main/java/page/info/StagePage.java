package page.info;

import common.util.stage.Stage;
import page.JBTN;
import page.Page;
import page.battle.BattleSetupPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class StagePage extends Page {

	private static final long serialVersionUID = 1L;

	protected final JBTN back = new JBTN(0, "back");
	protected final JBTN strt = new JBTN(0, "start");
	private final StageTable jt = new StageTable(this);
	private final JScrollPane jspjt = new JScrollPane(jt);
	private final HeadTable info = new HeadTable(this);
	private final JScrollPane jspinfo = new JScrollPane(info);

	protected Stage stage;

	public StagePage(Page p) {
		super(p);

		ini();
	}

	public Stage getStage() {
		return stage;
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == jt)
			jt.clicked(e.getPoint());
		if (e.getSource() == info)
			info.clicked(e.getPoint());
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspinfo, x, y, 800, 50, 1400, 300);
		set(jspjt, x, y, 800, 400, 1400, 800);
		jt.setRowHeight(size(x, y, 50));
		info.setRowHeight(size(x, y, 50));
	}

	protected void setData(Stage st) {
		stage = st;
		strt.setEnabled(st != null);
		if(st != null) {
			info.setData(st);
			jt.setData(st);
		}
		jspjt.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		strt.addActionListener(arg0 -> {
			if (stage == null)
				return;
			changePanel(new BattleSetupPage(getThis(), stage, 1));
		});

	}

	private void ini() {
		add(back);
		add(jspjt);
		add(jspinfo);
		add(strt);
		strt.setEnabled(false);
		addListeners();
	}

}
