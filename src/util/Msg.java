package util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import page.MainLocale;

public class Msg {

	private static final ResourceBundle REN = ResourceBundle.getBundle("util.Lang_en");

	public static String get(String key) {
		try {
			String str = MainLocale.getDef("util_", REN, key);
			String[] strs = str.split("#");
			if (strs.length == 1)
				return str;
			for (int i = 1; i < strs.length; i += 2)
				strs[i] = get(strs[i]);
			String ans = "";
			for (int i = 0; i < strs.length; i++)
				ans += strs[i];
			return ans;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	public static String[] get(String pre, int max) {
		String[] ans = new String[max];
		for (int i = 0; i < ans.length; i++)
			ans[i] = get(pre + i);
		return ans;
	}

}
