package io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

import util.Data;
import util.pack.Pack;

public class WebPack {

	public static class JLCont {

		private static final Map<JLabel, JLCont> map = new HashMap<>();

		public static JLCont getIns(JLabel jl, WebImg wi) {
			JLCont jc;
			if (map.containsKey(jl))
				jc = map.get(jl);
			else {
				jc = new JLCont(jl, wi);
				map.put(jl, jc);
			}
			jc.check(wi);
			return jc;
		}

		private final JLabel label;

		private int w, h;
		private WebImg img;

		private JLCont(JLabel jl, WebImg wi) {
			label = jl;
			check(wi);
		}

		private synchronized void check(WebImg wi) {
			if (wi == img && w == label.getWidth() && h == label.getHeight() && label.getIcon() != null)
				return;
			img = wi;
			w = label.getWidth();
			h = label.getHeight();
		}

	}

	public static class WebImg implements Runnable, Consumer<Progress> {

		private static final int PRELOAD = 0, LOADING = 1, LOADED = 2, NOIMG = 3, FAILED = 4;

		private String url, name;
		private BufferedImage bimg;
		private File tmp;

		private int state = PRELOAD;

		private Set<JLCont> wait = new HashSet<>();

		private TreeMap<Integer, BufferedImage> map = new TreeMap<>();

		public WebImg(String str) {
			url = str;
			if (str.length() > 0) {
				url = "http://battlecatsultimate.cf/packs/res/" + str;
				tmp = new File("./pack/img/" + str);
				if (tmp.exists()) {
					try {
						bimg = ImageIO.read(tmp);
						state = LOADED;
						load();
						return;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				String[] strs = str.split("/");
				name = strs[strs.length - 1];
				name = name.substring(0, name.length() - 4);
				new Thread(this).start();
			} else
				state = NOIMG;
		}

		@Override
		public synchronized void accept(Progress t) {
			if (t.state == Progress.DONE)
				try {
					bimg = ImageIO.read(tmp);
					map.put(bimg.getWidth(), bimg);
					state = LOADED;
					load();
				} catch (IOException e) {
					state = FAILED;
					e.printStackTrace();
				}
		}

		public synchronized void load(JLabel icon) {
			synchronized (wait) {
				synchronized (icon) {
					wait.add(JLCont.getIns(icon, this));
				}
			}
			if (bimg != null)
				load();
		}

		@Override
		public void run() {
			state = LOADING;
			WebFileIO.download(url, tmp, this);
		}

		private void load() {
			synchronized (wait) {
				if (bimg == null || wait.size() == 0 || state == NOIMG)
					return;
				for (JLCont cont : wait) {
					if (cont.img != this)
						return;
					JLabel target = cont.label;
					int tw, th;
					synchronized (target) {
						tw = target.getWidth();
						th = target.getHeight();
					}
					if (tw * th == 0)
						return;
					int bw = bimg.getWidth();
					int bh = bimg.getHeight();
					double r = Math.min(1.0 * tw / bw, 1.0 * th / bh);
					int mw = (int) (r * bw);
					int mh = (int) (r * bh);
					BufferedImage res = null;
					if (map.containsKey(mw))
						res = map.get(mw);
					else {
						res = new BufferedImage(mw, mh, bimg.getType());
						Graphics2D g = res.createGraphics();
						g.drawImage(bimg, 0, 0, mw, mh, null);
						g.dispose();
						map.put(mw, res);
					}
					synchronized (target) {
						target.setIcon(new ImageIcon(res));
					}
				}
				wait.clear();
			}
		}

	}

	public static final int SORT_ID = 0, SORT_RATE = 1, SORT_POP = 2, SORT_NEW = 3, SORT_UPDATE = 4;

	public static final int MAX_IMG = 1;

	public static Map<Integer, WebPack> packlist = new TreeMap<>();

	public static void clear() {
		for (WebPack wp : packlist.values()) {
			synchronized (wp.icon.wait) {
				wp.icon.wait.clear();
			}
			if (wp.thumbs != null)
				for (WebImg wi : wp.thumbs)
					synchronized (wi.wait) {
						wi.wait.clear();
					}
		}
	}

	public static Comparator<WebPack> getComp(int t) {
		return new WebPackComp(t);
	}

	public int pid, uid, version, bcuver, vote, state;
	public String name, author, time;
	public int[][] rate;

	public WebImg icon;
	public List<WebImg> thumbs = null;

	protected String desp = "";

	private boolean loaded;

	protected WebPack(int pack) {
		pid = pack;
		rate = new int[2][6];
		packlist.put(pid, this);
		icon = new WebImg("");
	}

	protected WebPack(JSONObject pack) {
		pid = pack.getInt("pid");
		uid = pack.getInt("uid");
		author = pack.getString("author");
		name = pack.getString("name");
		version = pack.getInt("version");
		bcuver = pack.getInt("bcuver");
		vote = pack.getInt("vote");
		state = pack.getInt("state");
		time = pack.getString("time");
		boolean hasIcon = pack.getBoolean("icon");
		rate = BCJSON.getRate(pack.getJSONObject("rate"));
		packlist.put(pid, this);
		if (hasIcon)
			icon = new WebImg(pid + "/img/icon.png");
		else
			icon = new WebImg("");
	}

	public boolean check() {
		if (!loaded)
			return load();
		return true;
	}

	public String getDesp() {
		check();
		return desp;
	}

	public int getNextThumbID() {
		if (thumbs == null)
			return -1;
		for (int i = 0; i <= Math.min(thumbs.size(), MAX_IMG); i++) {
			String name = Data.trio(i);
			boolean b = false;
			for (WebImg wi : thumbs)
				b |= name == wi.name;
			if (!b)
				return i;
		}
		return 0;
	}

	public int getRate_0() {
		int ans = 0;
		for (int i = 1; i <= 5; i++)
			ans += (i - 3) * rate[0][i];
		return ans;
	}

	public int getRate_1() {
		int ans = 0;
		int c = 0;
		for (int i = 1; i <= 5; i++) {
			ans += i * rate[0][i];
			c += rate[0][i];
		}
		if (c == 0)
			return 0;
		return ans * 100 / c;
	}

	public boolean load() {
		loaded = BCJSON.getPackInfo(this, pid);
		return loaded;
	}

	public void loadImg(JSONArray arr) {
		String[] strs = new String[arr.length()];
		for (int i = 0; i < arr.length(); i++)
			strs[i] = arr.getString(i);
		thumbs = new ArrayList<WebImg>();
		for (String str : strs)
			if (str.length() == 7 && str.endsWith(".png")) {
				if (Reader.parseIntN(str) < 0)
					continue;
				thumbs.add(new WebImg(pid + "/img/" + str));
			}
	}

	public void loadThumb(JLabel thumb, int i) {
		if (thumbs != null && thumbs.size() > i)
			thumbs.get(i).load(thumb);
	}

	@Override
	public String toString() {
		return pid + "-" + version + ": " + name;
	}

}

class WebPackComp implements Comparator<WebPack> {

	private static int comp(int t, WebPack o1, WebPack o2, boolean rep) {
		if (t == WebPack.SORT_POP) {
			int val = -Integer.compare(o1.getRate_0(), o2.getRate_0());
			if (val == 0 && rep)
				val = comp(WebPack.SORT_RATE, o1, o2, false);
			return val;
		}
		if (t == WebPack.SORT_RATE) {
			int val = -Integer.compare(o1.getRate_1(), o2.getRate_1());
			if (val == 0 && rep)
				val = comp(WebPack.SORT_POP, o1, o2, false);
			return val;
		}
		if (t == WebPack.SORT_NEW)
			return -o1.time.compareTo(o2.time);
		if (t == WebPack.SORT_UPDATE) {
			Pack p1 = Pack.map.get(o1.pid);
			Pack p2 = Pack.map.get(o2.pid);
			int a1 = p1 == null ? 0 : (p1.version == o1.version ? 1 : 2);
			int a2 = p2 == null ? 0 : (p2.version == o2.version ? 1 : 2);
			if (a1 == a2)
				return comp(WebPack.SORT_NEW, o1, o2, true);

			return Integer.compare(a1, a2);
		}
		return Integer.compare(o1.pid, o2.pid);
	}

	private final int type;

	WebPackComp(int typ) {
		type = typ;
	}

	@Override
	public int compare(WebPack o1, WebPack o2) {
		return comp(type, o1, o2, true);
	}

}