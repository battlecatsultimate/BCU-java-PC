package util;

import java.util.Queue;

public class Part
{
	protected int[] ints = new int[5];
	private int[][] moves;
	protected int n, max, off, fir;

	protected Part(Queue<String> lineQueue)
	{
		String[] ss = lineQueue.poll().trim().split(",");
		for (int i = 0; i < 5; i++)
		{
			ints[i] = Integer.parseInt(ss[i].trim());
		}
		
		n = Integer.parseInt(lineQueue.poll().trim());
		
		moves = new int[n][4];
		for (int i = 0; i < n; i++)
		{
			ss = lineQueue.poll().trim().split(",");
			for (int j = 0; j < 4; j++)
			{
				moves[i][j] = Integer.parseInt(ss[j].trim());
			}
		}
		validate();
	}

	protected void ensureLast(EPart[] es)
	{
		if (n == 0)
			return;
		es[ints[0]].alter(ints[1], moves[n - 1][1]);
	}

	protected int getMax()
	{
		return ints[2] > 1 ? fir + (max - fir) * ints[2] : max;
	}

	protected void update(int frame, EPart[] es)
	{
		for (int i = 0; i < n; i++)
		{
			if (frame == moves[i][0])
			{
				es[ints[0]].alter(ints[1], moves[i][1]);
			}
			else if (i < n - 1 && frame > moves[i][0] && frame < moves[i + 1][0])
			{
				if (ints[1] > 1)
				{
					int f0 = moves[i][0];
					int v0 = moves[i][1];
					int f1 = moves[i + 1][0];
					int v1 = moves[i + 1][1];
					double ti = 1.0 * (frame - f0) / (f1 - f0);
					if (moves[i][2] == 0)
						;
					else if (moves[i][2] == 1)
						ti = 0;
					else if (moves[i][2] == 2)
						if (moves[i][3] >= 0)
							ti = 1 - Math.sqrt(1 - Math.pow(ti, moves[i][3]));
						else
							ti = Math.sqrt(1 - Math.pow(1 - ti, -moves[i][3]));
					else if (moves[i][2] == 3)
					{
						es[ints[0]].alter(ints[1], ease3(i, frame));
						break;
					}
					else if (moves[i][2] == 4)
						if (moves[i][3] > 0)
							ti = 1 - Math.cos(ti * Math.PI / 2);
						else if (moves[i][3] < 0)
							ti = Math.sin(ti * Math.PI / 2);
						else
							ti = (1 - Math.cos(ti * Math.PI)) / 2;
					es[ints[0]].alter(ints[1], (int) ((v1 - v0) * ti + v0));
					break;
				}
			}
		}
		
		if (n > 0 && frame > moves[n - 1][0])
			ensureLast(es);
	}

	protected void validate()
	{
		int doff = 0;
		if (n != 0 && moves[0][0] - off < 0)
			doff -= moves[0][0];
		
		for (int i = 0; i < n; i++)
		{
			moves[i][0] += doff;
		}
		off += doff;
		fir = moves.length == 0 ? 0 : moves[0][0];
		max = n > 0 ? moves[n - 1][0] : 0;
	}

	private int ease3(int i, int frame)
	{
		int low = i;
		int high = i;
		for (int j = i - 1; j >= 0; j--)
		{
			if (moves[j][2] == 3)
				low = j;
			else
				break;
		}
		for (int j = i + 1; j < moves.length; j++)
		{
			if (moves[high = j][2] != 3)
				break;
		}
		double sum = 0;
		for (int j = low; j <= high; j++)
		{
			double val = moves[j][1] * 4096;
			for (int k = low; k <= high; k++)
			{
				if (j != k)
				{
					val *= 1.0 * (frame - moves[k][0]) / (moves[j][0] - moves[k][0]);
				}
			}
			sum += val;
		}
		return (int) (sum / 4096);
	}

}
