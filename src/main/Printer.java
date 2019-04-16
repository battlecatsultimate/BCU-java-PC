package main;

import java.text.SimpleDateFormat;
import java.util.Date;

public strictfp class Printer {

	public static void e(String source, int line, String description) {
		print(source, line, description);
	}

	public static void p(String source, int line, String description) {
		if (MainBCU.write)
			return;
		print(source, line, description);
	}

	public static void r(int i, String string) {
		e("Reader", i, string);

	}

	public static void w(int i, String string) {
		e("Writer", i, string);

	}

	private static void print(String source, int line, String description) {
		Date date = new Date();
		String h = new SimpleDateFormat("HH").format(date);
		String m = new SimpleDateFormat("mm").format(date);
		String s = new SimpleDateFormat("ss").format(date);

		System.out.println("[" + source + ":#" + line + "," + h + ":" + m + ":" + s + "]:" + description);
	}

}
