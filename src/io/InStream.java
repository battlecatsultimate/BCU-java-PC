package io;

import java.io.UnsupportedEncodingException;

import main.Opts;

public strictfp abstract class InStream extends DataIO {

	protected static InStream getIns(byte[] bs) {
		int[] is = DataIO.translate(bs);
		int sig = DataIO.toInt(is, 0);
		if (sig == bs.length - 4) {
			InStreamDef ans = new InStreamDef(is, 4, is.length);
			return ans;
		}
		throw new OverReadException("Unsupported version");
	}

	public abstract boolean end();

	public abstract int nextByte();

	public byte[] nextBytesB() {
		int len = nextByte();
		byte[] ints = new byte[len];
		for (int i = 0; i < len; i++)
			ints[i] = (byte) nextByte();
		return ints;
	}

	public byte[] nextBytesI() {
		int len = nextInt();
		byte[] ints = new byte[len];
		for (int i = 0; i < len; i++)
			ints[i] = (byte) nextByte();
		return ints;
	}

	public abstract double nextDouble();

	public double[] nextDoubles() {
		int len = nextByte();
		double[] ints = new double[len];
		for (int i = 0; i < len; i++)
			ints[i] = nextDouble();
		return ints;
	}

	public abstract float nextFloat();

	public abstract int nextInt();

	public int[] nextIntsB() {
		int len = nextByte();
		int[] ints = new int[len];
		for (int i = 0; i < len; i++)
			ints[i] = nextInt();
		return ints;
	}

	public int[][] nextIntsBB() {
		int len = nextByte();
		int[][] ints = new int[len][];
		for (int i = 0; i < len; i++)
			ints[i] = nextIntsB();
		return ints;
	}

	public abstract long nextLong();

	public abstract int nextShort();

	public String nextString() {
		byte[] bts = nextBytesB();
		try {
			return new String(bts, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(bts);
		}
	}

	public abstract int pos();

	public abstract void reread();

	public abstract int size();

	public abstract void skip(int n);

	public abstract InStream subStream();

	public abstract OutStream translate();

	protected void check(int i) {
	}

}

strictfp class InStreamDef extends InStream {

	private final int[] bs;
	private final int off, max;
	private int index;

	protected InStreamDef(int[] data, int ofs, int m) {
		bs = data;
		off = ofs;
		max = m;
		index = off;
	}

	@Override
	public boolean end() {
		return index == max;
	}

	@Override
	public int nextByte() {
		check(1);
		int ans = toByte(bs, index);
		index++;
		return ans;
	}

	@Override
	public double nextDouble() {
		check(8);
		double ans = toDouble(bs, index);
		index += 8;
		return ans;
	}

	@Override
	public float nextFloat() {
		check(4);
		float ans = toFloat(bs, index);
		index += 4;
		return ans;
	}

	@Override
	public int nextInt() {
		check(4);
		int ans = toInt(bs, index);
		index += 4;
		return ans;
	}

	@Override
	public long nextLong() {
		check(8);
		long ans = toLong(bs, index);
		index += 8;
		return ans;
	}

	@Override
	public int nextShort() {
		check(2);
		int ans = toShort(bs, index);
		index += 2;
		return ans;
	}

	@Override
	public int pos() {
		return index - off;
	}

	@Override
	public void reread() {
		index = off;
	}

	@Override
	public int size() {
		return max - off;
	}

	@Override
	public void skip(int n) {
		index += n;
	}

	@Override
	public InStreamDef subStream() {
		int n = nextInt();
		if (n > size()) {
			Opts.loadErr("corrupted file");
			new Exception("error in getting subStream").printStackTrace();
			Writer.logClose(false);
			System.exit(0);
		}
		InStreamDef is = new InStreamDef(bs, index, index + n);
		index += n;
		return is;
	}

	@Override
	public OutStream translate() {
		byte[] data = new byte[bs.length];
		for (int i = 0; i < bs.length; i++)
			data[i] = (byte) bs[i];
		return new OutStreamDef(data);
	}

	@Override
	protected void check(int i) {
		if (max - index < i) {
			String str = "out of bound: " + (index - off) + "/" + (max - off) + ", " + index + "/" + max + "/" + off
					+ "/" + bs.length;
			throw new OverReadException(str);
		}
	}

}
