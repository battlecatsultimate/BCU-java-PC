package main;

import decode.ZipLib;
import io.Reader;
import io.Writer;
import util.pack.Pack;

public class MainPackReform {

	public static final int pid = 732332, asg = 3001;

	public static void main(String[] str) {
		System.out.println("start");
		Reader.getData$0();
		ZipLib.init();
		Writer.logSetup();
		ZipLib.read();
		Reader.getData$1();
		System.out.println("loaded");
		Pack p = new Pack(asg);
		p.merge(Pack.map.get(pid), false);
		Writer.writeBytes(p.write(), "./res/enemy/00" + asg + ".bcuenemy");
		System.out.println("done");
	}

}
