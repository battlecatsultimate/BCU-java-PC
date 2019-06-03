package page.info.edit;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import io.Reader;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.support.ListJtfPolicy;
import util.Interpret;
import util.entity.data.AtkDataModel;

class AtkEditTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL latk = new JL(1, "atk");
	private final JL lpre = new JL(1, "preaa");
	private final JL lp0 = new JL(1, "p0");
	private final JL lp1 = new JL(1, "p1");
	private final JL ltp = new JL(1, "type");
	private final JL ldr = new JL(1, "dire");
	private final JL lct = new JL(1, "count");
	private final JTF fatk = new JTF();
	private final JTF fpre = new JTF();
	private final JTF fp0 = new JTF();
	private final JTF fp1 = new JTF();
	private final JTF ftp = new JTF();
	private final JTF fdr = new JTF();
	private final JTF fct = new JTF();
	private final JTG isr = new JTG(1, "isr");

	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final AtkProcTable apt;
	private final JScrollPane jsp;
	private final boolean editable;

	private double mul;
	private boolean changing = false;

	protected AtkDataModel adm;

	protected AtkEditTable(Page p, boolean edit, boolean unit) {
		super(p);

		apt = new AtkProcTable(this, edit, unit);
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
		set(fatk, x, y, 200, 0, 200, 50);
		set(fpre, x, y, 200, 50, 200, 50);
		set(fp0, x, y, 200, 100, 200, 50);
		set(fp1, x, y, 200, 150, 200, 50);
		set(ftp, x, y, 200, 200, 200, 50);
		set(fdr, x, y, 200, 250, 200, 50);
		set(fct, x, y, 200, 300, 200, 50);
		set(isr, x, y, 200, 300, 200, 50);// FIXME change it to 350
		apt.setPreferredSize(size(x, y, 750, 2000).toDimension());
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
		fdr.setText("" + (adm.rev ? -1 : 1));
		fct.setText("" + adm.count);
		isr.setSelected(adm.range);
	}

	private void ini() {
		set(latk);
		set(lpre);
		set(lp0);
		set(lp1);
		set(ltp);
		// FIXME set(lct);
		set(fatk);
		set(fpre);
		set(fp0);
		set(fp1);
		set(ftp);
		// FIXME set(fct);
		add(isr);
		ftp.setToolTipText(
				"<html>" + "+1 for normal attack<br>" + "+2 to attack kb<br>" + "+4 to attack underground<br>"
						+ "+8 to attack corpse<br>" + "+16 to attack soul<br>" + "+32 to attack ghost</html>");
		fpre.setToolTipText(
				"<html>use 0 for random attack attaching to previous one.<br>pre=0 for first attack will invalidate it</html>");
		String ttt = "<html>enter ID of abilities separated by comma or space.<br>" + "it changes the ability state"
				+ "(has to hot has, not has to has)<br>"
				+ "it won't change back until you make another attack to change it ";

		for (int i = 0; i < Interpret.SABIS.length; i++)
			ttt += i + ": " + Interpret.SABIS[i] + "<br>";
		fct.setToolTipText(ttt + "</html>");
		set(ldr);
		set(fdr);
		add(jsp);

		isr.setEnabled(editable);
		jsp.getVerticalScrollBar().setUnitIncrement(10);
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);

		isr.setLnr(x -> adm.range = isr.isSelected());
	}

	private void input(JTF jtf, String text) {
		if (text.length() > 0) {
			int v = Reader.parseIntN(text);
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
			if (jtf == fdr)
				adm.rev = v == -1;
			if (jtf == fct)
				if (v >= 0)
					adm.count = v;
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