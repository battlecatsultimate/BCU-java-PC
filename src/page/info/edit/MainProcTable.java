package page.info.edit;

import static util.Interpret.SPROC;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import io.Reader;
import page.JL;
import page.JTF;
import page.Page;
import page.support.ListJtfPolicy;
import util.Res;

class MainProcTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL lst = new JL(SPROC[9]);
	private final JL lle = new JL(SPROC[10]);
	private final JL lbr = new JL(SPROC[11]);
	private final JL lre = new JL(SPROC[12]);
	private final JL lstp = new JL("HP");
	private final JL lsta = new JL(1, "inc");
	private final JL llep = new JL(1, "prob");
	private final JL lbrt = new JL(1, "times");
	private final JL lbrd = new JL(1, "dist");
	private final JL lret = new JL(1, "times");
	private final JL lrei = new JL(1, "time");
	private final JL lrea = new JL("HP");
	private final JTF fstp = new JTF();
	private final JTF fsta = new JTF();
	private final JTF flep = new JTF();
	private final JTF fbrt = new JTF();
	private final JTF fbrd = new JTF();
	private final JTF fret = new JTF();
	private final JTF frei = new JTF();
	private final JTF frea = new JTF();
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
		set(lst, x, y, 0, 0, 300, 50);
		set(lstp, x, y, 0, 50, 100, 50);
		set(lsta, x, y, 0, 100, 100, 50);
		set(fstp, x, y, 100, 50, 200, 50);
		set(fsta, x, y, 100, 100, 200, 50);
		set(lle, x, y, 0, 150, 300, 50);
		set(llep, x, y, 0, 200, 100, 50);
		set(flep, x, y, 100, 200, 200, 50);
		set(lbr, x, y, 0, 250, 300, 50);
		set(lbrt, x, y, 0, 300, 100, 50);
		set(lbrd, x, y, 0, 350, 100, 50);
		set(fbrt, x, y, 100, 300, 200, 50);
		set(fbrd, x, y, 100, 350, 200, 50);
		set(lre, x, y, 0, 400, 300, 50);
		set(lret, x, y, 0, 450, 100, 50);
		set(lrei, x, y, 0, 500, 100, 50);
		set(lrea, x, y, 0, 550, 100, 50);
		set(fret, x, y, 100, 450, 200, 50);
		set(frei, x, y, 100, 500, 200, 50);
		set(frea, x, y, 100, 550, 200, 50);

	}

	protected void setData(int[][] ints) {
		proc = ints;
		fstp.setText(ints[9][0] + "%");
		fsta.setText("+" + ints[9][1] + "%");
		flep.setText(ints[10][0] + "%");
		fbrt.setText("" + ints[11][0]);
		fbrd.setText("" + ints[11][1]);
		fret.setText("" + ints[12][0]);
		frei.setText(ints[12][1] + "f");
		frea.setText(ints[12][2] + "%");
	}

	private void ini() {
		set(lst);
		set(lle);
		set(lbr);
		set(lre);
		set(lstp);
		set(lsta);
		set(llep);
		set(lbrt);
		set(lbrd);
		set(lret);
		set(lrei);
		set(lrea);

		set(fstp);
		set(fsta);
		set(flep);
		set(fbrt);
		set(fbrd);
		set(fret);
		set(frei);
		set(frea);

		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);

		lst.setIcon(new ImageIcon(Res.getIcon(1, 9)));
		lle.setIcon(new ImageIcon(Res.getIcon(1, 10)));
		lbr.setIcon(new ImageIcon(Res.getIcon(1, 11)));
		lre.setIcon(new ImageIcon(Res.getIcon(1, 12)));

	}

	private void input(JTF jtf, String input) {
		if (input.length() > 0) {
			int val = Reader.parseIntN(input);
			if (jtf == fstp) {
				if (val < 0)
					val = 0;
				if (val > 100)
					val = 100;
				proc[9][0] = val;
			}
			if (jtf == fsta) {
				if (val < 0)
					val = 0;
				proc[9][1] = val;
			}
			if (jtf == flep) {
				if (val < 0)
					val = 0;
				if (val > 100)
					val = 100;
				proc[10][0] = val;
			}
			if (jtf == fbrt) {
				if (val < -1)
					val = -1;
				proc[11][0] = val;
			}
			if (jtf == fbrd) {
				if (val < 0)
					val = 0;
				proc[11][1] = val;
			}
			if (jtf == fret) {
				if (val < -1)
					val = -1;
				proc[12][0] = val;
			}
			if (jtf == frei) {
				if (val < 0)
					val = 0;
				proc[12][1] = val;
			}
			if (jtf == frea) {
				if (val < 0)
					val = 0;
				if (val > 100)
					val = 100;
				proc[12][2] = val;
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
