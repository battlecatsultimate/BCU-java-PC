package io;

import static io.WebPack.packlist;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import main.MainBCU;
import page.LoadPage;
import page.MainLocale;
import util.Data;
import util.pack.Pack;

public class BCJSON extends Data {

	public static String USERNAME = "";
	public static long PASSWORD = 0;
	public static int ID = 0;
	public static int lib_ver = 1;
	public static int cal_ver = 0;

	private static final String str = "http://battlecatsultimate.cf/api/java/";
	private static final String dld = "http://battlecatsultimate.cf/api/resources/";
	private static final String l0l = "000/viewer.list";
	private static final String l0p = "000/viewer.pack";
	private static final String l1l = "001/viewer.list";
	private static final String l1p = "001/viewer.pack";
	private static final String path = "./lib/";
	private static final String login = "login.php";
	private static final String upload = "upload.php";
	private static final String rate = "rate.php";
	private static final String getinfo = "getinfo.php";
	private static final String delete = "delete.php";
	private static final String fileio = "fileio.php";
	private static final String retrieve = "acquire.php";

	private static final String[] cals;

	static {
		cals = new String[19];
		String cal = "calendar/";
		cals[0] = cal + "event ID.txt";
		cals[1] = cal + "gacha ID.txt";
		cals[2] = cal + "item ID.txt";
		cals[3] = cal + "group event.txt";
		cals[4] = cal + "group hour.txt";
		cals[5] = cal + "unit name.txt";
		cals[6] = cal + "stage name.txt";

		for (int i = 0; i < 3; i++) {// TODO
			String lang = "lang/" + MainLocale.LOCALE[i] + "/";
			cals[i * 4 + 7] = lang + "util.properties";
			cals[i * 4 + 8] = lang + "page.properties";
			cals[i * 4 + 9] = lang + "info.properties";
			cals[i * 4 + 10] = lang + "internet.properties";
			// TODO tutorial
		}

	}

	public static void checkDownload() {
		LoadPage.prog(0, 8, 0);
		File f0l = new File(path + l0l);
		File f0p = new File(path + l0p);
		File f1l = new File(path + l1l);
		File f1p = new File(path + l1p);
		File f;
		JSONObject data = null;
		try {
			data = getUpdate();
		} catch (IOException e) {
		}
		LoadPage.prog(0, 8, 1);
		if (data != null && data.length() >= 7) {
			if (!f0l.exists() && !download(dld + l0l, f0l))
				MainBCU.pop("failed to download " + l0l, "download error");
			LoadPage.prog(0, 8, 2);
			if (!f0p.exists()) {
				byte[] bs0 = download(dld + "000/subviewer0.pack");
				LoadPage.prog(0, 8, 3);
				byte[] bs1 = download(dld + "000/subviewer1.pack");
				if (bs0 == null || bs1 == null)
					MainBCU.pop("failed to download " + l0p, "download error");
				byte[] bs = Arrays.copyOf(bs0, bs0.length + bs1.length);
				for (int i = 0; i < bs1.length; i++)
					bs[bs0.length + i] = bs1[i];
				if (!Writer.writeBytes(bs, path + l0p))
					MainBCU.pop("failed to write " + l0p, "file permission error");
			}
			LoadPage.prog(0, 8, 4);
			if (!f1l.exists() && !download(dld + l1l, f1l))
				MainBCU.pop("failed to download " + l1l, "download error");
			if (!f1p.exists() && !download(dld + l1p, f1p))
				MainBCU.pop("failed to download " + l1p, "download error");
			for (int i = 0; i < cals.length; i++)
				if (!(f = new File(path + cals[i])).exists() && !download(dld + cals[i], f))
					MainBCU.pop("failed to download " + cals[i], "download error");
			LoadPage.prog(0, 8, 5);
			if (MainBCU.ver < data.getInt("jar")) {
				if (MainBCU.warning("JAR update available. do you want to update? " + data.getString("jar-desc"),
						"update check")) {
					String name = "BCU " + revVer(data.getInt("jar")) + ".jar";
					byte[] bs = download(dld + "jar/" + name);
					if (bs != null && Writer.writeBytes(bs, "./" + name)) {
						Writer.logClose(false);
						System.exit(0);
					} else
						MainBCU.pop("failed to download " + name, "download error");
				}

			}
			if (lib_ver < data.getInt("lib")) {
				if (MainBCU.warning("LIB update available. do you want to update? " + data.getString("lib-desc"),
						"update check")) {
					if (!download(dld + l1l, f1l))
						MainBCU.pop("failed to download " + l1l, "download error");
					if (!download(dld + l1p, f1p))
						MainBCU.pop("failed to download " + l1p, "download error");
					lib_ver = data.getInt("lib");
				}
			}
			if (cal_ver < data.getInt("cal")) {
				if (MainBCU.warning("text update available. do you want to update? " + data.getString("lib-desc"),
						"update check")) {
					for (int i = 0; i < cals.length; i++)
						if (!(f = new File(path + cals[i])).exists() && !download(dld + cals[i], f))
							MainBCU.pop("failed to download " + cals[i], "download error");
					cal_ver = data.getInt("cal");
				}
			}
			LoadPage.prog(0, 8, 6);
			int music = data.getInt("music");
			boolean[] mus = new boolean[music];
			File[] fs = new File[music];
			boolean down = false;
			for (int i = 0; i < music; i++)
				down |= mus[i] = !(fs[i] = new File("./lib/music/" + Data.trio(i) + ".ogg")).exists();
			if (down && MainBCU.warning("do you want to download new music?", "update check"))
				for (int i = 0; i < music; i++)
					if (mus[i])
						if (!download(dld + "music/" + Data.trio(i) + ".ogg", fs[i]))
							MainBCU.pop("failed to download music #" + i, "download error");
		}

		LoadPage.prog(0, 8, 7);
		boolean need = false;
		f = new File("./lib/000/");
		if (need |= !f.exists())
			f.mkdirs();
		f = new File("./lib/001/");
		if (need |= !f.exists())
			f.mkdirs();
		f = new File("./lib/calendar/");
		if (need |= !f.exists())
			f.mkdirs();
		need |= !f0l.exists();
		need |= !f0p.exists();
		need |= !f1l.exists();
		need |= !f1p.exists();
		for (int i = 0; i < cals.length; i++)
			need |= !new File(path + cals[i]).exists();
		if (need) {
			MainBCU.pop("failed to connect to internet while download is necessary", "download error");
			Writer.logClose(false);
			System.exit(0);
		}
		LoadPage.prog(1, 1, 0);
	}

	public static boolean delete(int pid) {
		JSONObject inp = new JSONObject();
		inp.put("uid", ID);
		inp.put("password", PASSWORD);
		inp.put("pid", pid);
		try {
			JSONObject ans = read(inp.toString(), delete);
			int ret = ans.getInt("ret");
			if (ret == 0) {
				WebPack wp = packlist.get(pid);
				wp.state = 1 - wp.state;
			}
			return ret == 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean download(String url, File file) {
		Writer.check(file);
		try {
			FileUtils.copyURLToFile(new URL(url), file);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			MainBCU.pop("failed to download", "download error");
			return false;
		}
	}

	public static int getID(String str) throws IOException {
		JSONObject inp = new JSONObject();
		inp.put("name", str);
		if (PASSWORD == 0)
			PASSWORD = new Random().nextLong();
		inp.put("password", PASSWORD);
		inp.put("bcuver", MainBCU.ver);
		JSONObject ans = read(inp.toString(), login);
		int ret = ans.getInt("ret");
		if (ret == 0)
			return ans.getInt("id");
		if (ret == 1)
			return -1;
		else if (ret == 2)
			return -100;
		throw new IOException(ans.getString("message"));
	}

	public static List<WebPack> getPacks(int uid, int sort) throws IOException {
		if (packlist.size() == 0)
			refreshPacks();
		List<WebPack> l = new ArrayList<>();
		for (WebPack wp : packlist.values())
			if ((uid == -1 || wp.uid == uid) && wp.state == 0 || wp.uid == uid && uid == ID)
				l.add(wp);
		l.sort(WebPack.getComp(sort));

		return l;
	}

	public static int getPassword(String str) throws IOException {
		JSONObject inp = new JSONObject();
		inp.put("name", str);
		JSONObject ans = read(inp.toString(), retrieve);
		int ret = ans.getInt("ret");
		if (ret == 0) {
			PASSWORD = ans.getLong("password");
			return ans.getInt("id");
		}
		if (ret == 1)
			return -1;
		else if (ret == 2)
			return -100 - ans.getInt("err");
		throw new IOException(ans.getString("message"));
	}

	public static int[][] getRate(JSONObject arr) {
		int[][] ans = new int[2][6];
		JSONArray tot = arr.getJSONArray("tot");
		JSONArray cur = arr.getJSONArray("cur");
		for (int i = 0; i < 6; i++) {
			ans[0][i] = tot.getInt(i);
			ans[1][i] = cur.getInt(i);
		}
		return ans;
	}

	public static int[][] rate(int pid, int val) {
		JSONObject inp = new JSONObject();
		inp.put("uid", ID);
		inp.put("password", PASSWORD);
		inp.put("pid", pid);
		inp.put("rate", val + 1);

		try {
			JSONObject ans = read(inp.toString(), rate);
			int ret = ans.getInt("ret");
			return ret == 0 ? getRate(ans.getJSONObject("rate")) : null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void refreshPacks() throws IOException {
		JSONObject inp = new JSONObject();
		inp.put("user", ID);
		JSONObject res = read(inp.toString(), getinfo);
		JSONArray packs = res.getJSONArray("pack");
		int len = packs.length();
		packlist.clear();
		for (int i = 0; i < len; i++) {
			new WebPack(packs.getJSONObject(i));
		}
	}

	public static boolean reversion(int pid) {
		Pack p = Pack.map.get(pid);
		WebPack wp = packlist.get(pid);
		wp.version++;
		p.version = wp.version;
		p.packUp();
		File f = new File("./pack/" + pid + ".bcupack");
		if (f.exists())
			try {
				boolean b = write(f);
				if (b) {
					JSONObject inp = new JSONObject();
					inp.put("uid", ID);
					inp.put("password", PASSWORD);
					inp.put("pid", pid);
					inp.put("rev", 1);
					inp.put("bcuver", MainBCU.ver);
					JSONObject ans = read(inp.toString(), upload);
					if (ans.getInt("ret") == 0)
						return true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		wp.version--;
		p.version = wp.version;
		return false;
	}

	public static boolean update(int pid, String name, String desc) {
		JSONObject inp = new JSONObject();
		inp.put("uid", ID);
		inp.put("password", PASSWORD);
		inp.put("pid", pid);
		inp.put("name", process(name));
		inp.put("desc", process(desc));

		try {
			JSONObject ans = read(inp.toString(), upload);
			int ret = ans.getInt("ret");
			if (ret == 0) {
				WebPack wp = packlist.get(pid);
				wp.desp = desc;
				wp.name = name;
				wp.version++;
			}
			return ret == 0;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int upload(int pid, String name, String desc) {
		JSONObject inp = new JSONObject();
		inp.put("uid", ID);
		inp.put("password", PASSWORD);
		inp.put("pid", pid);
		inp.put("name", process(name));
		inp.put("desc", process(desc));
		inp.put("bcuver", MainBCU.ver);

		try {
			JSONObject ans = read(inp.toString(), upload);
			int ret = ans.getInt("ret");
			if (ret == 0) {
				WebPack wp = new WebPack(pid);
				wp.author = USERNAME;
				wp.desp = desc;
				wp.name = name;
				wp.uid = ID;
				wp.url = str + "downloadpack.php?packid=" + pid;
				boolean b = reversion(pid);
				return b ? 0 : 5;
			} else if (ret == 2)
				return ans.getInt("err");
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
			return 4;
		}
	}

	private static byte[] download(String url) {
		File file = new File("./lib/temp.download");
		Writer.check(file);
		file.deleteOnExit();
		OutStream os = new OutStream();
		try {
			FileUtils.copyURLToFile(new URL(url), file, 5000, 5000);
			InputStream in = new FileInputStream(file);
			in = new BufferedInputStream(in);
			byte[] bts = new byte[1024];
			int len = -1;
			while ((len = in.read(bts)) == 1024)
				os.concat(bts);
			in.close();
			if (len > 0)
				os.concat(Arrays.copyOf(bts, len));
			os.terminate();
			return os.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
			MainBCU.pop("failed to download", "download error");
			return null;
		}
	}

	private static JSONObject getUpdate() throws IOException {
		JSONObject inp = new JSONObject();
		inp.put("bcuver", MainBCU.ver);
		JSONObject ans = read(inp.toString(), "getupdate.php");
		int ret = ans.getInt("ret");
		if (ret == 0)
			return ans;
		throw new IOException(ans.getString("message"));
	}

	private static String process(String str) {
		str = str.replaceAll("\\'", "\\\\'");
		return str;
	}

	private static JSONObject read(String json, String app) throws IOException {

		URL url = new URL(str + app);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		OutputStream os = conn.getOutputStream();
		os.write(json.getBytes("UTF-8"));
		os.close();

		InputStream in = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(in, "UTF-8");
		String result = readAll(new BufferedReader(isr));
		if (!MainBCU.write)
			System.out.println("result: " + result);// TODO
		if (!result.startsWith("{"))
			throw new IOException(result);
		JSONObject ans = new JSONObject(result);
		in.close();
		conn.disconnect();
		return ans;
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static boolean write(File file) throws IOException {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(str + fileio);
		FileBody fileBody = new FileBody(file, ContentType.DEFAULT_BINARY);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("catFile", fileBody);

		HttpEntity reqEntity = builder.build();
		post.setEntity(reqEntity);
		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200)
			return true;
		System.err.println("statusCode: " + statusCode);
		HttpEntity respEntity = response.getEntity();
		String responseString = EntityUtils.toString(respEntity, "UTF-8");
		System.err.println("response body: ");
		System.err.println(responseString);
		return false;
	}

}
