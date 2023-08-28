package page.battle;

import common.util.stage.Replay;
import io.BCMusic;
import main.MainBCU;
import main.Opts;
import page.JBTN;
import page.JTF;
import page.Page;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RecdSavePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN save = new JBTN(0, "save");
	private final JTF jtf = new JTF();

	private final Replay recd;
	private String name;

	protected RecdSavePage(Page p, Replay rec) {
		super(p);
		recd = rec;

		ini();
	}

	@Override
	protected JButton getBackButton() {
		return back;
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);

		set(back, x, y, 0, 0, 200, 50);
		set(jtf, x, y, 1000, 500, 300, 50);
		set(save, x, y, 1000, 600, 300, 50);
	}

	private void addListeners() {
		back.addActionListener(arg0 -> changePanel(getFront()));

		jtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = jtf.getText().trim();
				if (str.length() == 0)
					str = "new replay " + recd.st.toString();
				str = MainBCU.validate(str, '#');
				jtf.setText(name = str);
			}
		});

		save.addActionListener(arg0 -> {
			if (Replay.getMap().containsKey(name) && !Opts.conf("A replay named " + name + " already exists. Do you wish to overwrite?"))
				return;

			recd.rename(name, true);
			recd.write();
			Replay.getMap().put(recd.rl.id, recd);

			if (BCMusic.music != null)
				BCMusic.stopAll();

			changePanel(new RecdManagePage(getRootPage()));
		});

	}

	private void ini() {
		add(back);
		add(jtf);
		add(save);
		addListeners();
		String initName = name = "new replay " + recd.st.toString().replaceAll("/", "-");
		jtf.setText(initName);
	}

}
