package page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JToggleButton;

import common.util.lang.LocaleCenter.Binder;

public class JTG extends JToggleButton implements LocComp {

	private static final long serialVersionUID = 1L;

	private final LocSubComp lsc;

	public JTG() {
		lsc = new LocSubComp(this);
	}

	public JTG(Binder binder) {
		this();
		lsc.init(binder);
	}

	public JTG(int i, String str) {
		this();
		lsc.init(i, str);
	}

	public JTG(String str) {
		this(-1, str);
	}

	@Override
	public LocSubComp getLSC() {
		return lsc;
	}

	public void setLnr(Consumer<ActionEvent> c) {
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				c.accept(e);
			}

		});
	}

}
