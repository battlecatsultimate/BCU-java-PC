package io;

import java.io.UnsupportedEncodingException;

import main.Opts;

public strictfp interface InStream {

	public static InStream getIns(byte[] bs) {
		int[] is = DataIO.translate(bs);
		int sig = DataIO.toInt(is, 0);
		if (sig == bs.length - 4) {
			InStream ans = new InStreamDef(is, 4, is.length);
			return ans;
		}
		if (sig == -1) {
			InStream ans = new InStreamFmt(is, 4, is.length);
			return ans;
		}
		throw new OverReadException("Unsupported version");
	}

	public boolean end();

	public int nextByte();

	public byte[] nextBytesB();

	public byte[] nextBytesI();

	public double nextDouble();

	public double[] nextDoubles();

	public float nextFloat();

	public int nextInt();

	public int[] nextIntsB();

	public int[][] nextIntsBB();

	public long nextLong();

	public int nextShort();

	public String nextString();

	public int pos();

	public void reread();

	public int size();

	public void skip(int n);

	public InStream subStream();

	public OutStream translate();

}

strictfp class InStreamDef extends DataIO implements InStream {

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
	public InStream subStream() {
		int n = nextInt();
		if (n > size()) {
			Opts.loadErr("corrupted file");
			new Exception("error in getting subStream").printStackTrace();
			Writer.logClose(false);
			System.exit(0);
		}
		InStream is = new InStreamDef(bs, index, index + n);
		index += n;
		return is;
	}

	@Override
	public OutStream translate() {
		byte[] data = new byte[max - index];
		for (int i = 0; i < max - index; i++)
			data[i] = (byte) bs[index + i];
		return new OutStreamDef(data);
	}

	private void check(int i) {
		if (max - index < i) {
			String str = "out of bound: " + (index - off) + "/" + (max - off) + ", " + index + "/" + max + "/" + off
					+ "/" + bs.length;
			throw new OverReadException(str);
		}
	}

	@Override
	public byte[] nextBytesB() {
		int len = nextByte();
		byte[] ints = new byte[len];
		for (int i = 0; i < len; i++)
			ints[i] = (byte) nextByte();
		return ints;
	}

	@Override
	public byte[] nextBytesI() {
		int len = nextInt();
		byte[] ints = new byte[len];
		for (int i = 0; i < len; i++)
			ints[i] = (byte) nextByte();
		return ints;
	}

	@Override
	public double[] nextDoubles() {
		int len = nextByte();
		double[] ints = new double[len];
		for (int i = 0; i < len; i++)
			ints[i] = nextDouble();
		return ints;
	}

	@Override
	public int[] nextIntsB() {
		int len = nextByte();
		int[] ints = new int[len];
		for (int i = 0; i < len; i++)
			ints[i] = nextInt();
		return ints;
	}

	@Override
	public int[][] nextIntsBB() {
		int len = nextByte();
		int[][] ints = new int[len][];
		for (int i = 0; i < len; i++)
			ints[i] = nextIntsB();
		return ints;
	}

	@Override
	public String nextString() {
		byte[] bts = nextBytesB();
		try {
			return new String(bts, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new String(bts);
		}
	}

}

strictfp class InStreamFmt extends DataIO implements InStream {

	private final InStreamDef bs;

	protected InStreamFmt(int[] data, int ofs, int m) {
		bs = new InStreamDef(data, ofs, m);

	}

	@Override
	public int nextByte() {
		check(BYTE);
		return bs.nextByte();
	}

	@Override
	public double nextDouble() {
		check(DOUBLE);
		return bs.nextDouble();
	}

	@Override
	public float nextFloat() {
		check(FLOAT);
		return bs.nextFloat();
	}

	@Override
	public int nextInt() {
		check(INT);
		return bs.nextInt();
	}

	@Override
	public long nextLong() {
		check(LONG);
		return bs.nextLong();
	}

	@Override
	public int nextShort() {
		check(SHORT);
		return bs.nextShort();
	}

	@Override
	public boolean end() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int pos() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reread() {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void skip(int n) {
		// TODO Auto-generated method stub

	}

	@Override
	public InStream subStream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OutStream translate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] nextBytesB() {
		check(BYTESB);
		return bs.nextBytesB();
	}

	@Override
	public byte[] nextBytesI() {
		check(BYTESI);
		return bs.nextBytesI();
	}

	@Override
	public double[] nextDoubles() {
		check(DOUBLESB);
		return bs.nextDoubles();
	}

	@Override
	public int[] nextIntsB() {
		check(INTSB);
		return bs.nextIntsB();
	}

	@Override
	public int[][] nextIntsBB() {
		check(INTSSBB);
		return bs.nextIntsBB();
	}

	@Override
	public String nextString() {
		check(STRING);
		return bs.nextString();
	}

	private void check(byte f) {
		int r = bs.nextByte();
		if (r == f)
			return;
		if (r >= names.length || r == 0)
			throw new OverReadException("unknown data type: " + r);
		throw new OverReadException("Expected to Read " + names[f] + " but read " + names[r]);
	}

}
