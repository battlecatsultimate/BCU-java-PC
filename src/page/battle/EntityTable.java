package page.battle;

import page.MainLocale;
import page.support.EnemyTCR;
import page.support.SortTable;
import page.support.UnitTCR;
import util.entity.Entity;
import util.entity.data.MaskEnemy;
import util.entity.data.MaskUnit;
import util.unit.Enemy;
import util.unit.Form;

class EntityTable extends SortTable<Entity> {

	private static final long serialVersionUID = 1L;

	private static String[] title;

	static {
		redefine();
	}

	protected static void redefine() {
		title = MainLocale.getLoc(1, "u", 3);
	}

	private int dir;

	protected EntityTable(int dire) {
		dir = dire;
		if (dire == 1)
			setDefaultRenderer(Enemy.class, new EnemyTCR());
		else
			setDefaultRenderer(Form.class, new UnitTCR(lnk));
		sign = -1;
	}

	@Override
	public Class<?> getColumnClass(int c) {
		if (lnk[c] == 1)
			return dir == 1 ? Enemy.class : Form.class;
		else
			return Object.class;
	}

	@Override
	protected int compare(Entity e0, Entity e1, int c) {
		if (c == 1)
			return Integer.compare(getID(e0), getID(e1));
		else
			return Long.compare((long) get(e0, c), (long) get(e1, c));
	}

	@Override
	protected Object get(Entity t, int c) {
		if (c == 0)
			return t.health;
		else if (c == 1)
			return t.data.getPack();
		else if (c == 2)
			return (long) t.getAtk();
		else
			return null;
	}

	@Override
	protected String[] getTit() {
		return title;
	}

	private int getID(Entity e) {
		if (e.data instanceof MaskUnit)
			return ((MaskUnit) e.data).getPack().uid;
		if (e.data instanceof MaskEnemy)
			return ((MaskEnemy) e.data).getPack().id;
		return 0;
	}

}