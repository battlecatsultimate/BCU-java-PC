package page.info;

import page.MainLocale;

public strictfp class InfoText {

	public static String get(String key) {
		return MainLocale.getLoc(1, key);
	}

	public static String[] get(String pre, int max) {
		return MainLocale.getLoc(1, pre, max);
	}

}
