package util.entity;

import util.BattleObj;
import util.entity.attack.AttackAb;

public abstract class AbEntity extends BattleObj {

	public long health, maxH;
	public int dire;
	public double pos;

	protected AbEntity(int h) {
		if (h <= 0)
			h = 1;
		health = maxH = h;
	}

	public void added(int d, int p) {
		pos = p;
		dire = d;
	}

	public abstract void damaged(AttackAb atk);

	public abstract int getAbi();

	public abstract boolean isBase();

	public abstract void postUpdate();

	public abstract boolean targetable(int type);

	public abstract int touchable();

	public abstract void update();

}
