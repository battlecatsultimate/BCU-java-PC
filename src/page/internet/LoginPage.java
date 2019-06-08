package page.internet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.SwingConstants;

import io.BCJSON;
import page.JBTN;
import page.JL;
import page.JTF;
import page.Page;

public class LoginPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rest = new JBTN(2, "reset");
	private final JL jlu = new JL(2, "username");
	private final JL jle = new JL();
	private final JL jlc = new JL(2, "cont");
	private final JTF jtf = new JTF();
	private final JL lps = new JL(2, "e5");
	private final JTF jps = new JTF();
	private final JBTN login = new JBTN(2, "login");

	public LoginPage(Page p) {
		super(p);

		ini();
		if (BCJSON.USERNAME.length() > 0) {
			jtf.setText(BCJSON.USERNAME);
			jtf.setEditable(false);
		}
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jlu, x, y, 1000, 450, 300, 50);
		set(jtf, x, y, 1000, 500, 300, 50);
		set(login, x, y, 1000, 600, 300, 50);
		set(jlc, x, y, 800, 700, 700, 50);
		set(jle, x, y, 800, 750, 700, 50);
		set(rest, x, y, 1000, 850, 300, 50);
		set(lps, x, y, 800, 950, 700, 50);
		set(jps, x, y, 1000, 1000, 300, 50);
	}

	private void addListeners() {

		back.setLnr(x -> changePanel(getFront()));

		jps.setLnr(x -> {
			String str = jps.getText();
			BCJSON.PASSWORD = new Random(str.hashCode()).nextLong();
			login();
		});

		login.setLnr(x -> login());

		rest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = jtf.getText().trim();
				if (str.length() == 0) {
					jle.setText(2, "e2");
					jlc.setVisible(true);
					return;
				}
				try {
					int id = BCJSON.getPassword(str);
					jlc.setVisible(id < 0);
					if (id > 0) {
						BCJSON.ID = id;
						BCJSON.USERNAME = str;
						changePanel(new WebMainPage(getFront()));
					} else if (id == -101) {
						jle.setText(2, "e4");
					} else
						jle.setText(2, "e0");
				} catch (IOException e) {
					jle.setText(2, "e3");
					jlc.setVisible(true);
					e.printStackTrace();
				}
			}
		});

	}

	private void ini() {
		add(back);
		add(login);
		add(rest);
		rest.setVisible(false);
		add(jtf);
		add(jlu);
		add(jlc);
		add(jle);
		add(jps);
		add(lps);
		jle.setVisible(false);
		lps.setVisible(false);
		jps.setVisible(false);
		jlc.setVisible(false);
		jlu.setHorizontalAlignment(SwingConstants.CENTER);
		jle.setHorizontalAlignment(SwingConstants.CENTER);
		jlc.setHorizontalAlignment(SwingConstants.CENTER);
		lps.setHorizontalAlignment(SwingConstants.CENTER);
		addListeners();
	}

	private void login() {
		String str = jtf.getText().trim();
		if (str.length() == 0) {
			jle.setText(2, "e2");
			jlc.setVisible(true);
			return;
		}
		try {
			int id = BCJSON.getID(str);
			jlc.setVisible(id < 0);
			if (id > 0) {
				BCJSON.ID = id;
				BCJSON.USERNAME = str;
				changePanel(new WebMainPage(getFront()));
			} else if (id == -100) {
				jle.setText(2, "e1");
				jle.setVisible(true);
				jps.setVisible(true);
				lps.setVisible(true);
				rest.setVisible(true);
			} else
				jle.setText(2, "e0");
		} catch (IOException e) {
			jle.setText(2, "e3");
			jle.setVisible(true);
			jlc.setVisible(true);
			e.printStackTrace();
		}
	}

}
