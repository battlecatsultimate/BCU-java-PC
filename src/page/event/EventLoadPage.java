package page.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import event.EventBase;
import event.EventReader;
import page.JBTN;
import page.Page;

public class EventLoadPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN natv = new JBTN(0, "natv");
	private final JLabel load = new JLabel(get("load"));

	private final int t;

	public EventLoadPage(Page p, int type, boolean force) {
		super(p);

		ini();
		resized();
		new EventLoader(this, t = type, force).start();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(load, x, y, 1100, 700, 200, 50);
		set(natv, x, y, 1100, 750, 200, 50);

	}

	protected void turn(boolean success) {
		if (success)
			if (t == 0)
				changePanel(new EventPage(getFront()));
			else if (t == 1)
				changePanel(new GachaPage(getFront()));
			else if (t == 2)
				changePanel(new ItemPage(getFront()));
			else if (t == 3)
				changePanel(new CalendarPage(getFront()));
			else
				load.setText(get("error"));
		else
			load.setText(get("error"));
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventLoader.cut();
				changePanel(getFront());
			}
		});

		natv.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				EventLoader.cut();
				boolean b;
				if (t == 0)
					b = EventReader.readEvents(true);
				else if (t == 1)
					b = EventReader.readGachas(true);
				else if (t == 2)
					b = EventReader.readItems(true);
				else if (t == 3)
					b = EventReader.readAll(true);
				else
					b = false;
				turn(b);
			}
		});

	}

	private void ini() {
		add(back);
		add(load);
		add(natv);
		addListeners();
	}

}

class EventLoader extends Thread {

	private static EventLoader el = null;

	protected static void cut() {
		if (el != null)
			el.interrupt = true;
	}

	private final EventLoadPage page;
	private final int type;
	private final boolean force;

	private boolean interrupt = false;

	protected EventLoader(EventLoadPage lp, int t, boolean f) {
		if (el != this)
			cut();
		el = this;
		page = lp;
		type = t;
		force = f;
	}

	@Override
	public void run() {
		boolean b0 = EventBase.ebe != null && EventBase.ebe.le.size() != 0;
		boolean b1 = EventBase.ebg != null && EventBase.ebg.lg.size() != 0;
		boolean b2 = EventBase.ebi != null && EventBase.ebi.li.size() != 0;
		boolean ball = type == 0 && b0 || type == 1 && b1 || type == 2 && b2 || type == 3 && b0 && b1 && b2;
		if (ball && !force) {
			page.turn(true);
			el = null;
			return;
		}
		boolean b;
		if (type == 0)
			b = EventReader.readEvents(false);
		else if (type == 1)
			b = EventReader.readGachas(false);
		else if (type == 2)
			b = EventReader.readItems(false);
		else if (type == 3)
			b = EventReader.readAll(false);
		else
			b = false;
		if (interrupt) {
			el = null;
			return;
		}
		page.turn(b);
	}

}
