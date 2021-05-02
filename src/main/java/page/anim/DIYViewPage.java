package page.anim;

import common.system.VImg;
import common.util.AnimGroup;
import common.util.anim.AnimCE;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JTG;
import page.MainLocale;
import page.Page;
import page.awt.BBBuilder;
import page.support.AnimLCR;
import page.support.AnimTreeRenderer;
import page.view.AbViewPage;
import utilpc.UtilPC;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class DIYViewPage extends AbViewPage implements AbEditPage {

	private static final long serialVersionUID = 1L;

	private static final String[] icos = new String[] { "default", "starred", "EF", "TF", "uni_f", "uni_c", "uni_s" };

	private final JTree jlt = new JTree();
	private final JScrollPane jspu = new JScrollPane(jlt);
	private final JBTN group = new JBTN(MainLocale.PAGE, "addgroup");
	private final EditHead aep;
	private final AnimGroupTree agt;

	private final JTG ics = new JTG(0, "icon");
	private final JBTN icc = new JBTN(0, "confirm");
	private final JComboBox<String> jcb = new JComboBox<>(icos);
	private final JLabel uni = new JLabel();
	private final IconBox ib;

	public DIYViewPage(Page p) {
		super(p, BBBuilder.def.getIconBox());
		ib = (IconBox) vb;
		aep = new EditHead(this, 0);
		agt = new AnimGroupTree(jlt);
		ini();
		resized();
	}

	public DIYViewPage(Page p, AnimCE ac) {
		super(p, BBBuilder.def.getIconBox());
		ib = (IconBox) vb;
		aep = new EditHead(this, 0);
		agt = new AnimGroupTree(jlt);
		if (!ac.inPool())
			aep.focus = ac;
		ini();
		resized();
	}

	public DIYViewPage(Page p, EditHead bar) {
		super(p, BBBuilder.def.getIconBox());
		ib = (IconBox) vb;
		aep = bar;
		agt = new AnimGroupTree(jlt);
		ini();
		resized();
	}

	@Override
	public void setSelection(AnimCE ac) {

		DefaultMutableTreeNode selectedNode = agt.findAnimNode(ac);

		if(selectedNode == null)
			return;

		jlt.setSelectionPath(new TreePath(selectedNode));
		jlt.setExpandsSelectedPaths(true);
	}

	@Override
	protected void enabler(boolean b) {
		super.enabler(b);
		ics.setEnabled(ics.isSelected() || b && pause);
		icc.setEnabled(ics.isSelected());
		jlt.setEnabled(b);
	}

	@Override
	protected void keyPressed(KeyEvent ke) {
		super.keyPressed(ke);
		if (ke.getSource() == ib)
			ib.keyPressed(ke);
	}

	@Override
	protected void keyReleased(KeyEvent ke) {
		if (ke.getSource() == ib)
			ib.keyReleased(ke);
	}

	@Override
	protected void renew() {
		if (aep.focus == null) {
			AnimGroup.workspaceGroup.renewGroup();
			agt.renewNodes();
		}  else {
			DefaultMutableTreeNode root = new DefaultMutableTreeNode("Animation");

			root.add(new DefaultMutableTreeNode(aep.focus));

			jlt.setModel(new DefaultTreeModel(root));
		}

		jlt.setSelectionPath(new TreePath(agt.getVeryFirstAnimNode()));

		group.setEnabled(aep.focus == null);
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(aep, x, y, 550, 0, 1750, 50);
		set(jspu, x, y, 50, 100, 300, 1100);
		set(ics, x, y, 1000, 1050, 200, 50);
		set(uni, x, y, 750, 500, 200, 200);
		set(jcb, x, y, 750, 750, 200, 50);
		set(icc, x, y, 1000, 1150, 200, 50);
		set(group, x, y, 50, 1200, 300, 50);
	}

	@Override
	protected void updateChoice() {
		TreePath path = jlt.getSelectionPath();

		if(path == null)
			return;

		Object o = path.getLastPathComponent();

		if(!(o instanceof DefaultMutableTreeNode))
			return;

		if(((DefaultMutableTreeNode) o).getUserObject() instanceof AnimCE) {
			AnimCE e = (AnimCE) ((DefaultMutableTreeNode) o).getUserObject();
			aep.setAnim(e);
			uni.setIcon(e == null ? null : UtilPC.getIcon(e.getUni()));
			if (e == null)
				return;
			setAnim(e);
		}
	}

	private void addListeners() {

		jlt.addTreeSelectionListener(t -> {
			TreePath path = t.getNewLeadSelectionPath();

			if(path == null)
				return;

			updateChoice();
		});

		ics.addActionListener(arg0 -> {
			enabler(!ics.isSelected());
			ib.setBlank(ics.isSelected());
		});

		jcb.addActionListener(arg0 -> {
			int t = jcb.getSelectedIndex();
			IconBox.IBConf.mode = t / 4;
			IconBox.IBConf.type = t % 4;
			IconBox.IBConf.glow = IconBox.IBConf.type + IconBox.IBConf.mode > 1 ? 1 : 0;
			ib.changeType();
		});

		icc.setLnr(x -> {
			VImg clip = MainBCU.builder.toVImg(ib.getClip());
			if (IconBox.IBConf.mode == 0
					&& Opts.conf("are you sure to replace display icon? This action cannot be undone")) {
				AnimCE ac = aep.anim;
				ac.setEdi(clip);
				ac.saveIcon();
				jlt.repaint();
			}
			if (IconBox.IBConf.mode == 1
					&& Opts.conf("are you sure to replace battle icon? This action cannot be undone")) {
				AnimCE ac = aep.anim;
				ac.setUni(MainBCU.builder.toVImg(ib.getClip()));
				ac.saveUni();
				uni.setIcon(UtilPC.getIcon(ac.getUni()));
			}
		});

		group.setLnr(a -> {
			String groupName = Opts.read(get(MainLocale.PAGE, "addgrouppop"));

			if(groupName == null)
				return;

			groupName = AnimGroup.workspaceGroup.validateGroupName(groupName);

			AnimGroup.workspaceGroup.groups.put(groupName, new ArrayList<>());

			renew();
		});
	}

	private void ini() {
		preini();
		add(aep);
		add(jspu);
		add(ics);
		add(icc);
		add(jcb);
		add(uni);
		add(group);
		jcb.setSelectedIndex(IconBox.IBConf.type);
		ics.setEnabled(false);
		icc.setEnabled(false);
		jlt.setCellRenderer(new AnimTreeRenderer());
		group.setEnabled(aep.focus == null);
		addListeners();
	}

}
