package page.battle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import common.CommonStatic;
import common.util.basis.BasisSet;
import common.util.stage.MapColc;
import common.util.stage.Recd;
import main.Opts;
import page.JBTN;
import page.JTF;
import page.JTG;
import page.Page;
import page.basis.BasisPage;
import page.info.StageViewPage;

public abstract class AbRecdPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rply = new JBTN(0, "rply");
	private final JBTN recd = new JBTN(-1, "mp4");
	private final JBTN vsta = new JBTN(0, "vsta");
	private final JBTN jlu = new JBTN(0, "line");
	private final JTF seed = new JTF();
	private final JTG larg = new JTG(0, "larges");
	private final JBTN imgs = new JBTN(-1, "PNG");
	private final JLabel len = new JLabel();

	private StageViewPage svp;
	private BasisPage bp;

	protected final boolean editable;

	public AbRecdPage(Page p, boolean edit) {
		super(p);
		editable = edit;
	}

	public abstract Recd getSelection();

	protected void preini() {
		add(back);
		add(rply);
		add(len);
		add(recd);
		add(larg);
		add(imgs);
		add(vsta);
		add(seed);
		add(jlu);
		len.setBorder(BorderFactory.createEtchedBorder());
		addListeners();
	}

	@Override
	protected void renew() {
		setList();
		if (editable && svp != null) {
			Recd r = getSelection();
			if (r != null && svp.getStage() != null && Opts.conf("are you sure to change stage?")) {
				r.st = svp.getStage();
				r.avail = true;
				r.marked = true;
			}
		}
		if (editable && bp != null) {
			Recd r = getSelection();
			if (r != null && Opts.conf("are you sure to change lineup?")) {
				r.lu = BasisSet.current.sele.copy();
				r.marked = true;
			}
		}
		bp = null;
		svp = null;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(rply, x, y, 400, 100, 300, 50);
		set(recd, x, y, 400, 200, 300, 50);
		set(larg, x, y, 750, 200, 300, 50);
		set(imgs, x, y, 1100, 200, 300, 50);
		set(len, x, y, 400, 300, 300, 50);
		set(vsta, x, y, 400, 600, 300, 50);
		set(jlu, x, y, 750, 600, 300, 50);
		set(seed, x, y, 750, 300, 500, 50);
	}

	protected abstract void setList();

	protected void setRecd(Recd r) {
		rply.setEnabled(r != null && r.avail);
		recd.setEnabled(r != null && r.avail);
		imgs.setEnabled(r != null && r.avail);
		seed.setEditable(editable && r != null);
		vsta.setEnabled(r != null);
		jlu.setEnabled(r != null);
		if (r == null) {
			len.setText("");
			seed.setText("");
		} else {
			seed.setText("seed: " + r.seed);
			len.setText("length: " + r.getLen() + " frame");
		}
	}

	private void addListeners() {
		back.setLnr(x -> changePanel(getFront()));

		vsta.setLnr(x -> changePanel(svp = new StageViewPage(getThis(), MapColc.MAPS.values(), getSelection().st)));

		jlu.setLnr(x -> changePanel(bp = new BasisPage(getThis())));

		rply.setLnr(x -> changePanel(new BattleInfoPage(getThis(), getSelection(), 0)));

		recd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = getSelection();
				int conf = 1;
				if (larg.isSelected())
					conf |= 2;
				changePanel(new BattleInfoPage(getThis(), r, conf));
			}
		});

		imgs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Recd r = getSelection();
				int conf = 5;
				if (larg.isSelected())
					conf |= 2;
				changePanel(new BattleInfoPage(getThis(), r, conf));
			}
		});

		seed.setLnr(x -> {
			if (isAdj())
				return;
			Recd r = getSelection();
			if (r == null)
				return;
			r.seed = CommonStatic.parseLongN(seed.getText());
			r.marked = true;
			setRecd(r);
		});

	}

}
