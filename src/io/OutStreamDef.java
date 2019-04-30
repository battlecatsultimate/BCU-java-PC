package io;

import java.util.Arrays;

public strictfp class OutStreamDef extends OutStream {

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

	public void accept(OutStream os) {
		if(os instanceof OutStreamDef)
			accept$0((OutStreamDef) os);
	}
	
	private void accept$0(OutStreamDef os) {
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

	public byte[] signature() {
		byte[] len = new byte[4];
		fromInt(len, 0, bs.length);
		return len;
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
		return new InStreamDef(translate(bs),0,bs.length);
	}

	public void writeByte(byte n) {
		check(1);
		fromByte(bs, index, n);
		index++;
	}

	public void writeDouble(double n) {
		check(8);
		fromDouble(bs, index, n);
		index += 8;
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

	protected void check(int i) {
		if (index + i > bs.length * 2)
			bs = Arrays.copyOf(bs, index + i);
		else if (index + i > bs.length)
			bs = Arrays.copyOf(bs, bs.length * 2);
	}

}
