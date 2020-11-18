package page.info.edit;

import common.CommonStatic;
import common.battle.data.AtkDataModel;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.support.ListJtfPolicy;
import utilpc.Interpret;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

class AtkEditTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL latk = new JL(1, "atk");
	private final JL lpre = new JL(1, "preaa");
	private final JL lp0 = new JL(1, "p0");
	private final JL lp1 = new JL(1, "p1");
	private final JL ltp = new JL(1, "type");
	private final JL ldr = new JL(1, "dire");
	private final JL lct = new JL(1, "count");
	private final JL lab = new JL(1, "ability");
	private final JL lmv = new JL(1, "move");
	private final JTF fatk = new JTF();
	private final JTF fpre = new JTF();
	private final JTF fp0 = new JTF();
	private final JTF fp1 = new JTF();
	private final JTF ftp = new JTF();
	private final JTF fdr = new JTF();
	private final JTF fct = new JTF();
	private final JTF fab = new JTF();
	private final JTF fmv = new JTF();
	private final JTG isr = new JTG(1, "isr");

	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final ProcTable.AtkProcTable apt;
	private final JScrollPane jsp;
	private final boolean editable;

	private double mul;
	private final boolean changing = false;

	protected AtkDataModel adm;

	protected AtkEditTable(Page p, boolean edit, boolean unit) {
		super(p);
		apt = new ProcTable.AtkProcTable(this, edit, unit);
		jsp = new JScrollPane(apt);
		editable = edit;
		ini();
	}

	@Override
	public void callBack(Object o) {
		getFront().callBack(o);
	}

	@Override
	protected void resized(int x, int y) {
		set(latk, x, y, 0, 0, 200, 50);
		set(lpre, x, y, 0, 50, 200, 50);
		set(lp0, x, y, 0, 100, 200, 50);
		set(lp1, x, y, 0, 150, 200, 50);
		set(ltp, x, y, 0, 200, 200, 50);
		set(ldr, x, y, 0, 250, 200, 50);
		set(lct, x, y, 0, 300, 200, 50);
		set(lab, x, y, 0, 350, 200, 50);
		set(lmv, x, y, 0, 400, 200, 50);
		set(fatk, x, y, 200, 0, 200, 50);
		set(fpre, x, y, 200, 50, 200, 50);
		set(fp0, x, y, 200, 100, 200, 50);
		set(fp1, x, y, 200, 150, 200, 50);
		set(ftp, x, y, 200, 200, 200, 50);
		set(fdr, x, y, 200, 250, 200, 50);
		set(fct, x, y, 200, 300, 200, 50);
		set(fab, x, y, 200, 350, 200, 50);
		set(fmv, x, y, 200, 400, 200, 50);
		set(isr, x, y, 200, 450, 200, 50);
		apt.setPreferredSize(size(x, y, 750, 2200).toDimension());
		apt.resized(x, y);
		set(jsp, x, y, 450, 0, 800, 950);
	}

	protected void setData(AtkDataModel data, double multi) {
		adm = data;
		mul = multi;

		fatk.setText("" + (int) (adm.atk * mul));
		fpre.setText("" + adm.pre);
		fp0.setText("" + adm.ld0);
		fp1.setText("" + adm.ld1);
		ftp.setText("" + adm.targ);
		apt.setData(adm.ce.common ? adm.ce.rep.proc : adm.proc);
		fdr.setText("" + adm.dire);
		fct.setText("" + adm.count);
		fmv.setText("" + adm.move);
		int alt = adm.getAltAbi();
		int i = 0;
		String str = "{";
		while (alt > 0) {
			if ((alt & 1) == 1) {
				if (str.length() > 1)
					str += ",";
				str += i;
			}
			alt >>= 1;
			i++;
		}
		fab.setText(str + "}");
		isr.setSelected(adm.range);
	}

	private void ini() {
		set(latk);
		set(lpre);
		set(lp0);
		set(lp1);
		set(ltp);
		set(ldr);
		set(lct);
		set(lab);
		set(lmv);
		set(fatk);
		set(fpre);
		set(fp0);
		set(fp1);
		set(ftp);
		set(fdr);
		set(fct);
		set(fab);
		set(fmv);
		add(isr);
		ftp.setToolTipText(
				"<html>" + "+1 for normal attack<br>" + "+2 to attack kb<br>" + "+4 to attack underground<br>"
						+ "+8 to attack corpse<br>" + "+16 to attack soul<br>" + "+32 to attack ghost</html>");
		fdr.setToolTipText("direction, 1 means attack enemies, 0 means not an attack, -1 means assist allies");

		fpre.setToolTipText(
				"<html>use 0 for random attack attaching to previous one.<br>pre=0 for first attack will invalidate it</html>");
		String ttt = "<html>enter ID of abilities separated by comma or space.<br>" + "it changes the ability state"
				+ "(has to hot has, not has to has)<br>"
				+ "it won't change back until you make another attack to change it<br>";

		for (int i = 0; i < Interpret.SABIS.length; i++)
			ttt += i + ": " + Interpret.SABIS[i] + "<br>";
		fab.setToolTipText(ttt + "</html>");

		add(jsp);

		isr.setEnabled(editable);
		jsp.getVerticalScrollBar().setUnitIncrement(10);
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);

		isr.setLnr(x -> adm.range = isr.isSelected());
	}

	private void input(JTF jtf, String text) {
		if (text.length() > 0) {
			if (jtf == fab) {
				int[] ent = CommonStatic.parseIntsN(text);
				int ans = 0;
				for (int i : ent)
					if (i >= 0 && i < Interpret.ABIS.length)
						if (ans == -1)
							ans = 1 << i;
						else
							ans |= 1 << i;
				adm.alt = ans;
			}
			int v = CommonStatic.parseIntN(text);
			if (jtf == fatk) {
				v /= mul;
				adm.atk = v;
			}
			if (jtf == fpre) {
				if (v < 0)
					v = 1;
				adm.pre = v;
			}
			if (jtf == fp0) {
				adm.ld0 = v;
				if (adm.ld0 != 0 || adm.ld1 != 0)
					if (adm.ld1 <= v)
						adm.ld1 = v + 1;
			}
			if (jtf == fp1) {
				adm.ld1 = v;
				if (adm.ld0 != 0 || adm.ld1 != 0)
					if (adm.ld0 >= v)
						adm.ld0 = v - 1;
			}
			if (jtf == ftp) {
				if (v < 1)
					v = 1;
				adm.targ = v;
			}
			if (jtf == fdr) {
				if (v < -1)
					v = -1;
				if (v > 1)
					v = 1;
				adm.dire = v;
			}
			if (jtf == fct) {
				if (v < 0)
					v = -1;
				adm.count = v;
			}
			if (jtf == fmv)
				adm.move = v;
		}
		callBack(null);
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
				if (changing)
					return;
				input(jtf, jtf.getText());
				callBack(null);
			}

		});

	}

}