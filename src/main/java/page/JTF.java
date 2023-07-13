package page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class JTF extends JTextField implements CustomComp {

	private static final long serialVersionUID = 1L;
	private String hint;

	public JTF() {
		this("");
	}

	public JTF(String tos) {
		super(tos);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
					transferFocus();
					if (isFocusOwner())
						KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
				}
			}
		});

	}

	public void setLnr(Consumer<FocusEvent> c) {
		addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
				c.accept(e);
			}

		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (getText().length() == 0 && hint != null && !isFocusOwner()) {
			int h = getHeight();
			((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			Insets ins = getInsets();
			FontMetrics fm = g.getFontMetrics();
			int c0 = getBackground().getRGB();
			int c1 = getForeground().getRGB();
			int m = 0xfefefefe;
			int c2 = ((c0 & m) >>> 1) + ((c1 & m) >>> 1);
			g.setColor(new Color(c2, true));
			g.drawString(hint, ins.left, h / 2 + fm.getAscent() / 2 - 2);
		}
	}

	public void setHintText(String t) {
		hint = t;
	}
}
