package page.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import event.EventBase;
import event.Namer;
import page.JBTN;
import page.Page;

public abstract class URLPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN load = new JBTN(0, "reload");
	private final JLabel time = new JLabel();

	private final int type;

	protected URLPage(Page p, int t) {
		super(p);
		type = t;
	}

	protected void init() {
		add(back);
		add(load);
		add(time);
		addListeners();
	}

	@Override
	protected void resized(int x, int y) {
		set(back, x, y, 0, 0, 200, 50);
		set(load, x, y, 250, 0, 200, 50);
		set(time, x, y, 500, 0, 600, 50);
	}

	protected void setTime(EventBase eb) {
		long l = Long.parseLong(eb.time);
		int[] ints = new int[5];
		for (int i = 0; i < 5; i++) {
			ints[i] = (int) (l % 100);
			l /= 100;
		}
		String s = ": " + l + "/" + Namer.get(ints[4] + "") + ints[3] + ", ";
		s += ints[2] + ":";
		if (ints[1] < 10)
			s += "0";
		s += ints[1] + ":";
		if (ints[0] < 10)
			s += "0";
		s += ints[0];
<<<<<<< HEAD
		time.setText(get(0, "refresh") + s);
=======
		time.setText(get("refresh") + s);
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
	}

	protected abstract void updateList();

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(new EventLoadPage(getFront(), type, true));
			}
		});
	}

}
