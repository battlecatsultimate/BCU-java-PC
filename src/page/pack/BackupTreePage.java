package page.pack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import io.InStream;
import io.ZipAccess;
import main.Opts;
import page.JBTN;
import page.Page;
import page.info.TreaTable;
import page.support.Exporter;
import util.Interpret;
import util.anim.MaAnim;
import util.anim.Part;
import util.basis.BasisLU;
import util.basis.BasisSet;
import util.entity.data.AtkDataModel;
import util.entity.data.CustomEntity;
import util.pack.Background;
import util.pack.Pack;
import util.stage.CharaGroup;
import util.stage.LvRestrict;
import util.stage.Stage;
import util.stage.StageMap;
import util.system.Backup;
import util.system.files.BackupData;
import util.system.files.VFile;
import util.system.files.VFileRoot;
import util.unit.Enemy;
import util.unit.Form;
import util.unit.Unit;
import util.unit.UnitLevel;

public class BackupTreePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN read = new JBTN(0, "read list");
	private final JBTN dele = new JBTN(0, "delete");
	private final JBTN rest = new JBTN(0, "restore");
	private final JBTN entr = new JBTN(0, "enter");
	private final JBTN sntr = new JBTN(0, "enter");
	private final JBTN diff = new JBTN(0, "diff");
	private final JBTN vers = new JBTN(0, "versions");
	private final JBTN rept = new JBTN(0, "restore partial");
	private final JBTN expt = new JBTN(0, "export");
	private final JLabel jln = new JLabel();
	private final JLabel jli = new JLabel();
	private final JList<Backup> jlm = new JList<>();
	private final JTree jls = new JTree();
	private final JTree jlf = new JTree();
	private final JScrollPane jspm = new JScrollPane(jlm);
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JScrollPane jspf = new JScrollPane(jlf);

	private Backup backup;
	private VFileRoot<BackupData> vf;
	private VFile<BackupData> sel;
	private boolean changing;

	public BackupTreePage(Page p, boolean ntr) {
		super(p);

		ini(ntr);
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspm, x, y, 50, 100, 400, 800);
		set(read, x, y, 500, 100, 200, 50);
		set(rest, x, y, 500, 200, 200, 50);
		set(entr, x, y, 500, 300, 200, 50);
		set(dele, x, y, 500, 400, 200, 50);
		set(diff, x, y, 500, 500, 200, 50);
		set(vers, x, y, 500, 600, 200, 50);

		set(jln, x, y, 750, 50, 750, 50);
		set(jsps, x, y, 750, 100, 400, 800);
		set(rept, x, y, 1200, 100, 200, 50);
		set(expt, x, y, 1200, 200, 200, 50);
		set(sntr, x, y, 1200, 300, 200, 50);

		set(jli, x, y, 1450, 50, 750, 50);
		set(jspf, x, y, 1450, 100, 400, 800);

	}

	private void addListeners$0() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		read.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setList();
			}
		});

		dele.setLnr(x -> {
			if (jlm.getSelectedValue() == null)
				return;
			if (!Opts.conf())
				return;
			try {
				List<Backup> strs = jlm.getSelectedValuesList();
				if (ZipAccess.delete(strs))
					Opts.success("delete success");
				else
					Opts.backupErr("delete");
				jlm.setListData(ZipAccess.getList().toArray(new Backup[0]));
			} catch (IOException e) {
				e.printStackTrace();
				Opts.backupErr("delete");
			}
		});

		rest.setLnr(x -> {
			if (jlm.getSelectedValue() == null)
				return;
			try {
				if (ZipAccess.extract(jlm.getSelectedValue())) {
					Opts.success("restoration succeed, please restart program");
				} else
					Opts.backupErr("restore");
			} catch (IOException e) {
				e.printStackTrace();
				Opts.backupErr("restore");
			}
		});

		entr.setLnr(x -> {
			if (jlm.getSelectedValue() == null)
				return;
			try {
				vf = ZipAccess.extractList(jlm.getSelectedValue());
				if (vf == null)
					Opts.backupErr("read");
				else
					setTree(vf);
			} catch (IOException e) {
				e.printStackTrace();
				Opts.backupErr("read");
			}

		});

		diff.setLnr(x -> {
			if (jlm.getSelectedValue() == null)
				return;
			try {
				Backup str0 = jlm.getSelectedValue();
				List<Backup> lstr = jlm.getSelectedValuesList();
				lstr.remove(str0);
				Backup str1 = lstr.get(0);
				vf = ZipAccess.difference(str0.time, str1.time);
				if (vf == null)
					Opts.backupErr("read");
				else
					setTree(vf);
			} catch (IOException e) {
				e.printStackTrace();
				Opts.backupErr("read");
			}
		});

		vers.setLnr(x -> {
			try {
				vf = ZipAccess.extractAllList();
				if (vf == null)
					Opts.backupErr("read");
				else
					setTree(vf);
			} catch (IOException e) {
				e.printStackTrace();
				Opts.backupErr("read");
			}
		});

		jlm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlm.getValueIsAdjusting())
					return;
				setBackup(jlm.getSelectedValue());
			}

		});

	}

	private void addListeners$1() {

		sntr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sel.getData() == null)
					return;
				String s0 = sel.getName();
				String s1 = sel.getParent().getName();
				boolean b0 = s0.equals("basis.v") || s1.equals("basis.v");
				boolean b1 = s0.endsWith(".bcuenemy") || s1.endsWith(".bcuenemy");
				boolean b2 = s0.endsWith(".maanim") || s1.endsWith(".maanim");
				if (!b0 && !b1 && !b2)
					return;

				if (b0) {
					InStream is = sel.getData().getIS();
					if (is == null)
						Opts.backupErr("read");
					setT$Basis(is);
				}
				if (b1) {
					InStream is = sel.getData().getIS();
					if (is == null)
						Opts.backupErr("read");
					setT$Pack(is);
				}
				if (b2) {
					Queue<String> qs = sel.getData().readLine();
					if (qs == null)
						Opts.backupErr("read");
					setT$MA(qs);
				}
			}

		});

		rept.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (ZipAccess.extractPartial(sel.getData().toString(), sel)) {
						Opts.success("restoration succeed, please restart program");
					} else
						Opts.backupErr("restore");
				} catch (IOException e) {
					e.printStackTrace();
					Opts.backupErr("restore");
				}
			}

		});

		expt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String[] strs = sel.getName().split("\\.");
				new Exporter(backup.time, sel.getPath(), strs[strs.length - 1], Exporter.EXP_BAC);
			}

		});

		jls.addTreeSelectionListener(new TreeSelectionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				if (changing)
					return;
				Object obj = null;
				TreePath tp = jls.getSelectionPath();
				if (tp != null) {
					obj = tp.getLastPathComponent();
					if (obj != null)
						obj = ((DefaultMutableTreeNode) obj).getUserObject();
					sel = obj instanceof VFile ? (VFile<BackupData>) obj : null;
				} else
					sel = null;
				setSele();
			}

		});

	}

	private void addTree(DefaultMutableTreeNode par, VFile<BackupData> vf) {
		for (VFile<BackupData> c : vf.list()) {
			DefaultMutableTreeNode cur = new DefaultMutableTreeNode(c);
			par.add(cur);
			if (c.list() != null)
				addTree(cur, c);
		}
	}

	private void ini(boolean ntr) {
		add(back);
		add(jspm);
		add(read);
		add(dele);
		add(rest);
		add(jsps);
		add(entr);
		add(rept);
		add(expt);
		add(jln);
		add(diff);
		add(vers);
		if (ntr) {
			add(jli);
			add(jspf);
			add(sntr);
			setT();
		}
		setList();
		setTree(null);
		addListeners$0();
		addListeners$1();
	}

	private void setBackup(Backup bac) {
		if (backup != bac) {
			if (vf != null) {
				sel = vf = null;
				setTree(null);
			}
			backup = bac;
			if (backup != jlm.getSelectedValue()) {
				boolean boo = changing;
				changing = true;
				jlm.setSelectedValue(backup, true);
				changing = boo;
			}
		}
		boolean b = backup != null;
		entr.setEnabled(b);
		dele.setEnabled(b);
		rest.setEnabled(b);
		diff.setEnabled(jlm.getSelectedValuesList().size() > 1);
	}

	private void setList() {
		try {
			changing = true;
			jlm.setListData(ZipAccess.getList().toArray(new Backup[0]));
			changing = false;
		} catch (IOException e) {
			e.printStackTrace();
			Opts.backupErr("read");
		}
		setBackup(null);
	}

	private void setSele() {
		boolean b = sel != null;
		rept.setEnabled(b);
		expt.setEnabled(b && sel.list() == null);
		if (b && sel.getData() != null) {
			String size = "Size: ";
			if (sel.getData().size < 10000)
				size += sel.getData().size + " Bytes";
			else if (sel.getData().size < 10000000)
				size += (sel.getData().size >> 10) + " KB";
			else
				size += (sel.getData().size >> 20) + " MB";
			jli.setText(size);
		} else
			jli.setText("");

	}

	private void setT() {
		jlf.setModel(new DefaultTreeModel(null));
	}

	private void setT$Basis(InStream is) {
		BasisSet[] bas = BasisSet.readBackup(is);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("basis.v/");
		for (BasisSet bs : bas) {
			if (bs == null)
				continue;
			DefaultMutableTreeNode nbs = new DefaultMutableTreeNode(bs.name + "/");
			top.add(nbs);
			DefaultMutableTreeNode ntr = new DefaultMutableTreeNode("treasure/");
			nbs.add(ntr);
			for (int i = 0; i < Interpret.TREA.length; i++) {
				int ind = Interpret.TIND[i];
				String str = Interpret.TREA[ind] + ": " + TreaTable.tos(Interpret.getValue(ind, bs.t()), i + 1);
				DefaultMutableTreeNode net = new DefaultMutableTreeNode(str);
				ntr.add(net);
			}
			DefaultMutableTreeNode nlu = new DefaultMutableTreeNode("lineups/");
			nbs.add(nlu);
			for (BasisLU blu : bs.lb) {
				DefaultMutableTreeNode nli = new DefaultMutableTreeNode(blu.name + "/");
				nlu.add(nli);
				DefaultMutableTreeNode nlv = new DefaultMutableTreeNode("levels/");
				nli.add(nlv);
				for (Entry<Unit, int[]> e : blu.lu.map.entrySet()) {
					Unit u = e.getKey();
					Form f = u.forms[u.forms.length - 1];
					String str = u.toString() + " - " + f.lvText(e.getValue())[0];
					DefaultMutableTreeNode nuv = new DefaultMutableTreeNode(str);
					nlv.add(nuv);
				}
				int[] nyc = blu.nyc;
				String strcs = "castle: " + nyc[0] + ", " + nyc[1] + ", " + nyc[2];
				DefaultMutableTreeNode ncs = new DefaultMutableTreeNode(strcs);
				nli.add(ncs);
				DefaultMutableTreeNode nlp = new DefaultMutableTreeNode("lineup/");
				nli.add(nlp);
				for (Form[] fs : blu.lu.fs)
					for (Form f : fs)
						if (f != null) {
							DefaultMutableTreeNode nf = new DefaultMutableTreeNode(f.toString());
							nlp.add(nf);
						}
			}

		}
		jlf.setModel(new DefaultTreeModel(top));
	}

	private void setT$Entity(DefaultMutableTreeNode top, CustomEntity ce) {
		DefaultMutableTreeNode ti = new DefaultMutableTreeNode("info/");
		top.add(ti);
		ti.add(new DefaultMutableTreeNode("hp: " + ce.getHp()));
		ti.add(new DefaultMutableTreeNode("hb: " + ce.getHb()));
		ti.add(new DefaultMutableTreeNode("speed: " + ce.getSpeed()));
		ti.add(new DefaultMutableTreeNode("range: " + ce.getRange()));
		ti.add(new DefaultMutableTreeNode("barrier: " + ce.getShield()));
		for (int i = 0; i < ce.getAtkCount(); i++) {
			AtkDataModel am = (AtkDataModel) ce.getAtkModel(i);
			ti = new DefaultMutableTreeNode(am + "/");
			top.add(ti);
			ti.add(new DefaultMutableTreeNode("atk: " + am.atk));
			ti.add(new DefaultMutableTreeNode("pre: " + am.pre));
			ti.add(new DefaultMutableTreeNode("LD: " + am.ld0 + " - " + am.ld1));
		}
	}

	private void setT$MA(Queue<String> qs) {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("maanim/");
		MaAnim ma = null;
		try {
			ma = new MaAnim(qs);
		} catch (Exception e) {
		}
		if (ma != null) {
			for (Part p : ma.parts) {
				DefaultMutableTreeNode prt = new DefaultMutableTreeNode(p.name + "/");
				top.add(prt);
			}
		}
		jlf.setModel(new DefaultTreeModel(top));
	}

	private void setT$Pack(InStream is) {
		Pack p = new Pack(is, false);
		p.zreadt();
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(p + "/");
		DefaultMutableTreeNode tene = new DefaultMutableTreeNode("enemies/");
		top.add(tene);
		for (Enemy e : p.es.getList()) {
			DefaultMutableTreeNode te = new DefaultMutableTreeNode(e + "/");
			tene.add(te);
			setT$Entity(te, (CustomEntity) e.de);
		}
		DefaultMutableTreeNode tuni = new DefaultMutableTreeNode("units/");
		top.add(tuni);
		for (Unit u : p.us.ulist.getList()) {
			DefaultMutableTreeNode tu = new DefaultMutableTreeNode(u + "/");
			tuni.add(tu);
			for (Form f : u.forms) {
				DefaultMutableTreeNode tf = new DefaultMutableTreeNode(f + "/");
				tu.add(tf);
				setT$Entity(tf, (CustomEntity) f.du);
			}
		}
		DefaultMutableTreeNode tulv = new DefaultMutableTreeNode("UnitLevel/");
		top.add(tulv);
		for (UnitLevel ul : p.us.lvlist.getList()) {
			DefaultMutableTreeNode tlv = new DefaultMutableTreeNode(ul.toString());
			tlv.add(tlv);
		}
		DefaultMutableTreeNode tmc = new DefaultMutableTreeNode("stages/");
		top.add(tmc);
		String[] stastr = get(1, "t", 7);
		for (StageMap sm : p.mc.maps) {
			DefaultMutableTreeNode tsm = new DefaultMutableTreeNode(sm + "/");
			tmc.add(tsm);
			for (Stage st : sm.list) {
				DefaultMutableTreeNode tst = new DefaultMutableTreeNode(st + "/");
				tsm.add(tst);
				int[][] info = st.data.getSimple();
				Object[][] data = new Object[info.length][7];
				for (int i = 0; i < info.length; i++) {
					int ind = info.length - i - 1;
					data[ind][1] = info[i][0];
					data[ind][0] = info[i][8] == 1 ? "boss " : "";
					data[ind][2] = info[i][9];
					data[ind][3] = info[i][1] == 0 ? "infinite" : info[i][1];
					data[ind][4] = info[i][5] + "%";
					data[ind][5] = info[i][2];
					if (info[i][3] == info[i][4])
						data[ind][6] = info[i][3];
					else
						data[ind][6] = info[i][3] + "~" + info[i][4];
				}
				for (Object[] dat : data) {
					DefaultMutableTreeNode te = new DefaultMutableTreeNode("" + dat[0] + dat[1] + "/");
					tst.add(te);
					for (int i = 2; i < dat.length; i++) {
						DefaultMutableTreeNode ti = new DefaultMutableTreeNode(stastr[i] + ": " + dat[i]);
						te.add(ti);
					}
				}
			}
		}
		DefaultMutableTreeNode tbg = new DefaultMutableTreeNode("background/");
		top.add(tbg);
		for (Background bg : p.bg.getList()) {
			DefaultMutableTreeNode tb = new DefaultMutableTreeNode(bg + "/");
			tbg.add(tb);
		}
		DefaultMutableTreeNode tcg = new DefaultMutableTreeNode("CharaGroup/");
		top.add(tcg);
		for (CharaGroup cg : p.mc.groups.getList()) {
			DefaultMutableTreeNode tc = new DefaultMutableTreeNode(cg + "/");
			tcg.add(tc);
			for (Unit u : cg.set) {
				DefaultMutableTreeNode tu = new DefaultMutableTreeNode(u + "/");
				tc.add(tu);
			}
		}
		DefaultMutableTreeNode tlr = new DefaultMutableTreeNode("LvRestrict/");
		for (LvRestrict lr : p.mc.lvrs.getList()) {
			DefaultMutableTreeNode tl = new DefaultMutableTreeNode(lr + "/");
			tlr.add(tl);
			for (CharaGroup cg : lr.res.keySet()) {
				String str = cg.pack.id + cg.toString() + ": " + Form.lvString(lr.res.get(cg));
				DefaultMutableTreeNode tc = new DefaultMutableTreeNode(str);
				tl.add(tc);
			}
			String str = "General: " + Form.lvString(lr.all);
			DefaultMutableTreeNode tc = new DefaultMutableTreeNode(str);
			tl.add(tc);

			for (int i = 0; i < lr.rares.length; i++) {
				str = Interpret.RARITY[i] + ": " + Form.lvString(lr.rares[i]);
				tc = new DefaultMutableTreeNode(str);
				tl.add(tc);
			}
		}
		top.add(tlr);
		jlf.setModel(new DefaultTreeModel(top));
	}

	private void setTree(VFileRoot<BackupData> vfr) {
		if (vfr == null) {
			jls.setModel(new DefaultTreeModel(null));
			return;
		}
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(backup + "/");
		addTree(top, vfr);
		jls.setModel(new DefaultTreeModel(top));
	}

}
