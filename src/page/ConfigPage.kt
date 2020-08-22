package page

import common.CommonStatic
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
import common.util.ImgCore
import common.util.stage.EStage
import common.util.stage.StageMap
import common.util.stage.StageMap.StageMapInfo
import common.util.unit.UnitLevel
import io.BCMusic
import io.BCPlayer
import io.BCUReader
import main.MainBCU
import main.Opts
import page.JL
import page.MainLocale
import page.Page
import page.anim.AnimBox
import page.support.ListJtfPolicy
import page.support.SortTable
import page.view.ViewBox
import page.view.ViewBox.Conf
import page.view.ViewBox.Controller
import page.view.ViewBox.VBExporter
import utilpc.Theme
import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.util.function.Consumer
import javax.swing.*
import javax.swing.event.ChangeListener
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class ConfigPage(p: Page?) : Page(p) {
    private val back: JBTN = JBTN(0, "back")
    private val filt: JBTN = JBTN(0, "filter" + MainBCU.FILTER_TYPE)
    private val rlla: JBTN = JBTN(0, "rllang")
    private val prel: JTG = JTG(0, "preload")
    private val whit: JTG = JTG(0, "white")
    private val refe: JTG = JTG(0, "axis")
    private val jogl: JTG = JTG(0, "JOGL")
    private val musc: JTG = JTG(0, "musc")
    private val exla: JTG = JTG(0, "exlang")
    private val extt: JTG = JTG(0, "extip")
    private val left: Array<JBTN?> = arrayOfNulls<JBTN>(4)
    private val right: Array<JBTN?> = arrayOfNulls<JBTN>(4)
    private val name: Array<JL?> = arrayOfNulls<JL>(4)
    private val vals: Array<JL?> = arrayOfNulls<JL>(4)
    private val jlmin: JL = JL(0, "opamin")
    private val jlmax: JL = JL(0, "opamax")
    private val jlbg: JL = JL(0, "BGvol")
    private val jlse: JL = JL(0, "SEvol")
    private val theme: JBTN = JBTN(0, if (MainBCU.light) "themel" else "themed")
    private val nimbus: JBTN = JBTN(0, if (MainBCU.nimbus) "nimbus" else "tdefault")
    private val jsmin: JSlider = JSlider(0, 100)
    private val jsmax: JSlider = JSlider(0, 100)
    private val jsbg: JSlider = JSlider(0, 100)
    private val jsse: JSlider = JSlider(0, 100)
    private val jls: JList<String> = JList<String>(MainLocale.Companion.LOC_NAME)
    private val jsps: JScrollPane = JScrollPane(jls)
    private var changing = false
    override fun renew() {
        jlmin.setText(0, "opamin")
        jlmax.setText(0, "opamax")
        for (i in 0..3) {
            name[i]!!.setText(0, ImgCore.Companion.NAME.get(i))
            vals[i]!!.setText(0, ImgCore.Companion.VAL.get(cfg().ints.get(i)))
        }
    }

    override fun resized(x: Int, y: Int) {
        setBounds(0, 0, x, y)
        Page.Companion.set(back, x, y, 0, 0, 200, 50)
        Page.Companion.set(jogl, x, y, 50, 100, 200, 50)
        Page.Companion.set(prel, x, y, 50, 200, 200, 50)
        Page.Companion.set(whit, x, y, 50, 300, 200, 50)
        Page.Companion.set(refe, x, y, 50, 400, 200, 50)
        for (i in 0..3) {
            Page.Companion.set(name[i], x, y, 300, 100 + i * 100, 200, 50)
            Page.Companion.set(left[i], x, y, 550, 100 + i * 100, 100, 50)
            Page.Companion.set(vals[i], x, y, 650, 100 + i * 100, 200, 50)
            Page.Companion.set(right[i], x, y, 850, 100 + i * 100, 100, 50)
        }
        Page.Companion.set(jsps, x, y, 1100, 100, 200, 400)
        Page.Companion.set(jlmin, x, y, 50, 500, 400, 50)
        Page.Companion.set(jsmin, x, y, 50, 550, 1000, 100)
        Page.Companion.set(jlmax, x, y, 50, 650, 400, 50)
        Page.Companion.set(jsmax, x, y, 50, 700, 1000, 100)
        Page.Companion.set(jlbg, x, y, 50, 800, 400, 50)
        Page.Companion.set(jsbg, x, y, 50, 850, 1000, 100)
        Page.Companion.set(jlse, x, y, 50, 950, 400, 50)
        Page.Companion.set(jsse, x, y, 50, 1000, 1000, 100)
        Page.Companion.set(filt, x, y, 1100, 550, 200, 50)
        Page.Companion.set(musc, x, y, 1350, 550, 200, 50)
        Page.Companion.set(exla, x, y, 1100, 650, 450, 50)
        Page.Companion.set(extt, x, y, 1100, 750, 450, 50)
        Page.Companion.set(rlla, x, y, 1100, 850, 450, 50)
        Page.Companion.set(nimbus, x, y, 1100, 950, 200, 50)
        Page.Companion.set(theme, x, y, 1350, 950, 200, 50)
    }

    private fun addListeners() {
        back.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                changePanel(front)
            }
        })
        prel.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                MainBCU.preload = prel.isSelected()
            }
        })
        exla.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                MainLocale.Companion.exLang = exla.isSelected()
                Page.Companion.renewLoc(getThis())
            }
        })
        extt.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                MainLocale.Companion.exTTT = extt.isSelected()
                Page.Companion.renewLoc(getThis())
            }
        })
        rlla.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                BCUReader.readLang()
                Page.Companion.renewLoc(getThis())
            }
        })
        whit.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                Conf.white = whit.isSelected()
            }
        })
        refe.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                cfg().ref = refe.isSelected()
            }
        })
        jogl.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                MainBCU.USE_JOGL = jogl.isSelected()
                if (Opts.conf("This requires restart to apply. Do you want to restart?")) CommonStatic.def.exit(true)
            }
        })
        for (i in 0..3) {
            left[i].addActionListener(object : ActionListener {
                override fun actionPerformed(arg0: ActionEvent?) {
                    cfg().ints.get(i)--
                    vals[i]!!.setText(0, ImgCore.Companion.VAL.get(cfg().ints.get(i)))
                    left[i].setEnabled(cfg().ints.get(i) > 0)
                    right[i].setEnabled(cfg().ints.get(i) < 2)
                }
            })
            right[i].addActionListener(object : ActionListener {
                override fun actionPerformed(arg0: ActionEvent?) {
                    cfg().ints.get(i)++
                    vals[i]!!.setText(0, ImgCore.Companion.VAL.get(cfg().ints.get(i)))
                    left[i].setEnabled(cfg().ints.get(i) > 0)
                    right[i].setEnabled(cfg().ints.get(i) < 2)
                }
            })
        }
        jsmin.addChangeListener(ChangeListener { cfg().deadOpa = jsmin.getValue() })
        jsmax.addChangeListener(ChangeListener { cfg().fullOpa = jsmax.getValue() })
        jsbg.addChangeListener(ChangeListener { BCMusic.setBGVol(jsbg.getValue()) })
        jsse.addChangeListener(ChangeListener { BCMusic.setSEVol(jsse.getValue()) })
        jls.addListSelectionListener(object : ListSelectionListener {
            override fun valueChanged(arg0: ListSelectionEvent?) {
                if (changing) return
                changing = true
                if (jls.getSelectedIndex() == -1) {
                    jls.setSelectedIndex(cfg().lang)
                }
                cfg().lang = jls.getSelectedIndex()
                Page.Companion.renewLoc(getThis())
                changing = false
            }
        })
        filt.addActionListener(object : ActionListener {
            override fun actionPerformed(e: ActionEvent?) {
                MainBCU.FILTER_TYPE = 1 - MainBCU.FILTER_TYPE
                filt.setText(0, "filter" + MainBCU.FILTER_TYPE)
            }
        })
        musc.addActionListener(object : ActionListener {
            override fun actionPerformed(arg0: ActionEvent?) {
                BCMusic.play = musc.isSelected()
            }
        })
        nimbus.setLnr(Consumer { b: ActionEvent? ->
            MainBCU.nimbus = !MainBCU.nimbus
            if (Opts.conf("This requires restart to apply. Do you want to restart?")) CommonStatic.def.exit(true)
        })
        theme.setLnr(Consumer { b: ActionEvent? ->
            MainBCU.light = !MainBCU.light
            if (MainBCU.light) {
                theme.setText(MainLocale.Companion.getLoc(0, "themel"))
                Theme.LIGHT.setTheme()
                Page.Companion.BGCOLOR = Color(255, 255, 255)
                background = Page.Companion.BGCOLOR
                SwingUtilities.updateComponentTreeUI(this)
                theme.setToolTipText(MainLocale.Companion.getLoc(0, "themel"))
            } else {
                theme.setText(MainLocale.Companion.getLoc(0, "themed"))
                Theme.DARK.setTheme()
                Page.Companion.BGCOLOR = Color(40, 40, 40)
                background = Page.Companion.BGCOLOR
                SwingUtilities.updateComponentTreeUI(this)
                theme.setToolTipText(MainLocale.Companion.getLoc(0, "themed"))
            }
        })
    }

    private fun ini() {
        add(back)
        add(jogl)
        add(prel)
        add(refe)
        add(whit)
        add(jsps)
        set(jsmin)
        set(jsmax)
        add(jlmin)
        add(jlmax)
        add(filt)
        add(musc)
        add(rlla)
        add(exla)
        add(extt)
        add(jlbg)
        add(jlse)
        set(jsbg)
        set(jsse)
        add(nimbus)
        add(theme)
        jls.setSelectedIndex(cfg().lang)
        jsmin.setValue(cfg().deadOpa)
        jsmax.setValue(cfg().fullOpa)
        jsbg.setValue(BCMusic.VOL_BG)
        jsse.setValue(BCMusic.VOL_SE)
        for (i in 0..3) {
            left[i] = JBTN("<")
            right[i] = JBTN(">")
            name[i] = JL(0, ImgCore.Companion.NAME.get(i))
            vals[i] = JL(0, ImgCore.Companion.VAL.get(cfg().ints.get(i)))
            add(left[i])
            add(right[i])
            add(name[i])
            add(vals[i])
            name[i].setHorizontalAlignment(SwingConstants.CENTER)
            vals[i].setHorizontalAlignment(SwingConstants.CENTER)
            name[i].setBorder(BorderFactory.createEtchedBorder())
            vals[i].setBorder(BorderFactory.createEtchedBorder())
            left[i].setEnabled(cfg().ints.get(i) > 0)
            right[i].setEnabled(cfg().ints.get(i) < 2)
        }
        exla.setSelected(MainLocale.Companion.exLang)
        extt.setSelected(MainLocale.Companion.exTTT)
        prel.setSelected(MainBCU.preload)
        whit.setSelected(Conf.white)
        refe.setSelected(cfg().ref)
        musc.setSelected(BCMusic.play)
        jogl.setSelected(MainBCU.USE_JOGL)
        if (!MainBCU.nimbus) {
            theme.setEnabled(false)
        }
        addListeners()
    }

    private fun set(sl: JSlider) {
        add(sl)
        sl.setMajorTickSpacing(10)
        sl.setMinorTickSpacing(5)
        sl.setPaintTicks(true)
        sl.setPaintLabels(true)
    }

    companion object {
        private const val serialVersionUID = 1L
        private fun cfg(): CommonStatic.Config {
            return CommonStatic.getConfig()
        }
    }

    init {
        ini()
        resized()
    }
}
