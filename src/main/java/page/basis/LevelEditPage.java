package page.basis;

import common.CommonStatic;
import common.battle.BasisSet;
import common.battle.LineUp;
import common.battle.data.MaskUnit;
import common.battle.data.Orb;
import common.util.Data;
import common.util.stage.StageLimit;
import common.util.unit.Form;
import common.util.unit.Level;
import common.util.unit.Trait;
import main.MainBCU;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import utilpc.Interpret;
import utilpc.UtilPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.util.Data.ORB_TOT;

public class LevelEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final Page p;

	private final Form f;
	private final Level lv;
	private final List<int[]> orbs = new ArrayList<>();
	private final StageLimit sl;

	private final JBTN bck = new JBTN(0, "back");
	private final JLabel pcoin = new JLabel();
	private final JTF levels = new JTF();
	private final JList<String> orbList = new JList<>();
	private final JScrollPane orbScroll = new JScrollPane(orbList);
	private final OrbBox orbb = new OrbBox(new int[] {});
	private final JL ltyp = new JL(0, "type");
	private final JL lgra = new JL(0, "grade");
	private final JL ltra = new JL(0, "trait");
	private final JComboBox<String> type = new JComboBox<>();
	private final JComboBox<String> grade = new JComboBox<>();
	private final JComboBox<String> trait = new JComboBox<>();

	private List<Integer> typeData = new ArrayList<>();
	private List<Integer> traitData = new ArrayList<>();
	private List<Integer> gradeData = new ArrayList<>();

	private boolean updating = false;

	protected LevelEditPage(Page p, Level lv, Form f, StageLimit sl) {
		super(p);
		this.p = p;
		this.lv = lv;
		this.f = f;
		this.sl = sl;

		boolean exists = f != null && f.unit != null;

		if (exists) {
			BasisSet.synchronizeOrb(f.unit);
			int[][] orbLv = lv.getOrbs();
			if (orbLv == null) {
				for (int i = 0; i < f.unit.orbs.size(); i++)
					orbs.add(new int[0]);
			} else {
				orbs.addAll(Arrays.asList(orbLv));
			}
		}

		ini();
	}

	@Override
	protected JButton getBackButton() {
		return bck;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(bck, x, y, 0, 0, 200, 50);
		set(pcoin, x, y, 450, 150, 1200, 50);
		set(levels, x, y, 450, 200, 700, 50);
		set(orbScroll, x, y, 450, 275, 500, 600);
		set(orbb, x, y, 1050, 450, 200, 200);
		set(ltyp, x, y, 1300, 350, 300, 50);
		set(type, x, y, 1300, 400, 300, 50);
		set(lgra, x, y, 1300, 500, 300, 50);
		set(grade, x, y, 1300, 550, 300, 50);
		set(ltra, x, y, 1300, 650, 300, 50);
		set(trait, x, y, 1300, 700, 300, 50);
	}

	@Override
    public synchronized void onTimer(int t) {
		super.onTimer(t);

		orbb.paint(orbb.getGraphics());
	}

	private void addListeners() {
		bck.setLnr(x -> changePanel(getFront()));

		levels.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				Level lvs = Level.lvList(f.unit, CommonStatic.parseIntsN(levels.getText()), null);

				setLvOrb(lvs, generateOrb());

				updateOrbConsideringAbilities();
				orbList.setListData(generateNames());

				levels.setText(UtilPC.lvText(f, lv)[0]);
			}
		});

		orbList.addListSelectionListener(e -> {
			if (updating) {
				return;
			}

			type.setEnabled(valid());
			trait.setEnabled(valid());
			grade.setEnabled(valid());

			if (orbList.getSelectedIndex() != -1) {
				orbb.changeOrb(orbs.get(orbList.getSelectedIndex()));

				initializeDrops(orbs.get(orbList.getSelectedIndex()));
			} else {
				orbb.changeOrb(new int[] {});
			}
		});

		type.addActionListener(arg0 -> {
			if (updating) {
				return;
			}

			if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size()) {
				int[] data = orbs.get(orbList.getSelectedIndex());

				if (f.unit.orbs != null && !f.unit.orbs.isEmpty()) {
					if (type.getSelectedIndex() == 0) {
						data = new int[] {};
					} else {
						if (data.length == 0) {
							data = new int[] { 0, 0, 0 };
						}

						data[0] = typeData.get(type.getSelectedIndex() - 1);
					}
				} else {
					data[0] = typeData.get(type.getSelectedIndex());
				}

				orbs.set(orbList.getSelectedIndex(), data);

				initializeDrops(data);

				orbb.changeOrb(data);

				setLvOrb(lv, generateOrb());
			}
		});

		trait.addActionListener(arg0 -> {
			if (updating) {
				return;
			}

			if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size()) {
				int[] data = orbs.get(orbList.getSelectedIndex());

				data[1] = traitData.get(trait.getSelectedIndex());

				orbs.set(orbList.getSelectedIndex(), data);

				initializeDrops(data);

				orbb.changeOrb(data);

				setLvOrb(lv, generateOrb());
			}
		});

		grade.addActionListener(arg0 -> {
			if (updating) {
				return;
			}

			if (orbList.getSelectedIndex() != -1 && orbList.getSelectedIndex() < orbs.size()) {
				int[] data = orbs.get(orbList.getSelectedIndex());

				data[2] = gradeData.get(grade.getSelectedIndex());

				orbs.set(orbList.getSelectedIndex(), data);

				initializeDrops(data);

				orbb.changeOrb(data);

				setLvOrb(lv, generateOrb());
			}
		});
	}

	private String[] generateNames() {
		String[] res = new String[orbs.size()];

		for (int i = 0; i < res.length; i++) {
			int[] o = orbs.get(i);

			if (o.length != 0) {
				res[i] = i + 1 + ": Grade " + getGrade(o[2]) + " " + getType(o[0]);
				if (o[1] != 0)
					res[i] += " vs " + getTrait(o[1]);
				if (sl != null && sl.bannedOrb.contains(o[0])) {
					res[i] += " (Banned)";
				}
			} else {
				res[i] = (i + 1) + ": None";
			}

			Orb orb = f.unit.orbs.get(i);
			int totalLv = lv.getLv() + lv.getPlusLv();
			if (orb.isRestricted(f.fid, totalLv)) {
				res[i] += " (Req:";
				if (f.fid < orb.minForm)
					res[i] += " " +  Interpret.getNumberExtension(orb.minForm + 1) + " Form";
				if (lv.getLv() + lv.getPlusLv() < orb.minLv)
					res[i] += " " + "Lv. " + orb.minLv;
				res[i] += ")";
			}
		}

		return res;
	}

	private int[][] generateOrb() {
		if (orbs.isEmpty()) {
			return null;
		}

		int[][] data = new int[orbs.size()][];

		for (int i = 0; i < data.length; i++) {
			data[i] = orbs.get(i);
		}

		return data;
	}

	private String getGrade(int grade) {
		switch (grade) {
		case 0:
			return "D";
		case 1:
			return "C";
		case 2:
			return "B";
		case 3:
			return "A";
		case 4:
			return "S";
		default:
			return "Unknown Grade " + grade;
		}
	}

	private String getTrait(int trait) {
		StringBuilder res = new StringBuilder();

		for (int i = 0; i < Interpret.TRAIT.length; i++) {
			if (((trait >> i) & 1) > 0) {
				res.append(Interpret.TRAIT[i]).append("/ ");
			}
		}

		if (res.toString().endsWith("/ "))
			res = new StringBuilder(res.substring(0, res.length() - 2));

		return res.toString();
	}

	private String getType(int type) {
		if (type < ORB_TOT) {
			return Interpret.ORB[type];
		} else {
			return "Unknown Type " + type;
		}
	}

	private void ini() {
		add(bck);
		add(pcoin);
		add(levels);
		add(orbb);

		if (f.unit.orbs != null) {
			add(orbScroll);
			add(ltyp);
			add(type);
			add(ltra);
			add(trait);
			add(lgra);
			add(grade);
		}

		String[] strs = UtilPC.lvText(f, lu().getLv(f));

		levels.setText(strs[0]);
		pcoin.setText(strs[1]);

		addListeners();

		orbList.setListData(generateNames());
		orbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		orbList.setCellRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel jl = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				Orb orbSlot = f.unit.orbs.get(index);
				int[] orbData = orbs.get(index);
				if (orbSlot.isRestricted(f.fid, lv.getLv() + lv.getPlusLv()) || (sl != null && orbData.length != 0 && sl.bannedOrb.contains(orbData[0]))) {
					jl.setText("<html><strike>" + jl.getText() + "<html><strike>");
					jl.setForeground(isSelected ? Color.WHITE : !MainBCU.light ? Color.GRAY : Color.RED);
				}
				return jl;
			}
		});
		type.setEnabled(valid());
		trait.setEnabled(valid());
		grade.setEnabled(valid());
	}

	private void initializeDrops(int[] data) {
		CommonStatic.BCAuxAssets aux = CommonStatic.getBCAssets();

		updating = true;
		boolean hasOrbs = !f.unit.orbs.isEmpty();

		ArrayList<String> typeText = new ArrayList<>();

		boolean str = false;
		boolean mas = false;
		boolean res = false;

        if (hasOrbs) {
			for(Form form : f.unit.forms) {
				MaskUnit mu;

				if(form.du.getPCoin() != null) {
					mu = form.du.getPCoin().improve(lv.getTalents());
				} else {
					mu = form.du;
				}

				str |= (mu.getAbi() & Data.AB_GOOD) != 0;
				mas |= (mu.getAbi() & Data.AB_MASSIVE) != 0;
				res |= (mu.getAbi() & Data.AB_RESIST) != 0;
			}
		} else {
			MaskUnit mu;

			if(f.du.getPCoin() != null) {
				mu = f.du.getPCoin().improve(lv.getTalents());
			} else {
				mu = f.du;
			}

			str = (mu.getAbi() & Data.AB_GOOD) != 0;
			mas = (mu.getAbi() & Data.AB_MASSIVE) != 0;
			res = (mu.getAbi() & Data.AB_RESIST) != 0;
		}

		if (hasOrbs) {
			typeText.add("None");
		}

		typeData = new ArrayList<>();

		typeText.add(getType(0));
		typeData.add(Data.ORB_ATK);
		typeText.add(getType(1));
		typeData.add(Data.ORB_RES);

		if(str) {
			typeText.add(getType(2));
			typeData.add(Data.ORB_STRONG);
		}
		if(mas) {
			typeText.add(getType(3));
			typeData.add(Data.ORB_MASSIVE);
		}
		if(res) {
			typeText.add(getType(4));
			typeData.add(Data.ORB_RESISTANT);
		}

		for (int i = 5; i < ORB_TOT; i++) {
			typeText.add(getType(i));
			typeData.add(i);
		}

		if (hasOrbs && data.length == 0) {
			type.setModel(new DefaultComboBoxModel<>(typeText.toArray(new String[0])));

			type.setSelectedIndex(0);

			trait.setEnabled(false);
			grade.setEnabled(false);

			if (valid()) {
				int index = orbList.getSelectedIndex();

				orbList.setListData(generateNames());

				orbList.setSelectedIndex(index);
			}

			updating = false;

			return;
		}

		String[] traits;
		String[] grades;

		if (aux.ORB.containsKey(data[0])) {
			if(data[Data.ORB_TYPE] == Data.ORB_STRONG || data[Data.ORB_TYPE] == Data.ORB_MASSIVE || data[Data.ORB_TYPE] == Data.ORB_RESISTANT) {
				List<Integer> allTraits = new ArrayList<>(aux.ORB.get(data[0]).keySet());

				traitData = new ArrayList<>();

				List<Trait> traitList = new ArrayList<>();

				if (hasOrbs) {
					for(Form form : f.unit.forms) {
						MaskUnit mu;

						if(form.du.getPCoin() != null) {
							mu = form.du.getPCoin().improve(lv.getTalents());
						} else {
							mu = form.du;
						}

						for(Trait t : mu.getTraits()) {
							if(t.id.pack.equals("000000") && !traitList.contains(t))
								traitList.add(t);
						}
					}
				} else {
					MaskUnit mu;

					if(f.du.getPCoin() != null) {
						mu = f.du.getPCoin().improve(lv.getTalents());
					} else {
						mu = f.du;
					}

					for(Trait t : mu.getTraits()) {
						if(t.id.pack.equals("000000") && !traitList.contains(t))
							traitList.add(t);
					}
				}

				for (Trait t : traitList) {
					if (allTraits.contains(1 << t.id.id))
						traitData.add(1 << t.id.id);
				}

				if(traitData.isEmpty())
					traitData = allTraits;
				else
					traitData.sort(Integer::compareTo);
			} else {
				traitData = new ArrayList<>(aux.ORB.get(data[0]).keySet());
			}

			traits = new String[traitData.size()];

			for (int i = 0; i < traits.length; i++) {
				traits[i] = getTrait(traitData.get(i));
			}

			if (!traitData.contains(data[1])) {
				data[1] = traitData.get(0);
			}

			gradeData = aux.ORB.get(data[0]).get(data[1]);

			grades = new String[gradeData.size()];

			for (int i = 0; i < grades.length; i++) {
				grades[i] = getGrade(gradeData.get(i));
			}
		} else {
			return;
		}

		if (!gradeData.contains(data[2])) {
			data[2] = gradeData.get(2);
		}

		type.setModel(new DefaultComboBoxModel<>(typeText.toArray(new String[0])));
		trait.setModel(new DefaultComboBoxModel<>(traits));
		grade.setModel(new DefaultComboBoxModel<>(grades));
		trait.setEnabled(traitData.get(0) != 0);
		grade.setEnabled(true);

		if (hasOrbs) {
			type.setSelectedIndex(typeData.indexOf(data[0]) + 1);
		} else {
			type.setSelectedIndex(typeData.indexOf(data[0]));
		}

		trait.setSelectedIndex(traitData.indexOf(data[1]));

		grade.setSelectedIndex(gradeData.indexOf(data[2]));

		if (valid()) {
			int index = orbList.getSelectedIndex();

			orbs.set(index, data);

			orbList.setListData(generateNames());

			orbList.setSelectedIndex(index);
		}

		updating = false;
	}

	private LineUp lu() {
		return BasisSet.current().sele.lu;
	}

	private void setLvOrb(Level lv, int[][] orbs) {
		lu().setOrb(f.unit, lv, orbs);

		p.callBack(null);
	}

	private boolean valid() {
		return orbList.getSelectedIndex() != -1 && !orbs.isEmpty();
	}

	private void updateOrbConsideringAbilities() {
		boolean str = false;
		boolean mas = false;
		boolean res = false;

		List<Integer> possibleTraits = new ArrayList<>();

		if(!f.unit.orbs.isEmpty()) {
			for(Form form : f.unit.forms) {
				MaskUnit mu;

				if(form.du.getPCoin() != null) {
					mu = form.du.getPCoin().improve(lv.getTalents());
				} else {
					mu = form.du;
				}

				str |= (mu.getAbi() & Data.AB_GOOD) != 0;
				mas |= (mu.getAbi() & Data.AB_MASSIVE) != 0;
				res |= (mu.getAbi() & Data.AB_RESIST) != 0;

				for(Trait t : mu.getTraits()) {
					if(!t.id.pack.equals("000000"))
						continue;

					int bitMask = 1 << t.id.id;

					if(!possibleTraits.contains(bitMask))
						possibleTraits.add(bitMask);
				}
			}
		} else {
			MaskUnit mu;

			if(f.du.getPCoin() != null) {
				mu = f.du.getPCoin().improve(lv.getTalents());
			} else {
				mu = f.du;
			}

			str = (mu.getAbi() & Data.AB_GOOD) != 0;
			mas = (mu.getAbi() & Data.AB_MASSIVE) != 0;
			res = (mu.getAbi() & Data.AB_RESIST) != 0;

			for(Trait t : mu.getTraits()) {
				if(!t.id.pack.equals("000000"))
					continue;

				int bitMask = 1 << t.id.id;

				if(!possibleTraits.contains(bitMask)) {
					possibleTraits.add(bitMask);
				}
			}
		}

		if(lv.getOrbs() != null) {
			for(int[] data : lv.getOrbs()) {
				if(data.length == 0)
					continue;

				if(!str && data[Data.ORB_TYPE] == Data.ORB_STRONG) {
					data[Data.ORB_TYPE] = Data.ORB_ATK;
				}

				if(!mas && data[Data.ORB_TYPE] == Data.ORB_MASSIVE) {
					data[Data.ORB_TYPE] = Data.ORB_ATK;
				}

				if(!res && data[Data.ORB_TYPE] == Data.ORB_RESISTANT) {
					data[Data.ORB_TYPE] = Data.ORB_ATK;
				}

				if(data[Data.ORB_TYPE] == Data.ORB_STRONG || data[Data.ORB_TYPE] == Data.ORB_MASSIVE || data[Data.ORB_TYPE] == Data.ORB_RESISTANT) {
					List<Integer> allTraits = new ArrayList<>(CommonStatic.getBCAssets().ORB.get(data[0]).keySet());

					List<Integer> traits = new ArrayList<>();

					for(int t : possibleTraits) {
						if (allTraits.contains(t))
							traits.add(t);
					}

					if(!traits.isEmpty())
						traits.sort(Integer::compareTo);

					if(!traits.isEmpty() && !traits.contains(data[Data.ORB_TRAIT])) {
						data[Data.ORB_TRAIT] = traits.get(0);
					}
				}
			}
		}
	}
}
