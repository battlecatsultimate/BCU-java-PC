package page;

import common.CommonStatic;
import common.CommonStatic.Config;
import common.io.Backup;
import common.pack.UserProfile;
import common.util.ImgCore;
import common.util.lang.MultiLangCont;
import io.BCMusic;
import io.BCUReader;
import main.MainBCU;
import main.Opts;
import page.support.ColorPicker;
import page.view.ViewBox;
import utilpc.Theme;

import javax.swing.*;
import java.awt.*;

public class ConfigPage extends Page {

	private static final long serialVersionUID = 1L;

	private static Config cfg() {
		return CommonStatic.getConfig();
	}

	private final JBTN back = new JBTN(MainLocale.PAGE, "back");
	private final JBTN filt = new JBTN(MainLocale.PAGE, "filter" + MainBCU.FILTER_TYPE);
	private final JBTN rlla = new JBTN(MainLocale.PAGE, "rllang");
	private final JBTN rlpk = new JBTN(MainLocale.PAGE, "rlpks");
	private final JTG prel = new JTG(MainLocale.PAGE, "preload");
	private final JTG whit = new JTG(MainLocale.PAGE, "white");
	private final JTG refe = new JTG(MainLocale.PAGE, "axis");
	private final JTG jogl = new JTG(MainLocale.PAGE, "JOGL");
	private final JTG musc = new JTG(MainLocale.PAGE, "musc");
	private final JTG exla = new JTG(MainLocale.PAGE, "exlang");
	private final JTG extt = new JTG(MainLocale.PAGE, "extip");
	private final JTG secs = new JTG(MainLocale.PAGE, "secs");
	private final JTG btnsnd = new JTG(MainLocale.PAGE, "btnsnd");
	private final JTG bgeff = new JTG(MainLocale.PAGE, "bgeff");
	private final JTG btdly = new JTG(MainLocale.PAGE, "btdly");
	private final JL preflv = new JL(MainLocale.PAGE, "preflv");
	private final JTF prlvmd = new JTF();
	private final JBTN[] left = new JBTN[4];
	private final JBTN[] right = new JBTN[4];
	private final JL[] name = new JL[4];
	private final JL[] vals = new JL[4];
	private final JL jlmin = new JL(MainLocale.PAGE, "opamin");
	private final JL jlmax = new JL(MainLocale.PAGE, "opamax");
	private final JL jlbg = new JL(MainLocale.PAGE, "BGvol");
	private final JL jlse = new JL(MainLocale.PAGE, "SEvol");
	private final JL jlui = new JL(MainLocale.PAGE, "UIvol");
	private final JL mbac = new JL(MainLocale.PAGE, "maxback");
	private final JBTN theme = new JBTN(MainLocale.PAGE, MainBCU.light ? "themel" : "themed");
	private final JBTN nimbus = new JBTN(MainLocale.PAGE, MainBCU.nimbus ? "nimbus" : "tdefault");
	private final JSlider jsmin = new JSlider(0, 100);
	private final JSlider jsmax = new JSlider(0, 100);
	private final JSlider jsbg = new JSlider(0, 100);
	private final JSlider jsse = new JSlider(0, 100);
	private final JSlider jsui = new JSlider(0, 100);
	private final JSlider jsba = new JSlider(0, 50);
	private final JList<String> jls = new JList<>(MainLocale.LOC_NAME);
	private final JBTN row = new JBTN(MainLocale.PAGE, CommonStatic.getConfig().twoRow ? "tworow" : "onerow");
	private final JBTN vcol = new JBTN(MainLocale.PAGE, "viewcolor");

	private final JScrollPane jsps = new JScrollPane(jls);

	private boolean changing = false;

	protected ConfigPage(Page p) {
		super(p);

		ini();
		resized();
	}

	@Override
	protected void renew() {
		jlmin.setText(0, "opamin");
		jlmax.setText(0, "opamax");
		for (int i = 0; i < 4; i++) {
			name[i].setText(0, ImgCore.NAME[i]);
			vals[i].setText(0, ImgCore.VAL[cfg().ints[i]]);
		}
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jogl, x, y, 50, 100, 200, 50);
		set(prel, x, y, 50, 200, 200, 50);
		set(whit, x, y, 50, 300, 200, 50);
		set(refe, x, y, 50, 400, 200, 50);
		for (int i = 0; i < 4; i++) {
			set(name[i], x, y, 300, 100 + i * 100, 200, 50);
			set(left[i], x, y, 550, 100 + i * 100, 100, 50);
			set(vals[i], x, y, 650, 100 + i * 100, 200, 50);
			set(right[i], x, y, 850, 100 + i * 100, 100, 50);
		}
		set(jsps, x, y, 1100, 100, 200, 400);
		set(jlmin, x, y, 50, 500, 400, 50);
		set(jsmin, x, y, 50, 550, 1000, 100);
		set(jlmax, x, y, 50, 650, 400, 50);
		set(jsmax, x, y, 50, 700, 1000, 100);
		set(jlbg, x, y, 50, 800, 400, 50);
		set(jsbg, x, y, 50, 850, 1000, 100);
		set(jlse, x, y, 50, 950, 400, 50);
		set(jsse, x, y, 50, 1000, 1000, 100);
		set(jlui, x, y, 50, 1100, 400, 50);
		set(jsui, x, y, 50, 1150, 1000, 100);
		set(filt, x, y, 1100, 550, 200, 50);
		set(musc, x, y, 1350, 550, 200, 50);
		set(exla, x, y, 1100, 625, 450, 50);
		set(extt, x, y, 1100, 700, 450, 50);
		set(rlla, x, y, 1100, 775, 450, 50);
		set(nimbus, x, y, 1100, 850, 200, 50);
		set(theme, x, y, 1350, 850, 200, 50);
		set(row, x, y, 1100, 925, 450, 50);
		set(secs, x, y, 1100, 1000, 450, 50);
		set(preflv, x, y, 1600, 550, 200, 50);
		set(prlvmd, x, y, 1800, 550, 250, 50);
		set(mbac, x, y, 1100, 1100, 400, 50);
		set(jsba, x, y, 1100, 1150, 1000, 100);
		set(btnsnd, x, y, 1600, 625, 200, 50);
		set(bgeff, x, y, 1850, 625, 200, 50);
		set(btdly, x, y, 1600, 700, 450, 50);
		set(rlpk, x, y, 1600, 775, 200, 50);
		set(vcol, x, y, 1850, 775, 200, 50);
	}

	@Override
	public void callBack(Object obj) {
		super.callBack(obj);
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		secs.addActionListener(arg0 -> MainBCU.seconds = secs.isSelected());

		prel.addActionListener(arg0 -> MainBCU.preload = prel.isSelected());

		exla.addActionListener(arg0 -> {
			MainLocale.exLang = exla.isSelected();
			Page.renewLoc(getThis());
		});

		extt.addActionListener(arg0 -> {
			MainLocale.exTTT = extt.isSelected();
			Page.renewLoc(getThis());
		});

		rlla.addActionListener(arg0 -> {
			MultiLangCont.getStatic().clear();
			BCUReader.readLang();
			Page.renewLoc(getThis());
		});

		whit.addActionListener(arg0 -> ViewBox.Conf.white = whit.isSelected());

		refe.addActionListener(arg0 -> cfg().ref = refe.isSelected());

		jogl.addActionListener(arg0 -> {
			if (Opts.conf("This requires restart to apply. Do you want to restart?")) {
				MainBCU.USE_JOGL = jogl.isSelected();
				changePanel(new SavePage());
			} else
				jogl.setSelected(MainBCU.USE_JOGL);
		});

		for (int i = 0; i < 4; i++) {
			int I = i;

			left[i].addActionListener(arg0 -> {
				cfg().ints[I]--;
				vals[I].setText(0, ImgCore.VAL[cfg().ints[I]]);
				left[I].setEnabled(cfg().ints[I] > 0);
				right[I].setEnabled(cfg().ints[I] < 2);
			});

			right[i].addActionListener(arg0 -> {
				cfg().ints[I]++;
				vals[I].setText(0, ImgCore.VAL[cfg().ints[I]]);
				left[I].setEnabled(cfg().ints[I] > 0);
				right[I].setEnabled(cfg().ints[I] < 2);
			});

		}

		jsmin.addChangeListener(arg0 -> cfg().deadOpa = jsmin.getValue());

		jsmax.addChangeListener(arg0 -> cfg().fullOpa = jsmax.getValue());

		jsbg.addChangeListener(arg0 -> BCMusic.setBGVol(jsbg.getValue()));

		jsse.addChangeListener(arg0 -> BCMusic.setSEVol(jsse.getValue()));

		jsui.addChangeListener(arg0 -> BCMusic.setUIVol(jsui.getValue()));

		jsba.addChangeListener(arg0 -> {
			if(!jsba.getValueIsAdjusting()) {
				int back = Backup.backups.size();
				int pre = CommonStatic.getConfig().maxBackup;

				if(pre >= back && back > jsba.getValue() && jsba.getValue() != 0) {
					if(Opts.conf((back-jsba.getValue())+" "+get(MainLocale.PAGE, "backremwarn"))) {
						CommonStatic.getConfig().maxBackup = jsba.getValue();
					} else {
						jsba.setValue(CommonStatic.getConfig().maxBackup);
					}
				} else if(jsba.getValue() == 0) {
					if(Opts.conf((get(MainLocale.PAGE, "backinfwarn")))) {
						CommonStatic.getConfig().maxBackup = jsba.getValue();
					} else {
						jsba.setValue(CommonStatic.getConfig().maxBackup);
					}
				} else {
					CommonStatic.getConfig().maxBackup = jsba.getValue();
				}
			}
		});

		jls.addListSelectionListener(arg0 -> {
			if (changing)
				return;
			changing = true;
			if (jls.getSelectedIndex() == -1) {
				jls.setSelectedIndex(localeIndexOf(cfg().lang));
			}
			cfg().lang = MainLocale.LOC_INDEX[jls.getSelectedIndex()];
			Page.renewLoc(getThis());
			changing = false;
		});

		filt.addActionListener(e -> {
			MainBCU.FILTER_TYPE = 1 - MainBCU.FILTER_TYPE;
			filt.setText(0, "filter" + MainBCU.FILTER_TYPE);
		});

		musc.addActionListener(arg0 -> BCMusic.play = musc.isSelected());

		nimbus.setLnr((b) -> {
			if (Opts.conf("This requires restart to apply. Do you want to restart?"+(MainBCU.nimbus ? "\n\nWarning : Using Nimbus theme may result in high CPU usage" : ""))) {
				MainBCU.nimbus = !MainBCU.nimbus;
				changePanel(new SavePage());
			}
		});

		theme.setLnr((b) -> {
			MainBCU.light = !MainBCU.light;

			if (MainBCU.light) {
				theme.setText(MainLocale.getLoc(MainLocale.PAGE, "themel"));
				Theme.LIGHT.setTheme();
				Page.BGCOLOR = new Color(255, 255, 255);
				setBackground(BGCOLOR);
				SwingUtilities.updateComponentTreeUI(this);
				theme.setToolTipText(MainLocale.getLoc(MainLocale.PAGE, "themel"));
			} else {
				theme.setText(MainLocale.getLoc(MainLocale.PAGE, "themed"));
				Theme.DARK.setTheme();
				Page.BGCOLOR = new Color(40, 40, 40);
				setBackground(BGCOLOR);
				SwingUtilities.updateComponentTreeUI(this);
				theme.setToolTipText(MainLocale.getLoc(MainLocale.PAGE, "themed"));
			}
		});

		row.addActionListener(a -> {
			CommonStatic.getConfig().twoRow = !CommonStatic.getConfig().twoRow;
			row.setText(MainLocale.PAGE, CommonStatic.getConfig().twoRow ? "tworow" : "onerow");
		});

		btnsnd.addActionListener(a -> {
			MainBCU.buttonSound = !MainBCU.buttonSound;
			if(MainBCU.buttonSound)
				BCMusic.clickSound();
		});

		btdly.addActionListener(a -> CommonStatic.getConfig().buttonDelay = !CommonStatic.getConfig().buttonDelay);

		bgeff.addActionListener(a -> CommonStatic.getConfig().drawBGEffect = !CommonStatic.getConfig().drawBGEffect);

		rlpk.addActionListener(l -> UserProfile.reloadExternalPacks());

		vcol.addActionListener(l -> Opts.showColorPicker("Color pick pick", this));
	}

	private void ini() {
		add(back);
		add(jogl);
		add(prel);
		add(refe);
		add(whit);
		add(jsps);
		set(jsmin);
		set(jsmax);
		add(jlmin);
		add(jlmax);
		add(filt);
		add(musc);
		add(rlla);
		add(exla);
		add(extt);
		add(jlbg);
		add(jlse);
		add(jlui);
		set(jsbg);
		set(jsse);
		set(jsui);
		add(nimbus);
		add(theme);
		add(row);
		add(secs);
		add(preflv);
		add(prlvmd);
		set(prlvmd);
		set(jsba);
		add(mbac);
		add(btnsnd);
		add(bgeff);
		add(btdly);
		add(rlpk);
		//add(vcol);
		prlvmd.setText("" + CommonStatic.getConfig().prefLevel);
		jls.setSelectedIndex(localeIndexOf(cfg().lang));
		jsmin.setValue(cfg().deadOpa);
		jsmax.setValue(cfg().fullOpa);
		jsbg.setValue(BCMusic.VOL_BG);
		jsse.setValue(BCMusic.VOL_SE);
		jsui.setValue(BCMusic.VOL_UI);
		for (int i = 0; i < 4; i++) {
			left[i] = new JBTN("<");
			right[i] = new JBTN(">");
			name[i] = new JL(0, ImgCore.NAME[i]);
			vals[i] = new JL(0, ImgCore.VAL[cfg().ints[i]]);
			add(left[i]);
			add(right[i]);
			add(name[i]);
			add(vals[i]);
			name[i].setHorizontalAlignment(SwingConstants.CENTER);
			vals[i].setHorizontalAlignment(SwingConstants.CENTER);
			name[i].setBorder(BorderFactory.createEtchedBorder());
			vals[i].setBorder(BorderFactory.createEtchedBorder());
			left[i].setEnabled(cfg().ints[i] > 0);
			right[i].setEnabled(cfg().ints[i] < 2);
		}
		exla.setSelected(MainLocale.exLang);
		extt.setSelected(MainLocale.exTTT);
		secs.setSelected(MainBCU.seconds);
		prel.setSelected(MainBCU.preload);
		whit.setSelected(ViewBox.Conf.white);
		refe.setSelected(cfg().ref);
		musc.setSelected(BCMusic.play);
		jogl.setSelected(MainBCU.USE_JOGL);
		btnsnd.setSelected(MainBCU.buttonSound);
		jsba.setValue(CommonStatic.getConfig().maxBackup);
		bgeff.setSelected(CommonStatic.getConfig().drawBGEffect);
		btdly.setSelected(CommonStatic.getConfig().buttonDelay);
		if (!MainBCU.nimbus) {
			theme.setEnabled(false);
		}
		addListeners();
	}

	protected void set(JTF jtf) {
		jtf.setLnr(e -> {
			String text = jtf.getText().trim();
			if (text.length() > 0) {
				int[] v = CommonStatic.parseIntsN(text);
				CommonStatic.getConfig().prefLevel = Math.max(1, v[0]);
				jtf.setText("" + CommonStatic.getConfig().prefLevel);
			}
		});
	}

	private void set(JSlider sl) {
		add(sl);
		sl.setMajorTickSpacing(10);
		sl.setMinorTickSpacing(5);
		sl.setPaintTicks(true);
		sl.setPaintLabels(true);
	}

	private int localeIndexOf(int elem) {
		for(int i = 0; i < MainLocale.LOC_INDEX.length; i++) {
			if(MainLocale.LOC_INDEX[i] == elem)
				return i;
		}

		return -1;
	}
}
