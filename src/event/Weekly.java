package event;

public class Weekly extends Repeat {

	protected static final String[] name = new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

	protected boolean[] days = new boolean[7];

	protected Weekly(int week) {
		for (int i = 0; i < 7; i++)
			days[i] = ((week >> i) & 1) == 1;
	}

	@Override
	public String toString() {
		String ans = "";
		for (int i = 0; i < 7; i++)
			if (days[i]) {
				if (ans.length() > 0)
					ans += ", ";
				ans += Namer.get(name[i]);
			}
		return ans;
	}

	@Override
	protected boolean[] valid(EventDate d) {
		if (days[d.getWeek()])
			return construct(ALL, -1);
		else
			return null;
	}

}
