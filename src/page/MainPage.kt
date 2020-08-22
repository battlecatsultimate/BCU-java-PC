package page

import common.util.stage.MapColc
import io.BCUWriter
import page.Page
import page.anim.DIYViewPage
import page.anim.ImgCutEditPage
import page.anim.MaAnimEditPage
import page.anim.MaModelEditPage
import page.basis.BasisPage
import page.battle.BattleInfoPage
import page.battle.RecdManagePage
import page.info.StageViewPage
import page.info.filter.EnemyFindPage
import page.info.filter.UnitFindPage
import page.pack.PackEditPage
import page.pack.ResourcePage
import page.view.*
import java.awt.event.ActionEvent
import java.util.function.Consumer
import java.util.function.Supplier
import javax.swing.JLabel

class MainPage : Page(null) {
    private val memo = JLabel()
    private val seicon = JLabel("Source of enemy icon: battlecats-db.com")
    private val sgifau = JLabel("Author of GIF exporter: Kevin Weiner, FM Software")
    private val vuni: JBTN = JBTN(0, "vuni")
    private val vene: JBTN = JBTN(0, "vene")
    private val vsta: JBTN = JBTN(0, "vsta")
    private val vdiy: JBTN = JBTN(0, "vdiy")
    private val conf: JBTN = JBTN(0, "conf")
    private val veff: JBTN = JBTN(0, "veff")
    private val vcas: JBTN = JBTN(0, "vcas")
    private val vbgr: JBTN = JBTN(0, "vbgr")
    private val veif: JBTN = JBTN(0, "veif")
    private val vuif: JBTN = JBTN(0, "vuif")
    private val vmsc: JBTN = JBTN(0, "vmsc")
    private val bass: JBTN = JBTN(0, "bass")
    private val curr: JBTN = JBTN(0, "curr")
    private val pcus: JBTN = JBTN(0, "pcus")
    private val rply: JBTN = JBTN(0, "rply")
    private val caic: JBTN = JBTN(0, "caic")
    private val camm: JBTN = JBTN(0, "camm")
    private val cama: JBTN = JBTN(0, "cama")
    private val save: JBTN = JBTN(0, "save")
    private val bckp: JBTN = JBTN(0, "backup")
    private val allf: JBTN = JBTN(0, "all file")
    override fun renew() {
        Runtime.getRuntime().gc()
        curr.setEnabled(BattleInfoPage.Companion.current != null)
        setMemo()
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(memo, x, y, 50, 30, 500, 50)
        Page.Companion.set(seicon, x, y, 50, 60, 500, 50)
        Page.Companion.set(sgifau, x, y, 50, 90, 800, 50)
        Page.Companion.set(vuni, x, y, 600, 200, 200, 50)
        Page.Companion.set(vene, x, y, 600, 300, 200, 50)
        Page.Companion.set(veff, x, y, 600, 400, 200, 50)
        Page.Companion.set(vcas, x, y, 600, 500, 200, 50)
        Page.Companion.set(vbgr, x, y, 600, 600, 200, 50)
        Page.Companion.set(vmsc, x, y, 600, 700, 200, 50)
        Page.Companion.set(allf, x, y, 600, 800, 200, 50)
        Page.Companion.set(conf, x, y, 900, 200, 200, 50)
        Page.Companion.set(save, x, y, 900, 300, 200, 50)
        Page.Companion.set(bass, x, y, 900, 400, 200, 50)
        Page.Companion.set(bckp, x, y, 900, 500, 200, 50)
        Page.Companion.set(curr, x, y, 900, 600, 200, 50)
        Page.Companion.set(vsta, x, y, 1200, 200, 200, 50)
        Page.Companion.set(veif, x, y, 1200, 300, 200, 50)
        Page.Companion.set(vuif, x, y, 1200, 400, 200, 50)
        Page.Companion.set(pcus, x, y, 1200, 500, 200, 50)
        Page.Companion.set(rply, x, y, 1200, 600, 200, 50)
        Page.Companion.set(vdiy, x, y, 1500, 200, 200, 50)
        Page.Companion.set(caic, x, y, 1500, 300, 200, 50)
        Page.Companion.set(camm, x, y, 1500, 400, 200, 50)
        Page.Companion.set(cama, x, y, 1500, 500, 200, 50)
    }

    private fun addListeners() {
        vuni.setLnr(Supplier<Page?> { UnitViewPage(this, null as String?) })
        vene.setLnr(Supplier<Page?> { EnemyViewPage(this, null as String?) })
        vsta.setLnr(Supplier<Page?> { StageViewPage(this, MapColc.Companion.values()) })
        vdiy.setLnr(Supplier<Page?> { DIYViewPage(this) })
        conf.setLnr(Supplier<Page?> { ConfigPage(this) })
        veff.setLnr(Supplier<Page?> { EffectViewPage(this) })
        vcas.setLnr(Supplier<Page?> { CastleViewPage(this) })
        vbgr.setLnr(Supplier<Page?> { BGViewPage(this, null) })
        veif.setLnr(Supplier<Page?> { EnemyFindPage(this, null) })
        vuif.setLnr(Supplier<Page?> { UnitFindPage(this, null) })
        bass.setLnr(Supplier<Page?> { BasisPage(this) })
        curr.setLnr(Supplier<Page?> { BattleInfoPage.Companion.current })
        pcus.setLnr(Supplier<Page?> { PackEditPage(this) })
        caic.setLnr(Supplier<Page?> { ImgCutEditPage(this) })
        camm.setLnr(Supplier<Page?> { MaModelEditPage(this) })
        cama.setLnr(Supplier<Page?> { MaAnimEditPage(this) })
        save.setLnr(Consumer { e: ActionEvent? -> BCUWriter.writeData() })
        vmsc.setLnr(Supplier<Page?> { MusicPage(this) })
        rply.setLnr(Supplier<Page?> { RecdManagePage(this) })
        allf.setLnr(Supplier<Page?> { ResourcePage(this) })
    }

    private fun ini() {
        add(vuni)
        add(vene)
        add(vsta)
        add(vdiy)
        add(conf)
        add(veff)
        add(vcas)
        add(vbgr)
        add(veif)
        add(vuif)
        add(vmsc)
        add(bass)
        add(memo)
        add(curr)
        add(pcus)
        add(caic)
        add(camm)
        add(cama)
        add(save)
        add(seicon)
        add(sgifau)
        add(rply)
        add(bckp)
        add(allf)
        setMemo()
        addListeners()
    }

    private fun setMemo() {
        val f = Runtime.getRuntime().freeMemory()
        val t = Runtime.getRuntime().totalMemory()
        val m = Runtime.getRuntime().maxMemory()
        val per = 100.0 * (t - f) / m
        memo.text = "memory used: " + (t - f shr 20) + " MB / " + (m shr 20) + " MB, " + per.toInt() + "%"
    }

    companion object {
        private const val serialVersionUID = 1L
    }

    init {
        ini()
        resized()
    }
}
