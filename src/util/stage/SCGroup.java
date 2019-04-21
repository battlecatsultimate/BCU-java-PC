package util.stage;

import util.system.Copable;

public class SCGroup implements Copable<SCGroup>{
	
	public int max;
	
	@Override
	public SCGroup copy() {
		SCGroup ans=new SCGroup();
		ans.max=max;
		return ans;
	}

}
