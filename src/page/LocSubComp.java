package page;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import util.system.P;

class LocSubComp {

	protected final LocComp lc;
	protected int loc = -1;
	protected String info;
	protected Page page;

	public LocSubComp(LocComp comp) {
		lc = comp;
		lc.addMouseListener(new LSCPop(this));
	}

	protected void added(Page p) {
		page = p;
		setTTT();
	}

	protected void init(int i, String str) {
		lc.setToolTipText(setLoc(i, str));
	}

	protected void reLoc() {
		init(loc, info);
		setTTT();
	}

	protected void setText(int i, String str) {
		lc.setText(str);
		setLoc(i, str);
		setTTT();
	}

	private String setLoc(int i, String str) {
		String ans = MainLocale.getLoc(loc = i, info = str);
		lc.setText(ans);
		return ans;
	}

	private void setTTT() {
		if (page == null || info == null)
			return;
		String str = page.getClass().getSimpleName();
		String ttt = MainLocale.getTTT(str, info);
		if (ttt != null)
			lc.setToolTipText(ttt);
	}

}

class LSCPop extends MouseAdapter {

	private final LocSubComp lsc;

	protected LSCPop(LocSubComp comp) {
		lsc = comp;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			String cls = lsc.page.getClass().getSimpleName();
			JPanel panel = new JPanel();
			P size = new P(lsc.page.getRootPage().getSize()).times(0.25);
			panel.setPreferredSize(size.toDimension());
			panel.setLayout(new BorderLayout());
			JPanel top = new JPanel(new GridLayout(2, 2));
			JTF id0 = new JTF(lsc.info);
			JTF id1 = new JTF(cls + "_" + lsc.info);
			top.add(new JLabel("tooltip ID to edit: "));
			top.add(id1);
			String lab = (lsc.loc >= 0 ? MainLocale.RENN[lsc.loc] : "") + "_";
			top.add(new JLabel("name ID to edit: " + lab));
			top.add(id0);
			panel.add(top, BorderLayout.PAGE_START);
			JTF jtf = new JTF(lsc.lc.getText());
			panel.add(jtf, BorderLayout.PAGE_END);
			JTextPane jta = new JTextPane();
			jta.setText(lsc.lc.getToolTipText());
			panel.add(new JScrollPane(jta), BorderLayout.CENTER);
			if (lsc.loc < 0) {
				id0.setEnabled(false);
				jtf.setEnabled(false);
			}
			int type = JOptionPane.OK_CANCEL_OPTION;
			int ok = JOptionPane.OK_OPTION;
			int res = JOptionPane.showConfirmDialog(null, panel, "", type);
			String str = jtf.getText();
			String ttt = jta.getText();
			if (res == ok && str != null && !str.equals(lsc.lc.getText())) {
				MainLocale.setLoc(lsc.loc, id0.getText().trim(), str);
				Page.renewLoc(lsc.page);
			}
			if (res == ok && ttt != null
					&& (!ttt.equals(lsc.lc.getToolTipText()) || !id1.getText().equals(cls + "_" + lsc.info))) {
				String[] ids = id1.getText().trim().split("_", 2);
				if (ids.length == 2 && ids[0].length() > 0 && ids[1].length() > 0) {
					MainLocale.setTTT(ids[0], ids[1], ttt);
					Page.renewLoc(lsc.page);
				}
			}
		}

	}

}
