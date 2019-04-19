package util.stage;

import util.basis.StageBasis;
import util.entity.EEnemy;
import util.unit.AbEnemy;
import util.unit.EnemyStore;

public class EStage {

	public final Stage s;
	public final Limit lim;
	public final int[] num, rem;
	public final double mul;

	public StageBasis b;

	public EStage(Stage st) {
		s = st;
		st.validate();
		rem = new int[s.datas.length];
		num = new int[s.datas.length];
		for (int i = 0; i < rem.length; i++) {
			rem[i] = st.datas[i][2];
			num[i] = st.datas[i][1];
		}
		lim = null;
		mul = 1;
	}

	public EStage(Stage st, int star) {
		s = st;
		st.validate();
		rem = new int[s.datas.length];
		num = new int[s.datas.length];
		for (int i = 0; i < rem.length; i++) {
			rem[i] = st.datas[i][2];
			num[i] = st.datas[i][1];
		}
		lim = st.getLim(star);
		mul = st.map.stars[star] * 0.01;
	}

	/** add n new enemies to StageBasis */
	public EEnemy allow() {
		for (int i = 0; i < rem.length; i++) {
			boolean in;
			int[] data = s.datas[i];
			if (s.trail)
				in = b.ebase.maxH - b.ebase.health >= data[5];
			else
				in = b.getEBHP() * 100 <= data[5];
			if (in && rem[i] == 0 && num[i] != -1) {
				rem[i] = data[3] + (int) (b.r.nextDouble() * (data[4] - data[3]));
				if (num[i] > 0) {
					num[i]--;
					if (num[i] == 0)
						num[i] = -1;
				}
				if (data[8] == 1)
					b.shock = true;

				AbEnemy e = EnemyStore.getAbEnemy(data[0], false);
				double multi = (data[9] == 0 ? 100 : data[9]) * mul * 0.01;
				return e.getEntity(b, data, multi, data[6], data[7], data[8]);
			}
		}
		return null;
	}

	/** get the Entity representing enemy base, return null if none */
	public EEnemy base(StageBasis sb) {
		int ind = num.length - 1;
		if (ind >= 0 && s.datas[ind][5] == 0) {
			num[ind] = -1;
			int[] data = s.datas[ind];
			double multi = data[9] * mul * 0.01;
			AbEnemy e = EnemyStore.getAbEnemy(data[0], false);
			return e.getEntity(sb, this, multi, data[6], data[7], -1);
		}
		return null;
	}

	/** return true if there is still boss in the base */
	public boolean hasBoss() {
		for (int i = 0; i < rem.length; i++) {
			int[] data = s.datas[i];
			if (data[8] == 1 && num[i] > 0)
				return true;
		}
		return false;
	}

	public void update() {
		for (int i = 0; i < rem.length; i++) {
			boolean in;
			int[] data = s.datas[i];
			if (s.trail)
				in = b.ebase.maxH - b.ebase.health >= data[5];
			else
				in = b.getEBHP() * 100 <= data[5];
			if (in && rem[i] < 0)
				rem[i] *= -1;
			if (rem[i] > 0)
				rem[i]--;
		}
	}

}
