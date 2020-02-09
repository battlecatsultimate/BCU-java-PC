package main;

import common.util.pack.Pack;
import decode.ZipLib;
import io.Reader;
import io.Writer;

public class MainPackReform {

	public static final int pid = 732332, asg = 3001;

	public static void main(String[] str) {
		System.out.println("start");
		Reader.getData$0();
		ZipLib.init();
		Writer.logPrepare();
		Reader.getData$1();
		System.out.println("loaded");
		Pack p = new Pack(asg);
		p.merge(Pack.map.get(pid));
		Writer.writeBytes(p.write(), "./res/enemy/00" + asg + ".bcuenemy");
		System.out.println("done");
	}

}
