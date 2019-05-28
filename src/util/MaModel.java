package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class MaModel implements Cloneable
{
	protected int[][] confs;
	protected int[][] parts;
	protected int[] ints = new int[3];
	protected int numOfLine1;
	protected int numOfLine2;

	protected MaModel(String filename)
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
		
		lineQueue.poll();	// 1st line: [mamodel]
		lineQueue.poll();	// 2nd line: 3
		
		String line3 = lineQueue.poll().trim();
		numOfLine1 = Integer.parseInt(line3);
		parts = new int[numOfLine1][14];
		for (int i = 0; i < numOfLine1; i++)
		{
			String line = lineQueue.poll().trim();
			String[] lineParts = line.split(",");
			
			// only column 1 - 13 are numbers 
			for (int j = 0; j < 13; j++)
			{
				parts[i][j] = Integer.parseInt(lineParts[j].trim());
			}
		}
		
		/////////////////////////////////////////////////////////
		// 開始處理最後3行
		/////////////////////////////////////////////////////////
		
		// 倒數第3行
		String[] ss = lineQueue.poll().trim().split(",");
		for (int i = 0; i < 3; i++)
		{
			ints[i] = Integer.parseInt(ss[i].trim());
		}
		
		// 倒數第2行
		numOfLine2 = Integer.parseInt(lineQueue.poll().trim());
		
		// 最後1行
		confs = new int[numOfLine2][6];
		for (int i = 0; i < numOfLine2; i++)
		{
			ss = lineQueue.poll().trim().split(",");
			for (int j = 0; j < 6; j++)
			{
				confs[i][j] = Integer.parseInt(ss[j].trim());
			}
		}
	}
	
	protected EPart[] arrange(EAnimU e)
	{
		EPart[] ents = new EPart[numOfLine1];
		for (int i = 0; i < numOfLine1; i++)
		{
			ents[i] = new EPart(this, e.a, parts[i], ents);
		}
		return ents;
	}
}
