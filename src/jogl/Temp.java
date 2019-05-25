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
import util.system.fake.FakeTransform;
import util.system.fake.ImageBuilder;

public class Temp extends StdGLC {

	private static AnimC test;
	private static EAnimU ent;

	public static void main(String[] args) throws IOException {
		ImageBuilder.builder = new GLIB();
		final GLCanvas glcanvas = new GLCanvas(GLStatic.GLC);
		Temp b = new Temp();
		glcanvas.addGLEventListener(b);
		glcanvas.setSize(800, 800);

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
	
	private long time=0;
	
	@Override
	public void drawFake(GLGraphics fg) {
		long tt=System.currentTimeMillis();
		System.out.println(tt-time);
		time=tt;
		FakeTransform ft=fg.getTransform();
		for(int i=0;i<10;i++)
			for(int j=0;j<10;j++){
			ent.draw(fg, new P(600+j*10, 750+i*10), 1);
			fg.setTransform(ft);
		}
		fg.flush();
		ent.update(true);
	}

}