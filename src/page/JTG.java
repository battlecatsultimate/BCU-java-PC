package page;

import javax.swing.JToggleButton;

public class JTG extends JToggleButton implements LocComp {

	private static final long serialVersionUID = 1L;

	private final LocSubComp lsc;

	public JTG() {
		lsc = new LocSubComp(this);
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

}
