package io;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public strictfp class OutStream extends DataIO {

	private byte[] bs;
	private int index;

	public OutStream() {
		bs = new byte[1024];
	}

	public OutStream(int size) {
		bs = new byte[size];
		index = 0;
	}

	protected OutStream(byte[] data) {
		bs = data;
	}

	public void accept(OutStream os) {
		writeInt(os.size());
		check(os.size());
		for (int i = 0; i < os.size(); i++)
			bs[index++] = os.bs[i];
	}

	public void concat(byte[] s) {
		check(s.length);
		for (byte b : s)
			fromByte(bs, index++, b);
	}

	public byte[] getBytes() {
		return bs;
	}

	public int pos() {
		return index;
	}

	public int size() {
		return bs.length;
	}

	public void terminate() {
		if (index == bs.length)
			return;
		bs = Arrays.copyOf(bs, index);
	}

	public InStream translate() {
		return new InStream(translate(bs));
	}

	public void writeByte(byte n) {
		check(1);
		fromByte(bs, index, n);
		index++;
	}

	public void writeBytesB(byte[] s) {
		check(s.length + 1);
		fromByte(bs, index++, (byte) s.length);
		for (byte b : s)
			fromByte(bs, index++, b);
	}

	public void writeBytesI(byte[] s) {
		writeInt(s.length);
		check(s.length);
		for (byte b : s)
			fromByte(bs, index++, b);
	}

	public void writeDouble(double n) {
		check(8);
		fromDouble(bs, index, n);
		index += 8;
	}

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

	public void writeFloat(float n) {
		check(4);
		fromFloat(bs, index, n);
		index += 4;
	}

	public void writeInt(int n) {
		check(4);
		fromInt(bs, index, n);
		index += 4;
	}

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

	public void writeLong(long n) {
		check(8);
		fromLong(bs, index, n);
		index += 8;
	}

	public void writeShort(short n) {
		check(2);
		fromShort(bs, index, n);
		index += 2;
	}

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

	private void check(int i) {
		if (index + i > bs.length * 2)
			bs = Arrays.copyOf(bs, index + i);
		else if (index + i > bs.length)
			bs = Arrays.copyOf(bs, bs.length * 2);
	}

}
