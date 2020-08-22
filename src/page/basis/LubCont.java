package page.basis;

import common.system.Node;
import common.util.unit.Unit;
import page.Page;
import page.info.UnitInfoPage;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class LubCont extends Page {

	private static final long serialVersionUID = 1L;

	protected LubCont(Page p) {
		super(p);
	}

	protected abstract LineUpBox getLub();

	@Override
	protected void keyTyped(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar())) {
			int i = Integer.parseInt(e.getKeyChar() + "");
			if (i == 0)
				i = 9;
			else
				i--;
			getLub().setPos(i);
		} else if (e.getKeyChar() == 'd') {
			getLub().setPos(10);
		}
	}

	@Override
	protected void mouseClicked(MouseEvent e) {
		if (e.getSource() == getLub())
			if (e.getButton() == MouseEvent.BUTTON1)
				getLub().click(e.getPoint());
			else if (getLub().sf != null) {
				Node<Unit> n = new Node<>(getLub().sf.unit);
				changePanel(new UnitInfoPage(this, n));
			}
	}

	@Override
	protected void mouseDragged(MouseEvent e) {
		if (e.getSource() == getLub())
			getLub().drag(e.getPoint());
	}

	@Override
	protected void mousePressed(MouseEvent e) {
		if (e.getSource() == getLub())
			getLub().press(e.getPoint());
	}

	@Override
	protected void mouseReleased(MouseEvent e) {
		if (e.getSource() == getLub())
			getLub().release(e.getPoint());
	}

	@Override
	protected void timer(int t) {
		getLub().paint(getLub().getGraphics());
		resized();
	}

}
