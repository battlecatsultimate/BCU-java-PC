package main;

import common.CommonStatic;
import common.util.Data;
import common.util.lang.MultiLangCont;
import common.util.stage.MapColc;
import common.util.stage.Stage;
import common.util.stage.StageMap;
import page.*;
import page.battle.BattleInfoPage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Opts {

	public static final int MEMORY = 1001, SECTY = 1002, REQITN = 1003, INSTALL = 1004;

	private static boolean nshowi, nshowu;
	private static boolean popped = false;

	public static void animErr(String f) {
		if (nshowi)
			return;
		nshowi = !warning("error in reading file " + f + ", Click Cancel to supress this popup?", "IO error");
	}

	public static void backupErr(String t) {
		pop("failed to " + t + " backup", "backup access error");
	}

	public static boolean conf() {
		return warning(Page.get(MainLocale.PAGE, "w0"), "confirmation");
	}

	public static boolean conf(File f) {
		return warning("Are you sure that you want to delete "+f.getName()+"? This can't be undone", "Confirmation");
	}

	public static boolean conf(String text) {
		return warning(text, "confirmation");
	}

	public static void dloadErr(String text) {
		pop("failed to download " + text, "download error");
	}

	public static void ioErr(String text) {
		pop(text, "IO error");
	}

	public static void loadErr(String text) {
		pop(text, "loading error");
	}

	public static boolean packConf(String text) {
		return warning(text, "pack conflict");
	}

	public static void pop(int id, String... is) {
		if (id == MEMORY)
			pop("not enough memory. Current memory: " + is[0] + "MB.", "not enough memory");
		if (id == SECTY)
			pop("Failed to access files. Please move BCU folder to another place", "file permission error");
		if (id == REQITN)
			pop("failed to connect to internet while download is necessary", "download error");
		if (id == INSTALL)
			pop("<html>BCU library is not properly installed.<br>Download and run BCU-Installer to install BCU library</html>",
					"library not installed");
	}

	public static void pop(String text, String title) {
		int opt = JOptionPane.PLAIN_MESSAGE;
		JOptionPane.showMessageDialog(null, text, title, opt);
	}

	public static void errOnce(String text, String title, boolean fatal) {
		if(popped)
			return;

		popped = true;

		int opt = JOptionPane.DEFAULT_OPTION;
		int result = JOptionPane.showOptionDialog(null, text, title, opt, JOptionPane.INFORMATION_MESSAGE, null, null, null);

		if(result == JOptionPane.OK_OPTION || result == JOptionPane.NO_OPTION) {
			if(fatal)
				CommonStatic.def.save(false, true);
			popped = false;
		}
	}

	public static void warnPop(String text, String title) {
		int opt = JOptionPane.WARNING_MESSAGE;
		JOptionPane.showMessageDialog(null, text, title, opt);
	}

	public static String read(String string) {
		return JOptionPane.showInputDialog(null, string, "");
	}

	public static void recdErr(String name, String suf) {
		pop("replay " + name + " uses unavailable " + suf, "replay read error");
	}

	public static void servErr(String text) {
		pop(text, "server error");
	}

	public static void success(String text) {
		pop(text, "success");
	}

	public static void unitErr(String f) {
		if (nshowu)
			return;
		nshowu = !warning(f + ", Click Cancel to supress this popup?", "can't find unit");
	}

	public static boolean updateCheck(String s, String p) {
		return warning(s + " update available. do you want to update? " + p, "update check");
	}

	public static void verErr(String o, String v) {
		pop(o + " version is too old, use BCU " + v + " or " + (o.equals("BCU") ? "newer" : "older")
				+ " version to open it", "version error");
	}

	public static boolean writeErr0(String f) {
		return Opts.warning("failed to write file: " + f + " do you want to retry?", "IO error");
	}

	public static boolean writeErr1(String f) {
		return Opts.warning("failed to write file: " + f + " do you want to save it in another place?", "IO error");
	}

	private static boolean warning(String text, String title) {
		int opt = JOptionPane.OK_CANCEL_OPTION;
		int val = JOptionPane.showConfirmDialog(null, text, title, opt);
		return val == JOptionPane.OK_OPTION;
	}

	public static int selection(String message, String title, String ...choices) {
		return JOptionPane.showOptionDialog(null, message, title,
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[0]);
	}

	public static boolean[] confirmSave() {
		JLabel jl = new JLabel(Page.get(MainLocale.PAGE, "savwarn"));
		JCheckBox check = new JCheckBox(Page.get(MainLocale.PAGE, "genbckp"));
		check.setSelected(true);

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(jl);
		//panel.add(check); //TODO - Optional Backup generation

		int choice = JOptionPane.showConfirmDialog(null, panel, Page.get(MainLocale.PAGE, "savconf"), JOptionPane.YES_NO_CANCEL_OPTION);

		if (choice == JOptionPane.CLOSED_OPTION || choice == JOptionPane.CANCEL_OPTION)
			return null;

		return new boolean[]{choice == JOptionPane.YES_OPTION, check.isSelected()};
	}

	public static Object[] showTextCheck(String title, String content, boolean defaultCheck) {
		JLabel contents = new JLabel(content);
		JTF text = new JTF();
		JCheckBox check = new JCheckBox("Allow users to copy animation without password");
		JCheckBox parentCheck = new JCheckBox("Allow users to set this pack as parent pack");
		JTF parentText = new JTF();

		parentCheck.setSelected(true);
		parentText.setEnabled(!parentCheck.isSelected());

		check.addItemListener(e -> text.setEnabled(e.getStateChange() != ItemEvent.SELECTED));
		parentCheck.addItemListener(e -> parentText.setEnabled(e.getStateChange() != ItemEvent.SELECTED));

		Border b = text.getBorder();
		Border e = new EmptyBorder(8, 0, 8 ,0);
		if(b == null) {
			text.setBorder(e);
		} else {
			text.setBorder(new CompoundBorder(e, b));
		}

		b = check.getBorder();
		e = new EmptyBorder(0, 0, 8, 0);

		if(b == null) {
			check.setBorder(e);
		} else {
			check.setBorder(new CompoundBorder(e, b));
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(contents);
		panel.add(text);
		panel.add(check);
		panel.add(parentCheck);
		panel.add(parentText);

		check.setSelected(defaultCheck);

		int result = JOptionPane.showConfirmDialog(null, panel, title, JOptionPane.OK_CANCEL_OPTION);

		if(result == JOptionPane.OK_OPTION) {
			Object[] res = new Object[3];

			res[0] = text.getText();
			res[1] = check.isSelected();
			res[2] = !parentCheck.isSelected() ? parentText.getText() : null;

			return res;
		} else {
			return null;
		}
	}

	public static void popAgreement(String title, String text) {
		Icon warnIcon = UIManager.getIcon("OptionPane.warningIcon");
		JLabel content = new JLabel(text);
		JBTN okay = new JBTN("OK");
		JBTN cancel = new JBTN("Cancel");
		JCheckBox agree = new JCheckBox();

		Border b = content.getBorder();
		Border eb = new EmptyBorder(8, 0, 8, 0);

		if(b == null) {
			content.setBorder(eb);
		} else {
			content.setBorder(new CompoundBorder(eb, b));
		}

		okay.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(okay);

			MainBCU.announce0510 = true;
		});

		okay.setEnabled(false);

		cancel.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(cancel);

			CommonStatic.def.save(false, true);
		});

		agree.setText("I read this warning and agree to proceed");

		agree.addActionListener(a -> okay.setEnabled(agree.isSelected()));

		Border cb = content.getBorder();
		Border ecb = new EmptyBorder(0, 0, 8, 0);

		if(cb == null) {
			agree.setBorder(ecb);
		} else {
			agree.setBorder(new CompoundBorder(ecb, cb));
		}

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(content);
		panel.add(agree);

		JOptionPane.showOptionDialog(
				null,
				panel,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				warnIcon,
				new Object[] {okay, cancel},
				cancel
		);
	}

	@SuppressWarnings("MagicConstant")
	public static void showColorPicker(String title, Page pg, int... rgb) {
		ColorPickPage p = new ColorPickPage(pg);

		if(rgb.length == 3) {
			int c = Math.max(0, Math.min(255, rgb[0]));
			c = (c << 8) + Math.max(0, Math.min(255, rgb[1]));
			c = (c << 8) + Math.max(0, Math.min(255, rgb[2]));

			p.picker.setHex(c);
		} else if(rgb.length == 1) {
			p.picker.setHex(Math.max(0, Math.min(0xFFFFFF, rgb[0])));
		}

		Runnable run = new Runnable() {
			public final int fps = 33;
			public int inter = 0;

			@SuppressWarnings("BusyWait")
			@Override
			public void run() {
				while (true) {
					long m = System.currentTimeMillis();
					try {
						p.timer(0);
						int delay = (int) (System.currentTimeMillis() - m);
						inter = (inter * 9 + 100 * delay / fps) / 10;
						int sle = delay >= fps ? 1 : fps - delay;
						Thread.sleep(sle);
					} catch (InterruptedException e) {
						return;
					}
				}
			}
		};

		Thread thread = new Thread(run);

		thread.start();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		int w = MainFrame.F.getRootPane().getWidth();
		int h = MainFrame.F.getRootPane().getHeight();

		p.setPreferredSize(new Dimension((int) (w * 0.524), (int) (h * 0.3)));
		p.setBounds(0, 0, (int) (w * 0.525), (int) (h * 0.3));

		panel.add(p);
		panel.setPreferredSize(new Dimension((int) (w * 0.524), (int) (h * 0.368)));

		panel.setBackground(new Color(64, 64, 64));

		JBTN okay = new JBTN("OK");
		JBTN cancel = new JBTN("Cancel");

		okay.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(okay);

			pg.callBack(p.picker);
			thread.interrupt();
		});

		cancel.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(cancel);
		});

		JOptionPane.showOptionDialog(
				null,
				panel,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				-1,
				null,
				new Object[]{okay, cancel},
				null
		);
	}

	@SuppressWarnings("MagicConstant")
	public static void showExStageSelection(String title, String content, Stage s, BattleInfoPage bp) {
		if(s.info == null || !(s.info.exConnection || s.info.exStages != null))
			throw new IllegalStateException("This stage doesn't have EX stage");

		JLabel contents = new JLabel(content);

		//Gather EX stages
		List<Stage> exStages = new ArrayList<>();

		if(s.info.exConnection) {
			StageMap sm = MapColc.DefMapColc.getMap(s.info.exMapID + 4000);

			if(sm != null) {
				for(int i = s.info.exStageIDMin; i <= s.info.exStageIDMax; i++) {
					Stage st = sm.list.get(i);

					if(st != null)
						exStages.add(st);
				}
			}
		}

		if(s.info.exStages != null) {
			for(Stage st : s.info.exStages) {
				if(st != null)
					exStages.add(st);
			}
		}

		AtomicInteger selection = new AtomicInteger();
		ButtonGroup bg = new ButtonGroup();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		panel.add(contents);

		for(int i = 0; i < exStages.size(); i++) {
			Stage e = exStages.get(i);

			String map = MultiLangCont.get(e.getCont());

			if(map == null || map.isEmpty()) {
				map = e.getCont().getSID()+"/"+ Data.trio(e.getCont().id.id);
			}

			String stage = MultiLangCont.get(e);

			if(stage == null || stage.isEmpty()) {
				stage = Data.trio(e.id.id);
			}

			JRadioButton rb = new JRadioButton(map+" - "+stage);

			int finalI = i;

			rb.addActionListener(e1 -> selection.set(finalI));

			if(i == 0)
				rb.setSelected(true);

			bg.add(rb);

			panel.add(rb);
		}

		JBTN okay = new JBTN("OK");
		JBTN cancel = new JBTN("Cancel");

		okay.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(okay);

			bp.callBack(exStages.get(selection.get()));
		});

		cancel.addActionListener(a -> {
			JOptionPane pane = getOptionPane((JComponent) a.getSource());

			pane.setValue(cancel);
		});

		JOptionPane.showOptionDialog(
				null,
				panel,
				title,
				JOptionPane.OK_CANCEL_OPTION,
				-1,
				null,
				new Object[]{okay, cancel},
				null
		);
	}

	private static JOptionPane getOptionPane(JComponent parent) {
		JOptionPane pane;

		if (!(parent instanceof JOptionPane)) {
			pane = getOptionPane((JComponent) parent.getParent());
		} else {
			pane = (JOptionPane) parent;
		}

		return pane;
	}
}
