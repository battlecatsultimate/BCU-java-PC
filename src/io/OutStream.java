package io;

import java.io.UnsupportedEncodingException;

public strictfp abstract class OutStream extends DataIO {

	public static OutStream getIns() {
		return new OutStreamDef();
	}

	public abstract void accept(OutStream os);

	public abstract void concat(byte[] s);

	public abstract byte[] getBytes();

	public abstract byte[] signature();
	
	public abstract int pos();

	public abstract int size();

	public abstract void terminate();

	public abstract InStream translate();

	public abstract void writeByte(byte n);

	public void writeBytesB(byte[] s) {
		check(s.length + 1);
		writeByte((byte) s.length);
		for (byte b : s)
			writeByte(b);
	}

	public void writeBytesI(byte[] s) {
		writeInt(s.length);
		check(s.length);
		for (byte b : s)
			writeByte(b);
	}

	public abstract void writeDouble(double n);

	public void writeDoubles(double[] ints) {
		if (ints == null) {
			writeByte((byte) 0);
			return;
		}
		writeByte((byte) ints.length);
		for (double i : ints)
			writeDouble(i);
	}

	public void writeFloat(double n) {
		writeFloat((float) n);
	}

	public abstract void writeFloat(float n);

	public abstract void writeInt(int n);

	public void writeIntB(int[] ints) {
		if (ints == null) {
			writeByte((byte) 0);
			return;
		}
		writeByte((byte) ints.length);
		for (int i : ints)
			writeInt(i);
	}

	public void writeIntBB(int[][] ints) {
		if (ints == null) {
			writeByte((byte) 0);
			return;
		}
		writeByte((byte) ints.length);
		for (int[] i : ints)
			writeIntB(i);
	}

	public void writeIntsN(int... ns) {
		for (int i : ns)
			writeInt(i);
	}

	public abstract void writeLong(long n);

	public abstract void writeShort(short n);
	
	public void writeString(String str) {
		byte[] bts;
		try {
			bts = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			bts = str.getBytes();
			e.printStackTrace();
		}
		writeBytesB(bts);
	}

	protected abstract void check(int i);

}
