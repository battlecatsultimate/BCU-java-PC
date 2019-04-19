package page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DefaultPage extends Page {

	private static final long serialVersionUID = 1L;

	private final JBTN back = new JBTN(0, "back");

	protected DefaultPage(Page p) {
		super(p);

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
		addListeners();
	}

}
