package page;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

}
