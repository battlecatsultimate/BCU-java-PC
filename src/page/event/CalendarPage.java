package page.event;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import event.DisplayEvent;
import event.Event;
import event.EventBase;
import event.FCald;
import event.Gacha;
import event.TimedEvent;
import page.MainFrame;
import page.Page;

public class CalendarPage extends URLPage {

	private static final long serialVersionUID = 1L;

	private final JList<Event> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final JList<String> jlss = new JList<>();
	private final JScrollPane jspss = new JScrollPane(jlss);
	private final JTable jte = new JTable();
	private final JScrollPane jspte = new JScrollPane(jte);
	private final JTable jtg = new JTable();
	private final JScrollPane jsptg = new JScrollPane(jtg);

	private final MonthButton mb = new MonthButton(this);
	private final FCald fc = new FCald();

	private Object[][] os;
	private Gacha[][] gs;

	public CalendarPage(Page p) {
		super(p, 3);

		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		super.resized(x, y);
		set(mb, x, y, 1550, 100, 700, 450);
		set(jspte, x, y, 50, 100, 650, 1150);
		set(jspss, x, y, 750, 100, 750, 450);
		set(jspe, x, y, 750, 600, 700, 450);
		set(jsptg, x, y, 1500, 600, 750, 450);
		jte.setRowHeight(size(x, y, 50));
		jte.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jte.getColumnModel().getColumn(0).setPreferredWidth(size(x, y, 150));
		jte.getColumnModel().getColumn(1).setPreferredWidth(size(x, y, 250));
		jte.getColumnModel().getColumn(2).setPreferredWidth(size(x, y, 230));
		jtg.setRowHeight(size(x, y, 50));
		jtg.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtg.getColumnModel().getColumn(0).setPreferredWidth(size(x, y, 350));
		jtg.getColumnModel().getColumn(1).setPreferredWidth(size(x, y, 350));
		mb.componentResized(x, y);
		jlss.setFont(MainFrame.font);
		jle.setFont(MainFrame.font);
		jte.setFont(MainFrame.font);
		jtg.setFont(MainFrame.font);
	}

	@Override
	protected void updateList() {
		jlss.setListData(new String[0]);
		if (fc == null)
			return;
<<<<<<< HEAD
		String[] title = new String[] { get(0, "time"), get(0, "perm"), get(0, "situ") };
=======
		String[] title = new String[] { get("time"), get("perm"), get("situ") };
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
		List<TimedEvent> lte = fc.getEvent(mb.getDate());
		int n = 0, i = 0;
		for (TimedEvent te : lte)
			n += te.size();
		os = new Object[n][3];
		for (TimedEvent te : lte) {
			os[i][0] = te.getTime();
			int x = te.size();
			int j = 0;
			for (DisplayEvent e : te.situ)
				os[i + j++][2] = e;
			j = 0;
			for (DisplayEvent e : te.perm)
				os[i + j++][1] = e;
			i += x;
		}
		jte.setModel(new DefaultTableModel(os, title));

		title = new String[] { "0:00~11:00", "11:00~24:00" };
		gs = fc.getGacha(mb.getDate());
		jtg.setModel(new DefaultTableModel(gs, title));

		resized();
	}

	private void addListeners() {

		ListSelectionListener lsl = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int r = jte.getSelectedRow();
				int c = jte.getSelectedColumn();
				if (r < 0 || c < 1)
					return;
				DisplayEvent de = (DisplayEvent) os[r][c];
				if (de != null) {
					jle.setListData(de.getEvents());
					jle.setSelectedIndex(0);
					jlss.setListData(de.getEvents()[0].getStrings());
				} else {
					jle.setListData(new Event[0]);
					jlss.setListData(new String[0]);
				}
			}
		};

		ListSelectionModel lsm = jte.getSelectionModel();
		lsm.setSelectionMode(SINGLE_SELECTION);
		lsm.addListSelectionListener(lsl);
		lsm = jte.getColumnModel().getSelectionModel();
		lsm.setSelectionMode(SINGLE_SELECTION);
		lsm.addListSelectionListener(lsl);

		lsl = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int r = jtg.getSelectedRow();
				int c = jtg.getSelectedColumn();
				if (r < 0 || c < 0)
					return;
				Gacha g = gs[r][c];
				if (g != null) {
					jlss.setListData(g.getStrings());
				} else {
					jlss.setListData(new String[0]);
				}
			}
		};

		lsm = jtg.getSelectionModel();
		lsm.setSelectionMode(SINGLE_SELECTION);
		lsm.addListSelectionListener(lsl);
		lsm = jtg.getColumnModel().getSelectionModel();
		lsm.setSelectionMode(SINGLE_SELECTION);
		lsm.addListSelectionListener(lsl);

		lsl = new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (jle.getSelectedValue() == null)
					return;
				jlss.setListData(jle.getSelectedValue().getStrings());
			}
		};

		jle.setSelectionMode(SINGLE_SELECTION);
		jle.addListSelectionListener(lsl);

	}

	private void ini() {
		init();
		add(mb);
		add(jspte);
		add(jspss);
		add(jsptg);
		add(jspe);
		setTime(EventBase.ebe);
		addListeners();
		updateList();
	}

}
