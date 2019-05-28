package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Queue;

public class MaAnim
{

	protected Part[] parts;

	private int max = 1, n;

	protected MaAnim(String str)
	{
		Queue<String> qs = null;
		try
		{
			qs = new ArrayDeque<>(Files.readAllLines(new File(str).toPath()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		qs.poll();
		qs.poll();
		n = Integer.parseInt(qs.poll().trim());
		parts = new Part[n];
		for (int i = 0; i < n; i++)
		{
			parts[i] = new Part(qs);
		}
		validate();
	}

	protected void update(int f, EAnimU eAnim, boolean rotate)
	{
		if (f == 0 || rotate && f % max == 0)
		{
			for (EPart e : eAnim.ent)
				e.setValue();
			eAnim.order.sort(null);
		}
		
		for (int i = 0; i < n; i++)
		{
			int loop = parts[i].ints[2];
			int smax = parts[i].max;
			int fir = parts[i].fir;
			int lmax = smax - fir;
			boolean prot = rotate || loop == -1;
			int frame = 0;
			if (prot)
			{
				int mf = loop == -1 ? smax : max;
				frame = mf == 0 ? 0 : (f + parts[i].off) % mf;
				if (loop > 0 && lmax != 0)
				{
					if (frame > fir + loop * lmax)
					{
						parts[i].ensureLast(eAnim.ent);
						continue;
					}
					if (frame <= fir)
						;
					else if (frame < fir + loop * lmax)
						frame = fir + (frame - fir) % lmax;
					else
						frame = smax;
				}
			}
			else
			{
				frame = f + parts[i].off;
				if (loop > 0 && lmax != 0)
				{
					if (frame > fir + loop * lmax)
					{
						parts[i].ensureLast(eAnim.ent);
						continue;
					}
					if (frame <= fir)
					{
						// nothing to do
					}
					else if (frame < fir + loop * lmax)
					{
						frame = fir + (frame - fir) % lmax;
					}
					else
					{
						frame = smax;
					}
				}
			}
			
			parts[i].update(frame, eAnim.ent);
		}
	}

	private void validate()
	{
		max = 1;
		for (int i = 0; i < n; i++)
		{
			if (parts[i].getMax() > max)
			{
				max = parts[i].getMax();
			}
		}
	}

}
