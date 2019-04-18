package page.info.edit;

import static util.Data.AB_GLASS;
import static util.Interpret.EABIIND;
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
<<<<<<< HEAD
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.anim.DIYViewPage;
import page.info.StageFilterPage;
import page.info.filter.EnemyEditBox;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import page.support.ListJtfPolicy;
import page.view.EnemyViewPage;
import util.anim.AnimC;
import util.basis.Basis;
import util.basis.BasisSet;
import util.entity.data.AtkDataModel;
import util.entity.data.CustomEnemy;
import util.unit.DIYAnim;
import util.unit.Enemy;
import util.unit.Form;

public class EnemyEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");

	private final JL lhp = new JL(1, "HP");
	private final JL lhb = new JL(1, "HB");
	private final JL lsp = new JL(1, "speed");
	private final JL lra = new JL(1, "range");
	private final JL lwd = new JL(1, "width");
	private final JL lsh = new JL(1, "shield");
	private final JL ltb = new JL(1, "TBA");
	private final JL lbs = new JL(1, "tbase");
	private final JL ldr = new JL(1, "drop");
	private final JTF fhp = new JTF();
	private final JTF fhb = new JTF();
	private final JTF fsp = new JTF();
	private final JTF fra = new JTF();
	private final JTF fwd = new JTF();
	private final JTF fsh = new JTF();
	private final JTF ftb = new JTF();
	private final JTF fbs = new JTF();
	private final JTF fdr = new JTF();
	private final JTF fsr = new JTF();
	private final JTG isr = new JTG(1, "isr");
	private final JList<String> jli = new JList<>();
	private final JScrollPane jspi = new JScrollPane(jli);
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN link = new JBTN(0, "link");
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN appr = new JBTN(0, "stage");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vuni = new JBTN(0, "unit");
	private final JTG comm = new JTG(1, "common");
	private final JTF atkn = new JTF();
	private final JL lpst = new JL(1, "postaa");
	private final JLabel vpst = new JLabel();
	private final JL litv = new JL(1, "atkf");
=======
import page.JTF;
import page.JTG;
import page.Page;
import page.anim.DIYViewPage;
import page.info.InfoText;
import page.info.StageFilterPage;
import page.info.filter.EnemyEditBox;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import page.support.ListJtfPolicy;
import page.view.EnemyViewPage;
import util.anim.AnimC;
import util.basis.Basis;
import util.basis.BasisSet;
import util.entity.data.AtkDataModel;
import util.entity.data.CustomEnemy;
import util.unit.DIYAnim;
import util.unit.Enemy;
import util.unit.Form;

public class EnemyEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");

	private final JLabel lhp = new JLabel("HP");
	private final JLabel lhb = new JLabel("HB");
	private final JLabel lsp = new JLabel(InfoText.get("speed"));
	private final JLabel lra = new JLabel(InfoText.get("range"));
	private final JLabel lwd = new JLabel(InfoText.get("width"));
	private final JLabel lsh = new JLabel(InfoText.get("shield"));
	private final JLabel ltb = new JLabel(InfoText.get("TBA"));
	private final JLabel lbs = new JLabel(InfoText.get("tbase"));
	private final JLabel ldr = new JLabel(InfoText.get("drop"));
	private final JTF fhp = new JTF();
	private final JTF fhb = new JTF();
	private final JTF fsp = new JTF();
	private final JTF fra = new JTF();
	private final JTF fwd = new JTF();
	private final JTF fsh = new JTF();
	private final JTF ftb = new JTF();
	private final JTF fbs = new JTF();
	private final JTF fdr = new JTF();
	private final JTF fsr = new JTF();
	private final JTG isr = new JTG(1, "isr");
	private final JList<String> jli = new JList<>();
	private final JScrollPane jspi = new JScrollPane(jli);
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN link = new JBTN(0, "link");
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN appr = new JBTN(0, "stage");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN vuni = new JBTN(0, "unit");
	private final JTG comm = new JTG(1, "common");
	private final JTF atkn = new JTF();
	private final JLabel lpst = new JLabel(InfoText.get("postaa"));
	private final JLabel vpst = new JLabel();
	private final JLabel litv = new JLabel(InfoText.get("atkf"));
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
	private final JLabel vitv = new JLabel();
	private final JComboBox<AnimC> jcb = new JComboBox<>();
	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final AtkEditTable aet;
	private final MainProcTable mpt;
	private final EnemyEditBox eeb;
	private final boolean editable;
	private final Enemy ene;

	private boolean changing = false;
	private EnemyFindPage efp;
	private UnitFindPage ufp;

	protected Basis bas = BasisSet.current;
	protected CustomEnemy ce;

	public EnemyEditPage(Page p, Enemy e, boolean edit) {
		super(p);

		ene = e;
		aet = new AtkEditTable(this, edit, false);
		mpt = new MainProcTable(this, edit);
		eeb = new EnemyEditBox(this, edit);
		editable = edit;
		ini();
		setData((CustomEnemy) e.de);
		resized();
	}

	@Override
	public void callBack(Object o) {
		if (o instanceof int[]) {
			int[] vals = (int[]) o;
			if (vals.length == 3) {
				ce.type = vals[0];
				ce.abi = vals[1];
				for (int i = 0; i < EABIIND.length; i++)
					if (EABIIND[i] > 100) {
						int id = EABIIND[i] - 100;
						if ((vals[2] & (1 << id - IMUSFT)) > 0)
							ce.getProc(id)[0] = 100;
						else
							ce.getProc(id)[0] = 0;
					}
				ce.loop = (ce.abi & AB_GLASS) > 0 ? 1 : -1;
			}
		}
		setData(ce);
	}

	@Override
	protected void renew() {
		if (efp != null && efp.getEnemy() != null
				&& MainBCU.warning("do you want to overwrite stats? This operation cannot be undone", "confirmation")) {
			Enemy e = efp.getEnemy();
			ce.importData(e.de);
			setData(ce);
		}
		if (ufp != null && ufp.getForm() != null
				&& MainBCU.warning("do you want to overwrite stats? This operation cannot be undone", "confirmation")) {
			Form f = ufp.getForm();
			ce.importData(f.du);
			setData(ce);
		}
		efp = null;
		ufp = null;
	}

	@Override
	protected void resized(int x, int y) {
		setSize(x, y);
		set(back, x, y, 0, 0, 200, 50);
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

		set(mpt, x, y, 50, 650, 300, 600);
		set(eeb, x, y, 400, 50, 200, 1100);
		set(fsr, x, y, 400, 1200, 200, 50);
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
		set(jcb, x, y, 900, 1150, 400, 50);
		set(vene, x, y, 900, 1200, 200, 50);
		set(appr, x, y, 1100, 1200, 200, 50);

		set(impt, x, y, 1350, 1050, 200, 50);
		set(vuni, x, y, 1350, 1100, 200, 50);

		eeb.resized();

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
				ce.common = comm.isSelected();
				setData(ce);
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
				int n = ce.atks.length;
				int ind = jli.getSelectedIndex();
				AtkDataModel[] datas = new AtkDataModel[n + 1];
				for (int i = 0; i <= ind; i++)
					datas[i] = ce.atks[i];
				ind++;
				datas[ind] = new AtkDataModel(ce);
				for (int i = ind; i < n; i++)
					datas[i + 1] = ce.atks[i];
				ce.atks = datas;
				setData(ce);
				jli.setSelectedIndex(ind);
				setA(ind);
				changing = false;
			}
		});

		rem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = ce.atks.length;
				int ind = jli.getSelectedIndex();
				AtkDataModel[] datas = new AtkDataModel[n - 1];
				for (int i = 0; i < ind; i++)
					datas[i] = ce.atks[i];
				for (int i = ind + 1; i < n; i++)
					datas[i - 1] = ce.atks[i];
				ce.atks = datas;
				setData(ce);
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
				int n = ce.atks.length;
				int ind = jli.getSelectedIndex();
				ce.atks = Arrays.copyOf(ce.atks, n + 1);
				ce.atks[n] = ce.atks[ind].clone();
				setData(ce);
				jli.setSelectedIndex(n);
				setA(n);
				changing = false;
			}
		});

		link.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int n = ce.atks.length;
				int ind = jli.getSelectedIndex();
				ce.atks = Arrays.copyOf(ce.atks, n + 1);
				ce.atks[n] = ce.atks[ind];
				setData(ce);
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
				ce.isrange = isr.isSelected();
				setData(ce);
			}

		});

		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (changing)
					return;
				Enemy ene = ce.getPack();
				ene.anim = (AnimC) jcb.getSelectedItem();
				if (ce.getPost() < 1)
					for (AtkDataModel adm : ce.atks)
						adm.pre = 1;
				setData(ce);

			}

		});

		vene.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (editable)
					changePanel(new DIYViewPage(getThis(), new DIYAnim((AnimC) jcb.getSelectedItem())));
				else
					changePanel(new EnemyViewPage(getThis(), ene));
			}

		});

		appr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new StageFilterPage(getThis(), ene.findApp()));
			}

		});

		impt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(efp = new EnemyFindPage(getThis(), null));
			}

		});

		vuni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(ufp = new UnitFindPage(getThis(), null));
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

		set(fhp);
		set(fhb);
		set(fsp);
		set(fra);
		set(ftb);
		set(fdr);
		set(fsh);
		set(fwd);
		set(fbs);
		set(fsr);
		ljp.end();
		add(jspi);
		add(aet);
		add(eeb);
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
		add(vene);
		add(appr);
		add(impt);
		add(vuni);
		if (editable) {
			add(jcb);
			Vector<AnimC> vda = new Vector<>();
			AnimC ac = ((AnimC) ene.anim);
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

		if (jtf == atkn) {
			AtkDataModel adm = aet.adm;
			if (adm == null || adm.str.equals(text))
				return;
			text = ce.getAvailable(text);
			adm.str = text;
			return;
		}
		if (text.length() > 0) {
			int v = Reader.parseIntN(text);
			if (jtf == fhp) {
				if (v <= 0)
					v = 1;
				ce.hp = v;
			}
			if (jtf == fhb) {
				if (v <= 0)
					v = 1;
				if (v > ce.hp)
					v = ce.hp;
				ce.hb = v;
			}
			if (jtf == fsp) {
				if (v < 0)
					v = 0;
				if (v > 150)
					v = 150;
				ce.speed = v;
			}
			if (jtf == fra) {
				if (v <= 0)
					v = 1;
				ce.range = v;
			}
			if (jtf == fwd) {
				if (v <= 0)
					v = 1;
				ce.width = v;
			}
			if (jtf == fsh) {
				if (v < 0)
					v = 0;
				ce.shield = v;
			}
			if (jtf == ftb) {
				if (v < 0)
					v = 0;
				ce.tba = v;
			}
			if (jtf == fbs) {
				if (v < 0)
					v = 0;
				ce.base = v;
			}
			if (jtf == fdr) {
				int act = (int) (v / bas.t().getDropMulti());
				ce.drop = act;
			}
			if (jtf == fsr) {
				if (v < 0)
					v = 0;
				if (v > 4)
					v = 1;
				ce.star = v;
			}
		}
		setData(ce);
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
				setData(ce);
			}

		});

	}

	private void setA(int ind) {
		AtkDataModel adm = ce.atks[ind];
		atkn.setText(adm.str);
		aet.setData(adm, 1);
		rem.setEnabled(editable && ind != 0);
	}

	private void setData(CustomEnemy data) {
		changing = true;
		ce = data;
		fhp.setText("" + ce.hp);
		fhb.setText("" + ce.hb);
		fsp.setText("" + ce.speed);
		fra.setText("" + ce.range);
		fwd.setText("" + ce.width);
		fsh.setText("" + ce.shield);
		ftb.setText("" + ce.tba);
		fbs.setText("" + ce.base);
		fsr.setText("star: " + ce.star);
		isr.setSelected(ce.isrange);
		fdr.setText("" + (int) (ce.getDrop() * bas.t().getDropMulti()));
		vpst.setText("" + ce.getPost());
		vitv.setText("" + ce.getItv());
		comm.setSelected(data.common);
		int imu = 0;
		for (int i = 0; i < EABIIND.length; i++)
			if (EABIIND[i] > 100) {
				int id = EABIIND[i] - 100;
				if (ce.getProc(id)[0] == 100)
					imu |= 1 << id - IMUSFT;
			}
		eeb.setData(new int[] { ce.type, ce.abi, imu });
		mpt.setData(ce.rep.proc);
		String[] ints = new String[ce.atks.length];
		for (int i = 0; i < ints.length; i++)
			ints[i] = i + 1 + " " + ce.atks[i].str;
		int ind = jli.getSelectedIndex();
		jli.setListData(ints);
		if (ind < 0)
			ind = 0;
		if (ind >= ints.length)
			ind = ints.length - 1;
		setA(ind);
		jli.setSelectedIndex(ind);
		add.setEnabled(editable && ce.getPost() > 1);
		Enemy ene = ce.getPack();
		if (editable)
			jcb.setSelectedItem(ene.anim);
		changing = false;
	}

}
