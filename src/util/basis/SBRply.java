package util.basis;

import java.util.ArrayDeque;
import java.util.Queue;

import io.InStream;
import util.stage.EStage;

public class SBRply extends BattleField {

	private final Queue<Integer> recd = new ArrayDeque<>();

	private int rec, rep;

	public SBRply(InStream in, EStage stage, BasisLU bas, int[] ints, long seed) {
		super(stage, bas, ints, seed);
		int n = in.nextInt();
		for (int i = 0; i < n; i++)
			recd.add(in.nextInt());
	}

	/** process the user action */
	@Override
	protected void actions() {
		if (rep == 0)
			if (recd.isEmpty())
				rec = 0;
			else {
				rec = recd.poll();
				rep = recd.poll();
			}
		rep--;
		if ((rec & 1) > 0)
			act_mon();
		if ((rec & 2) > 0)
			act_can();
		if ((rec & 4) > 0)
			act_sniper();
		for (int i = 0; i < 2; i++)
			for (int j = 0; j < 5; j++) {
				if ((rec & (1 << (i * 5 + j + 3))) > 0)
					act_lock(i, j);
				act_spawn(i, j, (rec & (1 << (i * 5 + j + 13))) > 0);
			}
	}

}
