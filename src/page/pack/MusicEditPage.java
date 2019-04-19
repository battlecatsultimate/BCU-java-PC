package page.pack;

import static io.BCMusic.DEF;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import io.Writer;
import page.JBTN;
import page.Page;
import util.pack.MusicStore;
import util.pack.Pack;

public class MusicEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JList<String> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	private final JBTN relo = new JBTN(0, "read list");
	private final JBTN play = new JBTN(0, "start");
	private final JBTN show = new JBTN(0, "show");

	private final Pack pack;
	private final MusicStore ms;
	private List<File> list;
	private File sele;

	public MusicEditPage(Page p, Pack ac) {
		super(p);
		pack = ac;
		ms = ac.ms;
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
		set(jspst, x, y, 50, 100, 300, 1000);
		set(relo, x, y, 400, 100, 200, 50);
		set(play, x, y, 400, 200, 200, 50);
		set(show, x, y, 400, 300, 200, 50);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

		relo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ms.load();
				setList();
			}
		});

		show.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File f = new File("./res/img/" + pack.id + "/music/");
				Writer.check(f);
				try {
					Desktop.getDesktop().open(f);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		play.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (sele == null)
					return;
				DEF.set(sele);
			}
		});

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (isAdjusting() || arg0.getValueIsAdjusting())
					return;
				int ind = jlst.getSelectedIndex();
				sele = null;
				if (ind >= 0)
					sele = list.get(ind);
			}

		});
	}

	private void ini() {
		add(back);
		add(jspst);
		add(show);
		add(relo);
		add(play);
		setList();
		addListeners();

	}

	private void setList() {
		change(this, p -> {
			int ind = jlst.getSelectedIndex();
			list = ms.getList();
			String[] str = new String[list.size()];
			for (int i = 0; i < str.length; i++)
				str[i] = ms.nameOf(list.get(i));
			jlst.setListData(str);
			if (ind < 0)
				ind = 0;
			if (ind >= ms.size())
				ind = ms.size() - 1;
			jlst.setSelectedIndex(ind);
			if (ind >= 0)
				sele = list.get(ind);
		});
	}

}
