package page.anim;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import common.CommonStatic;
import common.system.VImg;
import common.system.fake.FakeImage;
import common.util.anim.AnimC;
import common.util.anim.ImgCut;
import common.util.anim.MaAnim;
import common.util.anim.Part;
import common.util.pack.Pack;
import common.util.unit.DIYAnim;
import common.util.unit.Enemy;
import io.Writer;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JTF;
import page.Page;
import page.support.AnimLCR;
import page.support.Exporter;
import page.support.Importer;
import utilpc.ReColor;
import utilpc.UtilPC;

public class ImgCutEditPage extends Page implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private final JTF jtf = new JTF();
	private final JTF resz = new JTF("resize to: _%");
	private final JBTN back = new JBTN(0, "back");
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN relo = new JBTN(0, "relo");
	private final JBTN save = new JBTN(0, "saveimg");
	private final JBTN swcl = new JBTN(0, "swcl");
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN expt = new JBTN(0, "export");
	private final JBTN ico = new JBTN(0, "icon");
	private final JBTN loca = new JBTN(0, "localize");
	private final JLabel icon = new JLabel();
	private final JList<DIYAnim> jlu = new JList<>();
	private final JScrollPane jspu = new JScrollPane(jlu);
	private final JList<String> jlf = new JList<>(ReColor.strs);
	private final JScrollPane jspf = new JScrollPane(jlf);
	private final JList<String> jlt = new JList<>(ReColor.strf);
	private final JScrollPane jspt = new JScrollPane(jlt);
	private final ImgCutEditTable icet = new ImgCutEditTable();
	private final JScrollPane jspic = new JScrollPane(icet);
	private final SpriteBox sb = new SpriteBox(this);
	private final EditHead aep;

	private boolean changing = false;

	public ImgCutEditPage(Page p) {
		super(p);
		aep = new EditHead(this, 1);
		if (aep.focus == null)
			jlu.setListData(new Vector<>(DIYAnim.map.values()));
		else
			jlu.setListData(new DIYAnim[] { aep.focus });
		ini();
		resized();

	}

	public ImgCutEditPage(Page p, EditHead bar) {
		super(p);
		aep = bar;
		if (aep.focus == null)
			jlu.setListData(new Vector<>(DIYAnim.map.values()));
		else
			jlu.setListData(new DIYAnim[] { aep.focus });
		ini();
		resized();
	}

	@Override
	public void callBack(Object o) {
		changing = true;
		if (sb.sele >= 0) {
			icet.getSelectionModel().setSelectionInterval(sb.sele, sb.sele);
			int h = icet.getRowHeight();
			icet.scrollRectToVisible(new Rectangle(0, h * sb.sele, 1, h));
		} else
			icet.clearSelection();
		setB(sb.sele);
		changing = false;
	}

	@Override
	public void setSelection(DIYAnim ac) {
		changing = true;
		jlu.setSelectedValue(ac, true);
		setA(ac);
		changing = false;
	}

	@Override
	protected void keyPressed(KeyEvent ke) {
		if (ke.getSource() == sb)
			sb.keyPressed(ke);
	}

	@Override
	protected void keyReleased(KeyEvent ke) {
		if (ke.getSource() == sb)
			sb.keyReleased(ke);
	}

	@Override
	protected void keyTyped(KeyEvent ke) {
		if (ke.getSource() == sb)
			sb.keyTyped(ke);
	}

	@Override
	protected void mouseDragged(MouseEvent me) {
		if (me.getSource() == sb)
			sb.mouseDragged(me.getPoint());
	}

	@Override
	protected void mousePressed(MouseEvent me) {
		if (me.getSource() == sb)
			sb.mousePressed(me.getPoint());
	}

	@Override
	protected void mouseReleased(MouseEvent me) {
		if (me.getSource() == sb)
			sb.mouseReleased(me.getPoint());
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(aep, x, y, 550, 0, 1750, 50);
		set(back, x, y, 0, 0, 200, 50);
		set(relo, x, y, 250, 0, 200, 50);
		set(jspu, x, y, 0, 50, 300, 500);
		set(add, x, y, 350, 200, 200, 50);
		set(rem, x, y, 600, 200, 200, 50);
		set(impt, x, y, 350, 300, 200, 50);
		set(expt, x, y, 600, 300, 200, 50);
		set(resz, x, y, 350, 400, 200, 50);
		set(loca, x, y, 600, 400, 200, 50);
		set(jtf, x, y, 350, 100, 200, 50);
		set(copy, x, y, 600, 100, 200, 50);
		set(addl, x, y, 350, 500, 200, 50);
		set(reml, x, y, 600, 500, 200, 50);
		set(jspic, x, y, 50, 600, 800, 650);
		set(sb, x, y, 900, 100, 1400, 900);
		set(jspf, x, y, 900, 1050, 200, 200);
		set(jspt, x, y, 1150, 1050, 200, 200);
		set(swcl, x, y, 1400, 1050, 200, 50);
		set(save, x, y, 1400, 1150, 200, 50);
		set(ico, x, y, 1650, 1050, 200, 50);
		set(icon, x, y, 1650, 1100, 400, 100);
		aep.componentResized(x, y);
		icet.setRowHeight(size(x, y, 50));
		sb.paint(sb.getGraphics());
	}

	private void addListeners$0() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bimg = new Importer("Add your sprite").getImg();
				if (bimg == null)
					return;
				changing = true;
				String str = AnimC.getAvailable("new anim");
				AnimC ac = new AnimC(str);
				try {
					ac.setNum(FakeImage.read(bimg));
				} catch (IOException e) {
					e.printStackTrace();
				}
				ac.saveImg();
				ac.createNew();
				DIYAnim da = new DIYAnim(str, ac);
				DIYAnim.map.put(str, da);
				Vector<DIYAnim> v = new Vector<>(DIYAnim.map.values());
				jlu.setListData(v);
				jlu.setSelectedValue(da, true);
				setA(da);
				changing = false;
			}

		});

		impt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bimg = new Importer("Update your sprite").getImg();
				if (bimg != null) {
					AnimC ac = icet.anim;
					try {
						ac.setNum(FakeImage.read(bimg));
						ac.saveImg();
						ac.reloImg();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});

		expt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Exporter((BufferedImage) icet.anim.num.bimg(), Exporter.EXP_IMG);
			}

		});

		jlu.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || jlu.getValueIsAdjusting())
					return;
				changing = true;
				setA(jlu.getSelectedValue());
				changing = false;

			}

		});

		jtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				changing = true;
				String str = jtf.getText().trim();
				str = MainBCU.validate(str);
				if (str.length() == 0 || icet.anim == null || icet.anim.name.equals(str)) {
					if (icet.anim != null)
						jtf.setText(icet.anim.name);
					return;
				}
				DIYAnim da = DIYAnim.map.remove(icet.anim.name);
				DIYAnim rep = DIYAnim.map.get(str);
				if (rep != null && Opts.conf(
						"Do you want to replace animation? This action cannot be undone. The animation which originally keeps this name will be replaced by selected animation.")) {
					da.anim.delete();
					da.anim.name = rep.anim.name;
					for (Pack pack : Pack.map.values())
						for (Enemy e : pack.es.getList())
							if (e.anim == rep.anim)
								e.anim = da.anim;
					rep.anim = da.anim;
					rep.anim.saveImg();
					rep.anim.saveIcon();
					Vector<DIYAnim> v = new Vector<>(DIYAnim.map.values());
					jlu.setListData(v);
					jlu.setSelectedValue(rep, true);
					setA(rep);
				} else {
					str = AnimC.getAvailable(str);
					icet.anim.renameTo(str);
					DIYAnim.map.put(str, da);
					jtf.setText(str);
				}
				changing = false;
			}
		});

		copy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				String str = icet.anim.name;
				str = AnimC.getAvailable(str);
				AnimC ac = new AnimC(str, icet.anim);
				DIYAnim da = new DIYAnim(str, ac);
				DIYAnim.map.put(str, da);
				Vector<DIYAnim> v = new Vector<>(DIYAnim.map.values());
				jlu.setListData(v);
				jlu.setSelectedValue(da, true);
				setA(da);
				changing = false;
			}

		});

		rem.setLnr(x -> {
			if (!Opts.conf())
				return;
			changing = true;
			int ind = jlu.getSelectedIndex();
			AnimC ac = icet.anim;
			ac.delete();
			DIYAnim.map.remove(ac.name);
			Vector<DIYAnim> v = new Vector<>(DIYAnim.map.values());
			jlu.setListData(v);
			if (ind >= v.size())
				ind--;
			jlu.setSelectedIndex(ind);
			setA(ind < 0 ? null : v.get(ind));
			changing = false;
		}

		);

		loca.setLnr(x -> {
			if (!Opts.conf())
				return;
			changing = true;
			int ind = jlu.getSelectedIndex();
			AnimC ac = icet.anim;
			ac.check();
			ac.delete();
			ac.inPool = false;
			DIYAnim.map.remove(ac.name);
			Vector<DIYAnim> v = new Vector<>(DIYAnim.map.values());
			jlu.setListData(v);
			if (ind >= v.size())
				ind--;
			jlu.setSelectedIndex(ind);
			setA(v.get(ind));
			changing = false;
		}

		);

		relo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (icet.anim == null)
					return;
				icet.anim.reloImg();
				icet.anim.ICedited();
			}

		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				icet.anim.saveImg();
			}

		});

		ico.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				BufferedImage bimg = new Importer("select enemy icon").getImg();
				if (bimg == null)
					return;
				icet.anim.edi = new VImg(bimg);
				icet.anim.edi.mark("edi");
				icet.anim.saveIcon();
				File f = new File("./res/img/.png");
				Writer.check(f);
				try {
					ImageIO.write(bimg, "PNG", f);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (icet.anim.edi != null)
					icon.setIcon(UtilPC.getIcon(icet.anim.edi));
			}

		});

	}

	private void addListeners$1() {

		ListSelectionModel lsm = icet.getSelectionModel();

		lsm.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (changing || lsm.getValueIsAdjusting())
					return;
				changing = true;
				setB(lsm.getLeadSelectionIndex());
				changing = false;
			}

		});

		addl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				ImgCut ic = icet.ic;
				int[][] data = ic.cuts;
				String[] name = ic.strs;
				ic.cuts = new int[++ic.n][];
				ic.strs = new String[ic.n];
				for (int i = 0; i < data.length; i++) {
					ic.cuts[i] = data[i];
					ic.strs[i] = name[i];
				}
				int ind = icet.getSelectedRow();
				if (ind >= 0)
					ic.cuts[ic.n - 1] = ic.cuts[ind].clone();
				else
					ic.cuts[ic.n - 1] = new int[] { 0, 0, 1, 1 };
				ic.strs[ic.n - 1] = "";
				icet.anim.unSave("imgcut add line");
				resized();
				lsm.setSelectionInterval(ic.n - 1, ic.n - 1);
				int h = icet.getRowHeight();
				icet.scrollRectToVisible(new Rectangle(0, h * (ic.n - 1), 1, h));
				setB(ic.n - 1);
				changing = false;
			}

		});

		reml.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				changing = true;
				ImgCut ic = icet.ic;
				int ind = sb.sele;
				int[][] data = ic.cuts;
				String[] name = ic.strs;
				ic.cuts = new int[--ic.n][];
				ic.strs = new String[ic.n];
				for (int i = 0; i < ind; i++) {
					ic.cuts[i] = data[i];
					ic.strs[i] = name[i];
				}
				for (int i = ind + 1; i < data.length; i++) {
					ic.cuts[i - 1] = data[i];
					ic.strs[i - 1] = name[i];
				}
				for (int[] ints : icet.anim.mamodel.parts)
					if (ints[2] > ind)
						ints[2]--;
				for (MaAnim ma : icet.anim.anims)
					for (Part part : ma.parts)
						if (part.ints[1] == 2)
							for (int[] ints : part.moves)
								if (ints[1] > ind)
									ints[1]--;
				icet.anim.ICedited();
				icet.anim.unSave("imgcut remove line");
				if (ind >= ic.n)
					ind--;
				lsm.setSelectionInterval(ind, ind);
				setB(ind);
				changing = false;
			}

		});

		swcl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int ind = sb.sele;
				int[] data = null;
				if (ind >= 0) {
					ImgCut ic = icet.ic;
					data = ic.cuts[ind];
				}
				ReColor.transcolor((BufferedImage) icet.anim.num.bimg(), data, jlf.getSelectedIndex(),
						jlt.getSelectedIndex());
				icet.anim.ICedited();
			}

		});

		jlf.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (jlf.getSelectedIndex() == -1)
					jlf.setSelectedIndex(0);
			}

		});

		jlt.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (jlt.getSelectedIndex() == -1)
					jlt.setSelectedIndex(0);
			}

		});

		resz.setLnr(x -> {
			double d = CommonStatic.parseIntN(resz.getText()) * 0.01;
			if (Opts.conf("do you want to resize sprite to " + d + "%?")) {
				icet.anim.resize(d);
				icet.anim.ICedited();
				icet.anim.unSave("resized");
			}
			resz.setText("resize to: _%");
		});
	}

	private void ini() {
		add(aep);
		add(resz);
		add(back);
		add(relo);
		add(save);
		add(swcl);
		add(jspu);
		add(jspic);
		add(add);
		add(rem);
		add(copy);
		add(addl);
		add(reml);
		add(jtf);
		add(sb);
		add(jspf);
		add(jspt);
		add(impt);
		add(expt);
		add(icon);
		add(loca);
		add(ico);
		add.setEnabled(aep.focus == null);
		jtf.setEnabled(aep.focus == null);
		relo.setEnabled(aep.focus == null);
		swcl.setEnabled(aep.focus == null);
		jlu.setCellRenderer(new AnimLCR());
		setA(null);
		jlf.setSelectedIndex(0);
		jlt.setSelectedIndex(1);
		addListeners$0();
		addListeners$1();
	}

	private void setA(DIYAnim da) {
		boolean boo = changing;
		changing = true;
		if (da != null && da.getAnimC().mismatch)
			da = null;
		aep.setAnim(da);
		AnimC anim = da == null ? null : da.getAnimC();
		addl.setEnabled(anim != null);
		swcl.setEnabled(anim != null);
		save.setEnabled(anim != null);
		resz.setEditable(anim != null);
		icet.setCut(anim);
		sb.setAnim(anim);
		if (sb.sele == -1)
			icet.clearSelection();
		jtf.setEnabled(anim != null);
		jtf.setText(anim == null ? "" : anim.name);
		boolean del = da != null && da.deletable();
		rem.setEnabled(aep.focus == null && da != null && del);
		loca.setEnabled(aep.focus == null && da != null && !del && da.anim.inPool);
		copy.setEnabled(aep.focus == null && anim != null);
		impt.setEnabled(anim != null);
		expt.setEnabled(anim != null);
		if (da != null && da.anim.edi != null)
			icon.setIcon(UtilPC.getIcon(da.anim.edi));
		setB(sb.sele);
		changing = boo;
	}

	private void setB(int row) {
		sb.sele = icet.getSelectedRow();
		reml.setEnabled(sb.sele != -1);
		if (sb.sele >= 0) {
			for (int[] ints : icet.anim.mamodel.parts)
				if (ints[2] == sb.sele)
					reml.setEnabled(false);
			for (MaAnim ma : icet.anim.anims)
				for (Part part : ma.parts)
					if (part.ints[1] == 2)
						for (int[] ints : part.moves)
							if (ints[1] == sb.sele)
								reml.setEnabled(false);
		}
	}

}
