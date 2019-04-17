package page.internet;

import java.util.ResourceBundle;

import page.MainLocale;

public class WebText {

	private static final ResourceBundle REN = ResourceBundle.getBundle("page.internet.Lang_en");

	public static String get(String key) {
		return MainLocale.getDef("internet_", REN, key);
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
