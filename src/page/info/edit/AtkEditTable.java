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
import page.Page;
import page.support.ListJtfPolicy;
import util.entity.data.AtkDataModel;

class AtkEditTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL latk = new JL(1, "atk");
	private final JL lpre = new JL(1, "preaa");
	private final JL lp0 = new JL(1, "p0");
	private final JL lp1 = new JL(1, "p1");
	private final JL ltp = new JL(1, "type");
	private final JL ldr = new JL(1, "dire");
	private final JTF fatk = new JTF();
	private final JTF fpre = new JTF();
	private final JTF fp0 = new JTF();
	private final JTF fp1 = new JTF();
	private final JTF ftp = new JTF();
	private final JTF fdr = new JTF();

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
		set(fatk, x, y, 200, 0, 200, 50);
		set(fpre, x, y, 200, 50, 200, 50);
		set(fp0, x, y, 200, 100, 200, 50);
		set(fp1, x, y, 200, 150, 200, 50);
		set(ftp, x, y, 200, 200, 200, 50);
		set(fdr, x, y, 200, 250, 200, 50);
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
	}

	private void ini() {
		set(latk);
		set(lpre);
		set(lp0);
		set(lp1);
		set(ltp);
		set(fatk);
		set(fpre);
		set(fp0);
		set(fp1);
		set(ftp);
		set(ldr);
		set(fdr);
		add(jsp);
		jsp.getVerticalScrollBar().setUnitIncrement(10);
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
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
				if (v - adm.pre > adm.ce.getPost() - 1)
					v = adm.pre + adm.ce.getPost() - 1;
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
				if (v > 1)
					v = 5;
				// TODO allow more
				adm.targ = v;
			}
			if (jtf == fdr) {
				adm.rev = v == -1;
			}
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