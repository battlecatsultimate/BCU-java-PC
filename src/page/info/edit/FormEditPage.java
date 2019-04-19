package page.info.edit;

import static util.Data.AB_GLASS;
import static util.Interpret.ABIIND;
import static util.Interpret.IMUSFT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import main.MainBCU;
import page.JBTN;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.anim.DIYViewPage;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitEditBox;
import page.info.filter.UnitFindPage;
import page.support.ListJtfPolicy;
import page.view.UnitViewPage;
import util.anim.AnimC;
import util.basis.Basis;
import util.basis.BasisSet;
import util.entity.data.AtkDataModel;
import util.entity.data.CustomUnit;
import util.unit.DIYAnim;
import util.unit.Enemy;
import util.unit.Form;

public class FormEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");

	private final JL llv = new JL(1, "Lv");
	private final JL lhp = new JL(1, "HP");
	private final JL lhb = new JL(1, "HB");
	private final JL lsp = new JL(1, "speed");
	private final JL lra = new JL(1, "range");
	private final JL lwd = new JL(1, "width");
	private final JL lsh = new JL(1, "shield");
	private final JL ltb = new JL(1, "TBA");
	private final JL lbs = new JL(1, "tbase");
	private final JL ldr = new JL(1, "price");
	private final JL lrs = new JL(1, "CD");
	private final JTF fhp = new JTF();
	private final JTF fhb = new JTF();
	private final JTF fsp = new JTF();
	private final JTF fra = new JTF();
	private final JTF fwd = new JTF();
	private final JTF fsh = new JTF();
	private final JTF ftb = new JTF();
	private final JTF fbs = new JTF();
	private final JTF fdr = new JTF();
	private final JTF flv = new JTF();
	private final JTF frs = new JTF();
	private final JTG isr = new JTG(1, "isr");
	private final JList<String> jli = new JList<>();
	private final JScrollPane jspi = new JScrollPane(jli);
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN link = new JBTN(0, "link");
	private final JBTN vuni = new JBTN(0, "vuni");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vene = new JBTN(0, "enemy");
	private final JTG comm = new JTG(1, "common");
	private final JTF atkn = new JTF();
	private final JL lpst = new JL(1, "postaa");
	private final JLabel vpst = new JLabel();
	private final JL litv = new JL(1, "atkf");
	private final JLabel vitv = new JLabel();
	private final JL ldps = new JL("DPS");
	private final JLabel vdps = new JLabel();
	private final JComboBox<AnimC> jcb = new JComboBox<>();
	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final AtkEditTable aet;
	private final MainProcTable mpt;
	private final UnitEditBox ueb;
	private final boolean editable;
	private final Form form;

	private boolean changing = false;
	private int lv;
	private UnitFindPage ufp;
	private EnemyFindPage efp;

	protected Basis bas = BasisSet.current;
	protected CustomUnit cu;

	public FormEditPage(Page p, Form f, boolean edit) {
		super(p);

		form = f;
		lv = f.unit.getPrefLv();
		aet = new AtkEditTable(this, edit, true);
		mpt = new MainProcTable(this, edit);
		ueb = new UnitEditBox(this, edit);
		editable = edit;
		ini();
		setData((CustomUnit) f.du);
		resized();
	}

	@Override
	public void callBack(Object o) {
		if (o instanceof int[]) {
			int[] vals = (int[]) o;
			if (vals.length == 3) {
				cu.type = vals[0];
				cu.abi = vals[1];
				for (int i = 0; i < ABIIND.length; i++) {
					int id = ABIIND[i] - 100;
					if ((vals[2] & (1 << id - IMUSFT)) > 0)
						cu.getProc(id)[0] = 100;
					else
						cu.getProc(id)[0] = 0;
				}
				cu.loop = (cu.abi & AB_GLASS) > 0 ? 1 : -1;
			}
		}
		setData(cu);
	}

	@Override
	protected void renew() {
		if (ufp != null && ufp.getForm() != null
				&& MainBCU.warning("do you want to overwrite stats? This operation cannot be undone", "confirmation")) {
			Form e = ufp.getForm();
			cu.importData(e.du);
			setData(cu);
		}
		if (efp != null && efp.getEnemy() != null
				&& MainBCU.warning("do you want to overwrite stats? This operation cannot be undone", "confirmation")) {
			Enemy e = efp.getEnemy();
			cu.importData(e.de);
			setData(cu);
		}
		ufp = null;
		efp = null;
	}

	@Override
	protected void resized(int x, int y) {
		setSize(x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(llv, x, y, 50, 50, 100, 50);
		set(flv, x, y, 150, 50, 200, 50);
		set(lhp, x, y, 50, 100, 100, 50);
		set(fhp, x, y, 150, 100, 200, 50);
		set(lhb, x, y, 50, 150, 100, 50);
		set(fhb, x, y, 150, 150, 200, 50);
		set(lsp, x, y, 50, 200, 100, 50);
		set(fsp, x, y, 150, 200, 200, 50);
		set(lra, x, y, 50, 250, 100, 50);
		set(fra, x, y, 150, 250, 200, 50);
		set(ltb, x, y, 50, 300, 100, 50);
		set(ftb, x, y, 150, 300, 200, 50);
		set(ldr, x, y, 50, 350, 100, 50);
		set(fdr, x, y, 150, 350, 200, 50);
		set(isr, x, y, 50, 400, 300, 50);
		set(lsh, x, y, 50, 450, 100, 50);
		set(fsh, x, y, 150, 450, 200, 50);
		set(lwd, x, y, 50, 500, 100, 50);
		set(fwd, x, y, 150, 500, 200, 50);
		set(lbs, x, y, 50, 550, 100, 50);
		set(fbs, x, y, 150, 550, 200, 50);
		set(lrs, x, y, 50, 600, 100, 50);
		set(frs, x, y, 150, 600, 200, 50);

		set(mpt, x, y, 50, 650, 300, 600);
		set(ueb, x, y, 400, 50, 200, 1250);
		set(jspi, x, y, 650, 50, 200, 300);
		set(add, x, y, 650, 400, 200, 50);
		set(rem, x, y, 650, 500, 200, 50);
		set(copy, x, y, 650, 600, 200, 50);
		set(link, x, y, 650, 700, 200, 50);
		set(atkn, x, y, 650, 800, 200, 50);
		set(comm, x, y, 650, 900, 200, 50);

		set(aet, x, y, 900, 50, 1400, 1000);

		set(lpst, x, y, 900, 1050, 200, 50);
		set(vpst, x, y, 1100, 1050, 200, 50);
		set(litv, x, y, 900, 1100, 200, 50);
		set(vitv, x, y, 1100, 1100, 200, 50);
		set(ldps, x, y, 900, 1150, 200, 50);
		set(vdps, x, y, 1100, 1150, 200, 50);
		set(jcb, x, y, 900, 1200, 400, 50);
		set(vuni, x, y, 1350, 1050, 200, 50);
		set(impt, x, y, 1350, 1100, 200, 50);
		set(vene, x, y, 1350, 1150, 200, 50);

		ueb.resized();

	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		comm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cu.common = comm.isSelected();
				setData(cu);
			}
		});

		jli.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (changing || jli.getValueIsAdjusting())
					return;
				changing = true;
				if (jli.getSelectedIndex() == -1)
					jli.setSelectedIndex(0);
				setA(jli.getSelectedIndex());
				changing = false;
			}

		});

		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = cu.atks.length;
				int ind = jli.getSelectedIndex();
				AtkDataModel[] datas = new AtkDataModel[n + 1];
				for (int i = 0; i <= ind; i++)
					datas[i] = cu.atks[i];
				ind++;
				datas[ind] = new AtkDataModel(cu);
				for (int i = ind; i < n; i++)
					datas[i + 1] = cu.atks[i];
				cu.atks = datas;
				setData(cu);
				jli.setSelectedIndex(ind);
				setA(ind);
				changing = false;
			}
		});

		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = cu.atks.length;
				int ind = jli.getSelectedIndex();
				AtkDataModel[] datas = new AtkDataModel[n - 1];
				for (int i = 0; i < ind; i++)
					datas[i] = cu.atks[i];
				for (int i = ind + 1; i < n; i++)
					datas[i - 1] = cu.atks[i];
				cu.atks = datas;
				setData(cu);
				ind--;
				jli.setSelectedIndex(ind);
				setA(ind);
				changing = false;
			}
		});

		copy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = cu.atks.length;
				int ind = jli.getSelectedIndex();
				cu.atks = Arrays.copyOf(cu.atks, n + 1);
				cu.atks[n] = cu.atks[ind].clone();
				setData(cu);
				jli.setSelectedIndex(n);
				setA(n);
				changing = false;
			}
		});

		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = cu.atks.length;
				int ind = jli.getSelectedIndex();
				cu.atks = Arrays.copyOf(cu.atks, n + 1);
				cu.atks[n] = cu.atks[ind];
				setData(cu);
				jli.setSelectedIndex(n);
				setA(n);
				changing = false;
			}
		});

		isr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (changing)
					return;
				cu.isrange = isr.isSelected();
				setData(cu);
			}

		});

		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (changing)
					return;
				Form f = cu.getPack();
				f.anim = (AnimC) jcb.getSelectedItem();
				if (cu.getPost() < 1)
					for (AtkDataModel adm : cu.atks)
						adm.pre = 1;
				setData(cu);

			}

		});

		vuni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (editable)
					changePanel(new DIYViewPage(getThis(), new DIYAnim((AnimC) jcb.getSelectedItem())));
				else
					changePanel(new UnitViewPage(getThis(), form.unit));
			}

		});

		impt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(ufp = new UnitFindPage(getThis(), null));
			}

		});

		vene.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(efp = new EnemyFindPage(getThis(), null));
			}

		});

	}

	private void ini() {
		set(lhp);
		set(lhb);
		set(lsp);
		set(lra);
		set(lwd);
		set(lsh);
		set(ltb);
		set(lbs);
		set(ldr);
		set(llv);
		set(lrs);

		set(flv);
		set(fhp);
		set(fhb);
		set(fsp);
		set(fra);
		set(ftb);
		set(fdr);
		set(fsh);
		set(fwd);
		set(fbs);
		set(frs);
		ljp.end();
		add(jspi);
		add(aet);
		add(ueb);
		add(mpt);
		add(add);
		add(rem);
		add(copy);
		add(link);
		add(back);
		set(atkn);
		add(isr);
		set(lpst);
		set(vpst);
		set(litv);
		set(vitv);
		add(comm);
		add(vuni);
		add(impt);
		add(vene);
		set(ldps);
		set(vdps);
		if (editable) {
			add(jcb);
			Vector<AnimC> vda = new Vector<>();
			AnimC ac = ((AnimC) form.anim);
			if (!ac.inPool)
				vda.add(ac);
			vda.addAll(DIYAnim.getAnims());
			jcb.setModel(new DefaultComboBoxModel<>(vda));
		}
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
		addListeners();
		isr.setEnabled(editable);
		add.setEnabled(editable);
		rem.setEnabled(editable);
		copy.setEnabled(editable);
		link.setEnabled(editable);
		atkn.setEnabled(editable);
		comm.setEnabled(editable);
	}

	private void input(JTF jtf, String text) {
		double mul = form.unit.lv.getMult(lv);
		double def = bas.t().getDefMulti();
		if (jtf == atkn) {
			AtkDataModel adm = aet.adm;
			if (adm == null || adm.str.equals(text))
				return;
			text = cu.getAvailable(text);
			adm.str = text;
			return;
		}
		if (text.length() > 0) {
			int v = Reader.parseIntN(text);
			if (jtf == fhp) {
				v /= mul * def;
				if (v <= 0)
					v = 1;
				cu.hp = v;
			}
			if (jtf == fhb) {
				if (v <= 0)
					v = 1;
				if (v > cu.hp)
					v = cu.hp;
				cu.hb = v;
			}
			if (jtf == fsp) {
				if (v < 0)
					v = 0;
				if (v > 150)
					v = 150;
				cu.speed = v;
			}
			if (jtf == fra) {
				if (v <= 0)
					v = 1;
				cu.range = v;
			}
			if (jtf == fwd) {
				if (v <= 0)
					v = 1;
				cu.width = v;
			}
			if (jtf == fsh) {
				if (v < 0)
					v = 0;
				cu.shield = v;
			}
			if (jtf == ftb) {
				if (v < 0)
					v = 0;
				cu.tba = v;
			}
			if (jtf == fbs) {
				if (v < 0)
					v = 0;
				cu.base = v;
			}
			if (jtf == fdr)
				cu.price = (int) (v / 1.5);
			if (jtf == flv) {
				if (v <= 0)
					v = 1;
				lv = v;
			}
			if (jtf == frs) {
				if (v <= 60)
					v = 60;
				cu.resp = bas.t().getRevRes(v);
			}

		}
		setData(cu);
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
				input(jtf, jtf.getText().trim());
				setData(cu);
			}

		});

	}

	private void setA(int ind) {
		double mul = form.unit.lv.getMult(lv);
		double atk = bas.t().getAtkMulti();
		AtkDataModel adm = cu.atks[ind];
		atkn.setText(adm.str);
		aet.setData(adm, mul * atk);
		rem.setEnabled(editable && ind != 0);
	}

	private void setData(CustomUnit data) {
		changing = true;
		cu = data;
		double mul = form.unit.lv.getMult(lv);
		double def = bas.t().getDefMulti();
		double atk = bas.t().getAtkMulti();
		fhp.setText("" + (int) (cu.hp * def * mul));
		fhb.setText("" + cu.hb);
		fsp.setText("" + cu.speed);
		fra.setText("" + cu.range);
		fwd.setText("" + cu.width);
		fsh.setText("" + cu.shield);
		ftb.setText("" + cu.tba);
		fbs.setText("" + cu.base);
		flv.setText("" + lv);
		frs.setText("" + bas.t().getFinRes(cu.getRespawn()));
		isr.setSelected(cu.isrange);
		fdr.setText("" + (int) (cu.getPrice() * 1.5));
		vpst.setText("" + cu.getPost());
		vitv.setText("" + cu.getItv());
		comm.setSelected(data.common);
		int imu = 0;
		for (int i = 0; i < ABIIND.length; i++) {
			int id = ABIIND[i] - 100;
			if (cu.getProc(id)[0] == 100)
				imu |= 1 << id - IMUSFT;
		}
		ueb.setData(new int[] { cu.type, cu.abi, imu });
		mpt.setData(cu.rep.proc);
		String[] ints = new String[cu.atks.length];
		for (int i = 0; i < ints.length; i++)
			ints[i] = i + 1 + " " + cu.atks[i].str;
		int ind = jli.getSelectedIndex();
		jli.setListData(ints);
		if (ind < 0)
			ind = 0;
		if (ind >= ints.length)
			ind = ints.length - 1;
		setA(ind);
		jli.setSelectedIndex(ind);
		add.setEnabled(editable && cu.getPost() > 1);
		Form frm = cu.getPack();
		if (editable)
			jcb.setSelectedItem(frm.anim);
		vdps.setText("" + (int) (cu.allAtk() * 30 * atk * mul / cu.getItv()));
		changing = false;
	}

}
