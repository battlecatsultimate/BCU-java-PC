package jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

import decode.ZipLib;
import io.Reader;
import io.Writer;
import jogl.util.GLGraphics;
import jogl.util.GLIB;
import util.anim.AnimU;
import util.anim.EAnimU;
import util.system.P;
import util.system.fake.FakeGraphics;
import util.system.fake.ImageBuilder;
import util.unit.UnitStore;

public class Temp extends StdGLC {

	private static AnimU test;
	private static EAnimU ent;

	public static void main(String[] args) throws IOException {
		ImageBuilder.builder = new GLIB();
		Reader.getData$0();
		Writer.logPrepare();
		ZipLib.init();
		ZipLib.read();
		Reader.getData$1();

		System.out.println("finish reading");

		final GLCanvas glcanvas = new GLCanvas(GLStatic.GLC);
		Temp b = new Temp();
		glcanvas.addGLEventListener(b);
		glcanvas.setSize(800, 800);

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
		test = UnitStore.get(377, 2, false).anim;
		test.check();
		ent = test.getEAnim(2);

		// adding canvas to it
		frame.getContentPane().add(glcanvas);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		FPSAnimator anim = new FPSAnimator(glcanvas, 30, true);
		anim.start();
	}

	private long time = 0;

	@Override
	public void drawFake(GLGraphics fg) {
		long tt = System.currentTimeMillis();
		System.out.println(tt - time);
		time = tt;
		fg.setColor(FakeGraphics.RED);
		fg.fillRect(100, 100, 200, 200);
		fg.colRect(300, 100, 200, 200, 255, 0, 255);
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