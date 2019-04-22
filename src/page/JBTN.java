package page;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JButton;

public class JBTN extends JButton implements LocComp {

	private static final long serialVersionUID = 1L;

	private final LocSubComp lsc;

	public JBTN() {
		lsc = new LocSubComp(this);
	}

	public JBTN(int i, String str) {
		this();
		lsc.init(i, str);
	}

	public JBTN(String str) {
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
