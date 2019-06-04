package page.internet;

import java.util.Random;

import io.BCJSON;
import main.Opts;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;

public class UserSettingsPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");

	private final JL lps = new JL(2, "altpass");
	private final JTF fps = new JTF();

	protected UserSettingsPage(Page p) {
		super(p);

		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(lps, x, y, 50, 100, 200, 50);
		set(fps, x, y, 250, 100, 300, 50);
	}

	private void addListeners() {
		back.setLnr(e -> changePanel(getFront()));

		fps.setLnr(e -> {
			if (fps.getText().length() == 0)
				return;
			String str = fps.getText();
			long pass = new Random(str.hashCode()).nextLong();
			if (BCJSON.changePassword(pass)) {
				BCJSON.PASSWORD = pass;
				Opts.success("password reset success");
			} else {
				fps.setText("");
				Opts.servErr("failed to change password");
			}
		});
	}

	private void ini() {
		add(back);
		add(lps);
		add(fps);
		addListeners();
	}

}
