package page.info;

import java.util.ResourceBundle;

import page.MainLocale;

public strictfp class InfoText {

	private static final ResourceBundle REN = ResourceBundle.getBundle("page.info.Lang_en");

	public static String get(String key) {
		return MainLocale.getDef("info_", REN, key);
	}

	public static String[] get(String... strs) {
		String[] ans = new String[strs.length];
		for (int i = 0; i < ans.length; i++)
			ans[i] = get(strs[i]);
		return ans;
	}

	public static String[] get(String pre, int max) {
		String[] ans = new String[max];
		for (int i = 0; i < ans.length; i++)
			ans[i] = get(pre + i);
		return ans;
	}

}
