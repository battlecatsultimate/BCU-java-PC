package util;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class EAnimU
{
	protected EPart[] ent = null;
	protected List<EPart> order;
	protected final AnimU a;

	private int f = -1;
	private MaAnim ma;
	private final MaModel mamodel;

	protected EAnimU(AnimU ani, int i)
	{
		a = ani;
		mamodel = ani.mamodel;
		ent = mamodel.arrange(this);
		order = new ArrayList<>();
		for (EPart e : ent)
		{
			e.ea = this;
			order.add(e);
		}
		order.sort(null);
		ma = ani.anims[i];
	}

	public void draw(Graphics2D g, P ori, double siz)
	{
		if (f == -1)
		{
			ma.update(f = 0, this, false);
		}
		
		g.translate(ori.x, ori.y);
		
		for (EPart e : order)
		{
			e.drawPart(g, new P(siz, siz));
		}
	}

	public void update(boolean rotate)
	{
		ma.update(++f, this, rotate);
	}
}
