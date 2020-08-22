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
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException

class ColorSet internal constructor(val CONTROL: Color, val INFO: Color, val NIMBUS_BASE: Color, val NIMBUS_ALERT_YELLOW: Color, val NIMBUS_DISABLED_TEXT: Color, val NIMBUS_FOCUS: Color, val NIMBUS_GREEN: Color, val NIMBUS_INFO_BLUE: Color, val NIMBUS_LIGHT_BG: Color,
                                    val NIMBUS_ORANGE: Color, val NIMBUS_RED: Color, val NIMBUS_SELECT_TEXT: Color, val NIMBUS_SELECT_BG: Color, val NIMBUS_TEXT: Color) {
    fun setTheme() {
        UIManager.put("control", CONTROL)
        UIManager.put("info", INFO)
        UIManager.put("nimbusBase", NIMBUS_BASE)
        UIManager.put("nimbusAlertYellow", NIMBUS_ALERT_YELLOW)
        UIManager.put("nimbusDisabledText", NIMBUS_DISABLED_TEXT)
        UIManager.put("nimbusFocus", NIMBUS_FOCUS)
        UIManager.put("nimbusGreen", NIMBUS_GREEN)
        UIManager.put("nimbusInfoBlue", NIMBUS_INFO_BLUE)
        UIManager.put("nimbusLightBackground", NIMBUS_LIGHT_BG)
        UIManager.put("nimbusOrange", NIMBUS_ORANGE)
        UIManager.put("nimbusRed", NIMBUS_RED)
        UIManager.put("nimbusSelectedText", NIMBUS_SELECT_TEXT)
        UIManager.put("nimbusSelectionBackground", NIMBUS_SELECT_BG)
        UIManager.put("text", NIMBUS_TEXT)
        UIManager.put("List[Selected].textForeground", NIMBUS_TEXT)
        UIManager.put("Table.textForeground", NIMBUS_TEXT)
        try {
            for (info in UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus" == info.getName()) {
                    UIManager.setLookAndFeel(info.getClassName())
                    break
                }
            }
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: UnsupportedLookAndFeelException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
