package page.info;

import common.util.stage.Stage;
import common.util.stage.info.DefStageInfo;
import main.Opts;
import page.JBTN;
import page.Page;
import page.battle.BattleSetupPage;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class StagePage extends Page {

	private static final long serialVersionUID = 1L;

	protected final JBTN back = new JBTN(0, "back");
	protected final JBTN strt = new JBTN(0, "start");
	private final StageTable jt = new StageTable(this);
	private final JScrollPane jspjt = new JScrollPane(jt);
	private final HeadTable info = new HeadTable(this);
	private final JScrollPane jspinfo = new JScrollPane(info);

	private final JBTN infs = new JBTN(0, "info");
	private final JBTN infm = new JBTN(0, "matinfo");

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
		set(infs, x, y, 1350, 350, 200, 50);
		set(infm, x, y, 1600, 350, 200, 50);
		jt.setRowHeight(size(x, y, 50));
		info.setRowHeight(size(x, y, 50));
	}

	protected synchronized void setData(Stage st, int starId) {
		stage = st;
		strt.setEnabled(st != null);
		infs.setEnabled(st != null);
		infm.setEnabled(st != null && st.info instanceof DefStageInfo && ((DefStageInfo) st.info).maxMaterial != -1);
		if(st != null) {
			info.setData(st, starId);
			jt.setData(st, Math.min(starId, st.getCont().stars.length - 1));
		}
		jspjt.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		infs.setLnr(x -> {
			if (stage == null)
				return;
			if (stage.info != null)
				Opts.pop(Interpret.readHTML(stage), "stage info");
			else
				Opts.pop(Interpret.readHTMLStage(stage, false), "stage info");
		});

		infm.setLnr(x -> {
			if (stage != null && stage.info instanceof DefStageInfo && ((DefStageInfo) stage.info).maxMaterial != -1)
				Opts.pop(Interpret.readMaterialData((DefStageInfo) stage.info), "map data");
		});

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
		add(infs);
		add(infm);
		strt.setEnabled(false);
		infs.setEnabled(false);
		infm.setEnabled(false);
		info.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				info.hover(e.getPoint());
			}
		});
		addListeners();
	}

}
