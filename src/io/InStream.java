package io;

import java.io.UnsupportedEncodingException;

public strictfp abstract class InStream extends DataIO {

	protected static InStream getIns(byte[] bs) {
		int[] is=DataIO.translate(bs);
		int sig=DataIO.toInt(is, 0);
		if(sig>0) {
			InStreamDef ans=new InStreamDef(is,4,is.length);
			return ans;
		}
		return null;
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
