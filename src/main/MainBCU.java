package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import util.AnimU;
import util.EAnimU;

public class MainBCU
{
	// 0: move, 1: wait, 2: attack, 3:kb, 4: burrow down, 5: underground, 6: burrow up
	private static final int ANIM = 2;

	// path of the animation data and its prefix
	private static final String PATH = "./res/anim/dio/dio";

	private static ViewBoxDef view;

	public static void main(String[] args)
	{
		JFrame jf = new JFrame("animation");
		jf.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});

		view = new ViewBoxDef();
		jf.add(view);
		view.setSize(800, 600);
		jf.setSize(jf.getContentPane().getPreferredSize());
		jf.setVisible(true);
		
		AnimU animU = new AnimU(PATH);
		EAnimU eAnimU = animU.getEAnim(ANIM);
		view.setEnt(eAnimU);
		new Timer().start();
	}

	protected static void timer(int i)
	{
		if (view != null)
			view.timer(-1);
	}

}
