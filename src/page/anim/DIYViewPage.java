package page.anim;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Opts;
import page.JBTN;
import page.JTG;
import page.Page;
import page.support.AnimLCR;
import page.view.AbViewPage;
import util.anim.AnimC;
import util.system.VImg;
import util.unit.DIYAnim;

public class DIYViewPage extends AbViewPage implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private static final String[] icos = new String[] { "default", "starred", "EF", "TF", "uni_f", "uni_c", "uni_s" };

	private final JList<DIYAnim> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final EditHead aep;

	private final JTG ics = new JTG(0, "icon");
	private final JBTN icc = new JBTN(0, "confirm");
	private final JComboBox<String> jcb = new JComboBox<>(icos);
	private final JLabel uni = new JLabel();
	private final IconBox ib;

	public DIYViewPage(Page p) {
		super(p, new IconBox());
		ib = (IconBox) vb;
		aep = new EditHead(this, 0);
		ini();
		resized();
	}

	public DIYViewPage(Page p, DIYAnim ac) {
		super(p, new IconBox());
		ib = (IconBox) vb;
		aep = new EditHead(this, 0);
		if (!DIYAnim.map.containsValue(ac))
			aep.focus = ac;
		ini();
		resized();
	}

	public DIYViewPage(Page p, EditHead bar) {
		super(p, new IconBox());
		ib = (IconBox) vb;

		aep = bar;
		ini();
		resized();
	}

	@Override
	public void setSelection(DIYAnim ac) {
		jlu.setSelectedValue(ac, true);
	}

	@Override
	protected void enabler(boolean b) {
		super.enabler(b);
		ics.setEnabled(ics.isSelected() || b && pause);
		icc.setEnabled(ics.isSelected());
		jlu.setEnabled(b);
	}

	@Override
	protected void keyPressed(KeyEvent ke) {
		if (ke.getSource() == ib)
			ib.keyPressed(ke);
	}

	@Override
	protected void keyReleased(KeyEvent ke) {
		if (ke.getSource() == ib)
			ib.keyReleased(ke);
	}

	@Override
	protected void renew() {
		if (aep.focus == null)
			jlu.setListData(new Vector<>(DIYAnim.map.values()));
		else
			jlu.setListData(new DIYAnim[] { aep.focus });
		jlu.setSelectedIndex(0);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(aep, x, y, 550, 0, 1750, 50);
		set(jspu, x, y, 50, 100, 300, 1100);
		set(ics, x, y, 1000, 1050, 200, 50);
		set(uni, x, y, 750, 500, 200, 200);
		set(jcb, x, y, 750, 750, 200, 50);
		set(icc, x, y, 1000, 1150, 200, 50);
	}

	@Override
	protected void updateChoice() {
		DIYAnim e = jlu.getSelectedValue();
		if (e != null && e.getAnimC().mismatch)
			e = null;
		aep.setAnim(e);
		uni.setIcon(e == null ? null : e.anim.uni.getIcon());
		if (e == null)
			return;
		setAnim(e);
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

		ics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enabler(!ics.isSelected());
				ib.blank = ics.isSelected();
			}

		});

		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int t = jcb.getSelectedIndex();
				IconBox.mode = t / 4;
				IconBox.type = t % 4;
				IconBox.glow = IconBox.type + IconBox.mode > 1 ? 1 : 0;
				ib.changeType();
			}

		});

		icc.setLnr(x -> {
			if (IconBox.mode == 0 && Opts.w$c("are you sure to replace display icon? This action cannot be undone")) {
				AnimC ac = aep.anim.anim;
				ac.edi = new VImg(ib.getClip());
				ac.saveIcon();
				jlu.repaint();
			}
			if (IconBox.mode == 1 && Opts.w$c("are you sure to replace battle icon? This action cannot be undone")) {
				AnimC ac = aep.anim.anim;
				ac.uni = new VImg(ib.getClip());
				ac.saveUni();
				uni.setIcon(ac.uni.getIcon());
			}
		});

	}

	private void ini() {
		preini();
		add(aep);
		add(jspu);
		add(ics);
		add(icc);
		add(jcb);
		add(uni);
		jcb.setSelectedIndex(IconBox.type);
		ics.setEnabled(false);
		icc.setEnabled(false);
		jlu.setCellRenderer(new AnimLCR());
		addListeners();

	}

}
