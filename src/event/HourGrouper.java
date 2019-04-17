package event;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class HourGrouper {

	protected static final List<HourGrouper> lgh = new ArrayList<>();

	public static void process(Queue<String> qs) {
		lgh.clear();
		while (qs.size() > 0) {
			String[] str = qs.poll().trim().split("\t");
			new StageGrouper(str);
		}
		new MissionGrouper();
	}

	public abstract String getName();

	protected abstract void grouping(TimedEvent te);

}
