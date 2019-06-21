package page.info.edit;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import common.util.Data;
import common.util.Interpret;
import common.CommonStatic;
import page.JL;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;
import utilpc.UtilPC;

class AtkProcTable extends Page {

	private static final long serialVersionUID = 1L;

	private static final int LEN = 17, SEC = 11;
	private static final int[] INDS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 27, 21, 22, 23, 24, 25, 26 };
	private static final int[] TREA = new int[] { 2, 1, 1, -1, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
	private static final int[][] STRS = new int[][] { { 0, 1, 2 }, { 0, 1 }, { 0, 1 }, { 0, 9 }, { 0, 4 }, { 0, 1, 3 },
			{ 0 }, { 0, 1, 2 }, { 0, 1 }, { 0 }, { 0 }, { 0, 1 }, { 0, 1 }, { 0, 5, 2, 9, 6, 1 }, { 0, 7, 8, 1, 2, 10 },
			{ 0, 1, 5 }, { 0, 1, 11, 10, 6 } };

	private static String getString(int ind) {
		if (ind == 0)
			return "prob";
		else if (ind == 1)
			return "time";
		else if (ind == 2)
			return "dist";
		else if (ind == 3)
			return "to";
		else if (ind == 4)
			return "Lv.";
		else if (ind == 5)
			return "ID";
		else if (ind == 6)
			return "type";
		else if (ind == 7)
			return "speed";
		else if (ind == 8)
			return "width";
		else if (ind == 9)
			return "buff";
		else if (ind == 10)
			return "itv";
		else if (ind == 11)
			return "damage";
		else
			return "!";
	}

	private final JLabel[] jlm = new JLabel[LEN];
	private final JLabel[][] jls = new JLabel[LEN][];
	private final JTF[][] jtfs = new JTF[LEN][];
	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final boolean editable, isUnit;

	private int[][] proc;

	protected AtkProcTable(Page p, boolean edit, boolean unit) {
		super(p);

		editable = edit;
		isUnit = unit;
		ini();
	}

	@Override
	protected void resized(int x, int y) {
		int h = 0;
		for (int i = 0; i < LEN; i++) {
			int c = i < SEC ? 0 : 400;
			set(jlm[i], x, y, c, h, 350, 50);
			h += 50;
			for (int j = 0; j < STRS[i].length; j++) {
				set(jls[i][j], x, y, c, h, 150, 50);
				set(jtfs[i][j], x, y, c + 150, h, 200, 50);
				h += 50;
			}
			if (i == SEC - 1)
				h = 0;
		}
	}

	protected void setData(int[][] ints) {
		proc = ints;
		for (int i = 0; i < LEN; i++) {
			int[] vals = STRS[i];
			// check extra abilities
			if (i == 0 && ints[i][0] > 0 && ints[i][1] == 0)
				ints[i][1] = Data.KB_TIME[Data.INT_KB];
			if (i == 0 && ints[i][0] > 0 && ints[i][2] == 0)
				ints[i][2] = Data.KB_DIS[Data.INT_KB];
			if (i == 3 && ints[i][0] > 0 && ints[i][1] == 0)
				ints[i][1] = 200;
			for (int j = 0; j < vals.length; j++) {
				String str = "" + ints[INDS[i]][j];
				if (isUnit && TREA[i] == j)
					if (vals[j] == 1)
						str += "(" + (int) (ints[INDS[i]][j] * 1.2) + ")";
					else if (vals[j] == 2)
						str += "(" + (int) (ints[INDS[i]][j] * 1.3) + ")";
				if (vals[j] == 0 || vals[j] == 3 || vals[j] == 9)
					str += "%";
				if (vals[j] == 1 || vals[j] == 10)
					str += "f";
				jtfs[i][j].setText(str);
			}

		}
	}

	private void ini() {
		for (int i = 0; i < LEN; i++) {
			jlm[i] = new JLabel(Interpret.SPROC[INDS[i]]);
			BufferedImage v = UtilPC.getIcon(1, INDS[i]);
			if (v != null)
				jlm[i].setIcon(new ImageIcon(v));
			set(jlm[i]);
			int[] vals = STRS[i];
			jls[i] = new JLabel[vals.length];
			jtfs[i] = new JTF[vals.length];
			for (int j = 0; j < vals.length; j++) {
				jls[i][j] = new JL(1, getString(vals[j]));
				jtfs[i][j] = new JTF();
				set(jls[i][j]);
				set(jtfs[i][j]);
			}
		}
		jtfs[13][4].setToolTipText("<html>use warp animation: +1<br>" + "use burrow animation: +2<br>"
				+ "use burrow animation and disable burrow: +3<br>" + "disregard limit: +4<br>" + "fix buff^: +8<br>"
				+ "same health: +16<br>" + "random layer: +32<br>" + "only on hit*: +64 <br>"
				+ "only on kill*: +128<br>" + "^: not applicapable for unit<br>"
				+ "*: distance relative to damaged entity</html>");
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
	}

	private void input(JTF jtf, String input) {
		if (input.length() > 0) {
			int val = CommonStatic.parseIntN(input);
			for (int i = 0; i < LEN; i++)
				for (int j = 0; j < STRS[i].length; j++)
					if (jtf == jtfs[i][j]) {
						int type = STRS[i][j];
						if ((type == 0 || type == 4) && val < 0)
							val = 0;
						if ((type == 0 || type == 3) && val > 100)
							val = 100;
						if ((type == 1 || type == 10) && val < -1)
							val = -1;
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
