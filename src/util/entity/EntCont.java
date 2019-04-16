package util.entity;

public class EntCont {

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
