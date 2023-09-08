package page.view;

import common.CommonStatic;
import common.pack.Source;
import common.pack.Source.ResourceLocation;
import common.pack.Source.Workspace;
import common.util.anim.*;
import io.BCUWriter;
import main.Timer;
import page.*;
import page.anim.ImgCutEditPage;
import page.awt.BBBuilder;
import page.view.ViewBox.Loader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public abstract class AbViewPage extends Page {

	private static final long serialVersionUID = 1L;
	private static final float res = 0.95f;

	private final JBTN back = new JBTN(MainLocale.PAGE, "back");
	protected final JBTN copy = new JBTN(MainLocale.PAGE, "copy");
	private final JList<String> jlt = new JList<>();
	private final JScrollPane jspt = new JScrollPane(jlt);
	private final JSlider jst = new JSlider(100, 900);
	private final JSlider jtl = new JSlider();
	private final JTG jtb = new JTG(MainLocale.PAGE, "pause");
	private final JBTN nex = new JBTN(MainLocale.PAGE, "nextf");
	private final JTG gif = new JTG(MainLocale.PAGE, "gif");
	private final JTG mp4 = new JTG(MainLocale.PAGE, "expmp4");
	private final JBTN png = new JBTN(MainLocale.PAGE, "png");
	protected final JBTN camres = new JBTN(MainLocale.PAGE, "rescam");
	protected final JTG larges = new JTG(MainLocale.PAGE, "larges");
	private final JLabel scale = new JLabel(MainLocale.getLoc(MainLocale.PAGE, "zoom"));
	private final JTF manualScale = new JTF();

	protected final ViewBox vb;

	private Loader loader = null;
	protected boolean pause;
	private boolean changingT;
	private boolean changingtl;
	private boolean focusOn = false;
	private final DecimalFormat df = new DecimalFormat("#.##");

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
		mp4.setEnabled(b);
		png.setEnabled(b && pause);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void exit() {
		if (CommonStatic.getConfig().performanceModeAnimation) {
			Timer.p = 1000 / 60;
		} else {
			Timer.p = 1000 / 30;
		}
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
		float d = (float) mwe.getPreciseWheelRotation();
		vb.resize((float) Math.pow(res, d));
	}

	protected void preini() {
		add(back);
		add(camres);
		add(copy);
		add((Canvas) vb);
		add(jspt);
		add(jst);
		add(jtb);
		add(jtl);
		add(nex);
		add(gif);
		add(png);
		add(mp4);
		add(scale);
		add(larges);
		add(manualScale);
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
		set(camres, x ,y, 600, 0, 200, 50);
		set(copy, x, y, 300, 0, 200, 50);
		set(larges, x, y , 900, 0, 200, 50);

		if (larges.isSelected()) {
			set((Canvas) vb, x, y, 500, 50, 1800, 1200);
			set(jspt, x, y, 100, 100, 300, 400);
			set(jtb, x, y, 25, 550, 200, 50);
			set(jtl, x, y, 0, 700, 500, 100);
			set(nex, x, y, 275, 550, 200, 50);
			set(png, x, y, 0, 650, 150, 50);
			set(gif, x, y, 175, 650, 150, 50);
			set(mp4, x, y, 350, 650, 150, 50);
			set(scale, x, y, 100, 50, 100, 50);
			set(manualScale, x, y, 200, 50, 150, 50);
			set(jst, x, y, 0, 0, 0, 0);
		} else {
			set((Canvas) vb, x, y, 1000, 100, 1000, 600);
			set(jspt, x, y, 400, 550, 300, 400);
			set(jst, x, y, 1000, 750, 1000, 100);
			set(jtl, x, y, 1000, 900, 1000, 100);
			set(jtb, x, y, 1300, 1050, 200, 50);
			set(nex, x, y, 1600, 1050, 200, 50);
			set(png, x, y, 1300, 1150, 200, 50);
			set(gif, x, y, 1600, 1150, 400, 50);
			set(mp4, x, y, 1800, 1050, 200, 50);
			set(scale, x, y, 1000, 50, 100, 50);
			set(manualScale, x, y, 1075, 50, 150, 50);
		}
	}

	protected <T extends Enum<T> & AnimI.AnimType<?, T>> void setAnim(AnimI<?, T> a) {
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
		vb.setEntity(a.getEAnim(a.types()[jlt.getSelectedIndex()]));
		jtl.setMinimum(0);

		if (CommonStatic.getConfig().performanceModeAnimation) {
			jtl.setMaximum(vb.getEnt().len() * 2);
		} else {
			jtl.setMaximum(vb.getEnt().len());
		}

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

		if (CommonStatic.getConfig().performanceModeAnimation) {
			Hashtable<Integer, JLabel> labels = new Hashtable<>();

			int f = 0;
			int gap;

			if (vb.getEnt().len() <= 50) {
				gap = 5;
			} else if (vb.getEnt().len() <= 200) {
				gap = 10;
			} else if (vb.getEnt().len() <= 1000) {
				gap = 50;
			} else if (vb.getEnt().len() <= 5000) {
				gap = 250;
			} else {
				gap = 1000;
			}

			while (f <= vb.getEnt().len()) {
				labels.put(f * 2, new JLabel(String.valueOf(f)));

				f += gap;
			}

			jtl.setLabelTable(labels);
		}
	}

	@Override
    public synchronized void onTimer(int t) {
		super.onTimer(t);

		if (!pause)
			eupdate();

		vb.paint();

		if (loader == null) {
			gif.setText(0, "gif");
			mp4.setText(0, "expmp4");
		} else {
			JTG alt = loader.mp4 ? mp4 : gif;
			alt.setText(loader.getProg());
			if (!alt.isSelected() && alt.isEnabled())
				(loader.mp4 ? gif : mp4).setEnabled(true);

			if (!gif.isSelected() && gif.isEnabled() && !mp4.isSelected() && mp4.isEnabled())
				loader = null;
		}

		if(!focusOn) {
			manualScale.setText(df.format(vb.getCtrl().siz * 100.0) + " %");
		}
	}

	protected abstract void updateChoice();

	private void addListener() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		camres.setLnr(x -> vb.resetPos());

		copy.addActionListener(arg0 -> {
			EAnimI ei = vb.getEnt();
			if (ei == null || !(ei.anim() instanceof AnimD))
				return;
			AnimD<?, ?> eau = (AnimD<?, ?>) ei.anim();
			ResourceLocation rl;

			if (eau.types[0].equals(AnimU.UType.SOUL))
				rl = new ResourceLocation(ResourceLocation.LOCAL, "new soul anim", Source.BasePath.SOUL);
			else
				rl = new ResourceLocation(ResourceLocation.LOCAL, "new anim", Source.BasePath.ANIM);

			Workspace.validate(rl);
			new AnimCE(rl, eau);
			changePanel(new ImgCutEditPage(getThis()));
		});

		jlt.addListSelectionListener(arg0 -> {
			if (arg0.getValueIsAdjusting())
				return;
			changingT = true;
			updateChoice();
			changingT = false;
		});

		jst.addChangeListener(arg0 -> {
			if (jst.getValueIsAdjusting())
				return;

			if (CommonStatic.getConfig().performanceModeAnimation) {
				Timer.p = jst.getValue() / 100 * 1000 / 60;
			} else {
				Timer.p = jst.getValue() / 100 * 1000 / 30;
			}
		});

		jtl.addChangeListener(arg0 -> {
			if (changingtl || !pause)
				return;

			if (vb.getEnt() != null) {
				if (CommonStatic.getConfig().performanceModeAnimation) {
					vb.getEnt().setTime(jtl.getValue() / 2f);
				} else {
					vb.getEnt().setTime(jtl.getValue());
				}
			}
		});

		jtb.addActionListener(arg0 -> {
			pause = jtb.isSelected();
			enabler(true);
		});

		nex.addActionListener(arg0 -> eupdate());

		png.addActionListener(arg0 -> {
			String str = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			File f = new File(CommonStatic.ctx.getBCUFolder(), "./img/" + str + ".png");
			BCUWriter.writeImage(vb.getPrev(), f);
		});

		gif.addActionListener(arg0 -> setLoader(false, gif.isSelected()));

		mp4.addActionListener(arg0 -> setLoader(true, mp4.isSelected()));

		larges.setLnr(x -> {
			remove((Canvas) vb);
			add((Canvas) vb);

			fireDimensionChanged();
		});

		manualScale.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				super.focusGained(e);

				focusOn = true;
			}

			@Override
			public void focusLost(FocusEvent e) {
				super.focusLost(e);

				String text = manualScale.getText();

				float value = CommonStatic.parseFloatN(text) / 100f;

				if(value > 0) {
					vb.getCtrl().siz = value;
				}

				focusOn = false;
			}
		});
	}

	private void setLoader(boolean mp4, boolean start) {
		if (start) {
			loader = vb.start(mp4);
			this.mp4.setEnabled(mp4);
			gif.setEnabled(!mp4);
		} else {
			vb.end(mp4 ? this.mp4 : gif);
			(mp4 ? gif : this.mp4).setEnabled(false);
		}
	}

	private void eupdate() {
		vb.update();
		changingtl = true;
		if (vb.getEnt() != null) {
			int selection;

			if (CommonStatic.getConfig().performanceModeAnimation) {
				selection = (int) (vb.getEnt().ind() * 2);
			} else {
				selection = (int) vb.getEnt().ind();
			}

			jtl.setValue(selection);
		}
		changingtl = false;
	}

}
