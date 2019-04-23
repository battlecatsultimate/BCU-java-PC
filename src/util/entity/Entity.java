package util.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import page.battle.BattleBox;
import util.Data;
import util.ImgCore;
import util.anim.AnimD;
import util.anim.EAnimD;
import util.anim.EAnimU;
import util.basis.StageBasis;
import util.entity.attack.AtkModelEntity;
import util.entity.attack.AttackAb;
import util.entity.data.MaskAtk;
import util.entity.data.MaskEntity;
import util.pack.EffAnim;
import util.pack.Soul;
import util.system.P;

/** Entity class for units and enemies */
public abstract class Entity extends AbEntity {

	/** entity data, read only */
	public final MaskEntity data;

	/** game engine, contains environment configuration */
	public final StageBasis basis;

	/** proc status, contains ability-specific status data */
	public final int[][] status;

	/** layer of display, constant field */
	public int layer;

	/** trait of enemy, also target trait of unit, use bitmask */
	public int type;

	/** group, used for search */
	public int group;

	/**
	 * dead FSM time <br>
	 * -1 means not dead<br>
	 * positive value means time remain for death anim to play
	 */
	public int dead = -1;

	/** soul anim, null means not dead yet */
	private EAnimD soul;

	/** KB anim, null means not being KBed, can have various value during battle */
	private EAnimD back;

	/** corpse anim */
	private EAnimD corpse;

	/**
	 * on-entity effect icons<br>
	 * index defined by Data.A_()
	 */
	protected final EAnimD[] effs = new EAnimD[A_TOT];

	/** attack model */
	protected final AtkModelEntity aam;

	/** entity anim */
	public final EAnimU anim;

	/** const field */
	protected boolean isBase;

	/**
	 * temp field within an update loop <br>
	 * used for moving determination
	 */
	private boolean touch;

	/** acted: temp field, for update sync */
	private boolean acted;

	/**
	 * KB FSM time, values: <br>
	 * 0: not KB <br>
	 * -1: dead <br>
	 * positive: KB time count-down <br>
	 * negative: burrow FSM type
	 */
	public int kbTime;

	/** atk FSM time */
	private int atkTime;

	/** wait FSM time */
	private int waitTime;

	/** pre-atk time const field */
	private final int[] pres;

	/** const field, attack count */
	private final int multi;

	/** weak proc processor */
	private final List<int[]> weaks = new ArrayList<>();

	/** barrier value, 0 means no barrier or broken */
	private int barrier;

	/** atk loop FSM type */
	private int preID;

	/** atk loop FSM time */
	private int preTime;

	/** temp field: damage accumulation */
	private long damage;

	/** responsive effect FSM time */
	private int efft;

	/** responsive effect FSM type */
	private int eftp;

	/** attack times remain */
	private int loop;

	/** KB FSM type */
	public int kbType;

	/** temp field to store wanted KB type */
	private int tempKBtype = -1;

	/** temp field to store wanted KB length */
	private double tempKBdist;

	/** atk id primarily for display */
	private int tempAtk = -1;

	/** remaining distance to KB */
	private double kbDis;

	/** remaining burrow distance */
	private double bdist;

	/** temp field: whether it can attack */
	private boolean touchEnemy;

	/** temp field: marker for double income */
	protected boolean tempearn;

	/** temp field: marker for zombie killer */
	private boolean tempZK;

	protected Entity(StageBasis b, MaskEntity de, EAnimU ea, double d0, double d1) {
		super(d1 < 0 ? b.st.health : (int) (de.getHp() * d1));
		basis = b;
		data = de;
		aam = AtkModelEntity.getIns(this, d0);
		anim = ea;
		int[][] raw = de.rawAtkData();
		pres = new int[multi = raw.length];
		for (int i = 0; i < multi; i++)
			pres[i] = raw[i][1];
		loop = de.getAtkLoop();
		barrier = de.getShield();
		status = new int[PROC_TOT][PROC_WIDTH];
		status[P_BORROW][0] = getProc(P_BORROW, 0);
		status[P_REVIVE][0] = getProc(P_REVIVE, 0);
	}

	/** accept attack */
	@Override
	public void damaged(AttackAb atk) {
		int dmg = getDamage(atk, atk.atk);
		// if immune to wave and the attack is wave, jump out
		if (atk.waveType == WT_WAVE) {
			if (getProc(P_IMUWAVE, 0) > 0)
				getEff(P_WAVE);
			if (getProc(P_IMUWAVE, 0) == 100)
				return;
			else
				dmg = dmg * (100 - getProc(P_IMUWAVE, 0)) / 100;
		}

		if (atk.waveType == WT_MOVE)
			if ((getAbi() & AB_MOVEI) > 0) {
				getEff(P_WAVE);
				return;
			}

		if (barrier > 0) {
			if (atk.getProc(P_BREAK)[0] > 0) {
				barrier = 0;
				getEff(BREAK_ABI);
			} else if (getDamage(atk, atk.atk) >= barrier) {
				barrier = 0;
				getEff(BREAK_ATK);
				return;
			} else {
				getEff(BREAK_NON);
				return;
			}
		}

		damage += dmg;
		tempZK |= (atk.abi & AB_ZKILL) > 0;
		tempearn |= (atk.abi & AB_EARN) > 0;
		if (atk.getProc(P_CRIT)[0] > 0)
			basis.lea.add(new EAnimCont(pos, EffAnim.effas[A_CRIT].getEAnim(0)));

		// process proc part
		if (atk.type != -1 && !receive(atk.type))
			return;
		double f = getFruit();
		double time = 1 + f * 0.2 / 3;
		double dist = 1 + f * 0.1;
		if (atk.type < 0 || atk.canon != -2)
			dist = time = 1;
		if (atk.getProc(P_STOP)[0] > 0) {
			int val = (int) (atk.getProc(P_STOP)[0] * time);
			int rst = getProc(P_IMUSTOP, 0);
			val = val * (100 - rst) / 100;
			status[P_STOP][0] = Math.max(status[P_STOP][0], val);
			if (rst < 100)
				getEff(P_STOP);
			else
				getEff(INV);
		}
		if (atk.getProc(P_SLOW)[0] > 0) {
			int val = (int) (atk.getProc(P_SLOW)[0] * time);
			int rst = getProc(P_IMUSLOW, 0);
			val = val * (100 - rst) / 100;
			status[P_SLOW][0] = Math.max(status[P_SLOW][0], val);
			if (rst < 100)
				getEff(P_SLOW);
			else
				getEff(INV);
		}
		if (atk.getProc(P_WEAK)[0] > 0) {
			int val = (int) (atk.getProc(P_WEAK)[0] * time);
			int rst = getProc(P_IMUWEAK, 0);
			val = val * (100 - rst) / 100;
			if (rst < 100) {
				weaks.add(new int[] { val, atk.getProc(P_WEAK)[1] });
				updateProc();
				getEff(P_WEAK);
			} else
				getEff(INV);
		}
		if (atk.getProc(P_CURSE)[0] > 0) {
			int val = atk.getProc(P_CURSE)[0];
			int rst = getProc(P_IMUCURSE, 0);
			val = val * (100 - rst) / 100;
			status[P_CURSE][0] = Math.max(status[P_CURSE][0], val);
			if (rst < 100)
				getEff(P_CURSE);
			else
				getEff(INV);
		}

		if (atk.getProc(P_KB)[0] != 0) {
			int rst = getProc(P_IMUKB, 0);
			if (rst < 100) {
				status[P_KB][0] = atk.getProc(P_KB)[1];
				interrupt(P_KB, atk.getProc(P_KB)[0] * dist * (100 - rst) / 100);
			} else
				getEff(INV);
		}
		if (atk.getProc(P_SNIPER)[0] > 0) {
			if ((getAbi() & AB_SNIPERI) == 0)
				interrupt(INT_ASS, KB_DIS[INT_ASS]);
			else
				getEff(INV);
		}
		if (atk.getProc(P_WARP)[0] > 0)
			if (getProc(P_IMUWARP, 0) < 100) {
				interrupt(INT_WARP, atk.getProc(P_WARP)[1]);
				EffAnim e = EffAnim.effas[A_W];
				int len = e.len(0) + e.len(1);
				int val = atk.getProc(P_WARP)[0];
				int rst = getProc(P_IMUWARP, 0);
				val = val * (100 - rst) / 100;
				status[P_WARP][0] = val + len;
			} else
				getEff(INVWARP);

		if (atk.getProc(P_SEAL)[0] > 0)
			if ((getAbi() & AB_SEALI) == 0) {
				status[P_SEAL][0] = atk.getProc(P_SEAL)[0];
				getEff(P_SEAL);
			} else
				getEff(INV);

		if (atk.getProc(P_POISON)[0] > 0)
			if ((getAbi() & AB_POII) == 0) {
				status[P_POISON][0] = atk.getProc(P_POISON)[0];
				status[P_POISON][1] = getDamage(atk, atk.getProc(P_POISON)[1]);
				getEff(P_POISON);
			} else
				getEff(INV);
	}

	/** draw this entity */
	public void draw(Graphics2D gra, P p, double siz) {
		if (dead > 0) {
			soul.draw(gra, p, siz);
			return;
		}
		AffineTransform at = gra.getTransform();
		if (corpse != null)
			corpse.draw(gra, p, siz);
		if (corpse == null || status[P_REVIVE][1] < Data.REVIVE_SHOW_TIME) {
			if (corpse != null) {
				gra.setTransform(at);
				anim.changeAnim(0);
			}
		} else
			return;

		anim.paraTo(back);
		if (kbTime == 0 || kbType != INT_WARP)
			anim.draw(gra, p, siz);
		anim.paraTo(null);
		gra.setTransform(at);
		if (!ImgCore.ref)
			return;

		// after this is the drawing of hit boxes
		siz *= 1.25;
		double rat = BattleBox.ratio;
		double poa = p.x - pos * rat * siz;
		int py = (int) p.y;
		int h = (int) (640 * rat * siz);
		gra.setColor(Color.RED);
		for (int i = 0; i < data.getAtkCount(); i++) {
			double[] ds = aam.inRange(i);
			double d0 = Math.min(ds[0], ds[1]);
			double ra = Math.abs(ds[0] - ds[1]);
			int x = (int) (d0 * rat * siz + poa);
			int y = (int) (p.y + 100 * i * rat * siz);
			int w = (int) (ra * rat * siz);
			if (tempAtk == i)
				gra.fillRect(x, y, w, h);
			else
				gra.drawRect(x, y, w, h);
		}
		gra.setColor(Color.YELLOW);
		int x = (int) ((pos + data.getRange() * dire) * rat * siz + poa);
		gra.drawLine(x, py, x, py + h);
		gra.setColor(Color.BLUE);
		int bx = (int) ((dire == -1 ? pos : pos - data.getWidth()) * rat * siz + poa);
		int bw = (int) (data.getWidth() * rat * siz);
		gra.drawRect(bx, (int) p.y, bw, h);
		gra.setColor(Color.CYAN);
		gra.drawLine((int) (pos * rat * siz + poa), py, (int) (pos * rat * siz + poa), py + h);
		tempAtk = -1;
	}

	/** draw the effect icons */
	public void drawEff(Graphics2D g, P p, double siz) {
		if (dead != -1)
			return;
		AffineTransform at = g.getTransform();
		int EWID = 48;
		double x = p.x;
		if (effs[eftp] != null) {
			effs[eftp].draw(g, p, siz);
		}
		for (EAnimD eae : effs) {
			if (eae == null)
				continue;
			g.setTransform(at);
			eae.draw(g, new P(x, p.y), siz);
			x -= EWID * dire * siz;
		}
	}

	/** get the current ability bitmask */
	@Override
	public int getAbi() {
		if (status[P_SEAL][0] > 0)
			return data.getAbi() & (AB_ONLY | AB_METALIC | AB_GLASS);
		return data.getAbi();
	}

	/** get the currently attack, only used in display */
	public int getAtk() {
		return aam.getAtk();
	}

	/** get a effect icon */
	public void getEff(int t) {
		if (t == INV) {
			effs[eftp] = null;
			eftp = A_EFF_INV;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == P_WAVE) {
			int id = dire == -1 ? A_WAVE_INVALID : A_E_WAVE_INVALID;
			effs[id] = EffAnim.effas[id].getEAnim(0);
			status[P_WAVE][0] = EffAnim.effas[id].len(0);
		}
		if (t == STPWAVE) {
			effs[eftp] = null;
			eftp = dire == -1 ? A_WAVE_STOP : A_E_WAVE_STOP;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == INVWARP) {
			effs[eftp] = null;
			eftp = dire == -1 ? A_FARATTACK : A_E_FARATTACK;
			effs[eftp] = EffAnim.effas[eftp].getEAnim(0);
			efft = EffAnim.effas[eftp].len(0);
		}
		if (t == P_STOP) {
			int id = dire == -1 ? A_STOP : A_E_STOP;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_SLOW) {
			int id = dire == -1 ? A_SLOW : A_E_SLOW;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_WEAK) {
			int id = dire == -1 ? A_DOWN : A_E_DOWN;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_CURSE) {
			int id = A_CURSE;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_POISON) {
			int id = A_POISON;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_SEAL) {
			int id = A_SEAL;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_STRONG) {
			int id = dire == -1 ? A_UP : A_E_UP;
			effs[id] = EffAnim.effas[id].getEAnim(0);
		}
		if (t == P_LETHAL) {
			int id = dire == -1 ? A_SHIELD : A_E_SHIELD;
			AnimD ea = EffAnim.effas[id];
			status[P_LETHAL][1] = ea.len(0);
			effs[id] = ea.getEAnim(0);
		}
		if (t == P_WARP) {
			AnimD ea = EffAnim.effas[A_W];
			int pa = status[P_WARP][2];
			basis.lea.add(new WaprCont(pos, pa, anim));
			status[P_WARP][pa] = ea.len(pa);

		}

		if (t == BREAK_ABI) {
			int id = dire == -1 ? A_U_E_B : A_E_B;
			effs[id] = EffAnim.effas[id].getEAnim(0);
			status[P_BREAK][0] = effs[id].len();
		}
		if (t == BREAK_ATK) {
			int id = dire == -1 ? A_U_E_B : A_E_B;
			effs[id] = EffAnim.effas[id].getEAnim(1);
			status[P_BREAK][0] = effs[id].len();
		}
		if (t == BREAK_NON) {
			int id = dire == -1 ? A_U_B : A_B;
			effs[id] = EffAnim.effas[id].getEAnim(4);
			status[P_BREAK][0] = effs[id].len();
		}
	}

	/** get the current proc array */
	public int getProc(int ind, int ety) {
		if (status[P_SEAL][0] > 0)
			return 0;
		return data.getProc(ind)[ety];
	}

	/** receive an interrupt */
	public void interrupt(int t, double d) {
		if (t == INT_SW && (getAbi() & AB_IMUSW) > 0)
			return;
		int prev = tempKBtype;
		if (prev == -1 || KB_PRI[t] >= KB_PRI[prev]) {
			tempKBtype = t;
			tempKBdist = d;
		}
	}

	@Override
	public boolean isBase() {
		return isBase;
	}

	/** mark it dead, proceed death animation */
	public void kill() {
		if (kbTime == -1)
			return;
		kbTime = -1;
		if (data.getDeathAnim() == -1)
			dead = 0;
		else
			dead = (soul = Soul.souls[data.getDeathAnim()].getEAnim(0)).len();
	}

	/** update the entity after receiving attacks */
	@Override
	public void postUpdate() {
		MaskAtk ma = data.getRepAtk();
		int hb = data.getHb();
		long ext = health * hb % maxH;
		if (ext == 0)
			ext = maxH;
		if (!isBase && damage > 0 && kbTime <= 0 && kbTime != -1 && (ext <= damage * hb || health < damage))
			interrupt(INT_HB, KB_DIS[INT_HB]);
		health -= damage;
		if (health > maxH)
			health = maxH;
		damage = 0;

		// increase damage
		int strong = ma.getProc(P_STRONG)[0];
		if (status[P_STRONG][0] == 0 && strong > 0 && health * 100 <= maxH * strong) {
			status[P_STRONG][0] = ma.getProc(P_STRONG)[1];
			getEff(P_STRONG);
		}
		// lethal strike
		if (ma.getProc(P_LETHAL)[0] > 0 && health <= 0) {
			boolean b = basis.r.nextDouble() * 100 < ma.getProc(P_LETHAL)[0];
			if (status[P_LETHAL][0] == 0 && b) {
				health = 1;
				getEff(P_LETHAL);
			}
			status[P_LETHAL][0]++;
		}

		doInterrupt();

		if ((getAbi() & AB_GLASS) > 0 && atkTime == 0 && kbTime == 0 && loop == 0)
			dead = 0;
		checkEff();

		// update animations
		if (status[P_STOP][0] == 0 && (kbTime == 0 || kbType != INT_SW))
			anim.update(false);
		if (back != null)
			back.update(false);
		for (int i = 0; i < effs.length; i++)
			if (effs[i] != null) {
				effs[i].update(false);
			}
		if (dead > 0) {
			soul.update(false);
			dead--;
		}
		if (health > 0) {
			tempearn = false;
			tempZK = false;
		}
		acted = false;
	}

	/** can be targeted by the cat with Targer ability of trait t */
	@Override
	public boolean targetable(int t) {
		return (type & t) > 0 || isBase;
	}

	/** get touch mode bitmask */
	@Override
	public int touchable() {
		int n = (getAbi() & AB_GHOST) > 0 ? TCH_EX : TCH_N;
		if (dead > 0)
			return TCH_SOUL;
		if (status[P_REVIVE][1] > 0)
			return TCH_CORPSE;
		if (status[P_BORROW][2] > 0)
			return n | TCH_UG;
		if (kbTime < -1)
			return TCH_UG;
		return kbTime == 0 ? n : TCH_KB;
	}

	/**
	 * update the entity. order of update: <br>
	 * KB -> revive -> move -> burrow -> attack -> wait
	 */
	@Override
	public void update() {
		// if this entity is in kb state, do kbmove()
		// eneity can move right after kbmove, no need to mark acted
		if (kbTime > 0)
			updateKB();

		// update revive status, mark acted
		updateRevive();

		// do move check if available, move if possible
		if (kbTime == 0 && !acted && atkTime == 0)
			updateTouch();

		boolean nstop = status[P_STOP][0] == 0;

		// update burrow state if not stopped
		if (nstop)
			updateBurrow();

		// update wait and attack state
		if (kbTime == 0) {
			boolean binatk = waitTime + kbTime + atkTime == 0;
			binatk = binatk && touchEnemy && loop != 0 && nstop;

			// if it can attack, setup attack state
			if (!acted && binatk) {
				atkTime = data.getAnimLen();
				loop--;
				preID = 0;
				preTime = pres[0];
				anim.changeAnim(2);
			}

			// update waiting state
			if ((waitTime > 0 || !touchEnemy) && touch && atkTime == 0 && anim.type != 1)
				anim.changeAnim(1);
			if (waitTime > 0)
				waitTime--;

			// update attack status when in attack state
			if (status[P_STOP][0] == 0 && atkTime > 0)
				updateAttack();
		}

		// update proc effects
		updateProc();
	}

	/** update effect icons animation */
	protected void checkEff() {
		if (efft == 0)
			effs[eftp] = null;
		if (status[P_STOP][0] == 0) {
			int id = dire == -1 ? A_STOP : A_E_STOP;
			effs[id] = null;
		}
		if (status[P_SLOW][0] == 0) {
			int id = dire == -1 ? A_SLOW : A_E_SLOW;
			effs[id] = null;
		}
		if (status[P_WEAK][0] == 0) {
			int id = dire == -1 ? A_DOWN : A_E_DOWN;
			effs[id] = null;
		}
		if (status[P_CURSE][0] == 0) {
			int id = A_CURSE;
			effs[id] = null;
		}
		if (status[P_POISON][0] == 0) {
			int id = A_POISON;
			effs[id] = null;
		}
		if (status[P_SEAL][0] == 0) {
			int id = A_SEAL;
			effs[id] = null;
		}
		if (status[P_LETHAL][1] == 0) {
			int id = dire == -1 ? A_SHIELD : A_E_SHIELD;
			effs[id] = null;
		} else
			status[P_LETHAL][1]--;
		if (status[P_WAVE][0] == 0) {
			int id = dire == -1 ? A_WAVE_INVALID : A_E_WAVE_INVALID;
			effs[id] = null;
		} else
			status[P_WAVE][0]--;
		if (status[P_STRONG][0] == 0) {
			int id = dire == -1 ? A_UP : A_E_UP;
			effs[id] = null;
		}
		if (status[P_BREAK][0] == 0) {
			int id = dire == -1 ? A_U_B : A_B;
			effs[id] = null;
		} else
			status[P_BREAK][0]--;
		efft--;

	}

	/** interrupt whatever this entity is doing */
	protected void clearState() {
		if (atkTime > 0) {
			atkTime = 0;
			preTime = 0;
			if (preID == multi)
				waitTime = data.getTBA();
		}
		if (kbTime < -1 || status[P_BORROW][2] > 0) {
			status[P_BORROW][2] = 0;
			bdist = 0;
			kbTime = 0;
		}
		if (status[P_REVIVE][1] > 0) {
			status[P_REVIVE][1] = 0;
			corpse = null;
		}
	}

	/** determine the amount of damage received from this attack */
	protected abstract int getDamage(AttackAb atk, int ans);

	/** get the extra proc time due to fruits, for EEnemy only */
	protected double getFruit() {
		return 0;
	}

	/** get max distance to go back */
	protected abstract double getLim();

	/** can be effected by the ability targeting trait t */
	protected boolean receive(int t) {
		return true;
	}

	/**
	 * move forward <br>
	 * maxl: max distance to move <br>
	 * extmov: distance try to add to this movement
	 */
	protected boolean updateMove(double maxl, double extmov) {
		acted = true;
		double bpos = (basis.getBase(dire).pos - pos) * dire - data.touchBase();
		if (maxl >= 0)
			bpos = Math.min(bpos, maxl);

		double mov = isBase ? 0 : status[P_SLOW][0] > 0 ? 0.5 : data.getSpeed() * 0.5;
		mov += extmov;
		pos += Math.min(mov, bpos) * dire;
		return bpos > mov;
	}

	/** process the interruption received */
	private void doInterrupt() {
		int t = tempKBtype;
		if (t == -1)
			return;
		double d = tempKBdist;
		tempKBtype = -1;
		clearState();
		kbType = t;
		kbTime = KB_TIME[t];
		kbDis = d;
		if (t != INT_SW && t != INT_WARP)
			anim.changeAnim(3);
		else {
			anim.changeAnim(0);
			anim.update(false);
		}
		if (t == INT_WARP) {
			kbTime = status[P_WARP][0];
			getEff(P_WARP);
			status[P_WARP][2] = 1;
		}
		if (t == INT_KB)
			kbTime = status[P_KB][0];
		if (t == INT_HB)
			back = EffAnim.effas[A_KB].getEAnim(0);
		if (t == INT_SW)
			back = EffAnim.effas[A_KB].getEAnim(1);
		if (t == INT_ASS)
			back = EffAnim.effas[A_KB].getEAnim(2);

		// Z-kill icon
		if (health <= 0 && tempZK && status[P_REVIVE][0] > 0) {
			EAnimD eae = EffAnim.effas[A_Z_STRONG].getEAnim(0);
			basis.lea.add(new EAnimCont(pos, eae));
		}
	}

	/** update attack state */
	private void updateAttack() {
		atkTime--;
		if (preTime >= 0) {
			if (preTime == 0) {
				int atk0 = preID;
				while (++preID < multi && pres[preID] == 0)
					;
				tempAtk = (int) (atk0 + basis.r.nextDouble() * (preID - atk0));
				basis.getAttack(aam.getAttack(tempAtk));
				if (preID < multi)
					preTime = pres[preID];
			}
			preTime--;
		}
		if (atkTime == 0) {
			waitTime = data.getTBA();
			anim.changeAnim(1);
		}
	}

	/** update burrow state */
	private void updateBurrow() {

		if (kbTime == 0 && touch && status[P_BORROW][0] != 0) {
			double[] ds = aam.touchRange();
			if (!basis.inRange(data.getTouch(), dire, ds[0], ds[1]).contains(basis.ubase)) {
				// setup burrow state
				status[P_BORROW][0]--;
				anim.changeAnim(4);
				status[P_BORROW][2] = anim.len();
				kbTime = -2;
			}
		}
		if (kbTime == -2) {
			acted = true;
			// burrow down
			status[P_BORROW][2]--;
			if (status[P_BORROW][2] == 0) {
				kbTime = -3;
				anim.changeAnim(5);
				bdist = data.getRepAtk().getProc(P_BORROW)[1];
			}
		}
		if (!acted && kbTime == -3) {
			// move underground
			double oripos = pos;
			boolean b = updateMove(bdist, 0);
			bdist -= (pos - oripos) * dire;
			if (!b) {
				bdist = 0;
				kbTime = -4;
				anim.changeAnim(6);
				status[P_BORROW][2] = anim.len();
			}
		}
		if (!acted && kbTime == -4) {
			// burrow up
			acted = true;
			status[P_BORROW][2]--;
			if (status[P_BORROW][2] == 0)
				kbTime = 0;
		}

	}

	/**
	 * update KB state <br>
	 * in KB state: deal with warp, KB go back, and anim change <br>
	 * end of KB: check whether it's killed, deal with revive
	 */
	private void updateKB() {
		if (kbType != INT_WARP) {
			double mov = kbDis / kbTime;
			kbDis -= mov;
			if (mov < 0)
				updateMove(-mov, -mov);
			else {
				double lim = getLim();
				if (mov < lim)
					pos -= mov * dire;
				else
					pos -= lim * dire;
			}
		} else {
			anim.changeAnim(0);
			if (status[P_WARP][0] > 0)
				status[P_WARP][0]--;
			if (status[P_WARP][1] > 0)
				status[P_WARP][1]--;
			EffAnim ea = EffAnim.effas[A_W];
			if (kbTime == ea.len(1)) {
				double mov = kbDis;
				if (mov < 0)
					updateMove(-mov, -mov);
				else {
					double lim = getLim();
					if (mov < lim)
						pos -= mov * dire;
					else
						pos -= lim * dire;
				}
				kbDis = 0;
				getEff(P_WARP);
				status[P_WARP][2] = 0;
			}
		}
		kbTime--;
		if (kbTime == 0) {
			tempKBtype = -1;
			back = null;
			anim.changeAnim(0);

			if (health <= 0)
				if (!tempZK && status[P_REVIVE][0] != 0) {
					// revive
					waitTime = 0;
					status[P_REVIVE][0]--;
					int deadAnim = data.getRepAtk().getProc(P_REVIVE)[1];
					EffAnim ea = EffAnim.effas[A_ZOMBIE];
					deadAnim += ea.getEAnim(0).len();
					status[P_REVIVE][1] = deadAnim;
					health = maxH * data.getRepAtk().getProc(P_REVIVE)[2] / 100;

					// clear state
					status[P_STOP] = new int[PROC_WIDTH];
					status[P_SLOW] = new int[PROC_WIDTH];
					status[P_WEAK] = new int[PROC_WIDTH];
					status[P_CURSE] = new int[PROC_WIDTH];
					status[P_SEAL] = new int[PROC_WIDTH];
					status[P_STRONG] = new int[PROC_WIDTH];
					status[P_LETHAL] = new int[PROC_WIDTH];
				} else
					kill();
		}
	}

	/** update proc status */
	private void updateProc() {
		if (status[P_STOP][0] > 0)
			status[P_STOP][0]--;
		if (status[P_SLOW][0] > 0)
			status[P_SLOW][0]--;
		if (status[P_CURSE][0] > 0)
			status[P_CURSE][0]--;
		if (status[P_SEAL][0] > 0)
			status[P_SEAL][0]--;
		if (status[P_POISON][0] > 0) {
			status[P_POISON][0]--;
			if (health > 0)
				damage += status[P_POISON][1];
		}

		// update weak
		for (int[] ws : weaks)
			ws[0]--;
		weaks.removeIf(w -> w[0] <= 0);
		int max = 0;
		int val = 100;
		for (int[] ws : weaks) {
			max = Math.max(max, ws[0]);
			val = Math.min(val, ws[1]);
		}
		status[P_WEAK][0] = max;
		status[P_WEAK][1] = val;
	}

	/** update revive status */
	private void updateRevive() {
		if (status[P_REVIVE][1] > 0) {
			acted = true;
			int id = dire == -1 ? A_U_ZOMBIE : A_ZOMBIE;
			EffAnim ea = EffAnim.effas[id];
			if (corpse == null)
				corpse = ea.getEAnim(1);
			if (status[P_REVIVE][1] == ea.getEAnim(0).len())
				corpse = ea.getEAnim(0);
			status[P_REVIVE][1]--;
			if (corpse != null)
				corpse.update(false);
			if (status[P_REVIVE][1] == 0)
				corpse = null;
		}
	}

	/** update touch state, move if possible */
	private void updateTouch() {
		touch = true;
		double[] ds = aam.touchRange();
		List<AbEntity> le = basis.inRange(data.getTouch(), dire, ds[0], ds[1]);
		boolean blds = false;
		if (data.isLD()) {
			double bpos = basis.getBase(dire).pos;
			blds = (bpos - pos) * dire > data.touchBase();
			if (blds)
				le.remove(basis.getBase(dire));
			blds &= le.size() == 0;
		} else
			blds = le.size() == 0;
		if (status[P_STOP][0] == 0 && blds) {
			touch = false;
			if (anim.type != 0)
				anim.changeAnim(0);
			updateMove(-1, 0);
		}
		touchEnemy = touch;
		if ((getAbi() & AB_ONLY) > 0) {
			touchEnemy = false;
			for (AbEntity e : le)
				if (e.targetable(type))
					touchEnemy = true;
		}
	}

}
