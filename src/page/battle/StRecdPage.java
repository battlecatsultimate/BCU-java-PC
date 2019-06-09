package page.battle;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.Opts;
import page.JBTN;
import page.JTF;
import page.Page;
import page.support.ReorderList;
import page.support.ReorderListener;
import util.stage.Recd;
import util.stage.Stage;

public class StRecdPage extends AbRecdPage {

	private static final long serialVersionUID = 1L;

	private final ReorderList<Recd> list = new ReorderList<>();
	private final JScrollPane jsp = new JScrollPane(list);
	private final JBTN addr = new JBTN(0, "add");
	private final JBTN remr = new JBTN(0, "rem");
	private final JTF jtf = new JTF();

	protected final Stage st;

	private RecdManagePage rmp;

	public StRecdPage(Page p, Stage stage, boolean edit) {
		super(p, edit);
		st = stage;
		preini();
		ini();
		resized();
	}

	@Override
	public Recd getSelection() {
		return list.getSelectedValue();
	}

	@Override
	protected void renew() {
		super.renew();
		if (rmp != null) {
			Recd recd = rmp.getSelection();
			if (recd != null) {
				if (recd.st == st || Opts.conf("stage mismatch. Do you really want to add?")) {
					st.recd.add(recd);
					setList();
				}
			}
		}
		rmp = null;
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jsp, x, y, 50, 100, 300, 600);
		set(addr, x, y, 400, 400, 300, 50);
		set(remr, x, y, 750, 400, 300, 50);
		set(jtf, x, y, 400, 500, 300, 50);
	}

	@Override
	protected void setList() {
		change(true);
		Recd r = list.getSelectedValue();
		list.setListData(st.recd.toArray(new Recd[0]));
		if (st.recd.contains(r))
			list.setSelectedValue(r, true);
		setRecd(list.getSelectedValue());
		addr.setEnabled(editable);
		change(false);
	}

	@Override
	protected void setRecd(Recd r) {
		super.setRecd(r);
		remr.setEnabled(editable && r != null);
		jtf.setEditable(editable && r != null);
		jtf.setText(r == null ? "" : r.name);
	}

	private void addListeners() {

		addr.setLnr(e -> changePanel(rmp = new RecdManagePage(this)));

		remr.setLnr(e -> {
			Recd recd = list.getSelectedValue();
			st.recd.remove(recd);
			setList();
		});

		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (isAdj() || list.getValueIsAdjusting())
					return;
				setRecd(list.getSelectedValue());
			}

		});

		list.list = new ReorderListener<Recd>() {

			@Override
			public void reordered(int ori, int fin) {
				change(false);
				List<Recd> l = st.recd;
				Recd sta = l.remove(ori);
				l.add(fin, sta);
			}

			@Override
			public void reordering() {
				change(true);
			}

		};

		jtf.setLnr(x -> {
			Recd r = list.getSelectedValue();
			if (isAdj() || r == null)
				return;
			r.name = jtf.getText();
		});

	}

	private void ini() {
		add(jsp);
		add(addr);
		add(remr);
		add(jtf);
		setList();
		addListeners();
	}

}
