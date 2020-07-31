package utilpc;

import java.awt.Color;

public class Theme {
	public static final ColorSet LIGHT = new ColorSet(
			new Color(216, 216, 216),
			new Color(179, 229, 252), //Tooltip BG
			new Color(144, 144, 144), //Button, Progress bar Color
			new Color(248, 187, 0),
			new Color(189, 189, 189), //Disabled Text
			new Color(115, 164, 209),
			new Color(174, 213, 129),
			new Color(129, 212, 250),
			new Color(245, 245, 245),
			new Color(255, 204, 128),
			new Color(239, 154, 154),
			new Color(91, 91, 91),
			new Color(128, 222, 234),
			new Color(91, 91, 91)
			);

	public static final ColorSet DARK = new ColorSet(
			new Color(64, 64, 64),
			new Color(84, 110, 122), //Tooltip BG
			new Color(16, 16, 16), //Button, Progress bar Color
			new Color(248, 187, 0),
			new Color(174, 174, 174), //Disabled Text
			new Color(115, 164, 209),
			new Color(174, 213, 129),
			new Color(129, 212, 250),
			new Color(64, 64, 64), //Panel BG
			new Color(255, 204, 128),
			new Color(239, 154, 154),
			new Color(245, 245, 245), //Text Color
			new Color(98, 117, 127),
			new Color(245, 245, 245) //Text Color
			);
}