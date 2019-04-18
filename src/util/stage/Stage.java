package util.stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import io.InStream;
import io.OutStream;
import io.Reader;
import main.MainBCU;
import util.Data;
import util.pack.Pack;
import util.system.BasedCopable;
import util.system.MultiLangCont;
import util.system.VFile;

public class Stage extends Data implements BasedCopable<Stage, StageMap> {

	public static final MapColc clipmc = new MapColc("clip", -1);
	public static final StageMap clipsm = clipmc.maps[0];

	public String name = "";
	public final StageMap map;
	public boolean non_con, trail;
	public int len, health, max;
	public int bg, mus0 = -1, mush, mus1 = -1;
	public int castle;
	public int[][] datas;
	public Limit lim;

	public Stage(StageMap sm) {
		map = sm;
		len = 3000;
		health = 60000;
		max = 8;
		name = "stage " + sm.list.size();
		datas = new int[0][10];
		lim = new Limit();
	}

	public Stage(StageMap sm, String str, InStream is) {
		map = sm;
		zread(str, is);
	}

	protected Stage(StageMap sm, int id, VFile f, int type) {
		Queue<String> qs = readLine(f);
		name = "" + id;
		map = sm;
		String temp;
		if (type == 0) {
			temp = qs.poll();
			String[] strs = temp.split(",");
			castle = Reader.parseIntN(strs[0]);
			non_con = strs[1].equals("1");
		} else {
			castle = -1;
			non_con = false;
		}
		int intl = type == 2 ? 9 : 10;
		String[] strs = qs.poll().split(",");
		len = Integer.parseInt(strs[0]);
		health = Integer.parseInt(strs[1]);
		bg = Integer.parseInt(strs[4]);
		max = Integer.parseInt(strs[5]);
		List<int[]> ll = new ArrayList<>();
		while (qs.size() > 0)
			if ((temp = qs.poll()).length() > 0) {
				if (!Character.isDigit(temp.charAt(0)))
					break;
				if (temp.startsWith("0,"))
					break;
				String[] ss = temp.split(",");
				int[] data = new int[10];
				for (int i = 0; i < intl; i++)
					data[i] = Integer.parseInt(ss[i]);
				data[0] -= 2;
				data[2] *= 2;
				data[3] *= 2;
				data[4] *= 2;
				if (intl > 9 && data[5] > 100 && data[9] == 100) {
					data[9] = data[5];
					data[5] = 100;
				}
				ll.add(data);
			}
		datas = new int[ll.size()][10];
		validate();
		for (int i = 0; i < ll.size(); i++)
			datas[i] = ll.get(datas.length - i - 1);
		if (strs.length > 6) {
			int ano = Reader.parseIntN(strs[6]);
			if (ano == 317)
				datas[ll.size() - 1][5] = 0;
		}
	}

	@Override
	public Stage copy(StageMap sm) {
		Stage ans = new Stage(sm);
		ans.len = len;
		ans.health = health;
		ans.max = max;
		ans.bg = bg;
		ans.castle = castle;
		ans.name = name;
		ans.datas = new int[datas.length][];
		for (int i = 0; i < datas.length; i++)
			ans.datas[i] = datas[i].clone();
		if (lim != null)
			ans.lim = lim.clone();
		return ans;
	}

	public int getCastle() {
		int ans = castle;
		if (ans < 1000 && map.cast != -1)
			ans += map.cast * 1000;
		if (castle < 0 || Castles.getCastle(ans) == null)
			return 0;
		return ans;
	}

	public Limit getLim(int star) {
		Limit tl = new Limit();
		if (lim != null && (lim.star == -1 || lim.star == star))
			tl.combine(lim);
		for (Limit l : map.lim)
			if (l.star == -1 || l.star == star)
				if (l.sid == -1 || l.sid == id())
					tl.combine(l);
		return tl;
	}

	public int id() {
		return map.list.indexOf(this);
	}

	public boolean isSuitable(Pack p) {
		for (int[] ints : datas) {
			if (ints[0] < 1000)
				continue;
			int pac = ints[0] / 1000;
			boolean b = pac == p.id;
			for (int rel : p.rely)
				b |= pac == rel;
			if (!b)
				return false;
		}
		return true;
	}

	public void setCast(int val) {
		castle = val;
	}

	public void setName(String str) {
		str = MainBCU.validate(str);
		while (!checkName(str))
			str += "'";
		name = str;
	}

	@Override
	public String toString() {
		String desp=MultiLangCont.get(this);
		if (desp!=null&&desp.length() > 0)
			return desp;
		if (name.length() > 0)
			return name;
		return map + " - " + id();
	}

	public OutStream write() {
		OutStream os = new OutStream();
		os.writeString("0.3.8");
		os.writeString(toString());
		os.writeInt(bg);
		os.writeInt(castle);
		os.writeInt(health);
		os.writeInt(len);
		os.writeInt(mus0);
		os.writeInt(mush);
		os.writeInt(mus1);
		os.writeByte((byte) max);
		os.writeByte((byte) (non_con ? 1 : 0));
		os.writeByte((byte) datas.length);
		for (int i = 0; i < datas.length; i++)
			for (int j = 0; j < 10; j++)
				os.writeInt(datas[i][j]);
		lim.write(os);
		os.terminate();
		return os;
	}

	protected void validate() {
		boolean t = false;
		for (int[] data : datas)
			if (data[5] > 100)
				t = true;
		trail = t;
	}

	private boolean checkName(String str) {
		for (Stage st : map.list)
			if (st != this && st.name.equals(str))
				return false;
		return true;
	}

	private void zread(String ver, InStream is) {
		int val = getVer(ver);
		if (val >= 308)
			zread$000308(val, is);
		else if (val >= 305)
			zread$000305(val, is);
		else if (val >= 203)
			zread$000203(is);
		validate();
	}

	private void zread$000203(InStream is) {
		name = is.nextString();
		bg = is.nextInt();
		castle = is.nextInt();
		health = is.nextInt();
		len = is.nextInt();
		max = is.nextByte();
		non_con = is.nextByte() == 1;
		int n = is.nextByte();
		datas = new int[n][10];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < 10; j++)
				datas[i][j] = is.nextInt();
			if (datas[i][5] < 100)
				datas[i][2] *= -1;
		}
		lim = new Limit(map.mc, 0, is);
	}

	private void zread$000305(int val, InStream is) {
		name = is.nextString();
		bg = is.nextInt();
		castle = is.nextInt();
		health = is.nextInt();
		len = is.nextInt();
		max = is.nextByte();
		non_con = is.nextByte() == 1;
		int n = is.nextByte();
		datas = new int[n][10];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 10; j++)
				datas[i][j] = is.nextInt();
		lim = new Limit(map.mc, val, is);
	}

	private void zread$000308(int val, InStream is) {
		name = is.nextString();
		bg = is.nextInt();
		castle = is.nextInt();
		health = is.nextInt();
		len = is.nextInt();
		mus0 = is.nextInt();
		mush = is.nextInt();
		mus1 = is.nextInt();
		max = is.nextByte();
		non_con = is.nextByte() == 1;
		int n = is.nextByte();
		datas = new int[n][10];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < 10; j++)
				datas[i][j] = is.nextInt();
		lim = new Limit(map.mc, val, is);
	}

}
