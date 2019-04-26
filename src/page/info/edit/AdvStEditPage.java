package page.info.edit;

import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Reader;
import page.JBTN;
import page.JTF;
import page.Page;
import page.support.AnimLCR;
import util.stage.SCDef;
import util.stage.SCGroup;
import util.stage.Stage;
import util.unit.AbEnemy;

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
	private final JBTN addl = new JBTN(0, "addl");
	private final JBTN reml = new JBTN(0, "reml");

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
		set(addl, x, y, 750, 100, 200, 50);
		set(reml, x, y, 950, 100, 200, 50);
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
			setList();
			setSCG(scg);
		});

		remg.setLnr(e -> {
			int ind = jls.getSelectedIndex();
			data.sub.remove(jls.getSelectedValue());
			if (ind > data.sub.size())
				ind--;
			setList();
			jls.setSelectedIndex(ind);
		});

		smax.setLnr(e -> {
			int val = Reader.parseIntN(smax.getText());
			SCGroup scg = jls.getSelectedValue();
			if (val > 0)
				scg.max = val;
			setSCG(scg);
		});
		
		sdef.setLnr(e ->{
			int i=Reader.parseIntN(sdef.getText());
			if(i>=0)data.sdef=i;
			setList();
		});

	}

	private void addListeners$1() {
		addl.setLnr(e -> sget.addLine(jle.getSelectedValue()));

		reml.setLnr(e -> sget.remLine());

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
			add(addl);
			add(reml);
		}
		jle.setCellRenderer(new AnimLCR());
		jle.setListData(aes);
		sdef.setText("default: " + data.sdef);
		setList();
		addListeners$0();
		addListeners$1();
	}

	private void setList() {
		SCGroup scg = jls.getSelectedValue();
		List<SCGroup> l = data.sub.getList();
		change(0, n -> {
			jls.clearSelection();
			jls.setListData(l.toArray(new SCGroup[0]));
		});
		sdef.setText("default: "+data.sdef);
		if (scg != null && !l.contains(scg))
			scg = null;
		setSCG(scg);
	}

	private void setSCG(SCGroup scg) {
		if (jls.getSelectedValue() != scg)
			change(scg, s -> jls.setSelectedValue(s, true));
		remg.setEnabled(scg != null);
		smax.setEnabled(scg != null);
		smax.setText(scg != null ? "max: " + scg.max : "");
	}

}
