package event;

import java.util.ArrayList;
import java.util.List;

public class EventBase {

	public static EventBase ebe, ebg, ebi;
	public static final int E = 1, G = 2, I = 3, A = 4;

	public static void clear() {
		ebe = ebg = ebi = null;
	}

	public String time;
	public int type, nv;
	public final List<Item> li = new ArrayList<>();
	public final List<DisplayEvent> le = new ArrayList<>();
	public final List<Gacha> lg = new ArrayList<>();

	protected final List<EventRaw> ler = new ArrayList<>();

	protected EventBase(int t) {
		type = t;
		if (t == E || t == A)
			ebe = this;
		if (t == G || t == A)
			ebg = this;
		if (t == I || t == A)
			ebi = this;
	}

}
