package util.stage;

import util.system.Copable;

public class SCGroup implements Copable<SCGroup>{
	
	public int max;
	
	public SCGroup(int n) {
		max=n;
	}

	@Override
	public SCGroup copy() {
		return new SCGroup(max);
	}

}
