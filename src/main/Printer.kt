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
import page.JL
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import java.text.SimpleDateFormat
import java.util.*

object Printer {
    fun e(source: String?, line: Int, description: String?) {
        Printer.print(source, line, description)
    }

    fun p(source: String?, line: Int, description: String?) {
        if (MainBCU.WRITE) return
        Printer.print(source, line, description)
    }

    fun r(i: Int, string: String?) {
        Printer.e("Reader", i, string)
    }

    fun w(i: Int, string: String?) {
        Printer.e("Writer", i, string)
    }

    private fun print(source: String, line: Int, description: String) {
        val date = Date()
        val h: String = SimpleDateFormat("HH").format(date)
        val m: String = SimpleDateFormat("mm").format(date)
        val s: String = SimpleDateFormat("ss").format(date)
        println("[$source:#$line,$h:$m:$s]:$description")
    }
}
