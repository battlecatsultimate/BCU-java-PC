package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JScrollPane;

import page.JBTN;
import page.JTF;
import page.Page;
import util.stage.SCDef;
import util.stage.SCGroup;

public class AdvStEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JTF sdef = new JTF();
	private final JList<SCGroup> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);

	private final SCDef data;

	protected AdvStEditPage(Page p, SCDef scd) {
		super(p);
		data = scd;
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		setBounds(0, 0, x, y);
		set(back, x, y, 0, 0, 200, 50);
	}

	private void addListeners() {
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changePanel(getFront());
			}
		});

	}

	private void ini() {
		add(back);
		add(sdef);
		add(jsps);
		sdef.setText("default: " + data.sdef);
		addListeners();
	}

}
