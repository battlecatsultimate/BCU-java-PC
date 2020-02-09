package event;

public class Event implements DisplayEvent {

	protected static final String prev = "https://battlecats-db.com/stage/s";
	private static final String prex = "https://ponos.s3.dualstack.ap-northeast-1.amazonaws.com/information/appli/battlecats/stage/";
	private static final String[] loca = new String[] { "", "tw/", "en/", "kr/" };
	protected static final String sufx = ".html";

	public final EventRaw raw;
	public final int stage;

	protected Event(EventRaw er, int st) {
		raw = er;
		stage = st;
	}

	@Override
	public Event[] getEvents() {
		return new Event[] { this };
	}

	@Override
	public String getID() {
		return stage + "";
	}

	public String[] getStrings() {
		int n = raw.loop + 3;
		String[] ans = new String[n];
		ans[0] = raw.toString();
		for (int i = 0; i < raw.loop; i++)
			ans[i + 1] = raw.loops[i].toString();
		String sta = "" + stage % 1000;
		while (sta.length() < 3)
			sta = "0" + sta;
		int st = stage / 1000;
		if (st == 1)
			ans[n - 2] = prex + loca[EventReader.loc] + "S" + sta + sufx;
		if (st == 2)
			ans[n - 2] = prex + loca[EventReader.loc] + "C" + sta + sufx;
		String stag = "" + stage;
		while (stag.length() < 5)
			stag = "0" + stag;
		if (st == 1 || st == 2 || st == 11)
			ans[n - 1] = prev + stag + sufx;
		return ans;
	}

	@Override
	public int getType() {
		return raw.type;
	}

	@Override
	public String toString() {
		return Namer.getE(stage);
	}

}
