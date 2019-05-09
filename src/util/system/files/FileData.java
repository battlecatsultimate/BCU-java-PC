package util.system.files;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Queue;

import javax.imageio.ImageIO;

public abstract class FileData {

	protected static BufferedImage byte2Img(byte[] bs) {
		try {
			return ImageIO.read(new ByteArrayInputStream(bs));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public abstract BufferedImage getImg();

	public abstract Queue<String> readLine();

}
