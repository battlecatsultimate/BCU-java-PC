package util.system.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.imageio.ImageIO;

public class FDFile implements FileData {

	private final File file;

	public FDFile(File f) {
		file = f;
	}

	@Override
	public BufferedImage getImg() {
		try {
			return ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Queue<String> readLine() {
		try {
			return new ArrayDeque<String>(Files.readAllLines(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		return file.getName();
	}

}
