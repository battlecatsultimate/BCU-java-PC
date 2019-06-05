package io;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import org.json.JSONArray;
import org.json.JSONObject;

public class WebPack {

	public static class WebImg implements Runnable, Consumer<Progress> {

		private static final int PRELOAD = 0, LOADING = 1, LOADED = 2, NOIMG = 3, FAILED = 4;

		public static BufferedImage contain(BufferedImage bimg, int tw, int th) {
			int bw = bimg.getWidth();
			int bh = bimg.getHeight();
			double r = Math.min(1.0 * tw / bw, 1.0 * th / bh);
			int mw = (int) (r * bw);
			int mh = (int) (r * bh);
			BufferedImage res = new BufferedImage(mw, mh, bimg.getType());
			Graphics2D g = res.createGraphics();
			g.drawImage(bimg, 0, 0, mw, mh, null);
			g.dispose();
			return res;
		}

		private String url, temp;
		private BufferedImage bimg;

		private int state = PRELOAD;

		private List<JLabel> wait = new ArrayList<>();

		private TreeMap<Integer, BufferedImage> map = new TreeMap<>();

		public WebImg(String str) {
			url = str;
			temp = "" + Math.abs(url.hashCode());
			if (str.length() > 0) {
				url = "http://battlecatsultimate.cf/packs/res/" + str;
				new Thread(this).start();
			} else
				state = NOIMG;
		}

		@Override
		public synchronized void accept(Progress t) {
			if (t.state == Progress.DONE)
				try {
					bimg = ImageIO.read(new File("./img/tmp/" + temp + ".png"));
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
				wait.add(icon);
			}
			if (bimg != null)
				load();
		}

		@Override
		public void run() {
			state = LOADING;
			WebFileIO.download(url, new File("./img/tmp/" + temp + ".png"), this);
		}

		private void load() {
			synchronized (wait) {
				if (bimg == null || wait.size() == 0 || state == NOIMG)
					return;
				for (JLabel target : wait) {
					int tw, th;
					synchronized (target) {
						tw = target.getWidth();
						th = target.getHeight();
					}
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
			}
		}

	}

	public static final int SORT_ID = 0, SORT_RATE = 1, SORT_POP = 2, SORT_NEW = 3;

	protected static Map<Integer, WebPack> packlist = new TreeMap<>();

	public static void clear() {
		for (WebPack wp : packlist.values()) {
			synchronized (wp.icon.wait) {
				wp.icon.wait.clear();
			}
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
				thumbs.add(new WebImg("/img/" + str));
			}
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