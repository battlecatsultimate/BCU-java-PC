package page.info.edit;

import common.CommonStatic;
import common.util.stage.SCDef;
import common.util.stage.SCGroup;
import common.util.stage.Stage;
import common.util.unit.AbEnemy;
import page.JBTN;
import page.JTF;
import page.Page;
import page.support.AnimLCR;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class AdvStEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JTF sdef = new JTF();
	private final JTF smax = new JTF();
	private final JList<SCGroup> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JList<AbEnemy> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final SCGroupEditTable sget;
	private final JScrollPane jspt;
	private final JBTN addg = new JBTN(0, "add");
	private final JBTN remg = new JBTN(0, "rem");
	private final JBTN addt = new JBTN(0, "addl");
	private final JBTN remt = new JBTN(0, "reml");
	private final Stage st;
	private final SCDef data;

	protected AdvStEditPage(Page p, Stage stage) {
		super(p);
		st = stage;
		data = st.data;
		sget = new SCGroupEditTable(data);
		jspt = new JScrollPane(sget);
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsps, x, y, 50, 100, 300, 800);
		set(addg, x, y, 50, 900, 150, 50);
		set(remg, x, y, 200, 900, 150, 50);
		set(smax, x, y, 50, 950, 300, 50);
		set(jspe, x, y, 400, 100, 300, 800);
		set(sdef, x, y, 400, 900, 300, 50);
		set(jspt, x, y, 750, 150, 400, 800);
		set(addt, x, y, 750, 100, 200, 50);
		set(remt, x, y, 950, 100, 200, 50);
		sget.setRowHeight(size(x, y, 50));
	}

	private void addListeners$0() {
		back.setLnr(e -> changePanel(getFront()));

		jls.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdj() || jls.getValueIsAdjusting())
					return;
				setSCG(jls.getSelectedValue());
			}

		});

		addg.setLnr(e -> {
			int ind = data.sub.nextInd();
			SCGroup scg = new SCGroup(ind, st.max);
			data.sub.add(scg);
			setListG();
			setSCG(scg);
		});

		remg.setLnr(e -> {
			int ind = jls.getSelectedIndex();
			data.sub.remove(jls.getSelectedValue());
			if (ind > data.sub.size())
				ind--;
			setListG();
			jls.setSelectedIndex(ind);
		});

		smax.setLnr(e -> {
			int val = CommonStatic.parseIntN(smax.getText());
			SCGroup scg = jls.getSelectedValue();
			if (val > 0)
				scg.setMax(val, -1);
			setSCG(scg);
		});

		sdef.setLnr(e -> {
			int i = CommonStatic.parseIntN(sdef.getText());
			if (i >= 0)
				data.sdef = i;
			setListG();
		});

	}

	private void addListeners$1() {
		addt.setLnr(e -> sget.addLine(jle.getSelectedValue()));

		remt.setLnr(e -> sget.remLine());

	}

	private void ini() {
		AbEnemy[] aes = data.getSummon().toArray(new AbEnemy[0]);
		add(back);
		add(jsps);
		add(addg);
		add(remg);
		add(smax);

		if (aes.length > 0) {
			add(sdef);
			add(jspe);
			add(jspt);
			add(addt);
			add(remt);
		}
		jle.setCellRenderer(new AnimLCR());
		jle.setListData(aes);
		sdef.setText("default: " + data.sdef);
		setListG();
		addListeners$0();
		addListeners$1();
	}

	private void setListG() {
		SCGroup scg = jls.getSelectedValue();
		List<SCGroup> l = data.sub.getList();
		change(0, n -> {
			jls.clearSelection();
			jls.setListData(l.toArray(new SCGroup[0]));
		});
		sdef.setText("default: " + data.sdef);
		if (scg != null && !l.contains(scg))
			scg = null;
		setSCG(scg);
	}

	private void setSCG(SCGroup scg) {
		if (jls.getSelectedValue() != scg)
			change(scg, s -> jls.setSelectedValue(s, true));
		remg.setEnabled(scg != null);
		smax.setEnabled(scg != null);
		smax.setText(scg != null ? "max: " + scg.getMax(0) : "");
	}

}
