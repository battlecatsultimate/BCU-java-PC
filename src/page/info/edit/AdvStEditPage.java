package page.info.edit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JScrollPane;

import page.JBTN;
import page.JTF;
import page.Page;
import page.support.AnimLCR;
import util.stage.SCDef;
import util.stage.SCGroup;
import util.unit.AbEnemy;

public class AdvStEditPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");
	private final JTF sdef = new JTF();
	private final JList<SCGroup> jls = new JList<>();
	private final JScrollPane jsps = new JScrollPane(jls);
	private final JList<AbEnemy> jle = new JList<>();
	private final JScrollPane jspe = new JScrollPane(jle);
	private final SCGroupEditTable sget=new SCGroupEditTable();
	private final JScrollPane jspt = new JScrollPane(sget);

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
		set(jsps,x,y,50,100,300,800);
		set(jspe,x,y,400,100,300,800);
		set(jspt,x,y,750,100,400,800);
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
		add(jspe);
		add(jspt);
		jle.setCellRenderer(new AnimLCR());
		jle.setListData(data.getSummon().toArray(new AbEnemy[0]));
		sdef.setText("default: " + data.sdef);
		addListeners();
	}

}
