package util.basis;

import java.util.ArrayList;
import java.util.List;
import io.OutStream;
import page.KeyHandler;
import util.stage.EStage;
import util.stage.Recd;
import util.stage.Stage;

public class SBCtrl extends StageBasis {

	private final KeyHandler keys;

	public final List<Integer> action = new ArrayList<>();
	public final List<Integer> recd = new ArrayList<>();

	private int num, rep;

	public final Recd re;

	public SBCtrl(KeyHandler kh, Stage stage, int star, BasisLU bas, int[] ints, long seed) {
		super(new EStage(stage, star), bas, ints, seed);
		re = new Recd(bas, stage, star, ints, seed);
		keys = kh;
	}

	public Recd getData() {
		if (rep > 0) {
			recd.add(num);
			recd.add(rep);
		}
		num = 0;
		rep = 0;
		OutStream os = new OutStream(recd.size() * 4 + 4);
		os.writeInt(recd.size());
		for (int i : recd)
			os.writeInt(i);
		os.terminate();
		re.action = os;
		return re;
	}

	/** process the user action */
	@Override
	protected void actions() {
		if (ebase.health < 0)
			return;
		int rec = 0;
		if ((keys.pressed(-1, 0) || action.contains(-1)) && act_mon()) {
			rec |= 1;
			keys.remove(-1, 0);
		}
		if ((keys.pressed(-1, 1) || action.contains(-2)) && act_can()) {
			rec |= 2;
			keys.remove(-1, 1);
		}
		if ((keys.pressed(-1, 2) || action.contains(-3)) && act_sniper()) {
			rec |= 4;
			keys.remove(-1, 2);
		}
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 5; j++) {
				boolean b0 = keys.pressed(i, j);
				boolean b1 = action.contains(i * 5 + j);
				if (keys.pressed(-2, 0) || action.contains(10))
					if (b0) {
						act_lock(i, j);
						rec |= 1 << (i * 5 + j + 3);
						keys.remove(i, j);
					} else if (b1) {
						act_lock(i, j);
						rec |= 1 << (i * 5 + j + 3);
						action.remove((Object) (i * 5 + j));
					}
				if (act_spawn(i, j, b0 || b1) && (b0 || b1))
					rec |= 1 << (i * 5 + j + 13);
			}
		action.clear();
		if (rec == num)
			rep++;
		else {
			if (rep > 0) {
				recd.add(num);
				recd.add(rep);
			}
			num = rec;
			rep = 1;
		}
	}

}
