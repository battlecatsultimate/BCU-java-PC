package event;

public abstract class Repeat {

	public static final int ALL = -1, NONE = 0, BEFORE = 1, AFTER = 2;

	public static boolean[] construct(int type, int hr) {
		boolean[] ans = new boolean[24];
		if (type == -1)
			for (int i = 0; i < 24; i++)
				ans[i] = true;
		if (type == 1)
			for (int i = 0; i < hr; i++)
				ans[i] = true;
		if (type == 2)
			for (int i = hr; i < 24; i++)
				ans[i] = true;
		return ans;
	}

	protected abstract boolean[] valid(EventDate d);

}
