package io;

import java.io.File;
import java.io.FileNotFoundException;

public class Temp {

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("./img/temp.txt");
		Writer.check(f);
		WriteStream ws = new WriteStream(f);
		System.setErr(ws);
		new Exception("hi").printStackTrace();
		System.out.println(ws.writed);
	}

	public static void main$0(String[] args) {
		WebFileIO.download("http://battlecatsultimate.cf/api/resources/assets/080504.zip", new File("./img/Test.zip"),
				null);
	}

}
