package page;

import java.io.PrintStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TreeMap;

import io.Writer;
import page.basis.ComboListTable;
import page.battle.BattleInfoPage;
import page.info.HeadTable;
<<<<<<< HEAD
import page.info.StageTable;
import page.info.edit.StageEditPage;
import page.info.filter.EnemyListTable;
import page.info.filter.UnitListTable;
import util.Interpret;
import util.anim.AnimU;

public strictfp class MainLocale {

	public static final Map<String, MainLocale> NAMP = new TreeMap<>();
	public static final Map<String, TTT> TMAP = new TreeMap<>();
	public static final String[] LOC_NAME = { "English", "\u4E2D\u6587", "\uD55C\uAD6D\uC5B4", "Japanese", "Russian",
			"German", "French", "Dutch", "Spainsh" };
	public static final String[] LOC_CODE = { "en", "zh", "kr", "jp", "ru", "de", "fr", "nl", "es" };
	private static final String[] RENN = { "page", "info", "internet", "util" };
	private static final ResourceBundle[] RENS = new ResourceBundle[4];

	static {
		for (int i = 0; i < 4; i++)
			RENS[i] = ResourceBundle.getBundle("page." + RENN[i]);
	}

	public static int lang = 0;
	public static boolean exLang, exTTT;

	public static TTT addTTT(String loc, String page, String text, String cont) {
		TTT ttt = TMAP.get(loc);
		if (ttt == null)
			TMAP.put(loc, ttt = new TTT());
		if (text.equals("*"))
			ttt.tttp.put(page, cont);
		else if (page.equals("*"))
			ttt.ttts.put(text, cont);
		else {
			if (!ttt.ttt.containsKey(page))
				ttt.ttt.put(page, new TreeMap<String, String>());
			ttt.ttt.get(page).put(text, cont);
		}
		return ttt;
	}

	public static String getLoc(int loc, String key) {
		if (loc >= 0 && loc < 4) {
			String loci = RENN[loc] + "_";
			String locl = loci + LOC_CODE[MainLocale.lang];
			if (NAMP.containsKey(locl) && NAMP.get(locl).contains(key)) {
				String str = NAMP.get(loci + LOC_CODE[MainLocale.lang]).get(key);
				String[] strs = str.split("#");
				if (strs.length == 1)
					return str;
				for (int i = 1; i < strs.length; i += 2)
					strs[i] = getLoc(loc, strs[i]);
				String ans = "";
				for (int i = 0; i < strs.length; i++)
					ans += strs[i];
				return ans;
			}
			try {
				return exLang ? "[" + loci + key + "]" : RENS[loc].getString(key);
			} catch (MissingResourceException e) {
				return key;
			}
		}
		return key;
	}

	public static String[] getLoc(int loc, String... strs) {
		String[] ans = new String[strs.length];
		for (int i = 0; i < ans.length; i++)
			ans[i] = getLoc(loc, strs[i]);
		return ans;
	}

	public static String[] getLoc(int loc, String pre, int max) {
		String[] ans = new String[max];
		for (int i = 0; i < ans.length; i++)
			ans[i] = getLoc(loc, pre + i);
		return ans;
	}

	public static void saveWorks() {
		for (String loc : LOC_CODE) {
			TTT ttt = TMAP.get(loc);
			if (ttt != null && ttt.edited)
				ttt.write(Writer.newFile("./lib/lang/" + loc + "/tutorial.txt"));
			for (int i = 0; i < 4; i++) {
				MainLocale ml = NAMP.get(RENN[i] + "_" + loc);
				if (ml != null && ml.edited)
					ml.write(Writer.newFile("./lib/lang/" + loc + "/" + RENN[i] + ".properties"));
			}
		}
	}

	protected static String getID(int i) {
		if (i >= 0 && i < 4)
			return RENN[i];
		return "";
	}

	protected static String getTTT(String page, String text) {
		String loc = LOC_CODE[MainLocale.lang];
		String ans = null;
		if (TMAP.containsKey(loc))
			ans = TMAP.get(loc).getTTT(page, text);
		if (ans != null)
			return ans;
		if (exTTT)
			return "[" + page + "_" + text + "]";
		loc = LOC_CODE[0];
		if (TMAP.containsKey(loc))
			return TMAP.get(loc).getTTT(page, text);
		return null;
	}

	protected static void redefine() {
		AnimU.redefine();
		Interpret.redefine();
		EnemyListTable.redefine();
		UnitListTable.redefine();
		ComboListTable.redefine();
		StageTable.redefine();
		HeadTable.redefine();
		BattleInfoPage.redefine();
		StageEditPage.redefine();
	}

	protected static void setLoc(int i, String key, String value) {
		String loc = getID(i) + "_" + LOC_CODE[lang];
		MainLocale ml = NAMP.get(loc);
		if (ml == null)
			NAMP.put(loc, ml = new MainLocale(loc));
		ml.res.put(key, value);
		ml.edited = true;
	}

	protected static void setTTT(String loc, String info, String str) {
		addTTT(LOC_CODE[lang], loc, info, str).edited = true;
	}

	public final Map<String, String> res = new TreeMap<>();

	public boolean edited;

	public MainLocale(String str) {
		NAMP.put(str, this);
	}

	public void write(PrintStream ps) {
		for (Entry<String, String> ent : res.entrySet())
			ps.println(ent.getKey() + "\t" + ent.getValue());
	}

	private boolean contains(String str) {
		return res.containsKey(str);
	}

	private String get(String str) {
		if (res.containsKey(str))
			return res.get(str);
		return "!" + str + "!";
	}

}

class TTT {

	public final Map<String, String> tttp = new TreeMap<>();
	public final Map<String, String> ttts = new TreeMap<>();
	public final Map<String, Map<String, String>> ttt = new TreeMap<>();

	public boolean edited;

	public void write(PrintStream ps) {
		if (ps == null)
			return;
		for (Entry<String, String> ent : tttp.entrySet())
			ps.println(ent.getKey() + "\t*\t" + ent.getValue());
		for (Entry<String, String> ent : ttts.entrySet())
			ps.println("*\t" + ent.getKey() + "\t" + ent.getValue());
		for (Entry<String, Map<String, String>> ent : ttt.entrySet())
			for (Entry<String, String> snt : ent.getValue().entrySet())
				ps.println(ent.getKey() + "\t" + snt.getKey() + "\t" + snt.getValue());
	}

	protected String getTTT(String page, String text) {
		if (ttt.get(page) != null) {
			String strs = ttt.get(page).get(text);
			if (strs != null)
				return strs;
		}
		String strt = ttts.get(text);
		if (strt != null)
			return strt;
		String strp = tttp.get(page);
		if (strp != null)
			return strp;
		return null;
=======
import page.info.InfoText;
import page.info.StageTable;
import page.info.edit.StageEditPage;
import page.info.filter.EnemyListTable;
import page.info.filter.UnitListTable;
import page.internet.WebText;
import util.Interpret;
import util.Msg;
import util.anim.AnimU;

public strictfp class MainLocale {

	public static final Map<String, MainLocale> map = new TreeMap<>();
	public static final Map<String, TTT> tmap = new TreeMap<>();
	public static final String[] strs = new String[] { "English", "\u4E2D\u6587", "\uD55C\uAD6D\uC5B4", "Japanese",
			"Russian", "German", "French" };
	public static final String[] LOCALE = new String[] { "en", "zh", "kr", "jp", "ru", "de", "fr" };
	private static final ResourceBundle REN = ResourceBundle.getBundle("page.Lang_en");

	public static int lang = 0;
	public static boolean exLang, exTTT;

	public static TTT addTTT(String loc, String page, String text, String cont) {
		TTT ttt = tmap.get(loc);
		if (ttt == null)
			tmap.put(loc, ttt = new TTT());
		if (text.equals("*"))
			ttt.tttp.put(page, cont);
		else if (page.equals("*"))
			ttt.ttts.put(text, cont);
		else {
			if (!ttt.ttt.containsKey(page))
				ttt.ttt.put(page, new TreeMap<String, String>());
			ttt.ttt.get(page).put(text, cont);
		}
		return ttt;
	}

	public static String getDef(String loc, ResourceBundle rb, String key) {
		if (contains(loc, key))
			return get(loc, key);
		try {
			return exLang ? "[" + loc + key + "]" : rb.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	protected static String getTTT(String page, String text) {
		String loc = LOCALE[MainLocale.lang];
		String ans = null;
		if (tmap.containsKey(loc))
			ans = tmap.get(loc).getTTT(page, text);
		if (ans != null)
			return ans;
		if (exTTT)
			return "[" + page + "_" + text + "]";
		loc = LOCALE[0];
		if (tmap.containsKey(loc))
			return tmap.get(loc).getTTT(page, text);
		return null;
	}

	protected static String getID(int i) {
		if (i == 0)
			return "page";
		if (i == 1)
			return "info";
		if (i == 2)
			return "internet";
		if (i == 3)
			return "util";
		return "";
	}

	protected static String getLoc(int i, String key) {
		if (i == 0)
			return getString(key);
		if (i == 1)
			return InfoText.get(key);
		if (i == 2)
			return WebText.get(key);
		if (i == 3)
			return Msg.get(key);
		return key;
	}

	protected static String getString(String key) {
		return getDef("page_", REN, key);
	}

	protected static void redefine() {
		AnimU.redefine();
		Interpret.redefine();
		EnemyListTable.redefine();
		UnitListTable.redefine();
		ComboListTable.redefine();
		StageTable.redefine();
		HeadTable.redefine();
		BattleInfoPage.redefine();
		StageEditPage.redefine();
	}

	protected static void setLoc(int i, String key, String value) {
		String loc = getID(i) + "_" + LOCALE[lang];
		MainLocale ml = map.get(loc);
		if (ml == null)
			map.put(loc, ml=new MainLocale(loc));
		ml.res.put(key, value);
		ml.edited=true;
	}

	protected static void setTTT(String loc, String info, String str) {
		addTTT(LOCALE[lang], loc, info, str).edited=true;
	}

	public static void saveWorks() {
		for(String loc:LOCALE) {
			TTT ttt=tmap.get(loc);
			if(ttt!=null&&ttt.edited)
				ttt.write(Writer.newFile("./lib/lang/"+loc+"/tutorial.txt"));
			for(int i=0;i<4;i++) {
				MainLocale ml=map.get(getID(i)+"_"+loc);
				if(ml!=null&&ml.edited)
					ml.write(Writer.newFile("./lib/lang/"+loc+"/"+getID(i)+".properties"));
			}
		}
	}
	
	private static boolean contains(String loc, String key) {
		loc += LOCALE[MainLocale.lang];
		if (!map.containsKey(loc))
			return false;
		return map.get(loc).contains(key);
	}

	private static String get(String loc, String str) {
		loc += LOCALE[MainLocale.lang];
		return map.get(loc).get(str);
	}

	public final Map<String, String> res = new TreeMap<>();

	public boolean edited;
	
	public MainLocale(String str) {
		map.put(str, this);
	}

	private boolean contains(String str) {
		return res.containsKey(str);
	}

	private String get(String str) {
		if (res.containsKey(str))
			return res.get(str);
		return "!" + str + "!";
	}

	public void write(PrintStream ps) {
		for(Entry<String,String> ent:res.entrySet())
			ps.println(ent.getKey()+"\t"+ent.getValue());
	}
	
}

class TTT {

	public final Map<String, String> tttp = new TreeMap<>();
	public final Map<String, String> ttts = new TreeMap<>();
	public final Map<String, Map<String, String>> ttt = new TreeMap<>();

	public boolean edited;
	
	protected String getTTT(String page, String text) {
		if (ttt.get(page) != null) {
			String strs = ttt.get(page).get(text);
			if (strs != null)
				return strs;
		}
		String strt = ttts.get(text);
		if (strt != null)
			return strt;
		String strp = tttp.get(page);
		if (strp != null)
			return strp;
		return null;
	}
	
	public void write(PrintStream ps) {
		if(ps==null)
			return;
		for(Entry<String,String> ent:tttp.entrySet())
			ps.println(ent.getKey()+"\t*\t"+ent.getValue());
		for(Entry<String,String> ent:ttts.entrySet())
			ps.println("*\t"+ent.getKey()+"\t"+ent.getValue());
		for(Entry<String,Map<String,String>> ent:ttt.entrySet())
			for(Entry<String,String> snt:ent.getValue().entrySet())
				ps.println(ent.getKey()+"\t"+snt.getKey()+"\t"+snt.getValue());
>>>>>>> branch 'master' of https://github.com/lcy0x1/BCU.git
	}
}
