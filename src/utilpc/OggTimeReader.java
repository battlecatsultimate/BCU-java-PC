package utilpc;

import common.util.stage.Music;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class OggTimeReader {
	public static final int MAX_VORBIS_BITRATE = 500000;

	private final Music mus;
	private final InputStream fis;
	public final boolean canUse;

	public int time;

	public OggTimeReader(Music mus) throws Exception {
		this.mus = mus;
		this.fis = mus.data.getStream();
		canUse = fis != null;
	}

	public int getNextByte() {
		byte[] res = new byte[1];
		try {
			fis.read(res);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		return res[0];
	}

	public double getNextDouble() {
		byte[] res = new byte[8];
		try {
			fis.read(res);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return ByteBuffer.wrap(res).getDouble();
	}

	public int getNextInt() {
		byte[] res = new byte[4];
		try {
			fis.read(res);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		byte[] real = new byte[4];
		for (int i = res.length - 1; i >= 0; i--) {
			real[res.length - 1 - i] = res[i];
		}
		return ByteBuffer.wrap(real).getInt();
	}

	public String getNextString(int len) {
		byte[] res = new byte[len];
		try {
			fis.read(res);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return new String(res);
	}

	public long getTime() {
		String start = getNextString(4);
		if (!start.equals("OggS")) {
			return -1;
		}
		skip(1);
		int headerVersion = getNextByte();
		String header;
		switch (headerVersion) {
		case 0x00:
			header = "Continuation";
			break;
		case 0x02:
			header = "Begin";
			break;
		case 0x04:
			header = "End";
			break;
		default:
			header = "Unknown";
		}
		if (header.equals("Unknown")) {
			return -1;
		}
		skip(20);
		int page = getNextByte();
		skip(page + 1);
		start = getNextString(6);
		if (!start.equals("vorbis")) {
			return -2;
		}
		skip(13);
		int bitNormal = getNextInt();
		if (bitNormal > 500000 || bitNormal < 0) {
			return -1;
		}
		return mus.data.size() * 8 * 1000 / bitNormal;
	}

	public long getTimeWithInfo() {
		String start = getNextString(4);
		if (!start.equals("OggS")) {
			return -1;
		}
		int ver = getNextByte();
		System.out.println("Version : " + ver);
		int headerVersion = getNextByte();
		String header;
		switch (headerVersion) {
		case 0x00:
			header = "Continuation";
			break;
		case 0x02:
			header = "Begin";
			break;
		case 0x04:
			header = "End";
			break;
		default:
			header = "Unknown";
		}
		System.out.println("Header : " + header);
		if (header.equals("Unknown")) {
			return -1;
		}
		double granule = getNextDouble();
		System.out.println("Granule Position : " + granule);
		int serial = getNextInt();
		System.out.println("Serial : " + serial);
		int pageNumber = getNextInt();
		System.out.println("Page Number : " + pageNumber);
		int checkSum = getNextInt();
		System.out.println("Checksum : " + Integer.toHexString(checkSum));
		int page = getNextByte();
		System.out.println("Page : " + page);
		skip(page);
		int id = getNextByte();
		System.out.println("Header ID : " + id);
		start = getNextString(6);
		System.out.println(start);
		if (!start.equals("vorbis")) {
			return -2;
		}
		int vorver = getNextInt();
		System.out.println("Vorbis Version : " + vorver);
		int ch = getNextByte();
		System.out.println("Number of Channel : " + ch);
		int sample = getNextInt();
		System.out.print("Sample Rate : " + sample + "Hz");
		int bitMax = getNextInt();
		System.out.println("Max Bitrate : " + bitMax + "bps");
		int bitNormal = getNextInt();
		System.out.println("Normal Bitrate : " + bitNormal + "bps");
		int bitMin = getNextInt();
		System.out.println("Min Bitrate : " + bitMin + "bps");
		if (bitNormal > 500000 || bitNormal < 0) {
			return -3;
		}
		return mus.data.size() * 8 * 1000 / bitNormal;
	}

	public void skip(int len) {
		try {
			fis.skip(len);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
