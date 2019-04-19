package page.event;

import java.util.Vector;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import event.DisplayEvent;
import event.Event;
import page.MainFrame;
import page.Page;

public class EventPage extends URLPage {

	private static final long serialVersionUID = 1L;

	public static int[] dss = new int[0];

	private final Vector<DisplayEvent> vd = new Vector<>();
	private final JList<DisplayEvent> jld = new JList<>(vd);
	private final JScrollPane jspd = new JScrollPane(jld);
	private final JList<Event> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final JList<String> jltp = new JList<>();
	private final JScrollPane jsptp = new JScrollPane(jltp);
	private final FEvent fe = new FEvent(dss, this);

	private DisplayEvent de = null;
	private Event event = null;

	protected EventPage(Page p) {
		super(p, 0);

		ini();
		resized();
	}

	@Override
	protected void exit() {
		dss = fe.getDatas();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		super.resized(x, y);
		set(fe, x, y, 50, 100, 300, 1100);
		set(jspd, x, y, 400, 100, 400, 1100);
		set(jspe, x, y, 850, 100, 400, 1100);
		set(jsptp, x, y, 1300, 100, 750, 1100);
		fe.componentResized(x, y);
		jle.setFont(MainFrame.font);
		jld.setFont(MainFrame.font);
		jltp.setFont(MainFrame.font);
	}

	@Override
	protected void updateList() {
		vd.clear();
		vd.addAll(fe.getList());
		jld.setListData(vd);
		jle.setListData(new Event[0]);
		jltp.setListData(new String[0]);
	}

	private void addListeners() {

		jld.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				de = jld.getSelectedValue();
				if (de != null) {
					jle.setListData(de.getEvents());
					jle.setSelectedIndex(0);
				} else {
					jle.setListData(new Event[0]);
					jltp.setListData(new String[0]);
				}
			}
		});

		jle.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				event = jle.getSelectedValue();
				if (event != null)
					jltp.setListData(event.getStrings());
				else
					jltp.setListData(new String[0]);
			}
		});

	}

	private void ini() {
		init();
		add(jspd);
		add(jspe);
		add(jsptp);
		add(fe);
		updateList();
		addListeners();
	}

}
