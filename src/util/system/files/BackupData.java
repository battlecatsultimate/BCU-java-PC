package util.system.files;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Queue;

import io.InStream;
import io.ZipAccess;

public class BackupData implements FileData {

	private final String md5;

	public long size;

	public BackupData(String str) {
		md5 = str;
		size = -1;
	}

	public BackupData(String str, long fsize) {
		md5 = str;
		size = fsize;
	}

	@Override
	public BufferedImage getImg() {
		return null;// TODO
	}

	public InStream getIS() {
		try {
			return ZipAccess.readStream(md5);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Queue<String> readLine() {
		try {
			return ZipAccess.readLine(md5);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String toString() {
		return md5;
	}

}
