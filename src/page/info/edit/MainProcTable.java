package page.info.edit;

import static common.util.Interpret.SPROC;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.CommonStatic;
import page.JL;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;
import utilpc.UtilPC;

class MainProcTable extends Page {

	private static final long serialVersionUID = 1L;

	private static final int F_A = 0, F_P = 1, F_PC = 2, F_N = 3;
	private static final int U_N = 0, U_T = 1, U_PC = 2;

	private static final int[] INDS = { 9, 10, 11, 12, 28 };
	private static final int[][] LENS = { { F_PC, F_P }, { F_PC }, { F_P, F_N }, { F_N, F_P, F_PC, F_A, F_A, F_P },
			{ F_P } };
	private static final int[][] UNIT = { { U_PC, U_PC }, { U_PC }, { U_N, U_N }, { U_N, U_T, U_PC, U_N, U_N, U_N },
			{ U_N } };
	private static final String[][] STRS = { { "HP", "inc" }, { "prob" }, { "times", "dist" },
			{ "times", "time", "HP", "p0", "p1", "type" }, { "type" } };

	private final JL[] tits = new JL[INDS.length];
	private final JL[][] jls = new JL[INDS.length][];
	private final JTF[][] jtfs = new JTF[INDS.length][];

	private final ListJtfPolicy ljp = new ListJtfPolicy();

	private int[][] proc;

	private final boolean editable;

	protected MainProcTable(Page p, boolean edit) {
		super(p);

		editable = edit;
		ini();
	}

	@Override
	protected void resized(int x, int y) {
		setPreferredSize(size(x, y, 300, 850).toDimension());
		int h = 0;
		for (int i = 0; i < INDS.length; i++) {
			set(tits[i], x, y, 0, h, 300, 50);
			h += 50;
			for (int j = 0; j < LENS[i].length; j++) {
				set(jls[i][j], x, y, 0, h, 100, 50);
				set(jtfs[i][j], x, y, 100, h, 200, 50);
				h += 50;
			}
		}

	}

	protected void setData(int[][] ints) {
		proc = ints;
		for (int i = 0; i < INDS.length; i++)
			for (int j = 0; j < UNIT[i].length; j++)
				if (UNIT[i][j] == U_N)
					jtfs[i][j].setText(ints[INDS[i]][j] + "");
				else if (UNIT[i][j] == U_T)
					jtfs[i][j].setText(ints[INDS[i]][j] + "f");
				else if (UNIT[i][j] == U_PC)
					jtfs[i][j].setText(ints[INDS[i]][j] + "%");
	}

	private void ini() {

		for (int i = 0; i < INDS.length; i++) {
			set(tits[i] = new JL(SPROC[INDS[i]]));
			BufferedImage v = UtilPC.getIcon(1, INDS[i]);
			if (v != null)
				tits[i].setIcon(new ImageIcon(v));
			jls[i] = new JL[LENS[i].length];
			jtfs[i] = new JTF[LENS[i].length];
			for (int j = 0; j < LENS[i].length; j++) {
				set(jls[i][j] = new JL(STRS[i][j]));
				set(jtfs[i][j] = new JTF());
			}
		}
		jtfs[3][5].setToolTipText(
				"<html>" + "+16: apply to surrounding allies as well<br>" + "allow more revive times as this has<br>"
						+ "use shorter revive time and higher revive health if this has<br>"
						+ "+4: immune to z-kill<br>" + "+8: apply effects to allies without revive ability as well<br>"
						+ "0: effective to others when in range and is in normal phase<br>"
						+ "1: effective to others when in range and alive<br>"
						+ "2: effective to everything once passed the range when alive<br>"
						+ "3: effective to everything once passed the range</html>");
		jtfs[4][0].setToolTipText("<html>1: immune to crit<br>2: crit blocker</html>");
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);

	}

	private void input(JTF jtf, String input) {
		if (input.length() > 0) {
			int val = CommonStatic.parseIntN(input);
			for (int i = 0; i < INDS.length; i++)
				for (int j = 0; j < LENS[i].length; j++)
					if (jtf == jtfs[i][j]) {
						if (LENS[i][j] == F_PC) {
							if (val < 0)
								val = 0;
							if (val > 100)
								val = 100;
						}
						if (LENS[i][j] == F_P) {
							if (val < 0)
								val = 0;
						}
						if (LENS[i][j] == F_N) {
							if (val < -1)
								val = -1;
						}
						proc[INDS[i]][j] = val;
					}
		}
		getFront().callBack(null);
	}

	private void set(JLabel jl) {
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		jl.setBorder(BorderFactory.createEtchedBorder());
		add(jl);
	}

	private void set(JTF jtf) {
		jtf.setEditable(editable);
		add(jtf);
		ljp.add(jtf);

		jtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent fe) {
				input(jtf, jtf.getText());
			}

		});

	}

}
