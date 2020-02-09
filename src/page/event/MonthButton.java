package page.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import event.EventDate;
import event.Namer;
import page.JBTN;
import page.JTG;
import page.Page;

class MonthButton extends Page {

	private static final long serialVersionUID = 1L;
	protected static final String[] wks = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

	private final JBTN left = new JBTN("<");
	private final JBTN rght = new JBTN(">");
	private final JBTN lmon = new JBTN("<<");
	private final JBTN rmon = new JBTN(">>");
	private final JLabel date = new JLabel();

	private final JLabel[] jls = new JLabel[7];
	private final JTG[][] jtbss = new JTG[6][7];

	private JTG sele = null;
	private Calendar cald = Calendar.getInstance();
	private int ind, sd, ed;
	private boolean changing = false;
	private final CalendarPage cp;

	protected MonthButton(CalendarPage page) {
		super(null);
		cp = page;

		setBorder(BorderFactory.createEtchedBorder());
		ini();
		resized();
	}

	protected EventDate getDate() {
		return new EventDate(cald);
	}

	@Override
	protected void resized(int x, int y) {
		set(left, x, y, 100, 0, 100, 50);
		set(date, x, y, 200, 0, 300, 50);
		set(rght, x, y, 500, 0, 100, 50);
		set(lmon, x, y, 0, 0, 100, 50);
		set(rmon, x, y, 600, 0, 100, 50);
		for (int i = 0; i < 7; i++) {
			set(jls[i], x, y, 100 * i, 50, 100, 50);
			for (int j = 0; j < 6; j++) {
				int ex = 0;
				if (j * 7 + i >= sd)
					ex++;
				if (j * 7 + i >= ed)
					ex++;
				set(jtbss[j][i], x, y, 100 * i, 50 * (j + ex + 2), 100, 50);
			}
		}
	}

	private void clickOn(int I, int J) {
		if (!jtbss[I][J].isSelected()) {
			if (sele == jtbss[I][J])
				sele.setSelected(true);
			return;
		}
		int prev = ind;
		ind = I * 7 + J;
		if (sele != null && sele != jtbss[I][J])
			sele.setSelected(false);
		sele = jtbss[I][J];
		if (changing)
			return;
		cald.add(Calendar.DATE, ind - prev);
		setDate();
	}

	private void ini() {
		add(left);
		add(date);
		add(rght);
		add(lmon);
		add(rmon);

		date.setHorizontalAlignment(SwingConstants.CENTER);

		left.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cald.add(Calendar.DATE, -1);
				setDate();
			}
		});

		rght.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cald.add(Calendar.DATE, 1);
				setDate();
			}
		});

		lmon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cald.add(Calendar.MONTH, -1);
				setDate();
			}
		});

		rmon.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				cald.add(Calendar.MONTH, 1);
				setDate();
			}
		});

		for (int i = 0; i < 7; i++) {
			jls[i] = new JLabel(Namer.get(wks[i]));
			add(jls[i]);
			jls[i].setHorizontalAlignment(SwingConstants.CENTER);
		}

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++)
				add(jtbss[i][j] = new JTG());

		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++) {
				int I = i, J = j;

				jtbss[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						clickOn(I, J);
					}
				});
			}

		setDate();
	}

	private void setDate() {
		int y = cald.get(Calendar.YEAR);
		int m = cald.get(Calendar.MONTH);
		int d = cald.get(Calendar.DATE);
		int w = cald.get(Calendar.DAY_OF_WEEK) - 1;
		date.setText(Namer.get(m + 1 + "") + d + " " + y + "," + Namer.get(wks[w]));
		changing = true;
		Calendar temp = Calendar.getInstance();
		temp.set(Calendar.MONTH, m);
		temp.set(Calendar.DATE, 1);
		sd = temp.get(Calendar.DAY_OF_WEEK) - 1;
		temp.add(Calendar.DATE, -sd);
		for (int i = 0; i < 6; i++)
			for (int j = 0; j < 7; j++) {
				int td = temp.get(Calendar.DATE);
				jtbss[i][j].setText("" + td);
				jtbss[i][j].setSelected(i * 7 + j == sd + d - 1);
				if (td == 1 && i * 7 + j > 20)
					ed = i * 7 + j;
				clickOn(i, j);
				temp.add(Calendar.DATE, 1);
			}
		changing = false;
		resized();
		cp.updateList();
	}

}
