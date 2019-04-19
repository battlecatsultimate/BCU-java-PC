package util.entity.attack;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import page.battle.BattleBox;
import util.ImgCore;
import util.anim.EAnimD;
import util.entity.AbEntity;
import util.entity.Entity;
import util.system.P;

public abstract class ContWaveAb extends ContAb {

	protected final AttackWave atk;
	protected final EAnimD anim;
	private int t = 0;
	private int maxt;
	private boolean tempAtk;

	protected ContWaveAb(AttackWave a, double p, EAnimD ead) {
		super(a.model.b, p);
		atk = a;
		anim = ead;
		maxt = anim.len();
	}

	@Override
	public void draw(Graphics2D gra, P p, double siz) {
		AffineTransform at = gra.getTransform();
		anim.draw(gra, p, siz);
		gra.setTransform(at);
		drawAxis(gra, p, siz);
	}

	@Override
	public void update() {
		tempAtk = false;
		if (t == W_TIME) {
			atk.capture();
			for (AbEntity e : atk.capt)
				if ((e.getAbi() & AB_WAVES) > 0) {
					if (e instanceof Entity)
						((Entity) e).getEff(STPWAVE);
					activate = false;
					return;
				}
			sb.getAttack(atk);
			tempAtk = true;
			if (atk.getProc(P_WAVE)[0] > 0)
				nextWave();
		}
		if (maxt == t)
			activate = false;
		anim.update(false);
		t++;
	}

	protected void drawAxis(Graphics2D gra, P p, double siz) {
		if (!ImgCore.ref)
			return;

		// after this is the drawing of hit boxes
		siz *= 1.25;
		double rat = BattleBox.ratio;
		int h = (int) (640 * rat * siz);
		gra.setColor(Color.MAGENTA);
		double d0 = Math.min(atk.sta, atk.end);
		double ra = Math.abs(atk.sta - atk.end);
		int x = (int) ((d0 - pos) * rat * siz + p.x);
		int y = (int) p.y;
		int w = (int) (ra * rat * siz);
		if (tempAtk)
			gra.fillRect(x, y, w, h);
		else
			gra.drawRect(x, y, w, h);
	}

	/** generate the next wave container */
	protected abstract void nextWave();

}
