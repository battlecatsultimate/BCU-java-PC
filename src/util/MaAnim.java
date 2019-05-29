package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class MaAnim
{
	protected Part[] parts;
	private int max = 1;
	private int numOfParts;
	
	// dio_00.maanim
	protected MaAnim(String filename)
	{
		Queue<String> lineQueue = null;
		try
		{
			File file = new File(filename);
			Path path = file.toPath();
			List<String> lines = Files.readAllLines(path);
			lineQueue = new ArrayDeque<>(lines);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		lineQueue.poll();	// line 1: [maanim]
		lineQueue.poll();	// line 2: 1 (unused)
		String line3 = lineQueue.poll().trim();	// line 3
		numOfParts = Integer.parseInt(line3);	// line 3: num of parts
		
		parts = new Part[numOfParts];
		for (int i = 0; i < numOfParts; i++)
		{
			parts[i] = new Part(lineQueue);
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
		
		for (int i = 0; i < numOfParts; i++)
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
		for (int i = 0; i < numOfParts; i++)
		{
			if (parts[i].getMax() > max)
			{
				max = parts[i].getMax();
			}
		}
	}

}
