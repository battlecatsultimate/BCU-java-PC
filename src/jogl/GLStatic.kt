package jogl

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLProfile
import common.battle.data.DataEntity
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
import main.MainBCU
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter

object GLStatic {
    const val MIP = false
    val GLP: GLProfile? = null
    val GLC: GLCapabilities? = null
    var ALWAYS_GLIMG = true
    var GLTEST: Boolean = !MainBCU.WRITE
    var JOGL_SHADER = true
    var ALL_BIMG = true

    init {
        GLP = GLProfile.get(GLProfile.GL2)
        GLC = GLCapabilities(GLP)
    }
}
