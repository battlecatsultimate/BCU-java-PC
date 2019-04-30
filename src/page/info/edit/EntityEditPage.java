package page.info.edit;

import static util.Data.*;
import static util.Interpret.ABIIND;
import static util.Interpret.IMUSFT;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.JTG;
import page.Page;
import page.anim.DIYViewPage;
import page.info.filter.EnemyFindPage;
import page.info.filter.UnitFindPage;
import page.support.ListJtfPolicy;
import page.support.ReorderList;
import page.support.ReorderListener;
import page.view.EnemyViewPage;
import page.view.UnitViewPage;
import util.Animable;
import util.anim.AnimC;
import util.anim.AnimU;
import util.basis.Basis;
import util.basis.BasisSet;
import util.entity.data.AtkDataModel;
import util.entity.data.CustomEntity;
import util.pack.Soul;
import util.pack.SoulStore;
import util.unit.DIYAnim;
import util.unit.Enemy;
import util.unit.Form;
import util.unit.Unit;

public abstract class EntityEditPage extends Page {

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
	private final JL ltp = new JL(1, "type");
	private final JTF fhp = new JTF();
	private final JTF fhb = new JTF();
	private final JTF fsp = new JTF();
	private final JTF fra = new JTF();
	private final JTF fwd = new JTF();
	private final JTF fsh = new JTF();
	private final JTF ftb = new JTF();
	private final JTF fbs = new JTF();
	private final JTF ftp = new JTF();
	private final JTG isr = new JTG(1, "isr");
	private final ReorderList<String> jli = new ReorderList<>();
	private final JScrollPane jspi = new JScrollPane(jli);
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN link = new JBTN(0, "link");
	private final JTG comm = new JTG(1, "common");
	private final JTF atkn = new JTF();
	private final JL lpst = new JL(1, "postaa");
	private final JL vpst = new JL();
	private final JL litv = new JL(1, "atkf");
	private final JL lrev = new JL(1, "post-HB");
	private final JL lres = new JL(1, "post-death");
	private final JL vrev = new JL();
	private final JL vres = new JL();
	private final JL vitv = new JL();
	private final JComboBox<AnimC> jcb = new JComboBox<>();
	private final ListJtfPolicy ljp = new ListJtfPolicy();
	private final AtkEditTable aet;
	private final MainProcTable mpt;
	private final boolean editable;
	private final CustomEntity ce;

	private boolean changing = false;
	private EnemyFindPage efp;
	private UnitFindPage ufp;

	protected final Basis bas = BasisSet.current;

	public EntityEditPage(Page p, CustomEntity e, boolean edit) {
		super(p);
		ce = e;
		aet = new AtkEditTable(this, edit, false);
		mpt = new MainProcTable(this, edit);
		editable = edit;

	}

	@Override
	public void callBack(Object o) {
		if (o instanceof int[]) {
			int[] vals = (int[]) o;
			if (vals.length == 3) {
				ce.type = vals[0];
				ce.abi = vals[1];
				for (int i = 0; i < ABIIND.length; i++) {
					int id = ABIIND[i] - 100;
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

	protected double getAtk() {
		return 1;
	}

	protected double getDef() {
		return 1;
	}

	protected abstract void getInput(JTF jtf, int v);

	protected void ini() {
		set(lhp);
		set(lhb);
		set(lsp);
		set(lwd);
		set(lsh);
		set(lra);
		set(ltb);
		set(lbs);
		set(ltp);

		set(fhp);
		set(fhb);
		set(fsp);
		set(fsh);
		set(fwd);
		set(fra);
		set(ftb);
		set(fbs);
		set(ftp);
		ljp.end();
		add(jspi);
		add(aet);
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
		set(lrev);
		set(lres);
		set(vrev);
		set(vres);
		add(comm);
		if (editable) {
			add(jcb);
			Vector<AnimC> vda = new Vector<>();
			AnimC ac = ((AnimC) ce.getPack().anim);
			if (!ac.inPool)
				vda.add(ac);
			vda.addAll(DIYAnim.getAnims());
			jcb.setModel(new DefaultComboBoxModel<>(vda));
		}
		setFocusTraversalPolicy(ljp);
		setFocusCycleRoot(true);
		addListeners();
		atkn.setToolTipText("<html>use name \"revenge\" for attack during HB animation<br>"
				+ "use name \"resurrection\" for attack during death animation</html>");
		isr.setEnabled(editable);
		add.setEnabled(editable);
		rem.setEnabled(editable);
		copy.setEnabled(editable);
		link.setEnabled(editable);
		atkn.setEnabled(editable);
		comm.setEnabled(editable);
	}

	@Override
	protected void renew() {
		if (efp != null && efp.getEnemy() != null
				&& Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
			Enemy e = efp.getEnemy();
			ce.importData(e.de);
			setData(ce);
		}
		if (ufp != null && ufp.getForm() != null
				&& Opts.conf("do you want to overwrite stats? This operation cannot be undone")) {
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
		set(lsh, x, y, 50, 250, 100, 50);
		set(fsh, x, y, 150, 250, 200, 50);
		set(lwd, x, y, 50, 300, 100, 50);
		set(fwd, x, y, 150, 300, 200, 50);

		set(mpt, x, y, 50, 650, 300, 600);

		set(jspi, x, y, 550, 50, 300, 350);
		set(add, x, y, 550, 400, 300, 50);
		set(rem, x, y, 550, 450, 300, 50);
		set(copy, x, y, 550, 500, 300, 50);
		set(link, x, y, 550, 550, 300, 50);
		set(comm, x, y, 550, 600, 300, 50);
		set(atkn, x, y, 550, 650, 300, 50);

		set(lra, x, y, 550, 750, 100, 50);
		set(fra, x, y, 650, 750, 200, 50);
		set(ltb, x, y, 550, 800, 100, 50);
		set(ftb, x, y, 650, 800, 200, 50);
		set(isr, x, y, 550, 850, 300, 50);
		set(lbs, x, y, 550, 900, 100, 50);
		set(fbs, x, y, 650, 900, 200, 50);
		set(ltp, x, y, 550, 950, 100, 50);
		set(ftp, x, y, 650, 950, 200, 50);

		set(aet, x, y, 900, 50, 1400, 1000);

		set(lpst, x, y, 900, 1050, 200, 50);
		set(vpst, x, y, 1100, 1050, 200, 50);
		set(litv, x, y, 900, 1100, 200, 50);
		set(vitv, x, y, 1100, 1100, 200, 50);
		set(jcb, x, y, 900, 1150, 400, 50);

		set(lrev, x, y, 1600, 1050, 100, 50);
		set(vrev, x, y, 1700, 1050, 100, 50);
		set(lres, x, y, 1600, 1100, 100, 50);
		set(vres, x, y, 1700, 1100, 100, 50);

	}

	protected void set(JL jl) {
		jl.setHorizontalAlignment(SwingConstants.CENTER);
		add(jl);
	}

	protected void set(JTF jtf) {
		jtf.setEditable(editable);
		add(jtf);
		ljp.add(jtf);

		jtf.setLnr(e -> {
			input(jtf, jtf.getText().trim());
			setData(ce);
		});

	}

	protected void setData(CustomEntity data) {
		changing = true;
		fhp.setText("" + (int) (ce.hp * getDef()));
		fhb.setText("" + ce.hb);
		fsp.setText("" + ce.speed);
		fra.setText("" + ce.range);
		fwd.setText("" + ce.width);
		fsh.setText("" + ce.shield);
		ftb.setText("" + ce.tba);
		fbs.setText("" + ce.base);
		isr.setSelected(ce.isrange);
		vpst.setText("" + ce.getPost());
		vitv.setText("" + ce.getItv());
		ftp.setText("" + ce.touch);
		comm.setSelected(data.common);
		mpt.setData(ce.rep.proc);
		int[][] raw = ce.rawAtkData();
		int pre = 0;
		int n = ce.atks.length;
		if (ce.rev != null)
			n++;
		if (ce.res != null)
			n++;
		String[] ints = new String[n];
		for (int i = 0; i < ce.atks.length; i++) {
			ints[i] = i + 1 + " " + ce.atks[i].str;
			pre += raw[i][1];
			if (pre >= ce.getAnimLen())
				ints[i] += " (out of range)";
		}
		int ix = ce.atks.length;
		if (ce.rev != null)
			ints[ix++] = ce.rev.str;
		if (ce.res != null)
			ints[ix++] = ce.res.str;
		int ind = jli.getSelectedIndex();
		jli.setListData(ints);
		if (ind < 0)
			ind = 0;
		if (ind >= ints.length)
			ind = ints.length - 1;
		setA(ind);
		jli.setSelectedIndex(ind);
		Animable<AnimU> ene = ce.getPack();
		if (editable)
			jcb.setSelectedItem(ene.anim);
		vrev.setText(ce.rev == null ? "-" : (KB_TIME[INT_HB] - ce.rev.pre + "f"));
		Soul s=SoulStore.getSoul(ce.death);
		vres.setText(ce.res == null ? "-" : s == null ? "x" : (s.len(0) - ce.res.pre + "f"));
		changing = false;
	}

	protected void subListener(JBTN e, JBTN u, JBTN a, Object o) {
		e.setLnr(x -> changePanel(efp = new EnemyFindPage(getThis(), null)));

		u.setLnr(x -> changePanel(ufp = new UnitFindPage(getThis(), null)));

		a.setLnr(x -> {
			if (editable)
				changePanel(new DIYViewPage(getThis(), new DIYAnim((AnimC) jcb.getSelectedItem())));
			else if (o instanceof Unit)
				changePanel(new UnitViewPage(getThis(), (Unit) o));
			else if (o instanceof Enemy)
				changePanel(new EnemyViewPage(getThis(), (Enemy) o));
		});
	}

	private void addListeners() {

		back.setLnr(e -> changePanel(getFront()));

		comm.setLnr(e -> {
			ce.common = comm.isSelected();
			setData(ce);
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

		jli.list = new ReorderListener<String>() {

			@Override
			public void reordered(int ori, int fin) {
				if (ori < ce.atks.length) {
					if (fin >= ce.atks.length)
						fin = ce.atks.length - 1;
					List<AtkDataModel> l = new ArrayList<>();
					for (AtkDataModel adm : ce.atks)
						l.add(adm);
					l.add(fin, l.remove(ori));
					ce.atks = l.toArray(new AtkDataModel[0]);
				}
				setData(ce);
				changing = false;
			}

			@Override
			public void reordering() {
				changing = true;
			}

		};

		add.setLnr(e -> {
			changing = true;
			int n = ce.atks.length;
			int ind = jli.getSelectedIndex();
			if (ind >= ce.atks.length)
				ind = ce.atks.length - 1;
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
		});

		rem.setLnr(e -> remAtk(jli.getSelectedIndex()));

		copy.setLnr(e -> {
			changing = true;
			int n = ce.atks.length;
			int ind = jli.getSelectedIndex();
			ce.atks = Arrays.copyOf(ce.atks, n + 1);
			ce.atks[n] = ce.atks[ind].clone();
			setData(ce);
			jli.setSelectedIndex(n);
			setA(n);
			changing = false;
		});

		link.setLnr(e -> {
			changing = true;
			int n = ce.atks.length;
			int ind = jli.getSelectedIndex();
			ce.atks = Arrays.copyOf(ce.atks, n + 1);
			ce.atks[n] = ce.atks[ind];
			setData(ce);
			jli.setSelectedIndex(n);
			setA(n);
			changing = false;
		});

		isr.setLnr(e -> {
			if (changing)
				return;
			ce.isrange = isr.isSelected();
			setData(ce);
		});

		jcb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (changing)
					return;
				ce.getPack().anim = (AnimC) jcb.getSelectedItem();
				if (ce.getPost() < 1)
					for (AtkDataModel adm : ce.atks)
						adm.pre = 1;
				setData(ce);

			}

		});

	}

	private AtkDataModel get(int ind) {
		if (ind < ce.atks.length)
			return ce.atks[ind];
		else if (ind == ce.atks.length)
			return ce.rev == null ? ce.res : ce.rev;
		else
			return ce.res;
	}

	private void input(JTF jtf, String text) {

		if (jtf == atkn) {
			AtkDataModel adm = aet.adm;
			if (adm == null || adm.str.equals(text))
				return;
			text = ce.getAvailable(text);
			adm.str = text;
			if (text.equals("revenge")) {
				remAtk(adm);
				ce.rev = adm;
			}
			if (text.equals("resurrection")) {
				remAtk(adm);
				ce.res = adm;
			}
			return;
		}
		if (text.length() > 0) {
			int v = Reader.parseIntN(text);
			if (jtf == fhp) {
				v /= getDef();
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
			if (jtf == ftp) {
				if (v < 1)
					v = 1;
				ce.touch = v;
			}
			getInput(jtf, v);
		}
		setData(ce);
	}

	private void remAtk(AtkDataModel adm) {
		for (int i = 0; i < ce.atks.length; i++)
			if (ce.atks[i] == adm)
				remAtk(i);
	}

	private void remAtk(int ind) {
		changing = true;
		int n = ce.atks.length;
		if (ind >= n) {
			if (ind == n)
				if (ce.rev != null)
					ce.rev = null;
				else
					ce.res = null;
			else
				ce.res = null;
		} else {
			AtkDataModel[] datas = new AtkDataModel[n - 1];
			for (int i = 0; i < ind; i++)
				datas[i] = ce.atks[i];
			for (int i = ind + 1; i < n; i++)
				datas[i - 1] = ce.atks[i];
			ce.atks = datas;
		}
		setData(ce);
		ind--;
		if (ind < 0)
			ind = 0;
		jli.setSelectedIndex(ind);
		setA(ind);
		changing = false;
	}

	private void setA(int ind) {
		AtkDataModel adm = get(ind);
		assert adm != null;
		link.setEnabled(ind < ce.atks.length);
		copy.setEnabled(ind < ce.atks.length);
		atkn.setEnabled(ind < ce.atks.length);
		atkn.setText(adm.str);
		aet.setData(adm, getAtk());
		rem.setEnabled(editable && (ce.atks.length > 1 || ind >= ce.atks.length));
	}

}
