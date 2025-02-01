package utilpc;

import common.CommonStatic;
import common.battle.BasisLU;
import common.battle.BasisSet;
import common.battle.Treasure;
import common.battle.data.*;
import common.pack.Identifier;
import common.pack.UserProfile;
import common.system.P;
import common.util.Data;
import common.util.Data.Proc.ProcItem;
import common.util.lang.Formatter;
import common.util.lang.MultiLangCont;
import common.util.lang.ProcLang;
import common.util.stage.MapColc;
import common.util.stage.MapColc.DefMapColc;
import common.util.stage.Stage;
import common.util.stage.info.CustomStageInfo;
import common.util.stage.info.DefStageInfo;
import common.util.stage.info.StageInfo;
import common.util.unit.Combo;
import common.util.unit.Enemy;
import io.BCJSON;
import main.MainBCU;
import page.MainLocale;
import page.Page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;

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
    public static String[] ATKCONF;
    public static String[] COMF;
    public static String[] COMN;
    public static String[] TCTX;
    public static String[] PCTX;

    /**
     * treasure orderer
     */
    public static final int[] TIND = {0, 1, 18, 19, 20, 21, 22, 23, 2, 3, 4, 5, 24, 25, 26, 27, 28, 6, 7, 8, 9, 10, 11,
            12, 13, 14, 15, 16, 17, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50};

    /**
     * treasure grouper
     */
    public static final int[][] TCOLP = {{0, 8}, {8, 6}, {14, 3}, {17, 4}, {21, 3}, {29, 22}};

    /**
     * treasure max
     */
    private static final int[] TMAX = {30, 30, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 300, 600, 1500, 100,
            100, 100, 30, 30, 30, 30, 30, 10, 300, 300, 600, 600, 600, 30, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};

    /**
     * combo string component
     */
    private static final String[][] CDP = {{"", "+", "-"}, {"_", "_%", "_f", "Lv._"}};

    /**
     * combo string formatter
     */
    private static final int[][] CDC = {{1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 3}, {1, 0}, {1, 1}, {2, 1},
            {1, 1}, {1, 1}, {1, 1}, {2, 2}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1},
            {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}, {1, 1}};

    //Filters abilities and procs that are available for enemies. Also gives better organization to the UI
    public static final int[] EABIIND = {ABI_CSUR, ABI_WAVES, ABI_SNIPERI, ABI_TIMEI, ABI_GHOST, ABI_GLASS, ABI_THEMEI};
    public static final int[] EPROCIND = {Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_WEAK, Data.P_CRIT, Data.P_METALKILL, Data.P_WAVE, Data.P_MINIWAVE,
            Data.P_VOLC, Data.P_MINIVOLC, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE, Data.P_SEAL,
            Data.P_SATK, Data.P_POIATK, Data.P_ATKBASE, Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME, Data.P_THEME,
            Data.P_POISON, Data.P_ARMOR, Data.P_SPEED, Data.P_STRONG, Data.P_SPEEDUP, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI, Data.P_COUNTER,
            Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP, Data.P_IMUSLOW, Data.P_IMUWAVE, Data.P_IMUWEAK,
            Data.P_IMUWARP, Data.P_IMUCURSE, Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK, Data.P_IMUVOLC,
            Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_IMUCANNON, Data.P_DEATHSURGE};
    //Filters abilities and procs that are available for units. Also gives better organization to the UI
    public static final int[] UPROCIND = {Data.P_BSTHUNT, Data.P_KB, Data.P_STOP, Data.P_SLOW, Data.P_WEAK, Data.P_BOUNTY, Data.P_CRIT, Data.P_METALKILL, Data.P_WAVE,
            Data.P_MINIWAVE, Data.P_VOLC, Data.P_MINIVOLC, Data.P_BARRIER, Data.P_DEMONSHIELD, Data.P_BREAK, Data.P_SHIELDBREAK, Data.P_WARP, Data.P_CURSE,
            Data.P_SEAL, Data.P_SATK, Data.P_POIATK, Data.P_ATKBASE, Data.P_SUMMON, Data.P_MOVEWAVE, Data.P_SNIPER, Data.P_BOSS, Data.P_TIME,
            Data.P_THEME, Data.P_POISON, Data.P_ARMOR, Data.P_SPEED, Data.P_STRONG, Data.P_SPEEDUP, Data.P_LETHAL, Data.P_BURROW, Data.P_REVIVE, Data.P_CRITI,
            Data.P_COUNTER, Data.P_IMUATK, Data.P_DMGCUT, Data.P_DMGCAP, Data.P_IMUKB, Data.P_IMUSTOP, Data.P_IMUSLOW, Data.P_IMUWAVE,
            Data.P_IMUWEAK, Data.P_IMUWARP, Data.P_IMUCURSE, Data.P_IMUSEAL, Data.P_IMUMOVING, Data.P_IMUARMOR, Data.P_IMUPOI, Data.P_IMUPOIATK,
            Data.P_IMUVOLC, Data.P_IMUSPEED, Data.P_IMUSUMMON, Data.P_DEATHSURGE, Data.P_SPIRIT};

    private static final DecimalFormat df;
    public static String[] lvl;

    static {
        redefine();

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        df = (DecimalFormat) nf;
    }

    public static void loadCannonMax() {
        for (int i = 1; i <= Treasure.curveData.size(); i++)
            TMAX[29 + i] = Treasure.curveData.get(i).getMax();
        for (int i = 1; i <= Treasure.baseData.size(); i++)
            TMAX[36 + i] = Treasure.baseData.get(i).getMax();
        for (int i = 1; i <= Treasure.decorationData.size(); i++)
            TMAX[43 + i] = Treasure.decorationData.get(i).getMax();
    }

    public static boolean allRangeSame(MaskEntity me) {
        if (me instanceof CustomEntity) {
            List<Integer> near = new ArrayList<>();
            List<Integer> far = new ArrayList<>();

            for (AtkDataModel atk : ((CustomEntity) me).atks) {
                near.add(atk.getShortPoint());
                far.add(atk.getLongPoint());
            }

            if (near.isEmpty()) {
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
        } else {
            for (int i = 1; i < me.getAtkCount(); i++) {
                if (me.getAtkModel(i).getShortPoint() != me.getAtkModel(0).getShortPoint() || me.getAtkModel(i).getLongPoint() != me.getAtkModel(0).getLongPoint())
                    return false;
            }
        }

        return true;
    }

    public static String comboInfo(Combo c, BasisSet b) {
        return combo(c.type, CommonStatic.getBCAssets().values[c.type][c.lv], b);
    }

    public static String deco(int type, BasisSet b) { // 0 = slow
        double mag = ((int) ((b.t().getDecorationMagnification(type + 1, type, true)))) / 100.0;
        return MainLocale.getLoc(MainLocale.UTIL, "dec" + type) + " +" + mag + "%";
    }

    public static String base(int type, BasisSet b) {
        double mag = ((int) (b.t().getBaseMagnification(type + 1, UserProfile.getBCData().traits.getList(), true))) / 100.0;
        return MainLocale.getLoc(MainLocale.UTIL, "bas" + type) + " +" + mag + "%";
    }

    public static class ProcDisplay {
        private String text;
        private ImageIcon icon = null;

        public ProcDisplay(String desc, BufferedImage img) {
            text = desc;
            if (img != null)
                icon = new ImageIcon(img);
        }

        @Override
        public String toString() {
            return text;
        }

        public ImageIcon getIcon() {
            return icon;
        }
    }

    public static List<ProcDisplay> getAbi(MaskEntity me) {
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


        List<ProcDisplay> l = new ArrayList<>();
        if (!allRangeSame(me)) {
            LinkedHashMap<String, List<Integer>> LDInts = new LinkedHashMap<>();
            MaskAtk[] atks = me.getAtks();
            List<BufferedImage> ics = new ArrayList<>();

            for (int i = 0; i < atks.length; i++) {
                int rs = atks[i].getShortPoint();
                int rl = atks[i].getLongPoint();
                if (rs == 0 && rl == 0)
                    continue;
                String LDData = Page.get(MainLocale.UTIL, "ld0") + ": " + tb + ", " + Page.get(MainLocale.UTIL, "ld1")
                        + ": " + rs + "~" + rl + ", " + Page.get(MainLocale.UTIL, "ld2") + ": " + (rl - rs);
                if (LDInts.containsKey(LDData)) {
                    List<Integer> li = LDInts.get(LDData);
                    li.add(i + 1);
                } else {
                    List<Integer> li = new ArrayList<>();
                    li.add(i + 1);

                    LDInts.put(LDData, li);
                }
                if (atks[i].isOmni())
                    ics.add(UtilPC.getIcon(2, ATK_OMNI));
                else
                    ics.add(UtilPC.getIcon(2, ATK_LD));
            }

            int i = 0;
            for (String key : LDInts.keySet()) {
                List<Integer> inds = LDInts.get(key);
                if (inds == null) {
                    l.add(new ProcDisplay(key, ics.get(i++)));
                } else {
                    if (inds.size() == me.getAtkCount()) {
                        l.add(new ProcDisplay(key, ics.get(i++)));
                    } else {
                        l.add(new ProcDisplay(key + " " + getAtkNumbers(inds), ics.get(i++)));
                    }
                }
            }
        } else if (lds != 0 || ldr != 0) {
            int p0 = Math.min(lds, lds + ldr);
            int p1 = Math.max(lds, lds + ldr);
            int r = Math.abs(ldr);
            BufferedImage bi;
            if (me.isOmni()) {
                bi = UtilPC.getIcon(2, ATK_OMNI);
            } else {
                bi = UtilPC.getIcon(2, ATK_LD);
            }
            l.add(new ProcDisplay(Page.get(MainLocale.UTIL, "ld0") + ": " + tb + ", " + Page.get(MainLocale.UTIL, "ld1") + ": " + p0 + "~" + p1 + ", "
                    + Page.get(MainLocale.UTIL, "ld2") + ": " + r, bi));
        }
        AtkDataModel rev = me.getRevenge();
        for (int z = 0; z < 6; z++) {
            if (rev != null) {
                int revs = rev.getShortPoint();
                int revl = rev.getLongPoint();
                if (revs != 0 || revl != 0) {
                    BufferedImage bi;
                    if (rev.isOmni())
                        bi = (UtilPC.getIcon(2, ATK_OMNI));
                    else
                        bi = (UtilPC.getIcon(2, ATK_LD));
                    l.add(new ProcDisplay(Page.get(MainLocale.UTIL, "ld1") + ": " + revs + "~" + revl +
                            ", " + Page.get(MainLocale.UTIL, "ld2") + ": " + (revl - revs) +
                            " [" + Page.get(MainLocale.UTIL, "aa" + (z + 6)) + "]", bi));
                }
            }
            switch (z) {
                case 0:
                    rev = me.getResurrection();
                    break;
                case 1:
                    rev = me.getCounter();
                    break;
                case 2:
                    rev = me.getGouge();
                    break;
                case 3:
                    rev = me.getResurface();
                    break;
                case 4:
                    rev = me.getRevive();
                    break;
                default:
                    rev = null;
            }
        }
        String imu = Page.get(MainLocale.UTIL, "imu");
        for (int i = 0; i < ABIS.length; i++)
            if (((me.getAbi() >> i) & 1) > 0)
                if (ABIS[i].startsWith("IMU"))
                    l.add(new ProcDisplay(imu + ABIS[i].substring(3), UtilPC.getIcon(0, i)));
                else {
                    l.add(new ProcDisplay(ABIS[i], UtilPC.getIcon(0, i)));
                }
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

    public static List<ProcDisplay> getProc(MaskEntity du, boolean isEnemy, double[] magnification) {
        Formatter.Context ctx = new Formatter.Context(isEnemy, MainBCU.seconds, magnification);
        final boolean common;

        if (du instanceof CustomEntity) {
            common = ((CustomEntity) du).common;
        } else {
            common = true;
        }

        ArrayList<ProcDisplay> l = new ArrayList<>();
        List<Boolean> share = new ArrayList<>();

        if (common) {
            MaskAtk ma = du.getRepAtk();

            for (int i = 0; i < Data.PROC_TOT; i++) {
                ProcItem item = ma.getProc().getArr(i);

                if (!item.exists())
                    continue;
                if (du instanceof DefaultData) {
                    boolean p = false;
                    if (ma.getProc().sharable(i))
                        p = true;
                    else
                        for (int pr : BCShareable)
                            if (pr == i) {
                                p = true;
                                break;
                            }
                    share.add(p);
                }
                String format = ProcLang.get().get(i).format;
                String formatted = Formatter.format(format, item, ctx);

                l.add(new ProcDisplay(formatted, UtilPC.getIcon(1, i)));
            }
        } else {
            LinkedHashMap<String, List<Integer>> atkMap = new LinkedHashMap<>();
            List<BufferedImage> procIcons = new ArrayList<>();

            MaskAtk ma = du.getRepAtk();

            for (int i = 0; i < Data.PROC_TOT; i++) {
                ProcItem item = ma.getProc().getArr(i);

                if (!item.exists() || !ma.getProc().sharable(i))
                    continue;

                String format = ProcLang.get().get(i).format;
                String formatted = Formatter.format(format, item, ctx);
                l.add(new ProcDisplay(formatted, UtilPC.getIcon(1, i)));
            }

            for (int i = 0; i < du.getAtkCount(); i++) {
                ma = du.getAtkModel(i);

                for (int j = 0; j < Data.PROC_TOT; j++) {
                    ProcItem item = ma.getProc().getArr(j);

                    if (!item.exists() || ma.getProc().sharable(j))
                        continue;

                    String format = ProcLang.get().get(j).format;
                    String formatted = Formatter.format(format, item, ctx);

                    if (atkMap.containsKey(formatted)) {
                        List<Integer> inds = atkMap.get(formatted);

                        inds.add(i + 1);
                    } else {
                        List<Integer> inds = new ArrayList<>();

                        inds.add(i + 1);

                        atkMap.put(formatted, inds);
                        procIcons.add(UtilPC.getIcon(1, j));
                    }
                }
            }

            int i = 0;
            for (String key : atkMap.keySet()) {
                List<Integer> inds = atkMap.get(key);

                if (inds == null) {
                    l.add(new ProcDisplay(key, procIcons.get(i++)));
                } else {
                    if (inds.size() == du.getAtkCount()) {
                        l.add(new ProcDisplay(key, procIcons.get(i++)));
                    } else {
                        l.add(new ProcDisplay(key + " " + getAtkNumbers(inds), procIcons.get(i++)));
                    }
                }
            }
        }

        if (du instanceof DefaultData && !((DefaultData) du).isCommon()) {
            int[][] atkData = du.rawAtkData();
            List<Integer> atks = new ArrayList<>();
            for (int i = 0; i < atkData.length; i++)
                if (atkData[i][2] == 1)
                    atks.add(i + 1);
            for (int i = 0; i < l.size(); i++)
                if (!share.get(i))
                    l.get(i).text = l.get(i).text + " " + getAtkNumbers(atks);
        }

        MaskAtk rev = du.getRevenge();
        for (int i = 0; i < 6; i++) {
            if (rev != null) {
                for (int j = 0; j < Data.PROC_TOT; j++) {
                    ProcItem item = rev.getProc().getArr(j);

                    if (!item.exists() || rev.getProc().sharable(j))
                        continue;

                    String format = ProcLang.get().get(j).format;
                    String formatted = Formatter.format(format, item, ctx);
                    l.add(new ProcDisplay(formatted + " [" + Page.get(MainLocale.UTIL, "aa" + (6 + i)) + "]", UtilPC.getIcon(1, j)));
                }
            }
            switch (i) {
                case 0:
                    rev = du.getResurrection();
                    break;
                case 1:
                    rev = du.getCounter();
                    break;
                case 2:
                    rev = du.getGouge();
                    break;
                case 3:
                    rev = du.getResurface();
                    break;
                case 4:
                    rev = du.getRevive();
                    break;
                default:
                    rev = null;
            }
        }

        return l;
    }

    public static String getTrait(String[] cTraits, int star) {
        StringBuilder ans = new StringBuilder();
        for (String cTrait : cTraits) ans.append(cTrait).append(", ");
        if (star > 0)
            ans.append(STAR[star]);

        String res = ans.toString();

        if (res.endsWith(", ")) {
            res = res.substring(0, res.length() - 2);
        }

        return res;
    }

    public static int getValue(int ind, Treasure t) {
        switch (ind) {
            case 0:
                return t.tech[LV_RES];
            case 1:
                return t.tech[LV_ACC];
            case 2:
                return t.trea[T_ATK];
            case 3:
                return t.trea[T_DEF];
            case 4:
                return t.trea[T_RES];
            case 5:
                return t.trea[T_ACC];
            case 6:
                return t.fruit[T_RED];
            case 7:
                return t.fruit[T_FLOAT];
            case 8:
                return t.fruit[T_BLACK];
            case 9:
                return t.fruit[T_ANGEL];
            case 10:
                return t.fruit[T_METAL];
            case 11:
                return t.fruit[T_ZOMBIE];
            case 12:
                return t.fruit[T_ALIEN];
            case 13:
                return t.alien;
            case 14:
                return t.star;
            case 15:
                return t.gods[0];
            case 16:
                return t.gods[1];
            case 17:
                return t.gods[2];
            case 18:
                return t.tech[LV_BASE];
            case 19:
                return t.tech[LV_WORK];
            case 20:
                return t.tech[LV_WALT];
            case 21:
                return t.tech[LV_RECH];
            case 22:
                return t.tech[LV_CATK];
            case 23:
                return t.tech[LV_CRG];
            case 24:
                return t.trea[T_WORK];
            case 25:
                return t.trea[T_WALT];
            case 26:
                return t.trea[T_RECH];
            case 27:
                return t.trea[T_CATK];
            case 28:
                return t.trea[T_BASE];
            case 29:
                return t.bslv[BASE_H];
            case 30:
                return t.bslv[BASE_SLOW];
            case 31:
                return t.bslv[BASE_WALL];
            case 32:
                return t.bslv[BASE_STOP];
            case 33:
                return t.bslv[BASE_WATER];
            case 34:
                return t.bslv[BASE_GROUND];
            case 35:
                return t.bslv[BASE_BARRIER];
            case 36:
                return t.bslv[BASE_CURSE];
            case 37:
                return t.base[DECO_BASE_SLOW - 1];
            case 38:
                return t.base[DECO_BASE_WALL - 1];
            case 39:
                return t.base[DECO_BASE_STOP - 1];
            case 40:
                return t.base[DECO_BASE_WATER - 1];
            case 41:
                return t.base[DECO_BASE_GROUND - 1];
            case 42:
                return t.base[DECO_BASE_BARRIER - 1];
            case 43:
                return t.base[DECO_BASE_CURSE - 1];
            case 44:
                return t.deco[DECO_BASE_SLOW - 1];
            case 45:
                return t.deco[DECO_BASE_WALL - 1];
            case 46:
                return t.deco[DECO_BASE_STOP - 1];
            case 47:
                return t.deco[DECO_BASE_WATER - 1];
            case 48:
                return t.deco[DECO_BASE_GROUND - 1];
            case 49:
                return t.deco[DECO_BASE_BARRIER - 1];
            case 50:
                return t.deco[DECO_BASE_CURSE - 1];
            default:
                return -1;
        }
    }

    public static boolean isER(Enemy e, int t) {
        if (t == 0)
            return e.inDic;
        else if (t == 1)
            return e.de.getStar() == 1;
        else if (t == 5)
            return !e.id.pack.equals(Identifier.DEF);

        List<MapColc> lis = e.findMap();
        boolean colab = false;
        final int recurring;
        if (e.de instanceof DataEnemy)
            recurring = e.findApp(DefMapColc.getMap("N")).size() + e.findApp(DefMapColc.getMap("A")).size() + e.findApp(DefMapColc.getMap("CH")).size();
        else
            recurring = e.findApp(UserProfile.getUserPack(e.id.pack).mc).size();
        if (lis.contains(DefMapColc.getMap("C")))
            if (lis.size() == 1)
                colab = true;
            else {
                colab = lis.contains(DefMapColc.getMap("R")) || lis.contains(DefMapColc.getMap("CH")) || lis.contains(DefMapColc.getMap("CA"));
                if (lis.size() > 2)
                    colab &= recurring == 0;
            }
        if (t == 2)
            return !colab;
        else if (t == 3)
            return e.id.pack.equals(Identifier.DEF) && !e.inDic;
        else if (t == 4)
            return colab;
        else if (t == 6)
            return recurring > 1;
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
        else if (type == 6)
            return de.getRevenge() != null;
        else if (type == 7)
            return de.getResurrection() != null;
        return false;
    }

    public static void redefine() {
        ERARE = Page.get(MainLocale.UTIL, "er", 7);
        RARITY = Page.get(MainLocale.UTIL, "r", 6);
        TRAIT = Page.get(MainLocale.UTIL, "c", TRAIT_TOT);
        STAR = Page.get(MainLocale.UTIL, "s", 5);
        ABIS = Page.get(MainLocale.UTIL, "a", ABI_TOT);
        SABIS = Page.get(MainLocale.UTIL, "sa", ABI_TOT);
        ATKCONF = Page.get(MainLocale.UTIL, "aa", 8);
        TREA = Page.get(MainLocale.UTIL, "t", 51);
        COMF = Page.get(MainLocale.UTIL, "na", 6);
        COMN = Page.get(MainLocale.UTIL, "nb", 25);
        TCTX = Page.get(MainLocale.UTIL, "tc", 6);
        PCTX = Page.get(MainLocale.UTIL, "aq", PC_CORRES.length);
        EABI = new String[EABIIND.length];
        for (int i = 0; i < EABI.length; i++)
            EABI[i] = SABIS[EABIIND[i]];
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

    private static String combo(int t, int val, BasisSet b) {
        int[] con = CDC[t];
        if (t == C_RESP) {
            double research = (b.t().tech[LV_RES] - 1) * 6 + b.t().trea[T_RES] * 0.3;
            return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replaceAll("_", String.valueOf(research * val / 100));
        } else {
            return COMN[t] + " " + CDP[0][con[0]] + CDP[1][con[1]].replaceAll("_", String.valueOf(val));
        }
    }

    private static void setVal(int ind, int v, Treasure t) {
        if (v < 0)
            v = 0;

        v = Math.min(v, TMAX[ind]);

        switch (ind) {
            case 0:
                t.tech[LV_RES] = Math.max(v, 1);
                break;
            case 1:
                t.tech[LV_ACC] = Math.max(v, 1);
                break;
            case 2:
                t.trea[T_ATK] = v;
                break;
            case 3:
                t.trea[T_DEF] = v;
                break;
            case 4:
                t.trea[T_RES] = v;
                break;
            case 5:
                t.trea[T_ACC] = v;
                break;
            case 6:
                t.fruit[T_RED] = v;
                break;
            case 7:
                t.fruit[T_FLOAT] = v;
                break;
            case 8:
                t.fruit[T_BLACK] = v;
                break;
            case 9:
                t.fruit[T_ANGEL] = v;
                break;
            case 10:
                t.fruit[T_METAL] = v;
                break;
            case 11:
                t.fruit[T_ZOMBIE] = v;
                break;
            case 12:
                t.fruit[T_ALIEN] = v;
                break;
            case 13:
                t.alien = v;
                break;
            case 14:
                t.star = v;
                break;
            case 15:
                t.gods[0] = v;
                break;
            case 16:
                t.gods[1] = v;
                break;
            case 17:
                t.gods[2] = v;
                break;
            case 18:
                t.tech[LV_BASE] = Math.max(v, 1);
                break;
            case 19:
                t.tech[LV_WORK] = Math.max(v, 1);
                break;
            case 20:
                t.tech[LV_WALT] = Math.max(v, 1);
                break;
            case 21:
                t.tech[LV_RECH] = Math.max(v, 1);
                break;
            case 22:
                t.tech[LV_CATK] = Math.max(v, 1);
                break;
            case 23:
                t.tech[LV_CRG] = Math.max(v, 1);
                break;
            case 24:
                t.trea[T_WORK] = v;
                break;
            case 25:
                t.trea[T_WALT] = v;
                break;
            case 26:
                t.trea[T_RECH] = v;
                break;
            case 27:
                t.trea[T_CATK] = v;
                break;
            case 28:
                t.trea[T_BASE] = v;
                break;
            case 29:
                t.bslv[BASE_H] = v;
                break;
            case 30:
                t.bslv[BASE_SLOW] = v;
                break;
            case 31:
                t.bslv[BASE_WALL] = v;
                break;
            case 32:
                t.bslv[BASE_STOP] = v;
                break;
            case 33:
                t.bslv[BASE_WATER] = v;
                break;
            case 34:
                t.bslv[BASE_GROUND] = v;
                break;
            case 35:
                t.bslv[BASE_BARRIER] = v;
                break;
            case 36:
                t.bslv[BASE_CURSE] = v;
                break;
            case 37:
                t.base[DECO_BASE_SLOW - 1] = v;
                break;
            case 38:
                t.base[DECO_BASE_WALL - 1] = v;
                break;
            case 39:
                t.base[DECO_BASE_STOP - 1] = v;
                break;
            case 40:
                t.base[DECO_BASE_WATER - 1] = v;
                break;
            case 41:
                t.base[DECO_BASE_GROUND - 1] = v;
                break;
            case 42:
                t.base[DECO_BASE_BARRIER - 1] = v;
                break;
            case 43:
                t.base[DECO_BASE_CURSE - 1] = v;
                break;
            case 44:
                t.deco[DECO_BASE_SLOW - 1] = v;
                break;
            case 45:
                t.deco[DECO_BASE_WALL - 1] = v;
                break;
            case 46:
                t.deco[DECO_BASE_STOP - 1] = v;
                break;
            case 47:
                t.deco[DECO_BASE_WATER - 1] = v;
                break;
            case 48:
                t.deco[DECO_BASE_GROUND - 1] = v;
                break;
            case 49:
                t.deco[DECO_BASE_BARRIER - 1] = v;
                break;
            case 50:
                t.deco[DECO_BASE_CURSE - 1] = v;
                break;
        }
    }

    private static String getAtkNumbers(List<Integer> inds) {
        StringBuilder builder = new StringBuilder("[");

        switch (CommonStatic.getConfig().lang) {
            case ZH:
                builder.append("第 ");

                for (int i = 0; i < inds.size(); i++) {
                    builder.append(inds.get(i));

                    if (i < inds.size() - 1) {
                        builder.append(", ");
                    }
                }

                return builder.append(" 次攻擊]").toString();
            case KR:
                for (int i = 0; i < inds.size(); i++) {
                    builder.append(inds.get(i));

                    if (i < inds.size() - 1) {
                        builder.append(", ");
                    }
                }

                return builder.append(" 번째 공격]").toString();
            case JP:
                for (int i = 0; i < inds.size(); i++) {
                    builder.append(inds.get(i));

                    if (i < inds.size() - 1) {
                        builder.append(", ");
                    }
                }

                return builder.append(" 回目の攻撃]").toString();
            default:
                for (int i = 0; i < inds.size(); i++) {
                    builder.append(getNumberExtension(inds.get(i)));

                    if (i < inds.size() - 1) {
                        builder.append(", ");
                    }
                }

                return builder.append(" Attack]").toString();
        }
    }

    private static String getNumberExtension(int i) {
        if (i != 11 && i % 10 == 1) {
            return i + "st";
        } else if (i != 12 && i % 10 == 2) {
            return i + "nd";
        } else if (i != 13 && i % 10 == 3) {
            return i + "rd";
        } else {
            return i + "th";
        }
    }

    public static double formatDouble(double number, int decimalPlaces) {
        String format = "#." + new String(new char[decimalPlaces]).replace("\0", "#");
        df.applyPattern(format);
        return Double.parseDouble(df.format(number));
    }

    public static void setUnderline(JLabel label) {
        label.addMouseListener(new MouseAdapter() {
            String text;

            @Override
            public void mouseClicked(MouseEvent e) {
                JLabel j = (JLabel) e.getComponent();

                if (text == null || !j.getText().equals("<html><u>" + text + "</u></html>")) {
                    text = j.getText();
                    j.setText("<html><u>" + j.getText() + "</u></html>");
                } else {
                    j.setText(text);
                }
            }
        });

        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static Point getPoint(Point p, double x, double y, double size) {
        return new Point((int) ((p.x + x) / size), (int) ((p.y + y) / size));
    }

    public static Point getPoint(Point p, P pp, double size) {
        return new Point((int) ((p.x + pp.x) / size), (int) ((p.y + pp.y) / size));
    }

    public static String readHTMLStage(Stage st, boolean noHtml) { // TODO: cleanup html
        boolean exists = st.lim != null && st.lim.stageLimit != null;
        if (!exists)
            return noHtml ? "<html>No stage limit</html>" : "No limits";
        StringBuilder ans = new StringBuilder((noHtml ? "" : "<html>") + Page.get(MainLocale.INFO, "comboban") + ": ");

        if (!st.lim.stageLimit.bannedCatCombo.isEmpty()) {
            String[] comboData = new String[st.lim.stageLimit.bannedCatCombo.size()];
            int i = 0;
            for (int id : st.lim.stageLimit.bannedCatCombo)
                comboData[i++] = Page.get(MainLocale.UTIL, "nb" + id);
            ans.append(String.join(", ", comboData));
        } else {
            ans.append(Page.get(MainLocale.PAGE, "none"));
        }

        ans.append("<br>Cooldown on Start: ").append(st.lim.stageLimit.coolStart);
        ans.append("<br>Cooldown Multipliers: ").append(Arrays.toString(st.lim.stageLimit.cooldownMultiplier));
        ans.append("<br>Cost Multipliers: ").append(Arrays.toString(st.lim.stageLimit.costMultiplier));

        if (!noHtml)
            ans.append("</html>");

        return ans.toString();
    }

    public static String readHTML(StageInfo data) { // todo: figure out how to do this with custom stages without stage info (hopefully figure out how to attach all custom stages with stage info????)
        boolean isDef = data instanceof DefStageInfo;
        StringBuilder ans = new StringBuilder("<html>");

        if (isDef) {
            ans.append(Page.get(MainLocale.INFO, "energy")).append(": ").append(data.getEnergy());
            ans.append("<br>").append(Page.get(MainLocale.INFO, "xp")).append(": ").append(data.getXp());

            ans.append("<br>").append(Page.get(MainLocale.INFO, "hideclear")).append(": ")
                    .append(data.getStage().getCont().info.hiddenUponClear);

            if (data.getStage().getCont().info.resetMode != -1) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "reward" + data.getStage().getCont().info.resetMode));
            }

            if (data.getStage().getCont().info.clearLimit != -1) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "playnum")).append(": ")
                        .append(data.getStage().getCont().info.clearLimit);
            }

            if (data.getStage().getCont().info.waitTime != -1) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "playtime")).append(": ")
                        .append(data.getStage().getCont().info.waitTime)
                        .append(" minute(s)");
            }

            if (data.getStage().getCont().info.cantUseGoldCPU) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "nogold"));
            }
        }

        ans.append("<br>").append(readHTMLStage(data.getStage(), true));

        if (isDef) {
            ans.append("<br><br>").append(Page.get(MainLocale.INFO, "exstage")).append(": ")
                    .append(data.hasExConnection() || (data.getExStages() != null && data.getExChances() != null));

            if (data.hasExConnection()) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "exchance")).append(": ")
                        .append(data.getExChance())
                        .append("%<br>").append(Page.get(MainLocale.INFO, "exmap")).append(": ")
                        .append(MultiLangCont.get(MapColc.get("000004").maps.get(data.getExMapId())))
                        .append("<br>").append(Page.get(MainLocale.INFO, "exmin")).append(": ")
                        .append(Data.duo(data.getExStageIdMin()))
                        .append("<br>").append(Page.get(MainLocale.INFO, "exmax")).append(": ")
                        .append(Data.duo(data.getExStageIdMax()))
                        .append("<br>");
            }

            if (data.getExStages() != null && data.getExChances() != null) {
                ans.append("<table><tr><th>")
                        .append(Page.get(MainLocale.INFO, "exname"))
                        .append("</th><th>")
                        .append(Page.get(MainLocale.INFO, "chance"))
                        .append("</th></tr>");

                for (int i = 0; i < data.getExStages().length; i++) {
                    if (data.getExStages()[i] == null)
                        continue;

                    String name = MultiLangCont.get(data.getExStages()[i]);
                    String smName = MultiLangCont.get(data.getExStages()[i].getCont());

                    if (name == null || name.isEmpty())
                        name = data.getExStages()[i].id.toString();
                    else if (smName == null || smName.isEmpty()) {
                        smName = data.getExStages()[i].getCont().id.toString();

                        name = smName + " - " + name;
                    } else {
                        name = smName + " - " + name;
                    }

                    ans.append("<tr><td>")
                            .append(name)
                            .append("</td><td>")
                            .append(df.format(data.getExChances()[i]))
                            .append("%</td></tr>");
                }

                ans.append("</table>");
            }

            if (!data.hasExConnection() && (data.getExStages() == null || data.getExChances() == null)) {
                ans.append("<br>");
            }

            ans.append("<br>").append(Page.get(MainLocale.INFO, "rewards"));

            if (data.getDrop() == null || data.getDrop().length == 0) {
                ans.append(": ").append(Page.get(MainLocale.PAGE, "none"));
            } else {
                ans.append("<br>");
                readDropData((DefStageInfo) data, ans);
            }

            if (data.getTime().length > 0) {
                ans.append("<br>").append(Page.get(MainLocale.INFO, "timescore")).append("<br>");
                ans.append("<table><tr><th>")
                        .append(Page.get(MainLocale.INFO, "score"))
                        .append("</th><th>")
                        .append(Page.get(MainLocale.INFO, "item"))
                        .append("</th><th>")
                        .append(Page.get(MainLocale.INFO, "count"))
                        .append("</th></tr>");
                for (int[] tm : data.getTime())
                    ans.append("<tr><td>").append(tm[0]).append("</td><td>").append(MultiLangCont.getStageDrop(tm[1])).append("</td><td>").append(tm[2]).append("</td><tr>");
                ans.append("</table>");
            }
        } else {
            CustomStageInfo cdata = (CustomStageInfo) data;
            if (cdata.getExStages().length == 0)
                return null;
            ans.append("<br><br><table><tr><th>")
                    .append(Page.get(MainLocale.INFO, "followups"))
                    .append(":</th></tr>");
            for (int i = 0; i < cdata.getExStages().length; i++)
                ans.append("<tr><td>")
                        .append(cdata.getExStages()[i].getCont().toString())
                        .append(" - ")
                        .append(cdata.getExStages()[i].toString())
                        .append("</td><td>")
                        .append(df.format(cdata.getExChances()[i]))
                        .append("%</td></tr>");
        }


        return ans.toString();
    }

    public static void readDropData(DefStageInfo data, StringBuilder ans) { // fixme: incorrect ItF drop reward
        if (data.drop == null || data.drop.length == 0) {
            ans.append(Page.get(MainLocale.PAGE, "none"));
            return;
        }

        List<String> chances = data.analyzeRewardChance();

        if (chances == null) {
            ans.append(Page.get(MainLocale.PAGE, "none"));
            return;
        }

        ans.append("<table><tr><th>")
                .append(Page.get(MainLocale.INFO, chances.isEmpty() ? "numid" : "chance"))
                .append("</th><th>")
                .append(Page.get(MainLocale.INFO, "item"))
                .append("</th><th>")
                .append(Page.get(MainLocale.INFO, "count"))
                .append("</th></tr>");

        for (int i = 0; i < data.drop.length; i++) {
            if (!chances.isEmpty() && i < chances.size() && Double.parseDouble(chances.get(i)) == 0.0)
                continue;

            String chance = chances.isEmpty() ? String.valueOf(i + 1) : chances.get(i) + "%";
            String reward = MultiLangCont.getStageDrop(data.drop[i][1]);

            if (reward == null || reward.isEmpty())
                reward = Page.get(MainLocale.INFO, "rewid") + " " + data.drop[i][1];
            if (i == 0 && (data.rand == 1 || (data.drop[i][1] >= 1000 && data.drop[i][1] < 30000)))
                reward += " " + Page.get(MainLocale.INFO, "once");
            if (i == 0 && data.drop[i][0] != 100 && data.rand != -4)
                reward += " " + Page.get(MainLocale.INFO, "radar");

            ans.append("<tr><td>")
                    .append(chance)
                    .append("</td><td>")
                    .append(reward)
                    .append("</td><td>")
                    .append(data.drop[i][2])
                    .append("</td></tr>");
        }

        ans.append("</table>");
    }

    public static Vector<CommonStatic.Lang.Locale> getLocales() {
        Vector<CommonStatic.Lang.Locale> locales = new Vector<>();
        for (CommonStatic.Lang.Locale l : CommonStatic.Lang.Locale.values())
            if (Arrays.stream(BCJSON.PC_LANG_CODES).anyMatch(x -> x.equals(l.code)))
                locales.add(l);
        return locales;
    }

    public static String readMaterialData(DefStageInfo data) {
        StringBuilder ans = new StringBuilder("<html>Material Drop Data<br><br>");

        ans.append(Page.get(MainLocale.PAGE, "maxmat")).append("<br>");
        for (int i = 0; i < data.map.multiplier.length; i++) {
            ans.append(i + 1).append(" ").append(Page.get(MainLocale.INFO, "star")).append(": ")
                    .append((int) (data.map.multiplier[i] * data.maxMaterial))
                    .append("<br>");
        }

        ans.append("<br><table><tr><th>")
                .append(Page.get(MainLocale.INFO, "mat"))
                .append("</th><th>")
                .append(Page.get(MainLocale.INFO, "chance"))
                .append("</th></tr>");

        int missChance = data.map.materialDrop[0];
        int totalChances = Arrays.stream(data.map.materialDrop).reduce(0, Integer::sum) - missChance;
        for (int i = 1 ; i < data.map.materialDrop.length; i++) {
            int chance = data.map.materialDrop[i];
            if (data.map.materialDrop[i] == 0)
                continue;

            ans.append("<tr><td>")
                    .append(Page.get(MainLocale.UTIL, "m" + (i - 1)))
                    .append("</td><td>")
                    .append((double) (((100 - missChance) * chance * 100) / totalChances) / 100.0).append("%")
                    .append("</td></tr>");
        }

        ans.append("</table></html>");

        return ans.toString();
    }
}