package util.entity;

import util.Copible;

public class EntCont extends Copible {

	public Entity ent;

	public int t;

	public EntCont(Entity e, int time) {
		ent = e;
		t = time;
	}

	public void update() {
		if (t > 0)
			t--;
	}

}
