package event;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;

import io.Writer;
import main.MainBCU;
import main.Printer;

public class EventReader {

	public static final int JP = 0, TW = 1, EN = 2, KR = 3;
	public static final String[] LOC_NAME = new String[] { "BCJP", "BCTW", "BCEN", "BCKR" };

	private static final String[] LOC = new String[] { "jp/", "tw/", "en/", "kr/" };
	private static final String prev = "https://bc-seek.godfat.org/seek/";
	private static final String sale = "sale.tsv";
	private static final String gacha = "gatya.tsv";
	private static final String item = "item.tsv";

	public static int loc = EN;

	public static boolean readAll(boolean nat) {
		EventBase eb = new EventBase(EventBase.A);
		boolean b = RE(eb, nat);
		b |= RG(eb, nat);
		b |= RI(eb, nat);
		return b;
	}

	public static boolean readEvents(boolean nat) {
		EventBase eb = new EventBase(EventBase.E);
		return RE(eb, nat);
	}

	public static boolean readGachas(boolean nat) {
		EventBase eb = new EventBase(EventBase.G);
		return RG(eb, nat);
	}

	public static boolean readItems(boolean nat) {
		EventBase eb = new EventBase(EventBase.I);
		return RI(eb, nat);
	}

	private static boolean RE(EventBase eb, boolean nat) {
		try {
			Queue<String> qs;
			if (nat) {
				File dic = new File("./url/" + LOC_NAME[loc] + "-sale/");
				if (dic.exists()) {
					File f = dic.listFiles()[0];
					qs = readLines(f);
					eb.time = f.getName().substring(0, 14);
				} else
					return false;
			} else {
				qs = readLines(new URL(prev + LOC[loc] + sale));
				eb.time = MainBCU.getTime();
				Writer.writeURL(qs, LOC_NAME[loc] + "-sale");
			}
			for (String str : qs)
				new EventRaw(eb, str);
			EventRaw.pack(eb);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static Queue<String> readLines(File f) throws IOException {
		Queue<String> ans = new ArrayDeque<>();
		FileInputStream fis = new FileInputStream(f);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
		BufferedReader bis = new BufferedReader(isr);
		String temp = bis.readLine();
		if (temp.equals("[start]"))
			while (!(temp = bis.readLine()).equals("[end]"))
				ans.add(temp);
		else
			Printer.p("EventReader", 99, "illegal start: " + temp);
		bis.close();
		return ans;
	}

	private static Queue<String> readLines(URL url) throws IOException {
		Queue<String> ans = new ArrayDeque<>();
		InputStream is = url.openStream();
		InputStreamReader isr = new InputStreamReader(is, "UTF-8");
		BufferedReader bis = new BufferedReader(isr);
		String temp = bis.readLine();
		if (temp.equals("[start]"))
			while (!(temp = bis.readLine()).equals("[end]"))
				ans.add(temp);
		else
			Printer.p("EventReader", 99, "illegal start: " + temp);
		bis.close();
		return ans;
	}

	private static boolean RG(EventBase eb, boolean nat) {
		try {
			Queue<String> qs;
			if (nat) {
				File dic = new File("./url/" + LOC_NAME[loc] + "-gatya/");
				if (dic.exists()) {
					File f = dic.listFiles()[0];
					qs = readLines(f);
					eb.time = f.getName().substring(0, 14);
				} else
					return false;
			} else {
				qs = readLines(new URL(prev + LOC[loc] + gacha));
				eb.time = MainBCU.getTime();
				Writer.writeURL(qs, LOC_NAME[loc] + "-gatya");
			}
			for (String str : qs)
				new GachaTime(eb, str);
			Gacha.pack(eb);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean RI(EventBase eb, boolean nat) {
		try {
			Queue<String> qs;
			if (nat) {
				File dic = new File("./url/" + LOC_NAME[loc] + "-item/");
				if (dic.exists()) {
					File f = dic.listFiles()[0];
					qs = readLines(f);
					eb.time = f.getName().substring(0, 14);
				} else
					return false;
			} else {
				qs = readLines(new URL(prev + LOC[loc] + item));
				eb.time = MainBCU.getTime();
				Writer.writeURL(qs, LOC_NAME[loc] + "-item");
			}
			for (String str : qs)
				new Item(eb, str);
			Item.pack(eb);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
