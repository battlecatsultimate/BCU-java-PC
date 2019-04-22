package util.entity.data;

import java.util.Set;
import java.util.TreeSet;

import util.basis.Basis;
import util.unit.AbEnemy;
import util.unit.Enemy;

public interface MaskEnemy extends MaskEntity {

	public double getDrop();

	@Override
	public Enemy getPack();

	public int getStar();

	public default Set<AbEnemy> getSummon() {
		return new TreeSet<AbEnemy>();
	}

	public double multi(Basis b);

}
