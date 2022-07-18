package page.battle;

import common.CommonStatic;
import common.battle.data.MaskEnemy;
import common.battle.data.MaskUnit;
import common.battle.entity.Entity;
import common.pack.Identifier;
import common.util.unit.Enemy;
import common.util.unit.Form;
import page.MainLocale;
import page.support.EnemyTCR;
import page.support.SortTable;
import page.support.UnitTCR;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.text.DecimalFormat;

class EntityTable extends SortTable<Entity> {

	private static final long serialVersionUID = 1L;
	private static final DecimalFormat df = new DecimalFormat("#.##");

	private boolean dps = true;

	private String[] title;

	private final int dir;

	protected EntityTable(int dire) {
		dir = dire;

		if (dire == 1)
			setDefaultRenderer(Enemy.class, new EnemyTCR());
		else
			setDefaultRenderer(Form.class, new UnitTCR(lnk));
		sign = -1;
	}

	public void setDPS(boolean dps) {
		this.dps = dps;

		redefine(true);
	}

	public boolean getDPS() {
		return dps;
	}

	private void redefine(boolean update) {
		title = MainLocale.getLoc(MainLocale.INFO, "u", 4);

		if(update && !dps)
			title[3] = MainLocale.getLoc(MainLocale.INFO, "u3_1");

		if(update) {
			JTableHeader header = getTableHeader();
			TableColumnModel model = header.getColumnModel();

			for(int i = 0; i < model.getColumnCount(); i++) {
				model.getColumn(i).setHeaderValue(title[i]);
			}
		}
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
		if (c == 0)
			return Long.compare(CommonStatic.parseLongN(get(e0,c).toString()),CommonStatic.parseLongN(get(e1,c).toString()));
		if (c == 1)
			return getID(e0).compareTo(getID(e1));
		if (c == 3)
			return Double.compare((double) get(e0, c), (double) get(e1, c));
		else
			return Long.compare((long) get(e0, c), (long) get(e1, c));
	}

	@Override
	protected Object get(Entity t, int c) {
		if (c == 0)
			return t.currentShield > 0 ? t.health + " (+" + t.currentShield + ")" : t.health;
		else if (c == 1)
			return t.data.getPack();
		else if (c == 2)
			return (long) t.getAtk();
		else if (c == 3)
			if(dps)
				return t.livingTime == 0 ? 0.0 : Double.parseDouble(df.format(t.damageGiven * 30.0 / t.livingTime));
			else
				return (double) t.damageGiven;
		else
			return null;
	}

	@Override
	protected String[] getTit() {
		if(title == null)
			redefine(false);

		return title;
	}

	private Identifier<?> getID(Entity e) {
		if (e.data instanceof MaskUnit)
			return ((MaskUnit) e.data).getPack().uid;
		if (e.data instanceof MaskEnemy)
			return ((MaskEnemy) e.data).getPack().id;
		return null;
	}

}