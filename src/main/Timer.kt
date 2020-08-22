package main

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
import main.Timer
import page.JL
import page.MainFrame
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import javax.swing.SwingUtilities

@Strictfp
class Timer : Thread() {
    private var thr: Inv? = null
    override fun run() {
        while (true) {
            val m = System.currentTimeMillis()
            Timer.Companion.state = 0
            SwingUtilities.invokeLater(Inv().also { thr = it })
            try {
                var end = false
                while (!end) {
                    synchronized(thr!!) { end = Timer.Companion.state == 1 }
                    if (!end) sleep(1)
                }
                thr!!.join()
                Timer.Companion.delay = (System.currentTimeMillis() - m).toInt()
                Timer.Companion.inter = (Timer.Companion.inter * 9 + 100 * Timer.Companion.delay / Timer.Companion.p) / 10
                val sle = if (Timer.Companion.delay >= Timer.Companion.p) 1 else Timer.Companion.p - Timer.Companion.delay
                sleep(sle.toLong())
            } catch (e: InterruptedException) {
                return
            }
        }
    }

    companion object {
        var p = 33
        var inter = 0
        var state = 0
        private const val delay = 0
    }
}

@Strictfp
internal class Inv : Thread() {
    override fun run() {
        MainFrame.Companion.timer(-1)
        synchronized(this) { Timer.Companion.state = 1 }
    }
}
