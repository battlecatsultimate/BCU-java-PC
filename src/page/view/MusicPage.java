package page.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Collection;

import javax.swing.JList;
import javax.swing.JScrollPane;

import common.util.pack.MusicStore;
import common.util.pack.Pack;
import io.BCMusic;
import page.JBTN;
import page.Page;

public class MusicPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JBTN strt = new JBTN(0, "start");

	private final JList<File> jlf = new JList<>();
	private final JScrollPane jsp = new JScrollPane(jlf);

	public MusicPage(Page p) {
		this(p, MusicStore.getAll(null));

	}

	public MusicPage(Page p, Collection<File> mus) {
		super(p);
		jlf.setListData(mus.toArray(new File[0]));
		ini();
		resized();
	}

	public MusicPage(Page p, int mus) {
		this(p, Pack.map.get(mus / 1000));
		jlf.setSelectedIndex(mus);
	}

	public MusicPage(Page p, Pack pack) {
		this(p, MusicStore.getAll(pack));
	}

	public int getSelected() {
		return MusicStore.getID(jlf.getSelectedValue());
	}

	@Override
	protected void exit() {
		BCMusic.stopAll();
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
				BCMusic.setBG(jlf.getSelectedValue(), 0);
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
