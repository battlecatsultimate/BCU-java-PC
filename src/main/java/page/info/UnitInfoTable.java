package page.info;

import common.CommonStatic;
import common.battle.BasisSet;
import common.battle.data.MaskUnit;
import common.util.Data;
import common.util.unit.EForm;
import common.util.unit.Form;
import page.JL;
import page.JTF;
import page.Page;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class UnitInfoTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL[][] main = new JL[4][8];
	private final JL[][] special = new JL[1][8];
	private final JL[] atks;
	private final JLabel[] proc;
	private final JTF jtf = new JTF();
	private final JLabel pcoin;

	private final BasisSet b;
	private final Form f;
	private int[] multi;
	private boolean displaySpecial;

	protected UnitInfoTable(Page p, Form de, boolean sp) {
		super(p);
		b = BasisSet.current();

		f = de;
		displaySpecial = sp;
		multi = de.unit.getPrefLvs();
		atks = new JL[6];
		MaskUnit du = f.maxu();
		List<String> ls = Interpret.getAbi(du);
		ls.addAll(Interpret.getProc(du));
		boolean pc = de.getPCoin() != null;
		if (pc)
			ls.add("");
		proc = new JLabel[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			add(proc[i] = new JLabel(ls.get(i)));
			proc[i].setBorder(BorderFactory.createEtchedBorder());
		}
		if (pc)
			pcoin = proc[ls.size() - 1];
		else
			pcoin = null;
		ini();
	}

	protected int getH() {
		int l = main.length + 1;
		if (displaySpecial)
			l += special.length;
		return (l + (proc.length + 1) / 2) * 50;
	}

	protected void reset() {
		EForm ef = new EForm(f, multi);
		double mul = f.unit.lv.getMult(multi[0]);
		double atk = b.t().getAtkMulti();
		double def = b.t().getDefMulti();

		int attack = (int) (Math.round(ef.du.allAtk() * mul) * atk);

		int hp = (int) (Math.round(ef.du.getHp() * mul) * def);

		if(f.getPCoin() != null) {
			attack = (int) (attack * f.getPCoin().getAtkMultiplication(multi));
			hp = (int) (hp * f.getPCoin().getHPMultiplication(multi));
		}

		main[1][3].setText(hp + " / " + ef.du.getHb());
		main[2][3].setText("" + (attack * 30 / ef.du.getItv()));
		main[2][5].setText("" + (int) (ef.du.getSpeed() * (1 + b.getInc(Data.C_SPE) * 0.01)));
		main[1][5].setText(b.t().getFinRes(ef.du.getRespawn()) + "f");
		main[1][7].setText("" + ef.getPrice(1));
		main[0][4].setText(Interpret.getTrait(ef.du.getType(), 0));
		int[][] atkData = ef.du.rawAtkData();
		StringBuilder satk = new StringBuilder();
		for (int[] atkDatum : atkData) {
			if (satk.length() > 0)
				satk.append(" / ");

			int a = (int) (Math.round(atkDatum[0] * mul) * b.t().getAtkMulti());

			if(f.getPCoin() != null) {
				a = (int) (a * f.getPCoin().getAtkMultiplication(multi));
			}

			satk.append(a);
		}
		atks[1].setText(satk.toString());

		List<String> ls = Interpret.getAbi(ef.du);
		ls.addAll(Interpret.getProc(ef.du));
		for (JLabel l : proc)
			if (l != pcoin)
				l.setText("");
		for (int i = 0; i < ls.size(); i++)
			proc[i].setText(ls.get(i));

	}

	@Override
	protected void resized(int x, int y) {
		for (int i = 0; i < main.length; i++)
			for (int j = 0; j < main[i].length; j++)
				if ((i != 0 || j < 4) && (i != 1 || j > 1))
					set(main[i][j], x, y, 200 * j, 50 * i, 200, 50);
		set(jtf, x, y, 100, 50, 300, 50);
		set(main[1][0], x, y, 0, 50, 100, 50);
		set(main[0][4], x, y, 800, 0, 800, 50);
		int h = main.length * 50;
		if (displaySpecial) {
			for (JL[] jls : special) {
				for (int j = 0; j < jls.length; j++)
					set(jls[j], x, y, 200 * j, h, 200, 50);
				h += 50;
			}
		} else {
			for (JL[] jls : special) {
				for (int j = 0; j < jls.length; j++)
					set(jls[j], x, y, 200 * j, h, 0, 0);
			}
		}
		set(atks[0], x, y, 0, h, 200, 50);
		set(atks[1], x, y, 200, h, 400, 50);
		set(atks[2], x, y, 600, h, 200, 50);
		set(atks[3], x, y, 800, h, 400, 50);
		set(atks[4], x, y, 1200, h, 200, 50);
		set(atks[5], x, y, 1400, h, 200, 50);
		h += 50;
		for (int i = 0; i < proc.length; i++)
			set(proc[i], x, y, i % 2 * 800, h + 50 * (i / 2), 800, 50);

	}

	private void addListeners() {

		jtf.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				multi = f.regulateLv(CommonStatic.parseIntsN(jtf.getText()), multi);
				String[] strs = UtilPC.lvText(f, multi);
				jtf.setText(strs[0]);
				if (pcoin != null)
					pcoin.setText(strs[1]);
				reset();
			}

		});
	}

	private void ini() {
		for (int i = 0; i < main.length; i++)
			for (int j = 0; j < main[i].length; j++)
				if (i * j != 1 && (i != 0 || j < 5)) {
					add(main[i][j] = new JL());
					main[i][j].setBorder(BorderFactory.createEtchedBorder());
					if (i != 0 && j % 2 == 0 || i == 0 && j < 4)
						main[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				}
		for (int i = 0; i < 6; i++) {
			add(atks[i] = new JL());
			atks[i].setBorder(BorderFactory.createEtchedBorder());
			if (i % 2 == 0)
				atks[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		for (int i = 0; i < special.length; i++) {
			for (int j = 0; j < special[i].length; j++) {
				add(special[i][j] = new JL());
				special[i][j].setBorder(BorderFactory.createEtchedBorder());
				if (j % 2 == 0)
					special[i][j].setHorizontalAlignment(SwingConstants.CENTER);
			}
		}
		add(jtf);
		String[] strs = UtilPC.lvText(f, multi);
		jtf.setText(strs[0]);
		if (pcoin != null) {
			add(pcoin);
			pcoin.setText(strs[1]);
		}
		main[0][0].setText("ID");
		main[0][1].setText(f.uid + "-" + f.fid);
		if (f.anim.getEdi() != null && f.anim.getEdi().getImg() != null)
			main[0][2].setIcon(UtilPC.getIcon(f.anim.getEdi()));
		main[0][3].setText(1, "trait");
		main[1][0].setText(Interpret.RARITY[f.unit.rarity]);
		main[1][2].setText("HP / HB");
		main[1][4].setText("CD");
		main[1][6].setText(1, "price");
		main[2][0].setText(1, "range");
		main[2][1].setText("" + f.du.getRange());
		main[2][2].setText("dps");
		main[2][4].setText(1, "speed");
		main[2][6].setText(1, "atkf");
		main[2][7].setText(f.du.getItv() + "f");
		main[3][0].setText(1, "isr");
		main[3][1].setText("" + f.du.isRange());
		main[3][2].setText(1, "shield");
		main[3][3].setText("" + f.du.getShield());
		main[3][4].setText(1, "TBA");
		main[3][5].setText(f.du.getTBA() + "f");
		main[3][6].setText(1, "postaa");
		main[3][7].setText(f.du.getPost() + "f");
		special[0][0].setText(1, "count");
		special[0][1].setText(f.du.getAtkLoop() < 0 ? "infinite" : f.du.getAtkLoop() + "");
		special[0][2].setText(1, "width");
		special[0][3].setText(f.du.getWidth() + "");
		special[0][4].setText(1, "t7");
		int back = Math.min(f.du.getBack(), f.du.getFront());
		int front = Math.max(f.du.getBack(), f.du.getFront());
		if (back == front)
			special[0][5].setText(back + "");
		else
			special[0][5].setText(Math.min(back, front) + " ~ " + Math.max(back, front));
		atks[0].setText("atk");
		atks[2].setText(1, "preaa");
		atks[4].setText(1, "use");
		int[][] atkData = f.du.rawAtkData();
		StringBuilder pre = new StringBuilder();
		StringBuilder use = new StringBuilder();
		for (int[] atkDatum : atkData) {
			if (pre.length() > 0)
				pre.append(" / ");
			if (use.length() > 0)
				use.append(" / ");
			pre.append(atkDatum[1]).append("f");
			use.append(atkDatum[2] == 1);

		}
		atks[3].setText(pre.toString());
		atks[5].setText(use.toString());
		reset();
		addListeners();
	}

	public void setDisplaySpecial(boolean s) {
		displaySpecial = s;
	}
}
