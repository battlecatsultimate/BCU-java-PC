package jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;

public class Temp extends StdGLC {

	public static void main(String[] args) {

		final GLCanvas glcanvas = new GLCanvas(GLStatic.GLC);
		Temp b = new Temp();
		glcanvas.addGLEventListener(b);
		glcanvas.setSize(400, 400);

		// creating frame
		final JFrame frame = new JFrame(" Basic Frame");
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);

			}

		});

		// adding canvas to it
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);

	}

	@Override
	public void drawFake(GLGraphics fg) {
		fg.fillRect(100, 100, 200, 200);
	}

}