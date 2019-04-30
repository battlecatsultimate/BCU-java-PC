package io;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public strictfp abstract class OutStream extends DataIO {

	public static OutStream getIns() {
		return new OutStreamDef();
	}

	public abstract void accept(OutStream os);

	public abstract void concat(byte[] s);

	public abstract byte[] getBytes();

	public abstract int pos();

	public abstract byte[] signature();

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

strictfp class OutStreamDef extends OutStream {

	private byte[] bs;
	private int index;

	public OutStreamDef() {
		bs = new byte[1024];
	}

	public OutStreamDef(int size) {
		bs = new byte[size];
		index = 0;
	}

	protected OutStreamDef(byte[] data) {
		bs = data;
	}

	@Override
	public void accept(OutStream os) {
		if (os instanceof OutStreamDef)
			accept$0((OutStreamDef) os);
	}

	@Override
	public void concat(byte[] s) {
		check(s.length);
		for (byte b : s)
			fromByte(bs, index++, b);
	}

	@Override
	public byte[] getBytes() {
		return bs;
	}

	@Override
	public int pos() {
		return index;
	}

	@Override
	public byte[] signature() {
		byte[] len = new byte[4];
		fromInt(len, 0, bs.length);
		return len;
	}

	@Override
	public int size() {
		return bs.length;
	}

	@Override
	public void terminate() {
		if (index == bs.length)
			return;
		bs = Arrays.copyOf(bs, index);
	}

	@Override
	public InStream translate() {
		return new InStreamDef(translate(bs), 0, bs.length);
	}

	@Override
	public void writeByte(byte n) {
		check(1);
		fromByte(bs, index, n);
		index++;
	}

	@Override
	public void writeDouble(double n) {
		check(8);
		fromDouble(bs, index, n);
		index += 8;
	}

	@Override
	public void writeFloat(float n) {
		check(4);
		fromFloat(bs, index, n);
		index += 4;
	}

	@Override
	public void writeInt(int n) {
		check(4);
		fromInt(bs, index, n);
		index += 4;
	}

	@Override
	public void writeLong(long n) {
		check(8);
		fromLong(bs, index, n);
		index += 8;
	}

	@Override
	public void writeShort(short n) {
		check(2);
		fromShort(bs, index, n);
		index += 2;
	}

	@Override
	protected void check(int i) {
		if (index + i > bs.length * 2)
			bs = Arrays.copyOf(bs, index + i);
		else if (index + i > bs.length)
			bs = Arrays.copyOf(bs, bs.length * 2);
	}

	private void accept$0(OutStreamDef os) {
		writeInt(os.size());
		check(os.size());
		for (int i = 0; i < os.size(); i++)
			bs[index++] = os.bs[i];
	}

}
