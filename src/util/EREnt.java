package util;

import util.system.Copable;

public class EREnt<X> implements BattleStatic, Copable<EREnt<X>> {

	public X ent;
	public int multi = 100;
	public int share = 1;

	@Override
	public EREnt<X> copy() {
		EREnt<X> ans = new EREnt<X>();
		ans.ent = ent;
		ans.multi = multi;
		ans.share = share;
		return ans;
	}

}