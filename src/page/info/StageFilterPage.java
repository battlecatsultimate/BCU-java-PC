package page.info;

import common.util.stage.Stage;
import page.Page;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

public class StageFilterPage extends StagePage {

	private static final long serialVersionUID = 1L;

	private final JList<Stage> jlst = new JList<>();
	private final JScrollPane jspst = new JScrollPane(jlst);

	public StageFilterPage(Page p, List<Stage> ls) {
		super(p);

		jlst.setListData(ls.toArray(new Stage[0]));
		ini();
		resized();
	}

	@Override
	protected void resized(int x, int y) {
		super.resized(x, y);
		set(jspst, x, y, 400, 550, 300, 650);
		set(strt, x, y, 400, 0, 300, 50);
	}

	private void addListeners() {

		jlst.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (arg0.getValueIsAdjusting())
					return;
				Stage s = jlst.getSelectedValue();
				if (s == null)
					return;
				setData(s);
			}

		});

	}

	private void ini() {
		add(jspst);
		addListeners();

	}

}
