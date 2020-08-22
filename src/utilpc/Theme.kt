package utilpc

import java.awt.Color

object Theme {
    val LIGHT: ColorSet = ColorSet(Color(216, 216, 216), Color(179, 229, 252),  // Tooltip BG
            Color(144, 144, 144),  // Button, Progress bar Color
            Color(248, 187, 0), Color(189, 189, 189),  // Disabled Text
            Color(115, 164, 209), Color(174, 213, 129), Color(187, 222, 251), Color(245, 245, 245),
            Color(255, 204, 128), Color(239, 154, 154), Color(91, 91, 91), Color(187, 222, 251),
            Color(91, 91, 91))
    val DARK: ColorSet = ColorSet(Color(64, 64, 64), Color(98, 117, 127),  // Tooltip BG
            Color(16, 16, 16),  // Button, Progress bar Color
            Color(248, 187, 0), Color(174, 174, 174),  // Disabled Text
            Color(98, 117, 127), Color(174, 213, 129), Color(98, 117, 127), Color(64, 64, 64),  // Panel
            // BG
            Color(255, 204, 128), Color(239, 154, 154), Color(245, 245, 245),  // Text Color
            Color(98, 117, 127), Color(245, 245, 245) // Text Color
    )
}
