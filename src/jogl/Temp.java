package jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import util.anim.AnimC;
import util.anim.EAnimU;
import util.system.P;
import util.system.fake.ImageBuilder;

public class Temp extends StdGLC {

	private static AnimC test;
	private static EAnimU ent;

	public static void main(String[] args) throws IOException {
		ImageBuilder.builder = new GLIB();
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

		test = new AnimC("dio");
		test.check();
		ent = test.getEAnim(2);

		// adding canvas to it
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		FPSAnimator anim = new FPSAnimator(glcanvas, 30, true);
		anim.start();
	}

	@Override
	public void drawFake(GLGraphics fg) {
		ent.draw(fg, new P(800, 1000), 1);
		fg.flush();
		ent.update(true);
	}

}