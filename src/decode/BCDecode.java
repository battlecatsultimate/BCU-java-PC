package decode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import io.Writer;

public class BCDecode {

	@SuppressWarnings("unused")
	private static class SourceCode {

		private static class Decoder {

			private static class Func implements Comparable<Func> {

				private final Decoder dec;
				private final String name;

				private int line0 = -1, line1 = -1;

				private final Set<Func> callers = new TreeSet<>();
				private final Set<Func> subfunc = new TreeSet<>();

				private Func(Decoder d, String str) {
					dec = d;
					name = str;
					dec.MAP.put(name, this);
				}

				@Override
				public int compareTo(Func f) {
					return name.compareTo(f.name);
				}

				@Override
				public String toString() {
					return name;
				}

				private void add(String str) {
					Func f = null;
					if (dec.MAP.containsKey(str))
						f = dec.MAP.get(str);
					else
						dec.MAP.put(str, new Func(dec, str));
					subfunc.add(f);
					f.callers.add(this);
				}

				private void getTree(Set<Func> set) {
					subfunc.forEach(s -> {
						if (set.contains(s))
							return;
						set.add(s);
						s.getTree(set);
					});
				}

			}

			private final Map<String, Func> MAP = new TreeMap<>();

			private final Set<String> set = new TreeSet<>();
			private final String[] list;

			private final Func demain;

			private Func main = null;
			private int lv = 0, line = 0;

			private Decoder(String[] arr) {
				list = arr;
				demain = new Func(this, "global");
				main = demain;
			}

			private void addTo(String func) {
				set.add(func);
				if (lv == 0) {

					main = MAP.containsKey(func) ? MAP.get(func) : new Func(this, func);
					if (list[line].endsWith(";"))
						main = demain;
				} else {
					main.add(func);
				}
			}

			private void getRes(String fun) {
				Func f = MAP.get(fun);
				Set<Func> set = new TreeSet<>();
				f.getTree(set);
				set.add(f);
				set.forEach(e -> {
					Set<Func> call = new TreeSet<>(e.callers);
					call.retainAll(set);
					System.out.println(e.name + "," + call);
					// for(int i=e.line0-1;i<e.line1;i++)System.out.println(list[i]);
				});

			}

			private void run() {
				Pattern pat0 = Pattern.compile("sub_.....\\(");
				Pattern pat1 = Pattern.compile("sub_......\\(");
				for (String str : list) {
					if (str.length() > 0) {
						int dlv = level(str);
						if (dlv > 0) {
							if (lv == 0 && main != demain)
								main.line0 = line;
							lv += dlv;
						}
						Matcher mat0 = pat0.matcher(str);
						while (mat0.find()) {
							int st = mat0.start();
							if (st > 0 && str.charAt(st - 1) == '_')
								continue;
							addTo(str.substring(st + 4, st + 9));

						}
						Matcher mat1 = pat1.matcher(str);
						while (mat1.find()) {
							int st = mat1.start();
							if (str.charAt(st + 9) == ')' || str.charAt(st + 9) == '(')
								continue;
							addTo(str.substring(st + 4, st + 10));
						}
						if (dlv < 0) {
							lv += dlv;
							if (lv == 0 && main != demain) {
								main.line1 = line + 1;
								main = demain;
							}
						}
					}
					line++;
				}

			}

		}

		private static class Parall {

			private static class Block {

				private int line0, line1;

				private final String name;

				private Block(Parall par, String line) {
					name = getFuncName(line.trim().split("\\(")[0]);
					par.blocks.add(this);
				}

				@Override
				public String toString() {
					return name;
				}

			}

			private final List<Block> blocks = new ArrayList<Block>();
			private final Set<String> set = new TreeSet<>();
			private final String[] list;
			private Block main;

			private int lv = 0, line = 0;

			private Parall(String[] arr) {
				list = arr;
			}

			private void findBlock(String str) {
				for (Block b : blocks)
					if (b.name.equals(str)) {
						for (int i = b.line0; i < b.line1; i++)
							System.out.println(list[i]);
					}
			}

			private void run() {
				for (String str : list) {
					if (str.length() > 0) {
						int dlv = level(str);
						if (dlv > 0) {
							if (lv == 0) {
								main = new Block(this, list[line - 1]);
								main.line0 = line - 1;
							}
							lv += dlv;
						}
						if (dlv < 0) {
							lv += dlv;
							if (lv == 0) {
								main.line1 = line + 1;
								main = null;
							}
						}
					}
					line++;
				}

			}

		}

		private static String getFuncName(String line) {
			int l0 = 0, lv = 0;
			for (int i = 0; i < line.length(); i++) {
				if (lv == 0 && line.charAt(i) == ' ')
					l0 = i + 1;
				else if (line.charAt(i) == '<')
					lv++;
				else if (line.charAt(i) == '>')
					lv--;
			}
			return line.substring(l0);
		}

		private static int level(String str) {
			int c0 = 0, c1 = 0;
			boolean inq = false;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == '"')
					if (inq) {
						if (str.charAt(i - 1) != '\\')
							inq = false;
					} else
						inq = true;

				if (!inq && str.charAt(i) == '{') {
					c0++;
				}
				if (!inq && str.charAt(i) == '}') {
					c1++;
				}
			}
			return c0 - c1;
		}

		private static void main$0() {
			String[] list = processor("8.9-libnative-lib.c");
			Decoder dec = new Decoder(list);
			dec.run();
			dec.getRes("1E2E74");
		}

		private static void main$1() {

			// new 0x20u: "AES-128/ECB/PKCS7"
			// 14: "SHA-2"
			// 20: password

			// 18 in C47FC

			// 68A18

			Parall par = new Parall(processor("7.0-libnative-lib.c"));
			par.run();
			Scanner sc = new Scanner(System.in);
			String inp = sc.nextLine().trim();
			while (!inp.equals("exit")) {
				par.findBlock(inp);
				inp = sc.nextLine().trim();
			}
			sc.close();

		}

		private static void main$2() {
			PrintStream ps = Writer.newFile("./img/bclib/7.0-out.c");
			Stream<String> s = read("7.0-libnative-lib.c");
			s.forEach(e -> ps.println(e));
			s.close();
			ps.close();
		}

		private static String process(String str) {
			String ans = str.trim();
			for (int i = 0; i < str.length(); i++)
				if (str.charAt(i) == ' ')
					ans = "  " + ans;
				else
					break;
			boolean inq = false;
			int bre = -1;
			for (int i = 0; i < ans.length(); i++) {
				if (ans.charAt(i) == '"')
					if (inq) {
						if (ans.charAt(i - 1) != '\\')
							inq = false;
					} else
						inq = true;
				if (!inq && i > 0 && ans.charAt(i - 1) == '/' && ans.charAt(i) == '/') {
					bre = i - 1;
					break;
				}
			}
			return bre == -1 ? ans : ans.substring(0, bre).trim();
		}

		private static String[] processor(String path) {
			Stream<String> list = read(path);
			List<String> arr = new ArrayList<>();
			list.forEach(s -> arr.add(process(s)));
			list.close();
			return arr.toArray(new String[0]);
		}

		@SuppressWarnings("resource")
		private static Stream<String> read(String path) {
			File f = new File("./img/bclib/" + path);
			try {
				return new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8")).lines();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	public static void main(String[] args) {
		String w0 = "171707";
		String w1 = "d754868de89d717fa9e7b06da45ae9e3";

		char[] str0 = new char[32];
		char[] str1 = new char[32];

		for (int i = 0; i < 32; i++) {
			str0[i] = w0.charAt(i % 6);
			str1[i] = (char) (str0[i] ^ w1.charAt(i));
		}

		for (int i = 0; i < 32; i++) {
			str1[i] = (char) (str1[i] ^ str0[31 - i]);
		}

		System.out.println(new String(str1));

	}

}