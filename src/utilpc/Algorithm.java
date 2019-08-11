package utilpc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import main.Printer;

public class Algorithm {

	public static class SRResult {

		public int[][] pos;
		public int center, w, h;

		private SRResult(int[][] ans, int[] dim) {
			w = dim[0];
			h = dim[1];
			pos = ans;
			for (int i = 0; i < ans.length; i++)
				if (ans[i][0] == 0 && ans[i][1] == 0)
					center = i;
		}

	}

	private static class StackRect {

		private static class Dot {

			private int x, y;
			private Dot pre, nxt;

			private Dot() {
				this(0, 0);
			}

			private Dot(int x0, int y0) {
				x = x0;
				y = y0;
			}

			private Dot add(int w, int h) {
				Dot top;
				insert(new Dot(x, y + h));
				insert(top = new Dot(x + w, y + h));
				insert(new Dot(x + w, y));
				remove();
				top.cleanPre();
				top.cleanNxt();
				return top;
			}

			private void cleanNxt() {
				if (unnec()) {
					Dot x = nxt;
					remove();
					x.cleanNxt();
					return;
				}
				if (valid())
					return;
				if (invalid()) {
					nxt.cleanNxt();
					return;
				}
				Dot dx = pre;
				dx.x = nxt.x;
				nxt.remove();
				remove();
				dx.cleanNxt();

			}

			private void cleanPre() {
				if (unnec()) {
					Dot x = pre;
					remove();
					x.cleanPre();
					return;
				}
				if (valid())
					return;
				if (invalid()) {
					pre.cleanPre();
					return;
				}
				Dot dx = nxt;
				dx.y = pre.y;
				pre.remove();
				remove();
				dx.cleanPre();
			}

			private Dot copy(int dire) {
				Dot ans = new Dot(x, y);
				if (nxt != null && dire != -1) {
					ans.nxt = nxt.copy(1);
					ans.nxt.pre = ans;
				}
				if (pre != null && dire != 1) {
					ans.pre = pre.copy(-1);
					ans.pre.nxt = ans;
				}
				return ans;
			}

			private void delete() {
				if (nxt != null)
					nxt.delete();
				nxt = null;
				pre = null;
			}

			private Dot findHead() {
				return pre == null ? this : pre.findHead();
			}

			private void insert(Dot d) {
				if (nxt != null)
					nxt.pre = d;
				d.nxt = nxt;
				nxt = d;
				d.pre = pre;
			}

			private boolean invalid() {
				if (pre == null || nxt == null)
					return false;
				boolean b = x >= pre.x;
				b &= y >= pre.y;
				b &= x >= nxt.x;
				b &= y >= nxt.y;
				return b;
			}

			private void remove() {
				if (pre != null)
					pre.nxt = nxt;
				if (nxt != null)
					nxt.pre = pre;
				pre = nxt = null;
			}

			private boolean unnec() {
				if (pre == null || nxt == null)
					return false;
				return pre.x == x && x == nxt.x || pre.y == y && y == nxt.y;
			}

			private boolean valid() {
				if (pre == null || nxt == null)
					return true;
				boolean b = x <= pre.x;
				b &= y <= pre.y;
				b &= x <= nxt.x;
				b &= y <= nxt.y;
				return b;
			}

		}

		private static final int DEFTYPE = 0;

		public static SRResult stackRect(int[][] rects) {
			int[][] rs = new int[rects.length][3];
			for (int i = 0; i < rects.length; i++)
				rs[i] = new int[] { rects[i][0], rects[i][1], i };
			int[][] res = new StackRect(rs).stack();
			int[][] ans = new int[rects.length][3];
			for (int i = 0; i < rects.length; i++)
				ans[i] = new int[] { res[i][0], res[i][1], rs[i][2] };
			Arrays.sort(ans, (o0, o1) -> Integer.compare(o0[2], o1[2]));
			return new SRResult(ans, dim(res));
		}

		private static int[] dim(int[][] ans) {
			int w = 0, h = 0;
			for (int[] a : ans)
				if (a != null) {
					w = Math.max(w, a[0] + a[2]);
					h = Math.max(h, a[1] + a[3]);
				}
			return new int[] { w, h };
		}

		private static Comparator<int[]> getComp(int t) {
			return (o1, o2) -> o1[1] != o2[1] ? Integer.compare(o2[1], o1[1]) : Integer.compare(o1[0], o2[0]);
		}

		private final int[][] rects;

		private int min, len, count;

		private int[][] best = null;

		private Set<Integer> set = new TreeSet<>();

		private StackRect(int[][] rs) {
			rects = rs;
			int w = 0, h = 0;
			for (int[] a : rects)
				if (a != null) {
					w = Math.max(w, a[0]);
					h = Math.max(h, a[1]);
				}
			min = rects.length * w * h;
			len = (int) (Math.ceil(Math.sqrt(rects.length)) * (w + h));
		}

		private boolean detRep(int[][] ans, int i) {
			if (!rawRep(i))
				return false;
			if (ans[i - 1] == null)
				return true;
			if (ans[i - 1][1] > ans[i][1])
				return true;
			return false;
		}

		private void operate(int[][] ans, Dot dots) {
			count++;
			boolean operated = false;
			for (int i = 0; i < ans.length; i++)
				if (ans[i] == null) {
					operated = true;
					if (rawRep(i) && ans[i - 1] == null)
						continue;
					ans[i] = new int[] { 0, 0, rects[i][0], rects[i][1] };
					for (Dot d = dots; d != null; d = d.nxt) {
						if (d.invalid())
							continue;
						ans[i][0] = d.x;
						ans[i][1] = d.y;
						if (detRep(ans, i))
							continue;
						int hash = Arrays.deepHashCode(ans);
						int[] dim = dim(ans);
						if (restrict(dim) && !set.contains(hash)) {
							set.add(hash);
							Dot ndot = d.copy(0).add(ans[i][2], ans[i][3]).findHead();
							operate(ans, ndot);
							ndot.delete();
						}
					}
					ans[i] = null;
				}
			if (!operated) {
				int[] dim = dim(ans);
				if (restrict(dim)) {
					min = dim[0] * dim[1];
					len = dim[0] + dim[1];
					best = ans.clone();
					for (int i = 0; i < best.length; i++)
						best[i] = best[i].clone();
				}
			}
		}

		private boolean rawRep(int i) {
			return i > 0 && rects[i - 1][0] == rects[i][0] && rects[i - 1][1] == rects[i][1];
		}

		private boolean restrict(int[] dim) {
			if (dim[0] * dim[1] < min)
				return true;
			if (dim[0] * dim[1] > min)
				return false;
			if (dim[0] + dim[1] < len)
				return true;
			return dim[0] + dim[1] == len && best == null;
		}

		private int[][] stack() {
			Arrays.sort(rects, getComp(DEFTYPE));
			int[][] ans = new int[rects.length][];
			operate(ans, new Dot());
			Printer.p("StackRect", 176, rects.length + "->" + count);
			return best;
		}

	}

	public static void main(String[] strs) {
		int[][] rect = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 } };
		SRResult res = stackRect(rect);
		System.out.println(res.w + "," + res.h);
	}

	public static SRResult stackRect(int[][] rects) {
		return StackRect.stackRect(rects);
	}
}
