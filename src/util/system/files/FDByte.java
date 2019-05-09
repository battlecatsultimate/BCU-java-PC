package util.system.files;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;

public class FDByte extends FileData {

	private byte[] data;

	public FDByte(byte[] bs) {
		data = bs;
	}

	public byte[] getBytes() {
		return data;
	}

	@Override
	public BufferedImage getImg() {
		return byte2Img(data);
	}

	@Override
	public Queue<String> readLine() {
		try {
			Queue<String> ans = new ArrayDeque<>();
			BufferedReader reader = null;
			InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(data), "UTF-8");
			reader = new BufferedReader(isr);
			String temp = null;
			while ((temp = reader.readLine()) != null)
				ans.add(temp);
			reader.close();
			return ans;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
