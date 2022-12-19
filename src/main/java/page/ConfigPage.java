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
	private final JCB prel = new JCB(MainLocale.PAGE, "preload");
	private final JCB whit = new JCB(MainLocale.PAGE, "white");
	private final JCB refe = new JCB(MainLocale.PAGE, "axis");
	private final JCB jogl = new JCB(MainLocale.PAGE, "JOGL");
	private final JTG exla = new JTG(MainLocale.PAGE, "exlang");
	private final JTG extt = new JTG(MainLocale.PAGE, "extip");
	private final JBTN secs = new JBTN(MainLocale.PAGE, MainBCU.seconds ? "secs" : "frame");
	private final JCB musc = new JCB(MainLocale.PAGE, "musc");
	private final JCB jcsnd = new JCB(MainLocale.PAGE, "btnsnd");
	private final JCB jceff = new JCB(MainLocale.PAGE, "bgeff");
	private final JCB jcdly = new JCB(MainLocale.PAGE, "btdly");
	private final JCB stdis = new JCB(MainLocale.PAGE, "stdis");
	private final JL preflv = new JL(MainLocale.PAGE, "preflv");
	private final JCB shake = new JCB(MainLocale.PAGE, "shake");
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
	private final JL jlla = new JL(MainLocale.PAGE, "lang");
	private final JL jlfi = new JL(MainLocale.PAGE, "filter");
	private final JL jlti = new JL(MainLocale.PAGE, "meastime");
	private final JL jlly = new JL(MainLocale.PAGE, "layout");
	private final JL jlth = new JL(MainLocale.PAGE, "theme");
	private final JL jlga = new JL(MainLocale.PAGE, "gameplay");
	private final JL jlot = new JL(MainLocale.PAGE, "other");
	private final JL jlre = new JL(MainLocale.PAGE, "render");
	private final JL mbac = new JL(MainLocale.PAGE, "maxback");
	private final JCB jcbac = new JCB(MainLocale.PAGE, "jcbac");
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
	private final JBTN vres = new JBTN(MainLocale.PAGE, "viewreset");
	private final JCB excont = new JCB(MainLocale.PAGE, "excont");
	private final JL autosave = new JL(MainLocale.PAGE, "autosave");
	private final JTF savetime = new JTF(MainBCU.autoSaveTime > 0 ? MainBCU.autoSaveTime + "min" : "deactivated");

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

		set(jlre, x, y, 50, 100, 300, 50);
		set(jogl, x, y, 50, 150, 300, 50);
		set(whit, x, y, 50, 200, 300, 50);
		set(refe, x, y, 50, 250, 300, 50);
		set(prel, x, y, 50, 300, 300, 50);

		for (int i = 0; i < 4; i++) {
			set(name[i], x, y, 350, 100 + i * 75, 200, 50);
			set(left[i], x, y, 575, 100 + i * 75, 75, 50);
			set(vals[i], x, y, 650, 100 + i * 75, 200, 50);
			set(right[i], x, y, 850, 100 + i * 75, 75, 50);
		}

		set(jlmin, x, y, 50, 425, 300, 50);
		set(jsmin, x, y, 350, 425, 750, 75);
		set(jlmax, x, y, 50, 500, 300, 50);
		set(jsmax, x, y, 350, 500, 750, 75);
		set(jlbg, x, y, 50, 575, 300, 50);
		set(jsbg, x, y, 350, 575, 750, 75);
		set(jlse, x, y, 50, 650, 300, 50);
		set(jsse, x, y, 350, 650, 750, 75);
		set(jlui, x, y, 50, 725, 300, 50);
		set(jsui, x, y, 350, 725, 750, 75);
		set(mbac, x, y, 50, 800, 300, 50);
		set(jsba, x, y, 350, 800, 750, 75);

		set(jlga, x, y, 50, 900, 300, 50);
		set(musc, x, y, 50, 950, 300, 50);
		set(jceff, x, y, 50, 1000, 300, 50);
		set(jcdly, x, y, 50, 1050, 300, 50);
		set(stdis, x, y, 50, 1100, 300, 50);
		set(excont, x, y, 50, 1150, 300, 50);
		set(shake, x, y, 50, 1200, 300, 50);

		set(jlot, x, y, 350, 900, 300, 50);
		set(jcbac, x, y, 350, 950, 300, 50);
		set(jcsnd, x, y, 350, 1000, 300, 50);

		set(jlfi, x, y, 1225, 100, 200, 50);
		set(filt, x, y, 1425, 100, 200, 50);
		set(jlti, x, y, 1225, 175, 200, 50);
		set(secs, x, y, 1425, 175, 200, 50);
		set(jlly, x, y, 1225, 250, 200, 50);
		set(row, x, y, 1425, 250, 200, 50);
		set(jlth, x, y, 1225, 325, 200, 50);
		set(nimbus, x, y, 1425, 325, 200, 50);
		set(theme, x, y, 1425, 400, 200, 50);

		set(preflv, x, y, 1225, 475, 200, 50);
		set(prlvmd, x, y, 1425, 475, 200, 50);
		set(autosave, x, y, 1225, 550, 200, 50);
		set(savetime, x, y, 1425, 550, 200, 50);

		set(rlpk, x, y, 1225, 625, 400, 50);
		set(vcol, x, y, 1225, 700, 400, 50);
		set(vres, x, y, 1225, 775, 400, 50);

		set(jlla, x, y, 1750, 100, 300, 50);
		set(jsps, x, y, 1750, 150, 300, 300);
		set(exla, x, y, 1750, 475, 300, 50);
		set(extt, x, y, 1750, 550, 300, 50);
		set(rlla, x, y, 1750, 625, 300, 50);
	}

	@Override
	public void callBack(Object obj) {
		super.callBack(obj);

		if(obj instanceof ColorPicker) {
			int rgb = ((ColorPicker) obj).rgb[0];
			rgb = (rgb << 8) + ((ColorPicker) obj).rgb[1];
			rgb = (rgb << 8) + ((ColorPicker) obj).rgb[2];

			cfg().viewerColor = rgb;
		}
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		secs.addActionListener(arg0 -> {
			MainBCU.seconds = !MainBCU.seconds;
			secs.setText(0, MainBCU.seconds ? "secs" : "frame");
		});

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

		jcbac.addActionListener(arg0 -> {
			if (CommonStatic.getConfig().maxBackup != -1) {
				if (Opts.conf(get(MainLocale.PAGE, "nobacwarn"))) {
					CommonStatic.getConfig().maxBackup = -1;
					jsba.setEnabled(false);
				} else {
					jcbac.setSelected(true);
				}
			} else {
				jsba.setEnabled(true);
				jsba.setValue(Backup.backups.size());
			}
		});

		jsba.addChangeListener(arg0 -> {
			if(!jsba.getValueIsAdjusting()) {
				int back = Backup.backups.size();
				int pre = CommonStatic.getConfig().maxBackup;

				if(pre >= back && back > jsba.getValue() && jsba.getValue() > 0) {
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
			MainBCU.FILTER_TYPE = (byte) (1 - MainBCU.FILTER_TYPE);
			filt.setText(0, "filter" + MainBCU.FILTER_TYPE);
		});

		musc.addActionListener(arg0 -> {
			BCMusic.play = musc.isSelected();
			jsbg.setEnabled(BCMusic.play);
			jsse.setEnabled(BCMusic.play);
			jcsnd.setEnabled(BCMusic.play);
		});

		nimbus.setLnr((b) -> {
			if (Opts.conf("This requires restart to apply. Do you want to restart?"+(!MainBCU.nimbus ? "\n\nWarning : Using Nimbus theme may result in high CPU usage" : ""))) {
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

		jcsnd.addActionListener(a -> {
			MainBCU.buttonSound = jcsnd.isSelected();
			if(MainBCU.buttonSound)
				BCMusic.clickSound();
		});

		jcdly.addActionListener(a -> CommonStatic.getConfig().buttonDelay = !CommonStatic.getConfig().buttonDelay);

		stdis.addActionListener(a -> CommonStatic.getConfig().stageName = !CommonStatic.getConfig().stageName);

		jceff.addActionListener(a -> CommonStatic.getConfig().drawBGEffect = !CommonStatic.getConfig().drawBGEffect);

		rlpk.addActionListener(l -> UserProfile.reloadExternalPacks());

		vcol.addActionListener(l -> {
			if(CommonStatic.getConfig().viewerColor != -1)
				Opts.showColorPicker("Color pick pick", this, CommonStatic.getConfig().viewerColor);
			else
				Opts.showColorPicker("Color pick pick", this);
		});

		vres.addActionListener(l -> CommonStatic.getConfig().viewerColor = -1);

		excont.addActionListener(l -> CommonStatic.getConfig().exContinuation = excont.isSelected());

		savetime.setLnr(c -> {
			int time = CommonStatic.parseIntN(savetime.getText());
			boolean eq = time != -1 && time != MainBCU.autoSaveTime;

			savetime.setText(time > 0 ? time + "min" : "deactivated");
			if (eq) {
				MainBCU.autoSaveTime = time;
				MainBCU.restartAutoSaveTimer();
			}
		});

		shake.addActionListener(c -> CommonStatic.getConfig().shake = shake.isSelected());
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
		add(jlla);
		add(jlfi);
		add(jlti);
		add(jlly);
		add(jlth);
		add(filt);
		add(jlre);
		add(jlga);
		add(jlot);
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
		add(jcsnd);
		add(jceff);
		add(jcdly);
		add(stdis);
		add(rlpk);
		add(vcol);
		add(vres);
		add(excont);
		add(autosave);
		add(savetime);
		add(jcbac);
		add(shake);
		excont.setSelected(CommonStatic.getConfig().exContinuation);
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
		prel.setSelected(MainBCU.preload);
		whit.setSelected(ViewBox.Conf.white);
		refe.setSelected(cfg().ref);
		musc.setSelected(BCMusic.play);
		jsbg.setEnabled(BCMusic.play);
		jsse.setEnabled(BCMusic.play);
		jcsnd.setEnabled(BCMusic.play);
		jcsnd.setSelected(MainBCU.buttonSound);
		jogl.setSelected(MainBCU.USE_JOGL);
		jcbac.setSelected(CommonStatic.getConfig().maxBackup != -1);
		if (CommonStatic.getConfig().maxBackup != -1)
			jsba.setValue(CommonStatic.getConfig().maxBackup);
		else
			jsba.setEnabled(false);
		jceff.setSelected(CommonStatic.getConfig().drawBGEffect);
		jcdly.setSelected(CommonStatic.getConfig().buttonDelay);
		stdis.setSelected(CommonStatic.getConfig().stageName);
		if (!MainBCU.nimbus) {
			theme.setEnabled(false);
		}
		shake.setSelected(CommonStatic.getConfig().shake);
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
