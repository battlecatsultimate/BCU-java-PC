package utilpc

import common.io.assets.Admin
import common.io.assets.Admin.StaticPermitted
import common.io.assets.AssetLoader
import common.io.assets.AssetLoader.AssetHeader
import common.io.assets.AssetLoader.AssetHeader.AssetEntry
import common.io.json.JsonEncoder
import common.io.json.Test
import common.io.json.Test.JsonTest_0.JsonD
import common.io.json.Test.JsonTest_2
import common.pack.Source.AnimLoader
import common.pack.Source.ResourceLocation
import common.pack.Source.SourceAnimLoader
import common.pack.Source.SourceAnimSaver
import common.pack.Source.Workspace
import common.pack.Source.ZipSource
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCPlayer
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
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
