package page.internet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import io.BCJSON;
import page.JBTN;
import page.JTF;
import page.Page;

public class LoginPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN rest = new JBTN(2, "reset");
	private final JLabel jlu = new JLabel(get(2, "username"));
	private final JLabel jle = new JLabel();
	private final JLabel jlc = new JLabel(get(2, "cont"));
	private final JTF jtf = new JTF();
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
		set(jlc, x, y, 0, 700, 2300, 50);
		set(jle, x, y, 0, 750, 2300, 50);
		set(rest, x, y, 1000, 850, 300, 50);
	}

	private void addListeners() {

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = jtf.getText().trim();
				if (str.length() == 0) {
					jle.setText(get(2, "e2"));
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
						jle.setText(get(2, "e1"));
						rest.setVisible(true);
					} else
						jle.setText(get(2, "e0"));
				} catch (IOException e) {
					jle.setText(get(2, "e3"));
					jlc.setVisible(true);
					e.printStackTrace();
				}
			}
		});

		rest.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String str = jtf.getText().trim();
				if (str.length() == 0) {
					jle.setText(get(2, "e2"));
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
						jle.setText(get(2, "e4"));
					} else
						jle.setText(get(2, "e0"));
				} catch (IOException e) {
					jle.setText(get(2, "e3"));
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
		jlc.setVisible(false);
		jlu.setHorizontalAlignment(SwingConstants.CENTER);
		jle.setHorizontalAlignment(SwingConstants.CENTER);
		jlc.setHorizontalAlignment(SwingConstants.CENTER);
		addListeners();
	}

}
