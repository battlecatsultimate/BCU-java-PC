package page;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Consumer;

import javax.swing.JTextField;

public class JTF extends JTextField implements CustomComp {

	private static final long serialVersionUID = 1L;

	public JTF() {
		this("");
	}

	public JTF(String tos) {
		super(tos);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyCode() == KeyEvent.VK_ENTER)
					transferFocus();
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

}
