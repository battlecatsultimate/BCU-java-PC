package page.pack;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Writer;
import main.MainBCU;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;
import page.info.StageViewPage;
import page.info.edit.EnemyEditPage;
import page.info.edit.StageEditPage;
import page.support.AnimLCR;
import page.support.ReorderList;
import page.support.ReorderListener;
import page.view.BGViewPage;
import page.view.CastleViewPage;
import page.view.EnemyViewPage;
import page.view.MusicPage;
import util.entity.data.CustomEnemy;
import util.pack.Pack;
import util.stage.MapColc;
import util.stage.StageMap;
import util.unit.DIYAnim;
import util.unit.Enemy;

public class PackEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final Vector<Pack> vpack = new Vector<>(Pack.map.values());
	private final JList<Pack> jlp = new JList<>(vpack);
	private final JScrollPane jspp = new JScrollPane(jlp);
	private final JList<Enemy> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final JList<DIYAnim> jld = new JList<>(new Vector<>(DIYAnim.map.values()));
	private final JScrollPane jspd = new JScrollPane(jld);
	private final ReorderList<StageMap> jls = new ReorderList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JList<Pack> jlr = new JList<>();
	private final JScrollPane jspr = new JScrollPane(jlr);
	private final JList<Pack> jlt = new JList<>(vpack);
	private final JScrollPane jspt = new JScrollPane(jlt);

	private final JBTN addp = new JBTN(0, "add");
	private final JBTN remp = new JBTN(0, "rem");
	private final JBTN adde = new JBTN(0, "add");
	private final JBTN reme = new JBTN(0, "rem");
	private final JBTN adds = new JBTN(0, "add");
	private final JBTN rems = new JBTN(0, "rem");
	private final JBTN addr = new JBTN(0, "add");
	private final JBTN remr = new JBTN(0, "rem");
	private final JBTN edit = new JBTN(0, "edit");
	private final JBTN sdiy = new JBTN(0, "sdiy");
	private final JBTN vene = new JBTN(0, "vene");
	private final JBTN extr = new JBTN(0, "extr");
	private final JBTN vcas = new JBTN(0, "vcas");
	private final JBTN vbgr = new JBTN(0, "vbgr");
	private final JBTN vrcg = new JBTN(0, "recg");
	private final JBTN vrlr = new JBTN(0, "relr");
	private final JBTN cunt = new JBTN(0, "cunt");
	private final JBTN ener = new JBTN(0, "ener");
	private final JBTN vmcs = new JBTN(0, "vmsc");
	private final JTF jtfp = new JTF();
	private final JTF jtfe = new JTF();
	private final JTF jtfs = new JTF();

	private final JL lbp = new JL(0, "pack");
	private final JL lbe = new JL(0, "enemy");
	private final JL lbd = new JL(0, "seleanim");
	private final JL lbs = new JL(0, "stage");
	private final JL lbr = new JL(0, "parent");
	private final JL lbt = new JL(0, "selepar");

	private Pack pac;
	private Enemy ene;
	private StageMap sm;
	private boolean changing = false;

	public PackEditPage(Page p) {
		super(p);

		ini();
	}

	@Override
	protected void renew() {
		setPack(pac);
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);

		int w = 50, dw = 150;

		set(lbp, x, y, w, 100, 400, 50);
		set(jspp, x, y, w, 150, 400, 600);
		set(addp, x, y, w, 800, 200, 50);
		set(remp, x, y, w + 200, 800, 200, 50);
		set(jtfp, x, y, w, 850, 400, 50);
		set(extr, x, y, w, 950, 400, 50);

		w += 450;

		set(lbe, x, y, w, 100, 300, 50);
		set(jspe, x, y, w, 150, 300, 600);
		set(adde, x, y, w, 800, 150, 50);
		set(reme, x, y, w + dw, 800, 150, 50);
		set(jtfe, x, y, w, 850, 300, 50);
		set(edit, x, y, w, 950, 300, 50);
		set(vene, x, y, w, 1050, 300, 50);
		set(ener, x, y, w, 1150, 300, 50);

		w += 300;

		set(lbd, x, y, w, 100, 300, 50);
		set(jspd, x, y, w, 150, 300, 600);

		w += 50;

		set(vbgr, x, y, w, 850, 250, 50);
		set(vcas, x, y, w, 950, 250, 50);
		set(vrcg, x, y, w, 1050, 250, 50);
		set(vrlr, x, y, w, 1150, 250, 50);

		w += 300;

		set(lbs, x, y, w, 100, 300, 50);
		set(jsps, x, y, w, 150, 300, 600);
		set(adds, x, y, w, 800, 150, 50);
		set(rems, x, y, w + dw, 800, 150, 50);
		set(jtfs, x, y, w, 850, 300, 50);
		set(sdiy, x, y, w, 950, 300, 50);
		set(cunt, x, y, w, 1050, 300, 50);
		set(vmcs,x,y,w,1150,300,50);

		w += 350;

		set(lbr, x, y, w, 100, 350, 50);
		set(jspr, x, y, w, 150, 350, 600);
		set(addr, x, y, w, 800, 175, 50);
		set(remr, x, y, w + 175, 800, 175, 50);

		w += 350;

		set(lbt, x, y, w, 100, 350, 50);
		set(jspt, x, y, w, 150, 350, 600);
	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		vcas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pac != null && pac.editable)
					changePanel(new CastleEditPage(getThis(), pac));
				else if (pac != null)
					changePanel(new CastleViewPage(getThis(), pac.cs));
			}
		});

		vbgr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pac != null && pac.editable)
					changePanel(new BGEditPage(getThis(), pac));
				else if (pac != null)
					changePanel(new BGViewPage(getThis(), pac));
			}
		});

		vrcg.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pac != null && pac.editable)
					changePanel(new CGLREditPage(getThis(), pac));
				else
					changePanel(new CharaGroupPage(getThis(), pac, true));
			}
		});

		vrlr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pac != null && pac.editable)
					changePanel(new CGLREditPage(getThis(), pac));
				else
					changePanel(new LvRestrictPage(getThis(), pac, true));
			}
		});

		jld.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (jld.getValueIsAdjusting())
					return;
				adde.setEnabled(pac != null && pac != Pack.def && jld.getSelectedValue() != null);
			}

		});

	}

	private void addListeners$1() {

		addp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				pac = Pack.getNewPack();
				vpack.add(pac);
				jlp.setListData(vpack);
				jlt.setListData(vpack);
				jlp.setSelectedValue(pac, true);
				setPack(pac);
				changing = false;
			}
		});

		remp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!MainBCU.warning(get(0, "w0"), "warning"))
					return;
				changing = true;
				int ind = jlp.getSelectedIndex();
				Pack.map.remove(pac.id);
				if (pac.editable)
					pac.delete();
				else
					Writer.delete(pac.file);
				vpack.remove(pac);
				jlp.setListData(vpack);
				jlt.setListData(vpack);
				if (ind > 0)
					ind--;
				jlp.setSelectedIndex(ind);
				setPack(jlp.getSelectedValue());
				changing = false;
			}
		});

		jlp.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlp.getValueIsAdjusting())
					return;
				changing = true;
				setPack(jlp.getSelectedValue());
				changing = false;
			}

		});

		jtfp.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent fe) {
				String str = jtfp.getText().trim();
				str = MainBCU.validate(str);
				if (pac.name.equals(str))
					return;
				str = Pack.getAvailable(str);
				pac.name = str;
			}

		});

		extr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pac.packUp();
			}

		});
	}

	private void addListeners$2() {

		jle.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (changing || jle.getValueIsAdjusting())
					return;
				changing = true;
				setEnemy(jle.getSelectedValue());
				changing = false;
			}

		});

		adde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				CustomEnemy ce = new CustomEnemy();
				Enemy e = pac.es.addEnemy(jld.getSelectedValue(), ce);
				jle.setListData(pac.es.getList().toArray(new Enemy[0]));
				jle.setSelectedValue(e, true);
				setEnemy(e);
				changing = false;
			}

		});

		reme.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!MainBCU.warning(get(0, "w0"), "warning"))
					return;
				changing = true;
				int ind = jle.getSelectedIndex();
				pac.es.remove(ene);
				jle.setListData(pac.es.getList().toArray(new Enemy[0]));
				if (ind >= 0)
					ind--;
				jle.setSelectedIndex(ind);
				setEnemy(jle.getSelectedValue());
				changing = false;
			}

		});

		edit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				EnemyEditPage eet = new EnemyEditPage(getThis(), ene, pac.editable);
				changePanel(eet);
			}
		});

		jtfe.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent fe) {
				ene.name = jtfe.getText().trim();
			}

		});

		vene.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EnemyViewPage(getThis(), pac));
			}

		});

		ener.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EREditPage(getThis(), pac));
			}

		});

	}

	private void addListeners$3() {

		sdiy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (pac.editable)
					changePanel(new StageEditPage(getThis(), pac.mc, pac));
				else {
					List<MapColc> lmc = Arrays.asList(new MapColc[] { pac.mc });
					changePanel(new StageViewPage(getThis(), lmc));
				}
			}
		});

		cunt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new UnitManagePage(getThis(), pac));
			}

		});
		
		vmcs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(pac.editable?new MusicEditPage(getThis(), pac):
					new MusicPage(getThis(),pac.ms.getList()));
			}

		});

		jls.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jls.getValueIsAdjusting())
					return;
				changing = true;
				setMap(jls.getSelectedValue());
				changing = false;
			}

		});

		jls.list = new ReorderListener<StageMap>() {

			@Override
			public void reordered(int ori, int fin) {
				List<StageMap> lsm = new ArrayList<>();
				for (StageMap sm : pac.mc.maps)
					lsm.add(sm);
				StageMap sm = lsm.remove(ori);
				lsm.add(fin, sm);
				pac.mc.maps = lsm.toArray(new StageMap[0]);
				changing = false;
			}

			@Override
			public void reordering() {
				changing = true;
			}

		};

		jtfs.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent fe) {
				sm.name = jtfs.getText().trim();
			}

		});

		adds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				StageMap map = new StageMap(pac.mc);
				StageMap[] maps = pac.mc.maps;
				pac.mc.maps = Arrays.copyOf(maps, maps.length + 1);
				pac.mc.maps[maps.length] = map;
				jls.setListData(pac.mc.maps);
				jls.setSelectedValue(map, true);
				setMap(map);
				changing = false;
			}

		});

		rems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!MainBCU.warning(get(0, "w0"), "warning"))
					return;
				changing = true;
				int ind = jls.getSelectedIndex();
				int n = pac.mc.maps.length;
				StageMap[] maps = new StageMap[n - 1];
				for (int i = 0; i < ind; i++)
					maps[i] = pac.mc.maps[i];
				for (int i = ind + 1; i < n; i++)
					maps[i - 1] = pac.mc.maps[i];
				pac.mc.maps = maps;
				jls.setListData(maps);
				if (ind >= 0)
					ind--;
				jls.setSelectedIndex(ind);
				setMap(jls.getSelectedValue());
				changing = false;
			}

		});

	}

	private void addListeners$4() {

		jlr.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || arg0.getValueIsAdjusting())
					return;
				changing = true;
				setRely(jlr.getSelectedValue());
				changing = false;
			}

		});

		jlt.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || arg0.getValueIsAdjusting())
					return;
				checkAddr();
			}

		});

		addr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				Pack rel = jlt.getSelectedValue();
				pac.rely.add(rel.id);
				for (int id : rel.rely)
					if (!pac.rely.contains(id))
						pac.rely.add(id);
				updateJlr();
				jlr.setSelectedValue(rel, true);
				setRely(rel);
				changing = false;
			}

		});

		remr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				int ind = jlr.getSelectedIndex() - 1;
				Pack rel = jlr.getSelectedValue();
				if (pac.relyOn(rel.id) >= 0)
					if (MainBCU.warning("this action cannot be undone. Are you sure to remove "
							+ "all elements in this pack from the selected parent?", "Confirmation"))
						pac.forceRemoveParent(rel.id);
				pac.rely.remove((Integer) rel.id);
				updateJlr();
				jlr.setSelectedIndex(ind);
				setRely(jlr.getSelectedValue());
				changing = false;
			}

		});

	}

	private void checkAddr() {
		Pack rel = jlt.getSelectedValue();
		boolean b = pac.editable;
		b &= rel != null && !pac.rely.contains(rel.id);
		b &= rel != pac;
		if (b)
			for (int id : rel.rely)
				if (id == pac.id)
					b = false;
		addr.setEnabled(b);
	}

	private void ini() {
		add(back);
		add(jspp);
		add(jspe);
		add(jspd);
		add(addp);
		add(remp);
		add(jtfp);
		add(adde);
		add(reme);
		add(jtfe);
		add(edit);
		add(sdiy);
		add(jsps);
		add(adds);
		add(rems);
		add(jtfs);
		add(extr);
		add(jspr);
		add(jspt);
		add(addr);
		add(remr);
		add(vene);
		add(lbp);
		add(lbe);
		add(lbd);
		add(lbs);
		add(lbr);
		add(lbt);
		add(cunt);
		add(vcas);
		add(vrcg);
		add(vrlr);
		add(vbgr);
		add(ener);
		add(vmcs);
		jle.setCellRenderer(new AnimLCR());
		jld.setCellRenderer(new AnimLCR());
		setPack(null);
		addListeners();
		addListeners$1();
		addListeners$2();
		addListeners$3();
		addListeners$4();
	}

	private void setEnemy(Enemy e) {
		ene = e;
		boolean b = e != null && pac.editable;
		edit.setEnabled(e != null && e.de instanceof CustomEnemy);
		jtfe.setEnabled(b);
		reme.setEnabled(b);
		if (b) {
			jtfe.setText(e.name);
			reme.setEnabled(e.findApp(pac.mc).size() == 0);
		}
	}

	private void setMap(StageMap map) {
		sm = map;
		rems.setEnabled(sm != null && pac.editable);
		jtfs.setEnabled(sm != null && pac.editable);
		if (sm != null)
			jtfs.setText(map.name);
	}

	private void setPack(Pack pack) {
		pac = pack;
		boolean b = pac != null && pac.editable;
		remp.setEnabled(pac != null && pac != Pack.def);
		jtfp.setEnabled(b);
		adde.setEnabled(b && jld.getSelectedValue() != null);
		adds.setEnabled(b);
		extr.setEnabled(pac != null && pac != Pack.def);
		vcas.setEnabled(pac != null);
		vbgr.setEnabled(pac != null);
		vene.setEnabled(pac != null);
		if (b)
			jtfp.setText(pack.name);
		if (pac == null) {
			jle.setListData(new Enemy[0]);
			jlr.setListData(new Pack[0]);
			addr.setEnabled(false);
		} else {
			jle.setListData(pac.es.getList().toArray(new Enemy[0]));
			jle.clearSelection();
			updateJlr();
			jlr.clearSelection();
			checkAddr();
		}
		boolean b0 = pac != null && pac != Pack.def;
		sdiy.setEnabled(b0);
		if (b0) {
			jls.setListData(pac.mc.maps);
			jls.clearSelection();
		} else
			jls.setListData(new StageMap[0]);
		setRely(null);
		setMap(null);
		setEnemy(null);
	}

	private void setRely(Pack rel) {
		if (pac == null || rel == null) {
			remr.setEnabled(false);
			return;
		}
		int re = pac.relyOn(rel.id);
		if (rel.id < 1000) {
			remr.setEnabled(false);
			return;
		}
		remr.setText(0, re >= 0 ? "rema" : "rem");
		remr.setForeground(re >= 0 ? Color.RED : Color.BLACK);
		remr.setEnabled(true);
	}

	private void updateJlr() {
		Pack[] rel = new Pack[pac.rely.size()];
		for (int i = 0; i < pac.rely.size(); i++)
			rel[i] = Pack.map.get(pac.rely.get(i));
		jlr.setListData(rel);
	}

}
