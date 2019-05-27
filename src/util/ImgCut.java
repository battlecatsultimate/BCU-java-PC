package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Queue;

public class ImgCut {

	private int[][] cuts;
	private int n;
	private String[] strs;

	protected ImgCut(String str) {
		Queue<String> qs = null;
		try {
			qs = new ArrayDeque<>(Files.readAllLines(new File(str).toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		qs.poll();
		qs.poll();
		qs.poll();
		n = Integer.parseInt(qs.poll().trim());
		cuts = new int[n][4];
		strs = new String[n];
		for (int i = 0; i < n; i++) {
			String[] ss = qs.poll().trim().split(",");
			for (int j = 0; j < 4; j++)
				cuts[i][j] = Integer.parseInt(ss[j].trim());
			if (ss.length == 5)
				strs[i] = ss[4];
			else
				strs[i] = "";
		}
	}

	public BufferedImage[] cut(BufferedImage bimg) {
		int w = bimg.getWidth();
		int h = bimg.getHeight();
		BufferedImage[] parts = new BufferedImage[n];
		for (int i = 0; i < n; i++) {
			int[] cut = cuts[i].clone();
			if (cut[0] < 0)
				cut[0] = 0;
			if (cut[1] < 0)
				cut[1] = 0;
			if (cut[0] > w - 2)
				cut[0] = w - 2;
			if (cut[1] > h - 2)
				cut[1] = h - 2;
			if (cut[2] <= 0)
				cut[2] = 1;
			if (cut[3] <= 0)
				cut[3] = 1;
			if (cut[2] + cut[0] > w - 1)
				cut[2] = w - 1 - cut[0];
			if (cut[3] + cut[1] > h - 1)
				cut[3] = h - 1 - cut[1];
			parts[i] = bimg.getSubimage(cut[0], cut[1], cut[2], cut[3]);
		}
		return parts;
	}

}
