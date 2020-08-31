package page.battle;

import common.pack.Source;
import common.pack.Source.ResourceLocation;
import common.pack.Source.Workspace;
import common.util.stage.Replay;
import main.MainBCU;
import page.JBTN;
import page.JTF;
import page.Page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class RecdSavePage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN save = new JBTN(0, "save");
	private final JTF jtf = new JTF();

	private final Replay recd;

	protected RecdSavePage(Page p, Replay rec) {
		super(p);
		recd = rec;

		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jtf, x, y, 1000, 500, 300, 50);
		set(save, x, y, 1000, 600, 300, 50);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		jtf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String str = jtf.getText().trim();
				str = MainBCU.validate(str);
				if (str.length() == 0)
					str = "new replay for stage " + recd.st.toString();
				recd.rl = new ResourceLocation(ResourceLocation.LOCAL, str);
				// FIXME set replay name
				Workspace.validate(Source.REPLAY, recd.rl);

			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				recd.write();
				Replay.getMap().put(recd.rl.id, recd);
				changePanel(new RecdManagePage(getRootPage()));
			}
		});

	}

	private void ini() {
		add(back);
		add(jtf);
		add(save);
		jtf.setText("new replay for stage " + recd.st.toString());
		addListeners();
	}

}
