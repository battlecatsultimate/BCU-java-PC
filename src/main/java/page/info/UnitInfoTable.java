package page.info;

import common.CommonStatic;
import common.battle.BasisSet;
import common.battle.data.MaskUnit;
import common.battle.data.PCoin;
import common.system.VImg;
import common.util.Data;
import common.util.unit.EForm;
import common.util.unit.Form;
import common.util.unit.Level;
import common.util.unit.Trait;
import main.MainBCU;
import page.JL;
import page.JTF;
import page.MainLocale;
import page.Page;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UnitInfoTable extends Page {

	private static final long serialVersionUID = 1L;

	private final JL[][] main = new JL[4][8];
	private final JL[][] special = new JL[1][8];
	private final JL[][] upgrade = new JL[3][2];
	private final JL[] atks;
	private final JLabel[] proc;
	private final JTF jtf = new JTF();
	private final JLabel pcoin;
	private final JTextArea descr = new JTextArea();
	private final JTextArea cfdesc = new JTextArea();

	private final BasisSet b;
	private final Form f;
	private Level multi;
	private boolean displaySpecial;

	protected UnitInfoTable(Page p, Form de, Level lvs) {
		super(p);
		b = BasisSet.current();

		f = de;
		multi = lvs;
		atks = new JL[6];

		boolean pc = de.du.getPCoin() != null;
		MaskUnit du = pc ? f.du.getPCoin().improve(multi.getTalents()) : f.du;
		List<Interpret.ProcDisplay> ls = Interpret.getAbi(du);
		double mul = f.unit.lv.getMult(multi.getLv() + multi.getPlusLv());
		ls.addAll(Interpret.getProc(du, false, new double[]{Math.round(du.getHp() * mul) * b.t().getDefMulti(), multi.getLv() + multi.getPlusLv()}));
		if (pc)
			ls.add(new Interpret.ProcDisplay("", null));
		proc = new JLabel[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			Interpret.ProcDisplay display = ls.get(i);
			add(proc[i] = new JLabel(display.toString()));
			proc[i].setBorder(BorderFactory.createEtchedBorder());
			proc[i].setIcon(UtilPC.getScaledIcon(display.getIcon(), 40, 40));
		}
		pcoin = pc ? proc[ls.size() - 1] : null;
		ini();
	}

	protected UnitInfoTable(Page p, Form de, boolean sp) {
		super(p);
		b = BasisSet.current();

		f = de;
		displaySpecial = sp;
		multi = de.unit.getPrefLvs();
		atks = new JL[6];
		MaskUnit du = f.maxu();
		List<Interpret.ProcDisplay> ls = Interpret.getAbi(du);
		double mul = f.unit.lv.getMult(multi.getLv() + multi.getPlusLv());
		ls.addAll(Interpret.getProc(du, false, new double[]{Math.round(du.getHp() * mul) * b.t().getDefMulti(), multi.getLv() + multi.getPlusLv()}));
		boolean pc = de.du.getPCoin() != null;
		if (pc)
			ls.add(new Interpret.ProcDisplay("", null));
		proc = new JLabel[ls.size()];
		for (int i = 0; i < ls.size(); i++) {
			Interpret.ProcDisplay display = ls.get(i);
			add(proc[i] = new JLabel(display.toString()));
			proc[i].setBorder(BorderFactory.createEtchedBorder());
			proc[i].setIcon(UtilPC.getScaledIcon(display.getIcon(), 40, 40));
		}
		pcoin = pc ? proc[ls.size() - 1] : null;
		ini();
	}

	@Override
	protected JButton getBackButton() {
		return null;
	}

	protected int getH() {
		int l = main.length + 1;
		if (displaySpecial)
			l += special.length;
		if (f.hasEvolveCost() || f.hasZeroForm())
			l += upgrade.length;
		return (l + (proc.length + 1) / 2) * 50 + (f.getExplaination().replace("<br>", "").length() > 0 ? 200 : 0);
	}

	protected void reset() {
		EForm ef = new EForm(f, multi);
		double mul = f.unit.lv.getMult(multi.getLv() + multi.getPlusLv());
		double atk = b.t().getAtkMulti();
		double def = b.t().getDefMulti();

		int attack = (int) (Math.round(ef.du.allAtk() * mul) * atk);

		int hp = (int) (Math.round(ef.du.getHp() * mul) * def);

		PCoin pc = f.du.getPCoin();

		if (pc != null) {
			attack = (int) (attack * pc.getAtkMultiplication(multi.getTalents()));
			hp = (int) (hp * pc.getHPMultiplication(multi.getTalents()));
		}

		ArrayList<Trait> trs = ef.du.getTraits();
		trs.sort(Comparator.comparingInt(t -> t.id.id));
		trs.sort(Comparator.comparing(t -> t.id.pack));
		trs.sort(Comparator.comparing(t -> !t.BCTrait));
		String[] traits = new String[trs.size()];
		for (int i = 0; i < trs.size(); i++) {
			Trait trait = ef.du.getTraits().get(i);
			if (trait.BCTrait)
				traits[i] = Interpret.TRAIT[trait.id.id];
			else
				traits[i] = trait.name;
		}
		main[1][3].setText(hp + " / " + ef.du.getHb());
		main[2][3].setText(String.valueOf(attack * 30 / ef.du.getItv()));
		main[2][5].setText(String.valueOf((int) (ef.du.getSpeed() * (1 + b.getInc(Data.C_SPE) * 0.01))));
		main[3][5].setText(MainBCU.seconds ? MainBCU.toSeconds(ef.du.getTBA()) : ef.du.getTBA() + "f");

		int respawn = b.t().getFinRes(ef.du.getRespawn());
		main[1][5].setText(MainBCU.seconds ? MainBCU.toSeconds(respawn) : respawn + "f");
		main[1][7].setText(String.valueOf(ef.getPrice(1)));
		main[0][4].setText(Interpret.getTrait(traits, 0));
		int[][] atkData = ef.du.rawAtkData();
		StringBuilder satk = new StringBuilder();
		for (int[] atkDatum : atkData) {
			if (satk.length() > 0)
				satk.append(" / ");

			int a = (int) (Math.round(atkDatum[0] * mul) * b.t().getAtkMulti());
			if (pc != null)
				a *= pc.getAtkMultiplication(multi.getTalents());

			satk.append(a);
		}
		atks[1].setText(satk.toString());

		List<Interpret.ProcDisplay> ls = Interpret.getAbi(ef.du);
		ls.addAll(Interpret.getProc(ef.du, false, new double[]{mul, multi.getLv() + multi.getPlusLv()}));
		for (JLabel l : proc) {
			if (l != pcoin)
				l.setText("");
			l.setIcon(null);
		}
		for (int i = 0; i < ls.size(); i++) {
			Interpret.ProcDisplay display = ls.get(i);
			proc[i].setText(display.toString());
			proc[i].setIcon(UtilPC.getScaledIcon(display.getIcon(), 40, 40));
		}
		updateTooltips();
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
			set(proc[i], x, y, i % 2 * 800, h + 50 * (i / 2), i % 2 == 0 && i + 1 == proc.length ? 1600 : 800, 50);
		h += proc.length * 25 + (proc.length % 2 == 1 ? 25 : 0);
		if (f.hasEvolveCost() || f.hasZeroForm()) {
			set(cfdesc, x, y, 800, h, 800, 150);
			for (JL[] ug : upgrade) {
				for (int j = 0; j < ug.length; j++)
					set(ug[j], x, y, j * 400, h, 400, 50);
				h += 50;
			}
		}
		set(descr, x, y, 0, h, 1600, 200);
	}

	private void addListeners() {

		jtf.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				multi = f.regulateLv(Level.lvList(f.unit, CommonStatic.parseIntsN(jtf.getText()), null), multi);
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
		for (int i = 0; i < atks.length; i++) {
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
		if (f.hasEvolveCost() || f.hasZeroForm()) {
			for (int i = 0; i < upgrade.length; i++) {
				for (int j = 0; j < upgrade[i].length; j++) {
					add(upgrade[i][j] = new JL());
					upgrade[i][j].setBorder(BorderFactory.createEtchedBorder());
				}
			}
		}
		add(cfdesc);
		cfdesc.setBorder(BorderFactory.createEtchedBorder());
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
		main[0][3].setText(MainLocale.INFO, "trait");
		main[1][0].setText(Interpret.RARITY[f.unit.rarity]);
		main[1][2].setText(MainLocale.INFO, "hphb");
		main[1][4].setText(MainLocale.INFO, "cdo");
		main[1][6].setText(MainLocale.INFO, "price");
		main[2][0].setText(MainLocale.INFO, "range");
		main[2][1].setText(String.valueOf(f.du.getRange()));
		main[2][2].setText("dps");
		main[2][4].setText(MainLocale.INFO, "speed");
		main[2][6].setText(MainLocale.INFO, "atkf");

		main[3][0].setText(MainLocale.INFO, "isr");
		main[3][1].setText(String.valueOf(f.du.isRange()));
		main[3][2].setText(MainLocale.INFO, "will");
		main[3][3].setText(String.valueOf(f.du.getWill() + 1));
		main[3][4].setText(MainLocale.INFO, "TBA");
		main[3][6].setText(MainLocale.INFO, "postaa");

		if (MainBCU.seconds) {
			main[2][7].setText(MainBCU.toSeconds(f.du.getItv()));
			main[3][5].setText(MainBCU.toSeconds(f.du.getTBA()));
			main[3][7].setText(MainBCU.toSeconds(f.du.getPost()));
		} else {
			main[2][7].setText(f.du.getItv() + "f");
			main[3][5].setText(f.du.getTBA() + "f");
			main[3][7].setText(f.du.getPost() + "f");
		}

		special[0][0].setText(MainLocale.INFO, "count");
		special[0][1].setText(f.du.getAtkLoop() < 0 ? "infinite" : String.valueOf(f.du.getAtkLoop()));
		special[0][2].setText(MainLocale.INFO, "width");
		special[0][3].setText(String.valueOf(f.du.getWidth()));
		special[0][4].setText(MainLocale.INFO, "minpos");
		special[0][5].setText(String.valueOf(f.du.getLimit()));
		special[0][6].setText(MainLocale.INFO, "t7");
		int back = Math.min(f.du.getBack(), f.du.getFront());
		int front = Math.max(f.du.getBack(), f.du.getFront());
		if (back == front)
			special[0][7].setText(String.valueOf(back));
		else
			special[0][7].setText(Math.min(back, front) + " ~ " + Math.max(back, front));

		if (f.hasEvolveCost() || f.hasZeroForm()) {
			int[][] evo;
			int xpAmount;

			if (f.hasEvolveCost()) {
				evo = f.unit.info.evo;
				xpAmount = f.unit.info.xp;
			} else {
				evo = f.unit.info.zeroEvo;
				xpAmount = f.unit.info.zeroXp;
			}

			int count = 0;
			for (int i = 0; i < evo.length; i++) {
				int id = evo[i][0];
				JL up = upgrade[i / 2][i % 2];
				if (id == 0)
					break;
				VImg img = CommonStatic.getBCAssets().gatyaitem.get(id);
				up.setIcon(img != null ? UtilPC.getScaledIcon(img, 50, 50) : null);
				up.setText(evo[i][1] + " " + get(MainLocale.UTIL, "cf" + id));
				count++;
			}
			JL xp = upgrade[count / 2][count % 2];
			xp.setIcon(UtilPC.getScaledIcon(CommonStatic.getBCAssets().XP, 50, 30));
			xp.setText(xpAmount + " XP");

			if (f.fid == 2 && f.hasEvolveCost()) {
				String desc = f.unit.info.getCatfruitExplanation();

				if (desc != null)
					cfdesc.setText(desc.replace("<br>", "\n"));
			} else if (f.fid == 3 && f.hasZeroForm()) {
				String desc = f.unit.info.getUltraFormEvolveExplanation();

				if (desc != null)
					cfdesc.setText(desc.replace("<br>", "\n"));
			}
		}

		atks[0].setText(1, "atk");
		atks[2].setText(1, "preaa");
		atks[4].setText(1, "dire");
		int[][] atkData = f.du.rawAtkData();
		StringBuilder pre = new StringBuilder();
		StringBuilder use = new StringBuilder();
		for (int[] atkDatum : atkData) {
			if (pre.length() > 0)
				pre.append(" / ");
			if (use.length() > 0)
				use.append(" / ");

			if (MainBCU.seconds)
				pre.append(MainBCU.toSeconds(atkDatum[1]));
			else
				pre.append(atkDatum[1]).append("f");

			use.append(atkDatum[3]);

		}
		atks[3].setText(pre.toString());
		atks[5].setText(use.toString());

		special[0][5].setToolTipText("<html>This unit will stay at least "
				+ f.du.getLimit()
				+ " units away from the max stage length<br>once it passes that threshold.");
		String fDesc = f.getExplaination().replace("<br>", "\n");
		if (fDesc.replace("\n", "").length() > 0)
			add(descr);
		descr.setText(f.toString().replace((f.uid == null ? "NULL" : f.uid.id) + "-" + f.fid + " ", "") + "\n" + fDesc);
		descr.setEditable(false);
		descr.setBorder(BorderFactory.createEtchedBorder());
		cfdesc.setEditable(false);
		reset();
		addListeners();
		updateTooltips();
	}

	private void updateTooltips() {
		for (JLabel jl : proc) {
			String str = jl.getText();
			StringBuilder sb = new StringBuilder();
			FontMetrics fm = jl.getFontMetrics(jl.getFont());
			while (fm.stringWidth(str) >= 400) {
				int i = 1;
				String wrapped = str.substring(0, i);
				while (fm.stringWidth(wrapped) < 400)
					wrapped = str.substring(0, i++);

				int maximum; //JP proc texts don't count with space, this is here to prevent it from staying in while loop forever
				if (CommonStatic.getConfig().lang == 3)
					maximum = Math.max(wrapped.lastIndexOf("。"), wrapped.lastIndexOf("、"));
				else
					maximum = Math.max(Math.max(wrapped.lastIndexOf(" "), wrapped.lastIndexOf(".")), wrapped.lastIndexOf(","));

				if (maximum <= 0)
					maximum = Math.min(i, wrapped.length());

				wrapped = wrapped.substring(0, maximum);
				sb.append(wrapped).append("<br>");
				str = str.substring(wrapped.length());
			}
			sb.append(str);
			jl.setToolTipText("<html>" + sb + "</html>");
		}
	}

	public void setDisplaySpecial(boolean s) {
		displaySpecial = s;
		fireDimensionChanged();
	}
}
