package page.view;

import static io.BCMusic.DEF;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JList;
import javax.swing.JScrollPane;

import page.JBTN;
import page.Page;
import util.pack.MusicStore;

public class MusicPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");

	private final JList<File> jlf = new JList<>(MusicStore.getAll(null).toArray(new File[0]));
	private final JScrollPane jsp = new JScrollPane(jlf);

	public MusicPage(Page p) {
		super(p);

		ini();
		resized();
	}

	public MusicPage(Page p, int mus) {
		this(p);
		jlf.setSelectedIndex(mus);
	}

	public int getSelected() {
		return jlf.getSelectedIndex();
	}

	@Override
	protected void exit() {
		DEF.stop();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jsp, x, y, 50, 100, 300, 800);
		set(strt, x, y, 400, 100, 200, 50);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		strt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (jlf.getSelectedValue() == null)
					return;
				DEF.set(jlf.getSelectedValue());
			}
		});

	}

	private void ini() {
		add(back);
		add(strt);
		add(jsp);
		addListeners();
	}

}
