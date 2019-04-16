package util.entity.data;

import util.basis.Basis;
import util.unit.Enemy;

public interface MaskEnemy extends MaskEntity {

	public double getDrop();

	@Override
	public Enemy getPack();

	public int getStar();

	public double multi(Basis b);

}
