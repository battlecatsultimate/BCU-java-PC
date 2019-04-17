package util.unit;

import util.basis.StageBasis;
import util.entity.EEnemy;
import util.system.VImg;

public interface AbEnemy {

	public EEnemy getEntity(StageBasis sb, Object obj, double mul, int d0, int d1, int m);

	public VImg getIcon();

	public int getID();

}
