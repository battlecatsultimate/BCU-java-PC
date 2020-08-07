package page;

import java.awt.event.MouseListener;

interface LocComp extends CustomComp {

	@Override
	public default void added(Page p) {
		getLSC().added(p);
	}

	public void addMouseListener(MouseListener ml);

	public LocSubComp getLSC();

	public String getText();

	public String getToolTipText();

	public default void reLoc() {
		getLSC().reLoc();
	}

	public default void setText(int i, String str) {
		getLSC().init(i, str);
	}

	public void setText(String t);

	public void setToolTipText(String ttt);

}
