package page;

import common.util.lang.LocaleCenter.Binder;

import javax.swing.*;

public class JL extends JLabel implements LocComp {

	private static final long serialVersionUID = 1L;

	private final LocSubComp lsc;

	public JL() {
		lsc = new LocSubComp(this);
		setBorder(BorderFactory.createEtchedBorder());
	}

	public JL(Binder binder) {
		this();
		lsc.init(binder);
	}

	public JL(int i, String str) {
		this();
		lsc.init(i, str);
	}

	public JL(String str) {
		this(-1, str);
	}

	@Override
	public LocSubComp getLSC() {
		return lsc;
	}

}
