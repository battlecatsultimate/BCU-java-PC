package io;

import main.Opts;

public strictfp class InStreamDef extends InStream {
	
	private final int[] bs;
	private final int off,max;
	private int index;

	protected InStreamDef(int[] data,int ofs,int m) {
		bs = data;
		off=ofs;
		max=m;
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
		return index-off;
	}

	@Override
	public void reread() {
		index = off;
	}

	@Override
	public int size() {
		return max-off;
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
		InStreamDef is = new InStreamDef(bs,index,index+n);
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
		if(max-index<i) {
			String str="out of bound: "+(index-off)+"/"+(max-off)+", "+index+"/"+max+"/"+off+"/"+bs.length;
			throw new OverReadException(str);
		}
	}

}
