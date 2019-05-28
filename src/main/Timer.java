package main;

import javax.swing.SwingUtilities;

public class Timer extends Thread
{

	// time consumed by each frame
	public static int inter = 0;

	// time per frame, in ms
	public static final int TPF = 33;

	protected static int state;

	private static int delay = 0;

	private Inv thr = null;

	@Override
	public void run()
	{
		while (true)
		{
			long m = System.currentTimeMillis();
			state = 0;
			SwingUtilities.invokeLater(thr = new Inv());
			try
			{
				boolean end = false;
				while (!end)
				{
					synchronized (thr)
					{
						end = state == 1;
					}
					if (!end)
						sleep(1);
				}
				thr.join();
				delay = (int) (System.currentTimeMillis() - m);
				inter = (inter * 9 + 100 * delay / TPF) / 10;
				int sle = delay >= TPF ? 1 : TPF - delay;
				sleep(sle);
			}
			catch (InterruptedException e)
			{
				return;
			}
		}
	}

}

class Inv extends Thread
{
	@Override
	public void run()
	{
		MainBCU.timer(-1);
		synchronized (this)
		{
			Timer.state = 1;
		}
	}
}
