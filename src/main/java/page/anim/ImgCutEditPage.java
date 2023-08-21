package page.anim;

import common.CommonStatic;
import common.pack.PackData.UserPack;
import common.pack.Source;
import common.pack.Source.ResourceLocation;
import common.pack.Source.Workspace;
import common.pack.UserProfile;
import common.system.fake.FakeImage.Marker;
import common.util.AnimGroup;
import common.util.anim.AnimCE;
import common.util.anim.ImgCut;
import common.util.anim.MaAnim;
import common.util.anim.Part;
import common.util.unit.Enemy;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JTF;
import page.MainLocale;
import page.Page;
import page.support.AnimTreeRenderer;
import page.support.Exporter;
import page.support.Importer;
import page.support.TreeNodeExpander;
import utilpc.Algorithm;
import utilpc.Algorithm.SRResult;
import utilpc.ReColor;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ImgCutEditPage extends Page implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private final JTF name = new JTF();
	private final JTF resz = new JTF("resize to: _%");
	private final JBTN back = new JBTN(0, "back");
	private final JBTN add = new JBTN(0, "add");
	private final JBTN rem = new JBTN(0, "rem");
	private final JBTN copy = new JBTN(0, "copy");
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");
	private final JBTN relo = new JBTN(0, "relo");
	private final JBTN save = new JBTN(0, "saveimg");
	private final JBTN swcl = new JBTN();
	private final JBTN impt = new JBTN(0, "import");
	private final JBTN expt = new JBTN(0, "export");
	private final JBTN icob = new JBTN(0, "icondi");
	private final JBTN unib = new JBTN(0, "iconde");
	private final JBTN loca = new JBTN(0, "localize");
	private final JBTN merg = new JBTN(0, "merge");
	private final JBTN spri = new JBTN(0, "sprite");
	private final JBTN white = new JBTN(0, "whiteBG");
	private final JLabel icon = new JLabel();
	private final JLabel uni = new JLabel();
	private final JTree jta = new JTree();
	private final AnimGroupTree agt;
	private final JScrollPane jspu = new JScrollPane(jta);
	private final JList<String> jlf = new JList<>(ReColor.strs);
	private final JScrollPane jspf = new JScrollPane(jlf);
	private final JList<String> jlt = new JList<>(ReColor.strf);
	private final JScrollPane jspt = new JScrollPane(jlt);
	private final ImgCutEditTable icet = new ImgCutEditTable();
	private final JScrollPane jspic = new JScrollPane(icet);
	private final SpriteBox sb = new SpriteBox(this);
	private final EditHead aep;

	private SpriteEditPage sep;

	private boolean changing = false;

	public ImgCutEditPage(Page p) {
		super(p);
		aep = new EditHead(this, 1);
		agt = new AnimGroupTree(jta);
		if (aep.focus == null) {
			AnimGroup.workspaceGroup.renewGroup();
			agt.renewNodes();
		} else {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Animation");

			root.add(new DefaultMutableTreeNode(aep.focus));

			jta.setModel(new DefaultTreeModel(root));
		}

		ini();
		resized(true);

	}

	public ImgCutEditPage(Page p, EditHead bar) {
		super(p);
		aep = bar;
		agt = new AnimGroupTree(jta);
		if (aep.focus == null) {
			AnimGroup.workspaceGroup.renewGroup();
			agt.renewNodes();
		} else {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Animation");

			root.add(new DefaultMutableTreeNode(aep.focus));

			jta.setModel(new DefaultTreeModel(root));
		}

		ini();
		resized(true);
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	public void callBack(Object o) {
		changing = true;
		if (o instanceof SpriteBox) {
			if (sb.sele >= 0) {
				icet.getSelectionModel().setSelectionInterval(sb.sele, sb.sele);
				int h = icet.getRowHeight();
				icet.scrollRectToVisible(new Rectangle(0, h * sb.sele, 1, h));
			} else
				icet.clearSelection();
		}
		setB();
		changing = false;
	}

	@Override
	public void setSelection(AnimCE ac) {
		changing = true;
		DefaultMutableTreeNode selectedNode = agt.findAnimNode(ac, null);

		if (selectedNode == null) {
			changing = false;
			return;
		}

		agt.expandCurrentAnimNode(selectedNode);
		jta.setSelectionPath(new TreePath(selectedNode.getPath()));

		setA(ac);
		changing = false;
	}

	@Override
	protected void renew() {
		if (sep != null && Opts.conf("Do you want to save edited sprite?")) {
			icet.anim.setNum(MainBCU.builder.build(sep.getEdit()));
			icet.anim.saveImg();
			icet.anim.reloImg();
		}
		sep = null;
	}

	@Override
	public synchronized void timer(int t) {
		resized(false);
		sb.paint(sb.getGraphics());
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(aep, x, y, 750, 0, 1500, 50);
		set(back, x, y, 0, 0, 200, 50);

		set(jspu, x, y, 50, 100, 300, 500);
		set(name, x, y, 50, 600, 300, 50);

		set(icob, x, y, 50, 750, 150, 50);
		set(unib, x, y, 200, 750, 150, 50);
		set(icon, x, y, 50, 800, 150, 150);
		set(uni, x, y, 200, 800, 150, 150);

		set(swcl, x, y, 50, 1000, 300, 50);
		set(jspf, x, y, 50, 1050, 150, 200);
		set(jspt, x, y, 200, 1050, 150, 200);

		set(add, x, y, 450, 150, 200, 50);
		set(rem, x, y, 700, 150, 200, 50);
		set(copy, x, y, 450, 200, 200, 50);
		set(merg, x, y, 700, 200, 200, 50);

		set(impt, x, y, 450, 250, 200, 50);
		set(expt, x, y, 700, 250, 200, 50);
		set(spri, x, y, 450, 300, 200, 50);
		set(loca, x, y, 700, 300, 200, 50);

		set(jspic, x, y, 400, 500, 600, 750);
		set(addl, x, y, 400, 450, 200, 50);
		set(reml, x, y, 600, 450, 200, 50);
		set(resz, x, y, 800, 450, 200, 50);

		set(sb, x, y, 1050, 150, 1200, 1100);
		set(relo, x, y, 1050, 100, 200, 50);
		set(save, x, y, 1250, 100, 200, 50);
		set(white, x, y, 1450, 100, 200, 50);

		SwingUtilities.invokeLater(() -> jta.setUI(new TreeNodeExpander(jta)));
		aep.componentResized(x, y);
		icet.setRowHeight(size(x, y, 50));
	}

	private void selectAnimNode(AnimCE ac) {
		DefaultMutableTreeNode selectedNode = agt.findAnimNode(ac, null);

		if (selectedNode != null) {
			agt.expandCurrentAnimNode(selectedNode);
			jta.setSelectionPath(new TreePath(selectedNode.getPath()));
		}
	}

	private void addListeners$0() {

		back.addActionListener(arg0 -> changePanel(getFront()));

		add.addActionListener(arg0 -> {
			BufferedImage bimg = new Importer("Add your sprite", Importer.FileType.PNG).getImg();
			if (bimg == null)
				return;
			int selection = Opts.selection("What kind of animation do you want to create?",
					"Select Animation Type",
					"Unit/Enemy",
					"Soul");
			if (selection == -1)
				return;
			changing = true;
			ResourceLocation rl;
			if (selection == 1)
				rl = new ResourceLocation(ResourceLocation.LOCAL, "new soul anim", Source.BasePath.SOUL);
			else
				rl = new ResourceLocation(ResourceLocation.LOCAL, "new anim", Source.BasePath.ANIM);
			Workspace.validate(rl);
			AnimCE ac = new AnimCE(rl);
			ac.setNum(MainBCU.builder.build(bimg));
			ac.saveImg();
			ac.createNew();
			AnimCE.map().put(rl.id, ac);
			AnimGroup.workspaceGroup.renewGroup();
			agt.renewNodes();
			selectAnimNode(ac);
			setA(ac);
			changing = false;
		});

		impt.addActionListener(arg0 -> {
			BufferedImage bimg = new Importer("Update your sprite", Importer.FileType.PNG).getImg();
			if (bimg != null) {
				AnimCE ac = icet.anim;
				ac.setNum(MainBCU.builder.build(bimg));
				ac.saveImg();
				ac.reloImg();
			}
		});

		expt.addActionListener(arg0 -> new Exporter((BufferedImage) icet.anim.getNum().bimg(), Exporter.EXP_IMG));

		jta.addTreeSelectionListener(arg0 -> {
			if (changing)
				return;
			changing = true;
			TreePath path = jta.getSelectionPath();

			if (path != null && path.getLastPathComponent() instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (node.getUserObject() instanceof AnimCE) {
					setA((AnimCE) node.getUserObject());
				}
			}

			changing = false;

		});

		name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				changing = true;
				String str = CommonStatic.verifyFileName(name.getText().trim());
				if (str.length() == 0 || icet.anim == null || icet.anim.id.id.equals(str)) {
					if (icet.anim != null)
						name.setText(icet.anim.id.id);
					changing = false;
					return;
				}
				AnimCE rep = AnimCE.map().get(str);
				if (rep != null) {
					icet.anim.renameTo(str);
					for (UserPack pack : UserProfile.getUserPacks())
						for (Enemy e : pack.enemies.getList())
							if (e.anim == rep)
								e.anim = icet.anim;
					agt.renewNodes();
					selectAnimNode(icet.anim);
					setA(icet.anim);
				} else {
					str = AnimCE.getAvailable(str, icet.anim.id.base);
					icet.anim.renameTo(str);
					name.setText(str);
				}
				changing = false;
			}
		});

		copy.addActionListener(arg0 -> {
			changing = true;
			ResourceLocation rl = new ResourceLocation(ResourceLocation.LOCAL, icet.anim.id.id, icet.anim.id.base);
			Workspace.validate(rl);
			AnimCE ac = new AnimCE(rl, icet.anim);
			ac.setEdi(icet.anim.getEdi());
			ac.setUni(icet.anim.getUni());
			agt.renewNodes();
			selectAnimNode(ac);
			setA(ac);
			changing = false;
		});

		rem.setLnr(x -> {
					if (!Opts.conf())
						return;
					changing = true;
					AnimCE ac = icet.anim;
					ac.delete();
					agt.renewNodes();

					DefaultMutableTreeNode leftNode = agt.selectVeryFirstBaseNodeOr();

					if (leftNode != null) {
						agt.expandCurrentAnimNode(leftNode);
						jta.setSelectionPath(new TreePath(leftNode.getPath()));
					}

					setA(leftNode == null ? null : (AnimCE) leftNode.getUserObject());

					changing = false;
				}

		);

		loca.setLnr(x -> {
					if (!Opts.conf())
						return;
					changing = true;
					AnimCE ac = icet.anim;
					ac.localize();
					agt.renewNodes();

					DefaultMutableTreeNode leftNode = agt.selectVeryFirstBaseNodeOr();

					if (leftNode != null) {
						agt.expandCurrentAnimNode(leftNode);
						jta.setSelectionPath(new TreePath(leftNode.getPath()));
					}

					setA(leftNode == null ? null : (AnimCE) leftNode.getUserObject());

					changing = false;
				}

		);

		relo.addActionListener(arg0 -> {
			if (icet.anim == null)
				return;
			icet.anim.reloImg();
			icet.anim.ICedited();
		});

		save.addActionListener(arg0 -> icet.anim.saveImg());

		icob.addActionListener(arg0 -> {
			BufferedImage bimg = new Importer("select icon image", Importer.FileType.PNG).getImg();
			if (bimg == null)
				return;
			icet.anim.setEdi(MainBCU.builder.toVImg(bimg));
			icet.anim.saveIcon();
			icon.setIcon(icet.anim.getEdi() != null ? UtilPC.getScaledIcon(icet.anim.getEdi(), 128, 48) : null);
		});

		unib.addActionListener(arg0 -> {
			BufferedImage bimg = new Importer("select icon image", Importer.FileType.PNG).getImg();
			if (bimg == null)
				return;
			icet.anim.setUni(MainBCU.builder.toVImg(bimg));
			icet.anim.saveUni();
			uni.setIcon(icet.anim.getUni() != null ? UtilPC.getScaledIcon(icet.anim.getUni(), 110, 85) : null);
		});

		white.setLnr(e -> {
			if (!sb.isAnimValid())
				return;

			white.setText(MainLocale.getLoc(MainLocale.PAGE, sb.white ? "blackBG" : "whiteBG"));

			sb.white = !sb.white;
		});
	}

	private void addListeners$1() {

		ListSelectionModel lsm = icet.getSelectionModel();

		lsm.addListSelectionListener(arg0 -> {
			if (changing || lsm.getValueIsAdjusting())
				return;
			changing = true;
			setB();
			changing = false;
		});

		addl.addActionListener(arg0 -> {
			changing = true;
			sb.newSprite(icet.getSelectedRow(), null);
			changing = false;
		});

		reml.addActionListener(arg0 -> {
			changing = true;
			ImgCut ic = icet.anim.imgcut;
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
			setB();
			changing = false;
		});

		swcl.addActionListener(arg0 -> {
			int ind = sb.sele;
			int[] data = null;
			if (ind >= 0) {
				ImgCut ic = icet.anim.imgcut;
				data = ic.cuts[ind];
			}
			ReColor.transcolor((BufferedImage) icet.anim.getNum().bimg(), data, jlf.getSelectedIndex(),
					jlt.getSelectedIndex());
			icet.anim.getNum().mark(Marker.RECOLORED);
			icet.anim.ICedited();
		});

		jlf.addListSelectionListener(arg0 -> {
			if (jlf.getSelectedIndex() == -1)
				jlf.setSelectedIndex(0);
			setC();
		});

		jlt.addListSelectionListener(arg0 -> {
			if (jlt.getSelectedIndex() == -1)
				jlt.setSelectedIndex(0);
			setC();
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

		merg.addActionListener(e -> {
			changing = true;

			ResourceLocation rl = new ResourceLocation(ResourceLocation.LOCAL, "merged", icet.anim.id.base);
			Workspace.validate(rl);

			TreePath[] paths = jta.getSelectionPaths();

			if (paths == null)
				return;

			ArrayList<AnimCE> anims = new ArrayList<>();

			//validation
			for (TreePath path : paths) {
				if (!(path.getLastPathComponent() instanceof DefaultMutableTreeNode))
					return;

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (!(node.getUserObject() instanceof AnimCE))
					return;

				anims.add((AnimCE) node.getUserObject());
			}

			AnimCE[] list = anims.toArray(new AnimCE[0]);
			int[][] rect = new int[list.length][2];
			for (int i = 0; i < list.length; i++) {
				rect[i][0] = list[i].getNum().getWidth();
				rect[i][1] = list[i].getNum().getHeight();
			}
			SRResult ans = Algorithm.stackRect(rect);
			AnimCE cen = list[ans.center];
			AnimCE ac = new AnimCE(rl, cen);
			BufferedImage bimg = new BufferedImage(ans.w, ans.h, BufferedImage.TYPE_INT_ARGB);
			Graphics g = bimg.getGraphics();
			for (int i = 0; i < list.length; i++) {
				BufferedImage b = (BufferedImage) list[i].getNum().bimg();
				int x = ans.pos[i][0];
				int y = ans.pos[i][1];
				g.drawImage(b, x, y, null);
				if (i != ans.center)
					ac.merge(list[i], x, y);
			}
			ac.setNum(MainBCU.builder.build(bimg));
			ac.saveImg();
			ac.reloImg();
			ac.unSave("merge");
			agt.renewNodes();
			selectAnimNode(ac);
			setA(ac);
			changing = false;
		});

		spri.setLnr(x -> changePanel(sep = new SpriteEditPage(this, (BufferedImage) icet.anim.getNum().bimg())));

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
		add(name);
		add(sb);
		add(jspf);
		add(jspt);
		add(impt);
		add(expt);
		add(icon);
		add(loca);
		add(icob);
		add(unib);
		add(merg);
		add(spri);
		add(white);
		add(uni);
		icon.setVerticalAlignment(SwingConstants.CENTER);
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		icon.setBorder(BorderFactory.createEtchedBorder());
		uni.setVerticalAlignment(SwingConstants.CENTER);
		uni.setHorizontalAlignment(SwingConstants.CENTER);
		uni.setBorder(BorderFactory.createEtchedBorder());
		add.setEnabled(aep.focus == null);
		name.setEnabled(aep.focus == null);
		relo.setEnabled(aep.focus == null);
		swcl.setEnabled(aep.focus == null);
		jta.setCellRenderer(new AnimTreeRenderer());
		SwingUtilities.invokeLater(() -> jta.setUI(new TreeNodeExpander(jta)));
		setA(null);
		jlf.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlf.setSelectedIndex(0);
		jlt.setSelectedIndex(1);
		setC();
		addListeners$0();
		addListeners$1();
	}

	private void setA(AnimCE anim) {
		System.out.println("Dss");
		boolean boo = changing;
		if (anim != null) {
			anim.check();
		}
		changing = true;
		aep.setAnim(anim);
		addl.setEnabled(anim != null);
		swcl.setEnabled(anim != null);
		save.setEnabled(anim != null);
		resz.setEnabled(anim != null);
		relo.setEnabled(anim != null);
		white.setEnabled(anim != null);
		icet.setCut(anim);
		sb.setAnim(anim);
		if (sb.sele == -1)
			icet.clearSelection();
		name.setEnabled(anim != null);
		name.setText(anim == null ? "" : anim.id.id);
		boolean del = anim != null && anim.deletable();
		rem.setEnabled(aep.focus == null && anim != null && del);
		loca.setEnabled(aep.focus == null && anim != null && !del && anim.inPool());
		copy.setEnabled(aep.focus == null && anim != null);
		impt.setEnabled(anim != null);
		expt.setEnabled(anim != null);
		spri.setEnabled(anim != null);
		icob.setEnabled(anim != null);
		unib.setEnabled(anim != null);

		boolean mergeEnabled = true;

		TreePath[] paths = jta.getSelectionPaths();

		if (paths == null)
			mergeEnabled = false;
		else {
			for (TreePath path : paths) {
				if (!(path.getLastPathComponent() instanceof DefaultMutableTreeNode)) {
					mergeEnabled = false;
					break;
				}

				DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (!(node.getUserObject() instanceof AnimCE)) {
					mergeEnabled = false;
					break;
				}
			}
		}

		merg.setEnabled(mergeEnabled);
		if (anim != null) {
			icon.setIcon(anim.getEdi() != null ? UtilPC.getScaledIcon(anim.getEdi(), 128, 48) : null);
			uni.setIcon(anim.getUni() != null ? UtilPC.getScaledIcon(anim.getUni(), 110, 85) : null);
		}
		setB();
		changing = boo;
	}

	private void setB() {
		sb.setSprite(icet.getSelectedRow(), false);
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

	private void setC() {
		swcl.setText(jlf.getSelectedValue() + " to " + jlt.getSelectedValue());
	}
}
