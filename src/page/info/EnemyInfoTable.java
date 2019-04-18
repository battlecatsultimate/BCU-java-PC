package page.info;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import io.Reader;
import page.JL;
import page.JTF;
import page.Page;
import util.Interpret;
import util.basis.BasisSet;
import util.unit.Enemy;

public class EnemyInfoTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL[][] main = new JL[4][8];
	private final JL[][] atks;
	private final JLabel[] abis, proc;
	private final JTF jtf = new JTF();

	private final BasisSet b;
	private final Enemy e;
	private int multi;

	protected EnemyInfoTable(Page p, Enemy de, int mul) {
		super(p);
		b = BasisSet.current;

		e = de;
		multi = mul;
		atks = new JL[e.de.rawAtkData().length][8];
		List<String> ls = Interpret.getAbi(e.de);
		abis = new JLabel[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			add(abis[i] = new JLabel(ls.get(i)));
			abis[i].setBorder(BorderFactory.createEtchedBorder());
		}
		ls = Interpret.getProc(e.de);
		proc = new JLabel[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			add(proc[i] = new JLabel(ls.get(i)));
			proc[i].setBorder(BorderFactory.createEtchedBorder());
		}
		ini();
	}

	protected void reset() {
		double mul = multi * e.de.multi(b) / 100;
		main[1][3].setText("" + (int) (e.de.getHp() * mul));
		main[2][3].setText("" + (int) (e.de.allAtk() * mul * 30 / e.de.getItv()));
		int[][] atkData = e.de.rawAtkData();
		for (int i = 0; i < atks.length; i++)
			atks[i][1].setText("" + (int) (atkData[i][0] * mul));
	}

	@Override
	protected void resized(int x, int y) {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 8; j++)
				if (i * j != 1 && (i != 0 || j < 4))
					set(main[i][j], x, y, 200 * j, 50 * i, 200, 50);
		set(jtf, x, y, 200, 50, 200, 50);
		set(main[0][4], x, y, 800, 0, 800, 50);
		int h = 200;
		for (int i = 0; i < atks.length; i++)
			for (int j = 0; j < 8; j++)
				set(atks[i][j], x, y, 200 * j, h + 50 * i, 200, 50);
		h += atks.length * 50;
		for (int i = 0; i < abis.length; i++)
			set(abis[i], x, y, 0, h + 50 * i, 1200, 50);
		h += abis.length * 50;
		for (int i = 0; i < proc.length; i++)
			set(proc[i], x, y, 0, h + 50 * i, 1200, 50);

	}

	private void addListeners() {

		jtf.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				int val = Reader.parseIntN(jtf.getText());
				if (val != -1)
					multi = val;
				jtf.setText(multi + "%");
				reset();
			}

		});
	}

	private void ini() {
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 8; j++)
				if (i * j != 1 && (i != 0 || j < 5)) {
					add(main[i][j] = new JL());
					main[i][j].setBorder(BorderFactory.createEtchedBorder());
					if (i != 0 && j % 2 == 0 || i == 0 && j < 4)
						main[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				}
		for (int i = 0; i < atks.length; i++)
			for (int j = 0; j < 8; j++) {
				add(atks[i][j] = new JL());
				atks[i][j].setBorder(BorderFactory.createEtchedBorder());
				if (j % 2 == 0)
					atks[i][j].setHorizontalAlignment(SwingConstants.CENTER);
			}
		add(jtf);
		jtf.setText(multi + "%");
		int itv = e.de.getItv();
		main[0][0].setText("ID");
		main[0][1].setText("" + e.id);
		if (e.anim.edi != null && e.anim.edi.getImg() != null)
			main[0][2].setIcon(e.anim.edi.getIcon());
		main[0][3].setText(1, "trait");
		main[0][4].setText(Interpret.getTrait(e.de.getType(), e.de.getStar()));
		main[1][0].setText(1, "mult");
		main[1][2].setText("HP");
		main[1][4].setText("HB");
		main[1][5].setText("" + e.de.getHb());
		main[1][6].setText(1, "drop");
		main[1][7].setText("" + (int) (e.de.getDrop() * b.t().getDropMulti()));
		main[2][0].setText(1, "range");
		main[2][1].setText("" + e.de.getRange());
		main[2][2].setText("dps");
		main[2][4].setText(1, "speed");
		main[2][5].setText("" + e.de.getSpeed());
		main[2][6].setText(1, "atkf");
		main[2][7].setText(itv + "f");
		main[3][0].setText(1, "isr");
		main[3][1].setText("" + e.de.isRange());
		main[3][2].setText(1, "shield");
		main[3][3].setText("" + e.de.getShield());
		main[3][4].setText(1, "TBA");
		main[3][5].setText(e.de.getTBA() + "f");
		main[3][6].setText(1, "postaa");
		int[][] atkData = e.de.rawAtkData();
		for (int i = 0; i < atks.length; i++) {
			atks[i][0].setText("atk");
			atks[i][2].setText(1, "preaa");
			atks[i][3].setText(atkData[i][1] + "f");
			atks[i][4].setText(1, "use");
			atks[i][5].setText("" + (atkData[i][2] == 1));
			itv -= atkData[i][1];
		}
		main[3][7].setText(itv - e.de.getTBA() + "f");
		reset();
		addListeners();
	}

}
