package page;

import java.awt.event.MouseListener;

interface LocComp extends CustomComp {

	@Override
	default void added(Page p) {
		getLSC().added(p);
	}

	void addMouseListener(MouseListener ml);

	LocSubComp getLSC();

	String getText();

	String getToolTipText();

	default void reLoc() {
		getLSC().reLoc();
	}

	default void setText(int i, String str) {
		getLSC().init(i, str);
	}

	void setText(String t);

	void setToolTipText(String ttt);

}
