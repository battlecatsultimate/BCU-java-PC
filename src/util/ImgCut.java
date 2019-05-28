package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class ImgCut
{
	private int[][] cuts;			// 零件的value
	private int numOfParts;			// 共有多少個零件
	private String[] comments;

	protected ImgCut(String filename)
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
		
		lineQueue.poll();	// 1st line: [imgcut]
		lineQueue.poll();	// 2nd line: 0
		lineQueue.poll();	// 3rd lind: 257_e.png
		
		String line4 = lineQueue.poll().trim();
		numOfParts = Integer.parseInt(line4);
		cuts = new int[numOfParts][4];	// 每行有4個value
		comments = new String[numOfParts];
		for (int i = 0; i < numOfParts; i++)
		{
			String[] lineParts = lineQueue.poll().trim().split(",");
			for (int j = 0; j < 4; j++)
			{
				cuts[i][j] = Integer.parseInt(lineParts[j].trim());
			}
			
			if (lineParts.length == 5)
				comments[i] = lineParts[4];
			else
				comments[i] = "";
		}
	}

	public BufferedImage[] cut(BufferedImage bimg)
	{
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		BufferedImage[] parts = new BufferedImage[numOfParts];
		for (int i = 0; i < numOfParts; i++)
		{
			int[] cut = cuts[i].clone();	// 取1個零件的value array
			
			// 從完整圖片中截取1個零件部分
			if (cut[0] < 0)
				cut[0] = 0;
			if (cut[1] < 0)
				cut[1] = 0;
			if (cut[0] > width - 2)
				cut[0] = width - 2;
			if (cut[1] > height - 2)
				cut[1] = height - 2;
			
			if (cut[2] <= 0)
				cut[2] = 1;
			if (cut[3] <= 0)
				cut[3] = 1;
			
			if (cut[0] + cut[2] > width - 1)
				cut[2] = width - 1 - cut[0];
			if (cut[1] + cut[3] > height - 1)
				cut[3] = height - 1 - cut[1];
			
			parts[i] = bimg.getSubimage(cut[0], cut[1], cut[2], cut[3]);
		}
		return parts;
	}
}
