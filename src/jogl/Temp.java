package jogl;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;
import common.system.P;
import common.system.fake.FakeGraphics;
import common.system.fake.ImageBuilder;
import common.util.anim.AnimCE;
import common.util.anim.AnimU;
import common.util.anim.EAnimU;
import jogl.util.GLGraphics;
import jogl.util.GLIB;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Temp extends StdGLC {

	private static AnimU<?> test;
	private static EAnimU ent;

	public static void main(String[] args) throws IOException {
		ImageBuilder.builder = new GLIB();
		/*
		 * Writer.logPrepare(); Reader.getData$0(); Writer.logSetup(); ZipLib.init();
		 * ZipLib.read(); Reader.getData$1();
		 */

		System.out.println("finish reading");

		final GLCanvas glcanvas = new GLCanvas(GLStatic.GLC);
		Temp b = new Temp();
		glcanvas.addGLEventListener(b);
		glcanvas.setSize(1600, 1000);

		// creating frame
		final JFrame frame = new JFrame(" Basic Frame");
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// Writer.logClose(false);
				System.exit(0);

			}

		});
		// 91, 377
		test = new AnimCE("dio");
		test.check();
		ent = test.getEAnim(AnimU.UType.ATK);

		// adding canvas to it
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		FPSAnimator anim = new FPSAnimator(glcanvas, 30, true);
		anim.start();
	}

	@Override
	public void drawFake(GLGraphics fg) {
		fg.setColor(FakeGraphics.RED);
		fg.fillRect(100, 100, 200, 200);
		fg.colRect(300, 100, 200, 200, 255, 0, 255, -1);
		fg.gradRect(500, 100, 200, 200, 0, 100, new int[] { 255, 255, 255 }, 0, 300, new int[] { 0, 0, 0 });
		// FakeTransform ft = fg.getTransform();
		for (int i = 0; i < 1; i++)
			for (int j = 0; j < 1; j++) {
				ent.draw(fg, new P(800 + j * 10, 750 + i * 10), 1);
				// fg.setTransform(ft);
			}
		fg.dispose();
		ent.update(true);
	}

}

abstract class StdGLC implements GLEventListener {

	protected int x, y, w, h;

	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		drawFake(new GLGraphics(drawable.getGL().getGL2(), w, h));
		gl.glFlush();
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
	}

	@Override
	public void init(GLAutoDrawable drawable) {
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int xp, int yp, int width, int height) {
		x = xp;
		y = yp;
		w = width;
		h = height;
	}

	protected abstract void drawFake(GLGraphics fg);

}