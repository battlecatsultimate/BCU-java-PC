package page.info.edit;

import common.CommonStatic;
import common.util.stage.Limit;
import common.util.stage.MapColc.PackMapColc;
import common.util.stage.Stage;
import page.JBTN;
import page.JTF;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LimitEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JTF star = new JTF();
	private final JTF stag = new JTF();
	private final JList<Limit> jll = new JList<>();
	private final JScrollPane jspl = new JScrollPane(jll);
	private final JBTN addl = new JBTN(0, "add");
	private final JBTN reml = new JBTN(0, "rem");
	private final LimitTable lt;

	private final Stage st;

	protected LimitEditPage(Page p, Stage stage) {
		super(p);
		st = stage;
		lt = new LimitTable(this, this, ((PackMapColc) stage.getCont().getCont()).pack);
		ini();
		resized();
	}

	@Override
	public void callBack(Object o) {
		setLimit(jll.getSelectedValue());
	}

	@Override
	protected void renew() {
		lt.renew();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspl, x, y, 50, 100, 400, 800);
		set(addl, x, y, 50, 900, 200, 50);
		set(reml, x, y, 250, 900, 200, 50);
		set(stag, x, y, 50, 950, 200, 50);
		set(star, x, y, 250, 950, 200, 50);
		set(lt, x, y, 500, 100, 1400, 100);
	}

	private void addListeners$0() {
		back.setLnr(e -> changePanel(getFront()));

		star.setLnr(e -> {
			if (isAdj())
				return;
			Limit l = jll.getSelectedValue();
			int n = CommonStatic.parseIntN(star.getText()) - 1;
			if (n < 0)
				n = -1;
			if (n > 3)
				n = 0;
			if (l != null)
				l.star = n;
			setLimit(l);
		});

		stag.setLnr(e -> {
			if (isAdj())
				return;
			Limit l = jll.getSelectedValue();
			int n = CommonStatic.parseIntN(stag.getText());
			if (n < 0)
				n = -1;
			if (n >= st.getCont().list.size())
				n = 0;
			if (l != null)
				l.sid = n;
			setLimit(l);
		});

		addl.setLnr(e -> {
			st.getCont().lim.add(new Limit());
			setListL();
		});

		reml.setLnr(e -> {
			st.getCont().lim.remove(jll.getSelectedValue());
			setListL();
		});

		jll.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (isAdj() || jll.getValueIsAdjusting())
					return;
				setLimit(jll.getSelectedValue());
			}

		});

	}

	private void ini() {
		add(back);
		add(jspl);
		add(addl);
		add(reml);
		add(star);
		add(stag);
		add(lt);
		setListL();
		addListeners$0();
	}

	private void setLimit(Limit l) {
		reml.setEnabled(l != null);
		star.setEditable(l != null);
		stag.setEditable(l != null);
		star.setText(l == null ? "" : l.star == -1 ? "all stars" : ((l.star + 1) + " star"));
		stag.setText(l == null ? "" : l.sid == -1 ? "all stages" : l.sid + " - " + st.getCont().list.get(l.sid));
		lt.setLimit(l);
	}

	private void setListL() {
		Limit l = jll.getSelectedValue();
		change(st.getCont().lim.toArray(new Limit[0]), x -> jll.setListData(x));
		if (!st.getCont().lim.contains(l))
			l = null;
		setLimit(l);
	}

}
