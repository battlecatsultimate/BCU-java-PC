package utilpc;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.battle.Treasure;
import common.battle.data.*;
import common.pack.Identifier;
import common.util.Data;
import common.util.Data.Proc.ProcItem;
import common.util.lang.Formatter;
import common.util.lang.ProcLang;
import common.util.stage.MapColc;
import common.util.stage.MapColc.DefMapColc;
import common.util.unit.Combo;
import common.util.unit.Enemy;
import page.Page;

import java.util.ArrayList;
import java.util.List;

public class Interpret extends Data {

	/**
	 * enemy types
	 */
	public static String[] ERARE;

	/**
	 * unit rarities
	 */
	public static String[] RARITY;

	/**
	 * enemy traits
	 */
	public static String[] TRAIT;

	/**
	 * star names
	 */
	public static String[] STAR;

	/**
	 * ability name
	 */
	public static String[] ABIS;

	/**
	 * enemy ability name
	 */
	public static String[] EABI;

	public static String[] SABIS;
	public static String[] TREA;
	public static String[] TEXT;
	public static String[] ATKCONF;
	public static String[] COMF;
	public static String[] COMN;
	public static String[] TCTX;
	public static String[] PCTX;

	/**
	 * treasure orderer
	 */
	public static final int[] TIND = { 0, 1, 18, 19, 20, 21, 22, 23, 2, 3, 4, 5, 24, 25, 26, 27, 28, 6, 7, 8, 9, 10, 11,
			12, 13, 14, 15, 16, 17, 29, 30, 31, 32, 33, 34, 35, 36 };

	/**
	 * treasure grouper
	 */
	public static final int[][] TCOLP = { { 0, 6 }, { 8, 6 }, { 14, 3 }, { 17, 4 }, { 21, 3 }, { 29, 8 } };

	/**
	 * treasure max
	 */
	private static final int[] TMAX = { 30, 30, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 600, 1500, 100,
			100, 100, 30, 30, 30, 30, 30, 10, 300, 300, 600, 600, 600, 20, 30, 30, 30, 20, 20, 20, 20 };

	/**
	 * combo string component
	 */
	private static final String[][] CDP = { { "", "+", "-" }, { "_", "_%", "_f", "Lv._" } };

	/**
	 * combo string formatter
	 */
	private static final int[][] CDC = { { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 3 }, { 1, 0 }, { 1, 1 }, { 2, 2 },
			{}, { 1, 1 }, { 1, 1 }, { 2, 2 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 },
			{ 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 }, { 1, 1 } };

	public static final int[] EABIIND = { 5, 7, 8, 9, 10, 11, 12, 15, 16, 18, 113, 114, 115, 116, 117, 118, 119, 133,
			134 };
	public static final int[] ABIIND = { 113, 114, 115, 116, 117, 118, 119, 133, 134 };
	public static final int IMUSFT = 13, EFILTER = 8;

	static {
		redefine();
	}

	public static boolean allRangeSame(MaskEntity me) {
		if (me instanceof CustomEntity) {
			List<Integer> near = new ArrayList<>();
			List<Integer> far = new ArrayList<>();

			for (AtkDataModel atk : ((CustomEntity) me).atks) {
				near.add(atk.getShortPoint());
				far.add(atk.getLongPoint());
			}

			if (near.isEmpty() && far.isEmpty()) {
				return true;
			}

			for (int n : near) {
				if (n != near.get(0)) {
					return false;
				}
			}

			for (int f : far) {
				if (f != far.get(0)) {
					return false;
				}
			}

			return true;
		} else {
			return true;
		}
	}

	public static String comboInfo(Combo c) {
		return combo(c.type, CommonStatic.getBCAssets().values[c.type][c.lv]);
	}

	public static String[] comboInfo(int[] inc) {
		List<String> ls = new ArrayList<>();
		for (int i = 0; i < C_TOT; i++) {
			if (inc[i] == 0)
				continue;
			ls.add(combo(i, inc[i]));
		}
		return ls.toArray(new String[0]);
	}

	public static List<String> getAbi(MaskEntity me) {
		int tb = me.touchBase();
		final MaskAtk ma;

		if (me.getAtkCount() == 1) {
			ma = me.getAtkModel(0);
		} else {
			ma = me.getRepAtk();
		}

		int lds;
		int ldr;

		if (allRangeSame(me)) {
			lds = me.getAtkModel(0).getShortPoint();
			ldr = me.getAtkModel(0).getLongPoint() - me.getAtkModel(0).getShortPoint();
		} else {
			lds = ma.getShortPoint();
			ldr = ma.getLongPoint() - ma.getShortPoint();
		}

		List<String> l = new ArrayList<>();
		if (lds != -me.getWidth() && ldr != 0) {
			int p0 = Math.min(lds, lds + ldr);
			int p1 = Math.max(lds, lds + ldr);
			int r = Math.abs(ldr);
			l.add(Page.get(3, "ld0") + ": " + tb + ", " + Page.get(3, "ld1") + ": " + p0 + "~" + p1 + ", "
					+ Page.get(3, "ld2") + ": " + r);
		}
		String imu = Page.get(3, "imu");
		for (int i = 0; i < ABIS.length; i++)
			if (((me.getAbi() >> i) & 1) > 0)
				if (ABIS[i].startsWith("IMU"))
					imu += ABIS[i].substring(3) + ", ";
				else
					l.add(ABIS[i]);

		if (imu.length() > 10)
			l.add(imu);
		return l;
	}

	public static String[] getComboFilter(int n) {
		int[] res = CommonStatic.getBCAssets().filter[n];
		String[] strs = new String[res.length];
		for (int i = 0; i < res.length; i++)
			strs[i] = COMN[res[i]];
		return strs;
	}

	public static int getComp(int ind, Treasure t) {
		int ans = -2;
		for (int i = 0; i < TCOLP[ind][1]; i++) {
			int temp = getValue(TIND[i + TCOLP[ind][0]], t);
			if (ans == -2)
				ans = temp;
			else if (ans != temp)
				return -1;
		}
		return ans;
	}

	public static List<String> getProc(MaskEnemy de) {
		Formatter.Context ctx = new Formatter.Context(true, false);
		List<String> l = new ArrayList<>();
		MaskAtk ma = de.getRepAtk();
		for (int i = 0; i < Data.PROC_TOT; i++) {
			ProcItem item = ma.getProc().getArr(i);
			if (!item.exists())
				continue;
			String format = ProcLang.get().get(i).format;
			String formatted = Formatter.format(format, item, ctx);
			l.add(formatted);

		}
		return l;
	}

	public static List<String> getProc(MaskEntity du, Treasure t, int trait) {
		List<String> l = new ArrayList<>();
		Formatter.Context ctx = new Formatter.Context(false, false);
		MaskAtk ma = du.getRepAtk();
		for (int i = 0; i < Data.PROC_TOT; i++) {
			ProcItem item = ma.getProc().getArr(i);
			if (!item.exists())
				continue;
			String format = ProcLang.get().get(i).format;
			String formatted = Formatter.format(format, item, ctx);
			l.add(formatted);
		}
		return l;
	}

	public static String getTrait(int type, int star) {
		String ans = "";
		for (int i = 0; i < TRAIT.length; i++)
			if (((type >> i) & 1) > 0)
				ans += TRAIT[i] + ", ";
		if (star > 0)
			ans += STAR[star];
		return ans;
	}

	public static int getValue(int ind, Treasure t) {
		if (ind == 0)
			return t.tech[LV_RES];
		else if (ind == 1)
			return t.tech[LV_ACC];
		else if (ind == 2)
			return t.trea[T_ATK];
		else if (ind == 3)
			return t.trea[T_DEF];
		else if (ind == 4)
			return t.trea[T_RES];
		else if (ind == 5)
			return t.trea[T_ACC];
		else if (ind == 6)
			return t.fruit[T_RED];
		else if (ind == 7)
			return t.fruit[T_FLOAT];
		else if (ind == 8)
			return t.fruit[T_BLACK];
		else if (ind == 9)
			return t.fruit[T_ANGEL];
		else if (ind == 10)
			return t.fruit[T_METAL];
		else if (ind == 11)
			return t.fruit[T_ZOMBIE];
		else if (ind == 12)
			return t.fruit[T_ALIEN];
		else if (ind == 13)
			return t.alien;
		else if (ind == 14)
			return t.star;
		else if (ind == 15)
			return t.gods[0];
		else if (ind == 16)
			return t.gods[1];
		else if (ind == 17)
			return t.gods[2];
		else if (ind == 18)
			return t.tech[LV_BASE];
		else if (ind == 19)
			return t.tech[LV_WORK];
		else if (ind == 20)
			return t.tech[LV_WALT];
		else if (ind == 21)
			return t.tech[LV_RECH];
		else if (ind == 22)
			return t.tech[LV_CATK];
		else if (ind == 23)
			return t.tech[LV_CRG];
		else if (ind == 24)
			return t.trea[T_WORK];
		else if (ind == 25)
			return t.trea[T_WALT];
		else if (ind == 26)
			return t.trea[T_RECH];
		else if (ind == 27)
			return t.trea[T_CATK];
		else if (ind == 28)
			return t.trea[T_BASE];
		else if (ind == 29)
			return t.bslv[BASE_H];
		else if (ind == 30)
			return t.bslv[BASE_SLOW];
		else if (ind == 31)
			return t.bslv[BASE_WALL];
		else if (ind == 32)
			return t.bslv[BASE_STOP];
		else if (ind == 33)
			return t.bslv[BASE_WATER];
		else if (ind == 34)
			return t.bslv[BASE_GROUND];
		else if (ind == 35)
			return t.bslv[BASE_BARRIER];
		else if (ind == 36)
			return t.bslv[BASE_CURSE];
		return -1;
	}

	public static boolean isER(Enemy e, int t) {
		if (t == 0)
			return e.inDic;
		if (t == 1)
			return e.de.getStar() == 1;
		List<MapColc> lis = e.findMap();
		boolean colab = false;
		if (lis.contains(DefMapColc.getMap("C")))
			if (lis.size() == 1)
				colab = true;
			else if (lis.size() == 2)
				colab = lis.contains(DefMapColc.getMap("R")) || lis.contains(DefMapColc.getMap("CH"));

		if (t == 2)
			return !colab;
		if (t == 3)
			return !colab && !e.inDic;
		if (t == 4)
			return colab;
		if (t == 5)
			return !e.id.pack.equals(Identifier.DEF);
		return false;
	}

	public static boolean isType(MaskEntity de, int type) {
		int[][] raw = de.rawAtkData();
		if (type == 0)
			return !de.isRange();
		else if (type == 1)
			return de.isRange();
		else if (type == 2)
			return de.isLD();
		else if (type == 3)
			return raw.length > 1;
		else if (type == 4)
			return de.isOmni();
		else if (type == 5)
			return de.getTBA() + raw[0][1] < de.getItv() / 2;
		return false;
	}

	public static void redefine() {
		ERARE = Page.get(3, "er", 6);
		RARITY = Page.get(3, "r", 6);
		TRAIT = Page.get(3, "c", 12);
		STAR = Page.get(3, "s", 5);
		ABIS = Page.get(3, "a", 22);
		SABIS = Page.get(3, "sa", 22);
		ATKCONF = Page.get(3, "aa", 6);
		TREA = Page.get(3, "t", 37);
		TEXT = Page.get(3, "d", 9);
		COMF = Page.get(3, "na", 6);
		COMN = Page.get(3, "nb", 25);
		TCTX = Page.get(3, "tc", 6);
		PCTX = Page.get(3, "aq", 57);
		EABI = new String[EABIIND.length];
		for (int i = 0; i < EABI.length; i++) {
			if (EABIIND[i] < 100)
				EABI[i] = SABIS[EABIIND[i]];
			else
				EABI[i] = ProcLang.get().get(EABIIND[i] - 100).abbr_name;
		}
	}

	public static void setComp(int ind, int v, BasisSet b) {
		for (int i = 0; i < TCOLP[ind][1]; i++)
			setValue(TIND[i + TCOLP[ind][0]], v, b);
	}

	public static void setValue(int ind, int v, BasisSet b) {
		setVal(ind, v, b.t());
		for (BasisLU bl : b.lb)
			setVal(ind, v, bl.t());
	}

	private static String combo(int t, int val) {
		int[] con = CDC[t];
		return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replaceAll("_", "" + val);

	}

	private static void setVal(int ind, int v, Treasure t) {

		if (v < 0)
			v = 0;
		if (v > TMAX[ind])
			v = TMAX[ind];

		if (ind == 0)
			t.tech[LV_RES] = v;
		else if (ind == 1)
			t.tech[LV_ACC] = v;
		else if (ind == 2)
			t.trea[T_ATK] = v;
		else if (ind == 3)
			t.trea[T_DEF] = v;
		else if (ind == 4)
			t.trea[T_RES] = v;
		else if (ind == 5)
			t.trea[T_ACC] = v;
		else if (ind == 6)
			t.fruit[T_RED] = v;
		else if (ind == 7)
			t.fruit[T_FLOAT] = v;
		else if (ind == 8)
			t.fruit[T_BLACK] = v;
		else if (ind == 9)
			t.fruit[T_ANGEL] = v;
		else if (ind == 10)
			t.fruit[T_METAL] = v;
		else if (ind == 11)
			t.fruit[T_ZOMBIE] = v;
		else if (ind == 12)
			t.fruit[T_ALIEN] = v;
		else if (ind == 13)
			t.alien = v;
		else if (ind == 14)
			t.star = v;
		else if (ind == 15)
			t.gods[0] = v;
		else if (ind == 16)
			t.gods[1] = v;
		else if (ind == 17)
			t.gods[2] = v;
		else if (ind == 18)
			t.tech[LV_BASE] = v;
		else if (ind == 19)
			t.tech[LV_WORK] = v;
		else if (ind == 20)
			t.tech[LV_WALT] = v;
		else if (ind == 21)
			t.tech[LV_RECH] = v;
		else if (ind == 22)
			t.tech[LV_CATK] = v;
		else if (ind == 23)
			t.tech[LV_CRG] = v;
		else if (ind == 24)
			t.trea[T_WORK] = v;
		else if (ind == 25)
			t.trea[T_WALT] = v;
		else if (ind == 26)
			t.trea[T_RECH] = v;
		else if (ind == 27)
			t.trea[T_CATK] = v;
		else if (ind == 28)
			t.trea[T_BASE] = v;
		else if (ind == 29)
			t.bslv[BASE_H] = v;
		else if (ind == 30)
			t.bslv[BASE_SLOW] = v;
		else if (ind == 31)
			t.bslv[BASE_WALL] = v;
		else if (ind == 32)
			t.bslv[BASE_STOP] = v;
		else if (ind == 33)
			t.bslv[BASE_WATER] = v;
		else if (ind == 34)
			t.bslv[BASE_GROUND] = v;
		else if (ind == 35)
			t.bslv[BASE_BARRIER] = v;
		else if (ind == 36)
			t.bslv[BASE_CURSE] = v;

	}

}