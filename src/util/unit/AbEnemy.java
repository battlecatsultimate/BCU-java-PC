package util.unit;

import java.util.Set;

import util.basis.StageBasis;
import util.entity.EEnemy;
import util.system.VImg;

public interface AbEnemy extends Comparable<AbEnemy> {

	@Override
	public default int compareTo(AbEnemy e) {
		return Integer.compare(getID(), e.getID());

	}

	public EEnemy getEntity(StageBasis sb, Object obj, double mul, int d0, int d1, int m);

	public VImg getIcon();

	public int getID();

	public Set<Enemy> getPossible();

}
