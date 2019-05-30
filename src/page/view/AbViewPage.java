package page.view;

import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Writer;
import main.Timer;
import page.JBTN;
import page.JTG;
import page.Page;
import page.anim.ImgCutEditPage;
import page.awt.BBBuilder;
import page.view.ViewBox.Loader;
import util.Animable;
import util.anim.AnimC;
import util.anim.AnimD;
import util.anim.AnimI;
import util.anim.EAnimI;
import util.unit.DIYAnim;

public abstract class AbViewPage extends Page {

	private static final long serialVersionUID = 1L;
	private static final double res = 0.95;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN copy = new JBTN(0, "copy");
	private final JList<String> jlt = new JList<>();
	private final JScrollPane jspt = new JScrollPane(jlt);
	private final JSlider jst = new JSlider(100, 900);
	private final JSlider jtl = new JSlider();
	private final JTG jtb = new JTG(0, "pause");
	private final JBTN nex = new JBTN(0, "nextf");
	private final JTG gif = new JTG(0, "gif");
	private final JBTN png = new JBTN(0, "png");

	protected final ViewBox vb;

	private Loader loader = null;
	protected boolean pause;
	private boolean changingT;
	private boolean changingtl;

	protected AbViewPage(Page p) {
		this(p, BBBuilder.def.getViewBox());
	}

	protected AbViewPage(Page p, ViewBox box) {
		super(p);
		vb = box;
	}

	protected void enabler(boolean b) {
		jtb.setEnabled(b);
		back.setEnabled(b);
		copy.setEnabled(b);
		jlt.setEnabled(b);
		jst.setEnabled(b);
		jtl.setEnabled(b && pause);
		nex.setEnabled(b && pause);
		gif.setEnabled(b);
		png.setEnabled(b && pause);
	}

	@Override
	protected void exit() {
		Timer.p = 33;
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (e.getSource() == vb)
			vb.mouseDragged(e);
	}

	@Override
	protected void mousePressed(MouseEvent e) {
		if (e.getSource() == vb)
			vb.mousePressed(e);
	}

	@Override
	protected void mouseReleased(MouseEvent e) {
		if (e.getSource() == vb)
			vb.mouseReleased(e);
	}

	@Override
	protected void mouseWheel(MouseEvent e) {
		if (!(e.getSource() instanceof ViewBox))
			return;
		MouseWheelEvent mwe = (MouseWheelEvent) e;
		double d = mwe.getPreciseWheelRotation();
		vb.resize(Math.pow(res, d));
	}

	protected void preini() {
		add(back);
		add(copy);
		add((Canvas) vb);
		add(jspt);
		add(jst);
		add(jtb);
		add(jtl);
		add(nex);
		add(gif);
		add(png);
		jst.setPaintLabels(true);
		jst.setPaintTicks(true);
		jst.setMajorTickSpacing(100);
		jst.setMinorTickSpacing(25);
		jst.setValue(Timer.p * 100 / 33);
		jtl.setEnabled(false);
		jtl.setPaintTicks(true);
		jtl.setPaintLabels(true);
		png.setEnabled(false);
		addListener();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(copy, x, y, 250, 0, 200, 50);
		set((Canvas) vb, x, y, 1000, 100, 1000, 600);
		set(jspt, x, y, 400, 550, 300, 400);
		set(jst, x, y, 1000, 750, 1000, 100);
		set(jtl, x, y, 1000, 900, 1000, 100);
		set(jtb, x, y, 1300, 1050, 200, 50);
		set(nex, x, y, 1600, 1050, 200, 50);
		set(png, x, y, 1300, 1150, 200, 50);
		set(gif, x, y, 1600, 1150, 400, 50);
	}

	protected void setAnim(Animable<? extends AnimI> a) {
		if (!changingT) {
			int ind = jlt.getSelectedIndex();
			if (ind == -1)
				ind = 0;
			a.anim.check();
			String[] strs = a.anim.names();
			jlt.setListData(strs);
			if (ind >= strs.length)
				ind = 0;
			jlt.setSelectedIndex(ind);
		}
		if (jlt.getSelectedIndex() == -1)
			return;
		vb.setEntity(a.getEAnim(jlt.getSelectedIndex()));
		jtl.setMinimum(0);
		jtl.setMaximum(vb.getEnt().len());
		jtl.setLabelTable(null);
		if (vb.getEnt().len() <= 50) {
			jtl.setMajorTickSpacing(5);
			jtl.setMinorTickSpacing(1);
		} else if (vb.getEnt().len() <= 200) {
			jtl.setMajorTickSpacing(10);
			jtl.setMinorTickSpacing(2);
		} else if (vb.getEnt().len() <= 1000) {
			jtl.setMajorTickSpacing(50);
			jtl.setMinorTickSpacing(10);
		} else if (vb.getEnt().len() <= 5000) {
			jtl.setMajorTickSpacing(250);
			jtl.setMinorTickSpacing(50);
		} else {
			jtl.setMajorTickSpacing(1000);
			jtl.setMinorTickSpacing(200);
		}
	}

	@Override
	protected void timer(int t) {
		if (!pause)
			eupdate();
		vb.paint();
		if (loader == null)
			gif.setText(0, "gif");
		else
			gif.setText(loader.getProg());
		if (!gif.isSelected() && gif.isEnabled())
			loader = null;
	}

	protected abstract void updateChoice();

	private void addListener() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EAnimI ei = vb.getEnt();
				if (ei == null || !(ei.anim() instanceof AnimD))
					return;
				AnimD eau = (AnimD) ei.anim();
				String str = "new anim";
				str = AnimC.getAvailable(str);
				AnimC ac = new AnimC(str, eau);
				DIYAnim da = new DIYAnim(str, ac);
				DIYAnim.map.put(str, da);
				changePanel(new ImgCutEditPage(getThis()));
			}
		});

		jlt.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				changingT = true;
				updateChoice();
				changingT = false;
			}

		});

		jst.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (jst.getValueIsAdjusting())
					return;
				Timer.p = jst.getValue() * 33 / 100;
			}
		});

		jtl.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (changingtl || !pause)
					return;
				if (vb.getEnt() != null)
					vb.getEnt().setTime(jtl.getValue());

			}
		});

		jtb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pause = jtb.isSelected();
				enabler(true);
			}
		});

		nex.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				eupdate();
			}
		});

		png.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
				File f = new File("./img/" + str + ".png");
				Writer.writeImage(vb.getPrev(), f);
			}
		});

		gif.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (gif.isSelected())
					loader = vb.start();
				else
					vb.end(gif);
			}
		});

	}

	private void eupdate() {
		vb.update();
		changingtl = true;
		if (vb.getEnt() != null)
			jtl.setValue(vb.getEnt().ind());
		changingtl = false;
	}

}
